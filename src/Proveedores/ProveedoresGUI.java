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
    private JComboBox<String> comboBox1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;

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
                String nombre_producto = textField4.getText();
                int precio_proveedor = Integer.parseInt(textField5.getText());


                Proveedores proveedores = new Proveedores(0, nombre, telefono, categoria_producto, nombre_producto, precio_proveedor);
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
                String nombre_producto = textField4.getText();
                int precio_proveedor = Integer.parseInt(textField5.getText());
                int id_proveedor = Integer.parseInt(textField1.getText());

                Proveedores proveedores = new Proveedores(id_proveedor, nombre, telefono, categoria_producto, nombre_producto, precio_proveedor);
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
                    comboBox1.setSelectedItem(table1.getValueAt(filaSeleccionada, 2).toString());
                    textField3.setText(table1.getValueAt(filaSeleccionada, 3).toString());
                    textField4.setText(table1.getValueAt(filaSeleccionada, 4).toString());
                    textField5.setText(table1.getValueAt(filaSeleccionada, 5).toString());
                }
            }
        });
    }

    public void clear() {
        textField1.setText("");
        textField2.setText("");
        comboBox1.setSelectedIndex(0);
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }

    ConexionBD conexionBD = new ConexionBD();

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Proveedor");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Categoria_Producto");
        model.addColumn("Nombre_Producto");
        model.addColumn("Precio_Proveedor");

        table1.setModel(model);
        String[] dato = new String[6];
        Connection con = conexionBD.getConnection();

        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM proveedores";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                for (int i = 0; i < 6; i++) {
                    dato[i] = rs.getString(i + 1);
                }
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