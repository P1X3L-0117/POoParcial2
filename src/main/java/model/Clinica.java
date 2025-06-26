package model;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Clinica implements Serializable {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();

    private static final String RUTA = "clinica.txt";

    //** Carga todo el log y reconstruye el estado */
     //** Carga todo el log y reconstruye el estado */
    public void cargarDesdeTexto() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("P;")) {
                    // Paciente: "P;nombre;cedula;telefono"
                    String[] p = linea.split(";", 4);
                    Paciente paciente = new Paciente(p[1], p[2], p[3]);
                    pacientes.add(paciente);
                } else if (linea.startsWith("M;")) {
                    // Medico: "M;nombre;cedula;telefono;especialidad"
                    String[] m = linea.split(";", 5);
                    Medico medico = new Medico(m[1], m[2], m[3], m[4]);
                    medicos.add(medico);
                } else if (linea.startsWith("C;")) {
                    // Consulta: "C;cedulaPac;cedulaMed;fecha;sintomas;diagnostico;tratamiento"
                    String[] c = linea.split(";", 7);
                    // buscar referencias
                    Paciente pac = buscarPacientePorCedula(c[1]);
                    Medico med = buscarMedicoPorCedula(c[2]);
                    if (pac != null && med != null) {
                        Consulta consulta = new Consulta(pac, med, c[4], c[5], c[6], LocalDate.parse(c[3]));
                        consultas.add(consulta);
                        pac.agregarConsulta(consulta);
                        med.asignarConsulta(consulta);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // si no existe, inicia vacío; luego se creará al primer append
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //** Agrega en memoria y anota solo ese paciente al log */
    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
        appendLinea("P;" + paciente.getNombre() + ";" + paciente.getCedula() + ";" + paciente.getTelefono());
    }

    //** Agrega en memoria y anota solo ese médico al log */
    public void agregarMedico(Medico medico) {
        medicos.add(medico);
        appendLinea("M;" + medico.getNombre() + ";" + medico.getCedula() + ";" + medico.getTelefono() + ";" + medico.getEspecialidad());
    }

    //** Agrega en memoria y anota solo esa consulta al log */
    public void registrarConsulta(Consulta consulta) {
        consultas.add(consulta);
        consulta.getPaciente().agregarConsulta(consulta);
        consulta.getMedico().asignarConsulta(consulta);
        appendLinea("C;" +
            consulta.getPaciente().getCedula() + ";" +
            consulta.getMedico().getCedula() + ";" +
            consulta.getFecha() + ";" +
            consulta.getSintomas() + ";" +
            consulta.getDiagnostico() + ";" +
            consulta.getTratamiento()
        );
    }

    //** Método auxiliar de append */
    private void appendLinea(String linea) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RUTA, true))) {
            writer.println(linea);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public Paciente buscarPacientePorCedula(String cedula) {
        return pacientes.stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public Medico buscarMedicoPorCedula(String cedula) {
        return medicos.stream()
                .filter(m -> m.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    // Guardado manual, invocado explícitamente desde fuera
    public void guardarComoTexto(String ruta) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            writer.println("--- PACIENTES ---");
            for (Paciente p : pacientes) {
                writer.printf("%s;%s;%s%n", p.getNombre(), p.getCedula(), p.getTelefono());
            }

            writer.println("--- MEDICOS ---");
            for (Medico m : medicos) {
                writer.printf("%s;%s;%s;%s%n", m.getNombre(), m.getCedula(), m.getTelefono(), m.getEspecialidad());
            }

            writer.println("--- CONSULTAS ---");
            for (Consulta c : consultas) {
                writer.printf("%s;%s;%s;%s;%s;%s%n",
                        c.getPaciente().getCedula(),
                        c.getMedico().getCedula(),
                        c.getSintomas(),
                        c.getDiagnostico(),
                        c.getTratamiento(),
                        c.getFecha());
            }

        } catch (IOException e) {
            System.err.println("Error al guardar datos como texto: " + e.getMessage());
        }
    }

    public void cargarDesdeTexto(String ruta) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            String seccion = "";

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("---")) {
                    seccion = linea;
                    continue;
                }

                String[] partes = linea.split(";");
                switch (seccion) {
                    case "--- PACIENTES ---":
                        if (partes.length == 3) {
                            agregarPacienteSinGuardar(new Paciente(partes[0], partes[1], partes[2]));
                        }
                        break;
                    case "--- MEDICOS ---":
                        if (partes.length == 4) {
                            agregarMedicoSinGuardar(new Medico(partes[0], partes[1], partes[2], partes[3]));
                        }
                        break;
                    case "--- CONSULTAS ---":
                        if (partes.length >= 6) {
                            Paciente paciente = buscarPacientePorCedula(partes[0]);
                            Medico medico = buscarMedicoPorCedula(partes[1]);
                            if (paciente != null && medico != null) {
                                Consulta c = new Consulta(paciente, medico, partes[2], partes[3], partes[4]);
                                registrarConsultaSinGuardar(c);
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar archivo clinica.txt: " + e.getMessage());
        }
    }

    public void agregarPacienteSinGuardar(Paciente p) {
        pacientes.add(p);
    }

    public void agregarMedicoSinGuardar(Medico m) {
        medicos.add(m);
    }

    public void registrarConsultaSinGuardar(Consulta c) {
        consultas.add(c);
        c.getPaciente().agregarConsulta(c);
        c.getMedico().asignarConsulta(c);
    }
}
