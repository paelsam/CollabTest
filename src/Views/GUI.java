package Views;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileSystemView;

import java.awt.BorderLayout;
// import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame
{

    // private static Color Negro = new Color(10, 13, 34);
    // private static Color Azul = new Color(41, 107, 170);
    // private static Color Verde = new Color(10, 13, 34);
    // private static Color Blanco = new Color(10, 13, 34);
    // private static Color Amarillo = new Color(10, 13, 34);
    // private static Color Rojo = new Color(10, 13, 34);



    JTabbedPane tabsContainer;
    JPanel pCrearExamen, pExamenes, pInformesExamen;
    

    // Elementos de pCrearExamen
    JLabel lNombreExamen; JTextField tfNombreExamen;    
    JLabel lPreguntasExamen; JButton bCargarPreguntas; 
    JLabel lRutaPreguntas; JFileChooser fcSeleccionarPreguntas;
    JLabel lTiempoExamen; JSpinner sTiempoMinutos; JSpinner sTiempoSegundos;
    JButton bCrearExamen;
    

    public GUI() {
        setTitle("CollabTest");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
    }

    public void iniciarComponentes() {
        tabsContainer = new JTabbedPane();
        iniciarPCrearExamen();

        
        tabsContainer.addTab("Crear Examen", pCrearExamen);
        add(tabsContainer);
        
        setVisible(true);
        pack();
    }
    
    public void iniciarPCrearExamen() {
        pCrearExamen= new JPanel(new BorderLayout());

        // Sub panel para añadir componentes
        JPanel spFormularioCrearExamen = new JPanel(new GridLayout(4, 3, 10, 10));


        lNombreExamen = new JLabel("Nombre del examen:");
        tfNombreExamen = new JTextField(20);

        lPreguntasExamen = new JLabel("Preguntas (txt):");
        bCargarPreguntas = new JButton("Cargar");
        lRutaPreguntas = new JLabel("No has seleccionado un archivo"); // Se añadirá la ruta
        fcSeleccionarPreguntas = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        lTiempoExamen = new JLabel("Tiempo del examen:");
        sTiempoMinutos = new JSpinner(new SpinnerNumberModel(1,1,60,1));
        sTiempoSegundos = new JSpinner(new SpinnerNumberModel(1,1,60,1));
        bCrearExamen = new JButton("Crear Examen");

        spFormularioCrearExamen.add(lNombreExamen); spFormularioCrearExamen.add(tfNombreExamen); spFormularioCrearExamen.add(new JPanel(new BorderLayout()));
        spFormularioCrearExamen.add(lPreguntasExamen); spFormularioCrearExamen.add(bCargarPreguntas); 
        spFormularioCrearExamen.add(lRutaPreguntas); spFormularioCrearExamen.add(lTiempoExamen);
        spFormularioCrearExamen.add(sTiempoMinutos); spFormularioCrearExamen.add(sTiempoSegundos); 
        spFormularioCrearExamen.add(new JPanel(new BorderLayout()));spFormularioCrearExamen.add(bCrearExamen); spFormularioCrearExamen.add(new JPanel(new BorderLayout()));

        pCrearExamen.add(spFormularioCrearExamen, BorderLayout.CENTER);

        EventListener eventListener = new EventListener();
        bCargarPreguntas.addActionListener(eventListener);
    }


    class EventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == bCargarPreguntas ) {
                fcSeleccionarPreguntas.showSaveDialog(null);
                if ( !fcSeleccionarPreguntas.getSelectedFile().getPath().isEmpty() )
                    lRutaPreguntas.setText(fcSeleccionarPreguntas.getSelectedFile().getName());
            }
        }
    }

    public static void main(String[] args) {
        GUI miGui = new GUI();
        miGui.iniciarComponentes();
    }




}
