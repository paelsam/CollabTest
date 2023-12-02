package Models.ModeloServidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Models.Examen;
import Views.GUI;

public class HiloCliente extends Thread {
    ObjectOutputStream salida;
    ObjectInputStream entrada;
    Socket socket;

    int idCliente;
    GUI servidorGUI;
    Multicast multicast;

    public HiloCliente(GUI gui, Socket s, int id, Multicast multicast) {
        this.servidorGUI = gui;
        socket = s;
        idCliente = id;
        this.multicast = multicast;

    }

    @Override
    public void run() {
        try {
            procesarConexion();
        } catch (IOException e) {
            System.out.println("error al porecesaer la comuncacion con el cliente");
        } finally {
            cerrarConexion();
        }

    }

    public void obtetenerFlujos() throws IOException {
        salida = new ObjectOutputStream(socket.getOutputStream());
        salida.flush();
        entrada = new ObjectInputStream(socket.getInputStream());
        servidorGUI.mostrarMensaje("se obtubieron flujos para la comunicacion");

    }

    public void enviarDatos(String mensaje) {
        try {
            salida.writeObject("SERVIDOR>> " + mensaje);
            salida.flush();
            servidorGUI.mostrarMensaje("SERVIDOR>> " + mensaje);
        } catch (IOException e) {

            servidorGUI.mostrarMensaje("error al escribir el objeto");
        }
    }

    public void enviarExamen(Examen examen) {
        try {
            salida.writeObject(examen);
            salida.flush();
            servidorGUI.mostrarMensaje("examen enviado");
        } catch (IOException e) {
            System.out.println("error al enviar examen");
        }
    }

    public void procesarConexion() throws IOException {
        String mensaje = "conexion exitosa";
        enviarDatos(mensaje);

        do {
            try {
                mensaje = (String) entrada.readObject();
                servidorGUI.mostrarMensaje("\nDesde El Cliente" + mensaje);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!mensaje.equals("TERMINAR"));
    }

    public void cerrarConexion() {
        servidorGUI.mostrarMensaje("\nTerminamos conexion");
        try {
            salida.close();
            entrada.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
