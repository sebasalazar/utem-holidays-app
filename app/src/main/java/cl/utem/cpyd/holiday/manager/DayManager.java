package cl.utem.cpyd.holiday.manager;

import cl.utem.cpyd.holiday.persistence.model.Day;
import cl.utem.cpyd.holiday.persistence.repository.DayRepository;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author seba
 */
@Service
public class DayManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient DayRepository dayRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DayManager.class);

    @Transactional
    public Day create(Day day) {
        Day created = null;
        if (day != null) {
            created = dayRepository.findByWorkingDate(day.getWorkingDate());
            if (created == null) {
                created = dayRepository.saveAndFlush(day);
            }
        }
        return created;
    }
}
