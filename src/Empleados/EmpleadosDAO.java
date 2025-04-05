package Empleados;

import Conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDAO {

    public void agregar(Empleados empleado) {
        String sql = "INSERT INTO empleados (nombre, cargo, salario) VALUES (?, ?, ?)";

        try (Connection con = new ConexionBD().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getCargo());
            ps.setInt(3, empleado.getSalario());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
        }
    }

    public Empleados consultarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id_empleado = ?";

        try (Connection con = new ConexionBD().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Empleados(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getInt("salario")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar empleado por ID: " + e.getMessage());
        }

        return null;
    }

    public List<Empleados> buscarPorCampo(String campo, String valor) {
        List<Empleados> lista = new ArrayList<>();
        String sql;
        PreparedStatement ps = null;

        try (Connection con = new ConexionBD().getConnection()) {

            switch (campo) {
                case "nombre":
                case "cargo":
                    sql = "SELECT * FROM empleados WHERE " + campo + " LIKE ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, "%" + valor + "%");
                    break;
                case "id_empleado":
                    sql = "SELECT * FROM empleados WHERE id_empleado = ?";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, Integer.parseInt(valor));
                    break;
                default:
                    sql = "SELECT * FROM empleados";
                    ps = con.prepareStatement(sql);
                    break;
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Empleados(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("cargo"),
                        rs.getInt("salario")
                ));
            }

        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error al buscar empleados: " + e.getMessage());
        }

        return lista;
    }

    public void actualizar(Empleados empleado) {
        String sql = "UPDATE empleados SET nombre = ?, cargo = ?, salario = ? WHERE id_empleado = ?";

        try (Connection con = new ConexionBD().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getCargo());
            ps.setInt(3, empleado.getSalario());
            ps.setInt(4, empleado.getId_empleado());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (Connection con = new ConexionBD().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
        }
    }
}
