package excepciones;

import modelos.Empleado;

import java.util.ArrayList;
import java.util.Collection;

public class Verificador {
    public static boolean verificarUsuario(String usuario) throws UsuarioInvalidoException{
        boolean valido = true;

        // Verifico que el usuario ingresado no este vacio
        if(usuario == null || usuario.trim().isEmpty()){
            throw new UsuarioInvalidoException("El nombre del usuario no puede estar vacio.");
        }
        // verifico que el largo del nombre de usuario dea mayor a 4 caracteres
        if(usuario.length() <= 4){
            throw new UsuarioInvalidoException("El nombre de usuario debe tener al menos 4 caracteres");
        }
        // verifico que el nombre de usuario no tenga caracteres especiales con una expresion regular y el uso del metodo matches() de la clase String.
        if (!usuario.matches("^[a-zA-Z0-9_]+$")) {
            throw new UsuarioInvalidoException("El nombre de usuario solo puede contener letras, números y guiones bajos.");
        }

        return valido;
    }

    public static boolean verificarClave(String clave) throws ClaveInvalidaException{
        boolean valido = true;

        // verifico que la clave ingresada no este vacia
        if(clave == null || clave.trim().isEmpty()){
            throw new ClaveInvalidaException("La clave no puede estar vacia");
        }
        // verifico que el largo del nombre de usuario dea mayor a 12 caracteres
        if(clave.length() <= 12){
            throw new ClaveInvalidaException("La clave debe tener al menos 12 caracteres");
        }
        // verifica que la clave contenga una letra minuscula
        if (!clave.matches(".*[a-z].*")) {
            throw new ClaveInvalidaException("La clave debe contener al menos una letra minúscula.");
        }
        // verifica que la clave contenga una letra mayuscula
        if (!clave.matches(".*[0-9].*")) {
            throw new ClaveInvalidaException("La clave debe contener al menos un número.");
        }
        // verifica que la clave contenga un caracter especial
        if (!clave.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new ClaveInvalidaException("La clave debe contener al menos un carácter especial.");
        }

        return valido;
    }

    public static Empleado verificarCredenciales(ArrayList<Empleado> empleados, String usuario, String clave) throws CredencialesIncorrectasException {
        // itero en la lista de empleados para verificar si existe
        for (Empleado empleado : empleados) {
            if (empleado.getUsuario().equals(usuario) && empleado.getClave().equals(clave)) {
                return empleado;
            }
        }
        throw new CredencialesIncorrectasException("Usuario o clave incorrectos");
    }

    public static <T> boolean verificarExistente(T elemento, Collection<T> coleccion) throws ElementoExistenteException {
        boolean valido = true;

        if(coleccion.contains(elemento)){
            throw new ElementoExistenteException(elemento.getClass().getName() + " ya existente en el sistema.");
        }

        return valido;
    }

    public static <T> boolean verificarNoExistente(T elemento, Collection<T> coleccion) throws ElementoNoExistenteException {
        boolean valido = true;

        if(!coleccion.contains(elemento)){
            throw new ElementoNoExistenteException(elemento.getClass().getName() + " no existente en el sistema.");
        }

        return valido;
    }

    public static <T> boolean verificarArregloVacio(ArrayList<T> arreglo) throws ArregloVacioException {
        boolean valido = true;

        if(arreglo.isEmpty()){
            throw new ArregloVacioException("No hay "+arreglo.getFirst().getClass().getName()+"s en el sistema.");
        }

        return valido;
    }
}