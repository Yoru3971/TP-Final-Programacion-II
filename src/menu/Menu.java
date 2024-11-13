package menu;

import enumeraciones.TipoHabitacion;
import excepciones.CredencialesIncorrectasException;
import gestores.*;
import modelos.*;
import excepciones.Verificador;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private Empleado empleadoLogueado;
    private GestorEmpleados gestorEmpleados;
    private GestorClientes gestorClientes;
    private GestorHabitaciones gestorHabitaciones;
    private GestorReservas gestorReservas;

    //constructor
    public Menu() {
        this.scanner = new Scanner(System.in);
        this.empleadoLogueado = null;
        this.gestorEmpleados = new GestorEmpleados();
        this.gestorClientes = new GestorClientes();
        this.gestorHabitaciones = new GestorHabitaciones();
        this.gestorReservas = new GestorReservas();
    }

    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }
    public void setEmpleadoLogueado(Empleado empleadoLogueado) {
        this.empleadoLogueado = empleadoLogueado;
    }

    public void logIn() {
        // levanto la lista de empleados y chekeo que no este vacia
        ArrayList<Empleado> empleados = GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class);
        String usuario, clave;
        int i = 0;

        // pido usuario y clave por teclado hasta q ingrese bien o supere los 3 intentos
        while (empleadoLogueado == null && i < 3){
            System.out.println("Usuario:");
            usuario = scanner.nextLine();
            System.out.println("Clave:");
            clave = scanner.nextLine();
            i++;

            try{
                empleadoLogueado = Verificador.verificarCredenciales(empleados, usuario, clave);
            } catch (CredencialesIncorrectasException e){
                System.err.println(e.getMessage());
                if (i < 3) {
                    System.out.println("Intentos restantes: " + (3 - i));
                }
            }
        }

        if (empleadoLogueado == null) {
            System.err.println("Numero maximo de intentos alcandazos");
        }
    }

    public void menuInicial(){
        logIn();
        if (empleadoLogueado instanceof Administrador) {
            menuAdministrador();
        } else if (empleadoLogueado instanceof Recepcionista) {
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
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

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
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> gestionarEmpleados();
                case 2 -> gestionarHabitaciones();
                case 3 -> gestionarClientes();
                case 4 -> gestionarReservas();
                case 5 -> System.out.println("Saliendo del menú del Administrador...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    // Método para gestionar empleados (solo disponible para el Administrador)
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
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> gestorEmpleados.listar();
                case 2 -> gestorEmpleados.agregar();
                case 3 -> gestorEmpleados.modificar();
                case 4 -> gestorEmpleados.eliminar();
                case 5 -> buscarEmpleadoPorDNI();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // Método para buscar un empleado por DNI
    private void buscarEmpleadoPorDNI() {
        System.out.print("Ingrese el DNI del empleado a buscar: ");
        Integer dni = Integer.parseInt(scanner.nextLine());
        Empleado empleado = gestorEmpleados.buscarEmpleadoPorDNI(dni);
        if (empleado != null) {
            System.out.println("Empleado encontrado: " + empleado);
        } else {
            System.out.println("Empleado no encontrado.");
        }
    }


    // Método para gestionar habitaciones
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
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> gestorHabitaciones.listar();
                case 2 -> gestorHabitaciones.agregar(leerHabitacion());
                case 3 -> gestorHabitaciones.modificar();
                case 4 -> gestorHabitaciones.eliminar();
                case 5 -> buscarHabitacionPorNumero();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    ///Funcion en stand by, discutir futuro del codigo, si se opta por esta opcion se aplicara el metodo leer para todos los objetos.
    private Habitacion leerHabitacion() {
        Habitacion habitacion;
        //Logica para consultar al usuario todos los datos de la habitacion a agregar
        return habitacion;
    }

    // Método para buscar una habitación por número
    private void buscarHabitacionPorNumero() {
        System.out.print("Ingrese el número de la habitación a buscar: ");
        int numero = Integer.parseInt(scanner.nextLine());
        Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(numero);
        if (habitacion != null) {
            System.out.println("Habitación encontrada: " + habitacion);
        } else {
            System.out.println("Habitación no encontrada.");
        }
    }


    // Método para gestionar clientes
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
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> gestorClientes.listar();
                case 2 -> gestorClientes.agregar();
                case 3 -> gestorClientes.modificar();
                case 4 -> gestorClientes.eliminar();
                case 5 -> buscarClientePorDNI();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // Método para buscar un cliente por DNI
    private void buscarClientePorDNI() {
        System.out.print("Ingrese el DNI del cliente a buscar: ");
        Integer dni = Integer.parseInt(scanner.nextLine());
        Cliente cliente = gestorClientes.buscarClientePorDNI(dni);
        if (cliente != null) {
            System.out.println("Cliente encontrado: " + cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // Método para gestionar reservas
    private void gestionarReservas() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Reservas ---");
            System.out.println("1. Listar Reservas");
            System.out.println("2. Agregar Reserva");
            System.out.println("3. Modificar Reserva");
            System.out.println("4. Eliminar Reserva");
            System.out.println("5. Buscar Reserva por Código");
            System.out.println("6. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> gestorReservas.listar();
                case 2 -> gestorReservas.agregar();
                case 3 -> gestorReservas.modificar();
                case 4 -> gestorReservas.eliminar();
                case 5 -> buscarReservaPorCodigo();
                case 6 -> System.out.println("Volviendo al menú anterior...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 6);
    }

    // Método para buscar una reserva por código
    private void buscarReservaPorCodigo() {
        System.out.print("Ingrese el código de la reserva a buscar: ");
        String codigo = scanner.nextLine();
        Reserva reserva = gestorReservas.buscarReservaPorCodigo(codigo);
        if (reserva != null) {
            System.out.println("Reserva encontrada: " + reserva);
        } else {
            System.out.println("Reserva no encontrada.");
        }
    }
}