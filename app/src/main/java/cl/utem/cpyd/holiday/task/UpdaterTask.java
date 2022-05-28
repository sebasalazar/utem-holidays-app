package cl.utem.cpyd.holiday.task;

import cl.utem.cpyd.holiday.engine.client.RestClient;
import cl.utem.cpyd.holiday.engine.vo.GobHolidayVO;
import cl.utem.cpyd.holiday.manager.DayManager;
import cl.utem.cpyd.holiday.persistence.model.Day;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * Se busca proyectar a 10 años, los posibles valores de los días, de tal
     * manera que al momento de realizar las consultas REST estos valores
     * existan y la complejidad del cálculo se reduzca.
     */
    private void tenYears() {
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
                    LOGGER.info("[DB] La fecha {} fue creada con ID {}", current, create.getId());
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
            /**
             * Realizamos una proyección a 10 años, ante el improbable escenario
             * de una consulta tan extensa.
             */
            tenYears();

            // Url del servicio que tiene la información de feriados.
            final String url = "https://apis.digital.gob.cl/fl/feriados";

            // Consulta de Feriados
            final String json = restClient.get(url);

            // Verificamos que exista información, el json no está en blanco.
            if (StringUtils.isNotBlank(json)) {
                // Le indicamos a Jackson que la respuesta esperada es una lista de objetos GobHolidayVO

                TypeReference<List<GobHolidayVO>> typeReference = new TypeReference<List<GobHolidayVO>>() {
                };

                // Transformamos el string json, en un arreglo de objetos
                List<GobHolidayVO> holidays = RestClient.MAPPER.readValue(json, typeReference);
                if (CollectionUtils.isNotEmpty(holidays)) {
                    // Iteramos sobre la información encontrada
                    for (GobHolidayVO holiday : holidays) {
                        Day day = new Day();
                        day.setWorkingDate(holiday.getDate());
                        day.setHoliday(true);
                        Day create = dayManager.create(day);
                        if (create != null) {
                            LOGGER.info("[GOB] La fecha {} fue creada con ID {}", create.getWorkingDate(), create.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al consultar feriados: {}", e.getMessage());
            LOGGER.debug("Error al consultar feriados: {}", e.getMessage(), e);
        }
    }
}
