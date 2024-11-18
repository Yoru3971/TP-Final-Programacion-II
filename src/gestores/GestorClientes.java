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

        boolean dniValido = false;
        boolean nombreValido = false;
        boolean apellidoValido = false;
        boolean nacionalidadValida = false;
        boolean domicilioValido = false;
        boolean telefonoValido = false;
        boolean mailValido = false;

        System.out.println("Ingrese los datos del nuevo cliente: ");

        // Validación del DNI
        do {
            try {
                String dni = GestorEntradas.pedirCadena("Ingrese DNI: ");

                if(Verificador.verificarDNI(dni) &&  Verificador.verificarDNIUnico(dni, clientes)){
                    nuevoCliente.setDni(dni);
                    dniValido = true;
                }
            } catch (NombreInvalidoException e) {
                System.err.println(e.getMessage());
            } catch (ClienteExistenteException e) {
                System.err.println(e.getMessage());
            }
        } while (!dniValido);

        // Validación del nombre
        do {
            try {
                String nombre = GestorEntradas.pedirCadena("Ingrese nombre: ");
                Verificador.verificarNombre(nombre);
                nuevoCliente.setNombre(nombre);
                nombreValido = true;
            } catch (NombreInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!nombreValido);

        // Validación del apellido
        do {
            try {
                String apellido = GestorEntradas.pedirCadena("Ingrese apellido: ");
                Verificador.verificarApellido(apellido);
                nuevoCliente.setApellido(apellido);
                apellidoValido = true;
            } catch (ApellidoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!apellidoValido);

        // Validación de la nacionalidad
        do {
            try {
                String nacionalidad = GestorEntradas.pedirCadena("Ingrese nacionalidad: ");
                Verificador.verificarNacionalidad(nacionalidad);
                nuevoCliente.setNacionalidad(nacionalidad);
                nacionalidadValida = true;
            } catch (NacionalidadInvalidaException e) {
                System.err.println(e.getMessage());
            }
        } while (!nacionalidadValida);

        // Validación del domicilio
        do {
            try {
                String domicilio = GestorEntradas.pedirCadena("Ingrese domicilio: ");
                Verificador.verificarDomicilio(domicilio);
                nuevoCliente.setDomicilio(domicilio);
                domicilioValido = true;
            } catch (DomicilioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!domicilioValido);

        // Validación del teléfono
        do {
            try {
                String telefono = GestorEntradas.pedirCadena("Ingrese teléfono: ");
                Verificador.verificarTelefono(telefono);
                nuevoCliente.setTelefono(telefono);
                telefonoValido = true;
            } catch (TelefonoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!telefonoValido);

        // Validación del mail
        do {
            try {
                String mail = GestorEntradas.pedirCadena("Ingrese email: ");
                Verificador.verificarMail(mail);
                nuevoCliente.setMail(mail);
                mailValido = true;
            } catch (MailInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!mailValido);

        // Validación si es cliente VIP
        nuevoCliente.setClienteVip(GestorEntradas.pedirEntero("¿Es cliente VIP? 1. Sí 2. No") == 1);

        clientes.add(nuevoCliente); //Como antes ya verifique que el dni sea valido y unico, se puede agregar directamente aca
        System.out.println("Cliente agregado con éxito.");
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
}