package ui.custom;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Botonera extends JPanel implements ActionListener {
    private final String[] actionCommands = {
            "Nuevo", "Guardar", "Modificar", "Cancelar",
            "Eliminar", "Deshacer", "Eliminar Todo"
    };
    private BotoneraListener botoneraListener;
    private ArrayList<JButton> botones;

    public Botonera(BotoneraListener botoneraListener) {
        super();
        this.botoneraListener = botoneraListener;
        botones = new ArrayList<>();
        colocarBotones();
    }

    public Botonera() {
        super();
        colocarBotones();
        botones = new ArrayList<>();
        colocarBotones();
    }

    private void colocarBotones() {
        for (String actionCommand : actionCommands) {
            JButton boton = new JButton(actionCommand);
            boton.addActionListener(this);
            add(boton);
            botones.add(boton);
        }
    }

    public List<JButton> getBotones() {
        return botones;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Nuevo":
                botoneraListener.clickEnNuevo(e);
                break;
            case "Guardar":
                botoneraListener.clickEnGuardar(e);
                break;
            case "Modificar":
                botoneraListener.clickEnModificar(e);
                break;
            case "Cancelar":
                botoneraListener.clickEnCancelar(e);
                break;
            case "Eliminar":
                botoneraListener.clickEnEliminar(e);
                break;
            case "Deshacer":
                botoneraListener.clickEnDeshacer(e);
                break;
            case "Eliminar Todo":
                botoneraListener.clickEnEliminarTodo(e);
                break;
        }
    }

    public interface BotoneraListener {
        void clickEnNuevo(ActionEvent evento);
        void clickEnGuardar(ActionEvent evento);
        void clickEnModificar(ActionEvent evento);
        void clickEnCancelar(ActionEvent evento);
        void clickEnEliminar(ActionEvent evento);
        void clickEnDeshacer(ActionEvent evento);
        void clickEnEliminarTodo(ActionEvent evento);
    }

}
