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
import java.util.Map;

public class GestorArchivos {

    public static void hacerBackup(HashMap<String, ArrayList<?>> datosBackup) throws IOException {
        //Obtener la fecha actual para nombrar la carpeta
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nombreCarpeta = "Backup_" + fechaActual.format(formatoFecha);

        //Crear la carpeta de backup
        File carpetaBackup = new File(nombreCarpeta);
        if (!carpetaBackup.exists() && !carpetaBackup.mkdirs()) {
            throw new IOException("No se pudo crear la carpeta de backup.");
        }

        //Guardar cada lista en su archivo correspondiente
        for (Map.Entry<String, ArrayList<?>> entry : datosBackup.entrySet()) {
            String rutaArchivo = new File(carpetaBackup, entry.getKey() + ".json").getAbsolutePath();
            escribirArregloEnArchivo(entry.getValue(), rutaArchivo);
        }

        System.out.println("Backup completado en la carpeta: " + nombreCarpeta);
    }


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