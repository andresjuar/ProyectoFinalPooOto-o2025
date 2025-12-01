/**
 * Este paquete define las entidades específicas del juego de billar
 * que interactúan con el motor de física proporcionado por la librería {@code libreria.*}.
 * <p>
 * Las clases en este paquete representan los elementos físicos y lógicos del juego:
 * <ul>
 * <li>{@link juego.entidades.Bola}: Hereda de {@code Cuerpo} y es la entidad dinámica principal.</li>
 * <li>{@link juego.entidades.Mesa}: Hereda de {@code Cuerpo} (estático) y define los límites del juego.</li>
 * <li>{@link juego.entidades.Tronera}: Define las regiones de captura de las bolas.</li>
 * <li>{@link juego.entidades.Taco}: Maneja la entrada del usuario para aplicar el impulso inicial.</li>
 * <li>{@link juego.entidades.TipoBola} y {@link juego.entidades.GrupoBola}: Enumeraciones para la lógica de reglas y asignación de jugadores.</li>
 * </ul>
 * </p>
 *

 */
package juego.entidades;
