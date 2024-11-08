package modelos;

import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class Habitacion {
    private Integer numeroHabitacion;
    private HashMap<LocalDateTime,Boolean> disponibilidadReserva;       //<FECHA,DISPONIBILIDAD>
    private EstadoHabitacion estadoActual;
    private TipoHabitacion tipoHabitacion;
    private Double precioDiario;


    // VER SI AGREGAR ALGUN CONSTRUCTOR, SIN ALGUN ATRIBUTO EN PARTICULAR
    public Habitacion(Integer numeroHabitacion, HashMap<LocalDateTime, Boolean> disponibilidadReserva, EstadoHabitacion estadoActual, TipoHabitacion tipoHabitacion, Double precioDiario) {
        this.numeroHabitacion = numeroHabitacion;
        this.disponibilidadReserva = disponibilidadReserva;
        this.estadoActual = estadoActual;
        this.tipoHabitacion = tipoHabitacion;
        this.precioDiario = precioDiario;
    }

    public Integer getNumeroHabitacion() {
        return numeroHabitacion;
    }
    public void setNumeroHabitacion(Integer numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }
    public HashMap<LocalDateTime, Boolean> getDisponibilidadReserva() {
        return disponibilidadReserva;
    }
    public void setDisponibilidadReserva(HashMap<LocalDateTime, Boolean> disponibilidadReserva) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion that)) return false;
        return Objects.equals(numeroHabitacion, that.numeroHabitacion) && Objects.equals(disponibilidadReserva, that.disponibilidadReserva) && estadoActual == that.estadoActual && tipoHabitacion == that.tipoHabitacion && Objects.equals(precioDiario, that.precioDiario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroHabitacion, disponibilidadReserva, estadoActual, tipoHabitacion, precioDiario);
    }

    @Override
    public String toString() {
        return "Habitacion" + "\n" +
                "Numero = " + numeroHabitacion + "\n" +
                "Disponibilidad = " + disponibilidadReserva + "\n" +
                "Estado Acutal = " + estadoActual + "\n" +
                "Tipo de Habitacion = " + tipoHabitacion + "\n" +
                "Precio Diario = " + precioDiario;
    }
}
