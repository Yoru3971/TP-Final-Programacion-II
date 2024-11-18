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
            } catch (DNIInvalidoException e) {
                System.err.println(e.getMessage());
            } catch (DNIExistenteException e) {
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
        nuevoCliente.setClienteVip(GestorEntradas.pedirEntero("¿Es cliente VIP? \n1.Sí \n2.No\n") == 1);

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
        boolean dniValido = false;
        boolean nombreValido = false;
        boolean apellidoValido = false;
        boolean nacionalidadValida = false;
        boolean domicilioValido = false;
        boolean telefonoValido = false;
        boolean mailValido = false;
        boolean vipValido = false;

        Cliente clienteModificar = buscarClientePorDni(dni);
        System.out.println("Datos del cliente a modificar:");
        System.out.println(clienteModificar);
        int indiceClienteModificar = clientes.indexOf(clienteModificar);

        int opcion;
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
            opcion = GestorEntradas.pedirEntero("Seleccione una opción:");

            switch (opcion) {
               case 1 ->{
                   do {
                       try {
                           String dniModificar = GestorEntradas.pedirCadena("Ingrese nuevo DNI: ");

                           if(Verificador.verificarDNI(dni) &&  Verificador.verificarDNIUnico(dni, clientes)){
                               clienteModificar.setDni(dniModificar);
                               dniValido = true;
                           }
                       } catch (DNIInvalidoException e) {
                           System.err.println(e.getMessage());
                       } catch (DNIExistenteException e) {
                           System.err.println(e.getMessage());
                       }
                   } while (!dniValido);
                   System.out.println("DNI modificado con éxito");
               }
                case 2 -> {
                    do {
                        try {
                            String nombre = GestorEntradas.pedirCadena("Ingrese nuevo nombre: ");
                            if (Verificador.verificarNombre(nombre)) {
                                clienteModificar.setNombre(nombre);
                                nombreValido = true;
                            }
                        } catch (NombreInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!nombreValido);
                    System.out.println("Nombre modificado con éxito");
                }
                case 3 -> {
                    do {
                        try {
                            String apellido = GestorEntradas.pedirCadena("Ingrese nuevo apellido: ");
                            if (Verificador.verificarApellido(apellido)) {
                                clienteModificar.setApellido(apellido);
                                apellidoValido = true;
                            }
                        } catch (ApellidoInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!apellidoValido);
                    System.out.println("Apellido modificado con éxito");
                }
                case 4 -> {
                    do {
                        try {
                            String nacionalidad = GestorEntradas.pedirCadena("Ingrese nueva nacionalidad: ");
                            if (Verificador.verificarNacionalidad(nacionalidad)) {
                                clienteModificar.setNacionalidad(nacionalidad);
                                nacionalidadValida = true;
                            }
                        } catch (NacionalidadInvalidaException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!nacionalidadValida);
                    System.out.println("Nacionalidad modificada con éxito");
                }
                case 5 -> {
                    do {
                        try {
                            String domicilio = GestorEntradas.pedirCadena("Ingrese nuevo domicilio: ");
                            if (Verificador.verificarDomicilio(domicilio)) {
                                clienteModificar.setDomicilio(domicilio);
                                domicilioValido = true;
                            }
                        } catch (DomicilioInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!domicilioValido);
                    System.out.println("Domicilio modificado con éxito");
                }
                case 6 -> {
                    do {
                        try {
                            String telefono = GestorEntradas.pedirCadena("Ingrese nuevo teléfono: ");
                            if (Verificador.verificarTelefono(telefono)) {
                                clienteModificar.setTelefono(telefono);
                                telefonoValido = true;
                            }
                        } catch (TelefonoInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!telefonoValido);
                    System.out.println("Teléfono modificado con éxito");
                }
                case 7 -> {
                    do {
                        try {
                            String mail = GestorEntradas.pedirCadena("Ingrese nuevo email: ");
                            if (Verificador.verificarMail(mail)) {
                                clienteModificar.setMail(mail);
                                mailValido = true;
                            }
                        } catch (MailInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!mailValido);
                    System.out.println("Email modificado con éxito");
                }
                case 8 -> {
                    do {
                        int vip = GestorEntradas.pedirEntero("¿Es cliente VIP? \n1.Sí \n2.No ");
                        if (vip == 1 || vip == 2) {
                            clienteModificar.setClienteVip(vip == 1);
                            vipValido = true;
                        } else {
                            System.out.println("Opción invalida. Debe ser 1 o 2.");
                        }
                    } while (!vipValido);
                    System.out.println("Estado VIP modificado con éxito");
                }
                case 0 -> {
                    System.out.println("Saliendo...");
                }
                default -> {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }

            System.out.println("\n¿Quiere realizar otra modificación?");
            System.out.println("1. Sí");
            System.out.println("0. No");

            opcion = GestorEntradas.pedirEntero("Ingrese opción: ");
        } while (opcion != 0);

        System.out.println("\nCliente modificado");
        System.out.println(clienteModificar);
        System.out.println("¿Desea confirmar los cambios?");

        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int confirmar = GestorEntradas.pedirEntero("Ingrese opción: ");

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