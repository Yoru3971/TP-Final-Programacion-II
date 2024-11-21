package gestores;

import excepciones.*;
import modelos.Cliente;
import modelos.Habitacion;
import modelos.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorReservas {
    //Constantes para implementar color como en los menu
    private final String colorAmarillo = "\u001B[93m";
    private final String colorVerde = "\u001B[92m";
    private final String colorRojo = "\u001B[91m";
    private final String resetColor = "\u001B[0m";

    //Aca se guardan las reservas, luego esto es guardado en el archivo, se hace desde el menu
    private ArrayList<Reserva> reservas;

    //Constructores
    //Este recibe un arreglo (levantado de un archivo)
    public GestorReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    //Default en caso de querer crear un arreglo de personas desde cero
    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    //Getters y Setters, usados para modificar el arreglo levantado
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    //Metodos ABM y Listar
    public void agregar(Reserva nuevaReserva) {
        reservas.add(nuevaReserva);
        System.out.println(colorVerde+"\n  Reserva agregada con éxito."+resetColor);
    }

    public Habitacion eliminar(Integer id) {
        Reserva reservaEliminar = buscarReservaPorCodigo(id);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no una reserva con ese id

        if (reservaEliminar == null) {
            System.out.println(colorRojo + "\n  Reserva no encontrada." + resetColor);
            return null;
        }

        System.out.println("\n  Datos de la reserva a eliminar:");
        System.out.println(reservaEliminar);
        System.out.println("\n  ¿Desea confirmar la eliminación?\n  1. Sí \n  2. No \n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            reservas.remove(reservaEliminar);
            System.out.println(colorVerde + "\n  Eliminación completada con éxito." + resetColor);
        } else {
            System.out.println(colorRojo + "\n  Eliminación cancelada." + resetColor);
        }

        //Retorna la habitacion para despues desde afuera poder modificar las fechas en las que estaba alquilada esa habitacion
        return reservaEliminar.getHabitacion();
    }

    public void modificar(ArrayList<Habitacion> listaHabitaciones, ArrayList<Cliente> listaClientes) {
        Integer codigo = GestorEntradas.pedirEntero("  Ingrese el código de la reserva a modificar: ");
        Reserva reservaModificar = buscarReservaPorCodigo(codigo);

        if (reservaModificar == null) {
            System.out.println(colorRojo + "\n  Reserva no encontrada." + resetColor);
            GestorEntradas.pausarConsola();
            return;
        }

        System.out.println(colorAmarillo + "\n  Datos de la reserva a modificar:" + resetColor);
        System.out.println(reservaModificar);
        int indiceReservaModificar = reservas.indexOf(reservaModificar);

        String opcion;
        do {
            System.out.println("\n  ¿Qué desea modificar?");
            System.out.println("  1. Habitación");
            System.out.println("  2. Cliente");
            System.out.println("  3. Fecha de Check-In y/o Check-Out");
            System.out.println("  0. Salir");

            opcion = GestorEntradas.pedirCadena("  Seleccione una opción: ");

            switch (opcion) {
                case "1" -> modificarHabitacion(reservaModificar, listaHabitaciones);
                case "2" -> modificarCliente(reservaModificar, listaClientes);
                case "3" -> modificarFechas(reservaModificar);
                case "0" -> System.out.println("  Saliendo.");
                default -> System.out.println(colorRojo + "  Opción no válida. Intente nuevamente."+resetColor);
            }

            System.out.println("\n  ¿Quiere realizar otra modificación?\n  1. Sí \n  2. No");
            opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        } while (!opcion.equals("0"));

        System.out.println(colorAmarillo + "\n  Reserva modificada:" + resetColor);
        System.out.println(reservaModificar);
        System.out.println("  ¿Desea confirmar los cambios?\n  1. Sí \n  2. No");

        String confirmar = GestorEntradas.pedirCadena("  Ingrese una opción: ");
        if (confirmar.equals("1")) {
            reservas.set(indiceReservaModificar, reservaModificar); // Guardar los cambios de la reserva modificada
            System.out.println(colorVerde + "\n  Modificación completada." + resetColor);
        } else {
            System.out.println(colorRojo + "\n  Modificación cancelada." + resetColor);
        }
    }

    public void listar() {
        System.out.println(colorAmarillo + "\n  Lista de Reservas" + resetColor);
        System.out.println(colorAmarillo + "  =========================" + resetColor);

        if (!reservas.isEmpty()) {
            for (Reserva r : reservas) {
                System.out.println("  "+r);
            }
        } else {
            System.out.println(colorRojo + "  No hay reservas registradas." + resetColor);
        }
    }

    public void mostrarHistorialCliente(String dniCliente) {
        boolean hayReserva = false;
        int contador = 1;

        if (reservas.isEmpty()) {
            System.out.println(colorRojo + "\n  No hay reservas disponibles" + resetColor);
            return;
        }

        System.out.println(colorAmarillo + "\n  Historial de reservas del cliente con DNI: " + dniCliente + resetColor);
        System.out.println("  ===========================================");

        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getDni().equals(dniCliente)) {
                System.out.println("  " + contador + ": " + reserva);
                contador++;
                hayReserva = true;
            }
        }

        if (!hayReserva) {
            System.out.println(colorRojo + "\n  No se encontraron reservas para el cliente con DNI: " + dniCliente + resetColor);
        }
    }

    //Metodos de busqueda
    public Reserva buscarReservaPorCodigo(Integer codigo) {
        Reserva reserva = null;
        for (Reserva r : reservas) {
            if (r.getID().equals(codigo)) {
                return r;
            }
        }
        return null;
    }

    //Metodos especificos de Reserva para pedir datos
    //Tienen la misma estructura que los de GestorPersonas
    public LocalDate pedirFechaReserva(String mensaje){
        LocalDate fecha = null;

        boolean fechaValida = false;

        do{
            fecha = GestorEntradas.pedirFecha(mensaje);

            if(fecha==null){
                System.out.println(colorRojo+"  Fecha no valida. Intente de nuevo"+resetColor);
            }else{
                fechaValida = true;
            }
        }while(!fechaValida);

        return fecha;
    }

    private void modificarHabitacion(Reserva reservaModificar, ArrayList<Habitacion> listaHabitaciones) {
        boolean habitacionValida = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);
        Habitacion nuevaHabitacion = null;

        do{
            Integer nuevaHabitacionNumero = GestorEntradas.pedirEntero("  Ingrese el número de la nueva habitación: ");

            for (Habitacion habitacion : listaHabitaciones) {
                if (habitacion.getNumeroHabitacion().equals(nuevaHabitacionNumero)) {
                    nuevaHabitacion = habitacion;
                    break;
                }
            }

            if (nuevaHabitacion == null || nuevaHabitacion.equals(reservaModificar.getHabitacion())) {
                System.out.println("  La habitación seleccionada no es válida o ya está asignada a esta reserva.");
                return;
            }

            try {
                if (Verificador.verificarDisponibilidadHabitacion(nuevaHabitacion, reservaModificar.getCheckIn(), reservaModificar.getCheckOut())) {

                    System.out.println("  Datos de la  nueva habitacion:");
                    System.out.println(nuevaHabitacion);

                    System.out.println("\n  ¿Desea confirmar los cambios?\n  1. Si \n  2. No\n");

                    String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

                    if (opcion.equals("1")) {
                        reservaModificar.setHabitacion(nuevaHabitacion);
                        reservaModificar.liberarFechas();
                        reservaModificar.reservarFechas();

                        reservas.set(indiceReservaModificar, reservaModificar);
                        habitacionValida = true;
                        System.out.println(colorVerde + "\n  Modificación completada con éxito." + resetColor);
                    } else {
                        System.out.println(colorRojo + "\n  Modificación cancelada." + resetColor);
                    }
                }
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
            }
        }while(!habitacionValida);
    }

    private void modificarCliente(Reserva reservaModificar, ArrayList<Cliente> listaClientes) {
        boolean clienteValido = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);
        Cliente nuevoCliente = null;

        do{
            String nuevoDni = GestorEntradas.pedirCadena("  Ingrese el DNI del nuevo titular de la reserva: ");

            for (Cliente cliente : listaClientes) {
                if (cliente.getDni().equals(nuevoDni)) {
                    nuevoCliente = cliente;
                    break;
                }
            }

            if(nuevoCliente == null){
                System.out.println(colorRojo+"  Cliente no encontrado o DNI no valido."+resetColor);
                return;
            }

            System.out.println("  Datos del nuevo cliente:");
            System.out.println("  "+nuevoCliente);

            System.out.println("\n  ¿Desea confirmar los cambios?\n  1. Sí \n  2. No\n");

            String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

            if (opcion.equals("1")) {
                reservaModificar.setCliente(nuevoCliente);
                reservas.set(indiceReservaModificar, reservaModificar);
                clienteValido = true;
                System.out.println(colorVerde + "\n  Modificación completada con éxito." + resetColor);
            } else {
                System.out.println(colorRojo + "\n  Modificación cancelada." + resetColor);
            }
        }while(!clienteValido);
    }

    private void modificarFechas(Reserva reservaModificar) {
        System.out.println("  ¿Qué desea modificar?");
        System.out.println("  1. Solo Check-In");
        System.out.println("  2. Solo Check-Out");
        System.out.println("  3. Ambos Check-In y Check-Out");
        System.out.println("  0. Salir");

        String opcionFecha = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        switch (opcionFecha) {
            case "1" -> modificarCheckIn(reservaModificar);
            case "2" -> modificarCheckOut(reservaModificar);
            case "3" -> modificarCheckInYCheckOut(reservaModificar);
            case "4" -> System.out.println("  Saliendo...");
            default -> System.out.println(colorRojo+"  Opción no válida. Intente nuevamente."+resetColor);
        }
    }

    private void modificarCheckIn(Reserva reservaModificar) {
        boolean fechaValida = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);

        do{
            LocalDate nuevoCheckIn = pedirFechaReserva("  Ingrese nueva fecha de check-in (formato YYYY-MM-DD): ");
            try {
                if (Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), nuevoCheckIn, reservaModificar.getCheckOut())) {
                    System.out.println("\n  ¿Desea confirmar el "+nuevoCheckIn+"como nuevo check-in?\n  1. Si \n  2. No \n");

                    String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

                    if (opcion.equals("1")) {
                        reservaModificar.liberarFechas();
                        reservaModificar.setCheckIn(nuevoCheckIn);
                        reservaModificar.reservarFechas();

                        reservas.set(indiceReservaModificar, reservaModificar);
                        fechaValida = true;
                        System.out.println(colorVerde + "\n  Modificación completada con éxito." + resetColor);
                    } else {
                        System.out.println(colorRojo + "\n  Modificación cancelada." + resetColor);
                    }
                }
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
            }
        }while(!fechaValida);
    }

    private void modificarCheckOut(Reserva reservaModificar) {
        boolean fechaValida = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);

        do{
            LocalDate nuevoCheckOut = pedirFechaReserva("  Ingrese nueva fecha de check-out (formato YYYY-MM-DD): ");
            try {
                if(nuevoCheckOut.isAfter(reservaModificar.getCheckIn()) && Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), reservaModificar.getCheckIn(), nuevoCheckOut)){
                    System.out.println("\n  ¿Desea confirmar el "+nuevoCheckOut+"como nuevo check-in?\n  1. Si \n  2. No \n");

                    String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

                    if (opcion.equals("1")) {

                        reservaModificar.liberarFechas();
                        reservaModificar.setCheckOut(nuevoCheckOut);
                        reservaModificar.reservarFechas();

                        reservas.set(indiceReservaModificar, reservaModificar);
                        fechaValida = true;
                        System.out.println(colorVerde + "\n  Modificación completada con éxito." + resetColor);
                    } else {
                        System.out.println(colorRojo + "\n  Modificación cancelada." + resetColor);
                    }
                }else{
                    System.out.println(colorRojo+"  La fecha de check-out no puede ser previa a la de check-in."+resetColor);
                }
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(colorRojo+"  "+e.getMessage()+resetColor);
            }
        }while(!fechaValida);
    }

    private void modificarCheckInYCheckOut(Reserva reservaModificar) {
       modificarCheckIn(reservaModificar);
       modificarCheckOut(reservaModificar);
    }
}