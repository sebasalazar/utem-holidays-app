package cl.utem.cpyd.holiday.manager;

import cl.utem.cpyd.holiday.persistence.model.ProcessDay;
import cl.utem.cpyd.holiday.utils.LDTUtils;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.utem.cpyd.holiday.persistence.repository.ProcessDateRepository;

@Service("holidayManager")
public class HolidayManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient ProcessDateRepository holidayRepository;

    @Autowired
    private transient RestManager restManager;

    public Integer countHolidays(LocalDate start, LocalDate end) {
        Integer count = 0;

        List<LocalDate> list = LDTUtils.getLocalDatesBetween(start, end);
        if (list != null && !list.isEmpty()) {
            Integer total = list.size();
            for (LocalDate date : list) {
                ProcessDay holiday = holidayRepository.findByToday(date);
                if (holiday == null) {
                    if (DayOfWeek.SATURDAY.equals(date.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(date.getDayOfWeek())) {
                        persist(date);
                    } else {
                        if (restManager.isHoliday(date)) {
                            persist(date);
                        }
                    }
                }
            }

            Integer holidays = holidayRepository.countByTodayBetween(start, end);
            count = total - holidays;
        }

        return count;
    }

    @Transactional
    public ProcessDay persist(LocalDate date) {
        ProcessDay saved = null;
        if (date != null) {
            saved = holidayRepository.findByToday(date);
            if (saved == null) {
                ProcessDay newHoliday = new ProcessDay();
                newHoliday.setToday(date);
                saved = holidayRepository.saveAndFlush(newHoliday);
            }
        }
        return saved;
    }
}
