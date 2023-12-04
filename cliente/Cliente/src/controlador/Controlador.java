package controlador;

import java.util.ArrayList;

import Models.Examen;
import Models.Pregunta;
import views.Gui;

public class Controlador {
    private Examen examen;
    private Pregunta actual;

    Gui gui;
    conexion con;

    public Controlador() {
        this.gui = new Gui(this);
        this.con = new conexion(gui, this);
        // provicional
        // this.examen = new Examen("andres", 14, "text");

    }

    public void setExamen(Examen examen) {
        this.examen = examen;

    }

    public Examen getExamen() {
        return examen;
    }

    public void sacarPreguntaActual(String indice) {
        ArrayList<Pregunta> preguntas = examen.getPreguntas();
        actual = preguntas.get(Integer.parseInt(indice));

    }

    public void GuardarRespuesta(String respuesta) {
        if (actual.getOpcionCorrecta().equals(respuesta)) {
            for (int i = 0; i < examen.getPreguntas().size(); i++) {
                if (examen.getPreguntas().get(i) == actual) {
                    examen.getPreguntas().get(i).setEstado(true);
                }
            }

        }

    }

    public Pregunta getActual() {
        return actual;
    }

    public void setActual(Pregunta actual) {
        this.actual = actual;
    }

    public Gui getGui() {
        return gui;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public conexion getCon() {
        return con;
    }

    public void setCon(conexion con) {
        this.con = con;
    }

}
