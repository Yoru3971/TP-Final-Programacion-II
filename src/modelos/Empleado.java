package modelos;

import enumeraciones.TipoEmpleado;

public class Empleado extends Persona{
    private String usuario;
    private String clave;
    private Double salario;
    private TipoEmpleado cargo;

    //Constructores
    public Empleado(String dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, Double salario) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail);
        this.usuario = nombre.concat(apellido);
        this.clave = dni;
        this.salario = salario;
        this.cargo = TipoEmpleado.RECEPCIONISTA;
    }

    //Getters y Setters
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
    ///equals y hashCode heredados de Persona
    @Override
    public String toString() {
        return "Empleado{" +
                "usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", salario=" + salario +
                ", cargo=" + cargo +
                "} " + super.toString();
    }
}