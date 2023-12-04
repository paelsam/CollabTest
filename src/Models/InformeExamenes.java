package Models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Controllers.ControladorServidor;

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

    public static void main(String[] args) {

        Examen examen1 = new Examen("andres", 12, "src\\assets\\preguntas1.txt");

        // Examen examen2 = new Examen("caca", 12, "src\\assets\\preguntas1.txt");
        // Examen examen3 = new Examen("oca", 12, "src\\assets\\preguntas1.txt");
        // InformeExamenes info = new InformeExamenes();
        // info.addToHistorial(examen1);
        // info.addToHistorial(examen2);
        // info.addToHistorial(examen3);
        // info.guardarHistorial();
        // InformeExamenes info = new InformeExamenes();
        // info.cargarHistorial();
        // System.out.println(info.verHistorialExamenes());

        // System.out.println(examen1.getPreguntas().get(0).getDescripcion());
        // System.out.println(examen1.getTiempoDuracion());
        System.out.println(examen1.toString());

    }
}
