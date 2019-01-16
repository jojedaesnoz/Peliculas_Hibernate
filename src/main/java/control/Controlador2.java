package control;

import base.Pelicula;
import datos.Modelo;
import ui.Vista2;
import ui.custom.JLabelEstrella;
import util.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static javax.swing.JOptionPane.OK_OPTION;
import static util.Constantes.ANCHO_SINOPSIS;

public class Controlador2 implements ActionListener, MouseListener, DocumentListener, WindowListener, KeyListener {
    private Modelo modelo;
    private Vista2 vistaPeliculas;
    private Pelicula peliculaSeleccionada;
    private String imagenSeleccionada;
    private int valoracion;

    public enum Origen {
        NUEVO, MODIFICAR
    }
    private Origen origen;

    /*
    Posibilidad:
        En lugar de guardar imagenSeleccionada, guardar peliculaPantallla
        asignar imagenSeleccionada a peliculaPantalla
        guardar valoracion en peliculaPantalla
        que coger pelicula modifique peliculaPantalla
     */

    public Controlador2(Modelo modelo, Vista2 vistaPeliculas) {
        this.modelo = modelo;
        this.vistaPeliculas = vistaPeliculas;

        modelo.conectar();
//            iniciarSesion();

        // Establecer el estado por defecto de la aplicacion
        ponerListeners();
        colocarImagen(modelo.getDefaultImage());
        modoEdicion(false);
        vistaPeliculas.btDeshacer.setEnabled(false);
        vistaPeliculas.btModificar.setEnabled(false);
        refrescarLista(modelo.getPeliculas());
    }

    /****************************
     *                           *
     *      LISTENERS            *
     *                           *
     ****************************/

    private void ponerListeners() {
        // BOTONES: ActionListener
        JButton[] botones = {vistaPeliculas.btNuevo, vistaPeliculas.btModificar, vistaPeliculas.btGuardar,
                vistaPeliculas.btCancelar, vistaPeliculas.btEliminar, vistaPeliculas.btDeshacer, vistaPeliculas.btEliminarTodo};
        for (JButton boton: botones) {
            boton.addActionListener(this);
        }

        // TEXTO EN BUSQUEDA: DocumentListener
        vistaPeliculas.tfBusqueda.getDocument().addDocumentListener(this);

        // TEXTO EN LAS CAJAS
        for (JTextComponent component: new JTextComponent[]{
                vistaPeliculas.taTitulo, vistaPeliculas.taSinopsis, vistaPeliculas.taRecaudacion
        }) {
            component.addKeyListener(this);
        }

        // CLICK EN LAS ESTRELLAS: MouseListener
        for (JLabelEstrella labelEstrella : vistaPeliculas.estrellas) {
            labelEstrella.addMouseListener(this);
        }

        // IMAGEN: MouseListener
        vistaPeliculas.lImagen.addMouseListener(this);

        // CLICK EN LISTA: MouseListener
        vistaPeliculas.listaPeliculas.addMouseListener(this);

        // CIERRE DE LA VENTANA: WindowListener
        vistaPeliculas.addWindowListener(this);
    }

