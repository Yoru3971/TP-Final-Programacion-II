package modelos;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reserva {
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Double montoTotal;

    public Reserva(Habitacion habitacion, Cliente cliente, LocalDateTime checkIn, LocalDateTime checkOut) {
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.montoTotal = calcularMontoTotal(checkIn,checkOut,habitacion);
    }

    public Double calcularMontoTotal(LocalDateTime checkIn, LocalDateTime checkOut, Habitacion habitacion){
        long cantidadDias = ChronoUnit.DAYS.between(checkIn, checkOut);
        return cantidadDias * habitacion.getPrecioDiario();
    }

    public void hacerCheckIn(){
        //Stand By
    }

    public void hacerCheckOut(){
        //Stand By
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

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

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
