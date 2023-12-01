import Models.ModeloServidor.ConexionServidor;
import Views.GUI;

public class App {
    public static void main(String[] args) throws Exception {
        GUI gui = new GUI();
        ConexionServidor con = new ConexionServidor(gui, 12345);

    }
}
