package libreria.dinamica;

import libreria.matematicas.Vec2D;

/**
 * Interfaz que define la capacidad de un cuerpo físico para recibir fuerzas externas.
 * <p>
 * Esta interfaz es clave para la dinámica del motor, permitiendo que fuerzas
 * como la gravedad, el arrastre o las fuerzas de impulso sean aplicadas de manera
 * uniforme a cualquier objeto que implemente {@code Cuerpo}.
 * </p>
 *
 * 
 * @version 1.0
 */
public interface AplicarFuerza {
/**
     * Acumula una fuerza externa para ser considerada en el siguiente paso de la simulación.
     * <p>
     * Las implementaciones de esta interfaz generalmente suman la fuerza {@code f}
     * a un vector de fuerza acumulada dentro del cuerpo.
     * </p>
     *
     * @param f El vector de fuerza a aplicar (con componentes X y Y).
     */
     void aplicarFuerza(Vec2D f);    
}
