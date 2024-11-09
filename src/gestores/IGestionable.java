package gestores;

public interface IGestionable <T>{
    void agregar(T elemento);
    void eliminar(T elemento);
    void modificar(T elemento);
}
