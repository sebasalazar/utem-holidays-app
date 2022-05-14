package cl.utem.cpyd.holiday.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;

/**
 *
 * @author Sebastián Salazar Molina
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseJson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(
                this, ToStringStyle.JSON_STYLE);
    }
}
