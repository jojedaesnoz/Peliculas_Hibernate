package ui;

import javax.swing.*;

public class Vista extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel tabPelicula;

    {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Pelis 1", new VistaPeliculas());
        tabbedPane.addTab("Pelis 2", new VistaPeliculas());

        setVisible(true);
    }

    public static void main(String[] args) {
        new Vista();
    }


}
