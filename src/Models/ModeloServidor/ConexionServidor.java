package Models.ModeloServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConexionServidor extends Thread {

    ServerSocket servidor;
    int numeroEstudiantes = 0;

    // Para el multicast
    Multicast multicast;

    /**
     * Función que maneja la conexión al servidor
     * 
     * @param PUERTO
     */
    public ConexionServidor(int PUERTO) {
        try {
            servidor = new ServerSocket(PUERTO);
            start();
        } catch (IOException error) {
            System.out.println("Error al conectar al ServerSocket: ");
            System.out.println(error);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                adicionarEstudiante(servidor.accept());
            } catch (IOException error) {
                System.out.println("Error al aceptar estudiantes");
                System.out.println(error);
            }
        }
    }

    public void adicionarEstudiante(Socket socket) {
        numeroEstudiantes++;
        HiloEstudiante estudiante = new HiloEstudiante( numeroEstudiantes, socket, multicast);
        // try {
            // estudiante.obtenerFlujos();
            estudiante.start();
        // } 
        // catch (IOException error) {
        //     System.out.println("Error al abrir flujos");
        //     System.out.println(error);
        // }
    }

}