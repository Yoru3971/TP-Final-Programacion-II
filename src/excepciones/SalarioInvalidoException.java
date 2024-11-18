package excepciones;

public class SalarioInvalidoException extends RuntimeException {
    public SalarioInvalidoException(String message) {
        super(message);
    }
}
