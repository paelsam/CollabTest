package Controllers;

import java.util.ArrayList;

import Models.Examen;
import Models.ModeloServidor.ConexionServidor;
import Views.GUI;

public class Controlador {
    public static ArrayList<Examen> Examenes;
    static GUI gui;

    public Controlador() {
        gui = new GUI();
        Examenes = new ArrayList<>();
        ConexionServidor con = new ConexionServidor(gui, 12345);
    }

    public static void crearExamen() {

        Examenes.add(new Examen(gui.tNombre.getText(), Integer.parseInt(gui.tTiempo.getText()), gui.tRuta.getText()));
    }

    public void setExamen(Examen examen) {
        for (int i = 0; i < Examenes.size(); i++) {
            if (Examenes.get(i) == examen) {
                Examenes.set(i, examen);
            }
        }
    }

    public static void main(String[] args) {
        Controlador cont = new Controlador();

    }

}