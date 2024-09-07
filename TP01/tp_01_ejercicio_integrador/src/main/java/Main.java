import csv.CSVreader;
import daos.mysql.MySqlClienteDAO;
import daos.mysql.MySqlFacturaDAO;
import daos.mysql.MySqlProductoDAO;
import daos.mysql.MySqlFacturaProductoDAO;
import entities.Cliente;
import entities.Factura;
import entities.Producto;
import entities.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVreader reader = new CSVreader();

        try {
            // Obtener las instancias de los DAOs
            MySqlClienteDAO clienteDAO = MySqlClienteDAO.getInstance();
            MySqlFacturaDAO facturaDAO = MySqlFacturaDAO.getInstance();
            MySqlProductoDAO productoDAO = MySqlProductoDAO.getInstance();
            MySqlFacturaProductoDAO facturaProductoDAO = MySqlFacturaProductoDAO.getInstance();

            // Eliminar las tablas si existen y luego recrearlas
            clienteDAO.dropTable();
            clienteDAO.createTable();
            facturaDAO.dropTable();
            facturaDAO.createTable();
            productoDAO.dropTable();
            productoDAO.createTable();
            facturaProductoDAO.dropTable();
            facturaProductoDAO.createTable();

            // Cargar los datos de los archivos CSV
            List<Cliente> clientes = reader.leerArchivoClientes();
            List<Factura> facturas = reader.leerArchivoFacturas();
            List<Producto> productos = reader.leerArchivoProductos();
            List<FacturaProducto> facturasProductos = reader.leerArchivoFacturasProductos();

            // Insertar los clientes en la base de datos
            for (Cliente cliente : clientes) {
                clienteDAO.insert(cliente);
            }

            // Insertar las facturas en la base de datos
            for (Factura factura : facturas) {
                facturaDAO.insert(factura);
            }

            // Insertar los productos en la base de datos
            for (Producto producto : productos) {
                productoDAO.insert(producto);
            }

            // Insertar las relaciones FacturaProducto en la base de datos
            for (FacturaProducto fp : facturasProductos) {
                facturaProductoDAO.insert(fp);
            }

            // Seleccionar y mostrar todos los clientes
            List<Cliente> clientesDB = clienteDAO.selectAll();
            System.out.println("Clientes:");
            for (Cliente c : clientesDB) {
                System.out.println(c.getIdCliente() + " - " + c.getNombre() + " - " + c.getEmail());
            }

            // Seleccionar y mostrar todas las facturas
            List<Factura> facturasDB = facturaDAO.selectAll();
            System.out.println("\nFacturas:");
            for (Factura f : facturasDB) {
                System.out.println(f.getIdFactura() + " - Cliente: " + f.getIdCliente());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
