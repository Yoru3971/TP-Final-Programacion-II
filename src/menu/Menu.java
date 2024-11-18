package menu;

import enumeraciones.TipoEmpleado;
import excepciones.CredencialesIncorrectasException;
import gestores.*;
import modelos.*;
import excepciones.Verificador;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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
        //Esto deberia ir dentro de un try que catchee el error de apertura del archivo, y un finally que lo cierre
        ArrayList<Empleado> empleados = GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class);

        String usuario;
        String clave;

        int i = 0;

        // Pido usuario y clave por teclado hasta que ingrese bien o supere los 3 intentos
        while (empleadoLogueado == null && i < 3) {
            System.out.print("Usuario: ");
            usuario = GestorEntradas.pedirCadena("");

            System.out.print("Clave: ");
            clave = GestorEntradas.pedirCadena("");

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
                menuRecepcionista();
            }
        }catch(RuntimeException re){
            System.err.println("Hubo un error en la ejecución del programa.");
        }
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
            System.out.println("0. Salir");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestionarEmpleados();
                case 2 -> gestionarHabitacionesAdmin();
                case 3 -> gestionarClientes();
                case 4 -> gestionarReservas();
                case 5 -> realizarBackup();
                case 0 -> System.out.println("Saliendo del menú del Administrador...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
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
                case 1 -> gestionarHabitacionesRecep();
                case 2 -> gestionarClientes();
                case 3 -> gestionarReservas();
                case 0 -> System.out.println("Saliendo del menú del Recepcionista...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    //Metodo para gestionar empleados (solo disponible para el Administrador)
    private void gestionarEmpleados() {
        gestorEmpleados.setEmpleados(GestorArchivos.leerArregloDeArchivo("empleados.json", Empleado.class));
        int opcion;
        do {
            System.out.println("\n=== Gestión de Empleados ===");
            System.out.println("1. Listar Empleados");
            System.out.println("2. Agregar Empleado");
            System.out.println("3. Modificar Empleado");
            System.out.println("4. Eliminar Empleado");
            System.out.println("5. Buscar Empleado por DNI");
            System.out.println("0. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> gestorEmpleados.listar();
                case 2 -> gestorEmpleados.agregar();
                case 3 -> gestorEmpleados.modificar(GestorEntradas.pedirCadena("Ingrese el dni del empleado a modificar: "));
                case 4 -> gestorEmpleados.eliminar(GestorEntradas.pedirCadena("Ingrese el dni del empleado a eliminar: "));
                case 5 -> buscarEmpleadoPorDNI();
                case 0 -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorEmpleados.getEmpleados(), "empleados.json");
                    System.out.println("Volviendo al menú anterior...");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
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

    //Metodo para gestionar habitaciones
    private void gestionarHabitacionesAdmin() {
        gestorHabitaciones.setHabitaciones(GestorArchivos.leerArregloDeArchivo("habitaciones.json", Habitacion.class));

        int opcion;
        do {
            System.out.println("\n--- Gestión de Habitaciones ---");
            System.out.println("1. Listar Habitaciones");
            System.out.println("2. Agregar Habitación");
            System.out.println("3. Modificar Habitación");
            System.out.println("4. Eliminar Habitación");
            System.out.println("5. Buscar Habitación por Número");
            System.out.println("6. Ver Disponibilidad de una Habitacion");
            System.out.println("0. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorHabitaciones.listar();
                case 2 -> gestorHabitaciones.agregar();
                case 3 -> gestorHabitaciones.modificar(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion a modificar: "));
                case 4 -> gestorHabitaciones.eliminar(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion a eliminar: "));
                case 5 -> buscarHabitacionPorNumero();
                case 6 -> gestorHabitaciones.verDisponibilidad(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion de la cual desea ver el calendario: "));
                case 0 -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorHabitaciones.getHabitaciones(), "habitaciones.json");
                    System.out.println("Volviendo al menú anterior...");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void gestionarHabitacionesRecep() {
        gestorHabitaciones.setHabitaciones(GestorArchivos.leerArregloDeArchivo("habitaciones.json", Habitacion.class));

        int opcion;
        do {
            System.out.println("\n--- Gestión de Habitaciones ---");
            System.out.println("1. Listar Habitaciones");
            System.out.println("2. Modificar estado de una habitación");
            System.out.println("3. Buscar Habitación por Número");
            System.out.println("6. Ver Disponibilidad de una Habitacion");
            System.out.println("0. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorHabitaciones.listar();
                case 2 -> {}//hacer metodo q modifique SOLO EL ESTADO
                case 3 -> buscarHabitacionPorNumero();
                case 4 -> gestorHabitaciones.verDisponibilidad(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion de la cual desea ver el calendario: "));
                case 0 -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorHabitaciones.getHabitaciones(), "habitaciones.json");
                    System.out.println("Volviendo al menú anterior...");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
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
        gestorClientes.setClientes(GestorArchivos.leerArregloDeArchivo("clientes.json", Cliente.class));

        int opcion;
        do {
            System.out.println("\n--- Gestión de Clientes ---");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Agregar Cliente");
            System.out.println("3. Modificar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Buscar Cliente por DNI");
            System.out.println("0. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> gestorClientes.listar();
                case 2 -> gestorClientes.agregar();
                case 3 -> gestorClientes.modificar(GestorEntradas.pedirCadena("Ingrese el dni del cliente a modificar: "));
                case 4 -> gestorClientes.eliminar(GestorEntradas.pedirCadena("Ingrese el dni del cliente a eliminar: "));
                case 5 -> buscarClientePorDNI();
                case 0 -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorClientes.getClientes(), "clientes.json");
                    System.out.println("Volviendo al menú anterior...");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
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
        gestorReservas.setReservas(GestorArchivos.leerArregloDeArchivo("reservas.json", Reserva.class));

        int opcion;
        do {
            System.out.println("\n--- Gestión de Reservas ---");
            System.out.println("1. Listar Reservas");
            System.out.println("2. Agregar Reserva");
            System.out.println("3. Modificar Reserva");
            System.out.println("4. Eliminar Reserva");
            System.out.println("0. Volver al menú anterior");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");  // Usando GestorEntradas

            switch (opcion) {
                case 1 -> gestorReservas.listar();
                case 2 -> {
                    System.out.println("Ingrese los datos de la nueva reserva: ");

                    //faltan respectivas verificaciones
                    Habitacion habitacion = gestorHabitaciones.buscarHabitacionPorNumero(GestorEntradas.pedirEntero("Ingrese el numero de la habitacion a reservar: "));
                    //Opcional, mostrar habitacion y cliente
                    Cliente cliente = gestorClientes.buscarClientePorDni(GestorEntradas.pedirCadena("Ingrese el dni del titular de la reserva: "));

                    LocalDate checkIn = GestorEntradas.pedirFecha("Ingrese la fecha de check-in (formato: yyyy-MM-dd): ");
                    LocalDate checkOut = GestorEntradas.pedirFecha("Ingrese la fecha de check-out (formato: yyyy-MM-dd): ");

                    Reserva nuevaReserva = new Reserva(habitacion, cliente, checkIn, checkOut);

                    //Aca verifico que la reserva no pise las fechas disponibles para esa habitacion
                    //Con un for que revise el hashset de la habitacion y en cada vuelta verifique que las fechas
                    //entre el intervalo fecha1 y fecha2 no esten dentro del rango del arreglo de cada habitacion

                    gestorReservas.agregar(nuevaReserva);

                    //modificar fechas en la habitacion correspondiente
                    //guardarla en el archivo
                }
                case 3 -> gestorReservas.modificar(GestorEntradas.pedirCadena("Ingrese el codigo de la reserva a modificar: "));
                case 4 -> gestorReservas.eliminar(GestorEntradas.pedirCadena("Ingrese el codigo de la reserva a eliminar: "));
                case 0 -> {
                    GestorArchivos.escribirArregloEnArchivo(gestorReservas.getReservas(), "reservas.json");
                    System.out.println("Volviendo al menú anterior...");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    //Metodo para hacer backup de todos los archivos (solo accesible por el administrador)
    private void realizarBackup() {
        System.out.println("Realizando backup...");

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
                System.out.println("Backup realizado exitosamente.");
            } else {
                System.out.println("No hay datos para realizar el backup.");
            }
        } catch (IOException e) {
            System.out.println("Error al realizar el backup: " + e.getMessage());
        }
    }
}

