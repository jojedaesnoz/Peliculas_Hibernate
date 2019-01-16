package ui.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static util.Constantes.STAR_ICON;
import static util.Constantes.STAR_OFF;

public class PanelValoracion extends JPanel {
    private static final int ANCHO_ESTRELLA = 30;
    private int valoracion;
    private ArrayList<JLabel> estrellas;
    private ImageIcon imgOn, imgOff;
    private int numEstrellas;

    public PanelValoracion(int numEstrellas) {
        super();
        this.numEstrellas = numEstrellas;
        inicializar();
        setLayout(new GridLayout(1, numEstrellas, 10, 0));
        for (JLabel label : estrellas) {
            add(label);
        }
        setValoracion(numEstrellas);
    }

    private void inicializar() {
        // Array de imagenes
        estrellas = new ArrayList<>();
        EstrellaListener listener = new EstrellaListener();
        for (int i = 0; i < numEstrellas; i++) {
            estrellas.add(new JLabel());
            estrellas.get(i).addMouseListener(listener);
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
        for (i = 0; i < valoracion; i++) {
            estrellas.get(i).setIcon(imgOn);
        }
        for (i = i; i < estrellas.size(); i++) {
            estrellas.get(i).setIcon(imgOff);
        }
    }

    class EstrellaListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            setValoracion(estrellas.indexOf((JLabel) e.getSource()) + 1);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
