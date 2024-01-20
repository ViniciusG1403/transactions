package transactions.infrastructure.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import transactions.infrastructure.entities.Transaction;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
public interface TransactionRepository extends PanacheRepository<Transaction> {
}
