package Empleados;

import Conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos (DAO) para la entidad Empleados.
 * Se encarga de realizar operaciones CRUD en la base de datos.
 */
public class EmpleadosDAO {

    /**
     * Agrega un nuevo empleado a la base de datos.
     *
     * @param empleado Objeto Empleados a agregar
     */
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

    /**
     * Consulta un empleado en la base de datos según su ID.
     *
     * @param id ID del empleado a consultar
     * @return Objeto Empleados si se encuentra, o null si no
     */
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

    /**
     * Busca empleados por un campo específico.
     *
     * @param campo Campo por el cual filtrar (nombre, cargo o id_empleado)
     * @param valor Valor a buscar
     * @return Lista de empleados que cumplen con el criterio
     */
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

    /**
     * Actualiza un empleado existente en la base de datos.
     *
     * @param empleado Objeto Empleados con los nuevos datos
     */
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

    /**
     * Elimina un empleado de la base de datos por su ID.
     *
     * @param id ID del empleado a eliminar
     */
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
