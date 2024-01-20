package usuarios.infrastructure.resources;

import usuarios.dtos.UsuarioDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import usuarios.services.UsuarioService;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 19/01/24
 */
@Path("/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService usuarioService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(UsuarioDTO dto) {
        return Response.ok(usuarioService.save(dto)).status(Response.Status.CREATED).build();
    }
}
