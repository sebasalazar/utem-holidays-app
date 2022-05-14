package cl.utem.cpyd.holiday.api.rest.v1;

import cl.utem.cpyd.holiday.api.vo.QueryDaysVO;
import cl.utem.cpyd.holiday.api.vo.ResponseDaysVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/holidays", consumes = {"application/json; charset=utf-8"}, produces = {"application/json; charset=utf-8"})
public class HolidayRest {

    @PostMapping(value = "/{rut}/count", consumes = {"application/json; charset=utf-8"}, produces = {"application/json; charset=utf-8"})
    public ResponseEntity<ResponseDaysVO> countDays(@PathVariable("rut") String rut, @RequestBody QueryDaysVO request) {

        return ResponseEntity.ok(new ResponseDaysVO());
    }

}
