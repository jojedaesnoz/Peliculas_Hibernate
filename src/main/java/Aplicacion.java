import base.Pelicula;
import control.Controlador;
import datos.Modelo;
import ui.Vista;

public class Aplicacion {

    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);
    }
}
