package libreria.interfaces;

/**
 * Interfaz funcional que define la capacidad de un objeto para ser actualizado
 * o integrado en el tiempo.
 * <p>
 * Es una interfaz funcional porque define un único método abstracto. Es
 * fundamental para la simulación física, ya que permite que el motor de juego
 * o el mundo físico iteren sobre cualquier objeto dinámico y apliquen
 * la integración del movimiento.
 * </p>
 *
 * 
 * @version 1.0
 */

@FunctionalInterface
public interface Actualizable {
    // Actualiza el objeto dependiendo del tiempo transcurrido

    /**
     * Actualiza el estado del objeto en función del tiempo transcurrido.
     * <p>
     * Para los cuerpos físicos, este método se encarga de aplicar fuerzas,
     * integrar la aceleración y la velocidad, y actualizar la posición.
     * </p>
     *
     * @param dt El delta de tiempo (intervalo de tiempo) transcurrido desde la última actualización (segundos).
     */
    void actualizar(double dt);
}
