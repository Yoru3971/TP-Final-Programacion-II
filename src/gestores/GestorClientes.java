package gestores;

import modelos.Cliente;

import java.util.ArrayList;

public class GestorClientes extends GestorPersonas<Cliente> {
    //Constantes para implementar color como en los menu
    private final String colorAmarillo = "\u001B[93m";
    private final String colorVerde = "\u001B[92m";
    private final String colorRojo = "\u001B[91m";
    private final String resetColor = "\u001B[0m";

    //Constructores
    //Este recibe un arreglo (levantado de un archivo) y se lo pasa al constructor del gestor generico de personas
    public GestorClientes(ArrayList<Cliente> clientes) {
        super(clientes);
    }

    //Default en caso de querer crear un arreglo de personas desde cero
    public GestorClientes() {}

    //Getters y Setters, usados para modificar el arreglo levantado
    public ArrayList<Cliente> getClientes() {
        return super.getPersonas();
    }
    public void setClientes(ArrayList<Cliente> personas) {
        super.setPersonas(personas);
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        GestorEntradas.limpiarConsola();
        //Todos los datos tienen su propio metodo que pide en bucle que se ingresen de forma correcta los datos, hasta no ser correctamente ingresados, no se avanza al siguiente
        //Aca podriamos dejar que corte en cualquier momento, pero estar constantemente preguntando si queres seguir cargando datos nos parecio un poco molesto para el usuario

        Cliente nuevoCliente = new Cliente(false);
        System.out.println("\n  Ingrese los datos del nuevo cliente: ");

        pedirDNI(nuevoCliente, "  Ingrese DNI: ");
        pedirNombre(nuevoCliente, "  Ingrese nombre: ");
        pedirApellido(nuevoCliente, "  Ingrese apellido: ");
        pedirNacionalidad(nuevoCliente, "  Ingrese nacionalidad: ");
        pedirDomicilio(nuevoCliente, "  Ingrese domicilio: ");
        pedirTelefono(nuevoCliente, "  Ingrese teléfono: ");
        pedirMail(nuevoCliente, "  Ingrese email: ");
        pedirVIP(nuevoCliente, "  ¿Es cliente VIP? \n  1. Sí \n  2. No\n");

        System.out.println("\n  Datos del cliente a cargar:");
        System.out.println(nuevoCliente);
        System.out.println("¿Desea confirmar?\n  1. Sí \n  2. No\n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().add(nuevoCliente);
            System.out.println(colorVerde+"\n  Cliente agregado con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Carga de cliente cancelada."+resetColor);
        }
    }

    @Override
    public void eliminar(String dni) {
        Cliente clienteEliminar = buscarClientePorDni(dni);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no un cliente con ese DNI

        if (clienteEliminar == null) {
            System.out.println("\n  Cliente no encontrado.");
            return;
        }

        System.out.println("\n  Datos del cliente a eliminar:");
        System.out.println(clienteEliminar);
        System.out.println("\n  ¿Desea confirmar la eliminación?\n  1. Sí \n  2. No \n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().remove(clienteEliminar);
            System.out.println(colorVerde+"\n  Eliminación completada con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Eliminación cancelada."+resetColor);
        }
    }

    @Override
    public void modificar(String dni) {
        Cliente clienteModificar = buscarClientePorDni(dni);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no un cliente con ese DNI
        if (clienteModificar == null) {
            System.out.println(colorRojo+"\n  Cliente no encontrado."+resetColor);
            return;
        }

        int indiceClienteModificar = super.getPersonas().indexOf(clienteModificar);

        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n  Datos del cliente a modificar:");
            System.out.println(clienteModificar);

            System.out.println("\n  ¿Qué desea modificar?");
            System.out.println("  1. DNI");
            System.out.println("  2. Nombre");
            System.out.println("  3. Apellido");
            System.out.println("  4. Nacionalidad");
            System.out.println("  5. Domicilio");
            System.out.println("  6. Teléfono");
            System.out.println("  7. Email");
            System.out.println("  8. VIP (Sí/No)");
            System.out.println("  0. Salir");

            opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

            switch (opcion) {
                case "1" -> {
                    pedirDNI(clienteModificar, "  Ingrese nuevo DNI: ");
                    System.out.println(colorVerde+"  DNI modificado con éxito"+resetColor);
                }
                case "2" -> {
                    pedirNombre(clienteModificar, "  Ingrese nuevo nombre: ");
                    System.out.println(colorVerde+"  Nombre modificado con éxito"+resetColor);
                }
                case "3" -> {
                    pedirApellido(clienteModificar, "  Ingrese nuevo apellido: ");
                    System.out.println(colorVerde+"  Apellido modificado con éxito"+resetColor);
                }
                case "4" -> {
                    pedirNacionalidad(clienteModificar, "  Ingrese nueva nacionalidad: ");
                    System.out.println(colorVerde+"  Nacionalidad modificada con éxito"+resetColor);
                }
                case "5" -> {
                    pedirDomicilio(clienteModificar, "  Ingrese nuevo domicilio: ");
                    System.out.println(colorVerde+"  Domicilio modificado con éxito"+resetColor);
                }
                case "6" -> {
                    pedirTelefono(clienteModificar, "  Ingrese nuevo teléfono: ");
                    System.out.println(colorVerde+"  Teléfono modificado con éxito"+resetColor);
                }
                case "7" -> {
                    pedirMail(clienteModificar, "  Ingrese nuevo email: ");
                    System.out.println(colorVerde+"  Email modificado con éxito"+resetColor);
                }
                case "8" -> {
                    pedirVIP(clienteModificar, "  ¿Es cliente VIP? \n  1. Sí \n  2. No \n");
                    System.out.println(colorVerde+"  Estado VIP modificado con éxito"+resetColor);
                }
                case "0" -> {
                    System.out.println("  Saliendo...");
                }
                default -> {
                    System.out.println(colorRojo+"  Opción no válida. Intente nuevamente."+resetColor);
                }
            }
            System.out.println("\n  ¿Quiere realizar otra modificación?\n  1. Sí \n  2. No\n");
            opcion = GestorEntradas.pedirCadena("  Ingrese opción: ");
        } while (!opcion.equals("0"));

        System.out.println("\n  Cliente modificado:");
        System.out.println(clienteModificar);
        System.out.println("\n  ¿Desea confirmar los cambios?\n  1. Sí \n  2. No\n");

        opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().set(indiceClienteModificar, clienteModificar);
            System.out.println(colorVerde+"\n  Modificación completada con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Modificación cancelada."+resetColor);
        }
    }

    @Override
    public void listar() {
        System.out.println(colorAmarillo + "\n  Lista de Clientes" + resetColor);
        System.out.println(colorAmarillo + "  =========================" + resetColor);

        if (!super.getPersonas().isEmpty()) {
            for (Cliente c : super.getPersonas()) {
                System.out.println("  "+c);
            }
        } else {
            System.out.println(colorRojo + "  No hay clientes registrados." + resetColor);
        }
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

    //Metodos especificos de Cliente para pedir datos
    //Tienen la misma estructura que los de GestorPersonas
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

    public Cliente pedirCliente(){
        Cliente cliente = new Cliente(false);
        boolean clienteEncontrado = false;

        do{
            cliente = buscarClientePorDni(GestorEntradas.pedirCadena("\n  Ingrese el dni del titular de la reserva: "));

            if(cliente == null){
                System.out.println("Cliente no encontrado. Intente de nuevo");
            }else{
                clienteEncontrado = true;
            }
        }while(!clienteEncontrado);

        return cliente;
    }
}