package modelos;

import enumeraciones.TipoEmpleado;

import java.util.Objects;

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

    public Empleado() {
        super(null, null, null, null, null, null, null); // Llamada al constructor de Persona
        this.usuario = null;
        this.clave = null;
        this.salario = 0.0;
        this.cargo = TipoEmpleado.RECEPCIONISTA; // O dejarlo como null
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(usuario, empleado.usuario) && Objects.equals(clave, empleado.clave);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), usuario, clave);
    }
    @Override
    public String toString() {
        String colorAzul = "\u001B[94m";
        String colorVerde = "\u001B[92m";
        String resetColor = "\u001B[0m";

        return "[" + colorAzul + "Empleado" + resetColor + ": " +
                "DNI: " + colorVerde + getDni() + resetColor +
                ", Nombre: " + colorVerde + getNombre() + " " + getApellido() + resetColor +
                ", Nacionalidad: " + colorVerde + getNacionalidad() + resetColor +
                ", Domicilio: " + colorVerde + getDomicilio() + resetColor +
                ", Teléfono: " + colorVerde + getTelefono() + resetColor +
                ", Mail: " + colorVerde + getMail() + resetColor +
                ", Cargo: " + colorVerde + cargo + resetColor +
                ", Salario: " + colorVerde + salario + resetColor +"]";
    }
}