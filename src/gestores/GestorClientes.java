package gestores;

import excepciones.*;
import modelos.Cliente;
import java.util.ArrayList;

public class GestorClientes extends GestorPersonas<Cliente> {
    public GestorClientes(ArrayList<Cliente> clientes) {
        super(clientes);
    }

    public GestorClientes() {
    }

    public ArrayList<Cliente> getClientes() {
        return super.getPersonas();
    }

    public void setClientes(ArrayList<Cliente> personas) {
        super.setPersonas(personas);
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        Cliente nuevoCliente = new Cliente(false);
        System.out.println("Ingrese los datos del nuevo cliente: ");

        pedirDNI(nuevoCliente, "Ingrese DNI: ");
        pedirNombre(nuevoCliente, "Ingrese nombre: ");
        pedirApellido(nuevoCliente, "Ingrese apellido: ");
        pedirNacionalidad(nuevoCliente, "Ingrese nacionalidad: ");
        pedirDomicilio(nuevoCliente, "Ingrese domicilio: ");
        pedirTelefono(nuevoCliente, "Ingrese teléfono: ");
        pedirMail(nuevoCliente, "Ingrese email: ");
        pedirVIP(nuevoCliente, "¿Es cliente VIP? \n1. Sí \n2. No\n");

        super.getPersonas().add(nuevoCliente);
        System.out.println("\nCliente agregado con éxito.");
        GestorEntradas.pausarConsola();
    }

    @Override
    public void eliminar(String dni) {
        Cliente clienteEliminar = buscarClientePorDni(dni);

        if (clienteEliminar == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Datos del cliente a eliminar:");
        System.out.println(clienteEliminar);

        System.out.println("¿Desea confirmar la eliminacion?\n1.Si \n2.No \n");

        String opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().remove(clienteEliminar);
            System.out.println("Eliminacion completada con éxito.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }

    @Override
    public void modificar(String dni) {
        Cliente clienteModificar = buscarClientePorDni(dni);

        if (clienteModificar == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("Datos del cliente a modificar:");
        System.out.println(clienteModificar);
        int indiceClienteModificar = super.getPersonas().indexOf(clienteModificar);

        String opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. DNI");
            System.out.println("2. Nombre");
            System.out.println("3. Apellido");
            System.out.println("4. Nacionalidad");
            System.out.println("5. Domicilio");
            System.out.println("6. Teléfono");
            System.out.println("7. Email");
            System.out.println("8. VIP (Sí/No)");
            System.out.println("0. Salir");

            opcion = GestorEntradas.pedirCadena("Seleccione una opción: ");

            switch (opcion) {
                case "1" -> {
                    pedirDNI(clienteModificar, "Ingrese nuevo DNI");
                    System.out.println("DNI modificado con éxito");
                }
                case "2" -> {
                    pedirNombre(clienteModificar, "Ingrese nuevo nombre: ");
                    System.out.println("Nombre modificado con éxito");
                }
                case "3" -> {
                    pedirApellido(clienteModificar, "Ingrese nuevo apellido: ");
                    System.out.println("Apellido modificado con éxito");
                }
                case "4" -> {
                    pedirNacionalidad(clienteModificar, "Ingrese nueva nacionalidad: ");
                    System.out.println("Nacionalidad modificado con éxito");
                }
                case "5" -> {
                    pedirDomicilio(clienteModificar, "Ingrese nuevo domicilio: ");
                    System.out.println("Domicilio modificado con éxito");
                }
                case "6" -> {
                    pedirTelefono(clienteModificar, "Ingrese nuevo teléfono: ");
                    System.out.println("Teléfono modificado con éxito");
                }
                case "7" -> {
                    pedirMail(clienteModificar, "Ingrese nuevo email: ");
                    System.out.println("Email modificado con éxito");
                }
                case "8" -> {
                    pedirVIP(clienteModificar, "¿Es cliente VIP? \n1.Sí \n2.No\n");
                    System.out.println("Estado VIP modificado con éxito");
                }
                case "0" -> {
                    System.out.println("Saliendo...");
                }
                default -> {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }
            System.out.println("\n¿Quiere realizar otra modificación?\n1.Si \n2.No \n");
            opcion = GestorEntradas.pedirCadena("Ingrese opción: ");
        } while (!opcion.equals("0"));

        System.out.println("\nCliente modificado");
        System.out.println(clienteModificar);
        System.out.println("¿Desea confirmar los cambios?\n1.Si \n2.No \n");

        opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().set(indiceClienteModificar, clienteModificar);
            System.out.println("Modificación completada con éxito");
        } else {
            System.out.println("Modificación cancelada");
        }
    }

    @Override
    public void listar() {
        System.out.println("\nLista de Clientes");
        System.out.println("=========================");

        if(!super.getPersonas().isEmpty()){
            for (Cliente c : super.getPersonas()) {
                System.out.println(c);
            }
        }
    }

    public Cliente pedirCliente(){
        Cliente cliente = new Cliente(false);
        boolean clienteEncontrado = false;

        do{
            cliente = buscarClientePorDni(GestorEntradas.pedirCadena("\nIngrese el dni del titular de la reserva: "));

            if(cliente == null){
                System.out.println("Cliente no encontrado. Intente de nuevo");
            }else{
                clienteEncontrado = true;
            }
        }while(!clienteEncontrado);

        return cliente;
    }

    //Metodos de busqueda
    public Cliente buscarClientePorDni(String dni) {
        for(Cliente c : super.getPersonas()) {
            if(c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    //Metodos auxiliares
    private void pedirVIP(Cliente cliente, String mensaje) {
        boolean vipValido = false;
        do {
            int vip = GestorEntradas.pedirEntero(mensaje);
            if (vip == 1 || vip == 2) {
                cliente.setClienteVip(vip == 1);
                vipValido = true;
            } else {
                System.out.println("Opción inválida. Debe ser 1 o 2.");
            }
        } while (!vipValido);
    }
}