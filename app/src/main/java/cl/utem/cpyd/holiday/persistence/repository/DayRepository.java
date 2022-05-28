package cl.utem.cpyd.holiday.persistence.repository;

import cl.utem.cpyd.holiday.persistence.model.Day;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author seba
 */
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    public Day findByWorkingDate(LocalDate workingDate);

}
