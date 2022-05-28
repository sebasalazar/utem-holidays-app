package cl.utem.cpyd.holiday.persistence.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Esta clase será la base para todos los objetos que compartan datos en el
 * sistema.
 *
 * @author seba
 */
public class Utem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
