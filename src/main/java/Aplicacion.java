import base.Pelicula;
import control.Controlador;
import datos.Modelo;
import ui.Vista;

import java.util.Random;

public class Aplicacion {

    public static void main(String[] args) {

        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("El Señor de los Anillos: El Retorno del Rey");
        pelicula.setSinopsis("Best movie eva foreva");
    }
}
