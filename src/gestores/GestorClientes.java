package gestores;

import modelos.Cliente;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorClientes implements IGestionable<String>{
    private ArrayList<Cliente> clientes;
    private Scanner scanner = new Scanner(System.in);

    public GestorClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public GestorClientes() {
        clientes = new ArrayList<>();
    }

    //Faltan agregar las verificaciones
    @Override
    public void agregar() {
        Cliente nuevoCliente = new Cliente(false);

        nuevoCliente.setNombre(pedirNombre(scanner));
        nuevoCliente.setApellido(pedirApellido(scanner));
        nuevoCliente.setNacionalidad(pedirNacionalidad(scanner));
        nuevoCliente.setDomicilio(pedirDomicilio(scanner));
        nuevoCliente.setTelefono(pedirTelefono(scanner));
        nuevoCliente.setMail(pedirEmail(scanner));
        nuevoCliente.setClienteVip(pedirVipStatus(scanner));

        clientes.add(nuevoCliente);

        System.out.println("Cliente agregado con éxito.");
    }

    @Override
    public void eliminar(String dni) {
        Cliente clienteEliminar = buscarPorDni(dni);

        System.out.println("Confirmar eliminacion del cliente: ");
        System.out.println(clienteEliminar);

        System.out.println("1. Si");
        System.out.println("2. No");

        if(scanner.nextInt() == 1){
            clientes.remove(clienteEliminar);
            System.out.println("Cliente eliminado del sistema con exito.");
        }else{
            System.out.println("El cliente no fue eliminado del sistema.");
        }

        //Hay que ver donde hacemos la eliminacion del archivo (puede ser aca o en el metodo del backup del admin)
    }

    @Override
    public void modificar(String dni) {
        Cliente clienteModificado = buscarPorDni(dni);
        Integer indiceClienteModificar = clientes.indexOf(clienteModificado);

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
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    clienteModificado.setNombre(pedirNombre(scanner));
                    System.out.println("Nombre modificado con éxito");
                    break;
                case 2:
                    clienteModificado.setApellido(pedirApellido(scanner));
                    System.out.println("Apellido modificado con éxito");
                    break;
                case 3:
                    clienteModificado.setNacionalidad(pedirNacionalidad(scanner));
                    System.out.println("Nacionalidad modificado con éxito");
                    break;
                case 4:
                    clienteModificado.setDomicilio(pedirDomicilio(scanner));
                    System.out.println("Domicilio modificado con éxito");
                    break;
                case 5:
                    clienteModificado.setTelefono(pedirTelefono(scanner));
                    System.out.println("Telefono modificado con éxito");
                    break;
                case 6:
                    clienteModificado.setMail(pedirEmail(scanner));
                    System.out.println("Mail modificado con éxito");
                    break;
                case 7:
                    clienteModificado.setClienteVip(pedirVipStatus(scanner));
                    System.out.println("Estado Vip modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println("\nQuiere realizar otra modificacion?");
            System.out.println("1.Si");
            System.out.println("0.No");
            opcion = scanner.nextInt();
            scanner.nextLine();

        } while (opcion != 0);

        System.out.println("\nCliente modificado");
        System.out.println(clienteModificado);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1.Si");
        System.out.println("2.No");

        int confirmar = scanner.nextInt();

        if (confirmar == 1) {
            clientes.set(indiceClienteModificar, clienteModificado);
            System.out.println("Modificacion completada con éxito");
        } else {
            System.out.println("Modificacion cancelada");
        }
    }

    private Cliente buscarPorDni(String dni){
        Cliente cliente = null;
        for(Cliente c : clientes){
            if(c.getDni().equals(dni)){
                cliente = c;
            }
        }
        return cliente;
    }

    // Métodos para solicitar cada atributo
    private String pedirNombre(Scanner scanner) {
        System.out.print("Ingrese nombre: ");
        return scanner.nextLine();
    }

    private String pedirApellido(Scanner scanner) {
        System.out.print("Ingrese apellido: ");
        return scanner.nextLine();
    }

    private String pedirNacionalidad(Scanner scanner) {
        System.out.print("Ingrese nacionalidad: ");
        return scanner.nextLine();
    }

    private String pedirDomicilio(Scanner scanner) {
        System.out.print("Ingrese domicilio: ");
        return scanner.nextLine();
    }

    private String pedirTelefono(Scanner scanner) {
        System.out.print("Ingrese teléfono: ");
        return scanner.nextLine();
    }

    private String pedirEmail(Scanner scanner) {
        System.out.print("Ingrese email: ");
        return scanner.nextLine();
    }

    private boolean pedirVipStatus(Scanner scanner) {
        System.out.println("¿Es cliente VIP?");
        System.out.println("1. Sí");
        System.out.println("2. No");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        return opcion == 1;
    }
}
