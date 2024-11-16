package modelos;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;

public class Habitacion {
    private Integer numeroHabitacion;
    private HashMap<LocalDate, Boolean> disponibilidadReserva; // <fecha, disponibilidad>
    private EstadoHabitacion estadoActual;
    private TipoHabitacion tipoHabitacion;
    private Double precioDiario;

    // Constructor
    public Habitacion(Integer numeroHabitacion, EstadoHabitacion estadoActual, TipoHabitacion tipoHabitacion, Double precioDiario) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoActual = estadoActual;
        this.tipoHabitacion = tipoHabitacion;
        this.precioDiario = precioDiario;
        this.disponibilidadReserva = new HashMap<>();

        // Inicializamos la disponibilidad para los próximos 30 días como disponibles (true)
        LocalDate hoy = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            LocalDate fecha = hoy.plusDays(i);
            disponibilidadReserva.put(fecha, true);
        }
    }

    // Metodo para verificar la disponibilidad de un día específico
    public boolean isDisponible(LocalDate fecha) {
        return disponibilidadReserva.getOrDefault(fecha, false);
    }

    // Metodo para reservar un día
    public boolean reservarDia(LocalDate fecha) {
        if (isDisponible(fecha)) {
            disponibilidadReserva.put(fecha, false);
            return true;
        }
        return false;
    }

    //Metodo para cancelar una reserva
    public void cancelarReserva(LocalDate fecha) {
        disponibilidadReserva.put(fecha, true);
    }

    // Getters y Setters
    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }
    public void setNumeroHabitacion(Integer numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
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
    public HashMap<LocalDate, Boolean> getDisponibilidadReserva() {
        return disponibilidadReserva;
    }
    public void setDisponibilidadReserva(HashMap<LocalDate, Boolean> disponibilidadReserva) {
        this.disponibilidadReserva = disponibilidadReserva;
    }

    //equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(numeroHabitacion, that.numeroHabitacion);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(numeroHabitacion);
    }
    @Override
    public String toString() {
        StringBuilder disponibilidadStr = new StringBuilder();
        for (Map.Entry<LocalDate, Boolean> entry : disponibilidadReserva.entrySet()) {
            disponibilidadStr.append("\n  ").append(entry.getKey()).append(": ").append(entry.getValue() ? "Disponible" : "Ocupada");
        }

        return "Habitacion" + "\n" +
                "Numero = " + numeroHabitacion + "\n" +
                "Estado Actual = " + estadoActual + "\n" +
                "Tipo de Habitacion = " + tipoHabitacion + "\n" +
                "Precio Diario = " + precioDiario + "\n" +
                "Disponibilidad: " + disponibilidadStr;
    }
}