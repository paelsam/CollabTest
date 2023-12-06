package Models.ModeloServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Models.Examen;

public class ConexionServidor extends Thread {

    ServerSocket servidor;
    ArrayList<Examen> examenes;
    private ArrayList<HiloEstudiante> estudiantes;
    int numeroEstudiantes = 0;

    // Para el multicast
    Multicast multicast;

    /**
     * Función que maneja la conexión al servidor
     * 
     * @param PUERTO
     */
    public ConexionServidor(int PUERTO) {
        this.examenes = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
        multicast = new Multicast();
        try {
            System.out.println("Conectando por el puerto " + PUERTO);
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado " + servidor);
        } catch (IOException error) {
            System.out.println("Error al conectar al ServerSocket: ");
            System.out.println(error);
        }
    }

    @Override
    public void run() {

        while (numeroEstudiantes < 3) {
            try {
                adicionarEstudiante(servidor.accept());
            } catch (IOException error) {
                System.out.println("Error al aceptar estudiantes");
                System.out.println(error);
            }
        }

    }

    public void addExamen(Examen examen) {
        this.examenes.add(examen);
    }

    public void adicionarEstudiante(Socket socket) {
        numeroEstudiantes++;
        System.out.println("Estudiante #" + numeroEstudiantes + " conectado!");
        HiloEstudiante estudiante = new HiloEstudiante(numeroEstudiantes, socket, multicast);
        estudiantes.add(estudiante);
        System.out.println(estudiantes.size());

        try {
            estudiante.obtenerFlujos();
            estudiante.start();
        } catch (IOException error) {
            System.out.println("Error al abrir flujos");
            System.out.println(error);
        }
    }

    public Multicast getMulticast() {
        return multicast;
    }

    public ArrayList<HiloEstudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<HiloEstudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

}