package juego.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Manejador de eventos del mouse que implementa {@link MouseListener} y
 * {@link MouseMotionListener}.
 * <p>
 * Su función es capturar y gestionar el estado del ratón (posición, si está
 * presionado,
 * y los eventos de borde de presión/liberación) para ser utilizado por los
 * controladores de juego, como el {@code Taco}.
 * </p>
 *
 * @version 1.0
 */
public class MouseHandler implements MouseListener, MouseMotionListener {
    /** Coordenada X y Y actual del puntero del mouse. */
    public int mouseX, mouseY;
    /** {@code true} si el botón principal del mouse está actualmente presionado. */
    public boolean isPressed = false;
    /**
     * {@code true} solo en el primer frame en que se detecta la presión del botón
     * (evento de borde).
     */
    public boolean justPressed = false;
    /**
     * {@code true} solo en el primer frame en que se detecta la liberación del
     * botón (evento de borde).
     */
    public boolean justReleased = false;

    /**
     * Actualiza la posición del mouse cuando se mueve sin arrastrar (botón no
     * presionado).
     * 
     * @param e El evento de mouse.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /**
     * Actualiza la posición del mouse mientras se arrastra (botón presionado).
     * 
     * @param e El evento de mouse.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    /**
     * Maneja el evento cuando el botón del mouse es presionado.
     * <p>
     * Establece {@code isPressed} y activa el indicador de borde {@code justPressed}.
     * </p>
     * @param e El evento de mouse.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        isPressed = true;
        justPressed = true;
        justReleased = false;
        mouseMoved(e);
    }

    /**
     * Maneja el evento cuando el botón del mouse es liberado.
     * <p>
     * Desactiva {@code isPressed} y activa el indicador de borde {@code justReleased}.
     * </p>
     * @param e El evento de mouse.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
        justReleased = true;
        justPressed = false;
        mouseMoved(e);
    }

    // Métods no usados para efectos del juego, pero obligados a sobrescribirlos por
    // la interface.
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Llamar al final de cada update() para limpiar los “just”
    public void consumeEdgeTriggers() {
        justPressed = false;
        justReleased = false;
    }
}