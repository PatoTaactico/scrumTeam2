package Ordenes_compra;

import Conexion.ConexionBD;
import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class Ordenes_compraGUI extends JFrame {
    private JPanel main;
    private JTable table1;
    private JTextField textField1; // id_orden
    private JTextField textField2; // id_cliente
    private JTextField textField3; // id_empleado
    private JTextField textField4; // fecha
    private JTextField textField5; // total
    private JTextField textField6; // estado
    private JComboBox<String> comboBox1;
    private JButton volverButton;

    private Ordenes_compraDAO dao;

    public Ordenes_compraGUI() {

        textField1.setEnabled(false);
        textField2.setEnabled(false);
        textField3.setEnabled(false);
        textField4.setEnabled(false);
        textField5.setEnabled(false);

        setContentPane(main);
        setTitle("Gestión de Órdenes de Compra");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        try {
            // Obtener conexión y pasarla al DAO
            ConexionBD conexionBD = new ConexionBD();
            Connection connection = conexionBD.getConnection();
            dao = new Ordenes_compraDAO(connection);

            // Cargar tabla
            cargarTabla();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
                jFrame.dispose();
                MenuPrincipalGUI.main(null);
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = table1.getSelectedRow();
                if (fila >= 0) {
                    textField1.setText(table1.getValueAt(fila, 0).toString());
                    textField2.setText(table1.getValueAt(fila, 1).toString());
                    textField3.setText(table1.getValueAt(fila, 2).toString());
                    textField4.setText(table1.getValueAt(fila, 3).toString());
                    textField5.setText(table1.getValueAt(fila, 4).toString());
                    textField6.setText(table1.getValueAt(fila, 5).toString());
                }
            }
        });


    }

    private void cargarTabla() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"id_producto", "id_cliente", "id_empleado", "fecha_compra", "total", "estado_orden"}, 0);
        for (Ordenes_compra o : dao.obtenerOrdenes()) {
            model.addRow(new Object[]{
                    o.getid_producto(),
                    o.getid_cliente(),
                    o.getid_empleado(),
                    o.getfecha_compra(),
                    o.getTotal(),
                    o.getestado_orden()
            });
        }
        table1.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ordenes_compraGUI().setVisible(true));
    }
}
