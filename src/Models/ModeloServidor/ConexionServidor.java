package Models.ModeloServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Models.Examen;
import Models.InformeExamenes;

public class ConexionServidor extends Thread {

    ServerSocket servidor;
    ArrayList<Examen> examenes; 
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
        while (true) {
            try {
                if ( numeroEstudiantes <= 2 )
                    adicionarEstudiante(servidor.accept());
                else 
                    break;
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
        HiloEstudiante estudiante = new HiloEstudiante( numeroEstudiantes, socket, multicast);

        InformeExamenes informeExamenes = new InformeExamenes();

        Examen examen = new Examen("Mi primer examen", 20, "src\\assets\\preguntas\\preguntas1.txt");
        addExamen(examen);
        informeExamenes.cargarHistorial();
        System.out.println(informeExamenes.verHistorialExamenes());
        // informeExamenes.addToHistorial(examen);
        // informeExamenes.guardarHistorial();

        


        try {
            estudiante.obtenerFlujos();
            estudiante.start();
            estudiante.enviarExamen(examen);
        } 
        catch (IOException error) {
            System.out.println("Error al abrir flujos");
            System.out.println(error);
        }
    }

}