package Inventario_productos;

import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Inventario_productosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton actualizarButton;
    private JButton crearButton;
    private JButton eliminarButton;
    private JButton volverButton;
    private JTextField textField6; // ID producto
    private Inventario_productosDAO productosDAO;

    public Inventario_productosGUI() {
        productosDAO = new Inventario_productosDAO();
        cargarProductos();

        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });

        crearButton.addActionListener(e -> agregarProducto());
        actualizarButton.addActionListener(e -> actualizarProducto());
        eliminarButton.addActionListener(e -> eliminarProducto());

        // Cargar datos en los campos al seleccionar una fila
        table1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                int fila = table1.getSelectedRow();
                textField6.setText(table1.getValueAt(fila, 0).toString());
                textField1.setText(table1.getValueAt(fila, 1).toString());
                textField2.setText(table1.getValueAt(fila, 2).toString());
                textField3.setText(table1.getValueAt(fila, 3).toString());
                textField4.setText(table1.getValueAt(fila, 4).toString());
                Object idProveedor = table1.getValueAt(fila, 5);
                textField5.setText(idProveedor != null ? idProveedor.toString() : "");
            }
        });
    }

    private void agregarProducto() {
        try {
            String nombre_producto = textField1.getText();
            String categoria = textField2.getText();
            int cantidad_stock = Integer.parseInt(textField3.getText());
            int precio_producto = Integer.parseInt(textField4.getText());
            Integer id_Proveedor_asociado = null;

            if (!textField5.getText().isEmpty()) {
                id_Proveedor_asociado = Integer.parseInt(textField5.getText());
            }

            // 👇 ID en 0 porque la base de datos lo genera
            Inventario_productos producto = new Inventario_productos(
                    0, nombre_producto, categoria, precio_producto, cantidad_stock, id_Proveedor_asociado
            );

            productosDAO.agregar(producto);
            cargarProductos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Verifica que los campos de cantidad, precio y proveedor sean numéricos.");
        }
    }


    private void actualizarProducto() {
        int fila = table1.getSelectedRow();
        if (fila != -1) {
            try {
                // Obtener el ID del producto desde la tabla (columna 0)
                int id_producto = (int) table1.getValueAt(fila, 0);

                // Obtener los valores de los campos de texto
                String nombre_producto = textField1.getText();
                String categoria = textField2.getText();
                int cantidad_stock = Integer.parseInt(textField3.getText());
                int precio_producto = Integer.parseInt(textField4.getText());

                // Obtener el ID del proveedor si está presente
                Integer id_Proveedor_asociado = null;
                if (!textField5.getText().trim().isEmpty()) {
                    id_Proveedor_asociado = Integer.parseInt(textField5.getText().trim());
                }

                // Crear el objeto producto actualizado
                Inventario_productos producto = new Inventario_productos(
                        id_producto,
                        nombre_producto,
                        categoria,
                        precio_producto,
                        cantidad_stock,
                        id_Proveedor_asociado
                );

                // Llamar al DAO para actualizar
                productosDAO.actualizar(producto);
                cargarProductos(); // Refrescar tabla

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Verifica los campos numéricos.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para actualizar.");
        }
    }


    private void eliminarProducto() {
        int fila = table1.getSelectedRow();
        if (fila != -1) {
            int id = (int) table1.getValueAt(fila, 0);
            productosDAO.eliminar(id);
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar.");
        }
    }

    private void cargarProductos() {
        try {
            List<Inventario_productos> lista = productosDAO.obtenerProductos();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new String[]{
                    "ID", "Nombre", "Categoría", "cantidad_stock", "Precio_producto", "id_Proveedor_asociado"
            });

            for (Inventario_productos p : lista) {
                modelo.addRow(new Object[]{
                        p.getId_producto(), p.getNombre_producto(), p.getCategoria(),
                        p.getCantidad_stock(), p.getPrecio_producto(), p.getId_Proveedor_asociado()
                });
            }
            table1.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage());
        }
    }

    public JPanel getMainPanel() {
        return main;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Inventario de Productos");
        frame.setContentPane(new Inventario_productosGUI().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
}

