package modelos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Reserva {
    private final String codigo;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double montoTotal;

    //Constructor
    public Reserva(Habitacion habitacion, Cliente cliente, LocalDate checkIn, LocalDate checkOut) {
        this.codigo = UUID.randomUUID().toString().toUpperCase();
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.montoTotal = calcularMontoTotal(checkIn, checkOut, habitacion);
    }

    //Metodo para calcular el Monto de la Reserva
    public Double calcularMontoTotal(LocalDate checkIn, LocalDate checkOut, Habitacion habitacion) {
        int cantidadDias = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return cantidadDias * habitacion.getPrecioDiario();
    }

    //Metodo para bloquear los días reservados (check-in)
    public void bloquearFechas() {
        LocalDate inicio = checkIn;
        LocalDate fin = checkOut;
        while (!inicio.isAfter(fin)) {
            if (habitacion.isDisponible(inicio)) {
                habitacion.reservarDia(inicio);
            }
            inicio = inicio.plusDays(1);
        }
    }

    //Metodo para liberar los días reservados (check-out)
    public void liberarFechas() {
        LocalDate inicio = checkIn;
        LocalDate fin = checkOut;
        while (!inicio.isAfter(fin)) {
            habitacion.cancelarReserva(inicio);
            inicio = inicio.plusDays(1);
        }
    }

    //Getters y Setters
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
        return Objects.equals(habitacion, reserva.habitacion) &&
                Objects.equals(cliente, reserva.cliente) &&
                Objects.equals(checkIn, reserva.checkIn) &&
                Objects.equals(checkOut, reserva.checkOut) &&
                Objects.equals(montoTotal, reserva.montoTotal);
    }
    @Override
    public int hashCode() {
        return Objects.hash(habitacion, cliente, checkIn, checkOut, montoTotal);
    }
    @Override
    public String toString() {
        return "Reserva{" +
                "codigo='" + codigo + '\'' +
                ", habitacion=" + habitacion +
                ", cliente=" + cliente +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", montoTotal=" + montoTotal +
                '}';
    }
}