package gestores;

import modelos.Cliente;
import modelos.Habitacion;
import modelos.Reserva;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorReservas {
    private ArrayList<Reserva> reservas;

    public GestorReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    // Faltan verificaciones
    public void agregar(Habitacion habitacion, Cliente cliente) {
        LocalDateTime checkIn = GestorEntradas.pedirFechaHora("Ingrese la fecha y hora de check-in (formato: yyyy-MM-dd HH:mm): ");
        LocalDateTime checkOut = GestorEntradas.pedirFechaHora("Ingrese la fecha y hora de check-out (formato: yyyy-MM-dd HH:mm): ");

        Reserva nuevaReserva = new Reserva(habitacion, cliente, checkIn, checkOut);
        reservas.add(nuevaReserva);
        System.out.println("Reserva agregada con éxito.");
    }


    public void eliminar(String codigo) {
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
        Reserva reserva = buscarReservaPorCodigo(codigo);
        int index = buscarIndiceReserva(reserva);

        Reserva reservaModificada = reservas.get(index);

        int opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Habitación");
            System.out.println("2. Cliente");
            System.out.println("3. Fecha de Check-In");
            System.out.println("4. Fecha de Check-Out");
            System.out.println("0. Salir");
            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");

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
                    String nuevaFechaCheckIn = GestorEntradas.pedirCadena("Ingrese nueva fecha de Check-in (formato YYYY-MM-DDTHH:MM): ");
                    LocalDateTime nuevoCheckIn = LocalDateTime.parse(nuevaFechaCheckIn);
                    reservaModificada.setCheckIn(nuevoCheckIn);
                    System.out.println("Check-in modificado con éxito");
                    break;
                case 4:
                    String nuevaFechaCheckOut = GestorEntradas.pedirCadena("Ingrese nueva fecha de Check-out (formato YYYY-MM-DDTHH:MM): ");
                    LocalDateTime nuevoCheckOut = LocalDateTime.parse(nuevaFechaCheckOut);
                    reservaModificada.setCheckOut(nuevoCheckOut);
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
        System.out.println(reservaModificada);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int confirmar = GestorEntradas.pedirEntero("Seleccione una opción: ");

        if (confirmar == 1) {
            reservas.set(index, reservaModificada); // Guardar los cambios de la reserva modificada
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