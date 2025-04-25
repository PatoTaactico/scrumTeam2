package OrdenesCompra;

import Conexion.ConexionBD;
import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrdenesCompraGUI {
    private JTable table1;
    private JTextField idOrdenCompra;
    private JTextField idCliente;
    private JTextField idEmpleado;
    private JTextField idProducto;
    private JTextField total;
    private JComboBox<String> estadoCompra;
    private JTextField fechaCompra;
    private JButton actualizarCompraButton;
    private JButton volverButton;
    private JPanel main;

    public OrdenesCompraGUI() {
        aplicarEstilos();
        cargarDatos();

        // Listener para seleccionar una fila de la tabla
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1) {
                    try {
                        idOrdenCompra.setText(table1.getValueAt(filaSeleccionada, 0).toString());
                        idCliente.setText(table1.getValueAt(filaSeleccionada, 1).toString());
                        idEmpleado.setText(table1.getValueAt(filaSeleccionada, 2).toString());
                        idProducto.setText(table1.getValueAt(filaSeleccionada, 3).toString());
                        total.setText(table1.getValueAt(filaSeleccionada, 4).toString());
                        estadoCompra.setSelectedItem(table1.getValueAt(filaSeleccionada, 5).toString());
                        fechaCompra.setText(table1.getValueAt(filaSeleccionada, 6).toString());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al seleccionar la fila: " + ex.getMessage());
                    }
                }
            }
        });

        actualizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1) {
                    try {
                        int idOrden = Integer.parseInt(idOrdenCompra.getText().trim());
                        String nuevoEstado = (String) estadoCompra.getSelectedItem();

                        if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "El estado no puede estar vacío.");
                            return;
                        }

                        try (Connection conexion = ConexionBD.getConnection()) {
                            conexion.setAutoCommit(true); // Asegurar auto-commit
                            try (PreparedStatement ps = conexion.prepareStatement(
                                    "UPDATE ordenes_compra SET estado_orden = ? WHERE id_orden_compra = ?")) {

                                ps.setString(1, nuevoEstado);
                                ps.setInt(2, idOrden);

                                int filasActualizadas = ps.executeUpdate();

                                if (filasActualizadas > 0) {
                                    JOptionPane.showMessageDialog(null, "Estado actualizado correctamente.");
                                    cargarDatos(); // Recargar datos desde la base de datos
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontró la orden para actualizar.");
                                }
                            }
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El ID de la orden debe ser un número válido.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al actualizar el estado: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una orden para actualizar el estado.");
                }
            }
        });

        // Listener para volver al menú principal
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
                jFrame.dispose();
                MenuPrincipalGUI.main(null);
            }
        });
    }

    private void cargarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Orden");
        modelo.addColumn("ID Cliente");
        modelo.addColumn("ID Empleado");
        modelo.addColumn("ID Producto");
        modelo.addColumn("Total");
        modelo.addColumn("Estado");
        modelo.addColumn("Fecha Compra");

        try (Connection conexion = ConexionBD.getConnection();
             PreparedStatement ps = conexion.prepareStatement("SELECT * FROM ordenes_compra");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                        rs.getInt("id_orden_compra"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_empleado"),
                        rs.getInt("id_producto"),
                        rs.getInt("total"),
                        rs.getString("estado_orden"),
                        rs.getString("fecha_compra")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        }

        table1.setModel(modelo);
    }

    private void aplicarEstilos() {
        Font fuenteCampos = new Font("Serif", Font.PLAIN, 15);
        Font fuenteBotones = new Font("Serif", Font.BOLD, 15);
        Color colorFondo = new Color(228, 224, 246);
        Color colorTexto = new Color(0, 0, 0);

        main.setBackground(colorFondo);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField[] campos = {idOrdenCompra, idCliente, idEmpleado, idProducto, total, fechaCompra};
        for (JTextField campo : campos) {
            campo.setFont(fuenteCampos);
            campo.setBackground(Color.WHITE);
            campo.setForeground(colorTexto);
            campo.setBorder(BorderFactory.createLineBorder(colorTexto));
            campo.setEditable(false);
        }

        estadoCompra = new JComboBox<>(new String[]{"pendiente", "pagada", "enviada"});
        estadoCompra.setFont(fuenteCampos);
        estadoCompra.setBackground(Color.WHITE);
        estadoCompra.setForeground(colorTexto);

        actualizarCompraButton.setFont(fuenteBotones);
        actualizarCompraButton.setBackground(new Color(0, 123, 255));
        actualizarCompraButton.setForeground(Color.WHITE);

        volverButton.setFont(fuenteBotones);
        volverButton.setBackground(Color.RED);
        volverButton.setForeground(Color.WHITE);

        if (table1 != null) {
            table1.setFont(new Font("Serif", Font.PLAIN, 14));
            table1.setForeground(colorTexto);
            table1.setBackground(Color.WHITE);
            table1.setRowHeight(25);
            table1.getTableHeader().setFont(new Font("Serif", Font.BOLD, 15));
            table1.getTableHeader().setBackground(colorFondo);
            table1.getTableHeader().setForeground(colorTexto);

            Component parent = table1.getParent();
            if (parent instanceof JViewport) {
                Component grandParent = parent.getParent();
                if (grandParent instanceof JScrollPane) {
                    JScrollPane scrollPane = (JScrollPane) grandParent;
                    scrollPane.setBackground(colorFondo);
                    scrollPane.getViewport().setBackground(Color.WHITE);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Órdenes de Compra");
        frame.setContentPane(new OrdenesCompraGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}