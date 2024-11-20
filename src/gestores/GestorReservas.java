package gestores;

import excepciones.*;
import modelos.Cliente;
import modelos.Habitacion;
import modelos.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        try{
            if (Verificador.verificarArregloVacio(reservas)){
                for (Reserva reserva : reservas){
                    System.out.println(reserva);
                }
            }
        }catch (ArregloVacioException e){
            System.out.println(e.getMessage());
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

        if (!Verificador.verificarDNI(dniCliente)){
            System.out.println("Dni no valido.");
            return;
        }
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

    ///Porfavor para los metodos que siguen a partir de aca ver si falta implementar verificaciones y manejo de excepciones !!!
    private void modificarHabitacion(Reserva reservaModificar, ArrayList<Habitacion> listaHabitaciones) {
        Integer nuevaHabitacionNumero = GestorEntradas.pedirEntero("Ingrese el número de la nueva habitación: ");
        Habitacion nuevaHabitacion = null;

        for (Habitacion habitacion : listaHabitaciones) {
            if (habitacion.getNumeroHabitacion().equals(nuevaHabitacionNumero)) {
                nuevaHabitacion = habitacion;
                break;
            }
        }

        if (nuevaHabitacion != null && nuevaHabitacion != reservaModificar.getHabitacion()) {
            try {
                Verificador.verificarHabitacionNula(nuevaHabitacion);

                if (Verificador.verificarDisponibilidadHabitacion(nuevaHabitacion, reservaModificar.getCheckIn(), reservaModificar.getCheckOut())) {
                    reservaModificar.liberarFechas();
                    reservaModificar.setHabitacion(nuevaHabitacion);
                    reservaModificar.reservarFechas();
                    System.out.println("Habitación modificada con éxito.");
                }
            } catch (HabitacionNulaException | HabitacionNoDisponibleException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("La habitación seleccionada no es válida o ya está asignada a esta reserva.");
        }
    }

    private void modificarCliente(Reserva reservaModificar, ArrayList<Cliente> listaClientes) {
        String nuevoDni = GestorEntradas.pedirCadena("Ingrese el DNI del nuevo titular de la reserva: ");
        Cliente nuevoCliente = null;

        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equals(nuevoDni)) {
                nuevoCliente = cliente;
                break;
            }
        }

        try {
            if(Verificador.verificarClienteNulo(nuevoCliente)){
                reservaModificar.setCliente(nuevoCliente);
                System.out.println("Cliente modificado con éxito.");
            }
        } catch (ClienteNuloException e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarFechas(Reserva reservaModificar) {
        System.out.println("¿Qué desea modificar?");
        System.out.println("1. Solo Check-In");
        System.out.println("2. Solo Check-Out");
        System.out.println("3. Ambos Check-In y Check-Out");

        String opcionFecha = GestorEntradas.pedirCadena("Seleccione una opción: ");

        switch (opcionFecha) {
            case "1":
                modificarCheckIn(reservaModificar);
                break;
            case "2":
                modificarCheckOut(reservaModificar);
                break;
            case "3":
                modificarCheckInYCheckOut(reservaModificar);
                break;
            default:
                System.out.println("Opción no válida. Intente nuevamente.");
                break;
        }
    }

    private void modificarCheckIn(Reserva reservaModificar) {
        LocalDate nuevoCheckIn = pedirFechaReserva("Ingrese nueva fecha de Check-in (formato YYYY-MM-DD): ");
        try {
            if (Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), nuevoCheckIn, reservaModificar.getCheckOut())) {
                reservaModificar.liberarFechas();
                reservaModificar.setCheckIn(nuevoCheckIn);
                reservaModificar.reservarFechas();
                System.out.println("Check-in modificado con éxito.");
            }
        } catch (HabitacionNoDisponibleException e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarCheckOut(Reserva reservaModificar) {
        LocalDate nuevoCheckOut = pedirFechaReserva("Ingrese nueva fecha de Check-out (formato YYYY-MM-DD): ");
        try {
            if (Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), reservaModificar.getCheckIn(), nuevoCheckOut)) {
                reservaModificar.liberarFechas();
                reservaModificar.setCheckOut(nuevoCheckOut);
                reservaModificar.reservarFechas();
                System.out.println("Check-out modificado con éxito.");
            }
        } catch (HabitacionNoDisponibleException e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarCheckInYCheckOut(Reserva reservaModificar) {
        LocalDate nuevoCheckIn = pedirFechaReserva("Ingrese nueva fecha de Check-in (formato YYYY-MM-DD): ");
        LocalDate nuevoCheckOut = pedirFechaReserva("Ingrese nueva fecha de Check-out (formato YYYY-MM-DD): ");
        try {
            if (Verificador.verificarDisponibilidadHabitacion(reservaModificar.getHabitacion(), nuevoCheckIn, nuevoCheckOut)) {
                reservaModificar.liberarFechas();
                reservaModificar.setCheckIn(nuevoCheckIn);
                reservaModificar.setCheckOut(nuevoCheckOut);
                reservaModificar.reservarFechas();
                System.out.println("Check-in y Check-out modificados con éxito.");
            }
        } catch (HabitacionNoDisponibleException e) {
            System.out.println(e.getMessage());
        }
    }
}