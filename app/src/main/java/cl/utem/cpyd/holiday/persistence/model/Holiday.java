package cl.utem.cpyd.holiday.persistence.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "holidays")
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    private Long id = null;

    @Column(name = "holiday", nullable = false, unique = true)
    private LocalDate holiday = LocalDate.now();

    @Column(name = "description")
    private String description = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHoliday() {
        return holiday;
    }

    public void setHoliday(LocalDate holiday) {
        this.holiday = holiday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Holiday other = (Holiday) obj;
        return Objects.equals(this.id, other.id);
    }
}
