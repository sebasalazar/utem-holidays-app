package cl.utem.cpyd.holiday.api.rest;

import cl.utem.cpyd.holiday.api.vo.ErrorVO;
import cl.utem.cpyd.holiday.exception.BadDateException;
import cl.utem.cpyd.holiday.exception.BadRutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"cl.utem.cpyd.holiday.api.rest"})
public class ApiErrorHandler {

    @ExceptionHandler({BadRutException.class})
    public ResponseEntity<ErrorVO> handleException(BadRutException ex) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorVO error = new ErrorVO("El rut es inválido");
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({BadDateException.class})
    public ResponseEntity<ErrorVO> handleException(BadDateException ex) {
        final HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        ErrorVO error = new ErrorVO(ex.getMessage());
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorVO> handleException(Exception ex) {
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorVO error = new ErrorVO("Error no controlado");
        return new ResponseEntity<>(error, status);
    }

}
