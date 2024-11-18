package modelos;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Objects;

import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;

public class Habitacion {
    private Integer numeroHabitacion;
    private HashSet<LocalDate> disponibilidadReserva; // Solo almacenará fechas reservadas
    private EstadoHabitacion estadoActual;
    private TipoHabitacion tipoHabitacion;
    private Double precioDiario;

    //Constructores
    public Habitacion(Integer numeroHabitacion, EstadoHabitacion estadoActual, TipoHabitacion tipoHabitacion, Double precioDiario) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoActual = estadoActual;
        this.tipoHabitacion = tipoHabitacion;
        this.precioDiario = precioDiario;
        this.disponibilidadReserva = new HashSet<>();
    }

    //Metodo para verificar la disponibilidad de un día específico
    public boolean isDisponible(LocalDate fecha) {
        // Si la fecha NO está en el conjunto, entonces está disponible
        return !disponibilidadReserva.contains(fecha);
    }

    //Metodo para reservar un día
    public boolean reservarDia(LocalDate fecha) {
        if (isDisponible(fecha)) {
            disponibilidadReserva.add(fecha); // Agrega la fecha al conjunto
            return true;
        }
        return false;
    }

    //Metodo para cancelar la reserva de un dia
    public void liberarDia(LocalDate fecha) {
        disponibilidadReserva.remove(fecha); // Elimina la fecha del conjunto
    }

    //Metodo para mostrar disponibilidad en formato calendario
    public void mostrarCalendario() {
        /*Este metodo solo muestra la disponibilidad del mes que toma con el metodo now()
        En un futuro se deberia implementar que el cliente consulte disponibilidad de X fecha
        y este metodo la reciba como parametro, para mostrar el calendario de esas fechas...*/
        ///Lo implementaremos unicamente si llegamos con el tiempo <3
        LocalDate hoy = LocalDate.now();
        YearMonth mesActual = YearMonth.of(hoy.getYear(), hoy.getMonth());
        int diasEnMes = mesActual.lengthOfMonth();

        System.out.println("\nCalendario del mes: " + hoy.getMonth() + " " + hoy.getYear());
        System.out.println("Lu Ma Mi Ju Vi Sa Do");

        LocalDate primerDia = mesActual.atDay(1);
        int primerDiaSemana = primerDia.getDayOfWeek().getValue();

        for (int i = 1; i < primerDiaSemana; i++) {
            System.out.print("   ");
        }

        // Se imprimen todos ls dias del mes
        for (int dia = 1; dia <= diasEnMes; dia++) {
            LocalDate fechaActual = mesActual.atDay(dia);

            // Si la fecha esta reservada se mostrara en rojo con "err" en el print, sino en blanco
            if (disponibilidadReserva.contains(fechaActual)) {
                System.err.printf("%2d ", dia);
            } else {
                System.out.printf("%2d ", dia);
            }

            // Salta a la siguiente línea al final de la semana
            if ((dia + primerDiaSemana - 1) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    //Metodo para mostrar disponibilidad en formato calendario de los ultimos 12 meses
    ///Es un tipo de solucion al metodo de arriba que solo mostraba la disponibilidad del ultimo mes
    ///Este metodo lo hizo chatgpt, no tiene la mecanica de mostrar unicamente la disponibilidad que solicita el usuario
    ///pero al mostrar los ultimos 12 meses es mas util que el anterior <3
    ///Nota: si este funciona a la perfeccion estaria bueno eliminar el de arriba q esta medio useless xd
    public void mostrarCalendarioProximos12MesesEnFilas() {
        LocalDate hoy = LocalDate.now();
        System.out.println("Calendario de la Habitacion: " + this.numeroHabitacion);

        int mesesPorFila = 4; // Mostraremos 4 meses por fila
        for (int fila = 0; fila < 3; fila++) {
            // Imprimimos la cabecera de los meses (nombres y años)
            for (int columna = 0; columna < mesesPorFila; columna++) {
                int mesOffset = fila * mesesPorFila + columna;
                YearMonth mesActual = YearMonth.from(hoy).plusMonths(mesOffset);
                System.out.printf("%-20s", mesActual.getMonth() + " " + mesActual.getYear());
            }
            System.out.println();

            // Imprimimos la cabecera de los días de la semana para cada mes
            for (int columna = 0; columna < mesesPorFila; columna++) {
                System.out.print("Lu Ma Mi Ju Vi Sa Do  ");
            }
            System.out.println();

            // Imprimimos las semanas de cada mes
            for (int semana = 0; semana < 6; semana++) { // Máximo de 6 semanas en un mes
                for (int columna = 0; columna < mesesPorFila; columna++) {
                    int mesOffset = fila * mesesPorFila + columna;
                    YearMonth mesActual = YearMonth.from(hoy).plusMonths(mesOffset);
                    imprimirSemana(mesActual, semana);
                    System.out.print("  "); // Espacio entre meses
                }
                System.out.println();
            }
            System.out.println(); // Espacio entre filas de meses
        }
    }

    //Metodo auxiliar para imprimir una semana de un mes específico
    private void imprimirSemana(YearMonth mesActual, int semana) {
        int diasEnMes = mesActual.lengthOfMonth();
        LocalDate primerDia = mesActual.atDay(1);
        int primerDiaSemana = primerDia.getDayOfWeek().getValue();
        int diaInicio = semana * 7 - primerDiaSemana + 2;

        for (int dia = diaInicio; dia < diaInicio + 7; dia++) {
            if (dia < 1 || dia > diasEnMes) {
                System.out.print("   "); // Espacio vacío para días fuera del mes
            } else {
                LocalDate fechaActual = mesActual.atDay(dia);
                // Comprobar si la fecha está reservada
                if (disponibilidadReserva.contains(fechaActual)) {
                    System.err.printf("%2d ", dia); // Día reservado en rojo
                } else {
                    System.out.printf("%2d ", dia); // Día disponible en blanco
                }
            }
        }
    }

    //Getters y Setters
    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }
    public void setNumeroHabitacion(Integer numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }
    public HashSet<LocalDate> getDisponibilidadReserva() {
        return disponibilidadReserva;
    }
    public void setDisponibilidadReserva(HashSet<LocalDate> disponibilidadReserva) {
        this.disponibilidadReserva = disponibilidadReserva;
    }
    public EstadoHabitacion getEstadoActual() {
        return estadoActual;
    }
    public void setEstadoActual(EstadoHabitacion estadoActual) {
        this.estadoActual = estadoActual;
    }
    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }
    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
    public Double getPrecioDiario() {
        return precioDiario;
    }
    public void setPrecioDiario(Double precioDiario) {
        this.precioDiario = precioDiario;
    }

    //equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(numeroHabitacion, that.numeroHabitacion) && tipoHabitacion == that.tipoHabitacion;
    }
    @Override
    public int hashCode() {
        return Objects.hash(numeroHabitacion, tipoHabitacion);
    }

    @Override
    public String toString() {
        String colorAzul = "\u001B[34m";
        String colorRojo = "\u001B[31m";
        String resetColor = "\u001B[0m";

        return "[" + colorAzul + "Habitación N° " + numeroHabitacion+ resetColor + ": " +
                "Tipo: " + colorRojo + tipoHabitacion + resetColor +
                ", Estado: " + colorAzul + estadoActual + resetColor + "]]";
    }
}