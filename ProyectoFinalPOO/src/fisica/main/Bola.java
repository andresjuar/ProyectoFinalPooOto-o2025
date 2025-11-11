package fisica.main;

import java.awt.*;

public class Bola extends Cuerpo {
    private final double radio;
    private Color color = Color.WHITE;

    // Método Constructor
    public Bola(String id, double masa, double radio, Vec2D posInicial){
        super(id, masa);
        this.radio = radio;
        this.posicion = posInicial.clone();
    }

    // Devuelve el radio visual de la bola.
    public double getRadio(){
        return radio;
    }

    // Actualiza el color de la bola 
    public void setColor(Color c){
        this.color = c;
    }

    // Dibuja la bola en el contexto gráfico
    @Override
    public void draw(Graphics2D g){
        g.setColor(color);
        int d = (int)Math.round(radio * 2);
        g.fillOval((int)(posicion.x - radio), (int)(posicion.y - radio), d, d);
        g.setColor(Color.BLACK);
        g.drawOval((int)Math.round(posicion.x - radio), (int)Math.round(posicion.y - radio), d, d);
    }
}
