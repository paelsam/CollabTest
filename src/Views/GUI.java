package Views;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controllers.ControladorServidor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
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

    // Elemenetos de pExamenes
    JComboBox<String> cbExamenes;
    TextArea tAreaVisualizarExamen;
    JButton bIniciarExamen; JButton bVisualizarExamen;
    CircularLabel lEstudiantesConectados[] = new CircularLabel[3];
    

    public GUI() {
        setTitle("CollabTest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 200);
        setResizable(false);
    }

    public void iniciarComponentes() {
        tabsContainer = new JTabbedPane();
        pCrearExamen();
        pExamenes();
        pInformesExamenes();

        
        tabsContainer.addTab("Crear Examen", pCrearExamen);
        tabsContainer.addTab("Exámenes", pExamenes);
        tabsContainer.addTab("Informes", pInformesExamenes);
        add(tabsContainer);

        // Eventos 
        EventListener eventListener = new EventListener();
        bCargarPreguntas.addActionListener(eventListener);
        bCrearExamen.addActionListener(eventListener);
        bVisualizarExamen.addActionListener(eventListener);
        
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
    }

    public void pExamenes() {
        pExamenes = new JPanel(new BorderLayout());

        JPanel pNorte = new JPanel(new BorderLayout());
        JPanel pEste = new JPanel(new BorderLayout());
        JPanel pOeste = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel lTitulo = new JLabel("Iniciar examen");
        lTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lTitulo.setVerticalAlignment(SwingConstants.CENTER);

        JLabel lElegirExamen = new JLabel("Elige un examen:");
        lElegirExamen.setFont(new Font("Arial", Font.BOLD, 14));
        lElegirExamen.setHorizontalAlignment(SwingConstants.CENTER);
        lElegirExamen.setVerticalAlignment(SwingConstants.CENTER);


        JLabel lVisualizarExamen = new JLabel("Visualizar examen:");
        lVisualizarExamen.setFont(new Font("Arial", Font.BOLD, 14));
        lVisualizarExamen.setHorizontalAlignment(SwingConstants.CENTER);
        lVisualizarExamen.setVerticalAlignment(SwingConstants.CENTER);

        bIniciarExamen = new JButton("Iniciar");
        bIniciarExamen.setPreferredSize(new Dimension(80, 30));
        bVisualizarExamen = new JButton("Ver");
        bVisualizarExamen.setPreferredSize(new Dimension(80, 30));
        

        JLabel lTextEstudiantesConectados = new JLabel("Estudiantes en línea:");
        lTextEstudiantesConectados.setFont(new Font("Arial", Font.BOLD, 14));
        lTextEstudiantesConectados.setHorizontalAlignment(SwingConstants.CENTER);
        lTextEstudiantesConectados.setVerticalAlignment(SwingConstants.CENTER);

        cbExamenes = new JComboBox<>();
        cbExamenes.setPreferredSize(new Dimension(150, 30));

        // Creando labels para estudiantes conectados
        for (int i = 0; i < lEstudiantesConectados.length; i++) {
            lEstudiantesConectados[i] = crearLEstudiante();
        }

        tAreaVisualizarExamen = new TextArea(15, 15);

        pNorte.setPreferredSize(new Dimension(300, 50));
        pNorte.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pOeste.setPreferredSize(new Dimension(200, this.getHeight()));
        pOeste.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        pEste.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pNorte.add(lTitulo, BorderLayout.CENTER);
        
        pOeste.add(lElegirExamen);
        pOeste.add(cbExamenes);
        pOeste.add(bIniciarExamen);
        pOeste.add(bVisualizarExamen);
        pOeste.add(lTextEstudiantesConectados);
        for ( CircularLabel lEstudiante : lEstudiantesConectados )
            pOeste.add(lEstudiante);
            
        pEste.add(lVisualizarExamen, BorderLayout.NORTH);
        pEste.add(tAreaVisualizarExamen, BorderLayout.CENTER);
        
        pExamenes.add(pNorte, BorderLayout.NORTH);
        pExamenes.add(pOeste, BorderLayout.WEST);
        pExamenes.add(pEste, BorderLayout.CENTER);
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
                if ( !getTfNombreExamen().isEmpty() && !getRutaPreguntas().isEmpty() ) {
                    ControladorServidor.crearExamen();
                    addItems(ControladorServidor.getNombreExamenes());
                }
                else
                    mostrarMensaje("Llena todos los campos correctamente!", JOptionPane.ERROR_MESSAGE);
            }
            if ( e.getSource() == bVisualizarExamen ) {
                String nombreExamen = (String) cbExamenes.getSelectedItem();
                if ( !nombreExamen.isEmpty()  )
                    setTAreaVisualizarExamen(ControladorServidor.getExamenByName(nombreExamen).toString());
                    
            }
        }
    }

    public void mostrarMensaje(String mensaje, int status) {
        JOptionPane.showMessageDialog(null, mensaje, "Crear examen", status);
    }

    // Métodos de pExámenes
    public void addItems(String[] nombresExamenes) {
        for (String nombreExamen : nombresExamenes ) {
            cbExamenes.addItem(nombreExamen);
        }
    }

    public void setTAreaVisualizarExamen(String text) {
        tAreaVisualizarExamen.setText(text);
    }

    public CircularLabel crearLEstudiante() {
        CircularLabel lEstudiante = new CircularLabel();
        lEstudiante.setText(" ");
        lEstudiante.setPreferredSize(new Dimension(40, 40));
        lEstudiante.setBackground(Color.GRAY);
        return lEstudiante;
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
