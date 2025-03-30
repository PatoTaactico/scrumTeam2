package Proveedores;

import Conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProveedoresDAO
{
    private ConexionBD conexionBD = new ConexionBD();

    public void agregar(Proveedores proveedores){
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO proveedores (nombre,telefono,categoria_producto,nombre_producto,precio_proveedor) VALUES (?,?,?,?,?)";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,proveedores.getNombre());
            pst.setString(2,proveedores.getTelefono());
            pst.setString(3,proveedores.getCategoria_producto());
            pst.setString(4,proveedores.getNombre_producto());
            pst.setInt(5,proveedores.getId_proveedor());

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Se ha agregado al proveedor exitosamente");
            } else {
                JOptionPane.showMessageDialog(null,"Error al agregar el proveedor");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void actualizar(Proveedores proveedores){
        Connection con = conexionBD.getConnection();
        String query = "UPDATE proveedores SET nombre = ?, telefono = ?, categoria_producto = ?, nombre_producto, precio_proveedor = ? = ? WHERE id_proveedor = ?";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,proveedores.getNombre());
            pst.setString(1,proveedores.getTelefono());
            pst.setString(1,proveedores.getCategoria_producto());
            pst.setString(2,proveedores.getNombre_producto());
            pst.setInt(3,proveedores.getPrecio_proveedor());
            pst.setInt(4,proveedores.getId_proveedor());

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Se ha actualizado el estado del poveedor");
            } else {
                JOptionPane.showMessageDialog(null,"Error al actualizar el estado del proveedor");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void eliminar(int id_proveedor){
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM proveedores WHERE id_proveedor = ?";

        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id_proveedor);

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Provedor eliminado");
            } else {
                JOptionPane.showMessageDialog(null,"Error al eliminar el empleado");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
