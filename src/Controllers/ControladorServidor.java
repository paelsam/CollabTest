package Controllers;

import java.awt.Color;
import java.util.ArrayList;

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
        ControladorServidor.informeExamenes.cargarHistorial();
        gui = new GUI();
        servidor.start();
        gui.iniciarComponentes();
    }

    public static void serverLoop() {
        while (true) {
            if (gui.getPExamenes().isShowing()) {
                // Hay que cambiar esto
                gui.cambiarColorCirclesLabel(servidor.verificarEstudiantesActivos(), Color.GREEN);
            }
        }

    }

    public static void crearExamen() {
        int tiempoExamenSegundos = (gui.getTiempoMinutos() * 60) + gui.getTiempoSegundos();
        Examen nuevoExamen = new Examen(gui.getTfNombreExamen(), tiempoExamenSegundos, gui.getRutaPreguntas());
        informeExamenes.addExamen(nuevoExamen);
        gui.mostrarMensaje("Examen añadido con éxito", 1);
    }

    public static String[] getNombreExamenes() {
        String[] nombreExamenes = new String[informeExamenes.getExamenes().size()];
        for (int i = 0; i < informeExamenes.getExamenes().size(); i++) {
            nombreExamenes[i] = informeExamenes.getExamenes().get(i).getNombre();
        }
        return nombreExamenes;
    }

    public static Examen getExamenByName(String name) {
        for (Examen examen : informeExamenes.getExamenes()) {
            if (examen.getNombre().equals(name))
                return examen;
            continue;
        }
        return null;
    }

    public static void enviarExamenMulticast(Examen examen) {
        System.out.println("Examen enviado: " + examen.getNombre());
        servidor.getMulticast().enviarMensaje(examen);
    }

    public static String[] getNombreHistorialExamenes() {
        String[] nombreExamenes = new String[informeExamenes.getHistorialExamenes().size()];
        ArrayList<Examen> examenes = informeExamenes.getHistorialExamenes();
        for (int i = 0; i < examenes.size(); i++) {
            nombreExamenes[i] = examenes.get(i).getNombre();
        }
        return nombreExamenes;
    }

    public static GUI getGui() {
        return gui;
    }

    public static void setGui(GUI gui) {
        ControladorServidor.gui = gui;
    }

    public static InformeExamenes getInformeExamenes() {
        return informeExamenes;
    }

    public static void setInformeExamenes(InformeExamenes informeExamenes) {
        ControladorServidor.informeExamenes = informeExamenes;
    }

}