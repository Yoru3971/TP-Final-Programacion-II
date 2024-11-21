package gestores;

import excepciones.*;
import modelos.Habitacion;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;
import java.util.ArrayList;

public class GestorHabitaciones implements IGestionable<Integer> {
    //Constantes para implementar color como en los menu
    private final String colorAmarillo = "\u001B[93m";
    private final String colorVerde = "\u001B[92m";
    private final String colorRojo = "\u001B[91m";
    private final String resetColor = "\u001B[0m";

    //Aca se guardan las habitaciones, luego esto es guardado en el archivo, se hace desde el menu
    private ArrayList<Habitacion> habitaciones;

    //Constructores
    //Este recibe un arreglo (levantado de un archivo)
    public GestorHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    //Default en caso de querer crear un arreglo de personas desde cero
    public GestorHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    //Getters y Setters, usados para modificar el arreglo levantado
    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        GestorEntradas.limpiarConsola();
        //Todos los datos tienen su propio metodo que pide en bucle que se ingresen de forma correcta los datos, hasta no ser correctamente ingresados, no se avanza al siguiente
        //Aca podriamos dejar que corte en cualquier momento, pero estar constantemente preguntando si queres seguir cargando datos nos parecio un poco molesto para el usuario

        Habitacion nuevaHabitacion = new Habitacion();
        System.out.println("\n  Ingrese los datos de la nueva habitación: ");

        pedirNroHabitacion(nuevaHabitacion, "  Ingrese el numero: ");
        nuevaHabitacion.setEstadoActual(EstadoHabitacion.DISPONIBLE);
        nuevaHabitacion.setTipoHabitacion(pedirTipoHabitacion());
        pedirPrecioDiario(nuevaHabitacion, "  Ingrese el precio por día: ");

