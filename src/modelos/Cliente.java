package modelos;

public class Cliente extends Persona{
    private Boolean clienteVip;

    //Constructores
    public Cliente(String dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, Boolean clienteVip) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail);
        this.clienteVip = clienteVip;
    }
    public Cliente(Boolean clienteVip) {
        super(null, null, null, null, null, null, null);
        this.clienteVip = clienteVip;
    }

    //Getters y setters
    public Boolean getClienteVip() {
        return clienteVip;
    }
    public void setClienteVip(Boolean clienteVip) {
        this.clienteVip = clienteVip;
    }

    //equals, hashCode y toString
    @Override
    public String toString() {
        // Códigos de color
        String colorVerde = "\u001B[32m";
        String colorAzul = "\u001B[34m";
        String resetColor = "\u001B[0m";

        return "[" + colorAzul + "Cliente" + resetColor + ": " +
                "DNI: " + colorVerde + getDni() + resetColor +
                ", Nombre: " + colorVerde + getNombre() + " " + getApellido() + resetColor +
                ", Nacionalidad: " + colorVerde + getNacionalidad() + resetColor +
                ", Domicilio: " + colorVerde + getDomicilio() + resetColor +
                ", Teléfono: " + colorVerde + getTelefono() + resetColor +
                ", Mail: " + colorVerde + getMail() + resetColor +
                ", VIP: " + colorVerde + (clienteVip ? "Sí" : "No") + resetColor + "]";
    }
}