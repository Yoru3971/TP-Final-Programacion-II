package excepciones;

public class NombreInvalidoException extends RuntimeException {
    public NombreInvalidoException(String message) {
        super(message);
    }
}
