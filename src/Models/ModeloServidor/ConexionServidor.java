package Models.ModeloServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Views.GUI;

public class ConexionServidor extends Thread {
  ServerSocket servidor;
  GUI gui;
  int contador = 0;
  HiloCliente cliente;

  Multicast multicast;

  public ConexionServidor(GUI gui, int port) {
    this.gui = gui;
    try {
      this.gui.mostrarMensaje("Conectando por el puerto" + port + "Espere por favor...\n");
      servidor = new ServerSocket(port);
      this.gui.mostrarMensaje("servidor iniciado" + servidor + "\n");
      multicast = gui.getMulti();
      start();

    } catch (Exception e) {
      System.out.println("error al crear serverSocket");
    }

  }

  public void run() {
    while (true) {
      try {
        gui.mostrarMensaje("esperando un cliente....\n");
        adicionarCliente(servidor.accept());
        gui.mostrarMensaje("Conexion exitosa. Cliente " + contador + " conectado");
      } catch (IOException e) {
        System.out.println("error al aceptar clientes");
      }
    }
  }

  public void adicionarCliente(Socket socket) {
    contador++;
    gui.mostrarMensaje("Cliente No" + contador + "conectado!");
    cliente = new HiloCliente(gui, socket, contador, multicast);
    try {
      cliente.obtetenerFlujos();
      cliente.start();
    } catch (IOException e) {
      e.getStackTrace();
    }
  }

}
