package libreria.dinamica;

import libreria.matematicas.Vec2D;

/**
 * Clase de utilidad que contiene todas las constantes de configuración global
 * para la simulación física (motor de la librería).
 * <p>
 * Los valores aquí definidos controlan la estabilidad, precisión y el
 * comportamiento
 * dinámico de los cuerpos en el mundo físico.
 * </p>
 *
 * 
 * @version 1.0
 */
public class Config {
    /**
     * Constructor privado para evitar la instanciación de esta clase de constantes.
     */
    private Config() {

    }

    /**
     * * El delta de tiempo fijo para cada paso de actualización de la física (s).
     * <p>
     * Un valor de 1/60.0 asegura que la física se actualice 60 veces por segundo,
     * ayudando a la estabilidad de la simulación.
     * </p>
     */
    public static final double DELTA_TIME = 1.0 / 60.0;

    /**
     * Umbral de velocidad por debajo del cual un cuerpo se considera "en reposo"
     * o detenido, permitiendo que la simulación lo ponga a dormir para ahorrar
     * recursos.
     * (Unidades: metros/segundo).
     */
    public static final double VELOCIDAD_REPOSO = 4.0;

    /**
     * Velocidad lineal máxima permitida para cualquier cuerpo.
     * Sirve para evitar inestabilidades numéricas (como la "explosión") o
     * movimientos
     * irreales causados por fuerzas extremas o errores de integración.
     * (Unidades: metros/segundo).
     */
    public static final double MAX_VELOCIDAD = 800.0;

    /**
     * Porcentaje de corrección posicional aplicado para separar cuerpos tras una
     * colisión.
     * <p>
     * Un valor de 0.8 (80%) corrige la penetración sin ser demasiado agresivo.
     * Debe estar entre 0.0 y 1.0.
     * </p>
     */
    public static final double PORCENTAJE_CORRECCION_PENETRACION = 0.8;

    /**
     * Tolerancia mínima de penetración (traslape) permitida (metros).
     * <p>
     * Si la penetración es menor a este umbral, no se aplica corrección posicional.
     * Esto evita microcorrecciones constantes e inestabilidad por errores de punto
     * flotante.
     * </p>
     */
    public static final double TOLERANCIA_PENETRACION = 0.01;

    /**
     * Vector de gravedad global aplicado a todos los cuerpos dinámicos.
     * <p>
     * Se establece en un vector nulo (0, 0) para simular un ambiente sin gravedad,
     * típico en juegos de billar o vista superior.
     * </p>
     */
    public static final Vec2D GRAVEDAD = Vec2D.crearVectorNulo();
}
