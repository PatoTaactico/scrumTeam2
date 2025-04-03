package Inventario_productos;

import MenuPrincipal.MenuPrincipalGUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Inventario_productosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton volverButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton crearButton;
    private Inventario_productosDAO productosDAO;

    public Inventario_productosGUI() {
        Connection conexion = conectarBaseDatos();
        if (conexion != null) {
            productosDAO = new Inventario_productosDAO(conexion);
            cargarProductos();
        }

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
                jFrame.dispose();
                MenuPrincipalGUI.main(null);
            }
        });

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    private Connection conectarBaseDatos() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ferreteria?useSSL=false&serverTimezone=UTC",
                    "usuario",
                    ""
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void agregarProducto() {
        try {
            if (textField1.getText().isEmpty() || textField2.getText().isEmpty()
                    || textField3.getText().isEmpty() || textField4.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                return;
            }

            String nombre_producto = textField1.getText();
            String categoria = textField2.getText();
            int cantidad_stock = Integer.parseInt(textField3.getText());
            int precio_producto = Integer.parseInt(textField4.getText());

            Inventario_productos producto = new Inventario_productos(0, nombre_producto, categoria, precio_producto, cantidad_stock, 0);
            productosDAO.agregarProducto(producto);
            cargarProductos();

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
                        p.getId(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getCantidad(),
                        p.getPrecio()
                });
            }
            table1.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) table1.getValueAt(selectedRow, 0);
                String nombre_producto = textField1.getText();
                String categoria = textField2.getText();
                int cantidad_stock = Integer.parseInt(textField3.getText());
                int precio_producto = Integer.parseInt(textField4.getText());


                Inventario_productos producto = new Inventario_productos(0,  nombre_producto, categoria, precio_producto, cantidad_stock,0);
                productosDAO.actualizarProducto(producto);
                cargarProductos();
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos en Cantidad y Precio.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para actualizar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) table1.getValueAt(selectedRow, 0);
                productosDAO.eliminarProducto(id);
                cargarProductos();
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Inventario de Productos");
        frame.setContentPane(new Inventario_productosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}