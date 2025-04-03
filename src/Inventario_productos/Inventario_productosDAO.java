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
        String query = "SELECT id_producto, nombre_producto, categoria, precio_producto, cantidad_stock, id_Proveedor_asociado FROM productos";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            int id_producto = rs.getInt("id_producto");
            String nombre_producto = rs.getString("nombre_producto");
            String categoria = rs.getString("categoria");
            int precio_producto = rs.getInt("precio_producto");
            int cantidad_stock = rs.getInt("cantidad_stock");
            int id_Proveedor_asociado = rs.getInt("id_Proveedor_asociado");

            Inventario_productos producto = new Inventario_productos(id_producto, nombre_producto, categoria, precio_producto, cantidad_stock, id_Proveedor_asociado);
            productos.add(producto);
        }
        rs.close();
        stmt.close();
        return productos;
    }

    public void agregarProducto(Inventario_productos producto) throws SQLException {
        String query = "INSERT INTO productos(nombre_producto, categoria, precio_producto, cantidad_stock, id_Proveedor_asociado) VALUES (?, ?, ?, ?, ?)";
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
        String query = "UPDATE productos SET nombre_producto = ?, categoria = ?, precio_producto = ?, cantidad_stock = ?, id_Proveedor_asociado = ? WHERE id = ?";
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
        String query = "DELETE FROM productos WHERE id_producto = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
}

