package modelos;

import enumeraciones.TipoEmpleado;

import java.util.Objects;

public class Empleado extends Persona{
    private String usuario;
    private String clave;
    private Double salario;
    private TipoEmpleado cargo;

    //constructor
    public Empleado(String dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, Double salario) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail);
        this.usuario = nombre.concat(apellido);
        this.clave = dni;
        this.salario = salario;
        this.cargo = TipoEmpleado.RECEPCIONISTA;
    }

    //getters y setters
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public Double getSalario() {
        return salario;
    }
    public void setSalario(Double salario) {
        this.salario = salario;
    }
    public TipoEmpleado getCargo() {
        return cargo;
    }
    public void setCargo(TipoEmpleado cargo) {
        this.cargo = cargo;
    }

    //equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(usuario, empleado.usuario) && cargo == empleado.cargo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), usuario, cargo);
    }


}
