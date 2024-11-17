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

    //Constructores
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
        Double total = cantidadDias * habitacion.getPrecioDiario();

        if (cliente.getClienteVip()) {
            total -= 0.2 * total; // Descuento del 20% para cliente VIP
        }

        return total;
    }

    //Metodo para bloquear los días reservados
    public void reservarFechas() {
        LocalDate fecha = checkIn;
        while (!fecha.isAfter(checkOut)) {
            habitacion.reservarDia(fecha);
            fecha = fecha.plusDays(1);
        }
    }

    //Metodo para liberar los días reservados
    public void liberarFechas() {
        LocalDate fecha = checkIn;
        while (!fecha.isAfter(checkOut)) {
            habitacion.liberarDia(fecha);
            fecha = fecha.plusDays(1);
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
        return Objects.equals(codigo, reserva.codigo) && Objects.equals(habitacion, reserva.habitacion) && Objects.equals(cliente, reserva.cliente);
    }
    @Override
    public int hashCode() {
        return Objects.hash(codigo, habitacion, cliente);
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