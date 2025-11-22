package juego;

import fisica.main.*;
import fisica.mundo.Config;
import fisica.mundo.MundoFisico;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    // CONFIGURACIÓN DE PANTALLA
    final int originalTitleSize = 16;
    final int scale = 3;

    public final int titleSize = originalTitleSize * scale; //48*48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = titleSize * maxScreenCol; // 768 pixeles
    final int screenHeight = titleSize * maxScreenRow; // 576 pixeles

    int FPS = 60;
    Thread gameThread; // Hilo en el que corre el bucle principal del juego.

    // Motor & mundo
    MundoFisico mundo = new MundoFisico();
    Bola bolaBlanca;
    Mesa mesa;

    final MouseHandler mouse = new MouseHandler();
    Taco taco;

    // Constructor del planel del juego. Configura tamaño, color y registro de los listeners del mouse
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black); // Color de fondo del panel
        this.setDoubleBuffered(true);

        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.setFocusable(true); // Permite recibir eventos de teclado si se usan.

       inicializarMundo();

    }

    // Inicia el mundo físico: crea la mesa, bola, asigna material y color
    public void inicializarMundo(){
        int margen = 20;
        // Crear mesa centrada con un margen alrededor.
        mesa = new Mesa("mesa", 1, screenWidth - 2*margen, screenHeight - 2*margen , Vec2D.crearVector(margen,margen));
        mesa.crearMesaBillar(16); // Crea las troneras
        mundo.setMesaBillar(mesa); // Agrega la mesa al mundo

        // Configuración de la bola blanca
        double masaBola = 0.17; // De acuerdo con la escala
        double radio = 12; //pixeles en pantalla

        // Creación de la bola blanca
        bolaBlanca = new Bola("blanca", masaBola, radio, Vec2D.crearVector(margen+100,screenHeight/2.0));
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
        for (int fila = 0; fila < 5; fila++){
            for (int j = 0; j <= fila; j++){
                double x = inicioTrianguloX + fila * (radio * 2 + 1);
                double y = inicioTrianguloY - (fila*radio) + j * (radio * 2 + 1);
                if (fila == 0){
                    bolaUno = new Bola("uno", masaBola, radio, Vec2D.crearVector(x, y));
                    bolaUno.setMaterial(Material.CAUCHO);
                    mundo.agregarCuerpo(bolaUno);
                }else if(fila == 1){
                    if (j == 0){
                        bolaDos = new Bola("dos", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaDos.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaDos);
                    }else if(j == 1){
                        bolaTres = new Bola("tres", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaTres.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaTres);
                    }
                }else if (fila == 2){
                    if(j == 0){
                        bolaCuatro = new Bola("cuatro", masaBola, radio, Vec2D.crearVector(x,y));
                        bolaCuatro.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaCuatro);
                    }else if(j == 1){
                        bolaOcho = new Bola("ocho", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaOcho.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaOcho);
                    }else if (j == 2){
                        bolaCinco = new Bola("cinco", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaCinco.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaCinco);
                    }
                }else if(fila == 3){
                    if(j == 0){
                        bolaSeis = new Bola("seis", masaBola, radio, Vec2D.crearVector(x,y));
                        bolaSeis.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaSeis);
                    }else if(j == 1){
                        bolaSiete = new Bola("siete", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaSiete.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaSiete);
                    }else if (j == 2){
                        bolaNueve = new Bola("nueve", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaNueve.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaNueve);
                    }else if(j == 3){
                        bolaDiez = new Bola("diez", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaDiez.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaDiez);
                    }
                }else if(fila == 4){
                    if(j == 0){
                        bolaOnce = new Bola("once", masaBola, radio, Vec2D.crearVector(x,y));
                        bolaOnce.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaOnce);
                    }else if(j == 1){
                        bolaDoce = new Bola("doce", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaDoce.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaDoce);
                    }else if (j == 2){
                        bolaTrece = new Bola("trece", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaTrece.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaTrece);
                    }else if(j == 3){
                        bolaCatorce = new Bola("catorce", masaBola, radio, Vec2D.crearVector(x, y));
                        bolaCatorce.setMaterial(Material.CAUCHO);
                        mundo.agregarCuerpo(bolaCatorce);
                    }else if(j == 4){
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

    // Inicia el hilo del juego, el cual ejecuta el game loop
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    // Bucle principal del juego
    @Override
    public void run() {
        
        double drawInterval = 1_000_000_000.0/FPS; // Duración del frame en nanosegundos
        double delta = 0;
        long lastTime = System.nanoTime();
        
        while(gameThread != null){
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Solo cuando ha pasado el tiempo del frame completo
            if(delta >= 1){
                update(); // actualiza la lógica del motor
                delta--;
            }
            repaint();
        }
    }

    // Verifica si todas las bolas del mundo están prácticamente detenidas.
    private boolean todasDormidas(){
        double eps = Config.VELOCIDAD_REPOSO;
        for (Cuerpo c : mundo.getCuerpos()){
            if (c instanceof Bola bola){
                if (bola.getVel().magnitudVector() > eps) return false;
            }
        }
        return true;
    }


    // Actualiza el estado del juego en cada frame lógico:
    public void update(){
        boolean puedeTirar = todasDormidas();
        if (taco!= null) taco.update(Config.DELTA_TIME, mouse, puedeTirar);
        mundo.actualizarSimulacion(Config.DELTA_TIME);
    }

    // Estandar en JPanel
    // Dibuja todos los elementos del juego:
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dibujo de la mesa
        g2.setColor(new Color(90,50,20));
        g2.fillRect(0,0,screenWidth, screenHeight);
        g2.setColor(new Color (10,120,40));
        g2.fillRect((int)mesa.getBordeIzquierdo(), (int)mesa.getBordeSuperior(), 
        (int)(mesa.getBordeDerecho() - mesa.getBordeIzquierdo()), 
        (int)(mesa.getBordeInferior() - mesa.getBordeSuperior()));

        // Crear las troneras
            for(Tronera t : mesa.getAgujeros()){
                t.dibujarTronera(g2);
            }

        // Dibujo de los Bordes
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect((int)mesa.getBordeIzquierdo(), (int)mesa.getBordeSuperior(), 
        (int)(mesa.getBordeDerecho() - mesa.getBordeIzquierdo()), 
        (int)(mesa.getBordeInferior() - mesa.getBordeSuperior()));
        
        //Dibujo de las Bolas
        for (Cuerpo c : mundo.getCuerpos()){
            if (c instanceof Bola bola){
                bola.draw(g2);
            }
        }

        //Dibujo del Taco
        if (taco != null) taco.draw(g2, mouse, todasDormidas());

        // Libera recursos gráficos asociados a este contexto.
        g2.dispose();
    }
    
}