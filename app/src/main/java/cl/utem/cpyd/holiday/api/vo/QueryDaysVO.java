package cl.utem.cpyd.holiday.api.vo;

import cl.utem.cpyd.holiday.persistence.model.BaseJson;
import java.time.LocalDate;

/**
 *
 * @author seba
 */
public class QueryDaysVO extends BaseJson {

    private LocalDate start = null;
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
