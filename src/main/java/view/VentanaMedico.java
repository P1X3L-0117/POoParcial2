package view;

import viewmodel.ClinicaViewModel;

import javax.swing.*;
import java.awt.*;

public class VentanaMedico extends JFrame {

    public VentanaMedico(ClinicaViewModel vm) {
        super("Panel Médico");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabbed pane con Historial y Nueva Consulta
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Historial",    new PanelHistorial(vm));
        tabs.addTab("Nueva Consulta", new PanelConsulta(vm));
        add(tabs, BorderLayout.CENTER);

        // Panel inferior con botón Salir
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalir = new JButton("Salir");
        south.add(btnSalir);
        add(south, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> {
            // Cerrar ventana
            dispose();
            // volvemos al login
            new VentanaLogin(vm).setVisible(true);
        });
    }
}
