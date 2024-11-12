package gestores;

import modelos.Cliente;
import modelos.Empleado;
import modelos.Recepcionista;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorEmpleados implements IGestionable<String> {
    private ArrayList<Empleado> empleados;
    private Scanner scanner = new Scanner(System.in);

    public GestorEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public GestorEmpleados() {
        empleados = new ArrayList<>();
    }

    //Faltan verificaciones
    @Override
    public void agregar() {
        System.out.println("Ingrese los datos del nuevo empleado:");

        Integer dni = pedirDni();
        String nombre = pedirNombre();
        String apellido = pedirApellido();
        String nacionalidad = pedirNacionalidad();
        String domicilio = pedirDomicilio();
        String telefono = pedirTelefono();
        String mail = pedirMail();
        String usuario = pedirUsuario();
        String clave = pedirClave();
        Double salario = pedirSalario();

        //Esto deberia poder hacerse de manera generica, pero por ahora esta solo hecho para recepcionistas
        Recepcionista nuevoEmpleado = new Recepcionista(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail, usuario, clave, salario);
        empleados.add(nuevoEmpleado);
        System.out.println("Empleado agregado con éxito.");
    }

    public void eliminar(String dni) {
        Empleado empleadoEliminar = buscarPorDni(dni);

        System.out.println("Confirmar eliminacion del empleado: ");
        System.out.println(empleadoEliminar);

        System.out.println("1. Si");
        System.out.println("2. No");

        if(scanner.nextInt() == 1){
            empleados.remove(empleadoEliminar);
            System.out.println("Empleado eliminado del sistema con exito.");
        }else{
            System.out.println("El empleado no fue eliminado del sistema.");
        }

        //Hay que ver donde hacemos la eliminacion del archivo (puede ser aca o en el metodo del backup del admin)
    }

    //Algo a tener en cuenta, este metodo lo va a suar el admin para modificar empleados, pero si un empleado quiere
    //cambiar su usuario o clave, hay que hacerlo aparte, probablemente como metodo dentro de la clase Menu (que llama
    //a los metodos menuRecepcionista y menuAdmin

    @Override
    public void modificar(String dni) {

        Empleado empleadoModificado = buscarPorDni(dni);
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
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir nueva línea

            switch (opcion) {
                case 1:
                    System.out.println("No se puede modificar el DNI porque es un atributo final.");
                    break;
                case 2:
                    empleadoModificado.setNombre(pedirNombre());
                    System.out.println("Nombre modificado con éxito");
                    break;
                case 3:
                    empleadoModificado.setApellido(pedirApellido());
                    System.out.println("Apellido modificado con éxito");
                    break;
                case 4:
                    empleadoModificado.setNacionalidad(pedirNacionalidad());
                    System.out.println("Nacionalidad modificado con éxito");
                    break;
                case 5:
                    empleadoModificado.setDomicilio(pedirDomicilio());
                    System.out.println("Domicilio modificado con éxito");
                    break;
                case 6:
                    empleadoModificado.setTelefono(pedirTelefono());
                    System.out.println("Telefono modificado con éxito");
                    break;
                case 7:
                    empleadoModificado.setMail(pedirMail());
                    System.out.println("Mail modificado con éxito");
                    break;
                case 8:
                    empleadoModificado.setUsuario(pedirUsuario());
                    System.out.println("Usuario modificado con éxito");
                    break;
                case 9:
                    empleadoModificado.setClave(pedirClave());
                    System.out.println("Clave modificado con éxito");
                    break;
                case 10:
                    empleadoModificado.setSalario(pedirSalario());
                    System.out.println("Salario modificado con éxito");
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

        System.out.println("\nEmpleado modificado");
        System.out.println(empleadoModificado);
        System.out.println("¿Desea confirmar los cambios?");
        System.out.println("1.Si");
        System.out.println("2.No");

        int confirmar = scanner.nextInt();

        if (confirmar == 1) {
            empleados.set(indiceEmpleadoModificar, empleadoModificado);
            System.out.println("Modificación completada.");
        } else {
            System.out.println("Modificacion cancelada");
        }
    }


    private Empleado buscarPorDni(String dni){
        Empleado empleado = null;
        for(Empleado e : empleados){
            if(e.getDni().equals(dni)){
                empleado = e;
            }
        }
        return empleado;
    }

    // Métodos para pedir cada atributo
    private Integer pedirDni() {
        System.out.print("Ingrese DNI: ");
        return scanner.nextInt();
    }

    private String pedirNombre() {
        System.out.print("Ingrese nombre: ");
        return scanner.nextLine();
    }

    private String pedirApellido() {
        System.out.print("Ingrese apellido: ");
        return scanner.nextLine();
    }

    private String pedirNacionalidad() {
        System.out.print("Ingrese nacionalidad: ");
        return scanner.nextLine();
    }

    private String pedirDomicilio() {
        System.out.print("Ingrese domicilio: ");
        return scanner.nextLine();
    }

    private String pedirTelefono() {
        System.out.print("Ingrese teléfono: ");
        return scanner.nextLine();
    }

    private String pedirMail() {
        System.out.print("Ingrese email: ");
        return scanner.nextLine();
    }

    private String pedirUsuario() {
        System.out.print("Ingrese usuario: ");
        return scanner.nextLine();
    }

    private String pedirClave() {
        System.out.print("Ingrese clave: ");
        return scanner.nextLine();
    }

    private Double pedirSalario() {
        System.out.print("Ingrese salario: ");
        return scanner.nextDouble();
    }

    private int buscarIndiceEmpleado(Empleado empleado) {
        for (Empleado e : empleados) {
            if (e.equals(empleado)) {
                return empleados.indexOf(e);
            }
        }
        return -1;
    }
}
