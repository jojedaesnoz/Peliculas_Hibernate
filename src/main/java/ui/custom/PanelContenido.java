package ui.custom;

import ui.custom.BarraBusqueda;
import ui.custom.PanelEntrada;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import static java.awt.GridBagConstraints.*;

public class PanelContenido<T> extends JPanel {

    private JLabel labelImagen;
    private ArrayList<PanelEntrada> entradas;
    private BarraBusqueda barraBusqueda;
    private DefaultListModel<T> modeloLista;
    private JList<T> lista;
    private JScrollPane scrollLista;

    private int ANCHO_LABEL = 100;
    private final int ALTO_ENTRADA = 30;
    private final int ANCHO_LISTA = 120;

    public PanelContenido(ArrayList<PanelEntrada> entradas) {
        this.entradas = entradas;
        construirPanel();
    }

    public PanelContenido() {
        construirPanel();
    }

    private void construirPanel() {
        inicializar();
        colocarComponentes();
    }

    public void setEntradas(ArrayList<PanelEntrada> entradas) {
        this.entradas = entradas;
        colocarComponentes();
    }

    private void colocarComponentes() {
        // Borrar los componentes ya colocados
        for (Component componente : getComponents()) {
            remove(componente);
        }

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 10, 10);

        // Colocar imagen
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = BOTH;
        add(labelImagen, gbc);

        // Colocar barra de busqueda
        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = HORIZONTAL;
        add(barraBusqueda, gbc);

        // Colocar entradas
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = HORIZONTAL;
        for (PanelEntrada entrada : entradas) {
            gbc.gridy++;
//            entrada.getEntrada().setPreferredSize(new Dimension());
            entrada.getLabel().setPreferredSize(new Dimension(ANCHO_LABEL, ALTO_ENTRADA));
            add(entrada, gbc);
        }

        // Colocar lista
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = entradas.size();
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = BOTH;
        lista.setPreferredSize(new Dimension(ANCHO_LISTA, 0));
        add(lista, gbc);
    }

    public void colocarImagen(String imagen) {
        // TODO: Poner constantes
        ImageIcon imageIcon = new ImageIcon(imagen);
        Image image = imageIcon.getImage().getScaledInstance(253, -1, Image.SCALE_DEFAULT);
        labelImagen.setIcon(new ImageIcon(image));
    }

    private void inicializar() {
        labelImagen = new JLabel();
        if (entradas == null) {
            entradas = new ArrayList<>();
        }
        barraBusqueda = new BarraBusqueda();
        modeloLista = new DefaultListModel<>();
        lista = new JList<>(modeloLista);
        scrollLista = new JScrollPane(lista);
    }
}
