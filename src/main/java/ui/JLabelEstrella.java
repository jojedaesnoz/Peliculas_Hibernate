package ui;

import javax.swing.*;
import java.awt.*;

import static util.Constantes.STAR_ICON;
import static util.Constantes.STAR_OFF;

public class JLabelEstrella extends JLabel {
    private boolean activa;
    private static int autoincrement;
    private int numero;

    public JLabelEstrella() {
        activa = true;
        actualizar();
        autoincrement++;
        numero = autoincrement;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
        actualizar();
    }

    public boolean isActiva() {
        return activa;
    }

    private void actualizar() {
        String ruta = activa ? STAR_ICON : STAR_OFF;
        ImageIcon imageIcon = new ImageIcon(ruta);
        Image image = imageIcon.getImage().getScaledInstance(30, -1, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(image));
    }

    public int getNumero() {
        return numero;
    }
}
