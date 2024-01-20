package usuarios.enumerations;

/**
 * @author Vinicius Gabriel <vinicius.prado@nexuscloud.com.br>
 * @version 1.0
 * @since 20/01/24
 */
public enum TipoUsuario {

    COMUM, LOJISTA;

    public static TipoUsuario valueOf(Integer value) {
        return value == null ? null : values()[value];
    }

    public static Integer valueOf(final TipoUsuario value) {
        return value != null ? value.ordinal() : null;
    }
}
