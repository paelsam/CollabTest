package Controllers;

import java.util.ArrayList;

import Models.Examen;
import Views.GUI;

public class Controlador {
    public static ArrayList<Examen> Examenes;
    static GUI gui;

    public Controlador() {
        gui = new GUI();
        Examenes = new ArrayList<>();
    }

    public static void crearExamen() {

        Examenes.add(new Examen(gui.tNombre.getText(), Integer.parseInt(gui.tTiempo.getText()), gui.tRuta.getText()));
    }

    public static void main(String[] args) {
        Controlador cont = new Controlador();

    }

}