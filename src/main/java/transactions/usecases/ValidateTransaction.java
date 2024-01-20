package transactions.usecases;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import transactions.dtos.AutorizeTransactionDTO;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@RegisterRestClient(baseUri = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc")
public interface ValidateTransaction {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public AutorizeTransactionDTO validate();
}
