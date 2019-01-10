import base.Pelicula;
import ui.custom.PanelContenido;
import ui.custom.PanelEntrada;
import ui.custom.PanelValoracion;

import javax.swing.*;
import java.util.ArrayList;

public class Pruebas extends JFrame{

    public Pruebas() {

        PanelEntrada<JTextField> titulo, sinopsis, recaudacion;
        PanelEntrada<PanelValoracion> valoracion;

        titulo = new PanelEntrada<>(new JTextField());
        sinopsis = new PanelEntrada<>(new JTextField());
        valoracion = new PanelEntrada<>(new PanelValoracion());
        recaudacion = new PanelEntrada<>(new JTextField());
        titulo.setLabel("TÍTULO");
        sinopsis.setLabel("SINOPSIS");
        valoracion.setLabel("VALORACIÓN");
        recaudacion.setLabel("RECAUDACIÓN");

        PanelContenido<Pelicula> panelPeliculas = new PanelContenido<>();
        ArrayList<PanelEntrada> entradas = new ArrayList<>();
        entradas.add(titulo);
        entradas.add(sinopsis);
        entradas.add(valoracion);
        entradas.add(recaudacion);
        panelPeliculas.setEntradas(entradas);
        add(panelPeliculas);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        new Pruebas();
    }
}
