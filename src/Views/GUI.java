package Views;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controllers.ControladorServidor;
import Models.ModeloServidor.HiloEstudiante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class GUI extends JFrame {

    // private static Color Negro = new Color(10, 13, 34);
    // private static Color Azul = new Color(41, 107, 170);
    // private static Color Verde = new Color(10, 13, 34);
    // private static Color Blanco = new Color(10, 13, 34);
    // private static Color Amarillo = new Color(10, 13, 34);
    // private static Color Rojo = new Color(10, 13, 34);

    JTabbedPane tabsContainer;
    JPanel pCrearExamen, pExamenes, pInformesExamenes, pInformesIzquierda, pInformesDerecha;

    // Elementos de pCrearExamen
    JLabel lNombreExamen;
    JTextField tfNombreExamen;
    JLabel lPreguntasExamen;
    JButton bCargarPreguntas;
    JLabel lRutaPreguntas;
    JFileChooser fcSeleccionarPreguntas, fcSeleccionarInformes;
    JLabel lTiempoExamen;
    JSpinner sTiempoMinutos;
    JSpinner sTiempoSegundos;
    JButton bCrearExamen, bCargarInforme, bGuardarHistorial;
    JComboBox<String> comboSeleccionarExamen;

    // Elemenetos de pExamenes
    JComboBox<String> cbExamenes;
    TextArea tAreaVisualizarExamen;
    JTextArea tAreaVisualizarInforme;
    JButton bIniciarExamen;
    JButton bVisualizarExamen;
    CircularLabel lEstudiantesConectados[] = new CircularLabel[3];
    JLabel lTiempoRestanteText, lTiempoRestante;


    public GUI() {

        setTitle("CollabTest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 300);
        iniciarComponentes();
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

        // pCrearExamen
        bCargarPreguntas.addActionListener(eventListener);
        bCrearExamen.addActionListener(eventListener);

        // pExamenes
        bVisualizarExamen.addActionListener(eventListener);
        bIniciarExamen.addActionListener(eventListener);

        // pInformes
        comboSeleccionarExamen.addActionListener(eventListener);

        setVisible(true);
        pack();
    }

    public void pCrearExamen() {
        pCrearExamen = new JPanel(new BorderLayout(10, 10));

        // Sub panel para añadir componentes
        JPanel spFormularioCrearExamen = new JPanel(new GridLayout(4, 3, 10, 10));

        lNombreExamen = new JLabel("Nombre del examen:");
        tfNombreExamen = new JTextField(20);

        lPreguntasExamen = new JLabel("Preguntas (txt):");
        bCargarPreguntas = new JButton("Cargar");
        lRutaPreguntas = new JLabel("No has seleccionado un archivo"); // Se añadirá la ruta

        fcSeleccionarPreguntas = new JFileChooser();
        fcSeleccionarPreguntas.setCurrentDirectory(new File("src\\assets"));
        // Filtrador de archivos, solo acepta txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt", "text");
        fcSeleccionarPreguntas.setFileFilter(filter);

        lTiempoExamen = new JLabel("Tiempo del examen:");

        sTiempoMinutos = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
        sTiempoSegundos = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        bCrearExamen = new JButton("Crear Examen");

        spFormularioCrearExamen.add(lNombreExamen);
        spFormularioCrearExamen.add(tfNombreExamen);
        spFormularioCrearExamen.add(new JPanel(new BorderLayout()));
        spFormularioCrearExamen.add(lPreguntasExamen);
        spFormularioCrearExamen.add(bCargarPreguntas);
        spFormularioCrearExamen.add(lRutaPreguntas);
        spFormularioCrearExamen.add(lTiempoExamen);
        spFormularioCrearExamen.add(sTiempoMinutos);
        spFormularioCrearExamen.add(sTiempoSegundos);
        spFormularioCrearExamen.add(new JPanel(new BorderLayout()));
        spFormularioCrearExamen.add(bCrearExamen);
        spFormularioCrearExamen.add(new JPanel(new BorderLayout()));

        pCrearExamen.add(spFormularioCrearExamen, BorderLayout.CENTER);
    }

    public void pExamenes() {
        pExamenes = new JPanel(new BorderLayout());

        JPanel pNorte = new JPanel(new BorderLayout());
        JPanel pEste = new JPanel(new BorderLayout());
        JPanel pOeste = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));

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

        lTiempoRestanteText = new JLabel("Tiempo restante:");
        lTiempoRestante = new JLabel("00:00");

        lTiempoRestanteText.setFont(new Font("Arial", Font.BOLD, 14));
        lTiempoRestante.setFont(new Font("Arial", Font.BOLD, 24));
        lTiempoRestante.setPreferredSize(new Dimension(180, 30));
        lTiempoRestante.setHorizontalAlignment(SwingConstants.CENTER);
        lTiempoRestante.setVerticalAlignment(SwingConstants.CENTER);

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
        for (CircularLabel lEstudiante : lEstudiantesConectados)
            pOeste.add(lEstudiante);
        pOeste.add(lTiempoRestanteText);
        pOeste.add(lTiempoRestante);

        pEste.add(lVisualizarExamen, BorderLayout.NORTH);
        pEste.add(tAreaVisualizarExamen, BorderLayout.CENTER);

        pExamenes.add(pNorte, BorderLayout.NORTH);
        pExamenes.add(pOeste, BorderLayout.WEST);
        pExamenes.add(pEste, BorderLayout.CENTER);
    }

    public void pInformesExamenes() {
        pInformesExamenes = new JPanel(new BorderLayout());

        pInformesIzquierda = new JPanel(new GridBagLayout());
        pInformesIzquierda.setPreferredSize(new Dimension(200, this.getHeight()));
        GridBagConstraints gbc = new GridBagConstraints();

        tAreaVisualizarInforme = new JTextArea(20, 30);
        JScrollPane scrollPane = new JScrollPane(tAreaVisualizarInforme);

        String[] opciones = ControladorServidor.getNombreHistorialExamenes();
        comboSeleccionarExamen = new JComboBox<>(opciones);
        comboSeleccionarExamen.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        pInformesIzquierda.add(comboSeleccionarExamen, gbc);

        pInformesExamenes.add(pInformesIzquierda, BorderLayout.WEST);
        pInformesExamenes.add(scrollPane, BorderLayout.CENTER);
    }

    class EventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bCargarPreguntas) {
                switch (fcSeleccionarPreguntas.showSaveDialog(null)) {
                    case JFileChooser.APPROVE_OPTION:
                        if (fcSeleccionarPreguntas.getSelectedFile().exists())
                            lRutaPreguntas.setText(fcSeleccionarPreguntas.getSelectedFile().getName());
                        break;
                    default:
                        break;
                }
            }
            if (e.getSource() == bCrearExamen) {
                if (!getTfNombreExamen().isEmpty() && !getRutaPreguntas().isEmpty()) {
                    ControladorServidor.crearExamen();
                    addItems(ControladorServidor.getNombreExamenes());
                    tfNombreExamen.setText("");
                    lRutaPreguntas.setText("");
                    sTiempoMinutos.setValue(1);
                    sTiempoSegundos.setValue(0);
                } else
                    mostrarMensaje("Llena todos los campos correctamente!", JOptionPane.ERROR_MESSAGE);
            }
            if (e.getSource() == bVisualizarExamen) {
                String nombreExamen = (String) cbExamenes.getSelectedItem();
                if (!nombreExamen.isEmpty())
                    setTAreaVisualizarExamen(ControladorServidor.getExamenByName(nombreExamen).toString());

            }
            if (e.getSource() == bIniciarExamen) {
                String nombreExamen = (String) cbExamenes.getSelectedItem();
                if (!nombreExamen.isEmpty())
                    ControladorServidor.iniciarExamen(nombreExamen);
                else 
                    mostrarMensaje("Escoge un examen disponible!", JOptionPane.ERROR_MESSAGE);
            }

            if ( !(comboSeleccionarExamen.getSelectedItem() == null) ) {
                String seleccion = comboSeleccionarExamen.getSelectedItem().toString();
                String stringExamen = ControladorServidor.getInformeExamenes().verHistorialPorExamen(seleccion);
                setTAreaVisualizarInforme(stringExamen);
            }

        }
    }

    public void setTiempoRestante(String tiempo) {
        lTiempoRestante.setText(tiempo);
    }

    public void mostrarMensaje(String mensaje, int status) {
        JOptionPane.showMessageDialog(null, mensaje, "Crear examen", status);
    }

    // Métodos de pExámenes
    public void addItems(String[] nombresExamenes) {
        for (String nombreExamen : nombresExamenes) {
            cbExamenes.addItem(nombreExamen);
        }
    }

    public void setTAreaVisualizarExamen(String text) {
        tAreaVisualizarExamen.setText(text);
    }

    public void setTAreaVisualizarInforme(String text) {
        tAreaVisualizarInforme.setText(text);
    }

    public CircularLabel crearLEstudiante() {
        CircularLabel lEstudiante = new CircularLabel();
        lEstudiante.setText(" ");
        lEstudiante.setPreferredSize(new Dimension(40, 40));
        lEstudiante.setBackground(Color.GRAY);
        return lEstudiante;
    }

    public JPanel getPExamenes() {
        return pExamenes;
    }

    public void cambiarColorCircularLabels(int numCircles, Color color) {
        lEstudiantesConectados[numCircles].setBackground(color);
        pExamenes.updateUI();
    }

    public void cambiarColorCircularLabels() {
        ArrayList<HiloEstudiante> estudiantes = ControladorServidor.getServidor().getEstudiantes();
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).socket.isClosed()) {
                cambiarColorCircularLabels(i, Color.GRAY);
            } else {
                cambiarColorCircularLabels(i, Color.GREEN);
            }
        }
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
}
