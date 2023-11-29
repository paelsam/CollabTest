package Models;

import java.util.ArrayList;

public class HistorialExamenes {

    private ArrayList<Examen> examenes;

    public HistorialExamenes() {
        this.examenes = new ArrayList<>();
    }

    public void guardarHistorial() {
        System.out.println("Exámenes");
        System.out.println(examenes);
    }

    public void cargarHistorial() {
        // TODO: Crear crear un archivo que guarde el historial
    }
}
