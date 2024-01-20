package usuarios.dtos;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 19/01/24
 */
@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "O documento é obrigatório")
    private String documento;

    @NotNull(message = "O email é obrigatório")
    private String email;

    @NotNull(message = "A senha é obrigatória")
    private String password;

    @NotNull(message = "O tipo é obrigatório")
    @Min(value = 0, message = "O tipo deve ser 0 - Comum ou 1 - Lojista")
    @Max(value = 1, message = "O tipo deve ser 0 - Comum ou 1 - Lojista")
    private Integer type;

    @NotNull(message = "O saldo é obrigatório")
    @Min(value = 0, message = "O saldo deve ser maior ou igual a 0")
    private BigDecimal saldo;

}
