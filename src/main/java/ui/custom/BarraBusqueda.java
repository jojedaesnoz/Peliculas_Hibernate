package ui.custom;

import javax.swing.*;
import java.awt.*;

import static util.Constantes.SEARCH_ICON;

public class BarraBusqueda extends JPanel {
    private GridBagConstraints gbc;
    private JTextField tfBusqueda;
    private JLabel lbBusqueda;

    public BarraBusqueda() {
        super();
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        inicializar();

        add(lbBusqueda, gbc);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        add(tfBusqueda, gbc);
    }

    private void inicializar() {
        lbBusqueda = new JLabel();
        ImageIcon imageIcon = new ImageIcon(SEARCH_ICON);
        Image image = imageIcon.getImage().getScaledInstance(25, -1, Image.SCALE_DEFAULT);
        lbBusqueda.setIcon(new ImageIcon(image));

        tfBusqueda = new JTextField();
    }

    public JTextField getTfBusqueda() {
        return tfBusqueda;
    }
}
