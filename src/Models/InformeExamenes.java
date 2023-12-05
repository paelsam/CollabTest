package Models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InformeExamenes {

    private ArrayList<Examen> historialExamenes;

    public ArrayList<Examen> getHistorialExamenes() {
        return historialExamenes;
    }

    public void setHistorialExamenes(ArrayList<Examen> historialExamenes) {
        this.historialExamenes = historialExamenes;
    }

    private ArrayList<Examen> examenes;

    public InformeExamenes() {

        this.historialExamenes = new ArrayList<>();
        this.examenes = new ArrayList<>();
    }

    public void addToHistorial(Examen examenRealizado) {
        historialExamenes.add(examenRealizado);
    }

    public void addExamen(Examen examen) {
        examenes.add(examen);
    }

    public ArrayList<Examen> getExamenes() {
        return this.examenes;
    }

    public void guardarHistorial() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src\\assets\\examenes.txt"));
            System.out.println("Guardando archivo...");
            out.writeObject(historialExamenes);
            out.close();
        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e);
        }
    }

    /**
     * Carga historial de exámenes hecho a lo largo del tiempo
     */
    public void cargarHistorial() {
        try (FileInputStream fis = new FileInputStream("src\\assets\\examenes.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);) {
            this.historialExamenes = (ArrayList<Examen>) ois.readObject();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo: " + e);
        } catch (ClassNotFoundException clse) {
            System.out.println("Error class: " + clse);
        }
    }

    public String verHistorialExamenes() {
        String resultado = "";
        for (Examen examen : historialExamenes)
            resultado += examen.toString() + "\n----------------------------------\n";
        return resultado;
    }

    public String verHistorialPorExamen(String nombre) {
        String resultado = "";
        for (Examen examen : historialExamenes) {
            if (examen.getNombre().equals(nombre)) {
                resultado += examen.toString();
            }
        }
        return resultado;
    }
}