        System.out.println("\n  Datos de la habitación a cargar:");
        System.out.println(nuevaHabitacion);
        System.out.println("\n  ¿Desea confirmar?\n  1. Sí \n  2. No\n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            habitaciones.add(nuevaHabitacion);
            System.out.println(colorVerde+"\n  Habitación agregada con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Carga de habitación cancelada."+resetColor);
        }
    }

    @Override
    public void eliminar(Integer nroHabitacion) {
        Habitacion habitacionEliminar = buscarHabitacionPorNumero(nroHabitacion);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no una habitacion con ese numero

        if (habitacionEliminar == null) {
            System.out.println("\n  Habitación no encontrada.");
            return;
        }

        System.out.println("\n  Datos de la habitación a eliminar:");
        System.out.println(habitacionEliminar);
        System.out.println("\n  ¿Desea confirmar la eliminación?\n  1. Sí \n  2. No \n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            habitaciones.remove(habitacionEliminar);
            System.out.println(colorVerde+"\n  Eliminación completada con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Eliminación cancelada."+resetColor);
        }
    }

    @Override
    public void modificar(Integer nroHabitacion) {
        Habitacion habitacionModificar = buscarHabitacionPorNumero(nroHabitacion);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no una habitacion con ese numero
        if (habitacionModificar == null) {
            System.out.println(colorRojo + "\n  Habitacion no encontrada." + resetColor);
            return;
        }

        int indiceHabitacionModificar = habitaciones.indexOf(habitacionModificar);

        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n  Datos de la habitación a modificar:");
            System.out.println(habitacionModificar);

            System.out.println("\n  ¿Qué desea modificar?");
            System.out.println("  1. Número de habitación");
            System.out.println("  2. Estado actual");
            System.out.println("  3. Tipo de habitación");
            System.out.println("  4. Precio diario");
            System.out.println("  0. Salir");

            opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

            switch (opcion) {
                case "1" -> {
                    pedirNroHabitacion(habitacionModificar, "  Ingrese el nuevo número de la habitación: ");
                    System.out.println(colorVerde + "  Número de habitación modificado con éxito" + resetColor);
                }
                case "2" -> {
                    habitacionModificar.setEstadoActual(pedirEstadoHabitacion());
                    System.out.println(colorVerde + "  Estado actual modificado con éxito" + resetColor);
                }
                case "3" -> {
                    habitacionModificar.setTipoHabitacion(pedirTipoHabitacion());
                    System.out.println(colorVerde + "  Tipo de habitación modificado con éxito" + resetColor);
                }
                case "4" -> {
                    pedirPrecioDiario(habitacionModificar, "  Ingrese el nuevo precio por día: ");
                    System.out.println(colorVerde + "  Precio por día modificado con éxito" + resetColor);
                }
                case "0" -> {
                    System.out.println("  Saliendo...");
                }
                default -> {
                    System.out.println(colorRojo + "  Opción no válida. Intente nuevamente." + resetColor);
                }
            }

            System.out.println("\n  ¿Quiere realizar otra modificación?\n  1. Sí \n  2. No\n");
            opcion = GestorEntradas.pedirCadena("  Ingrese opción: ");
        } while (!opcion.equals("0"));

        System.out.println("\n  Habitación modificada:");
        System.out.println(habitacionModificar);
        System.out.println("\n  ¿Desea confirmar los cambios?\n  1. Sí \n  2. No\n");

        opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            habitaciones.set(indiceHabitacionModificar, habitacionModificar);
            System.out.println(colorVerde + "\n  Modificación completada con éxito." + resetColor);
        } else {
            System.out.println(colorRojo + "\n  Modificación cancelada." + resetColor);
        }
    }

    @Override
    public void listar() {
        System.out.println(colorAmarillo + "\n  Lista de Habitaciones" + resetColor);
        System.out.println(colorAmarillo + "  =========================" + resetColor);

        if (!habitaciones.isEmpty()) {
            for (Habitacion h : habitaciones) {
                System.out.println("  "+h);
            }
        } else {
            System.out.println(colorRojo + "  No hay habitaciones registradas." + resetColor);
        }
    }

    //Metodos para listar segun distintos criterios
    public void listarHabitacionesPrecio(Integer precioIn, Integer precioFin) {
        System.out.println(colorAmarillo + "\n  Lista de Habitaciones con Precio entre " + precioIn + " y " + precioFin + resetColor);
        System.out.println(colorAmarillo + "  =========================" + resetColor);

        if (!habitaciones.isEmpty()) {
            boolean hayDisponibles = false;
            for (Habitacion h : habitaciones) {
                if (h.getPrecioDiario() >= precioIn && h.getPrecioDiario() <= precioFin) {
                    System.out.println("  "+h);
                    hayDisponibles = true;
                }
            }
            if (!hayDisponibles) {
                System.out.println(colorRojo + "  No hay habitaciones en ese rango de precio." + resetColor);
            }
        } else {
            System.out.println(colorRojo + "  No hay habitaciones registradas." + resetColor);
        }
    }

    public void listarHabitacionesDisponibles() {
        System.out.println(colorAmarillo + "\n  Lista de Habitaciones Disponibles" + resetColor);
        System.out.println(colorAmarillo + "  =========================" + resetColor);

        if (!habitaciones.isEmpty()) {
            boolean hayDisponibles = false;
            for (Habitacion h : habitaciones) {
                if (h.getEstadoActual().equals(EstadoHabitacion.DISPONIBLE)) {
                    System.out.println("  "+h);
                    hayDisponibles = true;
                }
            }
            if (!hayDisponibles) {
                System.out.println(colorRojo + "  No hay habitaciones disponibles en este momento." + resetColor);
            }
        } else {
            System.out.println(colorRojo + "  No hay habitaciones registradas." + resetColor);
        }
    }

    public void listarHabitacionesNoDisponibles() {
        System.out.println(colorAmarillo + "\n  Lista de Habitaciones No Disponibles" + resetColor);
        System.out.println(colorAmarillo + "  =========================" + resetColor);

        if (!habitaciones.isEmpty()) {
            boolean hayOcupadas = false;
            for (Habitacion h : habitaciones) {
                if (!h.getEstadoActual().equals(EstadoHabitacion.DISPONIBLE)) {
                    System.out.println("  "+h);
                    hayOcupadas = true;
                }
            }
            if (!hayOcupadas) {
                System.out.println(colorRojo + "  Todas las habitaciones están disponibles." + resetColor);
            }
        } else {
            System.out.println(colorRojo + "  No hay habitaciones registradas." + resetColor);
        }
    }

    public void listarHabitacionesPorTipo() {
        TipoHabitacion tipo = pedirTipoHabitacion();
        boolean hayHabitaciones = false;
        int contador = 1;

        if (tipo == null) {
            System.out.println(colorRojo + "  El tipo de habitación proporcionado no es válido." + resetColor);
            return;
        }

        if (habitaciones == null || habitaciones.isEmpty()) {
            System.out.println(colorRojo + "  No hay habitaciones registradas en el sistema." + resetColor);
            return;
        }

        System.out.println(colorAmarillo + "\n  Habitaciones con tipo: " + tipo + resetColor);
        System.out.println(colorAmarillo + "  ====================================" + resetColor);

        for (Habitacion h : habitaciones) {
            if (h.getTipoHabitacion().equals(tipo)) {
                System.out.println("  "+h);
                hayHabitaciones = true;
                contador++;
            }
        }

        if (!hayHabitaciones) {
            System.out.println(colorRojo + "  No se encontraron habitaciones con el tipo: " + tipo + resetColor);
        }
    }

    public void modificarEstado(Integer nroHabitacion) {
        Habitacion habitacionModificar = buscarHabitacionPorNumero(nroHabitacion);

        if (habitacionModificar == null) {
            System.out.println("La habitacion con el numero dado no existe.");
            return;
        }

        EstadoHabitacion nuevoEstado = pedirEstadoHabitacion();

        System.out.println("¿Esta seguro que quiere modificar el estado de la habitacion?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int opcionConfirmacion = GestorEntradas.pedirEntero("Seleccione una opción:");

        if (opcionConfirmacion == 1) {
            habitacionModificar.setEstadoActual(nuevoEstado);
            int indiceHabitacionModificar = habitaciones.indexOf(habitacionModificar);
            habitaciones.set(indiceHabitacionModificar, habitacionModificar);
            System.out.println("Estado modificado con Exito.");
        } else {
            System.out.println("Cambio de estado cancelado.");
        }
    }

    public void verDisponibilidad (Integer numeroHabitacion) {
        Habitacion habitacion = buscarHabitacionPorNumero(numeroHabitacion);
        habitacion.mostrarCalendario12Meses();
    }

    //Metodos de busqueda
    public Habitacion buscarHabitacionPorNumero(Integer numeroHabitacion) {
        for(Habitacion h : habitaciones){
            if(h.getNumeroHabitacion().equals(numeroHabitacion)){
                return h;
            }
        }
        return null;
    }

    //Metodos especificos de Habitacion para pedir datos
    //Tienen la misma estructura que los de GestorPersonas
    private EstadoHabitacion pedirEstadoHabitacion() {
        System.out.println("  Seleccione el estado de la habitación:");
        System.out.println("  1. Disponible");
        System.out.println("  2. Limpieza");
        System.out.println("  3. Reparación");
        System.out.println("  4. Desinfección");
        System.out.println("  5. Fumigación");

        String opcion = GestorEntradas.pedirCadena("  Seleccione una opción: ");

        return switch (opcion) {
            case "1" -> EstadoHabitacion.DISPONIBLE;
            case "2" -> EstadoHabitacion.LIMPIEZA;
            case "3" -> EstadoHabitacion.REPARACION;
            case "4" -> EstadoHabitacion.DESINFECCION;
            case "5" -> EstadoHabitacion.FUMIGACION;
            default -> EstadoHabitacion.DISPONIBLE;
        };
    }

    private TipoHabitacion pedirTipoHabitacion() {
        System.out.println("  Seleccione el tipo de habitación:");
        System.out.println("  1. Simple");
        System.out.println("  2. Doble");
        System.out.println("  3. Matrimonial");
        System.out.println("  4. Suite");

        String opcion = GestorEntradas.pedirCadena("  Seleccione una opción: ");

        return switch (opcion) {
            case "1" -> TipoHabitacion.SIMPLE;
            case "2" -> TipoHabitacion.DOBLE;
            case "3" -> TipoHabitacion.MATRIMONIAL;
            case "4" -> TipoHabitacion.SUITE;
            default -> TipoHabitacion.SIMPLE;
        };
    }

    private void pedirNroHabitacion(Habitacion habitacion, String mensaje){
        boolean numeroValido = false;
        do {
            try {
                Integer numeroHabitacion = GestorEntradas.pedirEntero(mensaje);
                if(Verificador.verificarNumeroHabitacion(numeroHabitacion, habitaciones)){
                    habitacion.setNumeroHabitacion(numeroHabitacion);
                    numeroValido = true;
                }
            } catch (NumeroHabitacionInvalidoException | HabitacionExistenteException e) {
                System.out.println(colorRojo+e.getMessage()+resetColor);
            }
        } while (!numeroValido);
    }

    private void pedirPrecioDiario(Habitacion habitacion, String mensaje){
        boolean precioValido = false;
        do {
            try {
                Double precioDiario = GestorEntradas.pedirDouble(mensaje);
                if(Verificador.verificarPrecioHabitacion(precioDiario)){
                    habitacion.setPrecioDiario(precioDiario);
                    precioValido = true;
                }
            } catch (PrecioInvalidoException e) {
                System.out.println(colorRojo+e.getMessage()+resetColor);
            }
        } while (!precioValido);
    }

    public Habitacion pedirHabitacion(){
        Habitacion habitacion = new Habitacion();
        boolean habitacionEncontrada = false;

        do{
            habitacion = buscarHabitacionPorNumero(GestorEntradas.pedirEntero("\n  Ingrese el numero de la habitacion a reservar: "));

            if(habitacion == null){
                System.out.println("  Habitacion no encontrada. Intente de nuevo");
            }else{
                habitacionEncontrada = true;
            }
        }while(!habitacionEncontrada);
        return habitacion;
    }
}