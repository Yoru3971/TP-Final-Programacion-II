package gestores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestorEntradas {
    private static final Scanner scanner = new Scanner(System.in);

    public static String pedirCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static Integer pedirEntero(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextInt();
    }

    public static Double pedirDouble(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextDouble();
    }

    public static void limpiarBuffer() {
        scanner.nextLine();
    }

    public static LocalDate pedirFecha(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(input, formatter);
    }

}