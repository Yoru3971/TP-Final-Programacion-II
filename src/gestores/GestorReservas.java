package gestores;

import modelos.Reserva;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorReservas implements IGestionable<Reserva>{
    private ArrayList<Reserva> reservas;

    public GestorReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    //Faltan verificaciones
    @Override
    public void agregar(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public void eliminar(Reserva reserva) {
        reservas.remove(reserva);
    }

    @Override
    public void modificar(Reserva reserva) {
        Scanner scanner = new Scanner(System.in);

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
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Seleccione la nueva habitación:");
                    // Aquí deberías manejar la lógica para elegir una nueva habitación
                    // Asumo que tienes una lista de habitaciones
                    System.out.println("Habitacion modificado con éxito");
                    break;
                case 2:
                    System.out.println("Seleccione el nuevo cliente:");
                    // Lógica para seleccionar un nuevo cliente
                    System.out.println("Cliente modificado con éxito");
                    break;
                case 3:
                    System.out.print("Ingrese nueva fecha de Check-in (formato YYYY-MM-DDTHH:MM): ");
                    String nuevaFechaCheckIn = scanner.nextLine();
                    LocalDateTime nuevoCheckIn = LocalDateTime.parse(nuevaFechaCheckIn);
                    reservaModificada.setCheckIn(nuevoCheckIn);
                    System.out.println("CheckIn modificado con éxito");
                    break;
                case 4:
                    System.out.print("Ingrese nueva fecha de Check-out (formato YYYY-MM-DDTHH:MM): ");
                    String nuevaFechaCheckOut = scanner.nextLine();
                    LocalDateTime nuevoCheckOut = LocalDateTime.parse(nuevaFechaCheckOut);
                    reservaModificada.setCheckOut(nuevoCheckOut);
                    System.out.println("CheckOut modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println("\nQuiere realizar otra modificacion?");
            System.out.println("1.Si");
            System.out.println("0.No");
            opcion = scanner.nextInt();
            scanner.nextLine();

        } while (opcion != 0);

        System.out.println("\nReserva modificada");
        System.out.println(reservaModificada);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1.Si");
        System.out.println("2.No");

        int confirmar = scanner.nextInt();

        if (confirmar == 1) {
            reservas.set(index, reservaModificada); // Guardar los cambios de la reserva modificada
            System.out.println("Modificación completada.");
        } else {
            System.out.println("Modificacion cancelada");
        }
    }

    private int buscarIndiceReserva(Reserva reserva) {
        for (Reserva r : reservas) {
            if (r.equals(reserva)) {
                return reservas.indexOf(r);
            }
        }
        return -1;
    }

}
