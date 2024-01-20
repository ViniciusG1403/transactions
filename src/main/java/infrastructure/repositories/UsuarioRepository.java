package infrastructure.repositories;

import infrastructure.entities.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 19/01/24
 */
public interface UsuarioRepository extends PanacheRepository<Usuario> {
}
