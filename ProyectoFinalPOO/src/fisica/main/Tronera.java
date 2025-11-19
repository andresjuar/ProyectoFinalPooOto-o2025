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

    // Indica si la bola, esta dentro del agujero para determinar si entró o no a la tronera
    public boolean contieneBola(Bola bola){
        Vec2D centroBola = bola.getPosicion();
        // Determina la distancia que existe entre el centro de la bola, con el centro de la tronera
        double distBolaAlCentroTronera = centroBola.restaVectores(centro).magnitudVector();

        // Determina un margen de seguridad para asegurarnos evitar que solamente con el contacto
        // de la bola con la tronera
        double margenSeguridad = Math.max(2.0, bola.getRadio()*0.4); // Sean 2px o el 40% del radio de la bola

        // Devuelve VERDADERO en caso de que alguna pelota este en el centro del agujero
        return distBolaAlCentroTronera < (radio - margenSeguridad);
    }
}