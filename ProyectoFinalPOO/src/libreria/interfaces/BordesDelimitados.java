package libreria.interfaces;

/**
 * Interfaz que define las propiedades de un entorno rectangular o área de juego
 * que actúa como límite físico para los cuerpos dinámicos.
 * <p>
 * Esta interfaz permite que el motor de colisiones de la librería
 * {@code libreria.colision_basica}
 * pueda detectar colisiones contra las fronteras del mundo sin tener
 * conocimiento
 * de la implementación específica del juego (por ejemplo, la clase Mesa).
 * </p>
 *
 * 
 * @version 1.0
 */
public interface BordesDelimitados {

    /**
     * Obtiene la coordenada horizontal (X) del borde izquierdo del área delimitada.
     *
     * @return La coordenada X del límite izquierdo.
     */
    double getBordeIzquierdo();

    /**
     * Obtiene la coordenada horizontal (X) del borde derecho del área delimitada.
     *
     * @return La coordenada X del límite derecho.
     */
    double getBordeDerecho();

    /**
     * Obtiene la coordenada vertical (Y) del borde superior del área delimitada.
     *
     * @return La coordenada Y del límite superior.
     */
    double getBordeSuperior();

    /**
     * Obtiene la coordenada vertical (Y) del borde inferior del área delimitada.
     *
     * @return La coordenada Y del límite inferior.
     */
    double getBordeInferior();
}
