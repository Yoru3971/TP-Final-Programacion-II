package gestores;

import modelos.Cliente;
import modelos.Habitacion;
import modelos.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorReservas {
    private ArrayList<Reserva> reservas;

    //constructores
    public GestorReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    //getters y setters
    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Faltan verificaciones
    public void agregar(Habitacion habitacion, Cliente cliente) {
        LocalDate checkIn = GestorEntradas.pedirFecha("Ingrese la fecha de check-in (formato: yyyy-MM-dd): ");
        LocalDate checkOut = GestorEntradas.pedirFecha("Ingrese la fecha de check-out (formato: yyyy-MM-dd): ");

        Reserva nuevaReserva = new Reserva(habitacion, cliente, checkIn, checkOut);
        reservas.add(nuevaReserva);
        System.out.println("Reserva agregada con éxito.");
    }

    public void eliminar(String codigo) {
        //Cuando se elimine una reserva se deben liberar los dias que se bloquearon para dicha reserva en la habitacion
        Reserva reserva = buscarReservaPorCodigo(codigo);
        reservas.remove(reserva);
    }

    public void listar() {
        System.out.println("\nLista de Reservas");
        System.out.println("=========================");
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (Reserva r : reservas) {
                System.out.println(r);
            }
        }
    }

    public void modificar(String codigo) {
        Reserva reservaModificar = buscarReservaPorCodigo(codigo);

        int indiceReservaModificar = reservas.indexOf(reservaModificar);
        int opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Habitación");
            System.out.println("2. Cliente");
            System.out.println("3. Fecha de Check-In");
            System.out.println("4. Fecha de Check-Out");
            System.out.println("0. Salir");

            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
            GestorEntradas.limpiarBuffer();

            switch (opcion) {
                case 1:
                    System.out.println("Seleccione la nueva habitación:");
                    // Aquí deberías manejar la lógica para elegir una nueva habitación
                    // Asumo que tienes una lista de habitaciones
                    System.out.println("Habitación modificada con éxito");
                    break;
                case 2:
                    System.out.println("Seleccione el nuevo cliente:");
                    // Lógica para seleccionar un nuevo cliente
                    System.out.println("Cliente modificado con éxito");
                    break;
                case 3:
                    String nuevaFechaCheckIn = GestorEntradas.pedirCadena("Ingrese nueva fecha de Check-in (formato YYYY-MM-DD): ");
                    LocalDate nuevoCheckIn = LocalDate.parse(nuevaFechaCheckIn);
                    reservaModificar.setCheckIn(nuevoCheckIn);
                    System.out.println("Check-in modificado con éxito");
                    break;
                case 4:
                    String nuevaFechaCheckOut = GestorEntradas.pedirCadena("Ingrese nueva fecha de Check-out (formato YYYY-MM-DDTHH:MM): ");
                    LocalDate nuevoCheckOut = LocalDate.parse(nuevaFechaCheckOut);
                    reservaModificar.setCheckOut(nuevoCheckOut);
                    System.out.println("Check-out modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println("\n¿Quiere realizar otra modificación?");
            System.out.println("1. Sí");
            System.out.println("0. No");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");

        } while (opcion != 0);

        System.out.println("\nReserva modificada");
        System.out.println(reservaModificar);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int confirmar = GestorEntradas.pedirEntero("Seleccione una opción: ");

        if (confirmar == 1) {
            reservas.set(indiceReservaModificar, reservaModificar); // Guardar los cambios de la reserva modificada
            System.out.println("Modificación completada.");
        } else {
            System.out.println("Modificación cancelada.");
        }
    }

    // Métodos de busqueda
    private int buscarIndiceReserva(Reserva reserva) {
        for (Reserva r : reservas) {
            if (r.equals(reserva)) {
                return reservas.indexOf(r);
            }
        }
        return -1;
    }

    public Reserva buscarReservaPorCodigo(String codigo) {
        Reserva reserva = null;
        for (Reserva r : reservas) {
            if (r.getCodigo().equals(codigo)) {
                reserva = r;
            }
        }
        return reserva;
    }
}