package views;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import javax.swing.JMenuBar;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Models.Pregunta;
import controlador.Controlador;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Gui extends JFrame {
    JLabel lA, lB, lC, lD, lExamenes;
    JPanel pSur, pIzquierda, pDerecha, pContenedor, pContenedor2;

    JTextArea descripcionP;
    JScrollPane JScrollPane;

    JMenuBar barraMenu;
    JRadioButton rA, rB, rC, rD;
    JButton bObtener, bCargarE;
    ButtonGroup grupo;
    JList<String> lista;
    String[] elementos = { "     ", "     ", "    ", "    ", "     ", "     ", "     " };
    DefaultListModel<String> modeloLista;
    Controlador cont;

    public Gui(Controlador cont) {
        setSize(700, 600);
        setTitle("cliente");
        iniciarComponentes();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.cont = cont;

    }

    public void iniciarComponentes() {

        lA = new JLabel("pregunta A");
        lB = new JLabel("pregunta B");
        lC = new JLabel("preguntaC");
        lD = new JLabel("pregunta D");

        rA = new JRadioButton();
        rB = new JRadioButton();
        rC = new JRadioButton();
        rD = new JRadioButton();
        grupo = new ButtonGroup();
        grupo.add(rA);
        grupo.add(rB);
        grupo.add(rC);
        grupo.add(rD);

        bObtener = new JButton("obtener");
        bCargarE = new JButton("Iniciar");

        pDerecha = new JPanel();
        pSur = new JPanel();
        pSur.setLayout(new GridLayout(4, 2, 5, 5));
        pIzquierda = new JPanel();

        pContenedor = new JPanel(new FlowLayout());
        pContenedor.add(pIzquierda);
        pContenedor.add(pDerecha);

        pContenedor2 = new JPanel();
        pContenedor2.add(pSur);
        descripcionP = new JTextArea(20, 30);

        JScrollPane scrollPane = new JScrollPane(descripcionP);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        setJMenuBar(barraMenu);

        pSur.add(lA);
        pSur.add(rA);
        pSur.add(lB);
        pSur.add(rB);
        pSur.add(lC);
        pSur.add(rC);
        pSur.add(lD);
        pSur.add(rD);
        pDerecha.add(scrollPane);
        setLayout(new BorderLayout());
        add(pContenedor2, BorderLayout.SOUTH);
        add(pContenedor, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();

        for (String elemento : elementos) {
            modeloLista.addElement(elemento);
        }

        lista = new JList<>(modeloLista);

        JScrollPane bar = new JScrollPane(lista);
        bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pIzquierda.add(bar);
        pIzquierda.add(bObtener);
        pIzquierda.add(bCargarE);

        manejoEventos eventos = new manejoEventos();
        bObtener.addActionListener(eventos);
        bCargarE.addActionListener(eventos);

    }

    public class manejoEventos implements ActionListener {
        manejoEventos() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bObtener) {
                // mostrarMensaje(lista.getSelectedValue());
                if (!lista.getSelectedValue().equals(null)) {
                    cont.sacarPreguntaActual(lista.getSelectedValue());
                    mostrarPregunta();
                }

            }
            if (e.getSource() == bCargarE) {
                // mostrarMensaje(cont.getExamen().getNombre());
                introducirPreguntas(cont.getExamen().getPreguntas());
            }
        }

    }

    public void mostrarMensaje(String mensaje) {
        descripcionP.setText(mensaje + "\n");
    }

    public void mostrarPregunta() {

        mostrarMensaje(cont.getActual().getEnunciado() + "\n" + cont.getActual().getDescripcion() +
                "\nRESPUESTAS:" + "\nA: " + cont.getActual().getListadoOpciones().get(0) + "\nB: "
                + cont.getActual().getListadoOpciones().get(1)
                + "\nC: " + cont.getActual().getListadoOpciones().get(2) + "\nD: "
                + cont.getActual().getListadoOpciones().get(3));
    }

    public void introducirPreguntas(ArrayList<Pregunta> preguntas) {
        for (int i = 0; i < preguntas.size(); i++) {
            modeloLista.set(i, String.valueOf(i));

        }
        lista.setModel(modeloLista);

    }

}
