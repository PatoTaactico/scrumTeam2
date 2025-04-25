package Reportes;

import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class ReportesGUI extends JPanel {
    private JPanel main;
    private JPanel cardPanel;
    private JTable table1;
    private JTable table2;
    private JTable table3;
    private JComboBox<String> comboBox1;
    private JComboBox<String> comboBox2;
    private JComboBox<String> comboBox3;
    private JButton volverButton;

    private ReportesDAO reportesDAO = new ReportesDAO();

    public ReportesGUI() {
        setLayout(new BorderLayout());
        main = new JPanel(new BorderLayout());
        add(main);

        // Panel lateral con comboBox1, comboBox2 y comboBox3
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(228, 224, 246));

        comboBox1 = new JComboBox<>(new String[]{"Diario", "Semanal", "Mensual"});
        comboBox2 = new JComboBox<>(new String[]{"Pendiente", "Enviado", "Pagado"});
        comboBox3 = new JComboBox<>(new String[]{"Ventas", "Productos", "Clientes"});
        volverButton = new JButton("Salir");

        // Estilo para los JComboBox
        Dimension comboBoxSize = new Dimension(150, 25);
        comboBox1.setPreferredSize(comboBoxSize);
        comboBox2.setPreferredSize(comboBoxSize);
        comboBox3.setPreferredSize(comboBoxSize);

        comboBox1.setFont(new Font("Serif", Font.PLAIN, 14));
        comboBox2.setFont(new Font("Serif", Font.PLAIN, 14));
        comboBox3.setFont(new Font("Serif", Font.PLAIN, 14));

        // Estilo para el botón de salir
        volverButton.setBackground(Color.RED);
        volverButton.setForeground(Color.WHITE);
        volverButton.setFont(new Font("Serif", Font.BOLD, 14));
        volverButton.setFocusPainted(false);

        sidePanel.add(new JLabel("Selecciona el filtro:"));
        sidePanel.add(comboBox1);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(new JLabel("Selecciona el estado:"));
        sidePanel.add(comboBox2);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(new JLabel("Selecciona la tabla:"));
        sidePanel.add(comboBox3);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(volverButton);

        main.add(sidePanel, BorderLayout.WEST);

        // Panel central con CardLayout para las tablas
        cardPanel = new JPanel(new CardLayout());
        table1 = new JTable();
        table2 = new JTable();
        table3 = new JTable();

        cardPanel.add(new JScrollPane(table1), "Ventas");
        cardPanel.add(new JScrollPane(table2), "Productos");
        cardPanel.add(new JScrollPane(table3), "Clientes");

        main.add(cardPanel, BorderLayout.CENTER);

        // Cargar datos iniciales
        cargarDatosTabla("Ventas");

        // Listener para comboBox1
        comboBox1.addActionListener(e -> {
            String filtroSeleccionado = (String) comboBox1.getSelectedItem();
            cargarDatosTabla(filtroSeleccionado);
        });

        // Listener para comboBox2
        comboBox2.addActionListener(e -> {
            int filaSeleccionada = table1.getSelectedRow();
            if (filaSeleccionada != -1) {
                int idVenta = (int) table1.getValueAt(filaSeleccionada, 0);
                String nuevoEstado = (String) comboBox2.getSelectedItem();

                boolean actualizado = reportesDAO.actualizarEstadoVenta(idVenta, nuevoEstado);
                if (actualizado) {
                    JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                    table1.setValueAt(nuevoEstado, filaSeleccionada, 3);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el estado.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una venta para cambiar el estado.");
            }
        });

        // Listener para comboBox3
        comboBox3.addActionListener(e -> {
            String seleccion = (String) comboBox3.getSelectedItem();
            cargarDatosTabla(seleccion);
            ((CardLayout) cardPanel.getLayout()).show(cardPanel, seleccion);
        });

        // Listener para botón volver
        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });
    }

    public void cargarDatosTabla(String tabla) {
        switch (tabla) {
            case "Ventas":
                cargarDatosVentas();
                break;
            case "Productos":
                cargarDatosProductos();
                break;
            case "Clientes":
                cargarDatosClientes();
                break;
        }
    }

    private void cargarDatosVentas() {
        ResultSet rsVentas = reportesDAO.obtenerVentasFiltradas("Diario");
        DefaultTableModel modeloVentas = new DefaultTableModel();
        modeloVentas.setColumnIdentifiers(new String[]{"ID Venta", "Total", "Fecha", "Estado"});

        try {
            while (rsVentas != null && rsVentas.next()) {
                modeloVentas.addRow(new Object[]{
                        rsVentas.getInt("id_venta"),
                        rsVentas.getDouble("total"),
                        rsVentas.getDate("fecha"),
                        rsVentas.getString("estado")
                });
            }
            table1.setModel(modeloVentas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosProductos() {
        ResultSet rsProductos = reportesDAO.obtenerProductosMasVendidos("Diario");
        DefaultTableModel modeloProductos = new DefaultTableModel();
        modeloProductos.setColumnIdentifiers(new String[]{"Producto", "Cantidad Vendida"});

        try {
            while (rsProductos != null && rsProductos.next()) {
                modeloProductos.addRow(new Object[]{
                        rsProductos.getString("nombre_producto"),
                        rsProductos.getInt("total_vendidos")
                });
            }
            table2.setModel(modeloProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatosClientes() {
        ResultSet rsClientes = reportesDAO.obtenerClientesTop("Diario");
        DefaultTableModel modeloClientes = new DefaultTableModel();
        modeloClientes.setColumnIdentifiers(new String[]{"Nombre Cliente", "Total Comprado"});

        try {
            while (rsClientes != null && rsClientes.next()) {
                modeloClientes.addRow(new Object[]{
                        rsClientes.getString("nombre_cliente"),
                        rsClientes.getDouble("total_compras")
                });
            }
            table3.setModel(modeloClientes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reportes");
        frame.setContentPane(new ReportesGUI());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}