package excepciones;

public class DNIExistenteException extends RuntimeException {
    public DNIExistenteException(String message) {
        super(message);
    }
}
