package MenuPrincipal;

import Chat.ChatServidorGUI;
import Clientes.ClientesGUI;
import Empleados.EmpleadosGUI;
import Inventario.InventarioGUI;
import OrdenesCompra.OrdenesCompraGUI;
import Proveedores.ProveedoresGUI;
import RegistroVentas.RegistroVentasGUI;
import Reportes.ReportesGUI;
// import Registro_ventas.Registro_ventasGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MenuPrincipalGUI {
    private JPanel main;
    private JButton clientesButton;
    private JButton empleadosButton;
    private JButton proveedoresButton;
    private JButton inventarioButton;
    private JButton ordenesButton;
    private JButton registrosButton;
    private JButton chatButton;
    private JButton salirButton;
    private JButton reportesButton;

    public MenuPrincipalGUI() {
        main = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(100, 0, 0, 0); // margen superior

        JPanel botonesPanel = new JPanel(new GridLayout(3, 3, 20, 20));
        botonesPanel.setOpaque(false);

        clientesButton = crearBoton("Clientes");
        empleadosButton = crearBoton("Empleados");
        proveedoresButton = crearBoton("Proveedores");
        inventarioButton = crearBoton("Inventario");
        ordenesButton = crearBoton("Órdenes de Compra");
        registrosButton = crearBoton("Registro de Ventas");
        chatButton = crearBoton("Chat");
        salirButton = crearBoton("Salir");
        reportesButton = crearBoton("Reportes");

        // Fila 1
        botonesPanel.add(clientesButton);
        botonesPanel.add(empleadosButton);
        botonesPanel.add(proveedoresButton);

        // Fila 2
        botonesPanel.add(inventarioButton);
        botonesPanel.add(ordenesButton);
        botonesPanel.add(registrosButton);

        // Fila 3: espacio - Chat - espacio
        botonesPanel.add(chatButton);
        botonesPanel.add(reportesButton); // Reportes
        botonesPanel.add(salirButton);

        main.add(botonesPanel, gbc);
        configurarAcciones();

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        boton.setBackground(new Color(255, 182, 75));
        boton.setForeground(Color.BLACK);
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
        ordenesButton.addActionListener(e -> abrirVentana(OrdenesCompraGUI.class));
        registrosButton.addActionListener(e -> abrirVentana(RegistroVentasGUI.class));
        reportesButton.addActionListener(e -> abrirVentana(ReportesGUI.class));

        chatButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(main);
            if (jFrame != null) {
                jFrame.dispose();
            }
            ChatServidorGUI.main(null); // Abre el chat del SERVIDOR
        });
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
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/fondo.png");
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
