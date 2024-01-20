package usuarios.infrastructure.resources;

import jakarta.ws.rs.*;
import usuarios.dtos.UsuarioDTO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import usuarios.dtos.UsuarioSetSaldoDTO;
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

    @PUT
    @Path("/set-saldo")
    public Response setSaldoUsuario(UsuarioSetSaldoDTO dto) throws Exception {
        usuarioService.setSaldoUser(dto);
        return Response.ok("Saldo atualizado com sucesso").build();
    }

    @GET
    @Path("/{id}/get-saldo")
    public Response getSaldoUsuario(@PathParam("id") final Integer id) {
        return Response.ok(usuarioService.getSaldoUser(id)).build();
    }
}
