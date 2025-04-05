package Inventario_productos;

import MenuPrincipal.MenuPrincipalGUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Inventario_productosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1; // id_producto
    private JTextField textField2; // nombre
    private JComboBox<String> comboBox1; // categoría
    private JTextField textField3; // cantidad
    private JTextField textField4; // precio
    private JTextField textField5; // id_proveedor_asociado
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton volverButton;

    private Inventario_productosDAO productosDAO;

    public Inventario_productosGUI() {
        productosDAO = new Inventario_productosDAO();

        textField1.setEditable(false);
        textField5.setEditable(false);

        cargarCategorias();
        cargarProductos();

        agregarButton.addActionListener(e -> agregarProducto());
        actualizarButton.addActionListener(e -> actualizarProducto());
        eliminarButton.addActionListener(e -> eliminarProducto());

        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });

        table1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table1.getSelectedRow() != -1) {
                int fila = table1.getSelectedRow();
                textField1.setText(table1.getValueAt(fila, 0).toString());
                textField2.setText(table1.getValueAt(fila, 1).toString());
                comboBox1.setSelectedItem(table1.getValueAt(fila, 2).toString());
                textField3.setText(table1.getValueAt(fila, 3).toString());
                textField4.setText(table1.getValueAt(fila, 4).toString());
                Object idProveedor = table1.getValueAt(fila, 5);
                textField5.setText(idProveedor != null ? idProveedor.toString() : "");
            }
        });
    }

    private void cargarCategorias() {
        comboBox1.removeAllItems();
        comboBox1.addItem("Herramientas");
        comboBox1.addItem("Materiales");
        comboBox1.addItem("Construcción");
        comboBox1.addItem("Eléctricos");
    }

    private void agregarProducto() {
        try {
            String nombre = textField2.getText();
            String categoria = (String) comboBox1.getSelectedItem();
            int cantidad = Integer.parseInt(textField3.getText());
            int precio = Integer.parseInt(textField4.getText());
            //Integer proveedor = textField5.getText().isEmpty() ? null : Integer.parseInt(textField5.getText());

            Inventario_productos producto = new Inventario_productos(
                    0, nombre, categoria, precio, cantidad, 0
            );

            productosDAO.agregar(producto);
            cargarProductos();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Revisa que cantidad y precio sean numéricos.");
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos en Cantidad y Precio.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el producto.");
        }
    }

    private void cargarProductos() {
        try {
            List<Inventario_productos> productos = productosDAO.obtenerProductos();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id_producto");
            model.addColumn("nombre_producto");
            model.addColumn("Categoría");
            model.addColumn("cantidad_stock");
            model.addColumn("precio_producto");

            for (Inventario_productos p : productos) {
                model.addRow(new Object[]{
                        p.getId_producto(),
                        p.getNombre_producto(),
                        p.getCategoria(),
                        p.getCantidad_stock(),
                        p.getPrecio_producto()
                });
            }
            table1.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void actualizarProducto() {
        int fila = table1.getSelectedRow();
        if (fila != -1) {
            try {
                int id = Integer.parseInt(textField1.getText());
                String nombre = textField2.getText();
                String categoria = (String) comboBox1.getSelectedItem();
                int cantidad = Integer.parseInt(textField3.getText());
                int precio = Integer.parseInt(textField4.getText());
                Integer proveedor = textField5.getText().isEmpty() ? null : Integer.parseInt(textField5.getText());

                Inventario_productos productos = new Inventario_productos(
                        id, nombre, categoria, precio, cantidad, proveedor
                );

                productosDAO.actualizar(productos);
                cargarProductos();
                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Revisa los valores ingresados.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto.");
        }
    }

    private void eliminarProducto() {
        int fila = table1.getSelectedRow();
        if (fila != -1) {
            int id = Integer.parseInt(textField1.getText());
            productosDAO.eliminar(id);
            cargarProductos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar.");
        }
    }

    private void cargarProductos() {
        try {
            List<Inventario_productos> lista = productosDAO.obtenerProductos();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new String[]{
                    "ID", "Nombre", "Categoría", "Cantidad", "Precio", "ID Proveedor"
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

    private void limpiarCampos() {
        textField1.setText("");
        textField2.setText("");
        comboBox1.setSelectedIndex(0);
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }

    public JPanel getMainPanel() {
        return main;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Inventario");
        frame.setContentPane(new Inventario_productosGUI().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }
}
