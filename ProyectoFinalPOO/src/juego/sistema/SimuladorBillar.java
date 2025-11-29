package juego.sistema;

import java.util.ArrayList;
import java.util.List;

import juego.entidades.Bola;
import juego.entidades.Mesa;
import juego.entidades.Tronera;
import juego.eventos.EventoTroneraListener;
import libreria.colision_basica.*;
import libreria.dinamica.Cuerpo;
import libreria.interfaces.Integrador;
import libreria.matematicas.Vec2D;

/**
 * Gestiona el mundo físico específico para una partida de billar.
 * Contiene y gestiona todos los cuerpos de la mesa de billar.
 * <p>
 * Esta clase orquesta la simulación, integrando los cuerpos (bolas),
 * detectando colisiones, resolviéndolas y aplicando las reglas del juego
 * (como la detección de troneras y el manejo de la bola blanca).
 * </p>
 *
 * 
 * @version 1.0
 */
public class SimuladorBillar {
    /** Lista de todos los Cuerpos (bolas y mesa) activos en la simulación. */
    private final List<Cuerpo> cuerpos = new ArrayList<>();
    /**
     * Objeto que implementa la lógica de integración del movimiento (delegando a
     * Cuerpo.actualizar).
     */
    private final Integrador integrador = (obj, dt) -> obj.actualizar(dt);
    /** Referencia a la mesa de billar para sus dimensiones y troneras. */
    private Mesa mesaBillar;
    /** Listener para notificar eventos de bola embolsada al sistema de juego. */
    private EventoTroneraListener eventoTroneraListener;

    /**
     * Establece la mesa principal del simulador y la registra como un cuerpo en el
     * mundo.
     *
     * @param m La instancia de Mesa de Billar.
     */
    public void setMesaBillar(Mesa m) {
        this.mesaBillar = m;
        agregarCuerpo(m);
    }

    /**
     * Agrega un cuerpo físico (generalmente una Bola) al simulador para que sea
     * considerado en la simulación.
     *
     * @param c El Cuerpo a agregar.
     */
    public void agregarCuerpo(Cuerpo c) {
        cuerpos.add(c);
    }
    /**
     * Devuelve una copia de la lista de cuerpos para evitar modificaciones externas directas.
     * @return Lista de cuerpos
     */
    public List<Cuerpo> getCuerpos() {
        return new java.util.ArrayList<>(cuerpos);
    }

    /**
     * Establece el oyente de eventos para la detección de bolas en troneras.
     *
     * @param listener La implementación de EventoTroneraListener.
     */
    public void setEventoTroneraListener(EventoTroneraListener listener) {
        this.eventoTroneraListener = listener;
    }

    /**
     * Itera sobre todos los cuerpos y detecta si alguna Bola ha entrado en alguna Tronera.
     * Aplica la lógica de juego: si es bola normal, se elimina; si es bola blanca, se reposiciona.
     */
    public void detectarTroneras() {
        List<Cuerpo> eliminar = new ArrayList<>();
        for (Cuerpo c : cuerpos) {
            if (!(c instanceof Bola bola))
                continue;
            for (Tronera t : mesaBillar.getAgujeros()) {
                if (t.contieneBola(bola)) {

                    // Se noticia al juego que la bola cayó en una tronera
                    if (eventoTroneraListener != null) {
                        eventoTroneraListener.bolaEmbolsada(bola);
                    }

                    if ("blanca".equals(bola.getId())) {
                        // Posición "nueva" de la bola blanca
                        Vec2D reinicioBolaBlanca = Vec2D.crearVector(260, 576 / 2.0);
                        bola.setPosicion(reinicioBolaBlanca);
                        bola.setVel(Vec2D.crearVectorNulo());
                    } else {
                        /// Se agrega a la lista las bolas que entraron en alguna tronera
                        eliminar.add(bola);
                    }
                    break;
                }
            }
        }
        // En caso de que la lista no este vacía, eliminará del mundo todas las bolas en
        // la lista
        if (!eliminar.isEmpty())
            cuerpos.removeAll(eliminar);
    }

    /**
     * Avanza la simulación física de la partida de billar una cantidad de tiempo dada.
     * <p>
     * Pasos:
     * 1. Integración de movimiento.
     * 2. Detección y resolución de colisiones Bola vs Pared (Bordes).
     * 3. Detección y resolución de colisiones Bola vs Bola.
     * 4. Detección de bolas embolsadas (Troneras).
     * </p>
     *
     * @param deltaTime El intervalo de tiempo para el paso de simulación.
     */
    public void actualizarSimulacion(double deltaTime) {
        // 1) Integrar movimiento de todos los cuerpos según sus velocidades y fuerzas
        for (Cuerpo cuerpoSimulado : cuerpos) {
            integrador.integrar(cuerpoSimulado, deltaTime);
        }

        // 2) Detectar y resolver colisiones
        int cantidadCuerpos = cuerpos.size();

        for (int indiceCuerpoA = 0; indiceCuerpoA < cantidadCuerpos; indiceCuerpoA++) {
            Cuerpo cuerpoA = cuerpos.get(indiceCuerpoA);

            // Colisiones bola vs mesa (paredes)
            if (cuerpoA instanceof Bola bolaActual) {

                // Información de contacto entre la bola y la mesa
                ContactoColision contactoConMesa = new ContactoColision(bolaActual, mesaBillar);

                // Si hay colisión contra las bandas/llantas de la mesa, la resolvemos
                if (DetectorColisiones.circuloVsBordes(bolaActual, mesaBillar, contactoConMesa)) {
                    ResolverColisiones.resolver(contactoConMesa);
                }
            }

            for (int indiceCuerpoB = indiceCuerpoA + 1; indiceCuerpoB < cantidadCuerpos; indiceCuerpoB++) {
                Cuerpo cuerpoB = cuerpos.get(indiceCuerpoB);
                if (cuerpoA instanceof Bola bolaA && cuerpoB instanceof Bola bolaB) {
                    ContactoColision contactoEntreBolas = new ContactoColision(bolaA, bolaB);
                    if (DetectorColisiones.circuloVsCirculo(bolaA, bolaB, contactoEntreBolas)) {
                        ResolverColisiones.resolver(contactoEntreBolas);
                    }
                }
            }
        }
        // Si alguna bola llegará a entrar en alguna tronera esta función la quita de la
        // mesa
        detectarTroneras();
    }
}