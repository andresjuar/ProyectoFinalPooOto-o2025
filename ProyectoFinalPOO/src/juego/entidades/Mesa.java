package juego.entidades;

import java.awt.*;
import java.util.ArrayList;

import libreria.dinamica.Cuerpo;
import libreria.dinamica.TipoDeObjeto;
import libreria.interfaces.BordesDelimitados;
import libreria.matematicas.Vec2D;

/**
 * Representa la mesa de billar completa, sirviendo como el entorno estático del
 * juego.
 * <p>
 * {@code Mesa} extiende {@link libreria.dinamica.Cuerpo} para ser un objeto
 * físico
 * (estático) en la simulación, y además implementa
 * {@link libreria.interfaces.BordesDelimitados}
 * para permitir que el motor de colisiones detecte los choques contra las
 * bandas.
 * También gestiona la creación y el estado de las
 * {@link juego.entidades.Tronera}.
 * </p>
 *
 */
public class Mesa extends Cuerpo implements BordesDelimitados {
    /** Ancho físico de la mesa (dimensión X). */
    private final double ancho;
    /** Alto físico de la mesa (dimensión Y). */
    private final double alto;
    /** Colección de las seis troneras presentes en la mesa. */
    private final ArrayList<Tronera> troneras = new ArrayList<>();

    // Este método devuelve la lista de troneras creadas en la mesa
    /**
     * Devuelve la lista de troneras (agujeros) creadas y posicionadas en la mesa.
     *
     * @return Una lista de objetos {@code Tronera}.
     */
    public ArrayList<Tronera> getAgujeros() {
        return troneras;
    }

    // Constrcutor de Mesa
    /**
     * Constructor de la Mesa.
     *
     * @param id   Identificador de la mesa.
     * @param masa Masa de la mesa (es infinita ya que es estática).
     * @param w    Ancho físico de la mesa.
     * @param h    Alto físico de la mesa.
     * @param pos  Posición de la esquina superior izquierda de la mesa.
     */
    public Mesa(String id, double masa, double w, double h, Vec2D pos) {
        super(id, masa);
        this.ancho = w;
        this.alto = h;
        this.posicion = pos;
        setTipo(TipoDeObjeto.ESTATICO); // paredes/mesa
    }

    // Crea las troneras en la mesa de billar:
    // - Limpiando en caso de haber una configuración anterior
    /**
     * Crea y posiciona las seis troneras estándar de una mesa de billar (cuatro en
     * las esquinas y dos en el medio).
     * <p>
     * Las troneras se posicionan justo dentro de los bordes de la mesa.
     * </p>
     *
     * @param radio El radio de las troneras, generalmente basado en el radio de la
     *              bola.
     */
    public void crearMesaBillar(double radio) {
        troneras.clear();
        double izquierda = getBordeIzquierdo(), derecha = getBordeDerecho(),
                arriba = getBordeSuperior(), abajo = getBordeInferior();
        double medio = (izquierda + derecha) / 2.0;
        double k = radio - 1.0;

        // Genera las troneras de las esquinas de la mesa
        troneras.add(new Tronera(Vec2D.crearVector(izquierda + k, abajo - k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(izquierda + k, arriba + k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(derecha - k, arriba + k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(derecha - k, abajo - k), radio));

        // Genera las dos troneras que existen en el medio
        troneras.add(new Tronera(Vec2D.crearVector(medio, arriba + k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(medio, abajo - k), radio));

    }

    // Dibuja el contorno de la mesa y sus respectivas troneras
    /**
     * Dibuja el contorno de la mesa y sus respectivas troneras.
     *
     * @param g El contexto gráfico 2D.
     */
    @Override
    public void draw(Graphics2D g) {
        g.drawRect((int) posicion.x, (int) posicion.y, (int) ancho, (int) alto);

        for (Tronera t : troneras) {
            t.dibujarTronera(g);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve la coordenada X del borde izquierdo de la mesa.
     * </p>
     */
    @Override
    public double getBordeIzquierdo() {
        return posicion.x;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve la coordenada X del borde derecho de la mesa.
     * </p>
     */
    @Override
    public double getBordeDerecho() {
        return posicion.x + ancho;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve la coordenada Y del borde superior de la mesa.
     * </p>
     */
    @Override
    public double getBordeSuperior() {
        return posicion.y;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve la coordenada Y del borde inferior de la mesa.
     * </p>
     */
    @Override
    public double getBordeInferior() {
        return posicion.y + alto;
    }
}