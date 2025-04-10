package MenuPrincipal;

import Clientes.ClientesGUI;
import Empleados.EmpleadosGUI;
import Inventario.InventarioGUI;
import Ordenes_compra.Ordenes_compraGUI;
import Proveedores.ProveedoresGUI;
//import Registro_ventas.Registro_ventasGUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuPrincipalGUI {
    private JPanel main;
    private JButton clientesButton;
    private JButton empleadosButton;
    private JButton proveedoresButton;
    private JButton inventarioButton;
    private JButton ordenesButton;
    private JButton registrosButton;

    public MenuPrincipalGUI() {
        main = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1; // Posiciona los botones en el centro vertical
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(600, 0, 0, 0); // Ajuste para bajar los botones

        JPanel botonesPanel = new JPanel(new GridLayout(1, 6, 20, 10));
        botonesPanel.setOpaque(false);

        clientesButton = crearBoton("Clientes");
        empleadosButton = crearBoton("Empleados");
        proveedoresButton = crearBoton("Proveedores");
        inventarioButton = crearBoton("Inventario");
        ordenesButton = crearBoton("Órdenes de Compra");
        registrosButton = crearBoton("Registro de Ventas");

        botonesPanel.add(clientesButton);
        botonesPanel.add(empleadosButton);
        botonesPanel.add(proveedoresButton);
        botonesPanel.add(inventarioButton);
        botonesPanel.add(ordenesButton);
        botonesPanel.add(registrosButton);

        main.add(botonesPanel, gbc);
        configurarAcciones();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        boton.setBackground(new Color(133, 102, 77));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void configurarAcciones() {
        clientesButton.addActionListener(e -> abrirVentana(ClientesGUI.class));
        empleadosButton.addActionListener(e -> abrirVentana(EmpleadosGUI.class));
        proveedoresButton.addActionListener(e -> abrirVentana(ProveedoresGUI.class));
        inventarioButton.addActionListener(e -> abrirVentana(InventarioGUI.class));
        ordenesButton.addActionListener(e -> abrirVentana(Ordenes_compraGUI.class));
//        registrosButton.addActionListener(e -> abrirVentana(Registro_ventasGUI.class));
    }

    private void abrirVentana(Class<?> clase) {
        JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(main);
        if (jFrame != null) {
            jFrame.dispose();
        }
        try {
            clase.getMethod("main", String[].class).invoke(null, (Object) new String[]{});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú Principal");
        FondoPanel fondoPanel = new FondoPanel();
        JPanel mainPanel = new MenuPrincipalGUI().main;
        mainPanel.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(mainPanel, BorderLayout.CENTER);

        URL iconoURL = MenuPrincipalGUI.class.getClassLoader().getResource("imagenes/logo.png");
        if (iconoURL != null) {
            ImageIcon icono = new ImageIcon(iconoURL);
            Image imagenEscalada = icono.getImage().getScaledInstance(228, 228, Image.SCALE_SMOOTH);
            frame.setIconImage(imagenEscalada);
        }

        frame.setContentPane(fondoPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
    }

    static class FondoPanel extends JPanel {
        private Image imagenFondo;

        public FondoPanel() {
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/fondo2.png");
            if (imagenURL != null) {
                this.imagenFondo = new ImageIcon(imagenURL).getImage();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            if (imagenFondo != null) {
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            } else {
                GradientPaint gp = new GradientPaint(0, 0, new Color(51, 153, 255), getWidth(), getHeight(), new Color(0, 51, 102));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}
