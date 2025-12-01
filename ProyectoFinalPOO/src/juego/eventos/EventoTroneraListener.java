package juego.eventos;

import juego.entidades.Bola;

/**
 * Interfaz funcional para manejar eventos de embolsado de bolas.
 * <p>
 * Implementa el patrón Observer (o Listener), permitiendo que el
 * {@link juego.sistema.SimuladorBillar} notifique a la lógica de la
 * partida (ej. {@link juego.sistema.PartidaBillar}) cada vez que una
 * {@link juego.entidades.Bola} cae en una {@link juego.entidades.Tronera}.
 * </p>
 * <p>
 * La implementación de esta interfaz se encarga de aplicar las reglas
 * de juego asociadas al embolsado (cambio de turno, eliminación de bola,
 * detección de falta).
 * </p>
 * 
 */
@FunctionalInterface
public interface EventoTroneraListener {
    /**
     * Se llama cuando una bola entra en una tronera
     * <p>
     * Permite que el juego actualice sus datos.
     * </p>
     * 
     * @param bola La instancia de la Bola que ha sido embolsada.
     */
    public void bolaEmbolsada(Bola bola);
}
