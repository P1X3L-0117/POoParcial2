// Medico.java
// Hereda de Persona, implementa Agendable y almacena sus consultas

package model;

import java.util.ArrayList;
import java.util.List;

public class Medico extends Persona implements Agendable {
    private String especialidad;
    private List<Consulta> consultas = new ArrayList<>();

    public Medico(String nombre, String cedula, String telefono, String especialidad) {
        super(nombre, cedula, telefono);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void asignarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    @Override
    public List<Consulta> obtenerConsultas() {
        return consultas;
    }
}
