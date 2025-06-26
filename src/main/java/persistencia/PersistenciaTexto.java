package persistencia;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class PersistenciaTexto implements IPersistencia {

    private static final String ARCHIVO = "clinica.txt";

    @Override
    public void guardarDatos(Clinica clinica) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO))) {
            // Pacientes
            writer.println("[PACIENTES]");
            for (Paciente p : clinica.getPacientes()) {
                writer.printf("%s;%s;%s%n", p.getNombre(), p.getCedula(), p.getTelefono());
            }

            // Médicos
            writer.println("[MEDICOS]");
            for (Medico m : clinica.getMedicos()) {
                writer.printf("%s;%s;%s;%s%n", m.getNombre(), m.getCedula(), m.getTelefono(), m.getEspecialidad());
            }

            // Consultas
            writer.println("[CONSULTAS]");
            for (Consulta c : clinica.getConsultas()) {
                writer.printf("%s;%s;%s;%s;%s;%s%n",
                        c.getPaciente().getCedula(),
                        c.getMedico().getCedula(),
                        c.getFecha().toString(),
                        c.getSintomas(),
                        c.getDiagnostico(),
                        c.getTratamiento());
            }
        }
    }

    @Override
    public Clinica cargarDatos() throws IOException {
        Clinica clinica = new Clinica();

        Map<String, Paciente> pacientesMap = new HashMap<>();
        Map<String, Medico> medicosMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            String seccion = "";

            while ((linea = reader.readLine()) != null) {
                if (linea.equalsIgnoreCase("[PACIENTES]")) {
                    seccion = "PACIENTES";
                } else if (linea.equalsIgnoreCase("[MEDICOS]")) {
                    seccion = "MEDICOS";
                } else if (linea.equalsIgnoreCase("[CONSULTAS]")) {
                    seccion = "CONSULTAS";
                } else if (!linea.isBlank()) {
                    String[] partes = linea.split(";");
                    switch (seccion) {
                        case "PACIENTES" -> {
                            if (partes.length == 3) {
                                Paciente p = new Paciente(partes[0], partes[1], partes[2]);
                                clinica.agregarPacienteSinGuardar(p);
                                pacientesMap.put(p.getCedula(), p);
                            }
                        }
                        case "MEDICOS" -> {
                            if (partes.length == 4) {
                                Medico m = new Medico(partes[0], partes[1], partes[2], partes[3]);
                                clinica.agregarMedicoSinGuardar(m);
                                medicosMap.put(m.getCedula(), m);
                            }
                        }
                        case "CONSULTAS" -> {
                            if (partes.length == 6) {
                                String cedulaPaciente = partes[0];
                                String cedulaMedico = partes[1];
                                String fechaStr = partes[2];
                                String sintomas = partes[3];
                                String diagnostico = partes[4];
                                String tratamiento = partes[5];

                                Paciente p = pacientesMap.get(cedulaPaciente);
                                Medico m = medicosMap.get(cedulaMedico);

                                if (p != null && m != null) {
                                    LocalDate fecha = LocalDate.parse(fechaStr);
                                    Consulta consulta = new Consulta(p, m, sintomas, diagnostico, tratamiento, fecha);
                                    clinica.registrarConsultaSinGuardar(consulta);
                                }
                            }
                        }
                    }
                }
            }
        }

        return clinica;
    }
}
