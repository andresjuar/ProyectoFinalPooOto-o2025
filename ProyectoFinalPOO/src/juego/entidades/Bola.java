package juego.entidades;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import libreria.dinamica.Cuerpo;
import libreria.interfaces.CuerpoCircular;
import libreria.matematicas.Vec2D;

/**
 * Representa una bola de billar específica en el juego.
 * <p>
 * {@code Bola} extiende {@link libreria.dinamica.Cuerpo} para heredar todas las
 * propiedades físicas
 * (masa, velocidad, posición) y aplica la interfaz
 * {@link libreria.interfaces.CuerpoCircular}
 * para permitir que el motor de colisiones de la librería la trate como un
 * círculo.
 * Además, maneja sus propiedades visuales (texturas) y de juego (TipoBola).
 * </p>
 *
 * 
 * @version 1.0
 */

public class Bola extends Cuerpo implements CuerpoCircular {
    /** Radio de la bola (determina tanto la física como el dibujo). */
    private final double radio;
    /** Tipo de bola según las reglas del billar (LISA, RAYADA, OCHO, BLANCA). */
    private TipoBola tipo;
    /** Color de relleno por defecto si no se carga la textura. */
    private Color color = Color.WHITE;

    // Atributos para el diseño de las bolas
    public BufferedImage uno, dos, tres, cuatro, cinco, seis, siete, ocho,
            nueve, diez, once, doce, trece, catorce, quince, blanca;

    /**
     * Constructor de la bola. Inicializa las propiedades físicas y carga los
     * recursos visuales.
     *
     * @param id         Identificador de la bola ("blanca", "ocho", "uno", etc.).
     * @param masa       Masa de la bola (kg).
     * @param radio      Radio de la bola (m).
     * @param posInicial Posición inicial de la bola en el mundo físico.
     */
    public Bola(String id, double masa, double radio, Vec2D posInicial) {
        super(id, masa);
        this.radio = radio;
        this.posicion = posInicial.clone();
        this.tipo = setTipoBola(id);
        getImageBola();
    }

    /**
     * Devuelve el radio físico de la bola, requerido por la interfaz
     * {@code CuerpoCircular}
     * para la detección de colisiones.
     *
     * @return El radio de la bola.
     */
    @Override
    public double getRadio() {
        return radio;
    }

    /**
     * Establece un color de relleno para la bola. Utilizado
     * si la carga de texturas falla.
     *
     * @param c El color.
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * Define el tipo de bola (LISA, RAYADA, OCHO, BLANCA) basándose en su ID.
     *
     * @param id El ID de la bola ("uno" a "quince", "ocho", "blanca").
     * @return El {@code TipoBola} asociado.
     * @throws IllegalArgumentException Si el ID de la bola no es reconocido.
     */
    private TipoBola setTipoBola(String id) {
        if ("blanca".equals(id))
            return TipoBola.BLANCA;
        if ("ocho".equals(id))
            return TipoBola.OCHO;

        switch (id) {
            case "uno":
            case "dos":
            case "tres":
            case "cuatro":
            case "cinco":
            case "seis":
            case "siete":
                return TipoBola.LISA;
            case "nueve":
            case "diez":
            case "once":
            case "doce":
            case "trece":
            case "catorce":
            case "quince":
                return TipoBola.RAYADA;
            default:
                throw new IllegalArgumentException("Id de bola no válido: " + id);

        }
    }

    /**
     * Obtiene el tipo de bola.
     *
     * @return El {@code TipoBola} de esta instancia.
     */
    public TipoBola getTipo() {
        return tipo;
    }

    /**
     * Carga todas las imágenes (texturas) que representan las 16 bolas de billar
     * desde los recursos del proyecto.
     * <p>
     * Las imágenes cargadas se almacenan en los atributos {@code BufferedImage}.
     * </p>
     */
    private void getImageBola() {
        try {

            blanca = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_Blanca.png"));
            uno = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_1.png"));
            dos = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_2.png"));
            tres = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_3.png"));
            cuatro = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_4.png"));
            cinco = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_5.png"));
            seis = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_6.png"));
            siete = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_7.png"));
            ocho = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_8.png"));
            nueve = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_9.png"));
            diez = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_10.png"));
            once = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_11.png"));
            doce = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_12.png"));
            trece = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_13.png"));
            catorce = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_14.png"));
            quince = ImageIO.read(getClass().getResourceAsStream("/bolas/Bola_15.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dibuja la representación visual de la bola en el contexto gráfico 2D.
     * <p>
     * Intenta dibujar la {@code BufferedImage} correspondiente al ID de la bola.
     * Si la imagen no se carga, dibuja un círculo simple con el color de fallback.
     * </p>
     *
     * @param g El contexto gráfico 2D donde se dibuja.
     */
    @Override
    public void draw(Graphics2D g) {
        BufferedImage image = null;

        switch (id) {
            case "blanca":
                image = blanca;
                break;
            case "uno":
                image = uno;
                break;
            case "dos":
                image = dos;
                break;
            case "tres":
                image = tres;
                break;
            case "cuatro":
                image = cuatro;
                break;
            case "cinco":
                image = cinco;
                break;
            case "seis":
                image = seis;
                break;
            case "siete":
                image = siete;
                break;
            case "ocho":
                image = ocho;
                break;
            case "nueve":
                image = nueve;
                break;
            case "diez":
                image = diez;
                break;
            case "once":
                image = once;
                break;
            case "doce":
                image = doce;
                break;
            case "trece":
                image = trece;
                break;
            case "catorce":
                image = catorce;
                break;
            case "quince":
                image = quince;
                break;
            default:
                int d = (int) Math.round(radio * 2);
                g.setColor(color);
                g.fillOval((int) (posicion.x - radio), (int) (posicion.y - radio), d, d);
                g.setColor(Color.BLACK);
                g.drawOval((int) Math.round(posicion.x - radio), (int) Math.round(posicion.y - radio), d, d);
                return;
        }
        if (image != null) {
            int d = (int) Math.round(radio * 2);
            int x = (int) Math.round(posicion.x - radio);
            int y = (int) Math.round(posicion.y - radio);
            g.drawImage(image, x, y, d, d, null);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, d, d);
        } else {
            // Si no se cargó la textura esperada, usa fallback
            int d = (int) Math.round(radio * 2);
            g.setColor(color);
            g.fillOval((int) (posicion.x - radio), (int) (posicion.y - radio), d, d);
            g.setColor(Color.BLACK);
            g.drawOval((int) Math.round(posicion.x - radio), (int) Math.round(posicion.y - radio), d, d);
        }
    }
}