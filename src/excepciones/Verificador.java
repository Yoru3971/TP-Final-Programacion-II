package excepciones;

import modelos.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class Verificador {
    public static boolean verificarUsuario(String usuario) throws UsuarioInvalidoException{
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

        return true;
    }

    public static boolean verificarClave(String clave) throws ClaveInvalidaException{

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

        return true;
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
        if(coleccion.contains(elemento)){
            throw new ElementoExistenteException(elemento.getClass().getName() + " ya existente en el sistema.");
        }
        return true;
    }

    public static <T> boolean verificarNoExistente(T elemento, Collection<T> coleccion) throws ElementoNoExistenteException {
        if(!coleccion.contains(elemento)){
            throw new ElementoNoExistenteException(elemento.getClass().getName() + " no existente en el sistema.");
        }

        return true;
    }

    public static <T> boolean verificarArregloVacio(ArrayList<T> arreglo) throws ArregloVacioException {
        if(arreglo.isEmpty()){
            if (arreglo.getFirst() instanceof Habitacion){
                throw new ArregloVacioException("No hay "+arreglo.getFirst().getClass().getName().toLowerCase()+"es en el sistema.");
            }
            throw new ArregloVacioException("No hay "+arreglo.getFirst().getClass().getName().toLowerCase()+"s en el sistema.");
        }

        return true;
    }

    ///VERIFICACIONES NULL

    public static <T> boolean verificarObjetoNulo(T objeto) throws ObjetoNuloException{
        if (objeto.equals(null)){
            throw new ObjetoNuloException(objeto.getClass().getName() + "con datos invalidos");
        }
        return true;
    }

    public static boolean verificarHabitacionNula(Habitacion habitacion) throws HabitacionNulaException{
        if (habitacion.getNumeroHabitacion().equals(null) || habitacion.getEstadoActual().equals(null) || habitacion.getTipoHabitacion().equals(null) || habitacion.getPrecioDiario().equals(null)){
            throw new HabitacionNulaException("Habitacion invalida");
        }
        return true;
    }

    public static boolean verificarClienteNulo(Cliente cliente) throws ClienteNuloException{
        if (verificarPersonaNula(cliente) || cliente.getClienteVip().equals(null)){
            throw new ClienteNuloException("Cliente invalido");
        }
        return true;
    }

    public static boolean verificarPersonaNula(Persona persona){
        if (persona.getDni().equals(null) || persona.getNombre().equals(null) || persona.getApellido().equals(null)||
                persona.getNacionalidad().equals(null) || persona.getDomicilio().equals(null) || persona.getTelefono().equals(null)||
                persona.getMail().equals(null)){
            return true;
        }
        return false;
    }

    public static boolean verificarEmpleadoNulo(Empleado empleado) throws EmpleadoNuloException{
        if (verificarPersonaNula(empleado) || empleado.getUsuario().equals(null) || empleado.getClave().equals(null) || empleado.getSalario().equals(null) || empleado.getCargo().equals(null)){
            throw new EmpleadoNuloException("Empleado invalido");
        }
        return true;
    }

    public static boolean verificarReservaNula(Reserva reserva) throws ReservaNulaException, HabitacionNulaException, ClienteNuloException {
        if (Verificador.verificarClienteNulo(reserva.getCliente()) || Verificador.verificarHabitacionNula(reserva.getHabitacion())
                || reserva.getCodigo().equals(null) || reserva.getCheckIn().equals(null) ||
                reserva.getCheckOut().equals(null) || reserva.getMontoTotal().equals(null)){
                throw new ReservaNulaException("Reserva invalida");
        }
        return true;
    }

}