package Inventario_productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Inventario_productosDAO {
    private Connection connection;

    public Inventario_productosDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Inventario_productos> obtenerProductos() throws SQLException {
        List<Inventario_productos> productos = new ArrayList<>();
        String query = "SELECT id, nombre, categoria, precio, cantidad, idProveedor FROM productos";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String categoria = rs.getString("categoria");
            int precio = rs.getInt("precio");
            int cantidad = rs.getInt("cantidad");
            int idProveedor = rs.getInt("idProveedor");

            Inventario_productos producto = new Inventario_productos(id, nombre, categoria, precio, cantidad, idProveedor);
            productos.add(producto);
        }
        rs.close();
        stmt.close();
        return productos;
    }

    public void agregarProducto(Inventario_productos producto) throws SQLException {
        String query = "INSERT INTO productos(nombre, categoria, precio, cantidad, idProveedor) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, producto.getNombre());
        pstmt.setString(2, producto.getCategoria());
        pstmt.setInt(3, producto.getPrecio());
        pstmt.setInt(4, producto.getCantidad());
        pstmt.setInt(5, producto.getProveedorId());
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void actualizarProducto(Inventario_productos producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, categoria = ?, precio = ?, cantidad = ?, idProveedor = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, producto.getNombre());
        pstmt.setString(2, producto.getCategoria());
        pstmt.setInt(3, producto.getPrecio());
        pstmt.setInt(4, producto.getCantidad());
        pstmt.setInt(5, producto.getProveedorId());
        pstmt.setInt(6, producto.getId());
        pstmt.executeUpdate();
        pstmt.close();
    }


    public void eliminarProducto(int id) throws SQLException {
        String query = "DELETE FROM productos WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
