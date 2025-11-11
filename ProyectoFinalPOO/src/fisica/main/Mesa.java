package fisica.main;

import java.awt.*;
import java.util.ArrayList;

public class Mesa extends Cuerpo{
    // Atributos
    private final double ancho, alto;
    private final ArrayList<Tronera> troneras = new ArrayList<>();
    
    // Este método devuelve la lista de troneras creadas en la mesa
    public ArrayList<Tronera> getAgujeros(){
        return troneras;
    }

    // Constrcutor de Mesa
    public Mesa(String id, double masa, double w, double h, Vec2D pos){
        super(id, masa); 
        this.ancho = w; 
        this.alto = h; 
        this.posicion = pos;
        setTipo(TipoDeObjeto.ESTATICO); // paredes/mesa
    }

    // Crea las troneras en la mesa de billar:
    // - Limpiando en caso de haber una configuración anterior
    public void crearMesaBillar(double radio){
        troneras.clear();
        double izquierda = getBordeIzquierdo(), derecha = getBordeDerecho(),
               arriba = getBordeSuperior(), abajo = getBordeInferior();
        double medio = (izquierda + derecha) / 2.0;
        double k = radio - 1.0;

        // Genera las troneras de las esquinas de la mesa 
        troneras.add(new Tronera(Vec2D.crearVector(izquierda + k, abajo - k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(izquierda + k, arriba + k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(derecha - k, arriba + k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(derecha - k, abajo - k), radio));
        
        // Genera las dos troneras que existen en el medio
        troneras.add(new Tronera(Vec2D.crearVector(medio, arriba + k), radio));
        troneras.add(new Tronera(Vec2D.crearVector(medio, abajo - k), radio));

    }

    // Dibuja el contorno de la mesa y sus respectivas troneras
    @Override
    public void draw(Graphics2D g){
        g.drawRect((int)posicion.x,(int)posicion.y,(int)ancho,(int)alto);

        for (Tronera t : troneras){
            t.dibujarTronera(g);
        }
    }

    // Devuelve la coordenada X del borde izquierdo de la mesa.
    public double getBordeIzquierdo(){
        return posicion.x;
    }
    
    // Devuelve la coordenada X del borde derecho de la mesa.
    public double getBordeDerecho(){
        return posicion.x + ancho;
    }

    // Devuelve la coordenada Y del borde superior de la mesa.
    public double getBordeSuperior(){
        return posicion.y;
    }
    
    // Devuelve la coordenada Y del borde inferior de la mesa.
    public double getBordeInferior(){
        return posicion.y + alto;
    }
}
