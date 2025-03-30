package Proveedores;

import Conexion.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProveedoresGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    int filas = 0;

    ProveedoresDAO proveedoresDAO = new ProveedoresDAO();

    public ProveedoresGUI() {
        textField1.setEnabled(false);
        obtenerDatos();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String telefono = textField3.getText();
                String categoria_producto = (String) comboBox1.getSelectedItem();

                Proveedores proveedores = new Proveedores(0,nombre,telefono,categoria_producto);
                proveedoresDAO.agregar(proveedores);
                obtenerDatos();
                clear();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String telefono = textField3.getText();
                String categoria_producto = (String) comboBox1.getSelectedItem();
                int id_proveedor = Integer.parseInt(textField1.getText());

                Proveedores proveedores = new Proveedores(id_proveedor,nombre,telefono,categoria_producto);
                proveedoresDAO.actualizar(proveedores);
                obtenerDatos();
                clear();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_proveedor = Integer.parseInt(textField1.getText());

                proveedoresDAO.eliminar(id_proveedor);
                obtenerDatos();
                clear();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1) {
                    textField1.setText(table1.getValueAt(filaSeleccionada, 0).toString());
                    textField2.setText(table1.getValueAt(filaSeleccionada, 1).toString());
                    textField3.setText(table1.getValueAt(filaSeleccionada, 2).toString());
                    comboBox1.setSelectedItem(table1.getValueAt(filaSeleccionada, 3).toString());
                }
            }
        });
    }
    public void clear() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        comboBox1.setSelectedIndex(0);
    }

    ConexionBD conexionBD = new ConexionBD();

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Proveedor");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Categoria Producto");

        table1.setModel(model);
        String[] dato = new String[6];
        Connection con = conexionBD.getConnection();

        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM proveedores";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);

                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Proveedores");
        frame.setContentPane(new ProveedoresGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 700);
        frame.setResizable(false);
    }
}

