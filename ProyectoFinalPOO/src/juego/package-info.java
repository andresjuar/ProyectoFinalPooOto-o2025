/**
 * Contiene todas las clases, entidades, lógica del sistema y la interfaz de usuario (UI)
 * que son específicas del juego de Billar (Bola 8).
 *
 * <p>
 * Este paquete define la estructura de alto nivel del juego, separando las
 * responsabilidades en subpaquetes:
 * </p>
 * <ul>
 * <li>{@link juego.entidades}: Define los objetos del mundo del juego (Bola, Mesa, Taco, Tronera).</li>
 * <li>{@link juego.sistema}: Contiene la lógica central del juego, la simulación física (SimuladorBillar)
 * y la gestión de reglas (PartidaBillar).</li>
 * <li>{@link juego.ui}: Maneja la presentación y la interacción con el usuario (GamePanel, MouseHandler).</li>
 * <li>{@link juego.eventos}: Define interfaces para el manejo de notificaciones asíncronas (Listeners).</li>
 * </ul>
 *
 * <p>
 * El paquete {@code juego} depende totalmente del paquete {@code libreria} para las
 * funcionalidades de física (vectores, cuerpos, colisiones) y utilidades matemáticas.
 * </p>
 *
 */

package juego;
