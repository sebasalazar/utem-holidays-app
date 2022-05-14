package cl.utem.cpyd.holiday.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Sebastián Salazar Molina.
 */
public class LDTUtils implements Serializable {

    private static final long serialVersionUID = 7176835503513785344L;

    private static final Logger LOGGER = LoggerFactory.getLogger(LDTUtils.class);

    /**
     * Clase utilitaria, no debería instanciarse nunca.
     */
    private LDTUtils() {
        throw new AssertionError();
    }

    /**
     *
     * @param start Fecha de inicio
     * @param end Fecha de termino
     * @return Listado de fechas con ambas incluidas
     */
    public static List<LocalDate> getLocalDatesBetween(final LocalDate start, final LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        if (start != null && end != null && start.isBefore(end)) {
            Set<LocalDate> currents = new HashSet<>();
            LocalDate current = start;
            while (!Objects.equals(end, current)) {
                currents.add(current);
                current = current.plusDays(1);
            }

            dates = new ArrayList<>(currents);
            Collections.sort(dates);
        } else {
            LOGGER.error("Error al obtener listado: Fecha inicial '{}' Fecha Final '{}'", start, end);
        }
        return dates;
    }
}
