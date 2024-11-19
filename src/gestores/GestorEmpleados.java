package gestores;

import excepciones.*;
import modelos.Empleado;
import java.util.ArrayList;

public class GestorEmpleados implements IGestionable<String> {
    private ArrayList<Empleado> empleados;

    //Constructores
    public GestorEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }
    public GestorEmpleados() {
        empleados = new ArrayList<>();
    }

    //Getters y Setters
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        System.out.println("\nIngrese los datos del nuevo empleado:");

        boolean dniValido = false;
        boolean nombreValido = false;
        boolean apellidoValido = false;
        boolean nacionalidadValida = false;
        boolean domicilioValido = false;
        boolean telefonoValido = false;
        boolean mailValido = false;
        boolean salarioValido = false;

        Empleado nuevoEmpleado = new Empleado();

        // Validación del DNI
        do {
            try {
                String dni = GestorEntradas.pedirCadena("Ingrese DNI: ");
                if (Verificador.verificarDNI(dni) && Verificador.verificarDNIUnico(dni, empleados)) {
                    nuevoEmpleado.setDni(dni);
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
                if(Verificador.verificarNombre(nombre)){
                    nuevoEmpleado.setNombre(nombre);
                    nombreValido = true;
                }
            } catch (NombreInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!nombreValido);

        // Validación del apellido
        do {
            try {
                String apellido = GestorEntradas.pedirCadena("Ingrese apellido: ");
                if(Verificador.verificarApellido(apellido)){
                    nuevoEmpleado.setApellido(apellido);
                    apellidoValido = true;
                }
            } catch (ApellidoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!apellidoValido);

        // Validación de la nacionalidad
        do {
            try {
                String nacionalidad = GestorEntradas.pedirCadena("Ingrese nacionalidad: ");
                if(Verificador.verificarNacionalidad(nacionalidad)){
                    nuevoEmpleado.setNacionalidad(nacionalidad);
                    nacionalidadValida = true;
                }
            } catch (NacionalidadInvalidaException e) {
                System.err.println(e.getMessage());
            }
        } while (!nacionalidadValida);

        // Validación del domicilio
        do {
            try {
                String domicilio = GestorEntradas.pedirCadena("Ingrese domicilio: ");
                if(Verificador.verificarDomicilio(domicilio)){
                    nuevoEmpleado.setDomicilio(domicilio);
                    domicilioValido = true;
                }
            } catch (DomicilioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!domicilioValido);

        // Validación del teléfono
        do {
            try {
                String telefono = GestorEntradas.pedirCadena("Ingrese telefono: ");
                if(Verificador.verificarTelefono(telefono)){
                    nuevoEmpleado.setTelefono(telefono);
                    telefonoValido = true;
                }
            } catch (TelefonoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!telefonoValido);

        // Validación del mail
        do {
            try {
                String mail = GestorEntradas.pedirCadena("Ingrese mail: ");
                if(Verificador.verificarMail(mail)){
                    nuevoEmpleado.setMail(mail);
                    mailValido = true;
                }
            } catch (MailInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!mailValido);

        // Validación del salario
        do {
            try {
                Double salario = GestorEntradas.pedirDouble("Ingrese salario: ");
                if(Verificador.verificarSalario(salario)){
                    nuevoEmpleado.setSalario(salario);
                    salarioValido = true;
                }
            } catch (SalarioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!salarioValido);

        // Generación automática de usuario y clave
        empleados.add(nuevoEmpleado);

        System.out.println("\nUsuario creado por defecto: " + nuevoEmpleado.getUsuario());
        System.out.println("Clave creada por defecto: " + nuevoEmpleado.getClave());
        System.out.println("\nEmpleado agregado con éxito.");
    }

    @Override
    public void eliminar(String dni) {
        try{
            Empleado empleadoEliminar = buscarEmpleadoPorDni(dni);
            if (!Verificador.verificarObjetoNulo(empleadoEliminar)){
                throw new ObjetoNuloException("Empleado no encontrado");
            }
            int opcion;
            do {
            System.out.println("Confirmar eliminacion del empleado: ");
            System.out.println(empleadoEliminar);
            System.out.println("1. Si");
            System.out.println("2. No");

            opcion = GestorEntradas.pedirEntero("Ingrese su opción: ");

            if(opcion == 1){
                empleados.remove(empleadoEliminar);
                System.out.println("Empleado eliminado del sistema con éxito.");
            }else if (opcion == 2){
                System.out.println("El empleado no fue eliminado del sistema.");
            }else {
                System.out.println("Numero ingresado incorrecto, ingrese 1 para eliminar y 2 para cancelar la eliminacion");
            }
            }while(opcion < 1 && opcion > 2);
        }catch (ObjetoNuloException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modificar(String Dni) {
        boolean dniValido = false;
        boolean nombreValido = false;
        boolean apellidoValido = false;
        boolean nacionalidadValida = false;
        boolean domicilioValido = false;
        boolean telefonoValido = false;
        boolean mailValido = false;
        boolean salarioValido = false;
        boolean usuarioValido = false;
        boolean claveValida = false;

        Empleado empleadoModificado = buscarEmpleadoPorDni(Dni);
        System.out.println("Datos del empleado a modificar:");
        System.out.println(empleadoModificado);
        Integer indiceEmpleadoModificar = empleados.indexOf(empleadoModificado);

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
            System.out.println("8. Usuario");
            System.out.println("9. Clave");
            System.out.println("10. Salario");
            System.out.println("0. Salir");
            opcion = GestorEntradas.pedirEntero("Ingrese su opción: ");

            switch (opcion) {
                case 1->{
                    do {
                        try {
                            String dni = GestorEntradas.pedirCadena("Ingrese nuevo DNI: ");
                            if (Verificador.verificarDNI(dni) && Verificador.verificarDNIUnico(dni, empleados)) {
                                empleadoModificado.setDni(dni);
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
                case 2->{
                    do {
                        try {
                            String nombre = GestorEntradas.pedirCadena("Ingrese nuevo nombre: ");
                            if(Verificador.verificarNombre(nombre)){
                                empleadoModificado.setNombre(nombre);
                                nombreValido = true;
                            }
                        } catch (NombreInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!nombreValido);
                    System.out.println("Nombre modificado con éxito");
                }
                case 3->{
                    do {
                        try {
                            String apellido = GestorEntradas.pedirCadena("Ingrese nuevo apellido: ");
                            if(Verificador.verificarApellido(apellido)){
                                empleadoModificado.setApellido(apellido);
                                apellidoValido = true;
                            }
                        } catch (ApellidoInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!apellidoValido);
                    System.out.println("Apellido modificado con éxito");
                }
                case 4->{
                    do {
                        try {
                            String nacionalidad = GestorEntradas.pedirCadena("Ingrese nueva nacionalidad: ");
                            if(Verificador.verificarNacionalidad(nacionalidad)){
                                empleadoModificado.setNacionalidad(nacionalidad);
                                nacionalidadValida = true;
                            }
                        } catch (NacionalidadInvalidaException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!nacionalidadValida);
                    System.out.println("Nacionalidad modificado con éxito");
                }
                case 5->{
                    do {
                        try {
                            String domicilio = GestorEntradas.pedirCadena("Ingrese nuevo domicilio: ");
                            if(Verificador.verificarDomicilio(domicilio)){
                                empleadoModificado.setDomicilio(domicilio);
                                domicilioValido = true;
                            }
                        } catch (DomicilioInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!domicilioValido);
                    System.out.println("Domicilio modificado con éxito");
                }
                case 6->{
                    do {
                        try {
                            String telefono = GestorEntradas.pedirCadena("Ingrese nuevo telefono: ");
                            if(Verificador.verificarTelefono(telefono)){
                                empleadoModificado.setTelefono(telefono);
                                telefonoValido = true;
                            }
                        } catch (TelefonoInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!telefonoValido);
                    System.out.println("Telefono modificado con éxito");
                }
                case 7->{
                    do {
                        try {
                            String mail = GestorEntradas.pedirCadena("Ingrese nuevo mail: ");
                            if(Verificador.verificarMail(mail)){
                                empleadoModificado.setMail(mail);
                                mailValido = true;
                            }
                        } catch (MailInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!mailValido);

                    System.out.println("Mail modificado con éxito");
                }
                case 8->{
                    do {
                        try {
                            String usuario = GestorEntradas.pedirCadena("Ingrese nuevo usuario: ");
                            if(Verificador.verificarUsuario(usuario)){
                                empleadoModificado.setUsuario(usuario);
                                usuarioValido = true;
                            }
                        } catch (UsuarioInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!usuarioValido);
                    System.out.println("Usuario modificado con éxito");
                }
                case 9->{
                    do {
                        try {
                            String clave = GestorEntradas.pedirCadena("Ingrese nueva clave: ");
                            if(Verificador.verificarClave(clave)){
                                empleadoModificado.setClave(clave);
                                usuarioValido = true;
                            }
                        } catch (ClaveInvalidaException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!claveValida);
                    System.out.println("Clave modificado con éxito");
                }
                case 10->{
                    do {
                        try {
                            Double salario = GestorEntradas.pedirDouble("Ingrese nuevo salario: ");
                            if(Verificador.verificarSalario(salario)){
                                empleadoModificado.setSalario(salario);
                                salarioValido = true;
                            }
                        } catch (SalarioInvalidoException e) {
                            System.err.println(e.getMessage());
                        }
                    } while (!salarioValido);
                    System.out.println("Salario modificado con éxito");
                }
                case 0->{
                    System.out.println("Saliendo...");
                }
                default->{
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }

            System.out.println("\n¿Quiere realizar otra modificación?");
            System.out.println("1. Sí");
            System.out.println("0. No");
            opcion = GestorEntradas.pedirEntero("Ingrese opción: ");

        } while (opcion != 0);

        System.out.println("\nEmpleado modificado");
        System.out.println(empleadoModificado);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1. Sí");
        System.out.println("2. No");

        int confirmar = GestorEntradas.pedirEntero("Ingrese opción: ");

        if (confirmar == 1) {
            empleados.set(indiceEmpleadoModificar, empleadoModificado);
            System.out.println("Modificación completada.");
        } else {
            System.out.println("Modificación cancelada.");
        }
    }

    @Override
    public void listar() {
        System.out.println("\nLista de Empleados");
        System.out.println("=========================");

        try{
            if(Verificador.verificarArregloVacio(empleados)){
                for (Empleado empleado : empleados){
                    System.out.println(empleado);
                }
            }
        }catch (ArregloVacioException e){
            System.out.println(e.getMessage());
        }
        GestorEntradas.pausarConsola();
    }

    //Metodos de Busqueda
    private int buscarIndiceEmpleado(Empleado empleado) {
        for (Empleado e : empleados) {
            if (e.equals(empleado)) {
                return empleados.indexOf(e);
            }
        }
        return -1;
    }

    public Empleado buscarEmpleadoPorDni(String dni) {
        for (Empleado e : empleados) {
            if (e.getDni().equals(dni)) {
                return e;
            }
        }
        return null;
    }
}