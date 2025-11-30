package libreria.dinamica;

/**
 * Enumeración que define las propiedades físicas de diferentes materiales
 * utilizadas en el motor de simulación.
 * <p>
 * Cada material especifica el comportamiento de los cuerpos al colisionar
 * (restitución) y su resistencia al movimiento a través del tiempo
 * (amortiguamiento).
 * </p>
 *
 * 
 */
public enum Material {
    /** Material de Caucho (Rubber): Alta elasticidad y amortiguamiento moderado. */
    CAUCHO(0.96, 0.35),
    /** Material de Madera (Wood): Baja elasticidad y amortiguamiento alto. */
    MADERA(2, 0.5);

    /**
     * * Coeficiente de restitución (elasticidad).
     * <p>
     * Define la "cantidad" de rebote o la relación entre la velocidad de separación
     * y la velocidad de aproximación de los cuerpos tras un impacto.
     * Un valor de 1.0 es perfectamente elástico, 0.0 es perfectamente inelástico.
     * </p>
     */
    public final double restitucion;

    /**
     * * Factor de amortiguamiento (Damping).
     * <p>
     * Simula la resistencia del medio (fricción con el aire, la mesa, etc.) para
     * que
     * los cuerpos pierdan energía y frenen gradualmente.
     * </p>
     */
    public final double amortiguamiento;

    /**
     * Constructor para inicializar las propiedades del material.
     *
     * @param r Coeficiente de restitución (0.0 a 1.0).
     * @param a Coeficiente de amortiguamiento (damping).
     */
    Material(double r, double a) {
        this.restitucion = r;
        this.amortiguamiento = a;
    }

    /**
     * Obtiene el coeficiente de restitución del material.
     *
     * @return El valor de restitución.
     */
    public double getRestitucion() {
        return restitucion;
    }

    /**
     * Obtiene el coeficiente de amortiguamiento del material.
     *
     * @return El valor de amortiguamiento.
     */
    public double getDamping() {
        return amortiguamiento;
    }
}
