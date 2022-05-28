package cl.utem.cpyd.holiday.exception;

/**
 *
 * @author seba
 */
public class ApiException extends RuntimeException {

    public ApiException() {
        super("Error genérico");
    }

    public ApiException(String message) {
        super(message);
    }
}
