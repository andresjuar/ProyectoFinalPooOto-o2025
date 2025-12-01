package juego.entidades;

import java.awt.Color;
import java.awt.Graphics2D;

import libreria.matematicas.Vec2D;

/**
 * Representa una tronera (agujero o pocket) de la mesa de billar.
 * <p>
 * Su función principal es definir una región de captura para las bolas
 * y proporcionar la lógica para determinar si una {@link juego.entidades.Bola}
 * ha caído dentro de sus límites.
 * </p>
 *
 * 
 */
public class Tronera {
    /** El centro de la tronera en coordenadas del mundo físico. */
    private final Vec2D centro;
    /** El radio físico de la tronera. */
    private final double radio;

    /**
     * Constructor para crear una tronera.
     *
     * @param centro La posición central de la tronera.
     * @param radio  El radio de la tronera.
     */
    public Tronera(Vec2D centro, double radio) {
        this.centro = centro;
        this.radio = radio;
    }

    /**
     * Devuelve el radio de la Tronera.
     *
     * @return El valor del radio.
     */
    public double getRadioTronera() {
        return radio;
    }

    /**
     * Devuelve el centro de la Tronera.
     *
     * @return El vector de posición del centro.
     */
    public Vec2D getCentroTronera() {
        return centro;
    }

    /**
     * Dibuja la tronera como un círculo negro relleno en el contexto gráfico.
     *
     * @param g El contexto gráfico 2D.
     */
    public void dibujarTronera(Graphics2D g) {
        int diametro = (int) Math.round(radio * 2);
        g.setColor(Color.BLACK);
        g.fillOval((int) Math.round(centro.x - radio), (int) Math.round(centro.y - radio), diametro, diametro);
    }

    // Indica si la bola, esta dentro del agujero para determinar si entró o no a la
    // tronera
    /**
     * Indica si la bola ha caído dentro de la tronera, utilizando un margen de seguridad.
     * <p>
     * Una bola se considera "contenida" si la distancia entre su centro y el centro
     * de la tronera es menor que el radio de la tronera menos un margen de seguridad.
     * </p>
     *
     * @param bola La bola a evaluar.
     * @return {@code true} si la bola está dentro de la tronera; {@code false} en caso contrario.
     */
    public boolean contieneBola(Bola bola) {
        Vec2D centroBola = bola.getPosicion();
        // Determina la distancia que existe entre el centro de la bola, con el centro
        // de la tronera
        double distBolaAlCentroTronera = centroBola.restaVectores(centro).magnitudVector();

        // Determina un margen de seguridad para asegurarnos evitar que solamente con el
        // contacto
        // de la bola con la tronera
        double margenSeguridad = Math.max(2.0, bola.getRadio() * 0.4); // Sean 2px o el 40% del radio de la bola

        // Devuelve VERDADERO en caso de que alguna pelota este en el centro del agujero
        return distBolaAlCentroTronera < (radio - margenSeguridad);
    }
}