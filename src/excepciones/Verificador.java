package excepciones;

import modelos.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class Verificador {
    public static boolean verificarUsuario(String usuario) throws UsuarioInvalidoException{
        // Verifico que el usuario ingresado no este vacio
        if(usuario == null || usuario.trim().isEmpty()){
            throw new UsuarioInvalidoException("El nombre del usuario no puede estar vacio.");
        }
        // verifico que el largo del nombre de usuario dea mayor a 4 caracteres
        if(usuario.length() < 6){
            throw new UsuarioInvalidoException("El nombre de usuario debe tener al menos 6 caracteres");
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
        if(clave.length() < 10){
            throw new ClaveInvalidaException("La clave debe tener al menos 10 caracteres");
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
        if (!clave.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
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

    public static boolean verificarDisponibilidadHabitacion(Habitacion habitacion, LocalDate checkIn, LocalDate checkOut) throws HabitacionNoDisponibleException{
        LocalDate fecha = checkIn;
        while (!fecha.isAfter(checkOut)) {
            if (!habitacion.isDisponible(fecha)) {
                throw new HabitacionNoDisponibleException("La habitacion no esta disponible en las fechas que solicito. ");
            }
            fecha = fecha.plusDays(1);
        }
        return true;
    }

    //Metodos para verificar datos
    public static boolean verificarDNI(String dni) throws DNIInvalidoException {
        // Verificar que no sea null o esté vacío
        if (dni == null || dni.trim().isEmpty()) {
            throw new DNIInvalidoException("El DNI no puede estar vacío.");
        }
        // Verificar que contenga solo dígitos
        if (!dni.matches("\\d+")) { // Expresión regular para validar solo números
            throw new DNIInvalidoException("El DNI solo debe contener números.");
        }
        // Verificar longitud mínima
        if (dni.length() < 8 || dni.length() >11) {
            throw new DNIInvalidoException("El DNI debe tener al menos 8 y 10 dígitos.");
        }
        return true;
    }

    public static boolean verificarDNIUnico(String dni, ArrayList<? extends Persona> personas) throws DNIExistenteException {
        for (Persona persona : personas) {
            if (persona.getDni().equals(dni)) {
                throw new DNIExistenteException("El DNI ingresado ya está registrado en el sistema.");
            }
        }
        return true;
    }

    public static boolean verificarNombre(String nombre) throws NombreInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException("El nombre no puede estar vacío.");
        }
        if (!nombre.matches("[a-zA-Z]+")) {
            throw new NombreInvalidoException("Nombre invalido, solo debe contener letras (minusculas y mayusculas, sin espacios)");
        }
        return true;
    }

    public static boolean verificarApellido(String apellido) throws ApellidoInvalidoException {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ApellidoInvalidoException("El apellido no puede estar vacío.");
        }
        if (!apellido.matches("[a-zA-Z]+")) {
            throw new ApellidoInvalidoException("Apellido invalido, solo debe contener letras (minusculas y mayusculas, sin espacios)");
        }
        return true;
    }

    public static boolean verificarNacionalidad(String nacionalidad) throws NacionalidadInvalidaException {
        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new NacionalidadInvalidaException("La nacionalidad no puede estar vacía.");
        }
        if (!nacionalidad.matches("[a-zA-Z]+")) {
            throw new NacionalidadInvalidaException("Nacionalidad invalida, solo debe contener letras (minusculas y mayusculas, sin espacios)");
        }
        return true;
    }

    public static boolean verificarDomicilio(String domicilio) throws DomicilioInvalidoException {
        if (domicilio == null || domicilio.trim().isEmpty()) {
            throw new DomicilioInvalidoException("El domicilio no puede estar vacío.");
        }
        if (!domicilio.matches("[a-zA-Z0-9 ]+")) {
            throw new DomicilioInvalidoException("Domicilio invalido, solo puede contener letras, numeros y espacios");
        }
        return true;
    }

    public static boolean verificarTelefono(String telefono) throws TelefonoInvalidoException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new TelefonoInvalidoException("El teléfono no puede estar vacío.");
        }
        if (!telefono.matches("\\d{10,15}")) { // 10 a 15 dígitos
            throw new TelefonoInvalidoException("Teléfono invalido, debe contener solo números y el largo debe ser adecuado");
        }
        return true;
    }

    public static boolean verificarMail(String mail) throws MailInvalidoException {
        if (mail == null || mail.trim().isEmpty()) {
            throw new MailInvalidoException("El correo electrónico no puede estar vacío.");
        }
        if (!mail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")) {
            throw new MailInvalidoException("Email invalido, debe tener el formato correcto (ejemplo: nombre@dominio.com)");
        }
        return true;
    }

    public static boolean verificarSalario(Double salario) throws SalarioInvalidoException {
        if (salario == null || salario <= 0) {
            throw new SalarioInvalidoException("El salario debe ser un número positivo.");
        }
        return true;
    }

    public static boolean verificarNumeroHabitacion(Integer numero, ArrayList<Habitacion> habitaciones) throws NumeroHabitacionInvalidoException, HabitacionExistenteException {
        if (numero == null || numero <= 0) {
            throw new NumeroHabitacionInvalidoException("El número de habitación debe ser un entero positivo mayor a 0.");
        }

        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumeroHabitacion().equals(numero)) {
                throw new HabitacionExistenteException("El número de habitación ya existe en el sistema.");
            }
        }
        return true;
    }

    public static boolean verificarPrecioHabitacion(Double precio) throws PrecioInvalidoException {
        if (precio == null || precio <= 0) {
            throw new PrecioInvalidoException("El precio diario debe ser un valor positivo.");
        }
        return true;
    }

    public static boolean verificarUsuarioRepetido(String usuario, ArrayList<Empleado> empleados) throws UsuarioRepetidoException{
        for(Empleado e: empleados){
            if(e.getUsuario().equals(usuario)){
                throw new UsuarioRepetidoException("Usuario ya existente en el sistema.");
            }
        }
        return true;
    }
}