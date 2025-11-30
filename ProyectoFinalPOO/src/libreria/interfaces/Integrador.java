package libreria.interfaces;

/**
 * Interfaz funcional que define el contrato para la integración del estado
 * de un objeto {@code Actualizable} a lo largo del tiempo.
 * <p>
 * En el contexto de la física, un integrador es la lógica que utiliza
 * el delta de tiempo (dt) para actualizar la posición y velocidad de un cuerpo
 * basándose en su aceleración (como el método de Euler o Verlet).

 * </p>
 *
 * @author [Tu Nombre]
 * @version 1.0
 */
@FunctionalInterface
public interface Integrador {
    /**
     * Integra el estado de un objeto que es capaz de actualizarse a sí mismo
     * durante un intervalo de tiempo.
     * <p>
     * Típicamente, el método de integración delega la responsabilidad de
     * actualización al propio objeto: {@code obj.actualizar(dt)}.
     * </p>
     *
     * @param obj El objeto que implementa {@code Actualizable} y cuyo estado será modificado.
     * @param dt El intervalo de tiempo transcurrido para la integración (delta de tiempo).
     */
    // Integra el estado de un objeto durante un intervalo de tiempo.
    void integrar(Actualizable obj, double dt);
}
