package excepciones;

public class ElementoNoExistenteException extends RuntimeException {
    public ElementoNoExistenteException(String message) {
        super(message);
    }
}
