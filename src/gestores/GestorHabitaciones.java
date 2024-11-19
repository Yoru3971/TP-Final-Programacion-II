package gestores;

import excepciones.*;
import modelos.Habitacion;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;
import java.util.ArrayList;

public class GestorHabitaciones implements IGestionable<Integer> {
    private ArrayList<Habitacion> habitaciones;

    //Constructores
    public GestorHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }
    public GestorHabitaciones() {
        habitaciones = new ArrayList<>();
    }

    //Getters y Setters
    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        System.out.println("Ingrese los datos de la nueva habitación: ");

        Integer numeroHabitacion = null;
        boolean numeroValido = false;
        Double precioDiario = null;
        boolean precioValido = false;

        do {
            try {
                numeroHabitacion = GestorEntradas.pedirEntero("Ingrese el número de la habitación: ");
                if(Verificador.verificarNumeroHabitacion(numeroHabitacion, habitaciones)){
                    numeroValido = true;
                }
            } catch (NumeroHabitacionInvalidoException e) {
                System.err.println(e.getMessage());
            } catch (HabitacionExistenteException e) {
                System.err.println(e.getMessage());
            }
        } while (!numeroValido);

        EstadoHabitacion estado = pedirEstadoHabitacion();

        TipoHabitacion tipo = pedirTipoHabitacion();

        do {
            try {
                precioDiario = GestorEntradas.pedirDouble("Ingrese el precio por dia: ");
                if(Verificador.verificarPrecioHabitacion(precioDiario)){
                    precioValido = true;
                }
            } catch (PrecioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!precioValido);

        Habitacion nuevaHabitacion = new Habitacion(numeroHabitacion, estado, tipo, precioDiario);
        habitaciones.add(nuevaHabitacion);
        System.out.println("Habitación agregada con éxito.");
    }

    @Override
    public void eliminar(Integer nroHabitacion) {
        try{
            Habitacion habitacionEliminar = buscarHabitacionPorNumero(nroHabitacion);
            if (!Verificador.verificarObjetoNulo(habitacionEliminar)){
                throw new ObjetoNuloException("Habitacion no encontrada");
            }
            int opcion;
            do{
                System.out.println("Confirmar eliminación de la habitación " + nroHabitacion + ": ");
                System.out.println("1. Sí");
                System.out.println("2. No");

                opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
                //GestorEntradas.limpiarBuffer();

                if (opcion == 1) {
                    habitaciones.remove(habitacionEliminar);
                    System.out.println("Habitación eliminada del sistema con éxito.");
                } else if (opcion == 2){
                    System.out.println("La habitación no fue eliminada del sistema.");
                }else{
                    System.out.println("Numero ingresado incorrecto, ingrese 1 para eliminar o 2 para cancelar");
                }
            }while (opcion < 1 && opcion > 2);
        }catch(ObjetoNuloException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modificar(Integer nroHabitacion) {
        Habitacion habitacionModificar = buscarHabitacionPorNumero(nroHabitacion);
        int indiceHabitacionModificar = habitaciones.indexOf(habitacionModificar);

        boolean numeroValido = false;
        boolean precioValido = false;

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
                case 1->{
                    do {
                        try {
                            Integer numeroHabitacion = GestorEntradas.pedirEntero("Ingrese el nuevo número de la habitación: ");

                            if(Verificador.verificarNumeroHabitacion(numeroHabitacion, habitaciones)){
                                numeroValido = true;
                                habitacionModificar.setNumeroHabitacion(numeroHabitacion);
                            }
                        } catch (NumeroHabitacionInvalidoException e) {
                            System.err.println(e.getMessage());
                        } catch (HabitacionExistenteException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!numeroValido);
                    System.out.println("Número de habitación modificado con éxito");
                }
                case 2->{
                    habitacionModificar.setEstadoActual(pedirEstadoHabitacion());
                    System.out.println("Estado actual modificado con éxito");
                }
                case 3->{
                    habitacionModificar.setTipoHabitacion(pedirTipoHabitacion());
                    System.out.println("Tipo de habitación modificado con éxito");
                }
                case 4->{
                    do {
                        try {
                            Double precioDiario = GestorEntradas.pedirDouble("Ingrese el precio por dia: ");
                            if(Verificador.verificarPrecioHabitacion(precioDiario)){
                                habitacionModificar.setPrecioDiario(precioDiario);
                                precioValido = true;
                            }
                        } catch (PrecioInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!precioValido);
                    System.out.println("Precio por dia modificado con éxito");
                }
                case 0->{
                    System.out.println("Saliendo...");
                }
                default->{
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
            System.out.println("\n¿Quiere realizar otra modificación?");
            System.out.println("1. Sí");
            System.out.println("0. No");
            opcion = GestorEntradas.pedirEntero("Ingrese opción: ");
        } while (opcion != 0);

        System.out.println("\nHabitacion modificada");
        System.out.println(habitacionModificar);

        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int confirmar = GestorEntradas.pedirEntero("Ingrese opción: ");
        //ACA SE PUEDE LANZAR UNA OPCION NO VALIDA EXCEPTION
        if (confirmar == 1) {
            habitaciones.set(indiceHabitacionModificar, habitacionModificar);
            System.out.println("Modificación completada con éxito");
        } else {
            System.out.println("Modificación cancelada");
        }
    }

    @Override
    public void listar() {
        System.out.println("\nLista de Habitaciones");
        System.out.println("=========================");

        try{
            if (Verificador.verificarArregloVacio(habitaciones)){
                for (Habitacion habitacion : habitaciones){
                    System.out.println(habitacion);
                }
            }
        } catch (ArregloVacioException e) {
            System.out.println(e.getMessage());
        }
    }

    //Metodos para pedir atributos unicos de Habitacion
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

    //Metodo para ver disponibilidad de habitacion
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
}