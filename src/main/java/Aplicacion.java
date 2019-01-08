import control.Controlador;
import datos.Modelo;
import ui.VistaPeliculas;

public class Aplicacion {

    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        VistaPeliculas vistaPeliculas = new VistaPeliculas();
        Controlador controlador = new Controlador(modelo, vistaPeliculas);
    }
}
