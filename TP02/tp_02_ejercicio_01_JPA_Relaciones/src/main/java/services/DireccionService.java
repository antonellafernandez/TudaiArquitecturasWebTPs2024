package services;

import dao.interfaces.DAO;
import entities.Direccion;
import factories.DAOFactory;

import java.util.List;

public class DireccionService {
    private DAO<Direccion> direccionDAO;

    public DireccionService(DAOFactory factory) {
        this.direccionDAO = factory.getDireccionDAO();
    }

    public void addDireccion(Direccion direccion) {
        direccionDAO.insert(direccion);
    }

    public Direccion getDireccionById(int id) {
        return direccionDAO.selectById(id);
    }

    public List<Direccion> getAllDirecciones() {
        return direccionDAO.selectAll();
    }
}
