package libreria.colision_basica;

import libreria.dinamica.Cuerpo;
import libreria.matematicas.Vec2D;

/**
 * Clase que almacena la información detallada de un contacto o colisión
 * entre dos cuerpos físicos.
 * <p>
 * Esta información es crucial para el {@code ResolverColisiones}, ya que define
 * la dirección y magnitud de la separación (corrección de posición) y del
 * impulso
 * (corrección de velocidad) que deben aplicarse.
 * </p>
 *
 * 
 * @version 1.0
 */
public class ContactoColision {
    /** Los cuerpos involucrados en la colisión. */
    public final Cuerpo A, B;

    /**
     * Vector unitario normal al punto de contacto.
     * <p>
     * Se utiliza para calcular los impulsos y la corrección de posición.
     * Por convención, apunta desde el cuerpo A hacia el cuerpo B.
     * </p>
     */
    public Vec2D vectorNormal = Vec2D.crearVectorNulo();
    /**
     * Profundidad de penetración (traslape) entre los dos cuerpos.
     * <p>
     * Indica cuánto se han superpuesto las geometrías. Se usa en la corrección de
     * posición
     * para separarlos y resolver la penetración.
     * </p>
     */
    public double traslape = 0.0;

    /**
     * Proyección de la velocidad relativa de B con respecto a A sobre el vector
     * normal.
     * <p>
     * Un valor negativo indica que los cuerpos se están acercando, y un valor
     * positivo
     * (post-colisión) indica que se están separando. Es esencial para calcular la
     * magnitud
     * del impulso.
     * </p>
     */
    public double proyVelocidadRelEnNormal = 0.0;

    /**
     * Crea una instancia de contacto entre dos cuerpos involucrados.
     * <p>
     * Nota: Los parámetros de contacto específicos (normal, traslape, etc.)
     * deben ser calculados y establecidos por un {@code DetectorColisiones}
     * posteriormente.
     * </p>
     *
     * @param A El primer cuerpo rígido.
     * @param B El segundo cuerpo rígido.
     */
    public ContactoColision(Cuerpo A, Cuerpo B) {
        this.A = A;
        this.B = B;
    }
}