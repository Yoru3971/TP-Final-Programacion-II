package modelos;

import java.util.HashMap;
import java.util.Objects;
import enumeraciones.EstadoHabitacion;
import enumeraciones.TipoHabitacion;

public class Habitacion {
    private Integer numeroHabitacion;
    private HashMap<Integer, Boolean> disponibilidadReserva; // <día, disponibilidad>
    private EstadoHabitacion estadoActual;
    private TipoHabitacion tipoHabitacion;
    private Double precioDiario;

    //constructor
    public Habitacion(Integer numeroHabitacion, EstadoHabitacion estadoActual, TipoHabitacion tipoHabitacion, Double precioDiario) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoActual = estadoActual;
        this.tipoHabitacion = tipoHabitacion;
        this.precioDiario = precioDiario;
        this.disponibilidadReserva = new HashMap<>();

        // Inicializamos todos los días del 1 al 31 como disponibles (true)
        for (int dia = 1; dia <= 31; dia++) {
            disponibilidadReserva.put(dia, true);
        }
    }

    //getters y setters
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
    public HashMap<Integer, Boolean> getDisponibilidadReserva() {
        return disponibilidadReserva;
    }
    public void setDisponibilidadReserva(HashMap<Integer, Boolean> disponibilidadReserva) {
        this.disponibilidadReserva = disponibilidadReserva;
    }

    //equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion that)) return false;
        return Objects.equals(numeroHabitacion, that.numeroHabitacion) &&
                Objects.equals(disponibilidadReserva, that.disponibilidadReserva) &&
                estadoActual == that.estadoActual &&
                tipoHabitacion == that.tipoHabitacion &&
                Objects.equals(precioDiario, that.precioDiario);
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
                "Estado Actual = " + estadoActual + "\n" +
                "Tipo de Habitacion = " + tipoHabitacion + "\n" +
                "Precio Diario = " + precioDiario;
    }
}
