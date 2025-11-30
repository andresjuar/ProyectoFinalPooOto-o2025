package libreria.dinamica;

/**
 * Enumeración utilizada para clasificar los cuerpos físicos dentro de la simulación.
 * <p>
 * Esta clasificación es crucial para determinar si un cuerpo debe ser afectado
 * por la integración del movimiento, fuerzas y colisiones.
 * </p>
 *
 * 
 * @version 1.0
 */
public enum TipoDeObjeto {
    /**
     * Objeto Dinámico: Un cuerpo que se mueve, tiene masa finita y responde
     * a la aplicación de fuerzas, impulsos y la integración de movimiento.
     * (Ejemplo: Las bolas de billar).
     */
    DINAMICO, 
    /**
     * Objeto Estático: Un cuerpo fijo con masa y masa inversa infinitas (inversa 0).
     * No se mueve, no responde a fuerzas, e ignora la integración de movimiento.
     * (Ejemplo: Las paredes o bandas de la mesa).
     */
    ESTATICO
}
