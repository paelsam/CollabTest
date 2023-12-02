package Models.ModeloServidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Models.Examen;

public class HiloEstudiante extends Thread 
{
    ObjectInputStream entrada;    
    ObjectOutputStream salida;
    Multicast multicast;
    Socket socket;

    private int idEstudiante;

    public HiloEstudiante(int idEstudiante, Socket socket,  Multicast multicast)
    {
        this.idEstudiante = idEstudiante;
        this.socket = socket;
        this.multicast = multicast;
    }

    @Override
    public void run() {
        try {
            procesarConexion();
        } catch ( IOException e ) {
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
    public void procesarConexion() throws IOException
    {
        do {
            try {
                Examen examen = (Examen) entrada.readObject();

                System.out.println(examen.getPreguntas().get(0).getEstado());

                if ( !examen.estaTerminado() ) {
                    multicast.enviarMensaje(examen);
                    break;
                } else {
                    System.out.println("Quisiera ser una mosca...");
                }
            } catch (ClassNotFoundException error) {
                System.out.println("Se recibió un tipo de dato incorrecto: " + error);
                break;
            } catch ( IOException IOError ) {
                System.out.println("El Estudiante #" + idEstudiante + " se fue...");
                break;
            }
        } while (!socket.isClosed());
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