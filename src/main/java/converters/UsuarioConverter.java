package converters;

import dtos.UsuarioDTO;
import infrastructure.entities.Usuario;
import jakarta.enterprise.context.RequestScoped;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@RequestScoped
public class UsuarioConverter {

    public Usuario dtoToOrm(final UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setDocumento(dto.getDocumento());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setType(dto.getType());
        usuario.setSaldo(dto.getSaldo());
        return usuario;
    }

    public UsuarioDTO ormToDto(final Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setDocumento(usuario.getDocumento());
        dto.setEmail(usuario.getEmail());
        dto.setPassword(usuario.getPassword());
        dto.setType(usuario.getType());
        dto.setSaldo(usuario.getSaldo());
        return dto;
    }

}
