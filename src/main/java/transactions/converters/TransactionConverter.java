package transactions.converters;

import jakarta.enterprise.context.RequestScoped;
import transactions.dtos.TransactionDTO;
import transactions.dtos.TransactionResponseDTO;
import transactions.infrastructure.entities.Transaction;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@RequestScoped
public class TransactionConverter {

    public Transaction dtoToOrm(TransactionDTO dto){
        Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setPayerId(dto.getPayerId());
        transaction.setPayeeId(dto.getPayeeId());
        transaction.setValor(dto.getValor());
        return transaction;
    }

    public TransactionDTO ormToDto(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setPayerId(transaction.getPayerId());
        dto.setPayeeId(transaction.getPayeeId());
        dto.setValor(transaction.getValor());
        return dto;
    }

}
