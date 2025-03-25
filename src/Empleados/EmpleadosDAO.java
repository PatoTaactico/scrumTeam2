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
            pst.setDouble(3,empleados.getSalario());

            int resultado = pst.executeUpdate();
            if (resultado>0){
                JOptionPane.showMessageDialog(null,"Empleado agregado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null,"Error al agregar el empleado");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
