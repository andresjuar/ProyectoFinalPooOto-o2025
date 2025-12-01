package juego.entidades;

// Enum para asignar el grupo de bolas a los jugadores
/**
 * Enumeración utilizada para asignar los grupos de bolas (Lisas o Rayadas)
 * a los jugadores una vez que la bola es introducida legalmente por primera vez.
 * <p>
 * Es fundamental para la lógica de la partida, ya que define el objetivo
 * de cada jugador.
 * </p>
 *
 * 
 */
public enum GrupoBola {
    /**
     * Estado inicial antes de que se determine el grupo de un jugador.
     */
    SIN_ASIGNAR, 
    /**
     * El jugador debe introducir las bolas Lisas (números 1 a 7).
     */
    LISAS, 
    /**
     * El jugador debe introducir las bolas Rayadas (números 9 a 15).
     */
    RAYADAS    
}
