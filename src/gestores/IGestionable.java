package gestores;

public interface IGestionable <T>{
    void agregar();
    void eliminar(T elemento);
    void modificar(T elemento);
}
