package com.mycompany.clinicapoo2;

import com.formdev.flatlaf.FlatLightLaf;
import model.Clinica;
import viewmodel.ClinicaViewModel;
import view.VentanaLogin;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClinicaPOO2 {
    private static final String RUTA = "clinica.txt";

    public static void main(String[] args) {
        // 1) Look & Feel
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ignored) {}

        // 2) Si el fichero existe y contiene secciones, lo migramos:
        File f = new File(RUTA);
        if (f.exists() && contieneSecciones(f)) {
            migrarFormatoSeccionesALog(f);
        }

        // 3) Cargar con el nuevo formato log incremental
        Clinica clinica = new Clinica();
        clinica.cargarDesdeTexto();

        // 4) Crear ViewModel y arrancar UI
        ClinicaViewModel vm = new ClinicaViewModel(clinica);
        SwingUtilities.invokeLater(() -> new VentanaLogin(vm).setVisible(true));
    }

    //** Detecta si el fichero usa los headers --- PACIENTES --- etc */
    private static boolean contieneSecciones(File f) {
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.startsWith("--- PACIENTES")) return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    //** Convierte el fichero de formato secciones a formato log P;/M;/C; */
    private static void migrarFormatoSeccionesALog(File f) {
        List<String> log = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line; String seccion = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("--- PACIENTES")) { seccion = "P"; continue; }
                if (line.startsWith("--- MEDICOS"))   { seccion = "M"; continue; }
                if (line.startsWith("--- CONSULTAS")) { seccion = "C"; continue; }
                if (line.isBlank()) continue;

                String[] parts = line.split(";");
                switch (seccion) {
                    case "P" -> {
                        // nombre;cedula;telefono
                        log.add("P;" + parts[0] + ";" + parts[1] + ";" + parts[2]);
                    }
                    case "M" -> {
                        // nombre;cedula;telefono;especialidad
                        log.add("M;" + parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3]);
                    }
                    case "C" -> {
                        // pacienteCed;medCed;sint;diag;tratam;fecha?
                        // tu formato era 2;3;4;4;4;2025-06-25
                        // reorganizamos para que fecha sea el 3º campo:
                        // C;2;3;2025-06-25;4;4;4
                        String pacienteCed = parts[0],
                               medicoCed   = parts[1],
                               sintomas    = parts[2],
                               diag        = parts[3],
                               tratam      = parts[4],
                               fecha       = parts[5];
                        log.add("C;" + pacienteCed + ";" + medicoCed + ";" +
                                fecha + ";" + sintomas + ";" + diag + ";" + tratam);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Reescribe TODO el fichero en formato log
        try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
            for (String l : log) pw.println(l);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
