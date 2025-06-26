/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Agendable.java
// Interfaz para clases que manejan consultas (ej: Medico)

package model;

import java.util.List;

public interface Agendable {
    List<Consulta> obtenerConsultas();
}

