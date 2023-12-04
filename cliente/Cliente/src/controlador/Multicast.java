package controlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import Models.Examen;
import views.Gui;

public class Multicast extends Thread {
    MulticastSocket socketMulticast;
    InetAddress grupo;
    Gui gui;
    Controlador cont;

    public Multicast(Gui gui, Controlador con) {
        this.gui = gui;
        this.cont = con;
        try {
            socketMulticast = new MulticastSocket(10000);
            grupo = InetAddress.getByName("231.0.0.1");
            socketMulticast.joinGroup(grupo);
            start();
        } catch (IOException e) {
            System.out.println("error al crear multicast del cliente");
        }
    }

    private static Object deserializarObjeto(byte[] datos) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(datos);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        // Deserializar los bytes a un objeto
        Object objetoDeserializado = objectInputStream.readObject();

        // Cerrar streams
        objectInputStream.close();
        byteArrayInputStream.close();

        return objetoDeserializado;
    }

    public void resivirExamen() {
        byte[] mensajeRecibir = new byte[6400];
        DatagramPacket dtp = new DatagramPacket(mensajeRecibir, mensajeRecibir.length);
        Examen examen;
        while (true) {
            try {
                socketMulticast.receive(dtp);
                examen = (Examen) deserializarObjeto(dtp.getData());
                System.out.println(examen.getNombre());
                cont.setExamen(examen);
                System.out.println(cont.getExamen().getNombre());

            } catch (IOException e) {
                cerrarMulti();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void resivirMensaje() {
        byte[] mensajeRecibir = new byte[256];
        DatagramPacket dtp = new DatagramPacket(mensajeRecibir, mensajeRecibir.length);
        String mensaje;
        while (true) {
            try {
                socketMulticast.receive(dtp);
                mensaje = new String(dtp.getData());
                gui.mostrarMensaje(mensaje);

            } catch (IOException e) {
                cerrarMulti();
                break;
            }
        }
    }

    public void run() {
        resivirExamen();

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
