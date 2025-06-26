package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Consulta implements Serializable {
    private Paciente paciente;
    private Medico medico;
    private String sintomas;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;

    // Constructor con fecha actual (para nuevas consultas)
    public Consulta(Paciente paciente, Medico medico, String sintomas, String diagnostico, String tratamiento) {
        this.paciente = paciente;
        this.medico = medico;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = LocalDate.now();
    }

    // Constructor con fecha específica (para cargar desde archivo)
    public Consulta(Paciente paciente, Medico medico, String sintomas, String diagnostico, String tratamiento, LocalDate fecha) {
        this.paciente = paciente;
        this.medico = medico;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
    }

    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public String getSintomas() { return sintomas; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public LocalDate getFecha() { return fecha; }

    @Override
    public String toString() {
        return String.format("Consulta [%s]\nMédico: %s\nSíntomas: %s\nDiagnóstico: %s\nTratamiento: %s\n",
                fecha, medico.getNombre(), sintomas, diagnostico, tratamiento);
    }
}
