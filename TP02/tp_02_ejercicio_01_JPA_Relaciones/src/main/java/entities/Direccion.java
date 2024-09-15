package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column
    private String ciudad;

    @Column
    private String calle;

    // Una direccion puede ir a muchas personas
    @OneToMany(mappedBy="domicilio", fetch=FetchType.LAZY)
    // LAZY por defecto, no se hace fetch hasta no pedir algo
    // de la lista a la base de datos, porque es muy costoso
    private List<Persona> habitantes;

    public Direccion() {
        super();
        this.habitantes = new ArrayList<Persona>();
    }

    public Direccion(String ciudad, String calle) {
        super();
        this.ciudad = ciudad;
        this.calle = calle;
    }

    public int getId() {
        return id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", ciudad='" + ciudad + '\'' +
                ", calle='" + calle + '\'' +
                '}';
    }
}
