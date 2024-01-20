package transactions.usecases;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import transactions.dtos.SendNotificationResponseDTO;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@RegisterRestClient(baseUri = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
public interface SendNotification {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public SendNotificationResponseDTO send();
}
