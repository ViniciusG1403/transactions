package usuarios.services;

import usuarios.converters.UsuarioConverter;
import usuarios.dtos.UsuarioDTO;
import usuarios.infrastructure.entities.Usuario;
import usuarios.infrastructure.repositories.UsuarioRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import usuarios.usecases.UserAlreadyExistsByDocumento;
import usuarios.usecases.UserAlreadyExistsByEmail;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 19/01/24
 */
@RequestScoped
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioConverter converter;

    private final UserAlreadyExistsByEmail userAlreadyExistsByEmail;

    private final UserAlreadyExistsByDocumento userAlreadyExistsByDocumento;

    @Transactional
    public UsuarioDTO save(UsuarioDTO dto){
        validate(dto);
        Usuario orm = converter.dtoToOrm(dto);
        usuarioRepository.persist(orm);

        return converter.ormToDto(orm);
    }


    private void validate(UsuarioDTO dto){
        if(dto.getNome() == null || dto.getNome().isEmpty()){
            throw new ValidationException("O nome deve ser preenchido");
        }
        if(dto.getEmail() == null || dto.getEmail().isEmpty()){
            throw new ValidationException("O email deve ser preenchido");
        }
        if(dto.getPassword() == null || dto.getPassword().isEmpty()){
            throw new ValidationException("A senha deve ser preenchida");
        }
        if(dto.getType() == null){
            throw new ValidationException("O tipo deve ser preenchido");
        }

        if(userAlreadyExistsByEmail.execute(dto.getEmail(), dto.getId())){
            throw new ValidationException("Já existe um usuário com esse email");
        }

        if(userAlreadyExistsByDocumento.execute(dto.getDocumento(), dto.getId())){
            throw new ValidationException("Já existe um usuário com esse documento");
        }
    }
}
