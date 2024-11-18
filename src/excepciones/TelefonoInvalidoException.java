package excepciones;

public class TelefonoInvalidoException extends RuntimeException {
    public TelefonoInvalidoException(String message) {
        super(message);
    }
}
