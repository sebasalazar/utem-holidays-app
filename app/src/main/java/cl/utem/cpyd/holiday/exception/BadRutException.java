package cl.utem.cpyd.holiday.exception;

public class BadRutException extends RuntimeException {

    public BadRutException() {
        super("El rut es inválido");
    }
}
