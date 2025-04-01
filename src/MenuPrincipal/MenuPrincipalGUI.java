package MenuPrincipal;

import Clientes.ClientesGUI;
import Empleados.EmpleadosGUI;
import Inventario_productos.Inventario_productosGUI;
import Ordenes_compra.Ordenes_compraGUI;
import Proveedores.ProveedoresGUI;
import Registro_ventas.Registro_ventasGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalGUI {
    private JPanel main;
    private JButton clientesButton;
    private JButton empleadosButton;
    private JButton proveedoresButton;
    private JButton inventarioButton;
    private JButton ordenesButton;
    private JButton registrosButton;

    public MenuPrincipalGUI() {
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(clientesButton);
                jFrame.dispose();
                ClientesGUI.main(null);
            }
        });
        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(empleadosButton);
                jFrame.dispose();
                EmpleadosGUI.main(null);
            }
        });

        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(proveedoresButton);
                jFrame.dispose();
                ProveedoresGUI.main(null);
            }
        });

//        inventarioButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(inventarioButton);
//                jFrame.dispose();
//                Inventario_productosGUI.main(null);
//            }
//        });
//        ordenesButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(ordenesButton);
//                jFrame.dispose();
//                Ordenes_compraGUI.main(null);
//            }
//        });
//        registrosButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(registrosButton);
//                jFrame.dispose();
//                Registro_ventasGUI.main(null);
//            }
//        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu principal");
        frame.setContentPane(new MenuPrincipalGUI().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
    }
}
