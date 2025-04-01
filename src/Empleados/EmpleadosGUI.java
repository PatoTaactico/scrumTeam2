package Empleados;

import Conexion.ConexionBD;
import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadosGUI {
    private JTextField textField1, textField2, textField3;
    private JComboBox<String> comboBox1;
    private JButton consultarButton, actualizarButton, eliminarButton, volverButton;
    private JPanel main;
    private JTable table1;
    EmpleadosDAO empleadosDAO = new EmpleadosDAO();

    public EmpleadosGUI() {
        main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(new Color(230, 230, 250));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(200, 200, 255));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));
        formPanel.setBackground(new Color(220, 220, 250));

        formPanel.add(new JLabel("ID:"));
        textField1 = new JTextField();
        textField1.setEnabled(false);
        formPanel.add(textField1);

        formPanel.add(new JLabel("Nombre:"));
        textField2 = new JTextField();
        formPanel.add(textField2);

        formPanel.add(new JLabel("Cargo:"));
        comboBox1 = new JComboBox<>(new String[]{"Gerente", "Cajero", "Vendedor"});
        formPanel.add(comboBox1);

        formPanel.add(new JLabel("Salario:"));
        textField3 = new JTextField();
        formPanel.add(textField3);

        topPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 15, 10));
        buttonPanel.setBackground(new Color(200, 200, 255));

        consultarButton = createStyledButton("Consultar", new Color(76, 175, 80));
        actualizarButton = createStyledButton("Actualizar", new Color(33, 150, 243));
        eliminarButton = createStyledButton("Eliminar", new Color(244, 67, 54));
        volverButton = createStyledButton("Volver", new Color(255, 152, 0));

        buttonPanel.add(consultarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(volverButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        table1 = new JTable();
        table1.setBackground(new Color(255, 250, 205));
        JScrollPane scrollPane = new JScrollPane(table1);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(scrollPane, BorderLayout.CENTER);

        obtenerDatos();
        agregarEventos();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
        button.setPreferredSize(new Dimension(90, 35));
        return button;
    }

    private void agregarEventos() {
        consultarButton.addActionListener(e -> {
            String nombre = textField2.getText();
            String cargo = (String) comboBox1.getSelectedItem();
            int salario = Integer.parseInt(textField3.getText());

            Empleados empleados = new Empleados(0, nombre, cargo, salario);
            empleadosDAO.agregar(empleados);
            obtenerDatos();
            clear();
        });

        actualizarButton.addActionListener(e -> {
            String nombre = textField2.getText();
            String cargo = (String) comboBox1.getSelectedItem();
            int salario = Integer.parseInt(textField3.getText());
            int id_empleado = Integer.parseInt(textField1.getText());

            Empleados empleados = new Empleados(id_empleado, nombre, cargo, salario);
            empleadosDAO.actualizar(empleados);
            obtenerDatos();
            clear();
        });

        eliminarButton.addActionListener(e -> {
            int id_empleado = Integer.parseInt(textField1.getText());
            empleadosDAO.eliminar(id_empleado);
            obtenerDatos();
            clear();
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

        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });
    }

    public void clear() {
        textField1.setText("");
        textField2.setText("");
        comboBox1.setSelectedIndex(0);
        textField3.setText("");
    }

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Empleado");
        model.addColumn("Nombre");
        model.addColumn("Cargo");
        model.addColumn("Salario");

        table1.setModel(model);
        Connection con = new ConexionBD().getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM empleados");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Empleados");
        frame.setContentPane(new EmpleadosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
