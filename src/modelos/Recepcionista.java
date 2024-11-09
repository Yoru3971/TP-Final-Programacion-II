package modelos;

public class Recepcionista extends Empleado{
    //constructor
    public Recepcionista(Integer dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, String usuario, String clave, double salario) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail, usuario, clave, salario);
    }

    //equals, hashCode y toString
    @Override
    public String toString() {
        return "Recepcionista{}";
    }
}