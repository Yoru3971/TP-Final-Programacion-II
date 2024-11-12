package gestores;

import modelos.Habitacion;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorHabitaciones implements IGestionable<Integer> {
    private ArrayList<Habitacion> habitaciones;
    private Scanner scanner = new Scanner(System.in);

    public GestorHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public GestorHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    //Faltan verificaciones
    @Override
    public void agregar() {
        Habitacion nuevaHabitacion = new Habitacion(
                pedirNumeroHabitacion(scanner),
                pedirEstadoHabitacion(scanner),
                pedirTipoHabitacion(scanner),
                pedirPrecioDiario(scanner)
        );

        habitaciones.add(nuevaHabitacion);
        System.out.println("Habitación agregada con éxito.");
    }

    @Override
    public void eliminar(Integer nroHabitacion) {
        Habitacion habitacionEliminar = buscarPorNumeroHabitacion(nroHabitacion);

        System.out.println("Confirmar eliminacion de la habitacion " + nroHabitacion+": ");

        System.out.println("1. Si");
        System.out.println("2. No");

        if(scanner.nextInt() == 1){
            habitaciones.remove(habitacionEliminar);
            System.out.println("Habitacion eliminada del sistema con exito.");
        }else{
            System.out.println("La habitacion no fue eliminada del sistema.");
        }
    }

    //En todos los caso hay que agregar verificaciones, habitacion no repetida, fecha no repetida, etc
    @Override
    public void modificar(Integer nroHabitacion) {

        Habitacion habitacionModificada = buscarPorNumeroHabitacion(nroHabitacion);
        int indiceHabitacionModificada = habitaciones.indexOf(habitacionModificada);

        int opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Número de Habitación");
            System.out.println("2. Estado Actual");
            System.out.println("3. Tipo de Habitación");
            System.out.println("4. Precio Diario");
            System.out.println("0. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    habitacionModificada.setNumeroHabitacion(pedirNumeroHabitacion(scanner));
                    System.out.println("Numero de habitacion modificado con éxito");
                    break;
                case 2:
                    habitacionModificada.setEstadoActual(pedirEstadoHabitacion(scanner));
                    System.out.println("Estado Actual modificado con éxito");
                    break;
                case 3:
                    habitacionModificada.setTipoHabitacion(pedirTipoHabitacion(scanner));
                    System.out.println("Tipo de habitacion modificado con éxito");
                    break;
                case 4:
                    habitacionModificada.setPrecioDiario(pedirPrecioDiario(scanner));
                    System.out.println("Precio diario modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo de la modificación.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println("\nQuiere realizar otra modificacion?");
            System.out.println("1.Si");
            System.out.println("0.No");
            opcion = scanner.nextInt();
            scanner.nextLine();
            //FALTA PODER MANEJAR LA DISPONIBILIDAD DE FECHAS
            //ACA VA LIMPIAR PANTALLA
        } while (opcion != 0);

        System.out.println("\nHabitacion modificada");
        System.out.println(habitacionModificada);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1.Si");
        System.out.println("2.No");

        int confirmar = scanner.nextInt();

        if (confirmar == 1) {
            habitaciones.set(indiceHabitacionModificada, habitacionModificada); // Actualiza la habitación en el arreglo
            System.out.println("Modificación completada.");
        } else {
            System.out.println("Modificacion cancelada");
        }
    }

    // Métodos para pedir cada atributo

    private Integer pedirNumeroHabitacion(Scanner scanner) {
        System.out.print("Ingrese el número de la habitación: ");
        return scanner.nextInt();
    }

    private EstadoHabitacion pedirEstadoHabitacion(Scanner scanner) {
        System.out.println("Seleccione el estado de la habitación:");
        System.out.println("1. Disponible");
        System.out.println("2. Limpieza");
        System.out.println("3. Reparación");
        System.out.println("4. Desinfección");
        System.out.println("5. Fumigación");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        switch (opcion) {
            case 1: return EstadoHabitacion.DISPONIBLE;
            case 2: return EstadoHabitacion.LIMPIEZA;
            case 3: return EstadoHabitacion.REPARACION;
            case 4: return EstadoHabitacion.DESINFECCION;
            case 5: return EstadoHabitacion.FUMIGACION;
            default: return EstadoHabitacion.DISPONIBLE; // Opción por defecto
        }
    }

    private TipoHabitacion pedirTipoHabitacion(Scanner scanner) {
        System.out.println("Seleccione el tipo de habitación:");
        System.out.println("1. Simple");
        System.out.println("2. Doble");
        System.out.println("3. Matrimonial");
        System.out.println("4. Suite");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        switch (opcion) {
            case 1: return TipoHabitacion.SIMPLE;
            case 2: return TipoHabitacion.DOBLE;
            case 3: return TipoHabitacion.MATRIMONIAL;
            case 4: return TipoHabitacion.SUITE;
            default: return TipoHabitacion.SIMPLE; // Opción por defecto
        }
    }

    private Double pedirPrecioDiario(Scanner scanner) {
        System.out.print("Ingrese el precio diario: ");
        return scanner.nextDouble();
    }

    private Habitacion buscarPorNumeroHabitacion(Integer numeroHabitacion) {
        for (Habitacion h : habitaciones) {
            if (h.getNumeroHabitacion().equals(numeroHabitacion)) {
                return h;
            }
        }
        return null;
    }
}
