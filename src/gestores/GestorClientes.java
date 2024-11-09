package gestores;

import modelos.Cliente;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorClientes implements IGestionable<Cliente>{
    private ArrayList<Cliente> clientes;

    public GestorClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public GestorClientes() {
        clientes = new ArrayList<>();
    }


    //Faltan agregar las verificaciones
    @Override
    public void agregar(Cliente cliente) {
        clientes.add(cliente);
        System.out.println("Cliente agregado con éxito.");
    }

    @Override
    public void eliminar(Cliente cliente) {
        clientes.remove(cliente);
        System.out.println("Cliente eliminado con éxito.");
    }

    @Override
    public void modificar(Cliente cliente) {
        Scanner scanner = new Scanner(System.in);

        int index = buscarIndiceCliente(cliente);

        Cliente clienteModificado = clientes.get(index); // Obtener el cliente original

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
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    clienteModificado.setNombre(nuevoNombre);
                    break;
                case 2:
                    System.out.print("Ingrese nuevo apellido: ");

                    String nuevoApellido = scanner.nextLine();
                    clienteModificado.setApellido(nuevoApellido);
                    break;
                case 3:
                    System.out.print("Ingrese nueva nacionalidad: ");
                    String nuevaNacionalidad = scanner.nextLine();
                    clienteModificado.setNacionalidad(nuevaNacionalidad);
                    break;
                case 4:
                    System.out.print("Ingrese nuevo domicilio: ");
                    String nuevoDomicilio = scanner.nextLine();
                    clienteModificado.setDomicilio(nuevoDomicilio);
                    break;
                case 5:
                    System.out.print("Ingrese nuevo teléfono: ");
                    String nuevoTelefono = scanner.nextLine();
                    clienteModificado.setTelefono(nuevoTelefono);
                    break;
                case 6:
                    System.out.print("Ingrese nuevo email: ");
                    String nuevoMail = scanner.nextLine();
                    clienteModificado.setMail(nuevoMail);
                    break;
                case 7:
                    System.out.println("¿Es cliente VIP?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    int vipOption = scanner.nextInt();
                    if (vipOption == 1) {
                        clienteModificado.setClienteVip(true);
                    } else if (vipOption == 2) {
                        clienteModificado.setClienteVip(false);
                    } else {
                        System.out.println("Opción no válida. Se mantiene el estado actual.");
                    }
                    break;
                case 0:
                    System.out.println("Saliendo.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);

        clientes.set(index, clienteModificado); // Guardar los cambios del cliente modificado
        System.out.println("Modificación completada.");
    }

    private int buscarIndiceCliente(Cliente cliente) {
        for(Cliente c : clientes){
            if(c.equals(cliente)){
                return clientes.indexOf(c);
            }
        }
        return -1;
    }
}
