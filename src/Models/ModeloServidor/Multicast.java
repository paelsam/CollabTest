package Models.ModeloServidor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import Models.Examen;

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

    public boolean enviarExamenMulticast(Examen examen) {
        try {

            byte[] temporal = serializarObjeto(examen);

            datagrama.setData(temporal);
            datagrama.setLength(temporal.length);
            socket_multicast.send(datagrama);
            System.out.println("paso");
            return true;
        } catch (Exception e) {
            System.out.println("error al enviar examen");
            e.printStackTrace();
            return false;
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

    public byte[] serializarObjeto(Examen objeto) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(6400);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(objeto);
        objectOutputStream.flush();

        byte[] datosSerializados = byteArrayOutputStream.toByteArray();

        objectOutputStream.close();
        byteArrayOutputStream.close();

        return datosSerializados;
    }

    public void cerrarMulti() {
        try {
            socket_multicast.leaveGroup(grupo);
            socket_multicast.close();
        } catch (IOException e) {
            System.out.println("error al cerrar el socket multicast");
        }
    }

}
