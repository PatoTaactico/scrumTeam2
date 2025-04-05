package Inventario_productos;

import Conexion.ConexionBD;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Inventario_productosDAO {
    private ConexionBD conexionBD = new ConexionBD();


    public List<Inventario_productos> obtenerProductos() {
        List<Inventario_productos> lista = new ArrayList<>();
        Connection con = conexionBD.getConnection();

        try {
            String query = "SELECT * FROM inventario_productos";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id_producto = rs.getInt("id_producto");
                String nombre_producto = rs.getString("nombre_producto");
                String categoria = rs.getString("categoria");
                int cantidad_stock = rs.getInt("cantidad_stock");
                int precio = rs.getInt("precio_producto");
                int id_proveedor_asociado = rs.getInt("id_proveedor_asociado");

                // Si el proveedor es null en la base de datos, setea como null en el objeto
                Integer idProveedorAsociado = rs.wasNull() ? null : Integer.valueOf(id_proveedor_asociado);


                Inventario_productos producto = new Inventario_productos(
                        id_producto, nombre_producto, categoria, precio, cantidad_stock, id_proveedor_asociado
                );
                lista.add(producto);
            }

            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }


    public void agregar(Inventario_productos producto) {
        String sql = "INSERT INTO inventario_productos (nombre_producto, categoria, cantidad_stock, precio_producto, id_proveedor_asociado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conexionBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre_producto());
            stmt.setString(2, producto.getCategoria());
            stmt.setInt(3, producto.getCantidad_stock());
            stmt.setInt(4, producto.getPrecio_producto());

            if (producto.getId_Proveedor_asociado() != null) {
                stmt.setInt(5, producto.getId_Proveedor_asociado());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void eliminar(int id_producto) {
        Connection con = conexionBD.getConnection();

        try {
            String query = "DELETE FROM inventario_productos WHERE id_producto = ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_producto);

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }

            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void actualizar(Inventario_productos Inventario_productos) {
        Connection con = conexionBD.getConnection();

        try {
            if (Inventario_productos.getId_Proveedor_asociado() != null &&
                    !validarProveedor(Inventario_productos.getId_Proveedor_asociado())) {
                JOptionPane.showMessageDialog(null,
                        "Error: El proveedor con ID " + Inventario_productos.getId_Proveedor_asociado() + " no existe",
                        "Error de Proveedor",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "UPDATE inventario_productos SET nombre_producto = ?, categoria = ?, cantidad_stock = ?, precio_producto = ?, id_proveedor_asociado = ? WHERE id_producto = ?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, Inventario_productos.getNombre_producto());
            pst.setString(2, Inventario_productos.getCategoria());
            pst.setInt(3, Inventario_productos.getCantidad_stock());
            pst.setInt(4, Inventario_productos.getPrecio_producto());

            if (Inventario_productos.getId_Proveedor_asociado() != null) {
                pst.setInt(5, Inventario_productos.getId_Proveedor_asociado());
            } else {
                pst.setNull(5, java.sql.Types.INTEGER);
            }

            pst.setInt(6, Inventario_productos.getId_producto());

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al modificar producto");
            }

            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean validarProveedor(int id_proveedor) {
        Connection con = conexionBD.getConnection();

        try {
            String query = "SELECT COUNT(*) FROM proveedores WHERE id_proveedor = ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_proveedor);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar proveedor: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}

