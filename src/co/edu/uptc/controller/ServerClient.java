package co.edu.uptc.controller;

import co.edu.uptc.interfaces.IView;
import co.edu.uptc.view.ConsoleView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Properties;

public class ServerClient {

    private static final int PORT = 1234;
    private IView view;

    public ServerClient() {
        this.view = new ConsoleView();
    }

    public void start() {
        String ip = view.recoveryIP();
        try (
                Socket socket = new Socket(ip, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            view.showMessage("Servidor: " + input.readUTF());

            menuPrincipal(input, output);
        } catch (IOException e) {
            view.showMessage("Error de conexión con el servidor: " + e.getMessage());
        }
    }

    private void menuPrincipal(DataInputStream input, DataOutputStream output) throws IOException {

        boolean isRunning = true;
        while (isRunning) {
            int option = view.showMenuAndGetOption();
            switch (option) {
                case 1:
                    calculateMenu(input, output);
                    break;
                case 2:
                    requestHistory(input, output);
                    break;
                case 3:
                    isRunning = false;
                    view.showMessage("Cerrando cliente...");
                    break;

                default:
                    break;
            }
        }

    }

    private void calculateMenu(DataInputStream input, DataOutputStream output) throws IOException {
        output.writeInt(1);
        double num1 = view.readDouble("Ingrese el primer número: ");
        String op = view.readString("Ingrese el operador (+, -, *, /): ");
        double num2 = view.readDouble("Ingrese el segundo número: ");

        output.writeDouble(num1);
        output.writeUTF(op);
        output.writeDouble(num2);

        boolean isSuccess = input.readBoolean();
        if (isSuccess) {
            double result = input.readDouble();
            view.showMessage("Resultado: " + result);
        } else {
            String errorMsg = input.readUTF();
            view.showMessage("Error recibido del servidor: " + errorMsg);
        }
    }

    private void requestHistory(DataInputStream input, DataOutputStream output) throws IOException {
        output.writeInt(2);
        output.flush();

        int size = input.readInt();
        if (size == 0) {
            view.showMessage("No se han hecho operaciones");
        } else {
            for (int i = 0; i < size; i++) {
                double result = input.readDouble();
                view.showMessage((i + 1) + ": " + result);
            }
        }
    }
}