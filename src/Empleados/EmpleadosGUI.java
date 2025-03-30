package Empleados;

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

public class EmpleadosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JTextField textField3;
    private JButton consultarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    int filas = 0;

    EmpleadosDAO empleadosDAO = new EmpleadosDAO();

    public EmpleadosGUI() {
        textField1.setEnabled(false);
        obtenerDatos();

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = (String) comboBox1.getSelectedItem();
                int salario = Integer.parseInt(textField3.getText());

                Empleados empleados = new Empleados(0, nombre, cargo, salario);
                empleadosDAO.agregar(empleados);
                obtenerDatos();
                clear();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField2.getText();
                String cargo = (String) comboBox1.getSelectedItem();
                int salario = Integer.parseInt(textField3.getText());
                int id_empleado = Integer.parseInt(textField1.getText());

                Empleados empleados = new Empleados(id_empleado, nombre, cargo, salario);
                empleadosDAO.actualizar(empleados);
                obtenerDatos();
                clear();
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id_empleado = Integer.parseInt(textField1.getText());

                empleadosDAO.eliminar(id_empleado);
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
                }
            }
        });
    }
    public void clear() {
        textField1.setText("");
        textField2.setText("");
        comboBox1.setSelectedIndex(0);
        textField3.setText("");
    }

    ConexionBD conexionBD = new ConexionBD();

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Empleado");
        model.addColumn("Nombre");
        model.addColumn("Cargo");
        model.addColumn("Salario");

        table1.setModel(model);  // Asegúrate de asignar el modelo antes de llenar la tabla
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM empleados";
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
        JFrame frame = new JFrame("Empleados");
        frame.setContentPane(new EmpleadosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 700);
        frame.setResizable(false);
    }
}