    private void configurarModo(String actionCommand) {
        switch (actionCommand) {
            case "Nuevo":
            case "Modificar":
                modoEdicion(true);
                break;
            case "Guardar":
            case "Cancelar":
            case "Eliminar":
            case "Eliminar Todo":
                modoEdicion(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Configura el modo edicion segun que boton ha sido pulsado
        configurarModo(e.getActionCommand());

        // Realiza la accion correspondiente a cada boton
        switch (e.getActionCommand()) {
            case "Nuevo":
                origen = Origen.NUEVO;
                vaciarCajas();
                break;
            case "Modificar":
                origen = Origen.MODIFICAR;
                cargarPelicula(vistaPeliculas.listaPeliculas.getSelectedValue());
                break;
            case "Guardar":
                // Recoger la pelicula y si no hay errores, guardarla
                Pelicula pelicula;
                if (null != (pelicula = cogerDatosPelicula())) {
                    guardarPelicula(pelicula);
                }
                break;
            case "Cancelar":
                vaciarCajas();
                break;
            case "Eliminar":
                eliminarPelicula();
                vistaPeliculas.btDeshacer.setEnabled(true);
                break;
            case "Deshacer":
                deshacerUltimoBorrado();
                vistaPeliculas.btDeshacer.setEnabled(false);
                break;
            case "Eliminar Todo":
                eliminarTodo();
                break;
            default:
                break;
        }

        // Haga lo que haga, refresca la lista
        refrescarLista(modelo.getPeliculas());
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // Cargar la lista que devuelve lo que haya en la caja de busqueda
        if (e.getDocument().equals(vistaPeliculas.tfBusqueda.getDocument())) {
            refrescarLista(modelo.getPeliculas(vistaPeliculas.tfBusqueda.getText()));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // Cargar la lista que devuelve lo que haya en la caja de busqueda
        if (e.getDocument().equals(vistaPeliculas.tfBusqueda.getDocument())) {
            refrescarLista(modelo.getPeliculas(vistaPeliculas.tfBusqueda.getText()));
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        modelo.desconectar();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(vistaPeliculas.listaPeliculas) && !vistaPeliculas.modeloPeliculas.isEmpty()) {
            // Click en la lista
            cargarPelicula(vistaPeliculas.listaPeliculas.getSelectedValue());
            vistaPeliculas.btModificar.setEnabled(true);

        } else if (e.getComponent().equals(vistaPeliculas.lImagen)) {
            // Click en la imagen
            JFileChooser jfc = new JFileChooser();
            if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
                return;

            imagenSeleccionada = jfc.getSelectedFile().getAbsolutePath();
            colocarImagen(imagenSeleccionada);
        } else if (e.getComponent().getClass().equals(JLabelEstrella.class)) {
            // Click en las estrellas de valoracion
            JLabelEstrella labelEstrella = (JLabelEstrella)e.getComponent();
            valoracion = labelEstrella.getNumero();
            mostrarValoracion(valoracion);
        }
    }

    private void mostrarValoracion(int numero) {
        int i = 0;
        for (i = 0; i < numero; i++) {
            vistaPeliculas.estrellas[i].setActiva(true);
        }
        for (i = i; i < vistaPeliculas.estrellas.length; i++) {
            vistaPeliculas.estrellas[i].setActiva(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        JTextArea componente = (JTextArea) e.getSource();
        String texto = componente.getText();
        if (texto.length() > 0 && texto.length() % ANCHO_SINOPSIS == 0) {
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE
                    && e.getKeyChar() != KeyEvent.VK_DELETE
                    && e.getKeyChar() != KeyEvent.VK_ENTER) {
                // Si no esta borrando ni introduciendo un salto de linea,
                // salta de linea al llegar al final de la caja
                componente.append("\n");
            }
            vistaPeliculas.pack();
        }
    }

    /*
    Metodo para recoger los datos de las cajas de texto e imagen
    Comprueba que la pelicula tenga un titulo, porque lo hemos definido como necesario
    El resto de campos reciben valores por defecto si no estan completos
     */
    private Pelicula cogerDatosPelicula(){
        String titulo, sinopsis;
        float recaudacion;
        String imagen;

        // Recoger los datos, valores por defecto en caso de que los campos innecesarios no esten completos
        if (vistaPeliculas.taTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaPeliculas, "El título es necesario", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        titulo = vistaPeliculas.taTitulo.getText();
        sinopsis = vistaPeliculas.taSinopsis.getText();
        recaudacion = vistaPeliculas.taRecaudacion.getText().isEmpty() ?
                0 : Float.parseFloat(vistaPeliculas.taRecaudacion.getText());

        imagen = imagenSeleccionada != null? imagenSeleccionada : modelo.getDefaultImage();

        // Utilizar el objeto seleccionado o crear uno nuevo segun el origen
        Pelicula pelicula = null;
        if (origen.equals(Origen.MODIFICAR)) {
            pelicula = peliculaSeleccionada;
        } else {
            pelicula = new Pelicula();
        }

        // Construir la pelicula y devolverla
        pelicula.setTitulo(titulo);
        pelicula.setSinopsis(sinopsis);
        pelicula.setValoracion(valoracion);
        pelicula.setRecaudacion(recaudacion);
        pelicula.setRutaImagen(imagen);

        return pelicula;
    }


    /****************************
     *                           *
     *   METODOS DEL MODELO      *
     *                           *
     ****************************/

    private void iniciarSesion () {
        boolean autenticado = false;
        Login login = new Login();
        int intentos = 0;

        do {
            login.tfUsuario.requestFocus();
            login.mostrarDialogo();

            String usuario = login.getUsuario();
            String contrasena = login.getContrasena();

            autenticado = modelo.iniciarSesion(usuario, contrasena);

            if (!autenticado) {
                if (intentos > 2) {
                    Util.mensajeError("Número de intentos excedido.");
                }

                login.mostrarMensaje("Error en el usuario y/o contraseña.");
                intentos++;
            }

        } while (!autenticado);
    }

    private void guardarPelicula(Pelicula pelicula) {
        try {
            modelo.guardarPelicula(pelicula);
            origen = null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void eliminarPelicula() {
        Pelicula peliculaSeleccionada = vistaPeliculas.listaPeliculas.getSelectedValue();
        if (peliculaSeleccionada != null) {
            modelo.eliminarPelicula(peliculaSeleccionada);
        }
    }

    private void eliminarTodo() {
        String warning = "¿Está seguro de que desea borrarlo todo?";
        if(JOptionPane.showConfirmDialog(null, warning) == OK_OPTION){
            modelo.borrarTodo();
            if (origen != Origen.NUEVO) {
                vaciarCajas();
            }
        }
    }

    private void deshacerUltimoBorrado() {
        Pelicula borrada = modelo.getUltimaBorrada();
        borrada.setId(0);
        try {
            modelo.guardarPelicula(borrada);
            cargarPelicula(borrada);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /****************************
     *                           *
     *   METODOS DE LA VISTA     *
     *                           *
     ****************************/

    private void modoEdicion(boolean modo){
        // Activar/desactivar botones
        vistaPeliculas.btGuardar.setEnabled(modo);
        vistaPeliculas.btCancelar.setEnabled(modo);

        // Activar/desactivar campos
        vistaPeliculas.taTitulo.setEnabled(modo);
        vistaPeliculas.taSinopsis.setEnabled(modo);
        vistaPeliculas.tfValoracion.setEnabled(modo);
        vistaPeliculas.taRecaudacion.setEnabled(modo);

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

    private void refrescarLista(List<Pelicula> nuevaLista) {
        vistaPeliculas.modeloPeliculas.removeAllElements();
        for (Pelicula pelicula: nuevaLista) {
            vistaPeliculas.modeloPeliculas.addElement(pelicula);
        }
    }

    private void vaciarCajas() {
        JTextArea[] cajas = {vistaPeliculas.taTitulo,  vistaPeliculas.tfValoracion, vistaPeliculas.taRecaudacion};
        for (JTextArea caja : cajas) {
            caja.setText("");
        }
        mostrarValoracion(vistaPeliculas.estrellas.length);
        vistaPeliculas.taSinopsis.setText("");
        colocarImagen(modelo.getDefaultImage());
        vistaPeliculas.taTitulo.requestFocus();
        vistaPeliculas.btModificar.setEnabled(false);
    }

    private void cargarPelicula(Pelicula pelicula) {
        peliculaSeleccionada = pelicula;

        vistaPeliculas.taTitulo.setText(pelicula.getTitulo());
        mostrarValoracion(pelicula.getValoracion());
        vistaPeliculas.taRecaudacion.setText(String.valueOf(pelicula.getRecaudacion()));
        vistaPeliculas.taSinopsis.setText(pelicula.getSinopsis());

        // Colocar la imagen de la pelicula si tiene y si no, la imagen por defecto
        colocarImagen(new File(pelicula.getRutaImagen()).exists() ?
                pelicula.getRutaImagen() : modelo.getDefaultImage());

    }

    private void colocarImagen(String imagen) {
        ImageIcon imageIcon = new ImageIcon(imagen);
        Image image = imageIcon.getImage().getScaledInstance(253, -1, Image.SCALE_DEFAULT);
        vistaPeliculas.lImagen.setIcon(new ImageIcon(image));
        vistaPeliculas.pack();
    }


    /****************************
     *                           *
     *   LISTENERS VACIOS        *
     *                           *
     ****************************/

    @Override
    public void changedUpdate(DocumentEvent e) {
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

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
