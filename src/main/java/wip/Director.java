package base;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name="directores")
public class Director extends Persona{

    @OneToMany(mappedBy="director", cascade=CascadeType.ALL)
    private ArrayList<Pelicula> peliculas;
}
