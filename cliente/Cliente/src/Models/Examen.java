package Models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Examen implements Serializable {
    private String nombre;
    private int tiempoDuracion; // en minutos
    private List<String> listaIntegrantes;
    private float notaFinal;
    private int contador = 0;
    private String[] datos;
    private String nombreArchivo;
    private ArrayList<String> aux;
    private ArrayList<ArrayList<String>> listaPreguntas;
    private ArrayList<Pregunta> preguntas;

    public Examen() {

    }

    public Examen(String nombre, int tiempoDuracion, String nombreArchivo) {
        this.nombre = nombre;
        this.tiempoDuracion = tiempoDuracion;
        this.nombreArchivo = nombreArchivo;
        cargarExamen();
        // this.listaIntegrantes = listaIntegrantes;
    }

    public String leerArchivo() {
        BufferedReader br;
        FileReader fr;
        File archivo;
        String linea;
        String contenido = "";

        archivo = new File(
                "C:\\Users\\andres\\Desktop\\tercer semestre\\POE\\PROYECTO FINAL\\cliente\\Cliente\\src\\assets\\preguntas\\"
                        + nombreArchivo + ".txt");
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                contenido += "\n" + linea;
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            return "No se encontró ningun archivo";
        } catch (IOException e) {
            return "No se pudo leer la linea";
        }
        return contenido;

    }

    public void obtenerDatos(String content) {

        listaPreguntas = new ArrayList<>();
        datos = content.split(",");
        int elementosPorSubarray = 7;
        contador = 0;

        for (int i = 0; i < datos.length / elementosPorSubarray; i++) {
            aux = new ArrayList<>();

            for (int j = 0; j < datos.length; j++) {
                if (j >= contador && j < (contador + 7)) {
                    aux.add(datos[j]);
                }
            }
            listaPreguntas.add(aux);
            contador += 7;
        }
    }

    public void cargarExamen() {
        preguntas = new ArrayList<>();
        String contenido = leerArchivo();
        obtenerDatos(contenido);
        for (ArrayList<String> pregunta : listaPreguntas) {
            String enunciado = pregunta.get(0);
            ArrayList<String> listadoOpciones = new ArrayList<>(pregunta.subList(2, 6));
            String opcionCorrecta = pregunta.get(6);
            String descripcion = pregunta.get(1);
            Pregunta pre = new Pregunta(enunciado, listadoOpciones, opcionCorrecta, descripcion);
            preguntas.add(pre);
        }
    }

    // Getters y setters para acceder y modificar los atributos

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoDuracion() {
        return tiempoDuracion;
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public List<String> getListaIntegrantes() {
        return listaIntegrantes;
    }

    public void setListaIntegrantes(List<String> listaIntegrantes) {
        this.listaIntegrantes = listaIntegrantes;
    }

    public float getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(float notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String convertirExamenString() {
        return "Examen:";
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void agregarIntegrantes(String nuevoIntegrante) {
        listaIntegrantes.add(nuevoIntegrante);
    }

    public String mostrarDatos() {
        return "nombre:" + nombre + "\ntiempo: " + tiempoDuracion + "\nNo. preguntas:" + preguntas.size();
    }

    public String mostrarEstadoPreguntas() {
        String resultado = "";
        for (Pregunta p : preguntas) {
            resultado += p.getEstado() + "\n";
        }
        return resultado;
    }

}