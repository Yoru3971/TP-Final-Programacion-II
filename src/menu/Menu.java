package menu;

import enumeraciones.TipoEmpleado;
import excepciones.*;
import gestores.*;
import modelos.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Menu {
    //Aca se guarda la informacion de quien se logea, se usa para verificar si es Admin o Recepcionista
    private Empleado empleadoLogueado;

    //Aca se levanta y guarda toda la informacion que se maneja
    private GestorEmpleados gestorEmpleados;
    private GestorClientes gestorClientes;
    private GestorHabitaciones gestorHabitaciones;
    private GestorReservas gestorReservas;

    //Constantes para implementar color en los menu
    private final String colorRojo = "\u001B[91m";
    private final String colorVerde = "\u001B[92m";
    private final String colorAzul = "\u001B[94m";
    private final String resetColor = "\u001B[0m";

    // Constructor
    public Menu() {
        this.empleadoLogueado = null;
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorClientes = new GestorClientes();
        this.gestorHabitaciones = new GestorHabitaciones();
        this.gestorReservas = new GestorReservas();
    }

    //Getters y setters
    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }
    public void setEmpleadoLogueado(Empleado empleadoLogueado) {
        this.empleadoLogueado = empleadoLogueado;
    }

    //Metodos de ejecucion del programa
    public void logIn() {
        GestorEntradas.limpiarConsola();
        try {
            ArrayList<Empleado> empleados = GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class);
            String usuario = null;
            char[] claveArray = null;

            int intentos = 0;

            while (empleadoLogueado == null && intentos < 3) {
                // Solicitar usuario
                usuario = JOptionPane.showInputDialog(null, "Ingrese su usuario:", "Login", JOptionPane.PLAIN_MESSAGE);

                // Verificar si se canceló el diálogo de usuario
                if (usuario == null) {
                    System.out.println("Operación cancelada.");
                    break;
                }

                // Crear el campo de contraseña
                JPasswordField passwordField = new JPasswordField();
                int option = JOptionPane.showConfirmDialog(null, passwordField, "Ingrese su contraseña:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (option == JOptionPane.OK_OPTION) {
                    // Obtener la contraseña ingresada
                    claveArray = passwordField.getPassword();
                    String clave = new String(claveArray);

                    // Intentar autenticar
                    try {
                        empleadoLogueado = Verificador.verificarCredenciales(empleados, usuario, clave);
                    } catch (CredencialesIncorrectasException e) {
                        System.err.println(e.getMessage());
                        intentos++;
                        if (intentos < 3) {
                            JOptionPane.showMessageDialog(null, "Intentos restantes: " + (3 - intentos), "Error de autenticación", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    // Limpiar el array de contraseñas
                    Arrays.fill(claveArray, '\u0000');
                } else {
                    System.out.println("Operación cancelada.");
                    break;
                }
            }

            if (empleadoLogueado == null && intentos >= 3) {
                JOptionPane.showMessageDialog(null, "Número máximo de intentos alcanzado.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (empleadoLogueado != null) {
                JOptionPane.showMessageDialog(null, "¡Bienvenido, " + empleadoLogueado.getNombre() + "!", "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (RuntimeException e) {
            System.err.println("No se pudo abrir el archivo.");
        }
    }
    public void ejecutarPrograma() {
        try{
            logIn();
        }catch(NullPointerException e){
            System.err.println("Hubo un error en el LogIn.");
        }

        try{
            if (empleadoLogueado.getCargo() == TipoEmpleado.ADMINISTRADOR) {
                menuAdministrador();

            } else if (empleadoLogueado.getCargo() == TipoEmpleado.RECEPCIONISTA) {
                String usuarioLogueado = empleadoLogueado.getUsuario();
                String claveLoguado = empleadoLogueado.getClave();
                String nombreLogueado = empleadoLogueado.getNombre();
                String apellidoLogeado = empleadoLogueado.getApellido();
                String dniLogueado = empleadoLogueado.getDni();

                if (usuarioLogueado.equals(nombreLogueado.concat(apellidoLogeado)) && claveLoguado.equals(dniLogueado)){
                    System.out.println("Para el primer ingreso, por favor modifique su clave y usuario.");
                    boolean valido = false;

                        do {
                            try {
                                String clave = GestorEntradas.pedirCadena("Ingrese nueva clave: ");
                                if(Verificador.verificarClave(clave)){
                                //No es necesario verificar si el la clave es igual al DNI ya que el verificarClave te pide 12 caracteres por lo que ya lo filtra por logica.
                                    empleadoLogueado.setClave(clave);
                                    valido = true;
                                }
                            } catch (ClaveInvalidaException e) {
                                System.out.println(colorRojo+e.getMessage());
                            }
                        }while(!valido);
                        System.out.println("Clave modificada con éxito");
                        valido = false; // Volvemos a asumir q es falso hasta que sea valido.

                        do {
                            try {
                                String usuario = GestorEntradas.pedirCadena("Ingrese nuevo usuario: ");
                                if(Verificador.verificarUsuario(usuario) && Verificador.verificarUsuarioRepetido(usuario, empleadoLogueado)){
                                    empleadoLogueado.setUsuario(usuario);
                                    valido = true;
                                }
                            } catch (UsuarioInvalidoException e){
                                System.out.println(colorRojo+e.getMessage());
                            }catch (UsuarioRepetidoException e){
                                System.out.println(colorRojo+e.getMessage());
                            }
                        } while (!valido);
                        System.out.println("Usuario modificado con éxito");
                }

                menuRecepcionista();
            }
        }catch(RuntimeException re){
            System.out.println(colorRojo+"Hubo un error en la ejecución del programa.");
        }
    }

    //Menus segun tipo de empleado
    public void menuAdministrador() {
        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n"+colorAzul+"=== Menú del Administrador ==="+resetColor);
            System.out.println("  1. Gestionar Empleados");
            System.out.println("  2. Gestionar Habitaciones");
            System.out.println("  3. Gestionar Clientes");
            System.out.println("  4. Gestionar Reservas");
            System.out.println("  5. Realizar Backup");
            System.out.println("  0. Salir");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> gestionarEmpleados();
                case "2" -> gestionarHabitacionesAdmin();
                case "3" -> gestionarClientes();
                case "4" -> gestionarReservas();
                case "5" -> realizarBackup();
                case "0" -> System.out.println("\n  Saliendo...");
                default -> {
                    System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
                    GestorEntradas.pausarConsola();
                }
            }
        } while (!opcion.equals("0"));
    }
    public void menuRecepcionista() {
        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n" + colorAzul + "=== Menú del Recepcionista ===" + resetColor);
            System.out.println("  1. Gestionar Habitaciones");
            System.out.println("  2. Gestionar Clientes");
            System.out.println("  3. Gestionar Reservas");
            System.out.println("  0. Salir");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> gestionarHabitacionesRecep();
                case "2" -> gestionarClientes();
                case "3" -> gestionarReservas();
                case "0" -> System.out.println("\n  Saliendo...");
                default -> {
                    System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
                    GestorEntradas.pausarConsola();
                }
            }
        } while (!opcion.equals("0"));
    }

    //Metodos de gestion
    private void gestionarEmpleados() {
        gestorEmpleados.setEmpleados(GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class));
        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n" + colorAzul + "=== Gestión de Empleados ===" + resetColor);
            System.out.println("  1. Listar Empleados");
            System.out.println("  2. Agregar Empleado");
            System.out.println("  3. Modificar Empleado");
            System.out.println("  4. Eliminar Empleado");
            System.out.println("  5. Buscar Empleado por DNI");
            System.out.println("  0. Volver al menú anterior");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> gestorEmpleados.listar();
                case "2" -> gestorEmpleados.agregar();
                case "3" -> gestorEmpleados.modificar(GestorEntradas.pedirCadena("\n  Ingrese el DNI del empleado a modificar: "));
                case "4" -> gestorEmpleados.eliminar(GestorEntradas.pedirCadena("\n  Ingrese el DNI del empleado a eliminar: "));
                case "5" -> buscarEmpleadoPorDNI();
                case "0" -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorEmpleados.getEmpleados(), "empleados.json", false);
                    System.out.println("\n  Volviendo al menú anterior...");
                }
                default -> System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
            }
            GestorEntradas.pausarConsola();
        } while (!opcion.equals("0"));
    }
    private void gestionarHabitacionesAdmin() {
        gestorHabitaciones.setHabitaciones(GestorArchivos.leerArregloDeArchivo("habitaciones.json", Habitacion.class));
        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n" + colorAzul + "=== Gestión de Habitaciones ===" + resetColor);
            System.out.println("  1. Listar Habitaciones");
            System.out.println("  2. Agregar Habitación");
            System.out.println("  3. Modificar Habitación");
            System.out.println("  4. Eliminar Habitación");
            System.out.println("  5. Buscar Habitación por Número");
            System.out.println("  6. Ver Disponibilidad de una Habitación");
            System.out.println("  0. Volver al menú anterior");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> {
                    GestorEntradas.limpiarConsola();
                    System.out.println("\n  " + colorAzul + "=== Listar ===" + resetColor);
                    System.out.println("    1. Listar todas las habitaciones");
                    System.out.println("    2. Listar habitaciones disponibles");
                    System.out.println("    3. Listar habitaciones no disponibles");
                    System.out.println("    4. Listar habitaciones por tipo");
                    System.out.println("    5. Listar habitaciones entre precios");
                    System.out.println("    0. Volver al menu anterior");
                    opcion = GestorEntradas.pedirCadena("\n    Seleccione una opción: ");
                    switch (opcion) {
                        case "1" -> gestorHabitaciones.listar();
                        case "2" -> gestorHabitaciones.listarHabitacionesDisponibles();
                        case "3" -> gestorHabitaciones.listarHabitacionesNoDisponibles();
                        case "4" -> gestorHabitaciones.listarHabitacionesPorTipo();
                        case "5" -> gestorHabitaciones.listarHabitacionesPrecio(GestorEntradas.pedirEntero("Ingrese el precio minimo: "), GestorEntradas.pedirEntero("Ingrese el precio maximo: "));
                        case "0" -> System.out.println("\n    Volviendo al menú anterior...");
                        default -> System.out.println(colorRojo + "\n    Opción inválida. Intente nuevamente." + resetColor);
                    }
                }
                case "2" -> gestorHabitaciones.agregar();
                case "3" -> gestorHabitaciones.modificar(GestorEntradas.pedirEntero("\n  Ingrese el número de la habitación a modificar: "));
                case "4" -> gestorHabitaciones.eliminar(GestorEntradas.pedirEntero("\n  Ingrese el número de la habitación a eliminar: "));
                case "5" -> buscarHabitacionPorNumero();
                case "6" -> gestorHabitaciones.verDisponibilidad(GestorEntradas.pedirEntero("\n  Ingrese el número de la habitación de la cual desea ver el calendario: "));
                case "0" -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorHabitaciones.getHabitaciones(), "habitaciones.json", true);
                    System.out.println("\n  Volviendo al menú anterior...");
                }
                default -> System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
            }
            GestorEntradas.pausarConsola();
        } while (!opcion.equals("0"));
    }
    private void gestionarHabitacionesRecep() {
        gestorHabitaciones.setHabitaciones(GestorArchivos.leerArregloDeArchivo("habitaciones.json", Habitacion.class));
        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n" + colorAzul + "=== Gestión de Habitaciones ===" + resetColor);
            System.out.println("  1. Listar Habitaciones");
            System.out.println("  2. Modificar estado de una habitación");
            System.out.println("  3. Buscar Habitación por Número");
            System.out.println("  4. Ver Disponibilidad de una Habitación");
            System.out.println("  0. Volver al menú anterior");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> {
                    GestorEntradas.limpiarConsola();
                    System.out.println("\n  " + colorAzul + "=== Listar ===" + resetColor);
                    System.out.println("    1. Listar todas las habitaciones");
                    System.out.println("    2. Listar habitaciones disponibles");
                    System.out.println("    3. Listar habitaciones no disponibles");
                    System.out.println("    4. Listar habitaciones por tipo");
                    System.out.println("    5. Listar habitaciones entre precios");
                    System.out.println("    0. Volver al menu anterior");
                    opcion = GestorEntradas.pedirCadena("\n    Seleccione una opción: ");
                    switch (opcion) {
                        case "1" -> gestorHabitaciones.listar();
                        case "2" -> gestorHabitaciones.listarHabitacionesDisponibles();
                        case "3" -> gestorHabitaciones.listarHabitacionesNoDisponibles();
                        case "4" -> gestorHabitaciones.listarHabitacionesPorTipo();
                        case "5" -> gestorHabitaciones.listarHabitacionesPrecio(GestorEntradas.pedirEntero("Ingrese el precio minimo: "), GestorEntradas.pedirEntero("Ingrese el precio maximo: "));
                        case "0" -> System.out.println("\n    Volviendo al menú anterior...");
                        default -> System.out.println(colorRojo + "\n    Opción inválida. Intente nuevamente." + resetColor);
                    }
                }
                case "2" -> gestorHabitaciones.modificarEstado(GestorEntradas.pedirEntero("\n  Ingrese el número de la habitación a modificar: "));
                case "3" -> buscarHabitacionPorNumero();
                case "4" -> gestorHabitaciones.verDisponibilidad(GestorEntradas.pedirEntero("\n  Ingrese el número de la habitación de la cual desea ver el calendario: "));
                case "0" -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorHabitaciones.getHabitaciones(), "habitaciones.json", true);
                    System.out.println("\n  Volviendo al menú anterior...");
                }
                default -> System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
            }
            GestorEntradas.pausarConsola();
        } while (!opcion.equals("0"));
    }
    private void gestionarClientes() {
        gestorClientes.setClientes(GestorArchivos.leerArregloDeArchivo("clientes.json", Cliente.class));
        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n" + colorAzul + "=== Gestión de Clientes ===" + resetColor);
            System.out.println("  1. Listar Clientes");
            System.out.println("  2. Agregar Cliente");
            System.out.println("  3. Modificar Cliente");
            System.out.println("  4. Eliminar Cliente");
            System.out.println("  5. Buscar Cliente por DNI");
            System.out.println("  0. Volver al menú anterior");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> gestorClientes.listar();
                case "2" -> gestorClientes.agregar();
                case "3" ->
                        gestorClientes.modificar(GestorEntradas.pedirCadena("\n  Ingrese el DNI del cliente a modificar: "));
                case "4" ->
                        gestorClientes.eliminar(GestorEntradas.pedirCadena("\n  Ingrese el DNI del cliente a eliminar: "));
                case "5" -> buscarClientePorDNI();
                case "0" -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorClientes.getClientes(), "clientes.json", false);
                    System.out.println("\n  Volviendo al menú anterior...");
                }
                default -> System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
            }
            GestorEntradas.pausarConsola();
        } while (!opcion.equals("0"));
    }
    private void gestionarReservas() {
        gestorReservas.setReservas(GestorArchivos.leerArregloDeArchivo("reservas.json", Reserva.class));
        gestorClientes.setClientes(GestorArchivos.leerArregloDeArchivo("clientes.json", Cliente.class));
        gestorHabitaciones.setHabitaciones(GestorArchivos.leerArregloDeArchivo("habitaciones.json", Habitacion.class));

        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n" + colorAzul + "=== Gestión de Reservas ===" + resetColor);
            System.out.println("  1. Listar Reservas");
            System.out.println("  2. Agregar Reserva");
            System.out.println("  3. Modificar Reserva");
            System.out.println("  4. Eliminar Reserva");
            System.out.println("  5. Mostrar Reservas de Cliente");
            System.out.println("  0. Volver al menú anterior");
            opcion = GestorEntradas.pedirCadena("\n  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> gestorReservas.listar();
                case "2" -> {
                    System.out.println("\n  Ingrese los datos de la nueva reserva: ");

                    Cliente cliente = gestorClientes.pedirCliente();
                    Habitacion habitacion = gestorHabitaciones.pedirHabitacion();

                    System.out.println("\n  Datos del cliente");
                    System.out.println(cliente);
                    System.out.println("\n  Datos de la habitación");
                    System.out.println(habitacion);

                    boolean fechasDisponibles = false;
                    LocalDate checkIn = null;
                    LocalDate checkOut = null;

                    while (!fechasDisponibles) {
                        checkIn = gestorReservas.pedirFechaReserva("\n  Ingrese la fecha de check-in (formato: yyyy-MM-dd): ");

                        boolean fechasCorrectamenteOrdenadas = false;
                        do {
                            checkOut = gestorReservas.pedirFechaReserva("\n  Ingrese la fecha de check-out (formato: yyyy-MM-dd): ");
                            if (checkOut.isBefore(checkIn)) {
                                System.out.println(colorRojo + "\n  La fecha de check-out no puede ser previa a la fecha de check-in. Intente de nuevo." + resetColor);
                            } else {
                                fechasCorrectamenteOrdenadas = true;
                            }
                        } while (!fechasCorrectamenteOrdenadas);

                        try {
                            if (Verificador.verificarDisponibilidadHabitacion(habitacion, checkIn, checkOut)) {
                                fechasDisponibles = true;
                            }
                        } catch (HabitacionNoDisponibleException e) {
                            System.out.println(colorRojo + "\n  " + e.getMessage() + resetColor);
                            System.out.println("\n  Por favor, elija un rango de fechas diferente.");
                        }
                    }
                    Reserva nuevaReserva = new Reserva(habitacion, cliente, checkIn, checkOut);

                    System.out.println("\n  Cliente: " + cliente.getNombre());
                    System.out.println("  Habitación: " + habitacion.getNumeroHabitacion());
                    System.out.println("  Check-in: " + checkIn);
                    System.out.println("  Check-out: " + checkOut);

                    System.out.println(colorVerde + "\n  Reserva agregada con éxito." + resetColor);
                    gestorReservas.agregar(nuevaReserva);
                    nuevaReserva.reservarFechas();
                    GestorEntradas.pausarConsola();
                }
                case "3" -> {
                    gestorReservas.modificar(gestorHabitaciones.getHabitaciones(), gestorClientes.getClientes());
                }
                case "4" -> {
                    Habitacion habitacionEliminar = gestorReservas.eliminar(GestorEntradas.pedirEntero("\n  Ingrese el ID de la reserva a eliminar: "));

                    if (habitacionEliminar == null) {
                        System.out.println(colorRojo + "\n  Reserva no encontrada." + resetColor);
                        return;
                    }

                    Habitacion habitacionReemplazar = gestorHabitaciones.buscarHabitacionPorNumero(habitacionEliminar.getNumeroHabitacion());
                    Integer indiceHabitacion = gestorHabitaciones.getHabitaciones().indexOf(habitacionReemplazar);
                    gestorHabitaciones.getHabitaciones().set(indiceHabitacion, habitacionEliminar);
                }
                case "5" -> {
                    gestorReservas.mostrarHistorialCliente(GestorEntradas.pedirCadena("\n  Ingrese el DNI del cliente: "));
                }
                case "0" -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorReservas.getReservas(), "reservas.json", false);
                    GestorArchivos.escribirArregloEnArchivo(gestorHabitaciones.getHabitaciones(), "habitaciones.json", false);
                    System.out.println("\n  Volviendo al menú anterior...");
                }
                default -> System.out.println(colorRojo + "\n  Opción inválida. Intente nuevamente." + resetColor);
            }
            GestorEntradas.pausarConsola();
        } while (!opcion.equals("0"));
    }

    //Metodos de busqueda
    private void buscarEmpleadoPorDNI() {
        String dni = GestorEntradas.pedirCadena("Ingrese el DNI del empleado a buscar: ");
        Empleado empleado = gestorEmpleados.buscarEmpleadoPorDni(dni);
        if (empleado != null) {
            System.out.println("Empleado encontrado: " + empleado);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }
    private void buscarClientePorDNI() {
        String dni = GestorEntradas.pedirCadena("Ingrese el DNI del cliente a buscar: ");
        Cliente cliente = gestorClientes.buscarClientePorDni(dni);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
    private void buscarHabitacionPorNumero() {
        int numero = GestorEntradas.pedirEntero("Ingrese el número de la habitación a buscar: ");
        Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(numero);
        if (habitacion != null) {
            System.out.println("Habitación encontrada: " + habitacion);
        } else {
            System.out.println("Habitación no encontrada.");
        }
    }

    //Metodo para hacer backup de todos los archivos (solo accesible por el administrador)
    private void realizarBackup() {
        System.out.println("  Realizando backup...");

        //Levanto las listas de los archivos actuales (asumiendo que no estan corruptos)
        ArrayList<Empleado> empleados = GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class);
        ArrayList<Cliente> clientes = GestorArchivos.leerArregloDeArchivo("clientes.json", Cliente.class);
        ArrayList<Habitacion> habitaciones = GestorArchivos.leerArregloDeArchivo("habitaciones.json", Habitacion.class);
        ArrayList<Reserva> reservas = GestorArchivos.leerArregloDeArchivo("reservas.json", Reserva.class);

        //Creo un HashMap para los datos de backup y agrega cada lista al hashmap si no esta vacia
        HashMap<String, ArrayList<?>> datosBackup = new HashMap<>();

        if (empleados != null && !empleados.isEmpty()) {
            datosBackup.put("Empleados", empleados);
        }
        if (clientes != null && !clientes.isEmpty()) {
            datosBackup.put("Clientes", clientes);
        }
        if (habitaciones != null && !habitaciones.isEmpty()) {
            datosBackup.put("Habitaciones", habitaciones);
        }
        if (reservas != null && !reservas.isEmpty()) {
            datosBackup.put("Reservas", reservas);
        }

        //Llama al metodo hacerBackup del GestorArchivos si hay datos que respaldar
        try {
            if (!datosBackup.isEmpty()) {
                GestorArchivos.hacerBackup(datosBackup);
                System.out.println(colorVerde+"\n  Backup realizado exitosamente.");
            } else {
                System.out.println("\n  No hay datos para realizar el backup.");
            }
        } catch (IOException e) {
            System.out.println(colorRojo+"\n  Error al realizar el backup: " + e.getMessage());
        }
    }
}