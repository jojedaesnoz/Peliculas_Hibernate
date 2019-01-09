package base;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="peliculas")
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="titulo")
    private String titulo;

    @Column(name="sinopsis")
    private String sinopsis;

    @Column(name="valoracion")
    private int valoracion;

    @Column(name="recaudacion")
    private float recaudacion;

    @Column(name="imagen")
    private String rutaImagen;

//    @ManyToOne
//    @JoinColumn(name="id_director")
//    private Director director;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(float recaudacion) {
        this.recaudacion = recaudacion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
