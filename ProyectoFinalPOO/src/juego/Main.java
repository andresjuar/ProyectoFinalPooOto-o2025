package juego;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import juego.ui.GamePanel;
/**
 * Clase principal que contiene el método {@code main} y sirve como el punto de inicio
 * del juego de Billar.
 * <p>
 * Se encarga de crear la ventana principal del juego ({@link JFrame}),
 * configurar sus propiedades básicas (título, cierre, tamaño) e iniciar
 * el hilo del juego ({@link GamePanel#startGameThread()}).
 * </p>
 *
 * @version 1.0
 */
public class Main {
    /**
     * El método principal de la aplicación.
     * <p>
     * 1. Crea y configura la ventana ({@code JFrame}).
     * 2. Crea el panel del juego ({@link GamePanel}).
     * 3. Empaqueta la ventana al tamaño preferido por el panel.
     * 4. Centra la ventana y la hace visible.
     * 5. Inicia el bucle de juego en un hilo separado.
     * </p>
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Billar");
        java.net.URL urlIcono = Main.class.getResource("/bolas/Bola_8.png");
        if (urlIcono != null) {
            java.awt.Image icono = java.awt.Toolkit.getDefaultToolkit().getImage(urlIcono);
            
            // 3. Establecer el ícono.
            window.setIconImage(icono);
        } else {
            System.err.println("Advertencia: No se pudo cargar la imagen del ícono.");
        }
      
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}