// src/main/java/view/VentanaLogin.java
package view;

import viewmodel.ClinicaViewModel;

import javax.swing.*;
import java.awt.*;

public class VentanaLogin extends JFrame {

    public VentanaLogin(ClinicaViewModel viewModel) {
        setTitle("Inicio de Sesión");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel central
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos usuario/contraseña
        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(15);
        JLabel lblContra = new JLabel("Contraseña:");
        JPasswordField txtContra = new JPasswordField(15);

        // Radio buttons para rol
        JRadioButton rbMedico = new JRadioButton("Médico");
        JRadioButton rbAdmin  = new JRadioButton("Administrador");
        ButtonGroup bgRol = new ButtonGroup();
        bgRol.add(rbMedico);
        bgRol.add(rbAdmin);

        // Botón ingresar
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBackground(new Color(0, 120, 215));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);

        // Layout
        gbc.gridy = 0; gbc.gridx = 0; panel.add(lblUsuario, gbc);
        gbc.gridx = 1; panel.add(txtUsuario, gbc);

        gbc.gridy = 1; gbc.gridx = 0; panel.add(lblContra, gbc);
        gbc.gridx = 1; panel.add(txtContra, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1; {
            JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,0));
            rolPanel.setOpaque(false);
            rolPanel.add(rbMedico);
            rolPanel.add(rbAdmin);
            panel.add(rolPanel, gbc);
        }

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        add(panel, BorderLayout.CENTER);

        btnIngresar.addActionListener(e -> {
            String usuario = txtUsuario.getText().trim();
            String contra  = new String(txtContra.getPassword()).trim();

            // Aquí podrías validar credenciales...
            if (usuario.isEmpty() || contra.isEmpty() || (!rbMedico.isSelected() && !rbAdmin.isSelected())) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor complete usuario, contraseña y seleccione un rol.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            this.dispose();
            if (rbMedico.isSelected()) {
                new VentanaMedico(viewModel).setVisible(true);
            } else {
                new VentanaAdmin(viewModel).setVisible(true);
            }
        });
    }
}
