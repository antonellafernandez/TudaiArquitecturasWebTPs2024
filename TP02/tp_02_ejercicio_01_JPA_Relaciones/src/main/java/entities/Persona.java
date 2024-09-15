package entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Persona {
    @Id
    private int id;

    @Column(nullable=false)
    private String nombre;

    @Column(name="anios")
    private int edad;

    // Muchas personas pueden vivir en un mismo domicilio pero
    // no muchos domicilios asignados a la misma persona
    @ManyToOne
    private Direccion domicilio;

    @ManyToMany(fetch=FetchType.LAZY, mappedBy="jugadores")
    private List<Turno> turnos;

    public Persona() {
        super();
    }

    public Persona(int id, String nombre, int edad, Direccion domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.domicilio = domicilio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Direccion getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Direccion domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", domicilio=" + domicilio +
                '}';
    }
}
