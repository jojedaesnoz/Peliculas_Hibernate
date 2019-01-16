package ui;

import base.Pelicula;
import ui.custom.BotoneraCRUD;
import ui.custom.PanelContenido;
import ui.custom.PanelEntrada;
import ui.custom.PanelValoracion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Vista2 extends JFrame {

    private JMenuBar menuBar;
    private BotoneraCRUD botoneraCRUD;
    private PanelContenido panelPeliculas, panelDirectores;
    private JTabbedPane tabbedPane;
    public static void main(String[] args) {
        new Vista2();
    }

    public Vista2() {
        inicializar();

        add(botoneraCRUD, BorderLayout.SOUTH);
        add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addTab("Películas", panelPeliculas);
        tabbedPane.add("Directores", panelDirectores);
        add(menuBar, BorderLayout.NORTH);
    }

    public void mostrarVentana() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    private void inicializar() {
        botoneraCRUD = new BotoneraCRUD();
        tabbedPane = new JTabbedPane();
        inicializarPanelPeliculas();
        inicializarPanelDirectores();
        inicializarMenu();
    }

    private void inicializarMenu() {
        menuBar = new JMenuBar();
        JMenu archivo = new JMenu("Archivo");
        menuBar.add(archivo);
        JMenuItem nuevaPelicula = new JMenuItem("Nueva Película");
        nuevaPelicula.addActionListener(e -> {
            tabbedPane.add("Películas", panelPeliculas);
            pack();
        });
        JMenuItem nuevoDirector = new JMenuItem("Nuevo Director");
        archivo.add(nuevaPelicula);
        archivo.add(nuevoDirector);

    }

    private void inicializarPanelPeliculas() {
        // Inicializar
        PanelEntrada<JTextField> titulo = new PanelEntrada<>(new JTextField());
        PanelEntrada<JTextField> sinopsis = new PanelEntrada<>(new JTextField());
        PanelEntrada<PanelValoracion> valoracion = new PanelEntrada<>(new PanelValoracion(5));
        PanelEntrada<JTextField> recaudacion = new PanelEntrada<>(new JTextField());

        // Poner nombre a labels
        titulo.setLabel("TÍTULO");
        sinopsis.setLabel("SINOPSIS");
        valoracion.setLabel("VALORACIÓN");
        recaudacion.setLabel("RECAUDACIÓN");

        // Poner en una lista
        ArrayList<PanelEntrada> entradas = new ArrayList<>();
        entradas.add(titulo);
        entradas.add(sinopsis);
        entradas.add(valoracion);
        entradas.add(recaudacion);

        // Construir el PanelContenido
        panelPeliculas = new PanelContenido<>(entradas);
    }

    private void inicializarPanelDirectores() {
        // Inicializar
        PanelEntrada<JTextField> nombre = new PanelEntrada<>(new JTextField());
        PanelEntrada<JTextField> apellido = new PanelEntrada<>(new JTextField());
        PanelEntrada<JTextField> recaudacion = new PanelEntrada<>(new JTextField());

        // Poner nombre a labels
        nombre.setLabel("NOMBRE");
        apellido.setLabel("APELLIDO");
        recaudacion.setLabel("RECAUDACIÓN");

        // Poner en una lista
        ArrayList<PanelEntrada> entradas = new ArrayList<>();
        entradas.add(nombre);
        entradas.add(apellido);
        entradas.add(recaudacion);

        // Construir el PanelContenido
        panelDirectores = new PanelContenido<>(entradas);
    }

}
