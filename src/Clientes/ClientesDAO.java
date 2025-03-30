package Clientes;

import Conexion.ConexionBD;
import Empleados.Empleados;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientesDAO
{
    private ConexionBD conexionBD = new ConexionBD();

    public void agregar(Clientes clientes){
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO clientes (nombre,telefono,direccion,correo) VALUES (?,?,?,?)";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,clientes.getNombre());
            pst.setString(2,clientes.getTelefono());
            pst.setString(3,clientes.getDireccion());
            pst.setString(4,clientes.getCorreo());

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Se ha agregado el cliente exitosamente");
            } else {
                JOptionPane.showMessageDialog(null,"Error al agregar el cliente");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void actualizar(Clientes clientes){
        Connection con = conexionBD.getConnection();
        String query = "UPDATE clientes SET nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id_cliente = ?";

        try{
                PreparedStatement pst = con.prepareStatement(query);

                pst.setString(1,clientes.getNombre());
                pst.setString(2,clientes.getTelefono());
                pst.setString(3,clientes.getDireccion());
                pst.setString(4,clientes.getCorreo());
                pst.setInt(5, clientes.getId_cliente());


                int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Se ha actualizado el estado del cliente");
            } else {
                JOptionPane.showMessageDialog(null,"Error al actualizar el estado del cliente");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void eliminar(int id_cliente){
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM clientes WHERE id_cliente = ?";

        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id_cliente);

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Cliente eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null,"Error al eliminar el cliente");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
