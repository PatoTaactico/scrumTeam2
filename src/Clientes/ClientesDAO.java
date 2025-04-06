package Clientes;

import Conexion.ConexionBD;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase DAO (Data Access Object) para manejar las operaciones de base de datos
 * relacionadas con los clientes, como agregar, actualizar y eliminar.
 */
public class ClientesDAO {

    /** Objeto para manejar la conexión a la base de datos. */
    private ConexionBD conexionBD = new ConexionBD();

    /**
     * Agrega un nuevo cliente a la base de datos.
     *
     * @param clientes Objeto Clientes que contiene los datos del cliente a agregar
     */
    public void agregar(Clientes clientes) {
        Connection con = conexionBD.getConnection();
        String query = "INSERT INTO clientes (nombre,telefono,direccion,correo) VALUES (?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientes.getNombre());
            pst.setString(2, clientes.getTelefono());
            pst.setString(3, clientes.getDireccion());
            pst.setString(4, clientes.getCorreo());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Se ha agregado el cliente exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la información de un cliente existente en la base de datos.
     *
     * @param clientes Objeto Clientes con los datos actualizados
     */
    public void actualizar(Clientes clientes) {
        Connection con = conexionBD.getConnection();
        String query = "UPDATE clientes SET nombre = ?, telefono = ?, direccion = ?, correo = ? WHERE id_cliente = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, clientes.getNombre());
            pst.setString(2, clientes.getTelefono());
            pst.setString(3, clientes.getDireccion());
            pst.setString(4, clientes.getCorreo());
            pst.setInt(5, clientes.getId_cliente());

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Se ha actualizado el estado del cliente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el estado del cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un cliente de la base de datos según su ID.
     *
     * @param id_cliente ID del cliente a eliminar
     */
    public void eliminar(int id_cliente) {
        Connection con = conexionBD.getConnection();
        String query = "DELETE FROM clientes WHERE id_cliente = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id_cliente);

            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserta un nuevo cliente en la base de datos utilizando parámetros individuales.
     *
     * @param nombre Nombre del cliente
     * @param telefono Teléfono del cliente
     * @param direccion Dirección del cliente
     * @param correo Correo electrónico del cliente
     */
    public void insertarCliente(String nombre, String telefono, String direccion, String correo) {
        Clientes cliente = new Clientes();
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.setCorreo(correo);
        agregar(cliente);
    }

    /**
     * Actualiza un cliente existente en la base de datos utilizando parámetros individuales.
     *
     * @param id ID del cliente
     * @param nombre Nuevo nombre del cliente
     * @param telefono Nuevo teléfono del cliente
     * @param direccion Nueva dirección del cliente
     * @param correo Nuevo correo electrónico del cliente
     */
    public void actualizarCliente(int id, String nombre, String telefono, String direccion, String correo) {
        Clientes cliente = new Clientes();
        cliente.setId_cliente(id);
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.setCorreo(correo);
        actualizar(cliente);
    }

    /**
     * Elimina un cliente de la base de datos utilizando su ID.
     *
     * @param id ID del cliente a eliminar
     */
    public void eliminarCliente(int id) {
        eliminar(id);
    }
}
