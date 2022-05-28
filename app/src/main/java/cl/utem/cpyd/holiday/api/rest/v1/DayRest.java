package cl.utem.cpyd.holiday.api.rest.v1;

import cl.utem.cpyd.holiday.api.vo.QueryRequestVO;
import cl.utem.cpyd.holiday.api.vo.QueryResponseVO;
import cl.utem.cpyd.holiday.exception.ApiException;
import cl.utem.cpyd.holiday.manager.DayManager;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author seba
 */
@RestController
@RequestMapping(value = "/v1/days", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
public class DayRest {

    @Autowired
    private transient DayManager dayManager;

    @PostMapping(value = "/count", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<QueryResponseVO> queryDay(@RequestBody QueryRequestVO request) {
        if (request == null) {
            throw new ApiException("Se necesita información para la consulta");
        }

        final LocalDate start = request.getStart();
        if (start == null) {
            throw new ApiException("Se necesita una fecha de inicio");
        }

        final LocalDate today = LocalDate.now();
        if (start.isBefore(today)) {
            throw new ApiException("Se necesita una fecha igual o posterior a hoy");
        }

        final LocalDate end = request.getEnd();
        if (end == null) {
            throw new ApiException("Se necesita una fecha de término");
        }

        if (end.isBefore(start)) {
            throw new ApiException("La fecha de término debe ser posterior a la fecha de inicio");
        }

        final Long count = dayManager.countWorkingDays(start, end);

        QueryResponseVO response = new QueryResponseVO();
        response.setCount(count);
        response.setStart(start);
        response.setEnd(end);
        return ResponseEntity.ok(response);
    }

}
