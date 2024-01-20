package transactions.infrastructure.resource;

import jakarta.ws.rs.*;
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaction(@PathParam("id") Long id) {
        return Response.ok(transactionService.findById(id)).build();
    }

    @GET
    @Path("/{id}/payer/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions(@PathParam("id") Long id) {
        return Response.ok(transactionService.findAllByPayer(id)).build();
    }

    @GET
    @Path("/{id}/payee/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionsByPayee(@PathParam("id") Long id) {
        return Response.ok(transactionService.findAllByPayee(id)).build();
    }

}
