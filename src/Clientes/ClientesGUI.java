package Clientes;

import Conexion.ConexionBD;
import Empleados.Empleados;
import Empleados.EmpleadosDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientesGUI {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton crearButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JPanel main;
    private JTable table1;
    int filas = 0;

    ClientesDAO clientesDAO = new ClientesDAO();

    public ClientesGUI()
    {
        textField1.setEnabled(false);
        obtenerDatos();

        crearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e){
                String nombre = textField2.getText();
                String telefono = textField3.getText();
                String direccion = textField4.getText();
                String correo = textField5.getText();
                Clientes clientes = new Clientes(0, nombre, telefono, direccion, correo);
               clientesDAO.agregar(clientes);
                obtenerDatos();
                clear();
            }
        });
        actualizarButton.addActionListener(new ActionListener()
        {
            @Override
        public void actionPerformed(ActionEvent e)
            {
            String nombre = textField2.getText();
            String telefono = textField3.getText();
            String direccion = textField4.getText();
            String correo = textField5.getText();
            Clientes clientes = new Clientes(0, nombre, telefono, direccion, correo);
            clientesDAO.agregar(clientes);
            obtenerDatos();
            clear();
            }
        });
        eliminarButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int id_cliente = Integer.parseInt(textField1.getText());
                clientesDAO.eliminar(id_cliente);
                obtenerDatos();
                clear();
            }
        });
        table1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1)
                {
                    textField1.setText(table1.getValueAt(filaSeleccionada, 0).toString());
                    textField2.setText(table1.getValueAt(filaSeleccionada, 1).toString());
                    textField3.setText(table1.getValueAt(filaSeleccionada, 2).toString());
                }
            }
        });
    }
    public void clear()
    {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }

    ConexionBD conexionBD = new ConexionBD();

    public void obtenerDatos()
    {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Cliente");
        model.addColumn("Nombre");
        model.addColumn("Telefono");
        model.addColumn("Direccion");
        model.addColumn("Correo");


        table1.setModel(model);
        String[] dato = new String[4];
        Connection con = conexionBD.getConnection();

        try {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM clientes";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);
                dato[3] = rs.getString(4);

                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Clientes");
        frame.setContentPane(new ClientesGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 700);
        frame.setResizable(false);
    }
}