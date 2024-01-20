package transactions.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "payer_id", nullable = false)
    private Integer payerId;

    @Column(name = "payee_id", nullable = false)
    private Integer payeeId;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

}
