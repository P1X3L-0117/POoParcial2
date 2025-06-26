package viewmodel;

import excepciones.*;
import model.*;

import java.io.IOException;
import java.util.List;

public class ClinicaViewModel {

    private Clinica clinica;
    private static final String ARCHIVO_TXT = "clinica.txt";  // ruta centralizada

    public ClinicaViewModel() {
        this.clinica = new Clinica();
        clinica.cargarDesdeTexto(ARCHIVO_TXT);  // debes haber implementado este método en Clinica
    }

    public ClinicaViewModel(Clinica clinica) {
        this.clinica = clinica;
    }

    public void registrarPaciente(String nombre, String cedula, String telefono) throws CampoVacioException {
        if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty()) {
            throw new CampoVacioException("Todos los campos son obligatorios.");
        }
        Paciente paciente = new Paciente(nombre, cedula, telefono);
        clinica.agregarPaciente(paciente);
        guardar();  // 🔽
    }

    public void registrarMedico(String nombre, String cedula, String telefono, String especialidad) throws CampoVacioException {
        if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()) {
            throw new CampoVacioException("Todos los campos son obligatorios para médicos.");
        }
        Medico medico = new Medico(nombre, cedula, telefono, especialidad);
        clinica.agregarMedico(medico);
        guardar();  // 🔽
    }

    public void registrarConsulta(String cedulaPaciente, String cedulaMedico, String sintomas, String diagnostico, String tratamiento)
            throws CampoVacioException, UsuarioNoEncontradoException {
        if (cedulaPaciente.isEmpty() || cedulaMedico.isEmpty() || sintomas.isEmpty() || diagnostico.isEmpty() || tratamiento.isEmpty()) {
            throw new CampoVacioException("Ningún campo puede estar vacío.");
        }

        Paciente paciente = clinica.buscarPacientePorCedula(cedulaPaciente);
        if (paciente == null) {
            throw new UsuarioNoEncontradoException("Paciente no encontrado.");
        }

        Medico medico = clinica.buscarMedicoPorCedula(cedulaMedico);
        if (medico == null) {
            throw new UsuarioNoEncontradoException("Médico no encontrado.");
        }

        Consulta consulta = new Consulta(paciente, medico, sintomas, diagnostico, tratamiento);
        clinica.registrarConsulta(consulta);
        guardar();  // 🔽
    }

    public List<Consulta> obtenerHistorialPaciente(String cedula) throws UsuarioNoEncontradoException {
        Paciente paciente = clinica.buscarPacientePorCedula(cedula);
        if (paciente == null) {
            throw new UsuarioNoEncontradoException("Paciente no encontrado.");
        }
        return paciente.getHistorial();
    }

    public List<Paciente> getPacientes() {
        return clinica.getPacientes();
    }

    public List<Medico> getMedicos() {
        return clinica.getMedicos();
    }

    public Clinica getClinica() {
        return clinica;
    }

    private void guardar() {
        clinica.guardarComoTexto(ARCHIVO_TXT);
    }
}
