package modelos;

public class Cliente extends Persona{
    private Boolean clienteVip;

    //constructor
    public Cliente(String dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, Boolean clienteVip) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail);
        this.clienteVip = clienteVip;
    }

    public Cliente(Boolean clienteVip) {
        super(null, null, null, null, null, null, null);
        this.clienteVip = clienteVip;
    }

    //getters y setters
    public Boolean getClienteVip() {
        return clienteVip;
    }
    public void setClienteVip(Boolean clienteVip) {
        this.clienteVip = clienteVip;
    }

    //equals, hashCode y toString
    @Override
    public String toString() {
        return "Cliente{" +
                "clienteVip=" + clienteVip +
                '}';
    }
}