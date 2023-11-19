package Views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controllers.Controlador;

public class GUI extends JFrame {
    JPanel pestaña1, pestaña2, pestaña3, pCargar, pContenedor;
    JTabbedPane pestañas;
    public JTextField tRuta, tNombre, tTiempo;
    JLabel lNombre, lTiempo, lRuta, lInfo;
    JButton bCrear;
    JTextArea areaInfo;

    public GUI() {

        setTitle("QUIZLET");
        setSize(600, 300);
        iniciarComponentes();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void iniciarComponentes() {
        pestañas = new JTabbedPane();
        pestaña1 = new JPanel(new BorderLayout());
        pestaña2 = new JPanel();
        pestaña3 = new JPanel();
        pCargar = new JPanel(new GridLayout(3, 2));
        pContenedor = new JPanel();
        pContenedor.setLayout(new BoxLayout(pContenedor, BoxLayout.PAGE_AXIS));

        tRuta = new JTextField(20);
        tRuta.setText("path...");
        tNombre = new JTextField(20);
        tTiempo = new JTextField(20);

        lRuta = new JLabel("nombre archivo");
        lNombre = new JLabel("nombre Examen:");
        lTiempo = new JLabel("tiempo");
        lInfo = new JLabel("informacion");

        bCrear = new JButton("Crear");

        areaInfo = new JTextArea(5, 20);

        pCargar.add(lRuta);
        pCargar.add(tRuta);
        pCargar.add(lNombre);
        pCargar.add(tNombre);
        pCargar.add(lTiempo);
        pCargar.add(tTiempo);

        pContenedor.add(pCargar);
        pContenedor.add(bCrear);
        pContenedor.add(new JScrollPane(areaInfo));
        pestaña1.add(pContenedor, BorderLayout.CENTER);

        pestañas.addTab("Cargar Examen", pestaña1);
        pestañas.addTab("Hacer Examen", pestaña2);
        pestañas.addTab("visuallizar Informes", pestaña3);

        add(pestañas);

        gestionEventos gestion = new gestionEventos();
        bCrear.addActionListener(gestion);

    }

    public JLabel getlInfo() {
        return lInfo;
    }

    public void setlInfo(String info) {
        lInfo.setText(info);
    }

    public class gestionEventos implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCrear) {
                Controlador.crearExamen();
                int ultimoIndice = Controlador.Examenes.size() - 1;
                areaInfo.setText("examen creado con exito\n" + Controlador.Examenes.get(ultimoIndice).mostrarDatos());
            }
        }

    }

}
