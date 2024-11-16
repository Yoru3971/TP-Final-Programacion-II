package gestores;

import modelos.Cliente;
import java.util.ArrayList;

public class GestorClientes implements IGestionable<String> {
    private ArrayList<Cliente> clientes;

    //constructores
    public GestorClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    public GestorClientes() {
        clientes = new ArrayList<>();
    }

    //getters y setters
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public void agregar() {
        Cliente nuevoCliente = new Cliente(false);

//        boolean nombreValido = false;
//        do {
//            try {
//                String nombre = GestorEntradas.pedirCadena("Ingrese nombre: ");
//                ValidadorCliente.validarNombre(nombre);
//                nuevoCliente.setNombre(nombre);
//                nombreValido = true;
//            } catch (NombreInvalidoException e) {
//                System.out.println(e.getMessage());
//            }
//        } while (!nombreValido);
        ///Forma de Validar dato por dato para implementar en todos los metodos agregar() o metodos que necesiten pasar datos por filtro de verificaciones
        ///Se usa un try catch por dato dentro de un bucle que te imprime el mensaje de la excepcion personalizada y no se detiene hasta que el dato sea valido
        ///Desvetajas: si el usuario quiere dejar de ingresar datos no puede XD
        ///Usar try catch en el momento y no usar throw hace que sea mas sencillo manejar las excepciones ya que no la delegas al metodo que invoco el agregar()

        nuevoCliente.setNombre(GestorEntradas.pedirCadena("Ingrese nombre: "));
        nuevoCliente.setApellido(GestorEntradas.pedirCadena("Ingrese apellido: "));
        nuevoCliente.setNacionalidad(GestorEntradas.pedirCadena("Ingrese nacionalidad: "));
        nuevoCliente.setDomicilio(GestorEntradas.pedirCadena("Ingrese domicilio: "));
        nuevoCliente.setTelefono(GestorEntradas.pedirCadena("Ingrese teléfono: "));
        nuevoCliente.setMail(GestorEntradas.pedirCadena("Ingrese email: "));
        nuevoCliente.setClienteVip(GestorEntradas.pedirEntero("¿Es cliente VIP? 1. Sí 2. No") == 1);

        clientes.add(nuevoCliente);
        System.out.println("Cliente agregado con éxito.");
    }

    @Override
    public void eliminar(String dni) {
        Cliente clienteEliminar = buscarClientePorDni(dni);
        System.out.println("Confirmar eliminación del cliente: ");
        System.out.println(clienteEliminar);

        int opcion = GestorEntradas.pedirEntero("1. Sí 2. No");
        if (opcion == 1) {
            clientes.remove(clienteEliminar);
            System.out.println("Cliente eliminado del sistema con éxito.");
        } else {
            System.out.println("El cliente no fue eliminado del sistema.");
        }
    }

    @Override
    public void listar() {
        System.out.println("\nLista de Clientes");
        System.out.println("=========================");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    @Override
    public void modificar(String dni) {
        Cliente clienteModificar = buscarClientePorDni(dni);

        int indiceClienteModificar = clientes.indexOf(clienteModificar);
        int opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido");
            System.out.println("3. Nacionalidad");
            System.out.println("4. Domicilio");
            System.out.println("5. Teléfono");
            System.out.println("6. Email");
            System.out.println("7. VIP (Sí/No)");
            System.out.println("0. Salir");

            opcion = GestorEntradas.pedirEntero("Seleccione una opción:");
            GestorEntradas.limpiarBuffer();

            switch (opcion) {
                case 1:
                    clienteModificar.setNombre(GestorEntradas.pedirCadena("Ingrese nuevo nombre: "));
                    System.out.println("Nombre modificado con éxito");
                    break;
                case 2:
                    clienteModificar.setApellido(GestorEntradas.pedirCadena("Ingrese nuevo apellido: "));
                    System.out.println("Apellido modificado con éxito");
                    break;
                case 3:
                    clienteModificar.setNacionalidad(GestorEntradas.pedirCadena("Ingrese nueva nacionalidad: "));
                    System.out.println("Nacionalidad modificada con éxito");
                    break;
                case 4:
                    clienteModificar.setDomicilio(GestorEntradas.pedirCadena("Ingrese nuevo domicilio: "));
                    System.out.println("Domicilio modificado con éxito");
                    break;
                case 5:
                    clienteModificar.setTelefono(GestorEntradas.pedirCadena("Ingrese nuevo teléfono: "));
                    System.out.println("Teléfono modificado con éxito");
                    break;
                case 6:
                    clienteModificar.setMail(GestorEntradas.pedirCadena("Ingrese nuevo email: "));
                    System.out.println("Email modificado con éxito");
                    break;
                case 7:
                    clienteModificar.setClienteVip(GestorEntradas.pedirEntero("¿Es cliente VIP? 1. Sí 2. No") == 1);
                    System.out.println("Estado VIP modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            opcion = GestorEntradas.pedirEntero("\n¿Quiere realizar otra modificación? 1. Sí 0. No");

        } while (opcion != 0);

        System.out.println("\nCliente modificado");
        System.out.println(clienteModificar);
        System.out.println("¿Desea confirmar los cambios?");
        int confirmar = GestorEntradas.pedirEntero("1. Sí 2. No");

        if (confirmar == 1) {
            clientes.set(indiceClienteModificar, clienteModificar);
            System.out.println("Modificación completada con éxito");
        } else {
            System.out.println("Modificación cancelada");
        }
    }

    // Métodos de busqueda
    public Cliente buscarClientePorDni(String dni) {
        Cliente cliente = null;
        for(Cliente c : clientes) {
            if(c.getDni().equals(dni)) {
                cliente = c;
            }
        }
        return cliente;
    }
}