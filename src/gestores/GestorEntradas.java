package gestores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestorEntradas {

    public static String pedirCadena(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        String palabra = scanner.nextLine();
        return palabra;
    }

    public static Integer pedirEntero(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public static Double pedirDouble(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        return scanner.nextDouble();
    }

    public static void limpiarBuffer() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static LocalDate pedirFecha(String mensaje) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensaje);
        String input = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, formatter);
    }

    public static void limpiarConsola() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.err.println("Error al intentar limpiar la consola.");
        }
    }
}