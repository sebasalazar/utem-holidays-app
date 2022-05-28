package cl.utem.cpyd.holiday.persistence.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author seba
 */
@Entity
@Table(name = "days")
public class Day extends PkEntityBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "working_date", nullable = false, unique = true)
    private LocalDate workingDate = LocalDate.now();

    @Column(name = "holiday", nullable = false)
    private boolean holiday = false;

    public LocalDate getWorkingDate() {
        return workingDate;
    }

    public void setWorkingDate(LocalDate workingDate) {
        this.workingDate = workingDate;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }
}
