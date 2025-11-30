package libreria.matematicas;

import java.util.Objects;

/**
 * Representa un vector bidimensional (2D) con componentes {@code x} e
 * {@code y}.
 * <p>
 * Esta clase es fundamental para el motor de física, modelando posición,
 * velocidad, aceleración y fuerza. Los vectores son **inmutables**,
 * lo que significa que todas las operaciones matemáticas devuelven una
 * nueva instancia de {@code Vec2D}, garantizando la seguridad en el manejo
 * de la física.
 * </p>
 *
 * 
 * @version 1.0
 */
public class Vec2D {

    /** Componente X del vector. */
    public final double x;

    /** Componente Y del vector. */
    public final double y;

    /**
     * Constructor privado de la clase.
     * La creación de instancias se realiza exclusivamente a través de los métodos
     * estáticos {@code crearVector} y {@code crearVectorNulo}.
     *
     * @param x Componente X.
     * @param y Componente Y.
     */
    private Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Crea y devuelve un nuevo vector a partir de sus componentes especificadas.
     * Este es el método para instanciar {@code Vec2D}.
     *
     * @param x Componente X.
     * @param y Componente Y.
     * @return Una nueva instancia de Vec2D con las componentes dadas.
     */
    public static Vec2D crearVector(double x, double y) {
        return new Vec2D(x, y);
    }

    /**
     * Crea y devuelve un vector nulo (0, 0).
     *
     * @return Una nueva instancia de Vec2D con componentes (0, 0).
     */
    public static Vec2D crearVectorNulo() {
        return crearVector(0, 0);
    }

    /**
     * Realiza la suma vectorial entre el vector actual (this) y el vector
     * {@code b}.
     *
     * @param b El vector a sumar.
     * @return Un nuevo vector resultante de {@code this + b}.
     */
    public Vec2D sumaVectores(Vec2D b) {
        return crearVector(this.x + b.x, this.y + b.y);
    }

    /**
     * Realiza la resta vectorial entre el vector actual (this) y el vector
     * {@code b}.
     *
     * @param b El vector a restar (sustraendo).
     * @return Un nuevo vector resultante de {@code this - b}.
     */
    public Vec2D restaVectores(Vec2D b) {
        return crearVector(this.x - b.x, this.y - b.y);
    }

    /**
     * Realiza la multiplicación de un escalar {@code k} por el vector actual.
     *
     * @param k El valor escalar por el que se multiplica.
     * @return Un nuevo vector resultante de {@code this * k}.
     */
    public Vec2D escalarXVector(double k) {
        return crearVector(this.x * k, this.y * k);
    }

    /**
     * Realiza el producto punto entre el vector actual y el vector
     * {@code b}.
     *
     * @param b El segundo vector.
     * @return El valor escalar del producto punto: $x_1 x_2 + y_1 y_2$.
     */
    public double productoPunto(Vec2D b) {
        return (this.x * b.x) + (this.y * b.y);
    }
    /**
     * Calcula la magnitud (longitud) del vector.
     * <p>
     * Utiliza el teorema de Pitágoras: $\sqrt{x^2 + y^2}$.
     * </p>
     *
     * @return La magnitud del vector.
     */
    public double magnitudVector() {
        return Math.hypot(x, y);
    }

    /**
     * Calcula y devuelve el vector unitario (normalizado).
     * <p>
     * Si la magnitud del vector es cero, devuelve un vector nulo para evitar la división por cero.
     * </p>
     *
     * @return Un nuevo vector con magnitud 1.0 en la misma dirección, o un vector nulo.
     */
    public Vec2D vectorUnitario() {
        double magnitudActual = magnitudVector();
        return magnitudActual == 0 ? crearVectorNulo() : crearVector(x / magnitudActual, y / magnitudActual);
    }

    // Métodos sobreescritos de object

    /**
     * Devuelve un clon (copia exacta) del vector actual.
     *
     * @return Un nuevo objeto Vec2D con los mismos valores de x e y.
     */
    @Override
    public Vec2D clone() {
        return crearVector(x, y);
    }

    /**
     * Compara el vector actual con otro objeto para determinar si son iguales.
     * <p>
     * La comparación utiliza una tolerancia pequeña (epsilon $10^{-9}$) para manejar la imprecisión
     * de los números de punto flotante ({@code double}).
     * </p>
     *
     * @param o El objeto a comparar.
     * @return {@code true} si el objeto es un {@code Vec2D} con componentes iguales dentro de la tolerancia.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vec2D vector))
            return false;

        return Math.abs(vector.x - x) < 1e-9 &&
                Math.abs(vector.y - y) < 1e-9;
    }

    /**
     * Genera un código hash para el vector, necesario para colecciones basadas en hash.
     * <p>
     * Las componentes son escaladas y redondeadas antes de calcular el hash para
     * mantener la consistencia con el método {@code equals} que usa una tolerancia.
     * </p>
     *
     * @return Un valor de código hash consistente.
     */
    @Override
    public int hashCode() {
        long xRedondeada = Math.round(x * 1e9);
        long yRedondeada = Math.round(y * 1e9);
        return Objects.hash(xRedondeada, yRedondeada);
    }

    /**
     * Devuelve una representación en cadena del vector en el formato (x,y).
     *
     * @return La representación del vector como String.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}