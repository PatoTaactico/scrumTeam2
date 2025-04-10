package Inventario;

import Conexion.ConexionBD;
import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InventarioGUI {
    private JPanel main;
    private JTable table1;
    private JTextField id;
    private JTextField nombre;
    private JTextField categoria;
    private JTextField precio;
    private JTextField cantidad_stock;
    private JComboBox<Integer> id_proveedor;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton volverButton;

    InventarioDAO inventarioDAO = new InventarioDAO();
    ConexionBD conexionBD = new ConexionBD();

    public InventarioGUI() {
        main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(new Color(230, 230, 250));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(200, 200, 255));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));
        formPanel.setBackground(new Color(220, 220, 250));

        formPanel.add(new JLabel("ID:"));
        id = new JTextField();
        id.setEnabled(false);
        formPanel.add(id);

        formPanel.add(new JLabel("Nombre:"));
        nombre = new JTextField();
        formPanel.add(nombre);

        formPanel.add(new JLabel("Categoría:"));
        categoria = new JTextField();
        categoria.setEnabled(false);
        formPanel.add(categoria);

        formPanel.add(new JLabel("Precio:"));
        precio = new JTextField();
        formPanel.add(precio);

        formPanel.add(new JLabel("Stock:"));
        cantidad_stock = new JTextField();
        formPanel.add(cantidad_stock);

        formPanel.add(new JLabel("ID Proveedor:"));
        id_proveedor = new JComboBox<>();
        formPanel.add(id_proveedor);

        topPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(200, 200, 255));

        agregarButton = createStyledButton("Agregar", new Color(76, 175, 80));
        actualizarButton = createStyledButton("Actualizar", new Color(33, 150, 243));
        eliminarButton = createStyledButton("Eliminar", new Color(244, 67, 54));
        volverButton = createStyledButton("Volver", new Color(255, 152, 0));

        buttonPanel.add(agregarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(volverButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        table1 = new JTable();
        table1.setBackground(new Color(255, 250, 205));
        JScrollPane scrollPane = new JScrollPane(table1);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(scrollPane, BorderLayout.CENTER);

        obtener_datos();
        cargarIdsProveedores();
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
        id_proveedor.addActionListener(e -> {
            Integer selectedId = (Integer) id_proveedor.getSelectedItem();
            if (selectedId != null) {
                try (Connection con = conexionBD.getConnection()) {
                    String sql = "SELECT categoria_producto FROM proveedores WHERE id_proveedor = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setInt(1, selectedId);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        categoria.setText(rs.getString("categoria_producto"));
                    } else {
                        categoria.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al obtener la categoría del proveedor.");
                }
            }
        });

        agregarButton.addActionListener(e -> {
            String nombreProducto = nombre.getText();
            String categoriaProducto = categoria.getText();
            int precioProducto = Integer.parseInt(precio.getText());
            int cantidadStock = Integer.parseInt(cantidad_stock.getText());
            Integer idProveedor = (Integer) id_proveedor.getSelectedItem();

            Inventario inventario = new Inventario(0, nombreProducto, categoriaProducto, cantidadStock, precioProducto, idProveedor);
            inventarioDAO.agregar(inventario);
            obtener_datos();
            clear();
        });

        actualizarButton.addActionListener(e -> {
            String nombreProducto = nombre.getText();
            String categoriaProducto = categoria.getText();
            int precioProducto = Integer.parseInt(precio.getText());
            int cantidadStock = Integer.parseInt(cantidad_stock.getText());
            int idProducto = Integer.parseInt(id.getText());
            Integer idProveedor = (Integer) id_proveedor.getSelectedItem();

            Inventario inventario = new Inventario(idProducto, nombreProducto, categoriaProducto, cantidadStock, precioProducto, idProveedor);
            inventarioDAO.actualizar(inventario);
            obtener_datos();
            clear();
        });

        eliminarButton.addActionListener(e -> {
            int idProducto = Integer.parseInt(id.getText());
            inventarioDAO.eliminar(idProducto);
            obtener_datos();
            clear();
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = table1.getSelectedRow();
                id.setText(table1.getValueAt(fila, 0).toString());
                nombre.setText(table1.getValueAt(fila, 1).toString());
                categoria.setText(table1.getValueAt(fila, 2).toString());
                cantidad_stock.setText(table1.getValueAt(fila, 3).toString());
                precio.setText(table1.getValueAt(fila, 4).toString());
                id_proveedor.setSelectedItem(table1.getValueAt(fila, 5));
            }
        });

        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });
    }

    public void cargarIdsProveedores() {
        try (Connection con = conexionBD.getConnection()) {
            String sql = "SELECT id_proveedor FROM proveedores";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            id_proveedor.removeAllItems();
            while (rs.next()) {
                id_proveedor.addItem(rs.getInt("id_proveedor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores.");
        }
    }

    public void clear() {
        id.setText("");
        nombre.setText("");
        categoria.setText("");
        precio.setText("");
        cantidad_stock.setText("");
        id_proveedor.setSelectedIndex(-1);
    }

    public void obtener_datos() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id_producto");
        model.addColumn("nombre_producto");
        model.addColumn("categoria");
        model.addColumn("cantidad_stock");
        model.addColumn("precio_producto");
        model.addColumn("id_proveedor_asociado");

        table1.setModel(model);
        try (Connection con = conexionBD.getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM inventario_productos");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getString("categoria"),
                        rs.getInt("cantidad_stock"),
                        rs.getInt("precio_producto"),
                        rs.getObject("id_proveedor_asociado")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Inventario");
        frame.setContentPane(new InventarioGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}