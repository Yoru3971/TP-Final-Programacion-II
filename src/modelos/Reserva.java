package modelos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Reserva {
    private final String codigo;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double montoTotal;

    public Reserva(Habitacion habitacion, Cliente cliente, LocalDate checkIn, LocalDate checkOut) {
        this.codigo = UUID.randomUUID().toString().toUpperCase();
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.montoTotal = calcularMontoTotal(checkIn,checkOut,habitacion);
    }

    public Double calcularMontoTotal(LocalDate checkIn, LocalDate checkOut, Habitacion habitacion) {
        int cantidadDias = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return cantidadDias * habitacion.getPrecioDiario();
    }


    public void hacerCheckIn() {
        LocalDate inicio = checkIn;
        LocalDate fin = checkOut;

        // Bucle para recorrer los días entre checkIn y checkOut
        while (!inicio.isAfter(fin)) {
            int day = inicio.getDayOfMonth();
            if (habitacion.getDisponibilidadReserva().containsKey(day)) {
                habitacion.getDisponibilidadReserva().put(day, true); // Marcar el día como ocupado
            }
            inicio = inicio.plusDays(1);

            // Cambiar al siguiente mes si es necesario
            if (inicio.getDayOfMonth() == 1) {
                habitacion = actualizarDisponibilidadParaNuevoMes(habitacion);
            }
        }
    }

    public void hacerCheckOut() {
        LocalDate inicio = checkIn;
        LocalDate fin = checkOut;

        // Bucle para recorrer los días entre checkIn y checkOut
        while (!inicio.isAfter(fin)) {
            int day = inicio.getDayOfMonth();
            if (habitacion.getDisponibilidadReserva().containsKey(day)) {
                habitacion.getDisponibilidadReserva().put(day, false); // Marcar el día como disponible
            }
            inicio = inicio.plusDays(1);

            // Cambiar al siguiente mes si es necesario
            if (inicio.getDayOfMonth() == 1) {
                habitacion = actualizarDisponibilidadParaNuevoMes(habitacion);
            }
        }
    }

    // Metodo auxiliar para preparar la disponibilidad de una nueva habitación al cambiar de mes
    private Habitacion actualizarDisponibilidadParaNuevoMes(Habitacion habitacion) {
        HashMap<Integer, Boolean> nuevaDisponibilidad = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            nuevaDisponibilidad.put(i, false); // Asumimos que todos los días del nuevo mes están disponibles inicialmente
        }
        habitacion.setDisponibilidadReserva(nuevaDisponibilidad);
        return habitacion;
    }

    //getters y setters
    public String getCodigo() {
        return codigo;
    }
    public Habitacion getHabitacion() {
        return habitacion;
    }
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }
    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }
    public LocalDate getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
    public Double getMontoTotal() {
        return montoTotal;
    }
    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    //equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(cliente, reserva.cliente) && Objects.equals(checkIn, reserva.checkIn) && Objects.equals(checkOut, reserva.checkOut) && Objects.equals(montoTotal, reserva.montoTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, cliente, checkIn, checkOut, montoTotal);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "habitacion=" + habitacion +
                ", cliente=" + cliente +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", montoTotal=" + montoTotal +
                '}';
    }
}
