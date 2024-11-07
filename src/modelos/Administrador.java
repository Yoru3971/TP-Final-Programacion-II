package modelos;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Administrador extends Empleado{
    //gonstructor
    public Administrador(Integer dni, String nombre, String apellido, String nacionalidad, String domicilio, String telefono, String mail, String usuario, String clave, Double salario) {
        super(dni, nombre, apellido, nacionalidad, domicilio, telefono, mail, usuario, clave, salario);
    }

    //equals, hashCode y toString
    @Override
    public String toString() {
        return "Administrador{} " + super.toString();
    }

    //metodos
    /// El metodo crearUsuario() deberia estar en el gestorEmpleados, y que la clase administrador tenga acceso al gestor Empleados
    /// Igual con AsignarPermisos()

    /// El metodo hacerBackup deberia ser un metodo que cree una carpeta (con el nombre de la fecha actual) que dentro haga varios archivos.json donde almacene
    /// tanto empleados como clientes como habitaciones y reservas...
    /// Solucion:
    public void hacerBackup(){
        // Obtener la fecha actual en formato yyyy-MM-dd
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nombreCarpeta = fechaActual.format(formatoFecha);

        // Crear la carpeta con el nombre de la fecha
        File carpetaBackup = new File(nombreCarpeta);
        if (!carpetaBackup.exists()) {
            carpetaBackup.mkdir();
        }

        ///Solucion de abajo brindada por ChatGpt
        // Mapear los archivos con las listas a respaldar
        Map<File, List<?>> backups = new HashMap<>();
        backups.put(new File(carpetaBackup, "BackupEmpleados.json"), gestorEmpleados.get(empleados));///Esto es lo unico que me hace ruido, ya que habria que instanciar un gestor en esta clase
        backups.put(new File(carpetaBackup, "BackupClientes.json"), gestorClientes.get(clientes));   ///Creo que este metodo deberia ir en otro lado

        // Guardar cada lista en su respectivo archivo JSON
        for (Map.Entry<File, List<?>> entry : backups.entrySet()) {
            GestorDeArchivos.escribirEnJson(entry.getValue(), entry.getKey());
        }
    }
}