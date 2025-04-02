package Inventario_productos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Inventario_productosDAO {
    private Connection conexion;

    public Inventario_productosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void agregarProducto(Inventario_productos producto) throws SQLException {
        String sql = "INSERT INTO inventario_producto (nombre, categoria, precio, cantidad, proveedor_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.setInt(5, producto.getProveedorId());
            stmt.executeUpdate();
        }
    }

    public List<Inventario_productos> obtenerProductos() throws SQLException {
        List<Inventario_productos> productos = new ArrayList<>();
        String sql = "SELECT * FROM inventario_producto";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Inventario_productos(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getInt("precio"),
                        rs.getInt("cantidad"),
                        rs.getInt("proveedor_id")
                ));
            }
        }
        return productos;
    }

    public void actualizarProducto(Inventario_productos producto) throws SQLException {
        String sql = "UPDATE inventario_producto SET nombre=?, categoria=?, precio=?, cantidad=?, proveedor_id=? WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.setInt(5, producto.getProveedorId());
            stmt.setInt(6, producto.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM inventario_producto WHERE id=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
