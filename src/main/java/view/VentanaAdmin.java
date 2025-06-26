package view;

import viewmodel.ClinicaViewModel;

import javax.swing.*;
import java.awt.*;

public class VentanaAdmin extends JFrame {

    public VentanaAdmin(ClinicaViewModel vm) {
        super("Panel Administrador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabbed pane con Registro y Asignar Consulta
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Registro",        new PanelRegistro(vm));
        tabs.addTab("Asignar Consulta", new PanelConsulta(vm));
        add(tabs, BorderLayout.CENTER);

        // Panel inferior con botón Salir
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalir = new JButton("Salir");
        south.add(btnSalir);
        add(south, BorderLayout.SOUTH);

        btnSalir.addActionListener(e -> {
            // Cerramos esta ventana...
            dispose();
            // ...y volvemos al login
            new VentanaLogin(vm).setVisible(true);
        });
    }
}
