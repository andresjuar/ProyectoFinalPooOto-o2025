package juego.entidades;

/**
 * Enumeración que clasifica las bolas de billar según las reglas estándar del juego.
 * <p>
 * Esta clasificación es crucial para la lógica de la partida, ya que define
 * el grupo al que pertenece cada jugador (LISA o RAYADA) y distingue las
 * bolas con reglas especiales (BLANCA y OCHO).
 * </p>
 *
 */
public enum TipoBola {
    /**
     * La bola principal utilizada para golpear. Tiene reglas especiales de reinicio
     * si cae en una tronera. (ID "blanca").
     */
    BLANCA, 
    /**
     * Bolas de un solo color (números 1 a 7). Asignadas a uno de los jugadores.
     */
    LISA, 
    /**
     * Bolas con una franja de color (rayadas, números 9 a 15). Asignadas al otro jugador.
     */
    RAYADA, 
    /**
     * La bola final (número 8). Su embolsado prematuro o incorrecto resulta en la pérdida de la partida.
     */
    OCHO
}