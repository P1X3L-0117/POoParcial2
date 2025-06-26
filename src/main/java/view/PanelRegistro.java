package view;

import viewmodel.ClinicaViewModel;
import excepciones.CampoVacioException;

import javax.swing.*;

public class PanelRegistro extends JPanel {

    private ClinicaViewModel viewModel;

    public PanelRegistro(ClinicaViewModel viewModel) {
        this.viewModel = viewModel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField txtNombre = new JTextField();
        JTextField txtCedula = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtEspecialidad = new JTextField();
        String[] tipos = {"Paciente", "Medico"};
        JComboBox<String> tipoPersona = new JComboBox<>(tipos);

        JButton btnRegistrar = new JButton("Registrar");

        add(new JLabel("Tipo de persona:"));
        add(tipoPersona);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Cédula:"));
        add(txtCedula);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Especialidad (solo médicos):"));
        add(txtEspecialidad);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText();
                String cedula = txtCedula.getText();
                String telefono = txtTelefono.getText();
                String especialidad = txtEspecialidad.getText();
                String tipo = (String) tipoPersona.getSelectedItem();

                if ("Paciente".equals(tipo)) {
                    viewModel.registrarPaciente(nombre, cedula, telefono);
                } else {
                    viewModel.registrarMedico(nombre, cedula, telefono, especialidad);
                }

                JOptionPane.showMessageDialog(this, "Registro exitoso.");
            } catch (CampoVacioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
