package ui.custom;

import javax.swing.*;
import java.awt.*;

import static util.Constantes.STAR_ICON;
import static util.Constantes.STAR_OFF;

public class PanelValoracion extends JPanel {
    private static final int ANCHO_ESTRELLA = 30;
    private int valoracion;
    private JLabel[] estrellas;
    private ImageIcon imgOn, imgOff;

    public PanelValoracion() {
        super();
        inicializar();
    }

    private void inicializar() {
        // Array de imagenes
        estrellas = new JLabel[5];
        for (int i = 0; i < estrellas.length; i++) {
            estrellas[i] = new JLabel();
        }

        // Imagen de la estrella activada
        Image aux = new ImageIcon(STAR_ICON).getImage().getScaledInstance(ANCHO_ESTRELLA, -1, Image.SCALE_DEFAULT);
        imgOn = new ImageIcon(aux);

        // Imagen de la estrella desactivada
        aux = new ImageIcon(STAR_OFF).getImage().getScaledInstance(ANCHO_ESTRELLA, -1, Image.SCALE_DEFAULT);
        imgOff = new ImageIcon(aux);
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        // Almacenar el nuevo valor
        this.valoracion = valoracion;

        // Mostrarlo graficamente
        int i = 0;
        for (i = 0; i < valoracion - 1; i++) {
            estrellas[i].setIcon(imgOn);
        }
        for (i = i; i < estrellas.length; i++) {
            estrellas[i].setIcon(imgOff);
        }
    }
}
