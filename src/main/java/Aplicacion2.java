import control.Controlador;
import control.Controlador2;
import datos.Modelo;
import ui.Vista2;
import ui.VistaPeliculas;

public class Aplicacion2 {

    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista2 vistaPeliculas = new Vista2();
        Controlador2 controlador = new Controlador2(modelo, vistaPeliculas);
    }
}
