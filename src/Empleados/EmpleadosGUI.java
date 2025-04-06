package Empleados;

import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Interfaz gráfica para la gestión de empleados.
 * Permite agregar, consultar, actualizar y eliminar empleados.
 */
public class EmpleadosGUI {
    private JTextField textField1, textField2, textField3, buscarField;
    private JComboBox<String> comboBox1, comboBusqueda;
    private JButton agregarButton, consultarButton, actualizarButton, eliminarButton, volverButton;
    private JPanel main, panelBusqueda;
    private JTable table1;
    EmpleadosDAO empleadosDAO = new EmpleadosDAO();

    /**
     * Constructor de la interfaz gráfica de empleados.
     * Inicializa los componentes y configura los eventos.
     */
    public EmpleadosGUI() {
        // Configuración del panel principal
        main = new JPanel(new BorderLayout(10, 10));
        main.setBackground(new Color(230, 230, 250));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(200, 200, 255));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Empleado"));
        formPanel.setBackground(new Color(220, 220, 250));

        formPanel.add(new JLabel("ID:"));
        textField1 = new JTextField();
        textField1.setEnabled(false);
        formPanel.add(textField1);

        formPanel.add(new JLabel("Nombre:"));
        textField2 = new JTextField();
        formPanel.add(textField2);

        formPanel.add(new JLabel("Cargo:"));
        comboBox1 = new JComboBox<>(new String[]{"Gerente", "Cajero", "Vendedor"});
        formPanel.add(comboBox1);

        formPanel.add(new JLabel("Salario:"));
        textField3 = new JTextField();
        formPanel.add(textField3);

        panelBusqueda = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Empleados"));
        panelBusqueda.setBackground(new Color(220, 220, 250));
        panelBusqueda.setVisible(false);

        panelBusqueda.add(new JLabel("Buscar por:"));
        comboBusqueda = new JComboBox<>(new String[]{"ID", "Nombre", "Cargo", "Todos"});
        panelBusqueda.add(comboBusqueda);

        panelBusqueda.add(new JLabel("Valor a buscar:"));
        buscarField = new JTextField();
        panelBusqueda.add(buscarField);

        JPanel formContainer = new JPanel(new BorderLayout(10, 10));
        formContainer.setBackground(new Color(200, 200, 255));
        formContainer.add(formPanel, BorderLayout.NORTH);
        formContainer.add(panelBusqueda, BorderLayout.CENTER);

