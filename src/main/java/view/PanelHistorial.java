package view;

import viewmodel.ClinicaViewModel;
import model.Consulta;
import excepciones.UsuarioNoEncontradoException;

import javax.swing.*;
import java.util.List;

public class PanelHistorial extends JPanel {

    private ClinicaViewModel viewModel;

    public PanelHistorial(ClinicaViewModel viewModel) {
        this.viewModel = viewModel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTextField txtCedula = new JTextField();
        JTextArea areaHistorial = new JTextArea(10, 30);
        areaHistorial.setEditable(false);
        JButton btnBuscar = new JButton("Buscar Historial");

        add(new JLabel("Cédula del Paciente:"));
        add(txtCedula);
        add(btnBuscar);
        add(new JScrollPane(areaHistorial));

        btnBuscar.addActionListener(e -> {
            try {
                List<Consulta> historial = viewModel.obtenerHistorialPaciente(txtCedula.getText());
                areaHistorial.setText("");
                for (Consulta c : historial) {
                    areaHistorial.append(c.toString() + "\n\n");
                }
            } catch (UsuarioNoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

