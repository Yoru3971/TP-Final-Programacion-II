package excepciones;

public class ClienteExistenteException extends RuntimeException {
  public ClienteExistenteException(String message) {
    super(message);
  }
}
