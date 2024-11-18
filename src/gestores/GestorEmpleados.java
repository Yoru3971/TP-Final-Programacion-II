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
                String nombre = GestorEntradas.pedirCadena("Ingrese Nombre: ");
                Verificador.verificarNombre(nombre);
                nuevoEmpleado.setNombre(nombre);
                nombreValido = true;
            } catch (NombreInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!nombreValido);

        // Validación del apellido
        do {
            try {
                String apellido = GestorEntradas.pedirCadena("Ingrese Apellido: ");
                Verificador.verificarApellido(apellido);
                nuevoEmpleado.setApellido(apellido);
                apellidoValido = true;
            } catch (ApellidoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!apellidoValido);

        // Validación de la nacionalidad
        do {
            try {
                String nacionalidad = GestorEntradas.pedirCadena("Ingrese Nacionalidad: ");
                Verificador.verificarNacionalidad(nacionalidad);
                nuevoEmpleado.setNacionalidad(nacionalidad);
                nacionalidadValida = true;
            } catch (NacionalidadInvalidaException e) {
                System.err.println(e.getMessage());
            }
        } while (!nacionalidadValida);

        // Validación del domicilio
        do {
            try {
                String domicilio = GestorEntradas.pedirCadena("Ingrese Domicilio: ");
                Verificador.verificarDomicilio(domicilio);
                nuevoEmpleado.setDomicilio(domicilio);
                domicilioValido = true;
            } catch (DomicilioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!domicilioValido);

        // Validación del teléfono
        do {
            try {
                String telefono = GestorEntradas.pedirCadena("Ingrese Telefono: ");
                Verificador.verificarTelefono(telefono);
                nuevoEmpleado.setTelefono(telefono);
                telefonoValido = true;
            } catch (TelefonoInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!telefonoValido);

        // Validación del mail
        do {
            try {
                String mail = GestorEntradas.pedirCadena("Ingrese Mail: ");
                Verificador.verificarMail(mail);
                nuevoEmpleado.setMail(mail);
                mailValido = true;
            } catch (MailInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!mailValido);

        // Validación del salario
        do {
            try {
                Double salario = GestorEntradas.pedirDouble("Ingrese salario: ");
                Verificador.verificarSalario(salario);
                nuevoEmpleado.setSalario(salario);
                salarioValido = true;
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

        Empleado empleadoModificado = buscarEmpleadoPorDni(Dni);
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
                case 1:
                    System.out.println("No se puede modificar el DNI porque es un atributo final.");
                    break;
                case 2:
                    empleadoModificado.setNombre(GestorEntradas.pedirCadena("Ingrese nombre: "));
                    System.out.println("Nombre modificado con éxito");
                    break;
                case 3:
                    empleadoModificado.setApellido(GestorEntradas.pedirCadena("Ingrese apellido: "));
                    System.out.println("Apellido modificado con éxito");
                    break;
                case 4:
                    empleadoModificado.setNacionalidad(GestorEntradas.pedirCadena("Ingrese nacionalidad: "));
                    System.out.println("Nacionalidad modificado con éxito");
                    break;
                case 5:
                    empleadoModificado.setDomicilio(GestorEntradas.pedirCadena("Ingrese domicilio: "));
                    System.out.println("Domicilio modificado con éxito");
                    break;
                case 6:
                    empleadoModificado.setTelefono(GestorEntradas.pedirCadena("Ingrese telefono: "));
                    System.out.println("Telefono modificado con éxito");
                    break;
                case 7:
                    empleadoModificado.setMail(GestorEntradas.pedirCadena("Ingrese mail: "));
                    System.out.println("Mail modificado con éxito");
                    break;
                case 8:
                    empleadoModificado.setUsuario(GestorEntradas.pedirCadena("Ingrese usuario: "));
                    System.out.println("Usuario modificado con éxito");
                    break;
                case 9:
                    empleadoModificado.setClave(GestorEntradas.pedirCadena("Ingrese clave: "));
                    System.out.println("Clave modificado con éxito");
                    break;
                case 10:
                    empleadoModificado.setSalario(GestorEntradas.pedirDouble("Ingrese salario: "));
                    System.out.println("Salario modificado con éxito");
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
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