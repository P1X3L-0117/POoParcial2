package persistencia;

import model.*;
import java.io.*;
import java.util.*;

public class ClinicaPersistencia {

    private static final String PACIENTES_FILE = "pacientes.txt";
    private static final String MEDICOS_FILE = "medicos.txt";
    private static final String CONSULTAS_FILE = "consultas.txt";

    // Guardar pacientes
    public static void guardarPacientes(List<Paciente> pacientes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PACIENTES_FILE))) {
            for (Paciente p : pacientes) {
                writer.write(p.getNombre() + "|" + p.getCedula() + "|" + p.getTelefono());
                writer.newLine();
            }
        }
    }

    // Cargar pacientes
    public static List<Paciente> cargarPacientes() throws IOException {
        List<Paciente> pacientes = new ArrayList<>();
        File archivo = new File(PACIENTES_FILE);
        if (!archivo.exists()) return pacientes;

        try (BufferedReader reader = new BufferedReader(new FileReader(PACIENTES_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 3) {
                    pacientes.add(new Paciente(datos[0], datos[1], datos[2]));
                }
            }
        }
        return pacientes;
    }

    // Guardar médicos
    public static void guardarMedicos(List<Medico> medicos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEDICOS_FILE))) {
            for (Medico m : medicos) {
                writer.write(m.getNombre() + "|" + m.getCedula() + "|" + m.getTelefono() + "|" + m.getEspecialidad());
                writer.newLine();
            }
        }
    }

    // Cargar medicos
    public static List<Medico> cargarMedicos() throws IOException {
        List<Medico> medicos = new ArrayList<>();
        File archivo = new File(MEDICOS_FILE);
        if (!archivo.exists()) return medicos;

        try (BufferedReader reader = new BufferedReader(new FileReader(MEDICOS_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 4) {
                    medicos.add(new Medico(datos[0], datos[1], datos[2], datos[3]));
                }
            }
        }
        return medicos;
    }

    // Guardar consultas
    public static void guardarConsultas(List<Consulta> consultas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONSULTAS_FILE))) {
            for (Consulta c : consultas) {
                writer.write(c.getPaciente().getCedula() + "|" +
                             c.getMedico().getCedula() + "|" +
                             c.getSintomas() + "|" +
                             c.getDiagnostico() + "|" +
                             c.getTratamiento());
                writer.newLine();
            }
        }
    }

    // Cargar consultas (requiere lista previa de pacientes y médicos para asociar)
    public static List<Consulta> cargarConsultas(List<Paciente> pacientes, List<Medico> medicos) throws IOException {
        List<Consulta> consultas = new ArrayList<>();
        File archivo = new File(CONSULTAS_FILE);
        if (!archivo.exists()) return consultas;

        try (BufferedReader reader = new BufferedReader(new FileReader(CONSULTAS_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length == 5) {
                    Paciente paciente = buscarPaciente(pacientes, datos[0]);
                    Medico medico = buscarMedico(medicos, datos[1]);
                    if (paciente != null && medico != null) {
                        consultas.add(new Consulta(paciente, medico, datos[2], datos[3], datos[4]));
                    }
                }
            }
        }
        return consultas;
    }

    private static Paciente buscarPaciente(List<Paciente> pacientes, String cedula) {
        for (Paciente p : pacientes) {
            if (p.getCedula().equals(cedula)) return p;
        }
        return null;
    }

    private static Medico buscarMedico(List<Medico> medicos, String cedula) {
        for (Medico m : medicos) {
            if (m.getCedula().equals(cedula)) return m;
        }
        return null;
    }
}
