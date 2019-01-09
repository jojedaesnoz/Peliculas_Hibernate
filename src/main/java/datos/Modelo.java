package datos;

import base.Pelicula;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static util.Constantes.DEFAULT_IMAGE;
import static util.Constantes.RUTA_IMAGENES;

public class Modelo {
    private Pelicula ultimaBorrada;
    private ArrayList<Pelicula> peliculas;

    {
        peliculas = new ArrayList<>();
    }

    public void conectar() {
        HibernateUtil.buildSessionFactory();
    }

    public void desconectar() {
        limpiarImagenesSobrantes();
        HibernateUtil.closeSessionFactory();
    }

    public boolean iniciarSesion(String usuario, String contrasena) {
        return true;
    }

    public String getDefaultImage() {
        return DEFAULT_IMAGE;
    }

    public void guardarPelicula(Pelicula pelicula) throws IOException {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        pelicula.setRutaImagen(copiarImagen(pelicula.getRutaImagen()));
        System.out.println(pelicula.getRutaImagen());
        sesion.save(pelicula);
        sesion.getTransaction().commit();
        sesion.close();

        cargarPeliculas();
    }

    private void modificarPelicula (Pelicula pelicula) throws IOException {
        guardarPelicula(pelicula);
        cargarPeliculas();
    }

//    public ArrayList<Pelicula> getPeliculas() {
//        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Pelicula");
//        return (ArrayList<Pelicula>) query.list();
//    }

    public ArrayList<Pelicula> getPeliculas() {
        if (peliculas.isEmpty()) {
            cargarPeliculas();
        }
        return peliculas;
    }

    private void cargarPeliculas() {
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Pelicula");
        peliculas = (ArrayList<Pelicula>) query.list();
    }

    public ArrayList<Pelicula> getPeliculas(String busqueda) {
        if (peliculas.isEmpty()) {
            cargarPeliculas();
        }

        ArrayList<Pelicula> resultado = new ArrayList<>();
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTitulo().contains(busqueda)) {
                resultado.add(pelicula);
            }
        }
        return resultado;
    }

//    public ArrayList<Pelicula> getPeliculas(String busqueda) {
//        Query query = HibernateUtil.getCurrentSession().
//                createQuery("FROM Pelicula" + " p WHERE p.titulo LIKE :busqueda");
//        query.setParameter("busqueda", "%" + busqueda + "%");
//
//        return  (ArrayList<Pelicula>) query.list();
//    }

    public void eliminarPelicula(Pelicula peliculaABorrar) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.delete(peliculaABorrar);
        sesion.getTransaction().commit();
        sesion.close();
        ultimaBorrada = peliculaABorrar;

        cargarPeliculas();
    }


    public Pelicula getUltimaBorrada() {
        return ultimaBorrada;
    }

    public void borrarTodo() {
        Session sesion = HibernateUtil.getCurrentSession();
        Transaction transaction = sesion.beginTransaction();
        sesion.createQuery("delete Pelicula").executeUpdate();
        transaction.commit();
        sesion.close();
    }

    private void limpiarImagenesSobrantes() {
        // Almacenar en una lista las imagenes que estan siendo usadas
        ArrayList<String> imagenesUsadas = new ArrayList<>();
        for (Pelicula pelicula: getPeliculas()) {
            imagenesUsadas.add(pelicula.getRutaImagen());
        }

        // Comparar las imagenes usadas con las que hay en disco y borrar discrepancias
        File[] imagenesDisco = new File(RUTA_IMAGENES).listFiles();
        if (imagenesDisco != null) {
            for (File imagenDisco : imagenesDisco) {
                if (!imagenesUsadas.contains(imagenDisco.getPath())) {
                    imagenDisco.delete();
                }
            }
        }
    }

    private String copiarImagen (String rutaImagen) throws IOException {
        // Copiar la imagen a un directorio de la aplicacion, manteniendo su extension
        String[] partes =  new File(rutaImagen).getName().split("[.]");
        String extension = partes.length == 2 ? "." + partes[1] : "";
        String rutaSalida = RUTA_IMAGENES + File.separator
                + UUID.randomUUID().toString() + extension;
        Util.copiarFichero(rutaImagen, rutaSalida);

        // Devuelve la ruta nueva para poder asignarla a la pelicula que se va a guardar
        return rutaSalida;
    }

}
