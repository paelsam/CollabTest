import Controllers.ControladorServidor;

public class App {
    public static void main(String[] args) throws Exception {
        ControladorServidor controladorServidor = new ControladorServidor();
        ControladorServidor.serverLoop();
    }
}
