package Inventario_productos;

import MenuPrincipal.MenuPrincipalGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inventario_productosGUI {
    private JPanel main;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton volverButton;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;

    public Inventario_productosGUI() {
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
                jFrame.dispose();
                MenuPrincipalGUI.main(null);
            }
        });
    }
}
