package modelos;

import java.util.Objects;

public abstract class Empleado extends Persona{
    private String usuario;
    private String clave;
    private Double salario;

    //constructor
    public Empleado(String dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, String usuario, String clave, Double salario) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail);
        this.usuario = usuario;
        this.clave = clave;
        this.salario = salario;
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

    //equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(usuario, empleado.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), usuario);
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "usuario='" + usuario + '\'' +
                "} " + super.toString();
    }
}
