package excepciones;

public class HabitacionExistenteException extends RuntimeException {
    public HabitacionExistenteException(String message) {
        super(message);
    }
}
