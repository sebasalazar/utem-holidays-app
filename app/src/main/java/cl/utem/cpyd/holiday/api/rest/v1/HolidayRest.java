package cl.utem.cpyd.holiday.api.rest.v1;

import cl.utem.cpyd.holiday.api.vo.QueryDaysVO;
import cl.utem.cpyd.holiday.api.vo.ResponseDaysVO;
import cl.utem.cpyd.holiday.exception.BadDateException;
import cl.utem.cpyd.holiday.exception.BadRutException;
import cl.utem.cpyd.holiday.manager.HolidayManager;
import cl.utem.cpyd.holiday.utils.RutUtils;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/holidays", consumes = {"application/json; charset=utf-8"}, produces = {"application/json; charset=utf-8"})
public class HolidayRest {

    @Autowired
    private transient HolidayManager holidayManager;

    @PostMapping(value = "/{rut}/count", consumes = {"application/json; charset=utf-8"}, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<ResponseDaysVO> countDays(@PathVariable("rut") String rut, @RequestBody QueryDaysVO request) {

        if (!RutUtils.isRut(rut)) {
            throw new BadRutException();
        }

        final LocalDate start = request.getStart();
        if (start == null) {
            throw new BadDateException("La fecha de inicio no puede ser nula");
        }

        final LocalDate end = request.getEnd();
        if (end == null) {
            throw new BadDateException("La fecha de termino no puede ser nula");
        }

        if (start.isAfter(end)) {
            throw new BadDateException("La fecha de inicio no puede ser posterior a la fecha de termino");
        }

        Integer countHolidays = holidayManager.countHolidays(start, end);
        if (countHolidays == null || countHolidays <= 0) {
            throw new BadDateException("La cantidad de días es inconsistente");
        }

        ResponseDaysVO response = new ResponseDaysVO();
        response.setDays(countHolidays);
        response.setStart(start);
        response.setEnd(end);

        return ResponseEntity.ok(response);
    }

}
