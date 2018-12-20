package datos;

import base.Pelicula;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import util.Util;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static util.Constantes.*;

public class Modelo {
    private Pelicula ultimaBorrada;


    @Override
    protected void finalize() {
        desconectar();
    }

    public void conectar() {
        HibernateUtil.buildSessionFactory();
    }

    public void desconectar() {
        HibernateUtil.closeSessionFactory();
    }

    public boolean iniciarSesion(String usuario, String contrasena) {
        return true;
    }

    public String getDefaultImage() {
        return DEFAULT_IMAGE;
    }

    public void guardarPelicula(Pelicula pelicula) {
        System.out.println("ID Película a guardar " + pelicula.getId());
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.save(pelicula);
        sesion.getTransaction().commit();
        sesion.close();
    }

    private void modificarPelicula (Pelicula pelicula) {
        guardarPelicula(pelicula);
    }

    public ArrayList<Pelicula> getPeliculas() {
        Query query = HibernateUtil.getCurrentSession().createQuery("FROM Pelicula");
        return (ArrayList<Pelicula>) query.list();
    }

    public ArrayList<Pelicula> getPeliculas(String busqueda) {
        Query query = HibernateUtil.getCurrentSession().
                createQuery("FROM Pelicula" + " p WHERE p.titulo LIKE :busqueda");
        query.setParameter("busqueda", "%" + busqueda + "%");

        return  (ArrayList<Pelicula>) query.list();
    }

    public void eliminarPelicula(Pelicula peliculaABorrar) {
        Session sesion = HibernateUtil.getCurrentSession();
        sesion.beginTransaction();
        sesion.delete(peliculaABorrar);
        sesion.getTransaction().commit();
        sesion.close();
        ultimaBorrada = peliculaABorrar;
    }


    public Pelicula getUltimaBorrada() {
        return ultimaBorrada;
    }

    public void borrarTodo() {
        Session sesion = HibernateUtil.getCurrentSession();
        Transaction transaction = sesion.beginTransaction();

        String hqlDeleteAll = "delete Pelicula";
        int resul = sesion.createQuery("delete Pelicula").executeUpdate();
        transaction.commit();
        sesion.close();
    }

    private void limpiarImagenesSobrantes() {

    }

    private String copiarImagen (String rutaImagen) throws IOException {
        // Copiar la imagen a un directorio de la aplicacion, manteniendo su extension
        String[] partes =  new File(rutaImagen).getName().split("[.]");
        String extension = partes.length == 2 ? "." + partes[1] : "";
        String rutaSalida = RUTA_IMAGENES + File.separator
                + UUID.randomUUID().toString() + extension;
        Util.copiarFichero(rutaImagen, rutaSalida);
        return rutaSalida;
    }

}
