package cl.utem.cpyd.holiday.persistence.repository;

import cl.utem.cpyd.holiday.persistence.model.Holiday;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    public Holiday findByHoliday(LocalDate holiday);
}
