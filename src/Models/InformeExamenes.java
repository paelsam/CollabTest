package Models;

import java.util.ArrayList;

public class InformeExamenes {

    private ArrayList<Examen> examenes;

    public InformeExamenes() {
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
