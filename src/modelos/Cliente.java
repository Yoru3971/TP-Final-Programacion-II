package modelos;

import java.util.Objects;

public class Cliente extends Persona{
    private Boolean clienteVip;

    //gonstructor
    public Cliente(Integer dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, Boolean clienteVip) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail);
        this.clienteVip = clienteVip;
    }

    //getters y getters
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