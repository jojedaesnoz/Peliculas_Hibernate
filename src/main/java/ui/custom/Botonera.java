package ui.custom;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Botonera extends JPanel {
    private final String[] actionCommands = {
            "Nuevo", "Guardar", "Modificar", "Cancelar",
            "Eliminar", "Deshacer", "Eliminar Todo"
    };

    public Botonera() {
        super();
    }

    public Botonera(List<String> actionCommands) {
        setActionCommands(actionCommands);
    }

    public List<String> getActionCommands() {
        return actionCommands;
    }

    public void setActionCommands(List<String> actionCommands) {
        this.actionCommands = actionCommands;
        colocarBotones();
    }

    private void colocarBotones() {
        for (Component component : getComponents()) {
            remove(component);
        }
        for (String actionCommand : actionCommands) {
            add(new JButton(actionCommand));
        }
    }
}
