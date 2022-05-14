package cl.utem.cpyd.holiday.persistence.repository;

import cl.utem.cpyd.holiday.persistence.model.ProcessDay;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessDateRepository extends JpaRepository<ProcessDay, Long> {

    public ProcessDay findByToday(LocalDate holiday);

    public Integer countByTodayBetween(LocalDate start, LocalDate end);
}
