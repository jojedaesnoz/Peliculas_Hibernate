package control;

import base.Pelicula;
import datos.Modelo;
import ui.JLabelEstrella;
import ui.Vista;
import util.Util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

import static javax.swing.JOptionPane.OK_OPTION;
import static util.Constantes.ANCHO_SINOPSIS;

public class Controlador implements ActionListener, MouseListener, DocumentListener, WindowListener, KeyListener {
    private Modelo modelo;
    private Vista vista;
    private Pelicula peliculaSeleccionada;
    private String imagenSeleccionada;

    public enum Origen {
        NUEVO, MODIFICAR
    }
    private Origen origen;

    /*
    Puntos opcionales:
        Añadir una opción al usuario que permita recuperar el último elemento borrado
        Añadir una opción a la aplicación que permita eliminar todos los datos del programa
     */

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;

        modelo.conectar();
//            iniciarSesion();

        // Establecer el estado por defecto de la aplicacion
        modoEdicion(false);
        addListeners();
        colocarImagen(modelo.getDefaultImage());
        vista.btDeshacer.setEnabled(false);
        vista.btModificar.setEnabled(false);
        refrescarLista(modelo.getPeliculas());
    }

    /****************************
     *                           *
     *      LISTENERS            *
     *                           *
     ****************************/

    private void addListeners() {
        // BOTONES: ActionListener
        JButton[] botones = {vista.btNuevo, vista.btModificar, vista.btGuardar,
                vista.btCancelar, vista.btEliminar, vista.btDeshacer, vista.btEliminarTodo};
        for (JButton boton: botones) {
            boton.addActionListener(this);
        }

        // TEXTO EN BUSQUEDA: DocumentListener
        vista.tfBusqueda.getDocument().addDocumentListener(this);

        // TEXTO EN LAS CAJAS
        for (JTextComponent component: new JTextComponent[]{
                vista.taTitulo, vista.taSinopsis, vista.taRecaudacion
        }) {
            component.addKeyListener(this);
        }

        // CLICK EN LAS ESTRELLAS: MouseListener
        for (JLabelEstrella labelEstrella : vista.estrellas) {
            labelEstrella.addMouseListener(this);
        }

        // IMAGEN: MouseListener
        vista.lImagen.addMouseListener(this);

        // CLICK EN LISTA: MouseListener
        vista.listaPeliculas.addMouseListener(this);

        // CIERRE DE LA VENTANA: WindowListener
        vista.addWindowListener(this);
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
                cargarPelicula(vista.listaPeliculas.getSelectedValue());
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
                vista.btDeshacer.setEnabled(true);
                break;
            case "Deshacer":
                deshacerUltimoBorrado();
                vista.btDeshacer.setEnabled(false);
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
        if (e.getDocument().equals(vista.tfBusqueda.getDocument())) {
            refrescarLista(modelo.getPeliculas(vista.tfBusqueda.getText()));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // Cargar la lista que devuelve lo que haya en la caja de busqueda
        if (e.getDocument().equals(vista.tfBusqueda.getDocument())) {
            refrescarLista(modelo.getPeliculas(vista.tfBusqueda.getText()));
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        modelo.desconectar();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(vista.listaPeliculas) && !vista.modeloPeliculas.isEmpty()) {
            // Click en la lista
            cargarPelicula(vista.listaPeliculas.getSelectedValue());
            vista.btModificar.setEnabled(true);

        } else if (e.getComponent().equals(vista.lImagen)) {
            // Click en la imagen
            JFileChooser jfc = new JFileChooser();
            if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
                return;

            imagenSeleccionada = jfc.getSelectedFile().getAbsolutePath();
            colocarImagen(imagenSeleccionada);
        } else if (e.getComponent().getClass().equals(JLabelEstrella.class)) {
            // Click en las estrellas de valoracion
            JLabelEstrella labelEstrella = (JLabelEstrella)e.getComponent();
            int numero = labelEstrella.getNumero();
            int i = 0;
            for (i = 0; i <= numero;  i++) {
                vista.estrellas[i].setActiva(true);
            }
            for (i = i; i < vista.estrellas.length; i++) {
                vista.estrellas[i].setActiva(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //TODO: Arreglar el SUPR
        JTextArea componente = (JTextArea) e.getSource();
        String texto = componente.getText();
        if (texto.length() > 0 && texto.length() % ANCHO_SINOPSIS == 0) {
            if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                componente.append("\n");
            }
            vista.pack();
        }
    }

    /*
    Metodo para recoger los datos de las cajas de texto e imagen
    Comprueba que la pelicula tenga un titulo, porque lo hemos definido como necesario
    El resto de campos reciben valores por defecto si no estan completos
     */
    private Pelicula cogerDatosPelicula(){
        String titulo, sinopsis;
        int valoracion;
        float recaudacion;
        String imagen;

        // Recoger los datos, valores por defecto en caso de que los campos innecesarios no esten completos
        if (vista.taTitulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El título es necesario", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        titulo = vista.taTitulo.getText();
        sinopsis = vista.taSinopsis.getText();
        valoracion = vista.tfValoracion.getText().isEmpty() ?
                0 : Integer.parseInt(vista.tfValoracion.getText());
        recaudacion = vista.taRecaudacion.getText().isEmpty() ?
                0 : Float.parseFloat(vista.taRecaudacion.getText());

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
        modelo.guardarPelicula(pelicula);
        origen = null;
    }

    private void eliminarPelicula() {
        Pelicula peliculaSeleccionada = vista.listaPeliculas.getSelectedValue();
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
        modelo.guardarPelicula(borrada);
        cargarPelicula(borrada);
    }


    /****************************
     *                           *
     *   METODOS DE LA VISTA     *
     *                           *
     ****************************/

    private void modoEdicion(boolean modo){
        // Activar/desactivar botones
        vista.btGuardar.setEnabled(modo);
        vista.btCancelar.setEnabled(modo);

        // Activar/desactivar campos
        vista.taTitulo.setEnabled(modo);
        vista.taSinopsis.setEnabled(modo);
        vista.tfValoracion.setEnabled(modo);
        vista.taRecaudacion.setEnabled(modo);
    }

    private void refrescarLista(List<Pelicula> nuevaLista) {
        vista.modeloPeliculas.removeAllElements();
        for (Pelicula pelicula: nuevaLista) {
            vista.modeloPeliculas.addElement(pelicula);
        }
    }

    private void vaciarCajas() {
        JTextArea[] cajas = {vista.taTitulo,  vista.tfValoracion, vista.taRecaudacion};
        for (JTextArea caja : cajas) {
            caja.setText("");
        }
        vista.taSinopsis.setText("");
        colocarImagen(modelo.getDefaultImage());
        vista.taTitulo.requestFocus();
        vista.btModificar.setEnabled(false);
    }

    private void cargarPelicula(Pelicula pelicula) {
        peliculaSeleccionada = pelicula;

        vista.taTitulo.setText(pelicula.getTitulo());
        vista.tfValoracion.setText(String.valueOf(pelicula.getValoracion()));
        vista.taRecaudacion.setText(String.valueOf(pelicula.getRecaudacion()));
        vista.taSinopsis.setText(pelicula.getSinopsis());

        // Colocar la imagen de la pelicula si tiene y si no, la imagen por defecto
        colocarImagen(new File(pelicula.getRutaImagen()).exists() ?
                pelicula.getRutaImagen() : modelo.getDefaultImage());

    }

    private void colocarImagen(String imagen) {
        ImageIcon imageIcon = new ImageIcon(imagen);
        Image image = imageIcon.getImage().getScaledInstance(253, -1, Image.SCALE_DEFAULT);
        vista.lImagen.setIcon(new ImageIcon(image));
        vista.pack();
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
