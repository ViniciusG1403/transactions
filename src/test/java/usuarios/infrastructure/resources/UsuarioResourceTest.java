package usuarios.infrastructure.resources;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import usuarios.dtos.UsuarioDTO;
import usuarios.dtos.UsuarioSetSaldoDTO;
import usuarios.enumerations.TipoUsuario;

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
@DisplayName("Teste de integração do resource de usuários")
class UsuarioResourceTest {

    @Test
    @DataSet(cleanAfter = true)
    @DisplayName("Deve ser possível salvar um usuário do tipo comum")
    void shouldBePossibleSaveAUser(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Usuario comum");
        usuarioDTO.setDocumento("12345678901");
        usuarioDTO.setEmail("mail@test.com");
        usuarioDTO.setSaldo(BigDecimal.ZERO);
        usuarioDTO.setPassword("senha");
        usuarioDTO.setType(TipoUsuario.COMUM.ordinal());


        final Response response = given()
            .when()
            .body(usuarioDTO)
            .contentType("application/json")
            .post("/usuarios")
            .then()
            .extract()
            .response();

        assertEquals(Status.CREATED.getStatusCode(), response.statusCode());
        assertEquals("Usuario comum", response.getBody().jsonPath().getString("nome"));
        assertEquals("12345678901", response.getBody().jsonPath().getString("documento"));
        assertEquals("mail@test.com", response.getBody().jsonPath().getString("email"));
        assertEquals("senha", response.getBody().jsonPath().getString("password"));
        assertEquals("0", response.getBody().jsonPath().getString("saldo"));
        assertEquals(TipoUsuario.COMUM.ordinal(), response.getBody().jsonPath().getInt("type"));
    }

    @Test
    @DataSet(cleanAfter = true)
    @DisplayName("Deve ser possível salvar um usuário do tipo lojista")
    void shouldBePossibleSaveAUserLojista() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Usuario lojista");
        usuarioDTO.setDocumento("12345678901");
        usuarioDTO.setEmail("mail@mail.com");
        usuarioDTO.setSaldo(BigDecimal.ZERO);
        usuarioDTO.setPassword("senha");
        usuarioDTO.setType(TipoUsuario.LOJISTA.ordinal());

        final Response response = given()
            .when()
            .body(usuarioDTO)
            .contentType("application/json")
            .post("/usuarios")
            .then()
            .extract()
            .response();

        assertEquals(Status.CREATED.getStatusCode(), response.statusCode());
        assertEquals("Usuario lojista", response.getBody().jsonPath().getString("nome"));
        assertEquals("12345678901", response.getBody().jsonPath().getString("documento"));
        assertEquals("mail@mail.com", response.getBody().jsonPath().getString("email"));
        assertEquals("senha", response.getBody().jsonPath().getString("password"));
        assertEquals("0", response.getBody().jsonPath().getString("saldo"));
        assertEquals(TipoUsuario.LOJISTA.ordinal(), response.getBody().jsonPath().getInt("type"));
    }

    @Test
    @DataSet(value = "datasets/usuarios/set-saldo.yml", cleanAfter = true)
    @DisplayName("Deve ser possível atualizar o saldo de um usuário")
    void shouldBePossibleUpdateUserBalance() {
        UsuarioSetSaldoDTO usuarioSetSaldoDTO = new UsuarioSetSaldoDTO();
        usuarioSetSaldoDTO.setId(1L);
        usuarioSetSaldoDTO.setSaldo(new BigDecimal(100));

        final Response response = given()
            .when()
            .body(usuarioSetSaldoDTO)
            .contentType("application/json")
            .put("/usuarios/set-saldo")
            .then()
            .extract()
            .response();

        assertEquals(Status.OK.getStatusCode(), response.statusCode());
        assertEquals("Saldo atualizado com sucesso", response.getBody().asString());
    }
    @Test
    @DataSet(value = "datasets/usuarios/get-saldo.yml", cleanAfter = true)
    @DisplayName("Deve ser possível buscar o saldo de um usuário")
    void shouldBePossibleGetUserBalance() {
        final Response response = given()
            .when()
            .contentType("application/json")
            .get("/usuarios/1/get-saldo")
            .then()
            .extract()
            .response();

        assertEquals(Status.OK.getStatusCode(), response.statusCode());
        assertEquals("1500.00", response.getBody().asString());
    }

}