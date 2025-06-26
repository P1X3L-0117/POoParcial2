// UsuarioNoEncontradoException.java
// Se lanza cuando no se encuentra un paciente o médico con la cédula indicada.

package excepciones;

public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}


