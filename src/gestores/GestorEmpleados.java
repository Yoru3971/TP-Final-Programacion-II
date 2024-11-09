package gestores;

import modelos.Empleado;

import java.util.ArrayList;
import java.util.Scanner;

public class GestorEmpleados implements IGestionable<Empleado> {
    private ArrayList<Empleado> empleados;

    public GestorEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public GestorEmpleados() {
        empleados = new ArrayList<>();
    }


    //Faltan verificaciones
    @Override
    public void agregar(Empleado empleado) {
        empleados.add(empleado);
    }

    @Override
    public void eliminar(Empleado empleado) {
        empleados.remove(empleado);

    }

    //Algo a tener en cuenta, este metodo lo va a suar el admin para modificar empleados, pero si un empleado quiere
    //cambiar su usuario o clave, hay que hacerlo aparte, probablemente como metodo dentro de la clase Menu (que llama
    //a los metodos menuRecepcionista y menuAdmin
    @Override
    public void modificar(Empleado empleado) {
        Scanner scanner = new Scanner(System.in);

        int index = buscarIndiceEmpleado(empleado);

        if (index == -1) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        Empleado empleadoModificado = empleados.get(index); // Obtener el empleado original

        int opcion;
        do {
            System.out.println("\n¿Qué desea modificar?");
            System.out.println("1. Usuario");
            System.out.println("2. Clave");
            System.out.println("3. Salario");
            System.out.println("0. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nuevo usuario: ");
                    String nuevoUsuario = scanner.nextLine();
                    empleadoModificado.setUsuario(nuevoUsuario);
                    break;
                case 2:
                    System.out.print("Ingrese nueva clave: ");
                    String nuevaClave = scanner.nextLine();
                    empleadoModificado.setClave(nuevaClave);
                    break;
                case 3:
                    System.out.print("Ingrese nuevo salario: ");
                    Double nuevoSalario = scanner.nextDouble();
                    empleadoModificado.setSalario(nuevoSalario);
                    break;
                case 0:
                    System.out.println("Saliendo.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);

        empleados.set(index, empleadoModificado); // Guardar los cambios del empleado modificado
        System.out.println("Modificación completada.");
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
