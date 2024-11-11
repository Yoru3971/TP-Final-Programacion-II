package gestores;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestorArchivos {

    public static <T> void EscribirArregloEnArchivo(ArrayList<T> arreglo, String nombreArchivo) {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gson.toJson(arreglo, writer);
            System.out.println("Archivo de "+  arreglo.getFirst().getClass().getName()+"s guardado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

    public static <T> ArrayList<T> LeerArregloDeArchivo(T objeto, String nombreArchivo) {
        Gson gson = new Gson();

        ArrayList<T> elementos = null;

        try (FileReader reader = new FileReader(nombreArchivo)) {

            elementos = (ArrayList<T>) gson.fromJson(reader, objeto.getClass());

            System.out.println("Archivo de "+elementos.getFirst().getClass().getName() +"s leido exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return elementos;
    }
}
