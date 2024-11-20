package gestores;

import excepciones.*;
import modelos.Cliente;
import java.util.ArrayList;

public class GestorClientes implements IGestionable<String> {
    private ArrayList<Cliente> clientes;

    //Constructores
    public GestorClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    public GestorClientes() {
        clientes = new ArrayList<>();
    }

    //Getters y Setters
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
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

        clientes.add(nuevoCliente);
        System.out.println("\nCliente agregado con éxito.");
        GestorEntradas.pausarConsola();
    }

    @Override
    public void eliminar(String dni) {
        try {
            Cliente clienteEliminar = buscarClientePorDni(dni);
            if (!Verificador.verificarObjetoNulo(clienteEliminar)) {
                throw new ObjetoNuloException("Cliente no encontrado");
            }
            int opcion;
            do {
                System.out.println("Confirmar eliminación del cliente con DNI " + dni + ": ");
                System.out.println("1. Sí");
                System.out.println("2. No");

                opcion = GestorEntradas.pedirEntero("Seleccione una opción: ");
                //GestorEntradas.limpiarBuffer();

                if (opcion == 1) {
                    clientes.remove(clienteEliminar);
                    System.out.println("Cliente eliminado del sistema con éxito.");
                } else if (opcion == 2) {
                    System.out.println("El cliente no fue eliminado del sistema.");
                } else {
                    System.out.println("Número ingresado incorrecto, ingrese 1 para eliminar o 2 para cancelar");
                }
            } while (opcion < 1 && opcion > 2);
        } catch (ObjetoNuloException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modificar(String dni) {
        Cliente clienteModificar = buscarClientePorDni(dni);

        if (clienteModificar == null) {
            System.out.println("Cliente no encontrado.");
            GestorEntradas.pausarConsola();
            return;
        }

        System.out.println("Datos del cliente a modificar:");
        System.out.println(clienteModificar);
        int indiceClienteModificar = clientes.indexOf(clienteModificar);

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
            clientes.set(indiceClienteModificar, clienteModificar);
            System.out.println("Modificación completada con éxito");
        } else {
            System.out.println("Modificación cancelada");
        }
    }

    @Override
    public void listar() {
        System.out.println("\nLista de Clientes");
        System.out.println("=========================");

        try{
            if(Verificador.verificarArregloVacio(clientes)){
                for (Cliente c : clientes) {
                    System.out.println(c);
                }
            }
        }catch(ArregloVacioException e){
            System.out.println(e.getMessage());
        }

        GestorEntradas.pausarConsola();
    }

    //Metodos de busqueda
    public Cliente buscarClientePorDni(String dni) {
        for(Cliente c : clientes) {
            if(c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    //Metodos auxiliares
    private void pedirDNI(Cliente cliente, String mensaje){
        boolean dniValido = false;
        do {
            try {
                String dni = GestorEntradas.pedirCadena(mensaje);

                if(Verificador.verificarDNI(dni) &&  Verificador.verificarDNIUnico(dni, clientes)){
                    cliente.setDni(dni);
                    dniValido = true;
                }
            } catch (DNIInvalidoException e) {
                System.err.println(e.getMessage());
            } catch (DNIExistenteException e) {
                System.err.println(e.getMessage());
            }
        } while (!dniValido);
    }

    private void pedirNombre(Cliente cliente, String mensaje) {
        boolean nombreValido = false;
        do {
            try {
                String nombre = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarNombre(nombre)) {
                    cliente.setNombre(nombre);
                    nombreValido = true;
                }
            } catch (NombreInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!nombreValido);
    }

    private void pedirApellido(Cliente cliente, String mensaje) {
        boolean apellidoValido = false;
        do {
            try {
                String apellido = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarApellido(apellido)) {
                    cliente.setApellido(apellido);
                    apellidoValido = true;
                }
            } catch (ApellidoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!apellidoValido);
    }

    private void pedirNacionalidad(Cliente cliente, String mensaje) {
        boolean nacionalidadValida = false;
        do {
            try {
                String nacionalidad = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarNacionalidad(nacionalidad)) {
                    cliente.setNacionalidad(nacionalidad);
                    nacionalidadValida = true;
                }
            } catch (NacionalidadInvalidaException e) {
                System.err.println(e.getMessage());
            }
        } while (!nacionalidadValida);
    }

    private void pedirDomicilio(Cliente cliente, String mensaje) {
        boolean domicilioValido = false;
        do {
            try {
                String domicilio = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarDomicilio(domicilio)) {
                    cliente.setDomicilio(domicilio);
                    domicilioValido = true;
                }
            } catch (DomicilioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!domicilioValido);
    }

    private void pedirTelefono(Cliente cliente, String mensaje) {
        boolean telefonoValido = false;
        do {
            try {
                String telefono = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarTelefono(telefono)) {
                    cliente.setTelefono(telefono);
                    telefonoValido = true;
                }
            } catch (TelefonoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!telefonoValido);
    }

    private void pedirMail(Cliente cliente, String mensaje) {
        boolean mailValido = false;
        do {
            try {
                String mail = GestorEntradas.pedirCadena(mensaje);
                if (Verificador.verificarMail(mail)) {
                    cliente.setMail(mail);
                    mailValido = true;
                }
            } catch (MailInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!mailValido);
    }

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