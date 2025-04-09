package Ordenes_compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ordenes_compraDAO {
    private Connection conexion;

    public Ordenes_compraDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<Ordenes_compra> obtenerOrdenes() {
        List<Ordenes_compra> lista = new ArrayList<>();
        try {
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ordenes_compra");

            while (rs.next()) {
                Ordenes_compra orden = new Ordenes_compra(
                        rs.getInt("id_orden"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_empleado"),
                        rs.getString("fecha"),
                        rs.getDouble("total"),
                        rs.getString("estado")
                );
                lista.add(orden);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void agregarOrden(Ordenes_compra orden) {
        String sql = "INSERT INTO ordenes_compra (id_cliente, id_empleado, fecha_compra, total, estado_orden) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, orden.getid_cliente());
            stmt.setInt(2, orden.getid_empleado());
            stmt.setString(3, orden.getfecha_compra());
            stmt.setDouble(4, orden.getTotal());
            stmt.setString(5, orden.getestado_orden());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarOrden(int id) {
        try {
            PreparedStatement stmt = conexion.prepareStatement("DELETE FROM ordenes_compra WHERE id_orden = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarOrden(Ordenes_compra orden) {
        String sql = "UPDATE ordenes_compra SET id_cliente=?, id_empleado=?, fecha_compra=?, total=?, estado_orden=? WHERE id_producto=?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, orden.getid_cliente());
            stmt.setInt(2, orden.getid_empleado());
            stmt.setString(3, orden.getfecha_compra());
            stmt.setDouble(4, orden.getTotal());
            stmt.setString(5, orden.getestado_orden());
            stmt.setInt(6, orden.getid_producto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

