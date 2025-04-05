package Clientes;

import Conexion.ConexionBD;
import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientesGUI extends JFrame {
    private JTextField textField1, textField2, textField3, textField4, textField5;
    private JButton crearButton, actualizarButton, eliminarButton, volverButton;
    private JPanel main;
    private JTable table1;
    ClientesDAO clientesDAO = new ClientesDAO();

    public ClientesGUI() {

        main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(new Color(230, 230, 250));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(200, 200, 255));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        formPanel.setBackground(new Color(220, 220, 250));

        formPanel.add(new JLabel("ID:"));
        textField1 = new JTextField();
        textField1.setEnabled(false);
        formPanel.add(textField1);

        formPanel.add(new JLabel("Nombre:"));
        textField2 = new JTextField();
        formPanel.add(textField2);

        formPanel.add(new JLabel("Teléfono:"));
        textField3 = new JTextField();
        formPanel.add(textField3);

        formPanel.add(new JLabel("Dirección:"));
        textField4 = new JTextField();
        formPanel.add(textField4);

        formPanel.add(new JLabel("Correo:"));
        textField5 = new JTextField();
        formPanel.add(textField5);

        topPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(200, 200, 255));

        crearButton = createStyledButton("Crear", new Color(76, 175, 80));
        actualizarButton = createStyledButton("Actualizar", new Color(33, 150, 243));
        eliminarButton = createStyledButton("Eliminar", new Color(244, 67, 54));
        volverButton = createStyledButton("Volver", new Color(255, 152, 0));

        buttonPanel.add(crearButton);
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
        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });

        crearButton.addActionListener(e -> {
            String nombre = textField2.getText();
            String telefono = textField3.getText();
            String direccion = textField4.getText();
            String correo = textField5.getText();

            if (!nombre.isEmpty() && !telefono.isEmpty()) {
                clientesDAO.insertarCliente(nombre, telefono, direccion, correo);
                obtenerDatos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Nombre y Teléfono son obligatorios.");
            }
        });

        actualizarButton.addActionListener(e -> {
            if (textField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente para actualizar.");
                return;
            }
            int id = Integer.parseInt(textField1.getText());
            String nombre = textField2.getText();
            String telefono = textField3.getText();
            String direccion = textField4.getText();
            String correo = textField5.getText();

            clientesDAO.actualizarCliente(id, nombre, telefono, direccion, correo);
            obtenerDatos();
            limpiarCampos();
        });

        eliminarButton.addActionListener(e -> {
            if (textField1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminar.");
                return;
            }
            int id = Integer.parseInt(textField1.getText());
            clientesDAO.eliminarCliente(id);
            obtenerDatos();
            limpiarCampos();
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = table1.getSelectedRow();
                textField1.setText(table1.getValueAt(fila, 0).toString());
                textField2.setText(table1.getValueAt(fila, 1).toString());
                textField3.setText(table1.getValueAt(fila, 2).toString());
                textField4.setText(table1.getValueAt(fila, 3).toString());
                textField5.setText(table1.getValueAt(fila, 4).toString());
            }
        });
    }

    public void obtenerDatos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Cliente");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Direccion");
        model.addColumn("Correo");

        table1.setModel(model);
        Connection con = new ConexionBD().getConnection();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clientes");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clientes");
        frame.setContentPane(new ClientesGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
