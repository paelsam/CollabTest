package Models.ModeloServidor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import Models.Examen;

/**
 * Maneja el flujo de datos del servidos a uno o más clientes
 * @author paelsam
 */
public class Multicast 
{
    
    private MulticastSocket multicastSocket;
    private DatagramPacket datagramPacket;
    private InetAddress grupo;

    public Multicast()
    {
        try {
            System.out.println("Iniciando multitask....");
            this.multicastSocket = new MulticastSocket();
            this.grupo = InetAddress.getByName("224.0.0.2");
            this.datagramPacket = new DatagramPacket(new byte[0], 0, grupo, 9999);
        } catch (IOException error) {
            System.out.println("Error al crear el MulticastSocket: ");            
            System.out.println(error);
        }
    }

    /**
     * Enviar mensaje por medio del multitask
     * @param mensaje el mensaje a enviar
     * @return false si no se envió el mensaje; o true si se envió el mensaje
     */
    public boolean enviarMensaje(Examen examen) 
    {
        ByteArrayOutputStream byteArrayObject = new ByteArrayOutputStream(6400);
        try {
            ObjectOutputStream salida = new ObjectOutputStream(byteArrayObject);
            salida.writeObject(examen);

            // Enviando objeto
            datagramPacket.setData(byteArrayObject.toByteArray());
            datagramPacket.setLength(byteArrayObject.toByteArray().length);
            multicastSocket.send(datagramPacket);
            return true;
        } catch ( IOException error ) {
            System.out.println("Error al mandar el datagrama");
            System.out.println(error);
            return false;
        }
    }



}
