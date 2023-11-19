package Views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class GUI extends JFrame {
    JPanel pestaña1, pestaña2, pestaña3, pCargar;
    JTabbedPane pestañas;
    JTextField tRuta, tNombre, tTiempo;
    JLabel lNombre, lTiempo, lRuta;
    JButton bCrear;

    public GUI() {
        setTitle("QUIZLET");
        setSize(600, 550);
        iniciarComponentes();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void iniciarComponentes() {
        pestañas = new JTabbedPane();
        pestaña1 = new JPanel();
        pestaña2 = new JPanel();
        pestaña3 = new JPanel();
        pCargar = new JPanel(new GridLayout(3, 2));

        tRuta = new JTextField(20);
        tRuta.setText("path...");
        tNombre = new JTextField(20);
        tTiempo = new JTextField(20);

        lRuta = new JLabel("nombre archivo");
        lNombre = new JLabel("nombre:");
        lTiempo = new JLabel("tiempo");

        bCrear = new JButton("Crear");

        pCargar.add(lRuta);
        pCargar.add(tRuta);
        pCargar.add(lNombre);
        pCargar.add(tNombre);
        pCargar.add(lTiempo);
        pCargar.add(tTiempo);

        pestaña1.add(pCargar);
        pestaña1.add(bCrear);

        // pestaña1.add(new JLabel("Visualizar Examenes"));
        // pestaña2.add(new JLabel("Hacer Examen"));
        // pestaña3.add(new JLabel("visualizar Informes"));

        pestañas.addTab("Cargar Examen", pestaña1);
        pestañas.addTab("Hacer Examen", pestaña2);
        pestañas.addTab("visuallizar Informes", pestaña3);

        add(pestañas);

    }

    public static void main(String[] args) {
        GUI gui = new GUI();

    }

}
