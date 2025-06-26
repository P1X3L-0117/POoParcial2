package view;

import viewmodel.ClinicaViewModel;
import model.Medico;
import model.Paciente;
import excepciones.*;

import javax.swing.*;
import java.util.List;

public class PanelConsulta extends JPanel {

    private ClinicaViewModel viewModel;

    public PanelConsulta(ClinicaViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JComboBox<String> cmbPaciente = new JComboBox<>();
        JComboBox<String> cmbMedico = new JComboBox<>();
        JTextField txtSintomas = new JTextField();
        JTextField txtDiagnostico = new JTextField();
        JTextField txtTratamiento = new JTextField();
        JButton btnRegistrar = new JButton("Registrar Consulta");

        // Cargar datos en los comboBox
        List<Paciente> pacientes = viewModel.getPacientes();
        for (Paciente p : pacientes) {
            cmbPaciente.addItem(p.getCedula());
        }

        List<Medico> medicos = viewModel.getMedicos();
        for (Medico m : medicos) {
            cmbMedico.addItem(m.getCedula());
        }

        add(new JLabel("Paciente (Cédula):"));
        add(cmbPaciente);
        add(new JLabel("Médico (Cédula):"));
        add(cmbMedico);
        add(new JLabel("Síntomas:"));
        add(txtSintomas);
        add(new JLabel("Diagnóstico:"));
        add(txtDiagnostico);
        add(new JLabel("Tratamiento:"));
        add(txtTratamiento);
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            try {
                viewModel.registrarConsulta(
                        (String) cmbPaciente.getSelectedItem(),
                        (String) cmbMedico.getSelectedItem(),
                        txtSintomas.getText(),
                        txtDiagnostico.getText(),
                        txtTratamiento.getText()
                );
                JOptionPane.showMessageDialog(this, "Consulta registrada exitosamente.");
            } catch (CampoVacioException | UsuarioNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
