package entities;

import javax.persistence.*;

@Entity
public class Socio {
    @Id
    private int id;

    @OneToOne(cascade=CascadeType.ALL)
    @MapsId
    private Persona persona;

    @Column
    private String tipo;

    public Socio() {
        super();
    }

    public Socio(Persona persona, String tipo) {
        super();
        this.id = persona.getId();
        this.persona = persona;
        this.tipo = tipo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "id=" + id +
                ", persona=" + persona +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
