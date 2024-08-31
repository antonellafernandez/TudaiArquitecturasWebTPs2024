package services;

import dao.DireccionDAO;
import entities.Direccion;
import factories.DAOFactory;

import java.util.List;

public class DireccionService {
    private DireccionDAO direccionDAO;

    public DireccionService(DAOFactory factory) {
        this.direccionDAO = factory.getDireccionDAO();
    }

    public void addDireccion(Direccion direccion) {
        direccionDAO.addDireccion(direccion);
    }

    public Direccion getDireccionById(int id) {
        return direccionDAO.getDireccionById(id);
    }

    public List<Direccion> getAllDirecciones() {
        return direccionDAO.getAllDirecciones();
    }
}
