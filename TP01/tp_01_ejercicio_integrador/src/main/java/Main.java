import helpers.CSVreader;
import helpers.DatabaseLoader;
import daos.ClienteDAO;
import daos.ProductoDAO;
import dtos.ClienteConFacturacionDTO;
import dtos.ProductoMayorRecaudacionDTO;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVreader reader = new CSVreader();

        try {
            // Cargar todos los datos desde los archivos CSV a la base de datos
            DatabaseLoader.cargarDatos(reader);

            // Obtener instancias de DAOs para las consultas
            ClienteDAO clienteDAO = ClienteDAO.getInstance();
            ProductoDAO productoDAO = ProductoDAO.getInstance();

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
