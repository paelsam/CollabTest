package views;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import controlador.conexion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Gui extends JFrame {
    JLabel lA, lB, lC, lD, lExamenes, lPregunta;
    JPanel pDerecha, pCenter, pNorte;
    JMenu menuExamenes;
    JTextArea descripcionP;
    JMenuItem examen1, examen2;
    JMenuBar barraMenu;
    JRadioButton rA, rB, rC, rD;
    ButtonGroup grupo;
    conexion con;

    public Gui() {
        setSize(500, 500);
        setTitle("cliente");
        iniciarComponentes();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        con = new conexion(this);

    }

    public void iniciarComponentes() {
        lA = new JLabel("pregunta A");
        lB = new JLabel("pregunta B");
        lC = new JLabel("preguntaC");
        lD = new JLabel("pregunta D");
        lPregunta = new JLabel();

        rA = new JRadioButton();
        rB = new JRadioButton();
        rC = new JRadioButton();
        rD = new JRadioButton();
        grupo = new ButtonGroup();
        grupo.add(rA);
        grupo.add(rB);
        grupo.add(rC);
        grupo.add(rD);

        pDerecha = new JPanel();
        pDerecha.setLayout(new GridLayout(4, 2, 5, 5));
        pCenter = new JPanel();
        pNorte = new JPanel();

        menuExamenes = new JMenu("examenes");
        barraMenu = new JMenuBar();
        examen1 = new JMenuItem("examen1");
        examen2 = new JMenuItem("examen2");
        menuExamenes.add(examen1);
        menuExamenes.add(examen2);
        barraMenu.add(menuExamenes);
        descripcionP = new JTextArea(20, 30);

        setJMenuBar(barraMenu);

        pDerecha.add(lA);
        pDerecha.add(rA);
        pDerecha.add(lB);
        pDerecha.add(rB);
        pDerecha.add(lC);
        pDerecha.add(rC);
        pDerecha.add(lD);
        pDerecha.add(rD);
        pCenter.add(lPregunta);
        pCenter.add(descripcionP);
        setLayout(new BorderLayout());
        add(pDerecha, BorderLayout.WEST);
        add(pCenter, BorderLayout.CENTER);

    }

    public void mostrarMensaje(String mensaje) {
        descripcionP.append(mensaje);
    }

    public static void main(String[] args) {
        Gui gui = new Gui();
    }
}
