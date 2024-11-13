package gestores;

import modelos.Habitacion;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

public class GestorHabitaciones implements IGestionable<Habitacion> {
    private ArrayList<Habitacion> habitaciones;
    private Scanner scanner = new Scanner(System.in);

    public GestorHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public GestorHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    //Para agregar y eliminar, queda pendiente agregar verificaciones de habitacion repetida/no encontrada
    @Override
    public void agregar(Habitacion habitacion) {

        habitaciones.add(habitacion);

        System.out.println("Habitacion agregada con éxito.");
    }

    @Override
    public void eliminar(Habitacion habitacion) {
        habitaciones.remove(habitacion);
        System.out.println("Habitacion eliminada con éxito.");
    }

    @Override
    public void listar() {
        System.out.println("\nLista de Habitaciones");
        System.out.println("=========================");
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas.");
        } else {
            for (Habitacion h : habitaciones) {
                System.out.println(h);
            }
        }
    }

    //En todos los caso hay que agregar verificaciones, habitacion no repetida, fecha no repetida, etc
    @Override
    public void modificar(Habitacion habitacion) {
        int index = buscarIndiceHabitacion(habitacion);

        Habitacion habitacionModificada = habitaciones.get(index);

        int opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Número de Habitación");
            System.out.println("2. Disponibilidad");
            System.out.println("3. Estado Actual");
            System.out.println("4. Tipo de Habitación");
            System.out.println("5. Precio Diario");
            System.out.println("0. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nuevo número de habitación: ");
                    int nuevoNumero = scanner.nextInt();
                    scanner.nextLine();
                    habitacionModificada.setNumeroHabitacion(nuevoNumero);

                    //Al final de cada case, mostrar un mensaje de cambio guardado con exito
                    break;
                case 2:
                    System.out.print("Ingrese la fecha para cambiar disponibilidad (YYYY-MM-DD HH:MM): ");
                    String fechaStr = scanner.nextLine();
                    LocalDateTime fecha = LocalDateTime.parse(fechaStr);
                    System.out.println("Seleccione disponibilidad:");
                    System.out.println("1. Activa");
                    System.out.println("2. Inactiva");
                    int disponibilidadOpcion = scanner.nextInt();
                    scanner.nextLine();

                    boolean disponible = disponibilidadOpcion == 1; //Si disponiblidadOpcion es igual a 1, disponible es true, caso contrario, es false

                    habitacionModificada.getDisponibilidadReserva().put(fecha, disponible);
                    break;
                case 3:
                    System.out.println("Estado actual: " + habitacion.getEstadoActual());
                    System.out.println("Seleccione el nuevo estado:");
                    System.out.println("1. Limpieza");
                    System.out.println("2. Reparación");
                    System.out.println("3. Desinfección");
                    System.out.println("4. Fumigación");

                    int opcionEstado = scanner.nextInt();
                    if (opcionEstado == 1) {
                        habitacion.setEstadoActual(EstadoHabitacion.LIMPIEZA);
                    } else if (opcionEstado == 2) {
                        habitacion.setEstadoActual(EstadoHabitacion.REPARACION);
                    } else if (opcionEstado == 3) {
                        habitacion.setEstadoActual(EstadoHabitacion.DESINFECCION);
                    } else if (opcionEstado == 4) {
                        habitacion.setEstadoActual(EstadoHabitacion.FUMIGACION);
                    } else {
                        System.out.println("Opción no válida. No se realizó ningún cambio.");
                    }
                    break;
                case 4:
                    System.out.println("Tipo de habitación actual: " + habitacion.getTipoHabitacion());
                    System.out.println("Seleccione el nuevo tipo:");
                    System.out.println("1. Simple");
                    System.out.println("2. Doble");
                    System.out.println("3. Matrimonial");
                    System.out.println("4. Suite");

                    int opcionTipo = scanner.nextInt();
                    if (opcionTipo == 1) {
                        habitacion.setTipoHabitacion(TipoHabitacion.SIMPLE);
                    } else if (opcionTipo == 2) {
                        habitacion.setTipoHabitacion(TipoHabitacion.DOBLE);
                    } else if (opcionTipo == 3) {
                        habitacion.setTipoHabitacion(TipoHabitacion.MATRIMONIAL);
                    } else if (opcionTipo == 4) {
                        habitacion.setTipoHabitacion(TipoHabitacion.SUITE);
                    } else {
                        System.out.println("Opción no válida. No se realizó ningún cambio.");
                    }
                    break;
                case 5:
                    System.out.print("Ingrese nuevo precio diario: ");
                    double nuevoPrecio = scanner.nextDouble();
                    scanner.nextLine();

                    habitacionModificada.setPrecioDiario(nuevoPrecio);
                    break;
                case 0:
                    System.out.println("Saliendo de la modificación.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            //ACA VA LIMPIAR PANTALLA
            //ACA preguntar si quiere hacer otra modificacion
        } while (opcion != 0);

        //Agregar confirmacion
        habitaciones.set(index, habitacionModificada); // Actualiza la habitación en el arreglo
        System.out.println("Modificación completada.");
    }

    private int buscarIndiceHabitacion(Habitacion habitacion) {
        for(Habitacion h : habitaciones){
            if(h.equals(habitacion)){
                return habitaciones.indexOf(h);
            }
        }
        return -1;
    }

    public Habitacion buscarHabitacionPorNumero(int numero) {
        for(Habitacion h : habitaciones){
            if(h.getNumeroHabitacion()==numero){
                return h;
            }
        }
        return null;
    }
}
