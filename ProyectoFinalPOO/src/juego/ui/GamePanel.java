package juego.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import juego.entidades.Bola;
import juego.entidades.GrupoBola;
import juego.entidades.Mesa;
import juego.entidades.Taco;
import juego.entidades.Tronera;
import juego.sistema.Jugador;
import juego.sistema.SimuladorBillar;
import juego.sistema.PartidaBillar;
import libreria.dinamica.*;
import libreria.matematicas.Vec2D;

/**
 * Panel principal del juego, actúa como el controlador de la aplicación
 * y el lienzo de dibujo.
 * <p>
 * Implementa {@code Runnable} para gestionar el bucle principal del juego
 * (Game Loop) que se encarga de la actualización de la física y el repintado de
 * la UI.
 * </p>
 * <p>
 * Esta clase inicializa y orquesta: el {@link SimuladorBillar} (motor físico),
 * la {@link PartidaBillar} (lógica de reglas) y el manejo de la entrada
 * a través del {@link MouseHandler}.
 * </p>
 *
 * @version 1.0
 */
public class GamePanel extends JPanel implements Runnable {
    // CONFIGURACIÓN DE PANTALLA

    final int originalTitleSize = 16;
    final int scale = 3;

    public final int titleSize = originalTitleSize * scale; // 48*48
    final int maxScreenCol = 18;
    final int maxScreenRow = 12;

    /** Ancho total de la pantalla del juego en píxeles. */
    final int screenWidth = titleSize * maxScreenCol; // 864 pixeles
    /** Altura total de la pantalla del juego en píxeles. */
    final int screenHeight = titleSize * maxScreenRow; // 576 pixeles

    /** Frecuencia de actualización deseada (Frames Per Second). */
    int FPS = 60;
    /** Hilo en el que corre el bucle principal del juego. */
    Thread gameThread; // Hilo en el que corre el bucle principal del juego.

    // Motor & mundo
    /** Instancia del motor físico/simulador de billar. */
    SimuladorBillar mundo = new SimuladorBillar();
    /** Referencia a la bola blanca para la interacción con el {@code Taco}. */
    Bola bolaBlanca;
    /** Instancia de la mesa de billar. */
    Mesa mesa;

    /** Instancia de la lógica de reglas de la partida. */
    PartidaBillar partida = new PartidaBillar();
    /** Estado del turno anterior: {@code true} si las bolas estaban detenidas. */
    boolean antesQuietas = true;

    /** Manejador de eventos del mouse para la interacción con el usuario. */
    final MouseHandler mouse = new MouseHandler();
    /** Instancia del taco para aplicar el impulso a la bola blanca. */
    Taco taco;

