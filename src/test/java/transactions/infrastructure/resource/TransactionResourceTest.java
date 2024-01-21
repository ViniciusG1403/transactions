package transactions.infrastructure.resource;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.xml.bind.ValidationException;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import transactions.dtos.TransactionDTO;

import java.math.BigDecimal;
import jakarta.ws.rs.core.Response.Status;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 21/01/24
 */
@DBRider
@QuarkusTest
@DisplayName("Teste de integração para o resource de transações")
class TransactionResourceTest {

    @Test
    @DataSet(value = "datasets/transactions/create-transaction.yml", cleanAfter = true)
    @DisplayName("Deve ser possível criar uma transação")
    void shouldBePossibleCreateATransaction() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setPayerId(1L);
        transactionDTO.setPayeeId(2L);
        transactionDTO.setValor(BigDecimal.valueOf(100));

        final Response response = given()
            .when()
            .body(transactionDTO)
            .contentType("application/json")
            .post("/transaction")
            .then()
            .extract()
            .response();

        assertEquals(Status.CREATED.getStatusCode(), response.statusCode());
    }

    @Test
    @DataSet(value = "datasets/transactions/get-transaction.yml", cleanAfter = true)
    @DisplayName("Deve ser possível buscar uma transação por ID")
    void shouldBePossibleGetATransactionById() {
        final Response response = given()
            .when()
            .get("/transaction/1")
            .then()
            .extract()
            .response();

        assertEquals(Status.OK.getStatusCode(), response.statusCode());
        assertEquals("UserComum", response.getBody().jsonPath().getString("payerName"));
        assertEquals("UserLojista", response.getBody().jsonPath().getString("payeeName"));
        assertEquals("500.0", response.getBody().jsonPath().getString("valor"));
    }

    @Test
    @DataSet(value = "datasets/transactions/get-transaction.yml", cleanAfter = true)
    @DisplayName("Deve ser possível buscar uma lista de transações por ID do pagador")
    void shouldBePossibleGetATransactionByPayerId() {
        final Response response = given()
            .when()
            .get("/transaction/1/payer/transactions")
            .then()
            .extract()
            .response();

        assertEquals(Status.OK.getStatusCode(), response.statusCode());
    }

    @Test
    @DataSet(value = "datasets/transactions/get-transaction.yml", cleanAfter = true)
    @DisplayName("Deve ser possível buscar uma lista de transações por ID do recebedor")
    void shouldBePossibleGetATransactionByPayeeId() {
        final Response response = given()
            .when()
            .get("/transaction/2/payee/transactions")
            .then()
            .extract()
            .response();

        assertEquals(Status.OK.getStatusCode(), response.statusCode());
    }


}