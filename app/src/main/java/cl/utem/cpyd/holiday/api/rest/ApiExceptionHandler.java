package cl.utem.cpyd.holiday.api.rest;

import cl.utem.cpyd.holiday.api.vo.ErrorVO;
import cl.utem.cpyd.holiday.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author seba
 */
@RestControllerAdvice(basePackages = {"cl.utem.cpyd.holiday.api.rest"})
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorVO> handleException(ApiException ae) {
        LOGGER.error("Error manejado en el API: {}", ae.getMessage());
        LOGGER.trace("Error manejado en el API: {}", ae.getMessage(), ae);

        return new ResponseEntity<>(new ErrorVO(ae.getMessage()), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorVO> handleException(Exception ex) {
        LOGGER.error("Error no manejado en el API: {}", ex.getMessage());
        LOGGER.trace("Error no manejado en el API: {}", ex.getMessage(), ex);

        return new ResponseEntity<>(new ErrorVO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
