package gestores;

import excepciones.*;
import modelos.Empleado;
import java.util.ArrayList;

public class GestorEmpleados extends GestorPersonas<Empleado> {
    //Constantes para implementar color como en los menu
    private final String colorAmarillo = "\u001B[93m";
    private final String colorVerde = "\u001B[92m";
    private final String colorRojo = "\u001B[91m";
    private final String resetColor = "\u001B[0m";

    //Constructores
    //Este recibe un arreglo (levantado de un archivo) y se lo pasa al constructor del gestor generico de personas
    public GestorEmpleados(ArrayList<Empleado> personas) {
        super(personas);
    }

    //Default en caso de querer crear un arreglo de personas desde cero
    public GestorEmpleados() {}

    //Getters y Setters, usados para modificar el arreglo levantado
    public ArrayList<Empleado> getEmpleados() {
        return super.getPersonas();
    }
    public void setEmpleados(ArrayList<Empleado> personas) {
        super.setPersonas(personas);
    }

    //Metodos ABM y Listar de IGestionable
    @Override
    public void agregar() {
        GestorEntradas.limpiarConsola();
        //Todos los datos tienen su propio metodo que pide en bucle que se ingresen de forma correcta los datos, hasta no ser correctamente ingresados, no se avanza al siguiente
        //Aca podriamos dejar que corte en cualquier momento, pero estar constantemente preguntando si queres seguir cargando datos nos parecio un poco molesto para el usuario

        Empleado nuevoEmpleado = new Empleado();
        System.out.println(colorAmarillo+"\n  Ingrese los datos del nuevo empleado:\n"+resetColor);

        pedirDNI(nuevoEmpleado, "  Ingrese DNI: ");
        pedirNombre(nuevoEmpleado, "  Ingrese nombre: ");
        pedirApellido(nuevoEmpleado, "  Ingrese apellido: ");
        pedirNacionalidad(nuevoEmpleado, "  Ingrese nacionalidad: ");
        pedirDomicilio(nuevoEmpleado, "  Ingrese domicilio: ");
        pedirTelefono(nuevoEmpleado, "  Ingrese teléfono: ");
        pedirMail(nuevoEmpleado, "  Ingrese email: ");
        pedirSalario(nuevoEmpleado, "  Ingrese salario: ");

        //Generación automática de usuario y clave, esto luego se verifica al iniciar sesion por primera vez
        nuevoEmpleado.setUsuario(nuevoEmpleado.getNombre().concat(nuevoEmpleado.getApellido()));
        nuevoEmpleado.setClave(nuevoEmpleado.getDni());
        System.out.println("\n  Usuario creado por defecto: " + nuevoEmpleado.getUsuario());
        System.out.println("  Clave creada por defecto: " + nuevoEmpleado.getClave());

        System.out.println("\n  Datos del empleado a cargar:");
        System.out.println(nuevoEmpleado);
        System.out.println("¿Desea confirmar?\n  1. Si \n  2. No\n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().add(nuevoEmpleado);
            System.out.println(colorVerde+"\n  Empleado creado con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Carga de empleado cancelada."+resetColor);
        }
    }

    @Override
    public void eliminar(String dni) {
        Empleado empleadoEliminar = buscarEmpleadoPorDni(dni);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no un empleado con ese DNI

        if (empleadoEliminar == null) {
            System.out.println(colorRojo+"\n  Empleado no encontrado."+resetColor);
            return;
        }

        System.out.println("\n  Datos del empleado a eliminar:");
        System.out.println("  "+empleadoEliminar);
        System.out.println("\n  ¿Desea confirmar la eliminacion?\n  1.  Si\n  2. No\n");
        String opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().remove(empleadoEliminar);
            System.out.println(colorVerde+"\n  Eliminacion completada con éxito."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Eliminacion cancelada."+resetColor);
        }
    }

