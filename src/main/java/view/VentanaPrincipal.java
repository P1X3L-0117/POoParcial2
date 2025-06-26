package view;

import viewmodel.ClinicaViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(ClinicaViewModel viewModel) {
        setTitle("Sistema de Gestión Clínica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel central con estilo
        JPanel panel = new JPanel(new GridBagLayout());
        add(panel, BorderLayout.CENTER);

        // Título
        JLabel titulo = new JLabel("Sistema de Gestión Clínica");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(50, 50, 80));

        // Botones estilizados
        JButton btnRegistro = crearBoton("Registrar Pacientes / Médicos");
        JButton btnConsulta = crearBoton("Asignar Consulta");
        JButton btnHistorial = crearBoton("Historial de Pacientes");

        // Layout con espaciado
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        panel.add(titulo, gbc);
        gbc.gridy = 1;
        panel.add(btnRegistro, gbc);
        gbc.gridy = 2;
        panel.add(btnConsulta, gbc);
        gbc.gridy = 3;
        panel.add(btnHistorial, gbc);

        // Eventos que abren ventanas con los paneles
        btnRegistro.addActionListener(e -> abrirVentana("Registro", new PanelRegistro(viewModel)));
        btnConsulta.addActionListener(e -> abrirVentana("Consulta", new PanelConsulta(viewModel)));
        btnHistorial.addActionListener(e -> abrirVentana("Historial", new PanelHistorial(viewModel)));

        // Guardar datos al cerrar la ventana principal
        //addWindowListener(new WindowAdapter() {
            //@Override
           // public void windowClosing(WindowEvent e) {
           //     viewModel.getClinica().guardarComoTexto("clinica.txt");
             //   System.out.println("Datos guardados correctamente al cerrar la aplicación.");
           // }
        //});
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(60, 120, 200));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    private void abrirVentana(String titulo, JPanel panelContenido) {
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panelContenido);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
