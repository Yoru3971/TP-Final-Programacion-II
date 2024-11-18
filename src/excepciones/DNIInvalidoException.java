package excepciones;

public class DNIInvalidoException extends RuntimeException {
    public DNIInvalidoException(String message) {
        super(message);
    }
}
