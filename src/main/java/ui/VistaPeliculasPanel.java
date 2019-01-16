package ui;

import base.Pelicula;
import ui.custom.*;

import javax.swing.*;
import java.awt.*;

import static util.Constantes.*;

public class VistaPeliculasPanel extends JPanel {

    // LISTA
    public DefaultListModel<Pelicula> modeloPeliculas;
    public JList<Pelicula> listaPeliculas;

    // BOTONERA
    private BotoneraCRUD botoneraCRUD;

    // LABELS CAJAS E IMAGEN
    private PanelEntrada titulo, sinopsis, valoracion, recaudacion;
    public PanelValoracion panelValoracion;
    public BarraBusqueda barraBusqueda;
    public JLabel lImagen;

    public VistaPeliculasPanel() {
        super();
        setLayout(new BorderLayout());
        inicializarComponentes();
        colocarComponentes();
        darTamsBordesColores();
        prepararVentana();
    }

    private void inicializarComponentes() {

        // Entradas
        titulo = new PanelEntrada<>(new JTextField());
        sinopsis = new PanelEntrada<>(new JTextField());
        valoracion = new PanelEntrada<>(new PanelValoracion(5));
        recaudacion = new PanelEntrada<>(new JTextField());
        titulo.setLabel("TÍTULO");
        sinopsis.setLabel("SINOPSIS");
        valoracion.setLabel("VALORACIÓN");
        recaudacion.setLabel("RECAUDACIÓN");

        // Imagen
        lImagen = new JLabel();

        // BotoneraCRUD
        botoneraCRUD = new BotoneraCRUD();

        // Panel Valoracion
        panelValoracion = new PanelValoracion(5);

        // Barra Busqueda
        barraBusqueda = new BarraBusqueda();
    }

    private void darTamsBordesColores() {
        // Aspecto General
        setBackground(Color.black);

        // Entradas
        int anchoCaja = ANCHO_IMAGEN - ANCHO_LABEL;
        int altoCaja = ALTO_IMAGEN - ALTO_LABEL;
        for (PanelEntrada panelEntrada : new PanelEntrada[]{titulo, sinopsis, valoracion, recaudacion}){
            panelEntrada.getLabel().setPreferredSize(new Dimension(ANCHO_LABEL, ALTO_LABEL));
            panelEntrada.getLabel().setForeground(Color.decode(COLOR_TEXTO));

            panelEntrada.getEntrada().setBorder(BorderFactory.createLineBorder(Color.decode(COLOR_BORDE), 2));
            panelEntrada.getEntrada().setMinimumSize(new Dimension(anchoCaja, altoCaja));
        }

        // Imagen
        lImagen.setMinimumSize(new Dimension(ANCHO_IMAGEN, ALTO_IMAGEN));
        lImagen.setMaximumSize(lImagen.getMinimumSize());

        // BotoneraCRUD
        for (JButton boton: botoneraCRUD.getBotones()) {
            boton.setBackground(Color.decode(COLOR_BOTON));
            boton.setForeground(Color.decode(COLOR_TEXTO));
            boton.setBorder(BorderFactory.createLineBorder(Color.decode(COLOR_BORDE), 1));
            boton.setPreferredSize(new Dimension(ANCHO_LABEL, ALTO_LABEL));
        }

        // Panel Valoracion
        panelValoracion.setBackground(Color.decode(COLOR_FONDO));
    }

    private void colocarComponentes(){

        /*
        Este panel se compone de
        IMAGEN              BARRA BUSQUEDA

        PANELES ENTRADA     LISTA

         */
//        JPanel contenedor = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 5, 10);
//
//        // IMAGEN
//        lImagen.setBorder(BorderFactory.createLineBorder(Color.decode(COLOR_BORDE), 2));
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        gbc.gridheight = 2;
//        int posicionPostImagen = gbc.gridheight + 1;
//        gbc.fill = GridBagConstraints.VERTICAL;
//        contenedor.add(lImagen, gbc);
//        gbc.gridwidth = 1;
//        gbc.gridheight = 1;
//        gbc.fill = GridBagConstraints.NONE;
//
//        // ETIQUETAS
//        gbc.gridx = 0;
//        gbc.gridy = posicionPostImagen;
//        contenedor.add(lTitulo, gbc);
//
//        gbc.gridy++;
//        gbc.anchor = GridBagConstraints.NORTH;
//        contenedor.add(lSinopsis, gbc);
//        gbc.anchor = GridBagConstraints.CENTER;
//
//        gbc.gridy++;
//        contenedor.add(lValoracion, gbc);
//
//        gbc.gridy++;
//        contenedor.add(lRecaudacion, gbc);
//
//        // ENTRADAS
//        gbc.weighty = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        gbc.gridx = 1;
//        gbc.gridy = posicionPostImagen;
//        contenedor.add(taTitulo, gbc);
//
//        gbc.gridy++;
//        contenedor.add(taSinopsis, gbc);
//
//        gbc.gridy++;
//        gbc.fill = GridBagConstraints.NONE;
//        contenedor.add(panelValoracion, gbc);
//        gbc.fill = GridBagConstraints.BOTH;
//
//        gbc.gridy++;
//        contenedor.add(taRecaudacion, gbc);
//        gbc.weighty = 0;
//        gbc.weightx = 0;
//        gbc.fill = GridBagConstraints.NONE;
//
//
//        // CAJA DE BUSQUEDA
//        JPanel panelBusqueda = new JPanel(new GridBagLayout());
//        GridBagConstraints gbcBusqueda = new GridBagConstraints();
//        JLabel lbBusqueda = new JLabel();
//        lbBusqueda.setIcon(new ImageIcon());
//
//        ImageIcon imageIcon = new ImageIcon(SEARCH_ICON);
//        Image image = imageIcon.getImage().getScaledInstance(25, -1, Image.SCALE_DEFAULT);
//        lbBusqueda.setIcon(new ImageIcon(image));
//        panelBusqueda.add(lbBusqueda, gbcBusqueda);
//        gbcBusqueda.weightx = 1;
//        gbcBusqueda.weighty = 1;
//        gbcBusqueda.fill = GridBagConstraints.BOTH;
//        gbcBusqueda.gridx = 1;
//        panelBusqueda.add(tfBusqueda, gbcBusqueda);
//
//
//        gbc.gridx = 2;
//        gbc.gridy = 0;
//        gbc.gridheight = 1;
//        gbc.gridwidth = 1;
//        gbc.weightx = 1;
//        gbc.fill = GridBagConstraints.BOTH;
//        contenedor.add(panelBusqueda, gbc);
//
//        // LISTA
//        JScrollPane panelPeliculas = crearScrollPeliculas();
//        gbc.weighty = 1;
//        gbc.gridx = 2;
//        gbc.gridy = 1;
//        gbc.gridheight = 9;
//        gbc.fill = GridBagConstraints.BOTH;
//        contenedor.add(panelPeliculas, gbc);
//
//        // Colocar el panel
//        add(contenedor);
//
//        contenedor.setBackground(Color.decode(COLOR_FONDO));
    }

    private void prepararVentana() {
        Dimension dimensiones = new Dimension(800, 500);
        setSize(dimensiones);
        setMinimumSize(dimensiones);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JScrollPane crearScrollPeliculas() {
        modeloPeliculas = new DefaultListModel<>();
        listaPeliculas = new JList<>(modeloPeliculas);
        return new JScrollPane(listaPeliculas);
    }
}
