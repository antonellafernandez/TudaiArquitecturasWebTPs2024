import helpers.CSVreader;
import daos.mysql.ClienteDAO;
import daos.mysql.FacturaDAO;
import daos.mysql.ProductoDAO;
import daos.mysql.FacturaProductoDAO;
import dtos.ClienteConFacturacionDTO;
import dtos.ProductoMayorRecaudacionDTO;
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
            ClienteDAO clienteDAO = ClienteDAO.getInstance();
            FacturaDAO facturaDAO = FacturaDAO.getInstance();
            ProductoDAO productoDAO = ProductoDAO.getInstance();
            FacturaProductoDAO facturaProductoDAO = FacturaProductoDAO.getInstance();

            // Eliminar las tablas si existen y luego recrearlas
            clienteDAO.dropTable();
            clienteDAO.createTable();

            facturaProductoDAO.dropTable(); // Eliminar primero por FK referenciadas!

            facturaDAO.dropTable();
            facturaDAO.createTable();

            productoDAO.dropTable();
            productoDAO.createTable();

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

            // Obtiene y muestra el producto con mayor recaudación
            ProductoMayorRecaudacionDTO productoMayorRecaudacion = productoDAO.obtenerProductoMayorRecaudacion();

            if (productoMayorRecaudacion != null) {
                System.out.println("Producto con mayor recaudación: ");
                System.out.println(productoMayorRecaudacion);
            } else {
                System.out.println("No se encontraron productos.");
            }

            // Obtiene y muestra la lista de clientes ordenada por facturación
            List<ClienteConFacturacionDTO> clientesFacturados = clienteDAO.obtenerClientesPorMayorFacturacionDesc();

            if (clientesFacturados.isEmpty()) {
                System.out.println("\nNo se encontraron clientes.");
            } else {
                System.out.println("\nClientes ordenados por mayor facturación: ");

                for (ClienteConFacturacionDTO cliente : clientesFacturados) {
                    System.out.println(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
