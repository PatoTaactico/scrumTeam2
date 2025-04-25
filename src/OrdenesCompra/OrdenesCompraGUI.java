package OrdenesCompra;

import Conexion.ConexionBD;
import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenesCompraGUI {
    private JTable table1;
    private JTextField idOrdenCompra;
    private JTextField idCliente;
    private JTextField idEmpleado;
    private JTextField idProducto;
    private JTextField total;
    private JComboBox<String> estadoCompra;
    private JTextField fechaCompra;
    private JButton volverButton;
    private JPanel main;

    public OrdenesCompraGUI() {
        aplicarEstilos();
        cargarDatos();

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
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ordenes_compra")) {

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Órdenes de Compra");
        frame.setContentPane(new OrdenesCompraGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void aplicarEstilos() {
        Font fuenteCampos = new Font("Serif", Font.PLAIN, 15);
        Font fuenteBotones = new Font("Serif", Font.BOLD, 15);
        Color colorFondo = new Color(228, 224, 246); // Beige claro
        Color colorTexto = new Color(0, 0, 0);    // Negro

        main.setBackground(colorFondo);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField[] campos = { idOrdenCompra, idCliente, idEmpleado, idProducto, total, fechaCompra };
        for (JTextField campo : campos) {
            campo.setFont(fuenteCampos);
            campo.setBackground(Color.WHITE);
            campo.setForeground(colorTexto);
            campo.setBorder(BorderFactory.createLineBorder(colorTexto));
            campo.setEditable(false); // Campos no editables
        }

        estadoCompra = new JComboBox<>(new String[]{"Pendiente", "Pagada", "Enviada"});
        estadoCompra.setFont(fuenteCampos);
        estadoCompra.setBackground(Color.WHITE);
        estadoCompra.setForeground(colorTexto);

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
}