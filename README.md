 ClinicaPOO2

ClinicaPOO2 es una aplicación de escritorio en Java para la gestión de pacientes, médicos y consultas de una clínica. Emplea el patrón Modelo-Vista-ViewModel (MVVM), interfaz gráfica con Swing y persistencia de datos mediante archivos de texto.

---

## Funcionalidades

* Registro y consulta de pacientes y médicos.
* Registro y visualización de consultas médicas.
* Persistencia automática de datos en "clinica.txt" o en archivos separados.
* Migración automática de formatos antiguos de archivo.
* Interfaz visual moderna basada en FlatLaf.

---

## Estructura del proyecto

```
ClinicaPOO2/
├── com.mycompany.clinicapoo2/
│   └── ClinicaPOO2.java         # Clase principal: configuración y arranque de la aplicación
│
├── model/                       # Clases de modelo y lógica de negocio
│   ├── Clinica.java             # Gestiona listas de pacientes, médicos y consultas. Carga y guardado de datos.
│   ├── Persona.java             # Clase base para Paciente y Medico
│   ├── Paciente.java            # Hereda de Persona. Contiene historial de consultas.
│   ├── Medico.java              # Hereda de Persona, implementa Agendable. Contiene especialidad y agenda.
│   ├── Consulta.java            # Representa una cita médica entre paciente y médico.
│   └── IPersistencia.java       # Interfaz para estrategias de persistencia.
│
├── persistence/                 # Implementaciones de persistencia de datos
│   ├── ClinicaPersistencia.java # Archivos separados: pacientes.txt, medicos.txt, consultas.txt
│   └── PersistenciaTexto.java   # Implementa IPersistencia con formato de secciones en clinica.txt
│
├── viewmodel/                   # Lógica intermedia entre modelo y vista
│   └── ClinicaViewModel.java
│
└── view/                        # Componentes de interfaz gráfica
    ├── VentanaLogin.java        # Ventana de inicio de sesión
    ├── PanelRegistro.java       # Panel para registrar pacientes y médicos
    ├── PanelConsulta.java       # Panel para registrar consultas
    └── PanelHistorial.java      # Panel para consultar historial de pacientes
```

---

## Persistencia de datos

La aplicación admite tres estrategias de persistencia:

### 1. Log incremental en "clinica.txt"

* El archivo almacena entradas con prefijos "P;", "M;" o "C;".
* Ejemplo de líneas:

  ```txt
  P;Juan Perez;123456;312123123
  M;Dra. Ana;654321;311111111;Pediatría
  C;123456;654321;2025-06-25;fiebre alta;dengue;paracetamol
  ```
* `Clinica.cargarDesdeTexto()` reconstruye las listas a partir del log.
* Los métodos `agregarPaciente`, `agregarMedico` y `registrarConsulta` realizan una inserción al final del archivo.
* Se detecta automáticamente el formato antiguo (secciones) y se migra al formato moderno.

### 2. Archivos separados (ClinicaPersistencia.java)

* Tres archivos: `pacientes.txt`, `medicos.txt` y `consultas.txt`.
* Campos delimitados por "|".
* Métodos de guardado y carga específicos para cada entidad.

### 3. Formato de secciones en "clinica.txt" (PersistenciaTexto.java)

* Secciones marcadas con etiquetas:

  ```txt
  [PACIENTES]
  Juan Perez;123456;312123123
  [MEDICOS]
  Dra. Ana;654321;311111111;Pediatría
  [CONSULTAS]
  123456;654321;2025-06-25;fiebre alta;dengue;paracetamol
  ```
* `guardarDatos()` sobrescribe todo el archivo.
* `cargarDatos()` reconstruye primero pacientes y médicos, luego asocia consultas.

Se puede cambiar de estrategia instanciando la clase adecuada que implemente IPersistencia.

---

## Modelos de datos

### Persona

Clase abstracta con atributos comunes:

* nombre
* cedula
* telefono

### Paciente

* Hereda de Persona.
* Lista de consultas (`historial`).
* Métodos: `agregarConsulta(Consulta)` y `getHistorial()`.

### Medico

* Hereda de Persona.
* Implementa la interfaz Agendable.
* Atributo especializado: `especialidad`.
* Lista de consultas (`agenda`).
* Métodos: `asignarConsulta(Consulta)` y `obtenerConsultas()`.

### Consulta

* Referencias a Paciente y Medico.
* Atributos: sintomas, diagnostico, tratamiento y fecha.
* Dos constructores: uno para nuevas consultas (fecha actual) y otro para consultas cargadas (fecha recibida).
* Métodos: getters y `toString()`.

### IPersistencia

Interfaz que define:

```java
void guardarDatos(Clinica clinica) throws IOException;
Clinica cargarDatos() throws IOException, ClassNotFoundException;
```

Permite implementar diferentes estrategias de persistencia.

---

## Interfaz gráfica

La interfaz se basa en Swing y utiliza FlatLaf para el tema visual. Sigue el patrón MVVM mediante `ClinicaViewModel`.

* **VentanaLogin.java**: pantalla de inicio de sesión.
* **PanelRegistro.java**: registro de pacientes y médicos.
* **PanelConsulta.java**: registro de consultas médicas.
* **PanelHistorial.java**: visualización del historial de consultas de un paciente.

Cada panel se conecta al ViewModel, que expone métodos como:

```java
List<Paciente> getPacientes();
List<Medico> getMedicos();
void registrarPaciente(String nombre, String cedula, String telefono) throws CampoVacioException;
void registrarMedico(String nombre, String cedula, String telefono, String especialidad) throws CampoVacioException;
void registrarConsulta(String cedulaPac, String cedulaMed, String sintomas, String diagnostico, String tratamiento) throws CampoVacioException, UsuarioNoEncontradoException;
List<Consulta> obtenerHistorialPaciente(String cedula) throws UsuarioNoEncontradoException;
```

---

## Ejecución

1. Clonar el repositorio.
2. Abrir el proyecto en un IDE compatible (NetBeans, IntelliJ).
3. Asegurarse de disponer de Java 17 o superior.
4. Ejecutar la clase `ClinicaPOO2`.

---

## Autor
Hurtado Parada Diego Alejandro

Proyecto desarrollado para la asignatura Programación Orientada a Objetos.

