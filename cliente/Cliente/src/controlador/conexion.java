package controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import views.Gui;

public class conexion {
    Socket cliente;
    ObjectOutputStream salida;
    ObjectInputStream entrada;

    Multicast multicast;
    Gui gui;
    Controlador cont;

    public conexion(Gui gui2, Controlador cont) {
        this.gui = gui2;
        this.cont = cont;
        ejecutarCliente();
    }

    public conexion() {
        ejecutarCliente();
    }

    public void ejecutarCliente() {

        try {
            conectarAlServidor();
            obtenerFlujos();
            procesarConexion();

        } catch (Exception e) {
            gui.mostrarMensaje("error con la conexion con el cliente");
        }
    }

    public void conectarAlServidor() throws IOException {
        gui.mostrarMensaje("intentando establecer conexion...");
        cliente = new Socket("127.0.0.1", 12345);
        gui.mostrarMensaje("Conectando a: " + cliente.getInetAddress());
        multicast = new Multicast(gui, cont);
    }

    public void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream((cliente.getOutputStream()));
        salida.flush();
        entrada = new ObjectInputStream(cliente.getInputStream());
        gui.mostrarMensaje("se obtuvieron los flujos E/S");
    }

    public void procesarConexion() throws IOException {
        String mensaje = "";
        do {
            try {
                mensaje = (String) entrada.readObject();
                gui.mostrarMensaje(mensaje);
            } catch (Exception e) {
                gui.mostrarMensaje("error tipo de dato incorrecto");

            }
        } while (!mensaje.equals("SERVIDOR>>TERMINAR"));

    }

    public void cerrarConexion() {
        gui.mostrarMensaje("cerrando conexion...");
        try {
            salida.close();
            entrada.close();
            cliente.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarDatos(String mensaje) {
        try {
            salida.writeObject("" + mensaje);
            salida.flush();
        } catch (Exception e) {
            gui.mostrarMensaje("Error al mandar datos al servidor");
        }
    }

}
