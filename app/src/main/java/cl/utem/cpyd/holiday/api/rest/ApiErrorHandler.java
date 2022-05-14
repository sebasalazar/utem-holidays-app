package cl.utem.cpyd.holiday.api.rest;

import cl.utem.cpyd.holiday.api.vo.ErrorVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"cl.utem.cpyd.holiday.api.rest"})
public class ApiErrorHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorVO> handleException(Exception ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorVO error = new ErrorVO("Error no controlado");
        return new ResponseEntity<>(error, status);
    }

}
