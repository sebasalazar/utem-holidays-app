package cl.utem.cpyd.holiday.api.vo;

import cl.utem.cpyd.holiday.persistence.model.Utem;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 *
 * @author seba
 */
@Schema(name = "QueryRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QueryRequestVO extends Utem {

    @Schema(description = "Fecha de inicio de la consulta")
    private LocalDate start = null;

    @Schema(description = "Fecha de término de la consulta")
    private LocalDate end = null;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

}
