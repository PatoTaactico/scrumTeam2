package Empleados;

import Conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpleadosDAO
{
    private ConexionBD conexionBD = new ConexionBD();

    public void agregar(Empleados empleados){
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO empleados (nombre,cargo,salario) VALUES (?,?,?)";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,empleados.getNombre());
            pst.setString(2,empleados.getCargo());
            pst.setInt(3,empleados.getSalario());

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Se ha agregado el empleado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null,"Error al agregar el empleado");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void actualizar(Empleados empleados){
        Connection con = conexionBD.getConnection();
        String query = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id_empleado = ?";

        try{
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1,empleados.getNombre());
            pst.setString(2,empleados.getCargo());
            pst.setInt(3,empleados.getSalario());
            pst.setInt(4,empleados.getId_empleado());

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Se ha actualizado el estado del empleado");
            } else {
                JOptionPane.showMessageDialog(null,"Error al actualizar el estado del empleado");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void eliminar(int id_empleado){
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM empleados WHERE id_empleado = ?";

        try{
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1,id_empleado);

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Empleado despedido exitosamente");
            } else {
                JOptionPane.showMessageDialog(null,"Eror al despedir el empleado");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