    /**
     * Constructor del panel de juego.
     * <p>
     * Configura tamaño, color y registro de los listeners del mouse
     * </p>
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black); // Color de fondo del panel
        this.setDoubleBuffered(true);

        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.setFocusable(true); // Permite recibir eventos de teclado si se usan.

        inicializarMundo();
        conectarEventosTronera();

    }

    /**
     * Inicializa el mundo físico, creando y posicionando la {@link Mesa},
     * todas las {@link Bola}s, y el {@link Taco}.
     * <p>
     * También configura los materiales físicos y agrega los cuerpos al
     * {@code SimuladorBillar}.
     * </p>
     */
    public void inicializarMundo() {
        int margenExterior = 20;
        int anchoPanelInfo = 140;

        double xMesa = margenExterior + anchoPanelInfo;
        double yMesa = margenExterior;

        double anchoMesa = screenWidth - xMesa - margenExterior;
        double altoMesa = screenHeight - 2 * margenExterior;

        // Crear mesa centrada con un margen alrededor.
        mesa = new Mesa("mesa", 1, anchoMesa, altoMesa, Vec2D.crearVector(xMesa, yMesa));
        mesa.crearMesaBillar(16); // Crea las troneras
        mundo.setMesaBillar(mesa); // Agrega la mesa al mundo

        // Configuración de la bola blanca
        double masaBola = 0.17; // De acuerdo con la escala
        double radio = 12; // pixeles en pantalla

        // Creación de la bola blanca
        bolaBlanca = new Bola("blanca", masaBola, radio, Vec2D.crearVector(xMesa + 100, screenHeight / 2.0));
        bolaBlanca.setMaterial(Material.CAUCHO);
        bolaBlanca.setColor(Color.WHITE);
        mundo.agregarCuerpo(bolaBlanca);

        // Creación de las demas bolas para el juego
        Bola bolaUno, bolaDos, bolaTres, bolaCuatro, bolaCinco, bolaSeis, bolaSiete;
        Bola bolaOcho;
        Bola bolaNueve, bolaDiez, bolaOnce, bolaDoce, bolaTrece, bolaCatorce, bolaQuince;

        // Acomodo e inicialización de bolas
        double inicioTrianguloX = mesa.getBordeDerecho() - 200;
        double inicioTrianguloY = screenHeight / 2.0;
        for (int fila = 0; fila < 5; fila++) {
            for (int j = 0; j <= fila; j++) {
                double x = inicioTrianguloX + fila * (radio * 2 + 1);
                double y = inicioTrianguloY - (fila * radio) + j * (radio * 2 + 1);
                if (fila == 0) {
                    bolaUno = new Bola("uno", masaBola, radio, Vec2D.crearVector(x, y));
                    bolaUno.setMaterial(Material.CAUCHO);
                    mundo.agregarCuerpo(bolaUno);
                } else if (fila == 1) {
                    if (j == 0) {
                        bolaDos = new Bola("dos", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaDos.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaDos);
                    } else if (j == 1) {
                        bolaTres = new Bola("tres", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaTres.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaTres);
                    }
                } else if (fila == 2) {
                    if (j == 0) {
                        bolaCuatro = new Bola("cuatro", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaCuatro.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaCuatro);
                    } else if (j == 1) {
                        bolaOcho = new Bola("ocho", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaOcho.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaOcho);
                    } else if (j == 2) {
                        bolaCinco = new Bola("cinco", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaCinco.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaCinco);
                    }
                } else if (fila == 3) {
                    if (j == 0) {
                        bolaSeis = new Bola("seis", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaSeis.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaSeis);
                    } else if (j == 1) {
                        bolaSiete = new Bola("siete", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaSiete.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaSiete);
                    } else if (j == 2) {
                        bolaNueve = new Bola("nueve", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaNueve.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaNueve);
                    } else if (j == 3) {
                        bolaDiez = new Bola("diez", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaDiez.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaDiez);
                    }
                } else if (fila == 4) {
                    if (j == 0) {
                        bolaOnce = new Bola("once", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaOnce.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaOnce);
                    } else if (j == 1) {
                        bolaDoce = new Bola("doce", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaDoce.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaDoce);
                    } else if (j == 2) {
                        bolaTrece = new Bola("trece", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaTrece.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaTrece);
                    } else if (j == 3) {
                        bolaCatorce = new Bola("catorce", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaCatorce.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaCatorce);
                    } else if (j == 4) {
                        bolaQuince = new Bola("quince", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaQuince.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaQuince);
                    }

                }
            }
        }
        // Crea el taco
        taco = new Taco(bolaBlanca, Material.MADERA);
    }

    /**
     * Conecta el evento de bola embolsada del {@link SimuladorBillar} con
     * el método de registro de la {@link PartidaBillar}.
     */
    private void conectarEventosTronera() {
        mundo.setEventoTroneraListener(bola -> onBolaEmbolsada(bola));
    }

    /**
     * Manejador de eventos llamado cuando una bola es embolsada.
     * <p>
     * Delega la lógica de las reglas a la {@link PartidaBillar}.
     * </p>
     *
     * @param bola La {@link Bola} que ha caído en una tronera.
     */
    private void onBolaEmbolsada(Bola bola) {
        partida.registrarBolaEnTronera(bola);
    }

