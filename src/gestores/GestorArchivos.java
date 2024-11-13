package gestores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GestorArchivos {

    public static <T> void escribirArregloEnArchivo(ArrayList<T> arreglo, String nombreArchivo) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(arreglo, writer);
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
