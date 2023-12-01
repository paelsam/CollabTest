package Controllers;

import Models.ModeloServidor.ConexionServidor;
import Views.GUI;

class ControladorServidor {

    private GUI gui;
    private ConexionServidor servidor;

    public ControladorServidor() {
        this.servidor = new ConexionServidor(8080);
        this.gui = new GUI();
        this.gui.iniciarComponentes();
    }


}