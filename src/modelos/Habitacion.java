package modelos;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Objects;

import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;

public class Habitacion {
    private Integer numeroHabitacion;
    private ArrayList<LocalDate> fechasReservadas; // Solo almacenará fechas reservadas
    private EstadoHabitacion estadoActual;
    private TipoHabitacion tipoHabitacion;
    private Double precioDiario;

    //Constructores
    public Habitacion() {
        this.fechasReservadas = new ArrayList<>();
    }
    public Habitacion(Integer numeroHabitacion, EstadoHabitacion estadoActual, TipoHabitacion tipoHabitacion, Double precioDiario) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoActual = estadoActual;
        this.tipoHabitacion = tipoHabitacion;
        this.precioDiario = precioDiario;
        this.fechasReservadas = new ArrayList<>();
    }

    //Metodo para verificar la disponibilidad de un día específico
    public boolean isDisponible(LocalDate fecha) {
        // Si la fecha NO está en el conjunto, entonces está disponible
        return !fechasReservadas.contains(fecha);
    }

    //Metodo para reservar un día
    public void reservarDia(LocalDate fecha) {
        if (isDisponible(fecha)) {
            fechasReservadas.add(fecha); // Agrega la fecha al conjunto
        }
    }

    //Metodo para cancelar la reserva de un dia
    public void liberarDia(LocalDate fecha) {
        fechasReservadas.remove(fecha); // Elimina la fecha del conjunto
    }

    //Metodo para mostrar disponibilidad en formato calendario
    public void mostrarCalendario() {
        /*Este metodo solo muestra la disponibilidad del mes que toma con el metodo now()
        En un futuro se deberia implementar que el cliente consulte disponibilidad de X fecha
        y este metodo la reciba como parametro, para mostrar el calendario de esos meses...*/
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
            if (fechasReservadas.contains(fechaActual)) {
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

    //Metodo para mostrar disponibilidad en formato calendario de los ultimos 12 meses sin colores
    public void mostrarCalendario12Meses() {
        LocalDate hoy = LocalDate.now();

        final String resetColor = "\u001B[0m";
        final String colorAmarillo = "\u001B[93m";
        final String colorVerde = "\u001B[92m";
        final String colorRojo = "\u001B[91m";

        System.out.println(colorAmarillo+"\n  Calendario de los próximos 12 meses para la Habitación: " + this.numeroHabitacion+resetColor);
        for (int mesOffset = 0; mesOffset < 12; mesOffset++) {
            YearMonth mesActual = YearMonth.from(hoy).plusMonths(mesOffset);
            System.out.println("\n  " + mesActual.getMonth() + " " + mesActual.getYear());
            System.out.println("  Lu Ma Mi Ju Vi Sa Do");

            // Imprimo los días del mes
            int diasEnMes = mesActual.lengthOfMonth();
            LocalDate primerDia = mesActual.atDay(1);
            int primerDiaSemana = primerDia.getDayOfWeek().getValue();

            System.out.print("  ");
            for (int i = 1; i < primerDiaSemana; i++) {
                System.out.print("   ");
            }

            for (int dia = 1; dia <= diasEnMes; dia++) {
                LocalDate fechaActual = mesActual.atDay(dia);

                // Se imprime el día en rojo si está ocupado y en verde si está disponible
                if (fechasReservadas.contains(fechaActual)) {
                    System.out.printf(colorRojo + "%2d " + resetColor, dia);
                } else {
                    System.out.printf(colorVerde + "%2d " + resetColor, dia);
                }

                // Salta a la siguiente línea al final de la semana
                if ((dia + primerDiaSemana - 1) % 7 == 0) {
                    System.out.println();
                    System.out.print("  ");
                }
            }
            System.out.println();
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
                if (fechasReservadas.contains(fechaActual)) {
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
    public ArrayList<LocalDate> getFechasReservadas() {
        return fechasReservadas;
    }
    public void setFechasReservadas(ArrayList<LocalDate> fechasReservadas) {
        this.fechasReservadas = fechasReservadas;
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
        String colorAzul = "\u001B[94m";
        String colorVerde = "\u001B[92m";
        String resetColor = "\u001B[0m";

        return "[" + colorAzul + "Habitación "+ resetColor+" - " +
                "Nro: " +colorVerde + numeroHabitacion + resetColor +
                ", Tipo: " + colorVerde + tipoHabitacion + resetColor +
                ", Estado: " + colorVerde + estadoActual + resetColor + "]]";
    }
}