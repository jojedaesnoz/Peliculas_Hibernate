package ui;

import base.Pelicula;

import javax.swing.*;
import java.awt.*;

import static util.Constantes.*;

public class VistaPeliculasPanel extends JFrame {

    private final String COLOR_FONDO = "#893168";
    private final String COLOR_BOTON = "#4a1942";
    private final String COLOR_TEXTO = "#ffffff";
    private final String COLOR_BORDE = "#050404";

    // LISTA
    public DefaultListModel<Pelicula> modeloPeliculas;
    public JList<Pelicula> listaPeliculas;

    // BOTONES
    public JButton btNuevo, btGuardar, btModificar, btCancelar, btEliminar, btDeshacer, btEliminarTodo;

    // LABELS CAJAS E IMAGEN
    private JLabel lTitulo, lSinopsis, lValoracion, lRecaudacion;
    public JTextArea taTitulo, tfValoracion, taRecaudacion, taSinopsis;
    public JPanel panelValoracion;
    public JLabelEstrella[] estrellas;
    public JTextField tfBusqueda;
    public JLabel lImagen;

    public VistaPeliculasPanel() {
        inicializarComponentes();
        colocarComponentes();
        darTamsBordesColores();
        aspectoGeneral();
        crearPanelBotones();
        prepararVentana();
    }

    private void aspectoGeneral() {
        setBackground(Color.black);
    }

    private void inicializarComponentes() {
        // LABELS
        lTitulo = new JLabel("TÍTULO", SwingConstants.CENTER);
        lSinopsis = new JLabel("SINOPSIS", SwingConstants.CENTER);
        lValoracion = new JLabel("VALORACIÓN", SwingConstants.CENTER);
        lRecaudacion = new JLabel("RECAUDACIÓN", SwingConstants.CENTER);

        // CAJAS
        taTitulo = new JTextArea();
        taSinopsis = new JTextArea();
        tfValoracion = new JTextArea();
        taRecaudacion = new JTextArea();
        tfBusqueda = new JTextField();

        // IMAGEN
        lImagen = new JLabel();

        // BOTONES
        btNuevo = new JButton("Nuevo");
        btGuardar = new JButton("Guardar");
        btModificar = new JButton("Modificar");
        btCancelar = new JButton("Cancelar");
        btEliminar = new JButton("Eliminar");
        btDeshacer = new JButton("Deshacer");
        btEliminarTodo = new JButton("Eliminar Todo");

        panelValoracion = new JPanel(new GridLayout(1, 5, 10, 0));
        panelValoracion.setBackground(Color.decode(COLOR_FONDO));
    }

    private void darTamsBordesColores() {
        // LABELS
        for (JLabel label: new JLabel[]{lTitulo, lSinopsis, lValoracion, lRecaudacion}){
            label.setPreferredSize(new Dimension(ANCHO_LABEL, ALTO_LABEL));
            label.setForeground(Color.decode(COLOR_TEXTO));
        }

        // CAJAS
        int anchoCaja = ANCHO_IMAGEN - ANCHO_LABEL;
        int altoCaja = ALTO_IMAGEN - ALTO_LABEL;
        for (JComponent entrada: new JComponent[]{taTitulo, taSinopsis, tfValoracion, taRecaudacion, tfBusqueda}) {
            entrada.setBorder(BorderFactory.createLineBorder(Color.decode(COLOR_BORDE), 2));
            entrada.setMinimumSize(new Dimension(anchoCaja, altoCaja));
        }

        // IMAGEN
        lImagen.setMinimumSize(new Dimension(ANCHO_IMAGEN, ALTO_IMAGEN));
        lImagen.setMaximumSize(lImagen.getMinimumSize());

        // BOTONES
        for (JButton boton: new JButton[]{btNuevo, btGuardar, btModificar, btCancelar, btEliminar, btDeshacer, btEliminarTodo}) {
            boton.setBackground(Color.decode(COLOR_BOTON));
            boton.setForeground(Color.decode(COLOR_TEXTO));
            boton.setBorder(BorderFactory.createLineBorder(Color.decode(COLOR_BORDE), 1));
            boton.setPreferredSize(new Dimension(ANCHO_LABEL, ALTO_LABEL));
        }

        estrellas = new JLabelEstrella[5];
        for (int i = 0; i < estrellas.length; i++) {
            estrellas[i] = new JLabelEstrella();
            panelValoracion.add(estrellas[i]);
        }
    }

    private void colocarComponentes(){
        JPanel contenedor = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);

        // IMAGEN
        lImagen.setBorder(BorderFactory.createLineBorder(Color.decode(COLOR_BORDE), 2));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        int posicionPostImagen = gbc.gridheight + 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        contenedor.add(lImagen, gbc);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        // ETIQUETAS
        gbc.gridx = 0;
        gbc.gridy = posicionPostImagen;
        contenedor.add(lTitulo, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTH;
        contenedor.add(lSinopsis, gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy++;
        contenedor.add(lValoracion, gbc);

        gbc.gridy++;
        contenedor.add(lRecaudacion, gbc);

        // ENTRADAS
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = posicionPostImagen;
        contenedor.add(taTitulo, gbc);

        gbc.gridy++;
        contenedor.add(taSinopsis, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        contenedor.add(panelValoracion, gbc);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridy++;
        contenedor.add(taRecaudacion, gbc);
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;


        // CAJA DE BUSQUEDA
        JPanel panelBusqueda = new JPanel(new GridBagLayout());
        GridBagConstraints gbcBusqueda = new GridBagConstraints();
        JLabel lbBusqueda = new JLabel();
        lbBusqueda.setIcon(new ImageIcon());

        ImageIcon imageIcon = new ImageIcon(SEARCH_ICON);
        Image image = imageIcon.getImage().getScaledInstance(25, -1, Image.SCALE_DEFAULT);
        lbBusqueda.setIcon(new ImageIcon(image));
        panelBusqueda.add(lbBusqueda, gbcBusqueda);
        gbcBusqueda.weightx = 1;
        gbcBusqueda.weighty = 1;
        gbcBusqueda.fill = GridBagConstraints.BOTH;
        gbcBusqueda.gridx = 1;
        panelBusqueda.add(tfBusqueda, gbcBusqueda);


        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contenedor.add(panelBusqueda, gbc);

        // LISTA
        JScrollPane panelPeliculas = crearScrollPeliculas();
        gbc.weighty = 1;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 9;
        gbc.fill = GridBagConstraints.BOTH;
        contenedor.add(panelPeliculas, gbc);

        // Colocar el panel
        add(contenedor);

        contenedor.setBackground(Color.decode(COLOR_FONDO));
    }

    private void prepararVentana() {
        Dimension dimensiones = new Dimension(800, 500);
        setSize(dimensiones);
        setMinimumSize(dimensiones);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void crearPanelBotones() {

        JPanel botonera = new JPanel();
        JButton[] botones = {btNuevo, btGuardar, btModificar, btCancelar,
                btEliminar, btDeshacer, btEliminarTodo};

        botonera.setLayout(new GridLayout(1, botones.length));
        for (JButton boton: botones)
            botonera.add(boton);

        add(botonera, BorderLayout.SOUTH);
    }

    private JScrollPane crearScrollPeliculas() {
        modeloPeliculas = new DefaultListModel<>();
        listaPeliculas = new JList<>(modeloPeliculas);
        return new JScrollPane(listaPeliculas);
    }
}
