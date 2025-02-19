package Controllers;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Models.Examen;
import Models.InformeExamenes;
import Models.ModeloServidor.ConexionServidor;
import Views.GUI;

public class ControladorServidor {

    private static GUI gui;
    private static ConexionServidor servidor;
    private static InformeExamenes informeExamenes;

    public static Examen getExamenEscogido() {
        return examenEscogido;
    }

    public static void setExamenEscogido(Examen examenEscogido) {
        ControladorServidor.examenEscogido = examenEscogido;
    }

    private static Examen examenEscogido;
    private static Timer temporizador;

    // Variables para el temporizador
    private static int minutosRestantes, segundosRestantes;

    public ControladorServidor() {
        ControladorServidor.servidor = new ConexionServidor(10000);
        ControladorServidor.informeExamenes = new InformeExamenes();
        ControladorServidor.informeExamenes.cargarHistorial();
        ControladorServidor.gui = new GUI();
        ControladorServidor.temporizador = new Timer();

        servidor.start();
    }

    public static void tiempoRestanteTexto() {
        String tiempoRestante = "";
        if (minutosRestantes < 10)
            tiempoRestante += "0" + minutosRestantes;
        else
            tiempoRestante += minutosRestantes;

        tiempoRestante += ":";

        if (segundosRestantes < 10)
            tiempoRestante += "0" + segundosRestantes;
        else
            tiempoRestante += segundosRestantes;

        if (segundosRestantes == 0) {
            if (minutosRestantes != 0) {
                minutosRestantes--;
                segundosRestantes = 59;
            }
        } else {
            segundosRestantes--;
        }
        gui.setTiempoRestante(tiempoRestante);
    }

    public static void iniciarCuentaRegresiva(int tiempoTotalSegundos) {
        minutosRestantes = (tiempoTotalSegundos / 60);
        segundosRestantes = Math.round(tiempoTotalSegundos % 60);

        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (examenEscogido.estaTerminado()) {
                    temporizador.cancel();
                    temporizador.purge();
                    examenEscogido.setFinExamen(true);
                    examenEscogido.setNotaFinal(examenEscogido.calcularNotaFinal());
                    enviarExamenMulticast(examenEscogido);
                    informeExamenes.addToHistorial(examenEscogido);
                    informeExamenes.guardarHistorial();
                    informeExamenes.cargarHistorial();
                    gui.getpInformesIzquierda().updateUI();
                    System.out.println("Fin del examen");
                }

                if (!(minutosRestantes == 0 && segundosRestantes == 0)) {
                    tiempoRestanteTexto();
                } else {
                    temporizador.cancel();
                    temporizador.purge();
                    examenEscogido.setFinExamen(true);
                    examenEscogido.setNotaFinal(examenEscogido.calcularNotaFinal());
                    enviarExamenMulticast(examenEscogido);
                    informeExamenes.addToHistorial(examenEscogido);
                    informeExamenes.guardarHistorial();
                    informeExamenes.cargarHistorial();
                    gui.getpInformesIzquierda().updateUI();
                    System.out.println("Fin del examen");

                }
            }
        }, 0, 1000);
    }

    public static void estudiantesActivos() {
        while (true) {
            if (gui.getPExamenes().isShowing()) {
                gui.cambiarColorCircularLabels();
            }
        }
    }

    public static void crearExamen() {
        int tiempoExamenSegundos = (gui.getTiempoMinutos() * 60) + gui.getTiempoSegundos();
        Examen nuevoExamen = new Examen(gui.getTfNombreExamen(), tiempoExamenSegundos, gui.getRutaPreguntas());
        informeExamenes.addExamen(nuevoExamen);
        gui.mostrarMensaje("Examen añadido con éxito", 1);
    }

    public static String[] getNombreExamenes() {
        String[] nombreExamenes = new String[informeExamenes.getExamenes().size()];
        for (int i = 0; i < informeExamenes.getExamenes().size(); i++)
            nombreExamenes[i] = informeExamenes.getExamenes().get(i).getNombre();
        return nombreExamenes;
    }

    public static Examen getExamenByName(String nombreExamen) {
        for (Examen examen : informeExamenes.getExamenes()) {
            if (examen.getNombre().equals(nombreExamen))
                return examen;
            continue;
        }
        return null;
    }

    public static void enviarExamenMulticast(Examen examen) {
        System.out.println("Examen enviado: " + examen.getNombre());
        servidor.getMulticast().enviarMensaje(examen);
    }

    public static void iniciarExamen(String nombreExamen) {
        if (servidor.getEstudiantes().size() >= 1) {
            examenEscogido = getExamenByName(nombreExamen);
            iniciarCuentaRegresiva(examenEscogido.getTiempoDuracion());
            enviarExamenMulticast(examenEscogido);
        } else
            gui.mostrarMensaje("Para iniciar el examen deben estar 3 estudiantes conectados (Hay "
                    + servidor.getEstudiantes().size() + " estudiantes).", 0);
    }

    public static String[] getNombreHistorialExamenes() {
        String[] nombreExamenes = new String[informeExamenes.getHistorialExamenes().size()];
        ArrayList<Examen> examenes = informeExamenes.getHistorialExamenes();
        for (int i = 0; i < examenes.size(); i++) {
            nombreExamenes[i] = examenes.get(i).getNombre();
        }
        return nombreExamenes;
    }

    public static GUI getGui() {
        return gui;
    }

    public static void setGui(GUI gui) {
        ControladorServidor.gui = gui;
    }

    public static InformeExamenes getInformeExamenes() {
        return informeExamenes;
    }

    public static void setInformeExamenes(InformeExamenes informeExamenes) {
        ControladorServidor.informeExamenes = informeExamenes;
    }

    public static ConexionServidor getServidor() {
        return servidor;
    }

    public static void setServidor(ConexionServidor servidor) {
        ControladorServidor.servidor = servidor;
    }

}