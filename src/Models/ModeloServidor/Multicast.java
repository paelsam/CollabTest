package Models.ModeloServidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Multicast {
    private MulticastSocket socket_multicast;
    private DatagramPacket datagrama;
    private byte[] dato;
    private InetAddress grupo;

    public Multicast() {
        try {
            socket_multicast = new MulticastSocket();
            dato = new byte[0];
            grupo = InetAddress.getByName("231.0.0.1");
            datagrama = new DatagramPacket(dato, 0, grupo, 10000);
        } catch (IOException e) {
            System.out.println("error al crear el socket de multicast");

        }

    }

    public boolean enviarMensajeMulticast(String mensajeAMandar) {
        if (mensajeAMandar.isEmpty()) {
            System.out.println("el mensaje estaba vacio");
            return false;
        } else {
            try {
                byte[] temporal = mensajeAMandar.getBytes();
                datagrama.setData(temporal);
                datagrama.setLength(temporal.length);
                socket_multicast.send(datagrama);
                return true;
            } catch (Exception e) {
                System.out.println("error al mandar el datagrama");
                return false;
            }

        }
    }
}
