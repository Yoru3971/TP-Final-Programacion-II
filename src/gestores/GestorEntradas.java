package gestores;

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
}
