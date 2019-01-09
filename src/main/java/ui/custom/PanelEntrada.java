package ui.custom;

import javax.swing.*;
import java.awt.*;

public class PanelEntrada <T extends JComponent>  extends JPanel{
    private JLabel descripcion;
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

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(descripcion, gbc);

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
        descripcion = new JLabel();
        mensaje = new JLabel();
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion.setText(descripcion);
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje.setText(mensaje);
    }
    
    public T getEntrada() {
        return entrada;
    }
}
