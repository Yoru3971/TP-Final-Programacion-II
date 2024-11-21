package gestores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class GestorEntradas {

    //Desde aca se centraliza la pedida de datos en tddo el proyecto

    public static String pedirCadena(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static Integer pedirEntero(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public static LocalDate pedirFecha(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        String input = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaParseada = null;

        try{
            fechaParseada = LocalDate.parse(input, formatter);
        }catch(DateTimeParseException e){
            return null;
        }

        return fechaParseada;
    }

    //Una vez que decidimos implementar la consola, se pueden usar comandos como clear screen para mantener la ejecucion del programa mas limpia
    public static void limpiarConsola() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("  Error al intentar limpiar la consola.");
        }
    }

    public static void pausarConsola() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n  Presiona Enter para continuar...");
        scanner.nextLine();
    }
}