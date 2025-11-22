package fisica.main;

import java.util.Objects;

public class Vec2D {
    // Atributos (Componentes del Vector)
    public final double x, y;
    
    // Constructor de la clase
    private Vec2D(double x, double y){
        this.x=x; this.y=y;
    }
    
    // Crea un vector a partir de sus componentes (x,y)
    public static Vec2D crearVector(double x, double y){ 
        return new Vec2D(x,y); 
    }

    // Crea un vector nulo (0,0)
    public static Vec2D crearVectorNulo(){
        return crearVector(0,0);
    }

    // Suma el vector actual, con el ingresado en los parámetros
    public Vec2D sumaVectores(Vec2D b){
        return crearVector(this.x + b.x, this.y + b.y);
    }
    
    // Resta el vector actual con el ingresado en los parámetros
    public Vec2D restaVectores(Vec2D b){
        return crearVector(this.x - b.x, this.y - b.y);
    }

    // Realiza la multiplicación de un escalar por un vector
    public Vec2D escalarXVector(double k){
        return crearVector(this.x * k, this.y * k);
    }

    // Realiza el producto punto entre vectores
    public double productoPunto(Vec2D b){ 
        return (this.x * b.x) + (this.y * b.y);
    }

    // Realiza la magnitud de un vector
    public double magnitudVector(){
        return Math.hypot(x, y);
    }
    
    // Regresa el vector unitario, si el vector es (0,0), devuelve el mismo vector para no dividr entre zero
    public Vec2D vectorUnitario(){
        double magnitudActual = magnitudVector(); 
        return magnitudActual == 0 ? crearVectorNulo() : crearVector(x/magnitudActual, y/magnitudActual);
    }

    // Métodos sobreescritos de object
    @Override
    public Vec2D clone(){
        return crearVector(x,y);
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Vec2D vector)) return false;
        
        return Math.abs(vector.x - x)<1e-9 && 
        Math.abs(vector.y - y)<1e-9;
    }

    @Override
    public int hashCode(){
        long xRedondeada = Math.round(x * 1e9);
        long yRedondeada = Math.round(y * 1e9);
        return Objects.hash(xRedondeada, yRedondeada);
    }

    @Override
    public String toString(){ 
        return "(" + x + "," + y + ")";
    }
}