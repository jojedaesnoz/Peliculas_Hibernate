package ui.custom;

import javax.swing.*;
import java.awt.*;

public class PanelEntrada <T extends JComponent>  extends JPanel{
    private JLabel label;
    private T entrada;
    private JLabel mensaje;
    
    public PanelEntrada(T entrada) {
        super();
        this.entrada = entrada;
        inicializar();
        colocarComponentes();
    }

    private void colocarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 0, 5);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        add(label, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(mensaje, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        // todo: gbc.weighty = 1?
        add(entrada, gbc);
    }

    private void inicializar() {
        label = new JLabel();
        mensaje = new JLabel();
    }
    
    public void setLabel(String label) {
        this.label.setText(label);
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje.setText(mensaje);
    }
    
    public T getEntrada() {
        return entrada;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void setEntrada(T entrada) {
        this.entrada = entrada;
    }

    public JLabel getMensaje() {
        return mensaje;
    }

    public void setMensaje(JLabel mensaje) {
        this.mensaje = mensaje;
    }
}
