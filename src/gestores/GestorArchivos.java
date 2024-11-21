package gestores;

import com.google.gson.*;
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

    //Gson centralizado con adaptador para LocalDate
    private static final Gson gson = new GsonBuilder()
            //Serializador para pasar atributos de tipo LocalDate a String para escribir en archivo
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                    (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
            //Deserualizador para pasar atributos de tipo String a LocalDate leyendo de un archivo
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                    (json, type, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
            .create();

    //Metodo que llama el Admin para hacer un backup real de los datos
    public static void hacerBackup(HashMap<String, ArrayList<?>> datosBackup) throws IOException {
        //Se obtiene la fecha actual para nombrar la carpeta
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nombreCarpeta = "Backup_" + fechaActual.format(formatoFecha);

        //Se crea la carpeta de backup
        File carpetaBackup = new File(nombreCarpeta);
        if (!carpetaBackup.exists() && !carpetaBackup.mkdirs()) {
            throw new IOException("\n  No se pudo crear la carpeta de backup.");
        }

        //Se guarda cada lista en su archivo correspondiente
        for (Map.Entry<String, ArrayList<?>> entry : datosBackup.entrySet()) {
            String rutaArchivo = new File(carpetaBackup, entry.getKey() + ".json").getAbsolutePath();

            //Chequea si la lista que esta serializando es la de habitaciones, para pasarle true como 3er parametro
            escribirArregloEnArchivo(entry.getValue(), rutaArchivo, entry.getKey().equals("habitaciones"));
        }

        System.out.println("\n  Datos guardados en la carpeta: " + "'"+nombreCarpeta+"'");
    }

    //Metodos para escribir y leer arreglos
    public static <T> void escribirArregloEnArchivo(ArrayList<T> arreglo, String nombreArchivo, boolean usarAdaptadorLocalDate) {
        if (arreglo == null || arreglo.isEmpty()) {
            System.out.println("El arreglo está vacío. No se escribirá el archivo.");
            return;
        }

        Gson gsonToUse = gson;
        if (usarAdaptadorLocalDate) {
            gsonToUse = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                            (src, typeOfSrc, context) -> new JsonPrimitive(src.toString())) //Serializador LocalDate
                    .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                            (json, type, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString())) //Deserializador LocalDate
                    .create();
        }

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            gsonToUse.toJson(arreglo, writer);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    public static <T> ArrayList<T> leerArregloDeArchivo(String nombreArchivo, Class<T> clase) {
        ArrayList<T> elementos = new ArrayList<>();

        try (FileReader reader = new FileReader(nombreArchivo)) {
            //Usamos TypeToken para deserializar un ArrayList generico
            Type listType = TypeToken.getParameterized(ArrayList.class, clase).getType();
            elementos = gson.fromJson(reader, listType);

            if (elementos == null || elementos.isEmpty()) {
                System.out.println("\n  La base de datos está vacía");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return elementos;
    }
}