package libreria.dinamica;

import libreria.interfaces.Actualizable;
import libreria.matematicas.Vec2D;

/**
 * Clase abstracta que representa un cuerpo físico genérico en la simulación 2D.
 * <p>
 * Un cuerpo posee propiedades dinámicas como masa, posición, velocidad y
 * fuerzas
 * acumuladas. También define métodos abstractos para el dibujo y la integración
 * del movimiento en el mundo físico.
 * </p>
 *
 * @author Eduardo Villar
 * @version 1.0
 */

public abstract class Cuerpo implements Cloneable, Actualizable, AplicarFuerza {
    /** Identificador único del cuerpo. */
    protected final String id;
    /** Tipo de objeto físico (Dinámico o Estático). */
    protected TipoDeObjeto tipo = TipoDeObjeto.DINAMICO;

    /** Masa del cuerpo (kg). */
    public double masa;

    /** Inverso de la masa (1/masa), usado en cálculos de impulso y fuerza. */
    public double inversoMasa;

    /** Coeficiente de restitución (elasticidad) del material (0.0 a 1.0). */
    protected double restitucion = 0.8;

    /** Factor de amortiguamiento lineal (resistencia del medio). */
    protected double amortiguamiento = 0.02;

    /** Vector de posición actual del cuerpo (m). */
    public Vec2D posicion = Vec2D.crearVectorNulo();
    /** Vector de velocidad actual del cuerpo (m/s). */
    public Vec2D velocidad = Vec2D.crearVectorNulo();
    /** Vector de aceleración actual del cuerpo (m/s²). */
    public Vec2D aceleracion = Vec2D.crearVectorNulo();
    /**
     * Vector de fuerza neta acumulada en el cuerpo para el siguiente paso de
     * actualización (N).
     */
    public Vec2D fuerza = Vec2D.crearVectorNulo();

    /**
     * Constructor del cuerpo.
     *
     * @param id   Identificador único.
     * @param masa Masa del cuerpo en kilogramos.
     */

    protected Cuerpo(String id, double masa) {
        this.id = id;
        this.masa = masa;
        this.inversoMasa = 1.0 / masa;
    }

    /**
     * Método abstracto que obliga a las subclases a especificar cómo se deben
     * dibujar.
     *
     * @param g2 Contexto gráfico 2D para el dibujo.
     */
    public abstract void draw(java.awt.Graphics2D g2);

    /**
     * Asigna las propiedades físicas (restitución y amortiguamiento) del cuerpo
     * según un material predefinido.
     *
     * @param m El material a asignar.
     */
    public void setMaterial(Material m) {
        this.restitucion = m.restitucion;
        this.amortiguamiento = m.amortiguamiento;
    }

    /**
     * Cambia el tipo de objeto físico (dinámico/estático).
     * Si se establece como estático, su masa se vuelve infinita y no se ve afectado por fuerzas.
     *
     * @param t El nuevo tipo de objeto físico.
     */
    public void setTipo(TipoDeObjeto t) {
        this.tipo = t;
        if (t == TipoDeObjeto.ESTATICO) {
            this.masa = Double.POSITIVE_INFINITY;
            this.inversoMasa = 0.0;
        }
    }

   /**
     * Suma una fuerza externa a las fuerzas acumuladas del cuerpo para el siguiente paso de simulación.
     *
     * @param f El vector de fuerza a aplicar (N).
     */
    @Override
    public void aplicarFuerza(Vec2D f) {
        fuerza = fuerza.sumaVectores(f);
    }

    /**
     * Integra el movimiento del cuerpo en un intervalo de tiempo dado (paso de simulación).
     * <p>
     * Este método calcula la nueva aceleración, velocidad y posición
     * utilizando el integrador de Euler simple, y aplica amortiguamiento.
     * </p>
     *
     * @param dt El delta de tiempo (intervalo de tiempo) para la integración (segundos).
     */
    @Override
    public void actualizar(double dt) {
        if (tipo == TipoDeObjeto.ESTATICO)
            return;
        // Aceleración = (fuerza acumulada / masa) + gravedad global.
        aceleracion = fuerza.escalarXVector(inversoMasa).sumaVectores(Config.GRAVEDAD);
        // Actualizar velocidad con la aceleración calculada.
        velocidad = velocidad.sumaVectores(aceleracion.escalarXVector(dt));

        // Aplicar amortiguamiento para simular pérdida de energía.
        double factor = 1.0 - amortiguamiento * dt;
        if (factor < 0)
            factor = 0;

        velocidad = velocidad.escalarXVector(factor);

        // Limitar la velocidad máxima para evitar comportamientos inestables.
        double magnitudVelocidad = velocidad.magnitudVector();
        if (magnitudVelocidad > Config.MAX_VELOCIDAD)
            velocidad = velocidad.escalarXVector(Config.MAX_VELOCIDAD / magnitudVelocidad);

        // Actualizar posición en función de la velocidad resultante.
        posicion = posicion.sumaVectores(velocidad.escalarXVector(dt));

        // Limpiar las fuerzas acumuladas para el siguiente ciclo.
        fuerza = Vec2D.crearVectorNulo();
    }

    /**
     * Aplica un impulso instantáneo al cuerpo, modificando directamente su velocidad.
     * Solo tiene efecto si el cuerpo es dinámico.
     *
     * @param j El vector de impulso a aplicar.
     */
    public void aplicarImpulso(Vec2D j) {
        if (tipo == TipoDeObjeto.DINAMICO)
            velocidad = velocidad.sumaVectores(j.escalarXVector(inversoMasa));
    }

    /**
     * Ajusta manualmente la posición del cuerpo.
     * Solo se aplica a cuerpos dinámicos (usado típicamente para correcciones post-colisión).
     *
     * @param desplazamiento El vector de desplazamiento a aplicar.
     */
    public void mover(Vec2D desplazamiento) {
        if (tipo == TipoDeObjeto.DINAMICO) {
            posicion = posicion.sumaVectores(desplazamiento);
        }
    }

    /** Getters y Setters de los atributos de la clase */
    public String getId() {
        return id;
    }

    public double getInvMasa() {
        return (tipo == TipoDeObjeto.ESTATICO) ? 0.0 : inversoMasa;
    }

    public double getRestitucion() {
        return restitucion;
    }

    public Vec2D getPosicion() {
        return posicion;
    }

    public Vec2D getVel() {
        return velocidad;
    }

    public void setVel(Vec2D v) {
        this.velocidad = v;
    }

    public void setPosicion(Vec2D p) {
        this.posicion = p;
    }

    @Override
    public String toString() {
        return id + " posicion=" + posicion + " velocidad=" + velocidad;
    }
}