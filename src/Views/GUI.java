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
import Models.ModeloServidor.ConexionServidor;
import Models.ModeloServidor.Multicast;

public class GUI extends JFrame {
    JPanel pestaña1, pestaña2, pestaña3, pCargar, pContenedor1, pContenedor2;
    JTabbedPane pestañas;
    public JTextField tRuta, tNombre, tTiempo;
    JLabel lNombre, lTiempo, lRuta, lInfo;
    JButton bCrear, bHExamen;
    JTextArea areaInfo, areaInfo2;
    private Multicast multi;

    public GUI() {

        multi = new Multicast();
        setTitle("QUIZLET");
        setSize(800, 600);
        iniciarComponentes();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Multicast getMulti() {
        return multi;
    }

    public void iniciarComponentes() {
        pestañas = new JTabbedPane();
        iniciarPestaña1();
        iniciarPestaña2();
        /*
         * pestaña1 = new JPanel(new BorderLayout());
         * pestaña2 = new JPanel();
         * pestaña3 = new JPanel();
         * pCargar = new JPanel(new GridLayout(3, 2));
         * pContenedor1 = new JPanel();
         * pContenedor1.setLayout(new BoxLayout(pContenedor1, BoxLayout.PAGE_AXIS));
         * 
         * tRuta = new JTextField(20);
         * tRuta.setText("path...");
         * tNombre = new JTextField(20);
         * tTiempo = new JTextField(20);
         * 
         * lRuta = new JLabel("nombre archivo");
         * lNombre = new JLabel("nombre Examen:");
         * lTiempo = new JLabel("tiempo");
         * lInfo = new JLabel("informacion");
         * 
         * bCrear = new JButton("Crear");
         * 
         * areaInfo = new JTextArea(5, 20);
         * 
         * pCargar.add(lRuta);
         * pCargar.add(tRuta);
         * pCargar.add(lNombre);
         * pCargar.add(tNombre);
         * pCargar.add(lTiempo);
         * pCargar.add(tTiempo);
         * 
         * pContenedor1.add(pCargar);
         * pContenedor1.add(bCrear);
         * pContenedor1.add(new JScrollPane(areaInfo));
         * pestaña1.add(pContenedor1, BorderLayout.CENTER);
         * 
         * pestañas.addTab("Cargar Examen", pestaña1);
         * pestañas.addTab("Hacer Examen", pestaña2);
         */
        // pestañas.addTab("visuallizar Informes", pestaña3);

        add(pestañas);

        gestionEventos gestion = new gestionEventos();
        bCrear.addActionListener(gestion);
        bHExamen.addActionListener(gestion);

    }

    public void iniciarPestaña1() {
        pestaña1 = new JPanel(new BorderLayout());
        pCargar = new JPanel(new GridLayout(3, 2));
        pContenedor1 = new JPanel();
        pContenedor1.setLayout(new BoxLayout(pContenedor1, BoxLayout.PAGE_AXIS));
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

        pContenedor1.add(pCargar);
        pContenedor1.add(bCrear);
        pContenedor1.add(new JScrollPane(areaInfo));
        pestaña1.add(pContenedor1, BorderLayout.CENTER);
        pestañas.addTab("Cargar Examen", pestaña1);
    }

    public void iniciarPestaña2() {
        pestaña2 = new JPanel();
        pContenedor2 = new JPanel();
        areaInfo2 = new JTextArea(10, 10);
        pContenedor2.add(areaInfo2);
        pestaña2.add(pContenedor2, BorderLayout.CENTER);
        pestañas.addTab("Hacer Examen", pestaña2);
        bHExamen = new JButton("hacer Ex");
        pContenedor2.add(bHExamen);

    }

    public JLabel getlInfo() {
        return lInfo;
    }

    public void setlInfo(String info) {
        lInfo.setText(info);
    }

    public void mostrarMensaje(String mensaje) {
        areaInfo2.append(mensaje);
    }

    public class gestionEventos implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCrear) {
                Controlador.crearExamen();
                int ultimoIndice = Controlador.Examenes.size() - 1;
                System.out.println(Controlador.Examenes.get(0));
                areaInfo.setText("examen creado con exito\n" + Controlador.Examenes.get(ultimoIndice).mostrarDatos());

            }
            if (e.getSource() == bHExamen) {
                // multi.enviarMensajeMulticast("\nhola desde boton");
                multi.enviarExamenMulticast(Controlador.Examenes.get(0));

            }
        }

    }

}
