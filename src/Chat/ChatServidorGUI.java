package Chat;

import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class ChatServidorGUI {
    private JPanel panel;
    private JTextArea textArea1;
    private JTextField textField1;
    private JButton iniciarButton;
    private JButton enviarButton;
    private JButton volverButton;

    private PrintWriter out;
    private Socket clientSocket;

    public ChatServidorGUI() {
        inicializarInterfaz();
        configurarEventos();
    }

    private void inicializarInterfaz() {
        panel = new FondoPanelConOpacidad(); // Panel personalizado con fondo
        panel.setLayout(new BorderLayout());

        // Área de texto para mostrar mensajes
        textArea1 = new JTextArea();
        textArea1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        textArea1.setOpaque(false); // fondo transparente
        textArea1.setForeground(Color.BLACK);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea1);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Campo para escribir el mensaje
        textField1 = new JTextField();
        textField1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        textField1.setOpaque(false); // Fondo transparente
        textField1.setForeground(Color.BLACK);
        textField1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Botones
        iniciarButton = crearBoton("Iniciar");
        enviarButton = crearBoton("Enviar");
        volverButton = crearBoton("Volver");

        JPanel abajoPanel = new JPanel(new BorderLayout());
        abajoPanel.setOpaque(false);
        abajoPanel.add(textField1, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        botonesPanel.setOpaque(false);
        botonesPanel.add(iniciarButton);
        botonesPanel.add(enviarButton);
        botonesPanel.add(volverButton);

        abajoPanel.add(botonesPanel, BorderLayout.SOUTH);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(abajoPanel, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        boton.setBackground(new Color(133, 102, 77));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    private void configurarEventos() {
        iniciarButton.addActionListener(e -> new Thread(this::iniciarServidor).start());

        enviarButton.addActionListener(e -> enviarMensaje());

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enviarMensaje();
                }
            }
        });

        volverButton.addActionListener(e -> {
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            if (jFrame != null) {
                jFrame.dispose();
            }
            MenuPrincipalGUI.main(null);
        });
    }

    private void enviarMensaje() {
        String mensaje = textField1.getText().trim();
        if (!mensaje.isEmpty()) {
            if (out != null) {
                out.println(mensaje);
                actualizarTextArea("S. " + mensaje + "\n");
                textField1.setText("");
            } else {
                actualizarTextArea("Error: No hay cliente conectado.\n");
            }
        }
    }

    private void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            actualizarTextArea("Servidor iniciado. Esperando conexión...\n");

            while (true) {
                clientSocket = serverSocket.accept();
                actualizarTextArea("Cliente conectado\n");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                new Thread(() -> recibirMensajes(in)).start();
            }
        } catch (IOException e) {
            actualizarTextArea("Error en el servidor: " + e.getMessage() + "\n");
        }
    }

    private void recibirMensajes(BufferedReader in) {
        try {
            String mensaje;
            while ((mensaje = in.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("salir")) {
                    actualizarTextArea("Cliente ha abandonado el chat\n");
                    break;
                }
                actualizarTextArea("C. " + mensaje + "\n");
            }
        } catch (IOException e) {
            actualizarTextArea("Error al recibir mensajes: " + e.getMessage() + "\n");
        } finally {
            cerrarConexion();
        }
    }

    private void cerrarConexion() {
        try {
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            actualizarTextArea("Cliente desconectado. Esperando nueva conexión...\n");
        } catch (IOException e) {
            actualizarTextArea("Error al cerrar conexión: " + e.getMessage() + "\n");
        }
    }

    private void actualizarTextArea(String mensaje) {
        SwingUtilities.invokeLater(() -> textArea1.append(mensaje));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Servidor de Chat");
        ChatServidorGUI chatServidorGUI = new ChatServidorGUI();
        frame.setContentPane(chatServidorGUI.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    // Clase para fondo con imagen y opacidad
    static class FondoPanelConOpacidad extends JPanel {
        private Image imagenFondo;

        public FondoPanelConOpacidad() {
            try {
                URL imagenURL = getClass().getClassLoader().getResource("imagenes/chatfondo2.jpg");
                if (imagenURL != null) {
                    this.imagenFondo = new ImageIcon(imagenURL).getImage();
                }
            } catch (Exception e) {
                System.out.println("Error cargando fondo: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagenFondo != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Dibuja la imagen con opacidad del 40%
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
                g2d.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }
    }
}
