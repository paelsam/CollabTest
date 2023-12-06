package Models.ModeloServidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Models.Examen;

public class HiloEstudiante extends Thread {
    ObjectInputStream entrada;
    ObjectOutputStream salida;
    Multicast multicast;
    public Socket socket;

    private int idEstudiante;

    public HiloEstudiante(int idEstudiante, Socket socket, Multicast multicast) {
        this.idEstudiante = idEstudiante;
        this.socket = socket;
        this.multicast = multicast;
    }

    @Override
    public void run() {
        try {
            procesarConexion();
        } catch (IOException e) {
            System.out.println("Error al procesar la conexión: " + e);
        } finally {
            cerrarConexion();
        }
    }

    public void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(socket.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    // Recibe mensajes desde el cliente
    public void procesarConexion() throws IOException {
        do {
            try {
                System.out.println("Procesando...");
                Examen examen = (Examen) entrada.readObject();
                multicast.enviarMensaje(examen);
            } catch (ClassNotFoundException error) {
                System.out.println("Se recibió un tipo de dato incorrecto: " + error);
                break;
            } catch (IOException IOError) {
                if (!socket.isClosed())
                    System.out.println("Estudiante #" + idEstudiante + " se fue...");
                break;
            }
        } while (!socket.isClosed());
    }

    public boolean cerrarConexion() {
        try {
            salida.close();
            entrada.close();
            socket.close();
            return true;
        } catch (IOException IOError) {
            System.out.println("Ocurrió un error al cerrar la conexión: ");
            System.out.println(IOError);
            return false;
        }
    }

}