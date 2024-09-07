package com.example.ejemplodaoypatrones;

import com.example.ejemplodaoypatrones.dao.DireccionDAO;
import com.example.ejemplodaoypatrones.dao.PersonaDAO;
import com.example.ejemplodaoypatrones.dto.PersonaDTO;
import com.example.ejemplodaoypatrones.entities.Direccion;
import com.example.ejemplodaoypatrones.entities.Persona;
import com.example.ejemplodaoypatrones.factory.AbstractFactory;
import com.example.ejemplodaoypatrones.utils.HelperMySQL;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        HelperMySQL dbMySQL = new HelperMySQL();
        dbMySQL.dropTables();
        dbMySQL.createTables();
        dbMySQL.populateDB();
        dbMySQL.closeConnection();

        AbstractFactory chosenFactory = AbstractFactory.getDAOFactory(1);
        System.out.println();
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        System.out.println();
        DireccionDAO direccion = chosenFactory.getDireccionDAO();
        PersonaDAO persona = chosenFactory.getPersonaDAO();


        System.out.println("Busco una Persona por id: ");
        Persona personaById = persona.find(2);
        System.out.println(personaById);
        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");
        System.out.println("Lista de direcciones: ");
//        List<Direccion> listadoDirecciones = direccion.selectList();
//        System.out.println(listadoDirecciones);
        List<Direccion> listadoDirecciones = direccion.selectList();
        for (Direccion dir : listadoDirecciones) {
            System.out.println(dir);
        }

        System.out.println("////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////");

//        Persona p = new Persona(6,"Sergio",50,2);
//        persona.insertPersona(p);

        PersonaDTO personaDTO = persona.findPersonaDTO(2);
        System.out.println(personaDTO);

    }
}
