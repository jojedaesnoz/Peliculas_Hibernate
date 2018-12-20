package util;

import java.io.File;

public class Constantes {

    // TABLA PELICULAS BBDD
    public static final String TABLA_PELICULAS = "peliculas";
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String SINOPSIS = "sinopsis";
    public static final String VALORACION = "valoracion";
    public static final String RECAUDACION = "recaudacion";
    public static final String IMAGEN = "imagen";

    // RECURSOS LOCALES
    private static final String RECURSOS = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    public static final String RUTA_PROPERTIES = RECURSOS + "config.properties";
    public static final String RUTA_IMAGENES = RECURSOS + "images";
    public static final String DEFAULT_IMAGE = RECURSOS + "default_image.jpg";
    public static final String SEARCH_ICON = RECURSOS + "search_icon.png";
    public static final String STAR_ICON = RECURSOS + "star_icon.png";
    public static final String STAR_OFF = RECURSOS + "star_off.png";

    // VISTA
    public static final int ANCHO_LABEL = 90;
    public static final int ALTO_LABEL = 30;
    public static final int ANCHO_IMAGEN = 240;
    public static final int ALTO_IMAGEN = 150;
    public static final int ANCHO_SINOPSIS = 22;
}
