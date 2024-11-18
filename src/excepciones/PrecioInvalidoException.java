package excepciones;

public class PrecioInvalidoException extends RuntimeException {
    public PrecioInvalidoException(String message) {
        super(message);
    }
}
