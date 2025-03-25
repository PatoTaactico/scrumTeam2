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
                double salario = Double.parseDouble(textField3.getText());

                Empleados empleados = new Empleados(0, nombre, cargo, salario);
                empleadosDAO.agregar(empleados);
                obtenerDatos();
                clear();
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

        table1.setModel(model);
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