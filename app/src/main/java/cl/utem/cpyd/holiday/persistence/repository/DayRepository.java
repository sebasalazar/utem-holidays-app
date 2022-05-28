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

    /**
     * Busca una fecha por su atributo único (workingDate).
     *
     * @param workingDate Fecha de consulta
     * @return objeto day
     */
    public Day findByWorkingDate(LocalDate workingDate);

    /**
     *
     * @param holiday Flag que indica si la fecha es feriado o no
     * @param start Fecha de inicio
     * @param end Fecha de termino
     * @return La cantidad de registros que cumplen con la condición dada.
     */
    public Long countByHolidayAndWorkingDateBetween(boolean holiday, LocalDate start, LocalDate end);
}
