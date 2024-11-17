package gestores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorArchivos {

//    public static void hacerBackup(Map<String, ArrayList<?>> datosBackup){
//        // Obtener la fecha actual en formato yyyy-MM-dd
//        LocalDate fechaActual = LocalDate.now();
//        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String nombreCarpeta = "Backup_" + fechaActual.format(formatoFecha);
//
//        // Crear la carpeta con el nombre de la fecha
//        File carpetaBackup = new File(nombreCarpeta);
//        if (!carpetaBackup.exists()) {
//            carpetaBackup.mkdir();
//        }
//
//        // Guardar cada lista en su respectivo archivo JSON
//        for (Map.Entry<String, ArrayList<?>> entry : datosBackup.entrySet()) {
//            // Obtener la ruta del archivo como String
//            String rutaArchivo = new File(carpetaBackup, entry.getKey() + ".json").getAbsolutePath();
//            // Escribir la lista en el archivo
//            escribirArregloEnArchivo(entry.getValue(), rutaArchivo);
//        }
//
//        System.out.println("Backup realizado exitosamente en la carpeta: " + nombreCarpeta);
//    }

    ///Metodo testeado, no funciona solucionar

    public static <T> void escribirArregloEnArchivo(ArrayList<T> arreglo, String nombreArchivo) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(arreglo, writer);
            //Eliminar, esto sirve para testing
            System.out.println("Archivo de "+  arreglo.getFirst().getClass().getName()+"s guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

    public static <T> ArrayList<T> leerArregloDeArchivo(String nombreArchivo, Class<T> clase) {
        Gson gson = new Gson();

        ArrayList<T> elementos = new ArrayList<>();

        try (FileReader reader = new FileReader(nombreArchivo)) {
            // Utilizar TypeToken para deserializar un ArrayList del tipo T
            Type listType = TypeToken.getParameterized(ArrayList.class, clase).getType();
            elementos = gson.fromJson(reader, listType);

            if (elementos != null && !elementos.isEmpty()) {
                System.out.println("Archivo de " + clase.getSimpleName() + "s leído exitosamente.");
            } else {
                System.out.println("El archivo está vacío o no contiene elementos válidos.");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return elementos;
    }
}