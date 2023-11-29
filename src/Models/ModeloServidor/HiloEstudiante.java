package Models.ModeloServidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import Models.Examen;

public class HiloEstudiante extends Thread 
{
    ObjectInputStream entrada;    
    ObjectOutputStream salida;
    Multicast multicast;
    Socket socket;

    private int idEstudiante;
    private String nombreEstudiante;

    public HiloEstudiante(int idEstudiante, Socket socket,  Multicast multicast)
    {
        this.idEstudiante = idEstudiante;
        this.socket = socket;
        this.multicast = multicast;
    }

    @Override
    public void run() {
        procesarConexion();
    }

    public void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
        salida.flush();
    }

    public void enviarExamen(Examen examen) {
        try {
            salida.writeObject(examen);
            salida.flush();
        } catch (IOException IOError) {
            System.out.println("Error al enviar el examen: ");
            System.out.println(IOError);
        }
    }

    // Recibe mensajes desde el servidor 
    public Examen procesarConexion()
    {
        do {
            try {
                Examen examen = (Examen) entrada.readObject();
                if ( examen.estaTerminado() ) {
                    multicast.enviarMensaje(examen);
                    break;
                } else {
                    salida.flush();
                }
            } catch (ClassNotFoundException error) {
                System.out.println("Se recibió un tipo de dato incorrecto");
                System.out.println(error);
            } catch ( IOException IOError ) {
                System.out.println("Error al recibir el dato: ");
                System.out.println(IOError);
            }
        } while (!socket.isClosed());
        return null;
    }

    public boolean cerrarConexion() 
    {
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