        topPanel.add(formContainer, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 15, 10));
        buttonPanel.setBackground(new Color(200, 200, 255));

        agregarButton = createStyledButton("Agregar", new Color(0, 150, 136));
        consultarButton = createStyledButton("Consultar", new Color(76, 175, 80));
        actualizarButton = createStyledButton("Actualizar", new Color(33, 150, 243));
        eliminarButton = createStyledButton("Eliminar", new Color(244, 67, 54));
        volverButton = createStyledButton("Volver", new Color(255, 152, 0));

        buttonPanel.add(agregarButton);
        buttonPanel.add(consultarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(volverButton);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        table1 = new JTable();
        table1.setBackground(new Color(255, 250, 205));
        JScrollPane scrollPane = new JScrollPane(table1);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(scrollPane, BorderLayout.CENTER);

        obtenerDatos();
        agregarEventos();
    }

    /**
     * Crea un botón estilizado con el texto y color dados.
     *
     * @param text Texto del botón.
     * @param color Color de fondo del botón.
     * @return JButton con estilo personalizado.
     */
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));
        button.setPreferredSize(new Dimension(90, 35));
        return button;
    }

    /**
     * Agrega los eventos a los botones y tabla.
     */
    private void agregarEventos() {
        agregarButton.addActionListener(e -> {
            String nombre = textField2.getText().trim();
            String cargo = (String) comboBox1.getSelectedItem();
            String salarioStr = textField3.getText().trim();

            if (nombre.isEmpty() || salarioStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                return;
            }

            try {
                int salario = Integer.parseInt(salarioStr);
                Empleados empleado = new Empleados(0, nombre, cargo, salario);
                empleadosDAO.agregar(empleado);
                obtenerDatos();
                clear();
                JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El salario debe ser un número.");
            }
        });

        consultarButton.addActionListener(e -> {
            if (!panelBusqueda.isVisible()) {
                panelBusqueda.setVisible(true);
                main.revalidate();
                main.repaint();
                return;
            }

            String tipoBusqueda = (String) comboBusqueda.getSelectedItem();
            String valor = buscarField.getText().trim();

            if (tipoBusqueda.equals("Todos")) {
                obtenerDatos();
                panelBusqueda.setVisible(false);
                return;
            }

            if (valor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor para buscar.");
                return;
            }

            if (tipoBusqueda.equals("ID")) {
                try {
                    int id = Integer.parseInt(valor);
                    Empleados empleado = empleadosDAO.consultarPorId(id);
                    if (empleado != null) {
                        llenarTabla(List.of(empleado));
                        panelBusqueda.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número.");
                }
            } else {
                String campo = tipoBusqueda.equals("Nombre") ? "nombre" : "cargo";
                List<Empleados> lista = empleadosDAO.buscarPorCampo(campo, valor);
                if (!lista.isEmpty()) {
                    llenarTabla(lista);
                    panelBusqueda.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontraron resultados.");
                }
            }
        });

        actualizarButton.addActionListener(e -> {
            try {
                String nombre = textField2.getText();
                String cargo = (String) comboBox1.getSelectedItem();
                int salario = Integer.parseInt(textField3.getText());
                int id_empleado = Integer.parseInt(textField1.getText());

                Empleados empleados = new Empleados(id_empleado, nombre, cargo, salario);
                empleadosDAO.actualizar(empleados);
                obtenerDatos();
                clear();
                JOptionPane.showMessageDialog(null, "Empleado actualizado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Verifique que el salario y el ID sean números.");
            }
        });

        eliminarButton.addActionListener(e -> {
            try {
                int id_empleado = Integer.parseInt(textField1.getText());
                empleadosDAO.eliminar(id_empleado);
                obtenerDatos();
                clear();
                JOptionPane.showMessageDialog(null, "Empleado eliminado exitosamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Seleccione un empleado válido para eliminar.");
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = table1.getSelectedRow();
                if (filaSeleccionada != -1) {
                    textField1.setText(table1.getValueAt(filaSeleccionada, 0).toString());
                    textField2.setText(table1.getValueAt(filaSeleccionada, 1).toString());
                    comboBox1.setSelectedItem(table1.getValueAt(filaSeleccionada, 2).toString());
                    textField3.setText(table1.getValueAt(filaSeleccionada, 3).toString());
                }
            }
        });

        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
            jFrame.dispose();
            MenuPrincipalGUI.main(null);
        });
    }

    /**
     * Limpia los campos del formulario.
     */
    public void clear() {
        textField1.setText("");
        textField2.setText("");
        comboBox1.setSelectedIndex(0);
        textField3.setText("");
        buscarField.setText("");
    }

    /**
     * Obtiene los empleados desde la base de datos y actualiza la tabla.
     */
    public void obtenerDatos() {
        List<Empleados> lista = empleadosDAO.buscarPorCampo("nombre", "");
        llenarTabla(lista);
    }

    /**
     * Llena la tabla con los datos de los empleados.
     *
     * @param lista Lista de empleados a mostrar.
     */
    private void llenarTabla(List<Empleados> lista) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Empleado");
        model.addColumn("Nombre");
        model.addColumn("Cargo");
        model.addColumn("Salario");

        for (Empleados emp : lista) {
            model.addRow(new Object[]{
                    emp.getId_empleado(),
                    emp.getNombre(),
                    emp.getCargo(),
                    emp.getSalario()
            });
        }

        table1.setModel(model);
    }

    /**
     * Método principal para ejecutar la interfaz de empleados.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Empleados");
        frame.setContentPane(new EmpleadosGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
