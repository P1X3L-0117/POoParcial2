// CampoVacioException.java
// Se lanza cuando uno o más campos requeridos están vacíos.

package excepciones;

public class CampoVacioException extends Exception {
    public CampoVacioException(String mensaje) {
        super(mensaje);
    }
}
