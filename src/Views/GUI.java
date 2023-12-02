package Views;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controllers.ControladorServidor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame
{

    // private static Color Negro = new Color(10, 13, 34);
    // private static Color Azul = new Color(41, 107, 170);
    // private static Color Verde = new Color(10, 13, 34);
    // private static Color Blanco = new Color(10, 13, 34);
    // private static Color Amarillo = new Color(10, 13, 34);
    // private static Color Rojo = new Color(10, 13, 34);



    JTabbedPane tabsContainer;
    JPanel pCrearExamen, pExamenes, pInformesExamenes;
    

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
        setSize(800, 300);
    }

    public void iniciarComponentes() {
        tabsContainer = new JTabbedPane();
        pCrearExamen();
        pExamenes();
        pInformesExamenes();

        
        tabsContainer.addTab("Exámenes", pExamenes);
        tabsContainer.addTab("Crear Examen", pCrearExamen);
        tabsContainer.addTab("Informes", pInformesExamenes);
        add(tabsContainer);
        
        setVisible(true);
        pack();
    }
    
    public void pCrearExamen() {
        pCrearExamen= new JPanel(new BorderLayout(10, 10));

        // Sub panel para añadir componentes
        JPanel spFormularioCrearExamen = new JPanel(new GridLayout(4, 3, 10, 10));


        lNombreExamen = new JLabel("Nombre del examen:");
        tfNombreExamen = new JTextField(20);

        lPreguntasExamen = new JLabel("Preguntas (txt):");
        bCargarPreguntas = new JButton("Cargar");
        lRutaPreguntas = new JLabel("No has seleccionado un archivo"); // Se añadirá la ruta

        fcSeleccionarPreguntas = new JFileChooser();
        fcSeleccionarPreguntas.setCurrentDirectory(new File("src\\assets\\preguntas"));
        // Filtrador de archivos, solo acepta txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt", "text");
        fcSeleccionarPreguntas.setFileFilter(filter);

        lTiempoExamen = new JLabel("Tiempo del examen:");

        sTiempoMinutos = new JSpinner(new SpinnerNumberModel(1,1,60,1));
        sTiempoSegundos = new JSpinner(new SpinnerNumberModel(0,0,60,1));
        bCrearExamen = new JButton("Crear Examen");

        spFormularioCrearExamen.add(lNombreExamen); spFormularioCrearExamen.add(tfNombreExamen); spFormularioCrearExamen.add(new JPanel(new BorderLayout()));
        spFormularioCrearExamen.add(lPreguntasExamen); spFormularioCrearExamen.add(bCargarPreguntas); 
        spFormularioCrearExamen.add(lRutaPreguntas); spFormularioCrearExamen.add(lTiempoExamen);
        spFormularioCrearExamen.add(sTiempoMinutos); spFormularioCrearExamen.add(sTiempoSegundos); 
        spFormularioCrearExamen.add(new JPanel(new BorderLayout()));spFormularioCrearExamen.add(bCrearExamen); spFormularioCrearExamen.add(new JPanel(new BorderLayout()));

        pCrearExamen.add(spFormularioCrearExamen, BorderLayout.CENTER);

        // Eventos 
        EventListener eventListener = new EventListener();
        bCargarPreguntas.addActionListener(eventListener);
        bCrearExamen.addActionListener(eventListener);
    }

    public void pExamenes() {
        pExamenes = new JPanel(new BorderLayout());
    }

    public void pInformesExamenes() {
        pInformesExamenes = new JPanel(new BorderLayout());
    }


    class EventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == bCargarPreguntas ) {
                switch(fcSeleccionarPreguntas.showSaveDialog(null)) {
                    case JFileChooser.APPROVE_OPTION:
                        if ( fcSeleccionarPreguntas.getSelectedFile().exists() )
                            lRutaPreguntas.setText(fcSeleccionarPreguntas.getSelectedFile().getName());
                        break;
                    default:
                        break;
                };
            }
            if ( e.getSource() == bCrearExamen ) {
                if ( !getTfNombreExamen().isEmpty() && !getRutaPreguntas().isEmpty() )
                    ControladorServidor.crearExamen();
                else
                    mostrarMensaje("Llena todos los campos correctamente!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void mostrarMensaje(String mensaje, int status) {
        JOptionPane.showMessageDialog(null, mensaje, "Crear examen", status);
    }

    // Getters pCrearExamen 
    public String getTfNombreExamen() {
        return this.tfNombreExamen.getText();
    }

    public String getRutaPreguntas() {
        return this.fcSeleccionarPreguntas.getSelectedFile().getPath();
    }

    public int getTiempoMinutos() {
        return Integer.parseInt(this.sTiempoMinutos.getValue().toString());
    }

    public int getTiempoSegundos() {
        return Integer.parseInt(this.sTiempoMinutos.getValue().toString());
    }


    // public static void main(String[] args) {
    //     GUI miGui = new GUI();
    //     miGui.iniciarComponentes();
    // }
}
