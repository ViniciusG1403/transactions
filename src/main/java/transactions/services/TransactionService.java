package transactions.services;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import transactions.converters.TransactionConverter;
import transactions.dtos.AutorizeTransactionResponseDTO;
import transactions.dtos.SendNotificationResponseDTO;
import transactions.dtos.TransactionDTO;
import transactions.dtos.TransactionResponseDTO;
import transactions.infrastructure.entities.Transaction;
import transactions.infrastructure.repositories.TransactionRepository;
import transactions.usecases.SendNotification;
import transactions.usecases.ValidateTransaction;
import usuarios.enumerations.TipoUsuario;
import usuarios.infrastructure.entities.Usuario;
import usuarios.infrastructure.repositories.UsuarioRepository;

import java.util.List;
import java.util.Objects;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@RequestScoped
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final UsuarioRepository usuarioRepository;

    private final TransactionConverter transactionConverter;

    @Inject
    @RestClient
    private ValidateTransaction validateTransaction;

    @Inject
    @RestClient
    private SendNotification sendNotification;

    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = transactionRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Transação não encontrada"));

        Usuario payee = usuarioRepository.findByIdOptional(transaction.getPayeeId())
            .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));

        Usuario payer = usuarioRepository.findByIdOptional(transaction.getPayerId())
            .orElseThrow(() -> new NotFoundException("Pagador não encontrado"));

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setIdTransacao(transaction.getId());
        response.setValor(transaction.getValor());
        response.setPayerName(payer.getNome());
        response.setPayeeName(payee.getNome());

        return response;
    }

    public List<TransactionResponseDTO> findAllByPayer(Long id) {
        List<Transaction> transactions = transactionRepository.find("payerId", id).stream().toList();

        Usuario payer = usuarioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagador não encontrado"));

        return transactions.stream().map(transaction -> {
            Usuario payee = usuarioRepository.findByIdOptional(transaction.getPayeeId())
                .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));

            TransactionResponseDTO response = new TransactionResponseDTO();
            response.setIdTransacao(transaction.getId());
            response.setValor(transaction.getValor());
            response.setPayerName(payer.getNome());
            response.setPayeeName(payee.getNome());

            return response;
        }).toList();
    }

    public List<TransactionResponseDTO> findAllByPayee(Long id) {
        List<Transaction> transactions = transactionRepository.find("payeeId", id).stream().toList();

        Usuario payee = usuarioRepository.findByIdOptional(id)
            .orElseThrow(() -> new NotFoundException("Pagador não encontrado"));

        return transactions.stream().map(transaction -> {
            Usuario payer = usuarioRepository.findByIdOptional(transaction.getPayerId())
                .orElseThrow(() -> new NotFoundException("Beneficiário não encontrado"));

            TransactionResponseDTO response = new TransactionResponseDTO();
            response.setIdTransacao(transaction.getId());
            response.setValor(transaction.getValor());
            response.setPayeeName(payer.getNome());
            response.setPayerName(payee.getNome());

            return response;
        }).toList();
    }

    @Transactional
    public void createTransaction(TransactionDTO transactionDTO) {
        final Usuario payeer = usuarioRepository.findByIdOptional(
                transactionDTO.getPayerId())
            .orElseThrow(() -> new NotFoundException(
                "Pagador não encontrado com o ID " + transactionDTO.getPayerId()));

        validatePayer(payeer, transactionDTO);
        validateTransaction();

        final Usuario payee = usuarioRepository.findByIdOptional(
                transactionDTO.getPayeeId())
            .orElseThrow(() -> new NotFoundException(
                "Beneficiário não encontrado com o ID " + transactionDTO.getPayeeId()));

        payeer.setSaldo(payeer.getSaldo().subtract(transactionDTO.getValor()));
        payee.setSaldo(payee.getSaldo().add(transactionDTO.getValor()));
        try {
            usuarioRepository.persist(payeer);
            usuarioRepository.persist(payee);
            transactionRepository.persist(transactionConverter.dtoToOrm(transactionDTO));
            sendNotification();
        } catch (Exception e) {
            throw new ValidationException(
                "Erro ao realizar a transação, tente novamente mais tarde." + e.getMessage());
        }
    }

    private void validatePayer(Usuario payeer, TransactionDTO transactionDTO) {
        if (Objects.equals(payeer.getType(), TipoUsuario.LOJISTA)) {
            throw new ValidationException(
                "Lojistas não podem enviar transferencias, somente receber.");
        }

        if (payeer.getSaldo().compareTo(transactionDTO.getValor()) < 0) {
            throw new ValidationException(
                "Pagador não possui saldo suficiente para realizar a transação");
        }

        if (Objects.equals(payeer.getId(), transactionDTO.getPayeeId())) {
            throw new ValidationException(
                "Pagador não pode ser o mesmo que o beneficiário");
        }
    }

    private void validateTransaction() {
        AutorizeTransactionResponseDTO autorizeTransactionResponseDTO = validateTransaction.validate();
        if (!autorizeTransactionResponseDTO.getMessage().equals("Autorizado")) {
            throw new ValidationException("Transação não autorizada");
        }
    }

    private void sendNotification() {
        SendNotificationResponseDTO sendNotificationResponseDTO = sendNotification.send();
        if (!sendNotificationResponseDTO.getMessage().equals("true")) {
            throw new ValidationException("Notificação não enviada");
        }
    }
}
