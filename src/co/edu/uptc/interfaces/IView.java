package co.edu.uptc.interfaces;

/**
 * IView
 */
public interface IView {
    void showMessage(String message);

    double readDouble(String prompt);

    String readString(String prompt);

    int showMenuAndGetOption();

    String recoveryIP();
}