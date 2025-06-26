// Paciente.java
// Hereda de Persona y contiene historial de consultas

package model;

import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona {
    private List<Consulta> historial = new ArrayList<>();

    public Paciente(String nombre, String cedula, String telefono) {
        super(nombre, cedula, telefono);
    }

    public void agregarConsulta(Consulta consulta) {
        historial.add(consulta);
    }

    public List<Consulta> getHistorial() {
        return historial;
    }
}
