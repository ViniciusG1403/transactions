package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 19/01/24
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "id")
    private Integer id;

}
