package cl.utem.cpyd.holiday.exception;

public class BadDateException extends RuntimeException {

    public BadDateException() {
        super("La fecha es inválida");
    }

    public BadDateException(String message) {
        super(message);
    }
}
