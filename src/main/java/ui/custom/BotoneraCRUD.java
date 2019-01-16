package ui.custom;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotoneraCRUD extends JPanel implements ActionListener {
    private final String[] actionCommands = {
            "Nuevo", "Guardar", "Modificar", "Cancelar",
            "Eliminar", "Deshacer", "Eliminar Todo"
    };

    private JButton btNuevo, btGuardar, btModificar, btCancelar, btEliminar, btDeshacer, btEliminarTodo;
    private BotoneraListener botoneraListener;
    private ArrayList<JButton> botones;
    private boolean modoEdicion;

    {
        botones = new ArrayList<>();
        modoEdicion = false;
    }


    private void actualizarModoEdicion() {
        // Activar/desactivar botones
        btGuardar.setEnabled(modo);
        btCancelar.setEnabled(modo);

        // Poner y quitar listeners si los tiene
        if (modo) {
            for (JLabelEstrella estrella : vistaPeliculas.estrellas) {
                if (estrella.getMouseListeners().length == 0) {
                    estrella.addMouseListener(this);
                }
            }
            if (vistaPeliculas.lImagen.getMouseListeners().length == 0) {
                vistaPeliculas.lImagen.addMouseListener(this);
            }
        } else {
            // Borrar el listener, si no lo tiene, no hace nada
            for (JLabelEstrella estrella: vistaPeliculas.estrellas) {
                estrella.removeMouseListener(this);
            }
            vistaPeliculas.lImagen.removeMouseListener(this);
        }
    }

    public BotoneraCRUD (BotoneraListener botoneraListener) {
        super();
        this.botoneraListener = botoneraListener;
        colocarBotones();
    }

    public BotoneraCRUD() {
        super();
        colocarBotones();
    }

    private void colocarBotones() {
        JButton[] botonesArray = new JButton[] {
                btNuevo, btGuardar, btModificar, btCancelar,
                btEliminar, btDeshacer, btEliminarTodo
        };

        for (int i = 0; i < botonesArray.length; i++) {
            botonesArray[i] = new JButton(actionCommands[i]);
            botonesArray[i].addActionListener(this);
            botones.add(botonesArray[i]);
        }
    }

    public boolean getModoEdicion() {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion) {
        this.modoEdicion = modoEdicion;
        actualizarModoEdicion();
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
        default void clickEnNuevo(ActionEvent evento) {}
        default void clickEnGuardar(ActionEvent evento) {}
        default void clickEnModificar(ActionEvent evento) {}
        default void clickEnCancelar(ActionEvent evento) {}
        default void clickEnEliminar(ActionEvent evento) {}
        default void clickEnDeshacer(ActionEvent evento) {}
        default void clickEnEliminarTodo(ActionEvent evento) {}
    }

}
