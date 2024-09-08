package helpers;

import daos.*;
import daos.interfaces.DAO;
import entities.Cliente;
import entities.Factura;
import entities.FacturaProducto;
import entities.Producto;
import factories.DatabaseFactory;

import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader {
    // Método para cargar todos los datos de los CSVs a la base de datos
    public static void cargarDatos(CSVreader reader) throws SQLException {
        // Obtener las listas de entidades desde los archivos CSV
        List<Cliente> clientes = reader.leerArchivoClientes();
        List<Factura> facturas = reader.leerArchivoFacturas();
        List<Producto> productos = reader.leerArchivoProductos();
        List<FacturaProducto> facturasProductos = reader.leerArchivoFacturasProductos();

        // Obtener instancias de los DAOs
<<<<<<< HEAD
        DAO<ClienteDAO> dao = DAO.getInstance();
        ClienteDAO clienteDAO = ClienteDAO.getInstance();
        FacturaDAO facturaDAO = FacturaDAO.getInstance();
        ProductoDAO productoDAO = ProductoDAO.getInstance();
        FacturaProductoDAO facturaProductoDAO = FacturaProductoDAO.getInstance();
=======

        DatabaseFactory dBF = DatabaseFactory.getDAOFactory(1);
        DAO<Cliente> clienteDAO = dBF.getClienteDAO();
        DAO<Producto> productoDAO = dBF.getProductoDAO();
        DAO<FacturaProducto> facturaProductoDAO = dBF.getFacturaProductoDAO();
        DAO<Factura> facturaDAO = dBF.getFacturaDAO();
>>>>>>> d009bf191fba3fe8508043bc93cee2d04ad52f6c

        // Eliminar las tablas si existen y luego recrearlas
        clienteDAO.dropTable();
        clienteDAO.createTable();

        facturaProductoDAO.dropTable(); // Eliminar primero por las FK referenciadas!

        facturaDAO.dropTable();
        facturaDAO.createTable();

        productoDAO.dropTable();
        productoDAO.createTable();

        facturaProductoDAO.createTable();

        // Cargar datos en las tablas correspondientes
        cargarListaEnBaseDeDatos(clientes, clienteDAO);
        cargarListaEnBaseDeDatos(facturas, facturaDAO);
        cargarListaEnBaseDeDatos(productos, productoDAO);
        cargarListaEnBaseDeDatos(facturasProductos, facturaProductoDAO);
    }

    // Método genérico para cargar entidades en la base de datos usando cualquier DAO que implemente la interfaz DAO
    public static <T> void cargarListaEnBaseDeDatos(List<T> lista, DAO<T> dao) throws SQLException {
        for (T entidad : lista) {
            dao.insert(entidad);
        }
    }
}
