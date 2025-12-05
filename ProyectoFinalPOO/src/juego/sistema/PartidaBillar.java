package juego.sistema;

import java.util.ArrayList;
import java.util.List;

import juego.entidades.Bola;
import juego.entidades.GrupoBola;
import juego.entidades.TipoBola;

/**
 * Clase central que gestiona la lógica y el estado de una partida de Billar
 * (Bola 8).
 * <p>
 * Se encarga de manejar los turnos de los {@link Jugador}es, la asignación de
 * grupos
 * (Lisas/Rayadas), el conteo de bolas restantes y la determinación del fin de
 * la partida.
 * </p>
 * <p>
 * Actúa como el observador principal (listener) de los eventos de embolsado de
 * bolas
 * provenientes del simulador.
 * </p>
 *
 * @version 1.0
 */
public class PartidaBillar {
    /** Primer jugador de la partida. */
    private final Jugador j1;
    /** Segundo jugador de la partida. */
    private final Jugador j2;
    /** Jugador que está realizando el tiro actual. */
    private Jugador jugadorActual;

    /** Conteo de bolas lisas (1-7) que aún están en la mesa. */
    private int lisasRestantes = 7;
    /** Conteo de bolas rayadas (1-7) que aún están en la mesa. */
    private int rayadasRestantes = 7;

    /** Indicador de si la partida ha finalizado. */
    private boolean partidaTerminada;

    /**
     * Lista temporal de bolas embolsadas legalmente o ilegalmente durante el tiro
     * actual.
     */
    private final List<Bola> bolasEnTroneraEnTurno = new ArrayList<>();

    /** Indica si la bola blanca fue embolsada en el tiro actual (falta). */
    private boolean blancaEnEsteTurno = false;

    /**
     * Crea la partida del billar, creando a los dos jugadores y estableciendo la
     * partida por default
     * Crea una nueva instancia de la partida de Billar.
     * <p>
     * Inicializa a los dos jugadores ("Jugador 1" y "Jugador 2") y resetea
     * el estado de la partida a los valores iniciales.
     * </p>
     */
    public PartidaBillar() {
        this.j1 = new Jugador("Jugador 1");
        this.j2 = new Jugador("Jugador 2");
        reiniciar();
    }

    /*
     * Reinicia completamente la partida:
     * - limpia grupos, bolas restantes y turno actual.
     */

    /**
     * Reinicia completamente el estado interno de la partida para empezar de cero.
     * <p>
     * Esto incluye: resetear el conteo de bolas, asignar el turno al Jugador 1,
     * y establecer los grupos de bolas a {@code GrupoBola.SIN_ASIGNAR}.
     * </p>
     */
    public void reiniciar() {
        lisasRestantes = 7;
        rayadasRestantes = 7;

        j1.setGrupo(GrupoBola.SIN_ASIGNAR);
        j2.setGrupo(GrupoBola.SIN_ASIGNAR);

        jugadorActual = j1;
        partidaTerminada = false;

        bolasEnTroneraEnTurno.clear();
        blancaEnEsteTurno = false;
    }

    // Getters y Setters
    public Jugador getJugador1() {
        return j1;
    }

