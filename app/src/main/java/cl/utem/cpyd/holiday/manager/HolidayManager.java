package cl.utem.cpyd.holiday.manager;

import cl.utem.cpyd.holiday.persistence.repository.HolidayRepository;
import cl.utem.cpyd.holiday.utils.LDTUtils;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("holidayManager")
public class HolidayManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient HolidayRepository holidayRepository;

    @Autowired
    private transient RestManager restManager;

    public Integer countHolidays(LocalDate start, LocalDate end) {
        Integer count = 0;

        List<LocalDate> list = LDTUtils.getLocalDatesBetween(start, end);
        if (list != null && !list.isEmpty()) {
            Integer total = list.size();
            Integer holidays = 0;
            for (LocalDate date : list) {
                if (DayOfWeek.SATURDAY.equals(date.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(date.getDayOfWeek())) {
                    holidays += 1;
                } else {
                    if (restManager.isHoliday(date)) {
                        holidays += 1;
                    }
                }
            }

            count = total - holidays;
        }

        return count;
    }
}