    /**
     * Inicia el hilo principal del juego, que ejecuta el bucle {@code run()}.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Bucle principal del juego (Game Loop).
     * <p>
     * Gestiona la sincronización de los frames y llama a {@code update()} y
     * {@code repaint()} a la velocidad definida por {@code FPS}.
     * </p>
     */
    @Override
    public void run() {

        double drawInterval = 1_000_000_000.0 / FPS; // Duración del frame en nanosegundos
        double delta = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Solo cuando ha pasado el tiempo del frame completo
            if (delta >= 1) {
                update(); // actualiza la lógica del motor
                delta--;
            }
            repaint();
        }
    }

    // Verifica si todas las bolas del mundo están prácticamente detenidas.
    /**
     * Verifica si todas las bolas dinámicas en el {@link SimuladorBillar} están
     * por debajo del umbral de velocidad de reposo.
     *
     * @return {@code true} si todas las bolas están detenidas o "dormidas".
     */
    private boolean todasDormidas() {
        double eps = Config.VELOCIDAD_REPOSO;
        for (Cuerpo c : mundo.getCuerpos()) {
            if (c instanceof Bola bola) {
                if (bola.getVel().magnitudVector() > eps)
                    return false;
            }
        }
        return true;
    }

    /**
     * Detiene la velocidad de todas las bolas en la simulación.
     */
    private void detenerTodas() {
        for (Cuerpo c : mundo.getCuerpos()) {
            if (c instanceof Bola bola) {
                bola.setVel(Vec2D.crearVectorNulo());
            }
        }
    }

    /**
     * Actualiza el estado lógico del juego en cada paso del bucle.
     * <p>
     * Llama a {@code Taco.update()} si las bolas están quietas,
     * y llama a {@code SimuladorBillar.actualizarSimulacion()}.
     * Al detectar que el movimiento finalizó, llama a {@code procesarFinDeTiro()}.
     * </p>
     */
    public void update() {
        boolean puedeTirar = todasDormidas();
        if (taco != null && !partida.isPartidaTerminada())
            taco.update(Config.DELTA_TIME, mouse, puedeTirar);
        mundo.actualizarSimulacion(Config.DELTA_TIME);

        boolean ahoraQuietas = todasDormidas();
        if (ahoraQuietas) {
            detenerTodas();
        }

        if (!antesQuietas && ahoraQuietas)
            procesarFinDeTiro();

        antesQuietas = ahoraQuietas;
    }

    // Procesa la lógica cada vez que acaba un tiro
    /**
     * Procesa la lógica de fin de turno llamando a
     * {@code PartidaBillar.finDeTiro()}
     * y maneja la terminación del juego si hay un ganador.
     */
    private void procesarFinDeTiro() {
        Jugador ganador = partida.finDeTiro();
        if (ganador != null) {
            terminarPartida(ganador);
        }
    }

    /**
     * Finaliza la partida, muestra el ganador en un cuadro de diálogo y da la
     * opción de reiniciar o salir.
     *
     * @param ganador El {@link Jugador} que ganó la partida.
     */
    public void terminarPartida(Jugador ganador) {
        Jugador perdedor = (ganador == partida.getJugador1()) ? partida.getJugador2() : partida.getJugador1();
        String mensaje = "Juego terminado\n\nGanador: " + ganador.getNombre()
                + "\nPerdedor: " + perdedor.getNombre();

        String[] opciones = { "Volver a jugar", "Salir" };

        int seleccion = JOptionPane.showOptionDialog(
                this,
                mensaje,
                "Juego terminado",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion == 0) {
            reiniciarPartida();
        } else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            } else {
                System.exit(0);
            }
        }
    }

    // Reinicia el juego
    /**
     *  Reinicia el juego
     */
    private void reiniciarPartida() {
        // Resetear el mundo físico
        mundo = new SimuladorBillar();
        bolaBlanca = null;
        mesa = null;
        taco = null;

        // Resetear las reglas del juego (jugadores, grupos, conteos)
        partida.reiniciar();

        // Volver a armar todo
        inicializarMundo();
        conectarEventosTronera();
    }

    // 
    /**
     * Metodos para interfaz de informacion de juego
     * @param grupo El {@link GrupoBola} a traducir.
     * @return que describe el grupo ("Lisas", "Rayadas", "Sin asignar").
     */
    private String textoTipo(GrupoBola grupo) {
        return switch (grupo) {
            case SIN_ASIGNAR -> "Sin asignar";
            case LISAS -> "Lisas";
            case RAYADAS -> "Rayadas";
        };
    }

    // Funcion que dibuja el panel izquierdo de información de la partida
    /**
     * Dibuja el panel lateral con la información de la partida actual:
     * turno, grupos asignados y bolas restantes de cada {@link Jugador}.
     *
     * @param g2 El contexto gráfico 2D.
     */
    private void dibujarPanelInfo(Graphics2D g2) {
        if (mesa == null)
            return;

        int x = 10;
        int y = 40;
        int ancho = (int) mesa.getBordeIzquierdo() - 2 * x;
        int alto = screenHeight - 80;

        if (ancho <= 0)
            return;

        // Fondo
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x, y, ancho, alto, 20, 20);

        // Borde
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(x, y, ancho, alto, 20, 20);

        int textoX = x + 15;
        int textoY = y + 30;

        g2.drawString("Información del juego", textoX, textoY);
        textoY += 30;

        Jugador j1 = partida.getJugador1();
        Jugador j2 = partida.getJugador2();

        // Jugador 1
        g2.drawString(j1.getNombre(), textoX, textoY);
        textoY += 18;
        g2.drawString("Tipo: " + textoTipo(j1.getGrupo()), textoX, textoY);
        textoY += 18;
        g2.drawString("Bolas restantes: " + partida.getBolasRestantes(j1), textoX, textoY);

        textoY += 30;

        // Jugador 2
        g2.setColor(Color.WHITE);
        g2.drawString(j2.getNombre(), textoX, textoY);
        textoY += 18;
        g2.drawString("Tipo: " + textoTipo(j2.getGrupo()), textoX, textoY);
        textoY += 18;
        g2.drawString("Bolas restantes: " + partida.getBolasRestantes(j2), textoX, textoY);

        textoY += 30;

        // Turno actual
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Turno actual:", textoX, textoY);
        textoY += 18;

        if (!partida.isPartidaTerminada()) {
            g2.setColor(new Color(255, 215, 0));
            g2.drawString(partida.getJugadorActual().getNombre(), textoX, textoY);
        } else {
            g2.setColor(Color.RED);
            g2.drawString("Partida terminada", textoX, textoY);
        }
    }

    // Estandar en JPanel
    // Dibuja todos los elementos del juego
    /**
     * Método de {@code JPanel} para dibujar todos los elementos del juego.
     * <p>
     * Dibuja el fondo, la mesa, las troneras, las bolas y el taco.
     * </p>
     *
     * @param g El contexto gráfico.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dibujo de la mesa
        g2.setColor(new Color(90, 50, 20));
        g2.fillRect(0, 0, screenWidth, screenHeight);
        g2.setColor(new Color(10, 120, 40));
        g2.fillRect((int) mesa.getBordeIzquierdo(), (int) mesa.getBordeSuperior(),
                (int) (mesa.getBordeDerecho() - mesa.getBordeIzquierdo()),
                (int) (mesa.getBordeInferior() - mesa.getBordeSuperior()));

        // Crear las troneras
        for (Tronera t : mesa.getAgujeros()) {
            t.dibujarTronera(g2);
        }

        // Dibujo de los Bordes
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect((int) mesa.getBordeIzquierdo(), (int) mesa.getBordeSuperior(),
                (int) (mesa.getBordeDerecho() - mesa.getBordeIzquierdo()),
                (int) (mesa.getBordeInferior() - mesa.getBordeSuperior()));

        dibujarPanelInfo(g2);
        // Dibujo de las Bolas
        for (Cuerpo c : mundo.getCuerpos()) {
            if (c instanceof Bola bola) {
                bola.draw(g2);
            }
        }

        // Dibujo del Taco
        if (taco != null)
            taco.draw(g2, mouse, todasDormidas());

        // Libera recursos gráficos asociados a este contexto.
        g2.dispose();
    }
}