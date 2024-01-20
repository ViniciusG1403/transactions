package usecases;

import infrastructure.repositories.UsuarioRepository;
import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@RequestScoped
@RequiredArgsConstructor
public class UserAlreadyExistsByDocumento {

    private final UsuarioRepository usuarioRepository;

    public boolean execute(final String documento, final Integer id) {
        return usuarioRepository.find("documento", documento).stream().anyMatch(usuario -> {
            return !Objects.equals(usuario.getId(), id);
        });
    }

}
