package libreria.interfaces;

/**
 * Interfaz que define la capacidad de un cuerpo físico para exponer su radio.
 * <p>
 * Se utiliza para modelar cuerpos con geometría circular,
 * permitiendo que los detectores de colisión (como {@code DetectorColisiones})
 * puedan realizar cálculos específicos de colisiones entre círculos.
 * </p>
 * <p>
 * Esta interfaz asegura que el motor de colisiones de la librería no necesite
 * conocer la clase concreta (ej. Bola) que representa el círculo.
 * </p>
 *
 * 
 * @version 1.0
 */
public interface CuerpoCircular {

    /**
     * Obtiene el radio del cuerpo circular.
     *
     * @return El valor del radio.
     */
    double getRadio();
}
