
package co.edu.uptc.view;

import co.edu.uptc.interfaces.IView;
import java.util.Scanner;

public class ConsoleView implements IView {
    private Scanner scanner;

    public ConsoleView() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Entrada no válida. " + prompt);
            scanner.next();
        }
        return scanner.nextDouble();
    }

    @Override
    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    @Override
    public int showMenuAndGetOption() {
        System.out.println("\n--- CALCULADORA SOCK ETS ---");
        System.out.println("1. Realizar Operación");
        System.out.println("2. Mostrar historial");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
        return scanner.nextInt();
    }
    @Override
    public String recoveryIP(){
        System.out.println("Retorne la ip");
        return scanner.next();
    }   
}