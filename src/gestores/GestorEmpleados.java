package gestores;

import excepciones.*;
import modelos.Empleado;
import java.util.ArrayList;

public class GestorEmpleados extends GestorPersonas<Empleado> implements IGestionable<String>{

    public GestorEmpleados(ArrayList<Empleado> personas) {
        super(personas);
    }

    public GestorEmpleados() {
    }

    public ArrayList<Empleado> getEmpleados() {
        return super.getPersonas();
    }

    public void setEmpleados(ArrayList<Empleado> personas) {
        super.setPersonas(personas);
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        Empleado nuevoEmpleado = new Empleado();
        System.out.println("Ingrese los datos del nuevo empleado:");

        pedirDNI(nuevoEmpleado, "Ingrese DNI: ");
        pedirNombre(nuevoEmpleado, "Ingrese nombre: ");
        pedirApellido(nuevoEmpleado, "Ingrese apellido: ");
        pedirNacionalidad(nuevoEmpleado, "Ingrese nacionalidad: ");
        pedirDomicilio(nuevoEmpleado, "Ingrese domicilio: ");
        pedirTelefono(nuevoEmpleado, "Ingrese teléfono: ");
        pedirMail(nuevoEmpleado, "Ingrese email: ");
        pedirSalario(nuevoEmpleado, "Ingrese salario: ");

        // Generación automática de usuario y clave
        System.out.println("\nUsuario creado por defecto: " + nuevoEmpleado.getUsuario());
        System.out.println("Clave creada por defecto: " + nuevoEmpleado.getClave());
        super.getPersonas().add(nuevoEmpleado);
        System.out.println("\nEmpleado agregado con éxito.");
        GestorEntradas.pausarConsola();
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
                    super.getPersonas().remove(empleadoEliminar);
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
        Empleado empleadoModificar = buscarEmpleadoPorDni(Dni);

        if (empleadoModificar == null) {
            System.out.println("Empleado no encontrado.");
            GestorEntradas.pausarConsola();
            return;
        }

        System.out.println("Datos del empleado a modificar:");
        System.out.println(empleadoModificar);
        int indiceEmpleadoModificar = super.getPersonas().indexOf(empleadoModificar);

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
            System.out.println("8. Usuario");
            System.out.println("9. Clave");
            System.out.println("10. Salario");
            System.out.println("0. Salir");

            opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

            switch (opcion) {
                case "1" -> {
                    pedirDNI(empleadoModificar, "Ingrese nuevo DNI: ");
                    System.out.println("DNI modificado con éxito");
                }
                case "2" -> {
                    pedirNombre(empleadoModificar, "Ingrese nuevo nombre: ");
                    System.out.println("Nombre modificado con éxito");
                }
                case "3" -> {
                    pedirApellido(empleadoModificar, "Ingrese nuevo apellido: ");
                    System.out.println("Apellido modificado con éxito");
                }
                case "4" -> {
                    pedirNacionalidad(empleadoModificar, "Ingrese nueva nacionalidad: ");
                    System.out.println("Nacionalidad modificada con éxito");
                }
                case "5" -> {
                    pedirDomicilio(empleadoModificar, "Ingrese nuevo domicilio: ");
                    System.out.println("Domicilio modificado con éxito");
                }
                case "6" -> {
                    pedirTelefono(empleadoModificar, "Ingrese nuevo teléfono: ");
                    System.out.println("Teléfono modificado con éxito");
                }
                case "7" -> {
                    pedirMail(empleadoModificar, "Ingrese nuevo email: ");
                    System.out.println("Email modificado con éxito");
                }
                case "8" -> {
                    pedirUsuario(empleadoModificar, "Ingrese nuevo usuario: ");
                    System.out.println("Usuario modificado con éxito");
                }
                case "9" -> {
                    pedirClave(empleadoModificar, "Ingrese nueva clave: ");
                    System.out.println("Clave modificada con éxito");
                }
                case "10" -> {
                    pedirSalario(empleadoModificar, "Ingrese nuevo salario: ");
                    System.out.println("Salario modificado con éxito");
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

        System.out.println("\nEmpleado modificado");
        System.out.println(empleadoModificar);
        System.out.println("¿Desea confirmar los cambios?\n1.Si \n2.No \n");

        opcion = GestorEntradas.pedirCadena("Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().set(indiceEmpleadoModificar, empleadoModificar);
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
            if(Verificador.verificarArregloVacio(super.getPersonas())){
                for (Empleado empleado : super.getPersonas()){
                    System.out.println(empleado);
                }
            }
        }catch (ArregloVacioException e){
            System.out.println(e.getMessage());
        }
    }

    //Metodos de Busqueda
    public Empleado buscarEmpleadoPorDni(String dni) {
        for (Empleado e : super.getPersonas()) {
            if (e.getDni().equals(dni)) {
                return e;
            }
        }
        return null;
    }

    //Metodos Auxiliares
    private void pedirSalario(Empleado empleado, String mensaje){
        boolean salarioValido = false;
        do {
            try {
                Double salario = GestorEntradas.pedirDouble(mensaje);
                if(Verificador.verificarSalario(salario)){
                    empleado.setSalario(salario);
                    salarioValido = true;
                }
            } catch (SalarioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!salarioValido);
    }

    private void pedirUsuario(Empleado empleado, String mensaje){
        boolean usuarioValido = false;
        do {
            try {
                String usuario = GestorEntradas.pedirCadena(mensaje);
                if(Verificador.verificarUsuario(usuario)){
                    empleado.setUsuario(usuario);
                    usuarioValido = true;
                }
            } catch (UsuarioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!usuarioValido);
    }

    private void pedirClave(Empleado empleado, String mensaje){
        boolean claveValida = false;
        do {
            try {
                String clave = GestorEntradas.pedirCadena(mensaje);
                if(Verificador.verificarClave(clave)){
                    empleado.setClave(clave);
                    claveValida = true;
                }
            } catch (ClaveInvalidaException e) {
                System.err.println(e.getMessage());
            }
        } while (!claveValida);
    }

}