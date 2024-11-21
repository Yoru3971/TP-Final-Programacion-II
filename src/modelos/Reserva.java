package modelos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reserva {
    private static int contadorID = 0;
    private final Integer ID;
    private Habitacion habitacion;
    private Cliente cliente;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double montoTotal;

    //Constructores
    public Reserva(Habitacion habitacion, Cliente cliente, LocalDate checkIn, LocalDate checkOut) {
        this.ID = contadorID++;
        this.habitacion = habitacion;
        this.cliente = cliente;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.montoTotal = calcularMontoTotal(checkIn, checkOut, habitacion);
    }

    //Metodo para calcular el Monto de la Reserva
    public Double calcularMontoTotal(LocalDate checkIn, LocalDate checkOut, Habitacion habitacion) {
        int cantidadDias = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        double total = cantidadDias * habitacion.getPrecioDiario();

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
    public Integer getID() {
        return ID;
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
        return Objects.equals(ID, reserva.ID) && Objects.equals(habitacion, reserva.habitacion) && Objects.equals(cliente, reserva.cliente);
    }
    @Override
    public int hashCode() {
        return Objects.hash(ID, habitacion, cliente);
    }
    @Override
    public String toString() {
        String colorAzul = "\u001B[94m";
        String colorVerde = "\u001B[92m";
        String resetColor = "\u001B[0m";

        return "[" + colorAzul + "Reserva" + resetColor + " - " +
                "Código: " + colorVerde + ID + resetColor +
                ", N° Habitación: " + colorVerde + habitacion.getNumeroHabitacion() + resetColor +
                ", DNI Cliente: " + colorVerde + cliente.getDni() + resetColor +
                ", Fecha Inicio: " + colorVerde + checkIn + resetColor +
                ", Fecha Fin: " + colorVerde + checkOut + resetColor +
                ", Monto Total: " + colorVerde + montoTotal + resetColor + "]";
    }
}