package transactions.infrastructure.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import transactions.dtos.TransactionDTO;
import transactions.services.TransactionService;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@Path("/transaction")
@RequiredArgsConstructor
public class TransactionResource {

    private final TransactionService transactionService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDTO dto) {
        transactionService.createTransaction(dto);
        return Response.ok().status(Response.Status.CREATED).build();
    }

}
