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
    ///equals y hashCode heredados de Persona
    @Override
    public String toString() {
        return "Cliente{" +
                "clienteVip=" + clienteVip +
                '}';
    }
}