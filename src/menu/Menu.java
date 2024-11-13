package menu;

import enumeraciones.TipoEmpleado;
import excepciones.CredencialesIncorrectasException;
import gestores.*;
import modelos.*;
import excepciones.Verificador;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu {
    private Empleado empleadoLogueado;
    private GestorEmpleados gestorEmpleados;
    private GestorClientes gestorClientes;
    private GestorHabitaciones gestorHabitaciones;
    private GestorReservas gestorReservas;

    // Constructor
    public Menu() {
        this.empleadoLogueado = null;
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorClientes = new GestorClientes();
        this.gestorHabitaciones = new GestorHabitaciones();
        this.gestorReservas = new GestorReservas();
    }

    // Getters y setters
    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }
    public void setEmpleadoLogueado(Empleado empleadoLogueado) {
        this.empleadoLogueado = empleadoLogueado;
    }

    public void logIn() {
        // Levanto la lista de empleados
        ArrayList<Empleado> empleados = GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class);
        String usuario;
        char[] claveArray;
        int i = 0;

        // Obtengo el Console para leer la contraseña de manera oculta
        Console console = System.console();
        if (console == null) {
            System.out.println("No se puede obtener la consola.");
            return;
        }

        // Pido usuario y clave por teclado hasta que ingrese bien o supere los 3 intentos
        while (empleadoLogueado == null && i < 3) {
            System.out.println("Usuario:");
            usuario = GestorEntradas.pedirCadena("");  // Usando GestorEntradas

            System.out.println("Clave:");
            claveArray = console.readPassword();  // Leo la contraseña sin mostrarla

            // Convertir el array de char a String
            String clave = new String(claveArray);
            i++;

            try {
                empleadoLogueado = Verificador.verificarCredenciales(empleados, usuario, clave);
            } catch (CredencialesIncorrectasException e) {
                System.err.println(e.getMessage());
                if (i < 3) {
                    System.out.println("Intentos restantes: " + (3 - i));
                }
            }
        }

        if (empleadoLogueado == null) {
            System.err.println("Número máximo de intentos alcanzado");
        }
    }

    public void menuInicial() {
        logIn();
        if (empleadoLogueado.getCargo().equals(TipoEmpleado.ADMINISTRADOR)) {
            menuAdministrador();
        } else if (empleadoLogueado.getCargo().equals(TipoEmpleado.RECEPCIONISTA)) {
            menuRecepcionista();
        }
    }

    public void menuRecepcionista() {
        int opcion;
        do {
            System.out.println("\n=== Menú del Recepcionista ===");
            System.out.println("1. Gestionar Habitaciones");
            System.out.println("2. Gestionar Clientes");
            System.out.println("3. Gestionar Reservas");
            System.out.println("4. Salir");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestionarHabitaciones();
                case 2 -> gestionarClientes();
                case 3 -> gestionarReservas();
                case 4 -> System.out.println("Saliendo del menú del Recepcionista...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    public void menuAdministrador() {
        int opcion;
        do {
            System.out.println("\n=== Menú del Administrador ===");
            System.out.println("1. Gestionar Empleados");
            System.out.println("2. Gestionar Habitaciones");
            System.out.println("3. Gestionar Clientes");
            System.out.println("4. Gestionar Reservas");
            System.out.println("5. Realizar Backup");
            System.out.println("6. Salir");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestionarEmpleados();
                case 2 -> gestionarHabitaciones();
                case 3 -> gestionarClientes();
                case 4 -> gestionarReservas();
                case 5 -> realizarBackup();
                case 6 -> System.out.println("Saliendo del menú del Administrador...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    // Metodo para gestionar empleados (solo disponible para el Administrador)
    private void gestionarEmpleados() {
        int opcion;
        do {
            System.out.println("\n=== Gestión de Empleados ===");
            System.out.println("1. Listar Empleados");
            System.out.println("2. Agregar Empleado");
            System.out.println("3. Modificar Empleado");
            System.out.println("4. Eliminar Empleado");
            System.out.println("5. Buscar Empleado por DNI");
            System.out.println("6. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorEmpleados.listar();
                case 2 -> gestorEmpleados.agregar();
                case 3 -> gestorEmpleados.modificar(GestorEntradas.pedirCadena("Ingrese el dni del empleado a modificar: "));
                case 4 -> gestorEmpleados.eliminar(GestorEntradas.pedirCadena("Ingrese el dni del empleado a eliminar: "));
                case 5 -> buscarEmpleadoPorDNI();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // Metodo para buscar un empleado por DNI
    private void buscarEmpleadoPorDNI() {
        String dni = GestorEntradas.pedirCadena("Ingrese el DNI del empleado a buscar: ");
        Empleado empleado = gestorEmpleados.buscarEmpleadoPorDni(dni);
        if (empleado != null) {
            System.out.println("Empleado encontrado: " + empleado);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }

    // Metodo para gestionar habitaciones
    private void gestionarHabitaciones() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Habitaciones ---");
            System.out.println("1. Listar Habitaciones");
            System.out.println("2. Agregar Habitación");
            System.out.println("3. Modificar Habitación");
            System.out.println("4. Eliminar Habitación");
            System.out.println("5. Buscar Habitación por Número");
            System.out.println("6. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorHabitaciones.listar();
                case 2 -> gestorHabitaciones.agregar();
                case 3 -> gestorHabitaciones.modificar(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion a modificar: "));
                case 4 -> gestorHabitaciones.eliminar(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion a eliminar: "));
                case 5 -> buscarHabitacionPorNumero();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }


    // Metodo para buscar una habitación por número
    private void buscarHabitacionPorNumero() {
        int numero = GestorEntradas.pedirEntero("Ingrese el número de la habitación a buscar: ");
        Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(numero);
        if (habitacion != null) {
            System.out.println("Habitación encontrada: " + habitacion);
        } else {
            System.out.println("Habitación no encontrada.");
        }
    }

    // Metodo para gestionar clientes
    private void gestionarClientes() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Clientes ---");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Agregar Cliente");
            System.out.println("3. Modificar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Buscar Cliente por DNI");
            System.out.println("6. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorClientes.listar();
                case 2 -> gestorClientes.agregar();
                case 3 -> gestorClientes.modificar(GestorEntradas.pedirCadena("Ingrese el dni del cliente a modificar: "));
                case 4 -> gestorClientes.eliminar(GestorEntradas.pedirCadena("Ingrese el dni del cliente a eliminar: "));
                case 5 -> buscarClientePorDNI();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // Metodo para buscar un cliente por DNI
    private void buscarClientePorDNI() {
        String dni = GestorEntradas.pedirCadena("Ingrese el DNI del cliente a buscar: ");
        Cliente cliente = gestorClientes.buscarClientePorDni(dni);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // Metodo para gestionar reservas
    private void gestionarReservas() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Reservas ---");
            System.out.println("1. Listar Reservas");
            System.out.println("2. Agregar Reserva");
            System.out.println("3. Modificar Reserva");
            System.out.println("4. Eliminar Reserva");
            System.out.println("5. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorReservas.listar();
                case 2 -> {
                    System.out.println("Ingrese los datos de la nueva reserva: ");

                    Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion a reservar: "));
                    Cliente cliente = gestorClientes.buscarClientePorDni(GestorEntradas.pedirCadena("Ingrese el dni del titular de la reserva: "));

                    gestorReservas.agregar(habitacion, cliente);
                }
                case 3 -> gestorReservas.modificar(GestorEntradas.pedirCadena("Ingrese el codigo de la reserva a modificar: "));
                case 4 -> gestorReservas.eliminar(GestorEntradas.pedirCadena("Ingrese el codigo de la reserva a eliminar: "));
                case 5 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    // Metodo para hacer backup de todos los archivos (solo accesible por el administrador)
    private void realizarBackup() {
        System.out.println("Realizando backup...");

        // Mapear los archivos con las listas (value) que deseo guardar con el nombre que
        // le quiero dar al archivo (key)
        Map<String, ArrayList<?>> datosBackup = Map.of(
                "Empleados", gestorEmpleados.getEmpleados(),
                "Clientes", gestorClientes.getClientes(),
                "Habitaciones", gestorHabitaciones.getHabitaciones(),
                "Reservas", gestorReservas.getReservas()
        );
        //Llamo metodo estatico hacerBackup del GestorDeArchivos
        GestorArchivos.hacerBackup(datosBackup);
    }
}