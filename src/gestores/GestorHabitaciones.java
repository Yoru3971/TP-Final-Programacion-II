package gestores;

import modelos.Habitacion;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;
import java.util.ArrayList;

public class GestorHabitaciones implements IGestionable<Integer> {
    private ArrayList<Habitacion> habitaciones;

    //constructores
    public GestorHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
    public GestorHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    //getters y setters
    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    //implementacion metodos interfaz IGestionable
    @Override
    public void agregar() {
        System.out.println("Ingrese los datos de la nueva habitación: ");

        Integer numeroHabitacion = GestorEntradas.pedirEntero("Ingrese el número de la habitación: ");
        if (buscarHabitacionPorNumero(numeroHabitacion) != null) {
            System.out.println("El número de habitación ya existe.");
            return;
        }

        EstadoHabitacion estado = pedirEstadoHabitacion();
        TipoHabitacion tipo = pedirTipoHabitacion();
        Double precioDiario = GestorEntradas.pedirDouble("Ingrese el precio diario: ");
        GestorEntradas.limpiarBuffer();

        Habitacion nuevaHabitacion = new Habitacion(numeroHabitacion, estado, tipo, precioDiario);
        habitaciones.add(nuevaHabitacion);
        System.out.println("Habitación agregada con éxito.");
    }

    @Override
    public void eliminar(Integer nroHabitacion) {
        Habitacion habitacionEliminar = buscarHabitacionPorNumero(nroHabitacion);
        if (habitacionEliminar == null) {
            System.out.println("No se encontró la habitación con el número proporcionado.");
            return;
        }

        System.out.println("Confirmar eliminación de la habitación " + nroHabitacion + ": ");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
        GestorEntradas.limpiarBuffer();

        if (opcion == 1) {
            habitaciones.remove(habitacionEliminar);
            System.out.println("Habitación eliminada del sistema con éxito.");
        } else {
            System.out.println("La habitación no fue eliminada del sistema.");
        }
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

    @Override
    public void modificar(Integer nroHabitacion) {
        Habitacion habitacionModificada = buscarHabitacionPorNumero(nroHabitacion);
        if (habitacionModificada == null) {
            System.out.println("No se encontró la habitación con el número proporcionado.");
            return;
        }

        int indiceHabitacionModificada = habitaciones.indexOf(habitacionModificada);
        int opcion;

        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Número de Habitación");
            System.out.println("2. Estado Actual");
            System.out.println("3. Tipo de Habitación");
            System.out.println("4. Precio Diario");
            System.out.println("0. Salir");

            opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
            GestorEntradas.limpiarBuffer();

            switch (opcion) {
                case 1:
                    habitacionModificada.setNumeroHabitacion(GestorEntradas.pedirEntero("Ingrese el nuevo número de la habitación: "));
                    System.out.println("Número de habitación modificado con éxito");
                    break;
                case 2:
                    habitacionModificada.setEstadoActual(pedirEstadoHabitacion());
                    System.out.println("Estado Actual modificado con éxito");
                    break;
                case 3:
                    habitacionModificada.setTipoHabitacion(pedirTipoHabitacion());
                    System.out.println("Tipo de habitación modificado con éxito");
                    break;
                case 4:
                    habitacionModificada.setPrecioDiario(GestorEntradas.pedirDouble("Ingrese el nuevo precio diario: "));
                    System.out.println("Precio diario modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo de la modificación.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);

        habitaciones.set(indiceHabitacionModificada, habitacionModificada);
        System.out.println("Modificación completada.");
    }

    // Métodos para pedir atributos unicos de Habitacion
    private EstadoHabitacion pedirEstadoHabitacion() {
        System.out.println("Seleccione el estado de la habitación:");
        System.out.println("1. Disponible");
        System.out.println("2. Limpieza");
        System.out.println("3. Reparación");
        System.out.println("4. Desinfección");
        System.out.println("5. Fumigación");

        int opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
        GestorEntradas.limpiarBuffer();

        switch (opcion) {
            case 1: return EstadoHabitacion.DISPONIBLE;
            case 2: return EstadoHabitacion.LIMPIEZA;
            case 3: return EstadoHabitacion.REPARACION;
            case 4: return EstadoHabitacion.DESINFECCION;
            case 5: return EstadoHabitacion.FUMIGACION;
            default: return EstadoHabitacion.DISPONIBLE;
        }
    }

    private TipoHabitacion pedirTipoHabitacion() {
        System.out.println("Seleccione el tipo de habitación:");
        System.out.println("1. Simple");
        System.out.println("2. Doble");
        System.out.println("3. Matrimonial");
        System.out.println("4. Suite");

        int opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
        GestorEntradas.limpiarBuffer();

        switch (opcion) {
            case 1: return TipoHabitacion.SIMPLE;
            case 2: return TipoHabitacion.DOBLE;
            case 3: return TipoHabitacion.MATRIMONIAL;
            case 4: return TipoHabitacion.SUITE;
            default: return TipoHabitacion.SIMPLE;
        }
    }

    // Métodos de busqueda
    public Habitacion buscarHabitacionPorNumero(Integer numeroHabitacion) {
        for(Habitacion h : habitaciones){
            if(h.getNumeroHabitacion().equals(numeroHabitacion)){
                return h;
            }
        }
        return null;
    }
}