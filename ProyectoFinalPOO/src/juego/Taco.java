package juego;

import fisica.main.Bola;
import fisica.main.Material;
import fisica.main.Vec2D;
import java.awt.*;

public class Taco{
    private final Bola blanca;
    private final Material material;

    private double power = 0.0;
    private boolean charging = false;

    // Potencia máxima del disparo, expresada como magnitud máxima del impulso aplicado a la bola.
    private final double impulsoMaximo = 140.0;

    // Velocidad a la que aumenta la potencia mientras se mantiene presionado el mouse (impulso por segundo).
    private final double velocidadDeCarga = 280.0;

    // Longitud visible del taco en pantalla, usada para el dibujo de la línea que representa el taco.
    private final double longitudTaco = 80.0;

    // Grosor visual del taco al dibujarlo, usado en el trazo de la línea.
    private final double grosorTaco = 6.0;

    public Taco(Bola blanca, Material material){
        this.blanca = blanca;
        this.material = material;
    }

    // Manejar carga y disparo con el mouse
    public void update(double dt, MouseHandler mouse, boolean puedeTirar){
        if (!puedeTirar) { // si hay bolas moviéndose, no mostrar ni cargar
            charging = false;
            power = 0;
            mouse.consumeEdgeTriggers();
            return;
        }

        if (mouse.justPressed) {
            charging = true;
            power = 0;
        }
        if (charging && mouse.isPressed) {
            power = Math.min(impulsoMaximo, power + velocidadDeCarga * dt);
        }
        if (charging && mouse.justReleased) {
            // dirección: de blanca -> mouse
            Vec2D dir = Vec2D.crearVector(mouse.mouseX - blanca.getPosicion().x, mouse.mouseY - blanca.getPosicion().y);
            double L = dir.magnitudVector();
            if (L < 1e-6) dir = Vec2D.crearVector(1, 0); 
            else dir = dir.escalarXVector(1.0 / L);

            // para no "inventar" fuerzas sin sentido, se utiliza el material al momento del contacto
            double factorMaterial = material.getRestitucion();
            double impulso = power * factorMaterial;
            
            
            // aplicar IMPULSO a la blanca
            blanca.aplicarImpulso(dir.escalarXVector(impulso));

            // reset estado
            charging = false;
            power = 0.0;
        }
        mouse.consumeEdgeTriggers();
    }

    //Dibujo del taco y de una barrita de potencia simple
    public void draw(Graphics2D g, MouseHandler mouse, boolean puedeTirar){
        if (!puedeTirar) return;

        // línea del taco: se dibuja por detrás de la blanca (opuesto a la dirección)
        double bx = blanca.getPosicion().x, by = blanca.getPosicion().y;
        Vec2D aim = Vec2D.crearVector(mouse.mouseX - bx, mouse.mouseY - by);
        double L = aim.magnitudVector();
        if (L < 1e-6) return;
        aim = aim.escalarXVector(1.0 / L);

        // El taco se dibuja desde un punto detrás de la bola (para que no “atraviese” la blanca)
        double pull = charging ? (power / impulsoMaximo) * 60.0 : 0.0; // retroceso visual (0..60 px)
        double startX = bx - aim.x * (blanca.getRadio() + 6 + pull);
        double startY = by - aim.y * (blanca.getRadio() + 6 + pull);
        double endX   = startX - aim.x * longitudTaco;
        double endY   = startY - aim.y * longitudTaco;

        Stroke old = g.getStroke();
        g.setStroke(new BasicStroke((float) grosorTaco, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(new Color(170, 120, 70)); // “madera”
        g.drawLine((int)Math.round(startX), (int)Math.round(startY),
                   (int)Math.round(endX),   (int)Math.round(endY));
        g.setStroke(old);

        // barra de poder
        int barW = 120, barH = 8, x = 20, y = 20;
        g.setColor(Color.DARK_GRAY);
        g.drawRect(x, y, barW, barH);
        g.setColor(new Color(230, 80, 60));
        int fill = (int)Math.round((power / impulsoMaximo) * barW);
        g.fillRect(x+1, y+1, Math.max(0, fill-2), barH-2);
    }
}