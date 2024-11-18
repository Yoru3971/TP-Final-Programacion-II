package gestores;

import excepciones.ArregloVacioException;
import excepciones.ObjetoNuloException;
import excepciones.Verificador;
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

    public void eliminar(String codigo) {
        //Cuando se elimine una reserva se deben liberar los dias que se bloquearon para dicha reserva en la habitacion

        try{
            Reserva reservaEliminar = buscarReservaPorCodigo(codigo);
            if (!Verificador.verificarObjetoNulo(reservaEliminar)) {
                throw new ObjetoNuloException("Reserva no encontrada");
            }
            int opcion;
            do {
                System.out.println("Confirmar eliminación de la reserva con el codigo " + codigo + ": ");
                System.out.println("1. Sí");
                System.out.println("2. No");

                opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
                //GestorEntradas.limpiarBuffer();

                if (opcion == 1) {
                    reservaEliminar.liberarFechas();
                    reservas.remove(reservaEliminar);
                    System.out.println("Reserva eliminada del sistema con éxito");
                } else if (opcion == 2) {
                    System.out.println("La reserva no fue eliminado del sistema");
                } else {
                    System.out.println("Opcion no valida. Intente nuevamente");
                }
            } while (opcion < 1 && opcion > 2);
        }catch (ObjetoNuloException e){
            System.out.println(e.getMessage());
        }
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

    //Pendiente de modificar
    public void modificar(String codigo, ArrayList<Habitacion>listaHabitaciones, ArrayList<Cliente> listaClientes) {
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

    //Metodos de busqueda
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