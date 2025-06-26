package model;

import java.io.IOException;

public interface IPersistencia {
    void guardarDatos(Clinica clinica) throws IOException;
    Clinica cargarDatos() throws IOException, ClassNotFoundException;
}