    public Jugador getJugador2() {
        return j2;
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public boolean isPartidaTerminada() {
        return partidaTerminada;
    }

    public int getLisasRestantes() {
        return lisasRestantes;
    }

    public int getRayadasRestantes() {
        return rayadasRestantes;
    }

    public int getBolasRestantes(Jugador j) {
        if (!j.tieneGrupoAsignado()) {
            // Antes de asignar tipo, asumimos 7 como máximo de bolas por jugador
            return 7;
        }
        return (j.getGrupo() == GrupoBola.LISAS) ? lisasRestantes : rayadasRestantes;
    }

    // Metodos para GamePanel

    // Detecta cada vez que alguna bola entra en una tronera y actualiza el estado
    // de la partida

    /**
     * Registra una {@link Bola} que ha caído en una tronera durante el tiro actual.
     * <p>
     * Si la bola es la blanca, marca la falta. En caso contrario, la agrega a la
     * lista temporal de bolas embolsadas para su procesamiento posterior en
     * {@link #finDeTiro()}.
     * </p>
     *
     * @param bola La instancia de la Bola que ha sido embolsada.
     */
    public void registrarBolaEnTronera(Bola bola) {
        if (partidaTerminada)
            return;

        if ("blanca".equals(bola.getId()))
            blancaEnEsteTurno = true;
        else
            bolasEnTroneraEnTurno.add(bola);
    }

    /*
     * Lógica que se ejecuta al finalizar un tiro:
     * Decide si el jugador conserva el turno, si cambia de jugador
     * o si la partida termina (por meter la 8 antes o después de tiempo).
     */

    /**
     * Ejecuta la lógica completa de las reglas del juego al finalizar un tiro.
     * <p>
     * Se encarga de:
     * <ul>
     * <li>Asignar los grupos Lisas/Rayadas si es el primer tiro válido.</li>
     * <li>Descontar las bolas embolsadas.</li>
     * <li>Determinar si se ha cometido falta o si hay un ganador (Bola 8).</li>
     * <li>Decidir si el {@code jugadorActual} mantiene el turno o si hay un
     * cambio.</li>
     * </ul>
     * </p>
     *
     * @return El {@code Jugador} ganador si la partida termina, o {@code null} si
     *         la partida continúa.
     */
    public Jugador finDeTiro() {
        if (partidaTerminada)
            return null;

        boolean hayBolas = !bolasEnTroneraEnTurno.isEmpty();
        boolean metioOcho = false;
        boolean metioLisa = false;
        boolean metioRayada = false;

        for (Bola b : bolasEnTroneraEnTurno) {
            TipoBola tipo = b.getTipo();
            switch (tipo) {
                case OCHO:
                    metioOcho = true;
                    break;
                case LISA:
                    metioLisa = true;
                    break;
                case RAYADA:
                    metioRayada = true;
                    break;
                default:
                    break;
            }
        }

        // Asignacion inicial del grupo de los jugadores
        if (!j1.tieneGrupoAsignado() && (metioLisa || metioRayada)) {
            GrupoBola grupo = metioLisa ? GrupoBola.LISAS : GrupoBola.RAYADAS;
            setGrupos(jugadorActual, grupo);
        }

        // Contar bolas y detectar si fueron de su grupo o del contrario
        boolean metioGrupo = false;
        boolean metioContrario = false;

        for (Bola b : bolasEnTroneraEnTurno) {
            TipoBola tipo = b.getTipo();
            switch (tipo) {
                case LISA:
                    descontarBolaDeGrupo(GrupoBola.LISAS);
                    if (jugadorActual.getGrupo() == GrupoBola.LISAS)
                        metioGrupo = true;
                    else if (jugadorActual.getGrupo() == GrupoBola.RAYADAS)
                        metioContrario = true;
                    break;
                case RAYADA:
                    descontarBolaDeGrupo(GrupoBola.RAYADAS);
                    if (jugadorActual.getGrupo() == GrupoBola.RAYADAS)
                        metioGrupo = true;
                    else if (jugadorActual.getGrupo() == GrupoBola.LISAS)
                        metioContrario = true;
                case OCHO:
                    break;
                default:
                    break;
            }
        }

        if (metioOcho) {
            Jugador ganador;

            if (!jugadorActual.tieneGrupoAsignado()) {
                ganador = obtenerOponente(jugadorActual);
            } else {
                int restantesGrupo = (jugadorActual.getGrupo() == GrupoBola.LISAS) ? lisasRestantes : rayadasRestantes;
                if (restantesGrupo == 0 && !blancaEnEsteTurno)
                    ganador = jugadorActual;
                else
                    ganador = obtenerOponente(jugadorActual);
            }

            partidaTerminada = true;
            limpiarEstado();
            return ganador;
        }

        // Cambios de turno
        boolean cambiarTurno;

        if (!jugadorActual.tieneGrupoAsignado()) {
            // Antes de que se definan lisas/rayadas
            if (!hayBolas || blancaEnEsteTurno) {
                cambiarTurno = true; // No metió nada o metió blanca
            } else {
                cambiarTurno = false; // Metió alguna bola de número -> sigue
            }
        } else {
            // Después de asignar lisas/rayadas
            if (blancaEnEsteTurno) {
                cambiarTurno = true;
            } else if (metioContrario) {
                cambiarTurno = true;
            } else if (metioGrupo) {
                cambiarTurno = false;
            } else if (!hayBolas) {
                cambiarTurno = true;
            } else {
                cambiarTurno = true; // caso raro → forzamos cambio
            }
        }

        if (cambiarTurno) {
            jugadorActual = obtenerOponente(jugadorActual);
        }

        limpiarEstado();
        return null; // La partida continúa
    }

    // Limpia las variales internas para preparar el siguiente tiro
    /**
     * Limpia las variables internas que registran el estado del tiro actual
     * para preparar el siguiente.
     */
    private void limpiarEstado() {
        bolasEnTroneraEnTurno.clear();
        blancaEnEsteTurno = false;
    }

    // Asigna el grupo(lisas o rayadas) a un jugador, despues de meter su priemra
    // bola
    /**
     * Asigna un grupo de bolas (Lisas o Rayadas) al jugador que acaba de realizar
     * un tiro, y asigna el grupo opuesto a su oponente.
     *
     * @param jugador El jugador al que se le asignará el grupo.
     * @param grupo   El {@link GrupoBola} (LISAS o RAYADAS) a asignar.
     */
    private void setGrupos(Jugador jugador, GrupoBola grupo) {
        jugador.setGrupo(grupo);
        Jugador otro = obtenerOponente(jugador);
        otro.setGrupo(grupo == GrupoBola.LISAS ? GrupoBola.RAYADAS : GrupoBola.LISAS);
    }

    /**
     * Devuelve la instancia del jugador oponente.
     *
     * @param jugador El jugador del cual se desea obtener el oponente.
     * @return El {@code Jugador} oponente.
     */
    private Jugador obtenerOponente(Jugador jugador) {
        return (jugador == j1) ? j2 : j1;
    }

    /**
     *  Resta una bola al grupo indicado en caso de haber metido una bola
     * 
     * @param grupo El {@link GrupoBola} (LISAS o RAYADAS) del cual se debe descontar una bola.
     */
    private void descontarBolaDeGrupo(GrupoBola grupo) {
        if (grupo == GrupoBola.LISAS && lisasRestantes > 0) {
            lisasRestantes--;
        } else if (grupo == GrupoBola.RAYADAS && rayadasRestantes > 0) {
            rayadasRestantes--;
        }
    }
}
