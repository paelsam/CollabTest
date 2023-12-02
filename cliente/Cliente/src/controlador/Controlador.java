package controlador;

import Models.Examen;
import views.Gui;

public class Controlador {
    private Examen examen;
    Gui gui;
    conexion con;

    public Controlador() {
        this.gui = new Gui(this);
        this.con = new conexion(gui, this);
    }

    public void setExamen(Examen examen) {
        this.examen = examen;

    }

    public Examen getExamen() {
        return examen;
    }

}
