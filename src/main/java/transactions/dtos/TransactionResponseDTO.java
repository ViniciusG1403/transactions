package transactions.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@Getter
@Setter
public class TransactionResponseDTO {

    private Long idTransacao;

    private String payerName;

    private String payeeName;

    private BigDecimal valor;

}
