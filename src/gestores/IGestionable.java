package gestores;

public interface IGestionable <T>{
    void agregar();
    void eliminar(T elemento);
    void listar();
    void modificar(T elemento);
}