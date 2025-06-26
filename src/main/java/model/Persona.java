// Persona.java
// Clase abstracta base para Paciente y Medico

package model;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    protected String nombre;
    protected String cedula;
    protected String telefono;

    public Persona(String nombre, String cedula, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
