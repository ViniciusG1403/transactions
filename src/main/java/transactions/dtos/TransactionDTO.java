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
public class TransactionDTO {

    private Integer id;

    private Integer payerId;

    private Integer payeeId;

    private BigDecimal value;


}
