package controlador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import views.Gui;

public class Multicast extends Thread {
    MulticastSocket socketMulticast;
    InetAddress grupo;
    Gui gui;

    public Multicast(Gui gui) {
        this.gui = gui;
        try {
            socketMulticast = new MulticastSocket(10000);
            grupo = InetAddress.getByName("231.0.0.1");
            socketMulticast.joinGroup(grupo);
            start();
        } catch (IOException e) {
            System.out.println("error al crear multicast del cliente");
        }
    }

    public void run() {
        byte[] mensajeRecibir = new byte[256];
        DatagramPacket dtp = new DatagramPacket(mensajeRecibir, mensajeRecibir.length);
        String salida;
        while (true) {
            try {
                socketMulticast.receive(dtp);
                salida = new String(dtp.getData());
                gui.mostrarMensaje("\n" + salida);
            } catch (IOException e) {
                cerrarMulti();
                break;
            }
        }

    }

    public void cerrarMulti() {
        try {
            socketMulticast.leaveGroup(grupo);
            socketMulticast.close();
        } catch (IOException e) {
            System.out.println("error al cerrar el socket multicast");
        }
    }
}
