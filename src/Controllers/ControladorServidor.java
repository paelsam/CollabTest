package Controllers;

import Models.ModeloServidor.ConexionServidor;
import Views.GUI;

public class ControladorServidor {

    private GUI gui;
    private ConexionServidor servidor;

    public ControladorServidor() {
        this.servidor = new ConexionServidor(10000);
        this.gui = new GUI();
        this.gui.iniciarComponentes();
        this.servidor.start();
    }

    public void crearExamen() {
        
    }


}