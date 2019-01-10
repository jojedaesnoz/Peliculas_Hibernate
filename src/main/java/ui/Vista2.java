package ui;

import base.Pelicula;
import ui.custom.PanelContenido;
import ui.custom.PanelEntrada;
import ui.custom.PanelValoracion;

import javax.swing.*;
import java.util.ArrayList;

public class Vista2 extends JFrame {

    private PanelContenido peliculas, directores;

    public Vista2() {

    }

    private PanelContenido<Pelicula> panelPeliculas() {
        // Inicializar
        PanelEntrada<JTextField> titulo = new PanelEntrada<>(new JTextField());
        PanelEntrada<JTextField> sinopsis = new PanelEntrada<>(new JTextField());
        PanelEntrada<PanelValoracion> valoracion = new PanelEntrada<>(new PanelValoracion());
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
        PanelContenido<Pelicula> panelPeliculas = new PanelContenido<>();
        panelPeliculas.setEntradas(entradas);
        return panelPeliculas;
    }

    private PanelContenido<Pelicula> panelDirectores() {
        // Inicializar
        PanelEntrada<JTextField> nombre = new PanelEntrada<>(new JTextField());
        PanelEntrada<JTextField> sinopsis = new PanelEntrada<>(new JTextField());
        PanelEntrada<PanelValoracion> valoracion = new PanelEntrada<>(new PanelValoracion());
        PanelEntrada<JTextField> recaudacion = new PanelEntrada<>(new JTextField());

        // Poner nombre a labels
        nombre.setLabel("TÍTULO");
        sinopsis.setLabel("SINOPSIS");
        valoracion.setLabel("VALORACIÓN");
        recaudacion.setLabel("RECAUDACIÓN");

        // Poner en una lista
        ArrayList<PanelEntrada> entradas = new ArrayList<>();
        entradas.add(nombre);
        entradas.add(sinopsis);
        entradas.add(valoracion);
        entradas.add(recaudacion);

        // Construir el PanelContenido
        PanelContenido<Pelicula> panelPeliculas = new PanelContenido<>();
        panelPeliculas.setEntradas(entradas);
        return panelPeliculas;
    }


}
