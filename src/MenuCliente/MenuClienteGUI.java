package MenuCliente;

import Chat.ChatClienteGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;

public class MenuClienteGUI {
    private JPanel main;
    private JButton chatButton;
    private JButton comprarButton;

    public MenuClienteGUI() {
        main = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(600, 0, 0, 0); // Ajusta la posición vertical

        JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        botonesPanel.setOpaque(false);

        chatButton = crearBoton("Chat");
        comprarButton = crearBoton("Comprar");

        botonesPanel.add(chatButton);
        botonesPanel.add(comprarButton);

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
        chatButton.addActionListener((ActionEvent e) -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(main);
            if (jFrame != null) {
                jFrame.dispose();
            }
            ChatClienteGUI.main(null); // Abre el chat
        });

        comprarButton.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null, "Funcionalidad de compra aún no implementada.");
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menú Cliente");
        FondoPanel fondoPanel = new FondoPanel();
        JPanel mainPanel = new MenuClienteGUI().main;
        mainPanel.setOpaque(false);
        fondoPanel.setLayout(new BorderLayout());
        fondoPanel.add(mainPanel, BorderLayout.CENTER);

        URL iconoURL = MenuClienteGUI.class.getClassLoader().getResource("imagenes/logo.png");
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
            URL imagenURL = getClass().getClassLoader().getResource("imagenes/fondo1.png");
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
