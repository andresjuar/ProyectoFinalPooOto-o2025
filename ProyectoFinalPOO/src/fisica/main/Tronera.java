package fisica.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tronera{
    private final Vec2D centro;
    private final double radio;

    // Constructor para crear las troneras
    public Tronera(Vec2D centro, double radio){
        this.centro = centro;
        this.radio = radio;
    }

    // Devuelve el radio de la Tronera
    public double getRadioTronera(){
        return radio;
    }

    // Devuelve el centro de la Tronera
    public Vec2D getCentroTronera(){
        return centro;
    }

    // Dibuja la tronera como un circulo negro relleno
    public void dibujarTronera(Graphics2D g){
        int diametro = (int) Math.round(radio * 2);
        g.setColor(Color.BLACK);
        g.fillOval((int)Math.round(centro.x - radio), (int) Math.round(centro.y - radio), diametro, diametro);
    }
}