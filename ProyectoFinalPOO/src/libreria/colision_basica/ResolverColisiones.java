package libreria.colision_basica;

import libreria.dinamica.*;
import libreria.matematicas.Vec2D;

/**
 * Clase estática encargada de resolver una colisión aplicando los principios
 * de conservación de momento y corrigiendo la penetración geométrica.
 * <p>
 * Utiliza la información de un {@code ContactoColision} para calcular y aplicar
 * los impulsos necesarios para cambiar las velocidades y la corrección de
 * posición para separar los cuerpos tras el contacto.
 * </p>
 *
 * 
 * @version 1.0
 */
public class ResolverColisiones {
    /**
     * Resuelve una colisión aplicando el impulso y corrigiendo la penetración (traslape)
     * entre los dos cuerpos involucrados.
     * <p>
     * El proceso consta de dos partes principales:
     * 1. **Corrección del Impulso:** Modifica la velocidad de los cuerpos
     * basándose en la velocidad relativa, el coeficiente de restitución
     * (elasticidad) y sus masas.
     * 2. **Corrección Posicional:** Mueve los cuerpos para eliminar el traslape
     * geométrico, evitando que se peguen o atraviesen.
     * </p>
     *
     * @param m El objeto {@code ContactoColision} que contiene toda la información de contacto.
     */
    public static void resolver(ContactoColision m){
        Cuerpo A = m.A, B = m.B; // Cuerpos involucrados en la colisión
        
        // Evita resolver si se separan
        if (m.proyVelocidadRelEnNormal > 0) return;

        // Inversos de masa (si un cuerpo es estático, su inverso de masa será 0).
        double inversoMasaA = A.getInvMasa();
        double inversoMasaB = B.getInvMasa();
        // Si la suma es cero, no hay masa móvil que ajustar (colisión inválida o solo cuerpos estáticos).
        double denom = inversoMasaA + inversoMasaB;
        if (denom <= 0) return;

        // Coeficiente de restitución efectivo
        // Se toma el mínimo de ambos para evitar rebotes exagerados.
        double e = Math.min(A.getRestitucion(), B.getRestitucion());
        //Impulso Escalar
        double impulsoEscalar = -(1.0 + e) * m.proyVelocidadRelEnNormal / denom;
        // Vector de impulso
        Vec2D impulso = m.vectorNormal.escalarXVector(impulsoEscalar);

        A.aplicarImpulso(impulso.escalarXVector(-1));
        B.aplicarImpulso(impulso);

        // Corrección posicional, para que las bolas se mantengan dentro de la mesa
        double magnitudCoreccion = Math.max(m.traslape - Config.TOLERANCIA_PENETRACION, 0.0)
                         * Config.PORCENTAJE_CORRECCION_PENETRACION / denom;
        // Vector de corrección en dirección de la normal.
        Vec2D correction = m.vectorNormal.escalarXVector(magnitudCoreccion);
        
        // Ajuste de posiciones proporcional a sus masas
        A.mover(correction.escalarXVector(-1));
        B.mover(correction);
    }
}