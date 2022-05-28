package cl.utem.cpyd.holiday.task;

import cl.utem.cpyd.holiday.engine.client.RestClient;
import cl.utem.cpyd.holiday.engine.vo.GobHolidayVO;
import cl.utem.cpyd.holiday.manager.DayManager;
import cl.utem.cpyd.holiday.persistence.model.Day;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author seba
 */
@Service
public class UpdaterTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient DayManager dayManager;

    @Autowired
    private transient RestClient restClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdaterTask.class);

    @PostConstruct
    public void initTask() {
        try {
            tenYears();
        } catch (Exception e) {
            LOGGER.error("Error al iniciar tarea: {}", e.getMessage());
            LOGGER.debug("Error al iniciar tarea: {}", e.getMessage(), e);
        }
    }

    public void tenYears() {
        try {
            final YearMonth ym = YearMonth.now();
            final LocalDate end = LocalDate.of(ym.getYear() + 10, 12, 31);
            LocalDate current = LocalDate.of(ym.getYear(), 1, 1);

            while (current.isBefore(end)) {
                boolean holiday = (DayOfWeek.SATURDAY.equals(current.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(current.getDayOfWeek()));

                Day currentDay = new Day();
                currentDay.setWorkingDate(current);
                currentDay.setHoliday(holiday);
                Day create = dayManager.create(currentDay);
                if (create != null) {
                    LOGGER.info("La fecha {} fue creada con ID {}", current, create.getId());
                }

                current = current.plusDays(1);
            }
        } catch (Exception e) {
            LOGGER.error("Error al poblar los datos: {}", e.getMessage());
            LOGGER.debug("Error al poblar los datos: {}", e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 3 2 1,15 * ?")
    public void holidayProcess() {
        try {
            final String url = "https://apis.digital.gob.cl/fl/feriados";
            final String json = restClient.get(url);

            List<GobHolidayVO> holidays = new ArrayList<>();
            holidays = RestClient.MAPPER.readValue(json, holidays.getClass());
            if (CollectionUtils.isNotEmpty(holidays)) {
                for (GobHolidayVO holiday : holidays) {
                    Day day = new Day();
                    day.setWorkingDate(holiday.getDate());
                    day.setHoliday(true);
                    Day create = dayManager.create(day);
                    if (create != null) {
                        LOGGER.info("La fecha {} fue creada con ID {}", create.getWorkingDate(), create.getId());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al consultar feriados: {}", e.getMessage());
            LOGGER.debug("Error al consultar feriados: {}", e.getMessage(), e);
        }
    }
}
