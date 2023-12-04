package Models;

import java.io.Serializable;
import java.util.List;

public class Pregunta implements Serializable {
    public String getEnunciado() {
        return enunciado;
    }

    public List<String> getListadoOpciones() {
        return listadoOpciones;
    }

    public String getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    private final String enunciado;
    private List<String> listadoOpciones;
    private final String opcionCorrecta;
    private final String descripcion;
    private boolean estado;
    private String respondidoPor;
    private boolean esCorrecta;

    public Pregunta(String enunciado, List<String> listadoOpciones, String opcionCorrecta, String descripcion) {
        this.enunciado = enunciado;
        this.listadoOpciones = listadoOpciones;
        this.opcionCorrecta = opcionCorrecta;
        this.descripcion = descripcion;
        this.estado = false;
        this.respondidoPor = "";
        this.esCorrecta = false;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getRespondidoPor() {
        return respondidoPor;
    }

    public void setRespondidoPor(String respondidoPor) {
        this.respondidoPor = respondidoPor;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public void verificarOpcion(String opcion) {
        // codigo
    }

}