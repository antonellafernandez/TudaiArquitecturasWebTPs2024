package dao;

import entities.Direccion;
import java.util.List;

public interface DireccionDAO {
    void addDireccion(Direccion direccion);
    Direccion getDireccionById(int id);
    List<Direccion> getAllDirecciones();
}
