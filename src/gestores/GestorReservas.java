package gestores;

import excepciones.*;
import modelos.Cliente;
import modelos.Habitacion;
import modelos.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;


public class GestorReservas {
    private ArrayList<Reserva> reservas;

    //Constructores
    public GestorReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    //Getters y Setters
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    //Metodos ABM y Listar
    public void agregar(Reserva nuevaReserva) {
        reservas.add(nuevaReserva);
        System.out.println("Reserva agregada con éxito.");
    }

    public Habitacion eliminar(Integer id) {
        Reserva reservaEliminar = buscarReservaPorCodigo(id);

        if (reservaEliminar == null) {
            System.out.println("Reserva no encontrado.");
            return null;
        }

        System.out.println("Datos de la reserva a eliminar:");
        System.out.println(reservaEliminar);

        System.out.println("¿Desea confirmar la eliminacion?\n1.Si \n2.No \n");

        String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

        if (opcion.equals("1")) {
            reservas.remove(reservaEliminar);
            System.out.println("Eliminacion completada con éxito.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }

        return reservaEliminar.getHabitacion();
    }

    public void listar() {
        System.out.println("\nLista de Reservas");
        System.out.println("=========================");

        if (!reservas.isEmpty()){
            for (Reserva reserva : reservas){
                System.out.println(reserva);
            }
        }
    }

    public void modificar(ArrayList<Habitacion> listaHabitaciones, ArrayList<Cliente> listaClientes) {
        Integer codigo = GestorEntradas.pedirEntero("Ingrese el código de la reserva a modificar: ");
        Reserva reservaModificar = buscarReservaPorCodigo(codigo);

        if (reservaModificar == null) {
            System.out.println("Reserva no encontrada.");
            GestorEntradas.pausarConsola();
            return;
        }

        System.out.println("Datos de la reserva a modificar:");
        System.out.println(reservaModificar);
        int indiceReservaModificar = reservas.indexOf(reservaModificar);

        String opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Habitación");
            System.out.println("2. Cliente");
            System.out.println("3. Fecha de Check-In y/o Check-Out");
            System.out.println("0. Salir");

            opcion = GestorEntradas.pedirCadena("Seleccione una opción: ");

            switch (opcion) {
                case "1" -> modificarHabitacion(reservaModificar, listaHabitaciones);
                case "2" -> modificarCliente(reservaModificar, listaClientes);
                case "3" -> modificarFechas(reservaModificar);
                case "0" -> System.out.println("Saliendo.");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println("\n¿Quiere realizar otra modificación?\n1. Sí \n2. No");
            opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

        } while (!opcion.equals("0"));

        System.out.println("\nReserva modificada");
        System.out.println(reservaModificar);
        System.out.println("¿Desea confirmar los cambios?\n1. Sí\n2. No");

        String confirmar = GestorEntradas.pedirCadena("Ingrese una opción: ");
        if (confirmar.equals("1")) {
            reservas.set(indiceReservaModificar, reservaModificar); // Guardar los cambios de la reserva modificada
            System.out.println("Modificación completada.");
        } else {
            System.out.println("Modificación cancelada.");
        }
    }

    public void mostrarHistorialCliente(String dniCliente) {
        boolean hayReserva = false;
        int contador = 1;

        if (reservas.isEmpty()){
            System.out.println("No hay reservas disponibles");
            return;
        }
        System.out.println("Historial de reservas del cliente con DNI: " + dniCliente);
        System.out.println("===========================================");
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getDni().equals(dniCliente)) {
                System.out.println(contador + ": " + reserva);
                contador++;
                hayReserva = true;
            }
        }

        if (!hayReserva) {
            System.out.println("No se encontraron reservas para el cliente con DNI: " + dniCliente);
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

    //Metodos Auxiliares
    public LocalDate pedirFechaReserva(String mensaje){
        LocalDate fecha = null;

        boolean fechaValida = false;

        do{
            fecha = GestorEntradas.pedirFecha(mensaje);

            if(fecha==null){
                System.out.println("Fecha no valida. Intente de nuevo");
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
            Integer nuevaHabitacionNumero = GestorEntradas.pedirEntero("Ingrese el número de la nueva habitación: ");

            for (Habitacion habitacion : listaHabitaciones) {
                if (habitacion.getNumeroHabitacion().equals(nuevaHabitacionNumero)) {
                    nuevaHabitacion = habitacion;
                    break;
                }
            }

            if (nuevaHabitacion == null || nuevaHabitacion.equals(reservaModificar.getHabitacion())) {
                System.out.println("La habitación seleccionada no es válida o ya está asignada a esta reserva.");
                return;
            }

            try {
                if (Verificador.verificarDisponibilidadHabitacion(nuevaHabitacion, reservaModificar.getCheckIn(), reservaModificar.getCheckOut())) {

                    System.out.println("Datos de la  nueva habitacion:");
                    System.out.println(nuevaHabitacion);

                    System.out.println("¿Desea confirmar los cambios?\n1.Si \n2.No \n");

                    String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

                    if (opcion.equals("1")) {
                        reservaModificar.setHabitacion(nuevaHabitacion);
                        reservaModificar.liberarFechas();
                        reservaModificar.reservarFechas();

                        reservas.set(indiceReservaModificar, reservaModificar);
                        habitacionValida = true;
                        System.out.println("Modificación completada con éxito");
                    } else {
                        System.out.println("Modificación cancelada");
                    }
                }
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(e.getMessage());
            }
        }while(!habitacionValida);
    }

    private void modificarCliente(Reserva reservaModificar, ArrayList<Cliente> listaClientes) {
        boolean clienteValido = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);
        Cliente nuevoCliente = null;

        do{
            String nuevoDni = GestorEntradas.pedirCadena("Ingrese el DNI del nuevo titular de la reserva: ");

            for (Cliente cliente : listaClientes) {
                if (cliente.getDni().equals(nuevoDni)) {
                    nuevoCliente = cliente;
                    break;
                }
            }

            if(nuevoCliente == null){
                System.out.println("Cliente no encontrado o DNI no valido.");
                return;
            }

            System.out.println("Datos del nuevo cliente:");
            System.out.println(nuevoCliente);

            System.out.println("¿Desea confirmar los cambios?\n1.Si \n2.No \n");

            String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

            if (opcion.equals("1")) {
                reservaModificar.setCliente(nuevoCliente);
                reservas.set(indiceReservaModificar, reservaModificar);
                clienteValido = true;
                System.out.println("Modificación completada con éxito");
            } else {
                System.out.println("Modificación cancelada");
            }
        }while(!clienteValido);
    }

    private void modificarFechas(Reserva reservaModificar) {
        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Solo Check-In");
        System.out.println("2. Solo Check-Out");
        System.out.println("3. Ambos Check-In y Check-Out");
        System.out.println("0. Salir");

        String opcionFecha = GestorEntradas.pedirCadena("Seleccione una opción: ");

        switch (opcionFecha) {
            case "1" -> modificarCheckIn(reservaModificar);
            case "2" -> modificarCheckOut(reservaModificar);
            case "3" -> modificarCheckInYCheckOut(reservaModificar);
            case "4" -> System.out.println("Saliendo...");
            default -> System.out.println("Opción no válida. Intente nuevamente.");
        }
    }

    private void modificarCheckIn(Reserva reservaModificar) {
        boolean fechaValida = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);

        do{
            LocalDate nuevoCheckIn = pedirFechaReserva("Ingrese nueva fecha de check-in (formato YYYY-MM-DD): ");
            try {
                if (Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), nuevoCheckIn, reservaModificar.getCheckOut())) {
                    System.out.println("¿Desea confirmar el "+nuevoCheckIn+"como nuevo check-in?\n1.Si \n2.No \n");

                    String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

                    if (opcion.equals("1")) {
                        reservaModificar.liberarFechas();
                        reservaModificar.setCheckIn(nuevoCheckIn);
                        reservaModificar.reservarFechas();

                        reservas.set(indiceReservaModificar, reservaModificar);
                        fechaValida = true;
                        System.out.println("Modificación completada con éxito");
                    } else {
                        System.out.println("Modificación cancelada");
                    }
                }
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(e.getMessage());
            }
        }while(!fechaValida);
    }

    private void modificarCheckOut(Reserva reservaModificar) {
        boolean fechaValida = false;
        int indiceReservaModificar = reservas.indexOf(reservaModificar);

        do{
            LocalDate nuevoCheckOut = pedirFechaReserva("Ingrese nueva fecha de check-out (formato YYYY-MM-DD): ");
            try {
                if(nuevoCheckOut.isAfter(reservaModificar.getCheckIn()) && Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), reservaModificar.getCheckIn(), nuevoCheckOut)){
                    System.out.println("¿Desea confirmar el "+nuevoCheckOut+"como nuevo check-in?\n1.Si \n2.No \n");

                    String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

                    if (opcion.equals("1")) {

                        reservaModificar.liberarFechas();
                        reservaModificar.setCheckOut(nuevoCheckOut);
                        reservaModificar.reservarFechas();

                        reservas.set(indiceReservaModificar, reservaModificar);
                        fechaValida = true;
                        System.out.println("Modificación completada con éxito");
                    } else {
                        System.out.println("Modificación cancelada");
                    }
                }else{
                    System.out.println("La fecha de check-out no puede ser previa a la de check-in.");
                }
            } catch (HabitacionNoDisponibleException e) {
                System.out.println(e.getMessage());
            }
        }while(!fechaValida);
    }

    private void modificarCheckInYCheckOut(Reserva reservaModificar) {
       modificarCheckIn(reservaModificar);
       modificarCheckOut(reservaModificar);
    }
}