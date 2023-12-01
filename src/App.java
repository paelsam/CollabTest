import Models.Examen;
import Models.ModeloServidor.ConexionServidor;

public class App {
    public static void main(String[] args) throws Exception {
        ConexionServidor conexionServidor = new ConexionServidor(10000);
    }
}