    @Override
    public void modificar(String Dni) {
        Empleado empleadoModificar = buscarEmpleadoPorDni(Dni);
        //Como el metodo de busqueda retorna null si no encuentra nada, se puede usar para verificar si existe o no un empleado con ese DNI

        if (empleadoModificar == null) {
            System.out.println(colorRojo+"\n  Empleado no encontrado."+resetColor);
            return;
        }

        int indiceEmpleadoModificar = super.getPersonas().indexOf(empleadoModificar);

        String opcion;
        do {
            GestorEntradas.limpiarConsola();
            System.out.println("\n  Datos del empleado a modificar:");
            System.out.println(empleadoModificar);

            System.out.println("\n  ¿Qué desea modificar?");
            System.out.println("  1. DNI");
            System.out.println("  2. Nombre");
            System.out.println("  3. Apellido");
            System.out.println("  4. Nacionalidad");
            System.out.println("  5. Domicilio");
            System.out.println("  6. Teléfono");
            System.out.println("  7. Email");
            System.out.println("  8. Usuario");
            System.out.println("  9. Clave");
            System.out.println("  10. Salario");
            System.out.println("  0. Salir");

            opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

            switch (opcion) {
                case "1" -> {
                    pedirDNI(empleadoModificar, "  Ingrese nuevo DNI: ");
                    System.out.println(colorVerde+"  DNI modificado con éxito"+resetColor);
                }
                case "2" -> {
                    pedirNombre(empleadoModificar, "  Ingrese nuevo nombre: ");
                    System.out.println(colorVerde+"  Nombre modificado con éxito"+resetColor);
                }
                case "3" -> {
                    pedirApellido(empleadoModificar, "  Ingrese nuevo apellido: ");
                    System.out.println(colorVerde+"  Apellido modificado con éxito"+resetColor);
                }
                case "4" -> {
                    pedirNacionalidad(empleadoModificar, "  Ingrese nueva nacionalidad: ");
                    System.out.println(colorVerde+"  Nacionalidad modificada con éxito"+resetColor);
                }
                case "5" -> {
                    pedirDomicilio(empleadoModificar, "  Ingrese nuevo domicilio: ");
                    System.out.println(colorVerde+"  Domicilio modificado con éxito"+resetColor);
                }
                case "6" -> {
                    pedirTelefono(empleadoModificar, "  Ingrese nuevo teléfono: ");
                    System.out.println(colorVerde+"  Teléfono modificado con éxito"+resetColor);
                }
                case "7" -> {
                    pedirMail(empleadoModificar, "  Ingrese nuevo email: ");
                    System.out.println(colorVerde+"  Email modificado con éxito"+resetColor);
                }
                case "8" -> {
                    pedirUsuario(empleadoModificar);
                    System.out.println(colorVerde+"  Usuario modificado con éxito"+resetColor);
                }
                case "9" -> {
                    pedirClave(empleadoModificar);
                    System.out.println(colorVerde+"  Clave modificada con éxito"+resetColor);
                }
                case "10" -> {
                    pedirSalario(empleadoModificar, "  Ingrese nuevo salario: ");
                    System.out.println(colorVerde+"  Salario modificado con éxito"+resetColor);
                }
                case "0" -> {
                    System.out.println("  Saliendo...");
                }
                default -> {
                    System.out.println(colorRojo+"  Opción no válida. Intente nuevamente."+resetColor);
                }
            }
            System.out.println("\n  ¿Quiere realizar otra modificación?\n  1. Si \n  2. No\n");
            opcion = GestorEntradas.pedirCadena("  Ingrese opción: ");
        } while (!opcion.equals("0"));

        System.out.println("\n  Empleado modificado:");
        System.out.println(empleadoModificar);
        System.out.println("\n  ¿Desea confirmar los cambios?\n  1. Si \n  2. No\n");

        opcion = GestorEntradas.pedirCadena("  Ingrese una opción: ");

        if (opcion.equals("1")) {
            super.getPersonas().set(indiceEmpleadoModificar, empleadoModificar);
            System.out.println(colorVerde+"\n  Modificación completada."+resetColor);
        } else {
            System.out.println(colorRojo+"\n  Modificación cancelada."+resetColor);
        }
    }

    @Override
    public void listar() {
        System.out.println(colorAmarillo+"\n  Lista de Empleados"+resetColor);
        System.out.println(colorAmarillo+"  ========================="+resetColor);

        if (!super.getPersonas().isEmpty()) {
            for (Empleado e : super.getPersonas()) {
                System.out.println("  "+e);
            }
        } else {
            System.out.println(colorRojo + "  No hay empleado registrados." + resetColor);
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

    //Metodos especificos de Empleado para pedir datos
    //Tienen la misma estructura que los de GestorPersonas
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

    private void pedirUsuario(Empleado empleado){
        boolean usuarioValido = false;
        do {
            try {
                String usuario = GestorEntradas.pedirCadena("  Ingrese nuevo usuario: ");
                if(Verificador.verificarUsuario(usuario)){
                    empleado.setUsuario(usuario);
                    usuarioValido = true;
                }
            } catch (UsuarioInvalidoException e) {
                System.err.println(e.getMessage());
            }
        } while (!usuarioValido);
    }

    private void pedirClave(Empleado empleado){
        boolean claveValida = false;
        do {
            try {
                String clave = GestorEntradas.pedirCadena("  Ingrese nueva clave: ");
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