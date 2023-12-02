package Controllers;


import Models.Examen;
import Models.InformeExamenes;
import Models.ModeloServidor.ConexionServidor;
import Views.GUI;

public class ControladorServidor {

    private static GUI gui;
    private static ConexionServidor servidor;
    private static InformeExamenes informeExamenes;

    public ControladorServidor() {
        ControladorServidor.servidor = new ConexionServidor(10000);
        ControladorServidor.informeExamenes = new InformeExamenes();
        gui = new GUI();
        servidor.start();
        gui.iniciarComponentes();
    }

    public static void crearExamen() {
        int tiempoExamenSegundos = (gui.getTiempoMinutos() * 60) + gui.getTiempoSegundos();
        Examen nuevoExamen = new Examen(gui.getTfNombreExamen(), tiempoExamenSegundos, gui.getRutaPreguntas());
        informeExamenes.addExamen(nuevoExamen);
        gui.mostrarMensaje("Examen añadido con éxito", 1);
    }

}