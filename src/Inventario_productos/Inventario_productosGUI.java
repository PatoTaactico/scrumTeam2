package Inventario_productos;


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
    private JTextField textFieldNombre;
    private JTextField textFieldCategoria;
    private JTextField textFieldPrecio;
    private JTextField textFieldCantidad;
    private JButton crearButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private Inventario_productosDAO productosDAO;

    public Inventario_productosGUI() {
        Connection conexion = conectarBaseDatos();
        if (conexion != null) {
            productosDAO = new Inventario_productosDAO(conexion);
            cargarProductos();
        }

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
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/ferreteria", "usuario", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cargarProductos() {
        try {
            List<Inventario_productos> productos = productosDAO.obtenerProductos();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nombre");
            model.addColumn("Categoría");
            model.addColumn("Precio");
            model.addColumn("Cantidad");

            for (Inventario_productos p : productos) {
                model.addRow(new Object[]{p.getId(), p.getNombre(), p.getCategoria(), p.getPrecio(), p.getCantidad()});
            }
            table1.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarProducto() {
        String nombre = textFieldNombre.getText();
        String categoria = textFieldCategoria.getText();
        int precio = Integer.parseInt(textFieldPrecio.getText());
        int cantidad = Integer.parseInt(textFieldCantidad.getText());

        Inventario_productos producto = new Inventario_productos(0, nombre, categoria, precio, cantidad, 1);
        try {
            productosDAO.agregarProducto(producto);
            cargarProductos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actualizarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table1.getValueAt(selectedRow, 0);
            String nombre = textFieldNombre.getText();
            String categoria = textFieldCategoria.getText();
            int precio = Integer.parseInt(textFieldPrecio.getText());
            int cantidad = Integer.parseInt(textFieldCantidad.getText());

            Inventario_productos producto = new Inventario_productos(id, nombre, categoria, precio, cantidad, 1);
            try {
                productosDAO.actualizarProducto(producto);
                cargarProductos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void eliminarProducto() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) table1.getValueAt(selectedRow, 0);
            try {
                productosDAO.eliminarProducto(id);
                cargarProductos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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