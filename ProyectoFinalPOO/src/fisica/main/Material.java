package fisica.main;

public enum Material {
    CAUCHO(0.96, 0.35),
    MADERA(0.6, 0.05),
    ACERO(0.8, 0.01);
    
    // Velocidad que tiene las bolas después del impacto
    public final double restitucion;

    // Valor que simula la resistencia al entorno, para que pueda frenar las bolas
    public final double amortiguamiento;
    
    Material(double e, double d){ 
        this.restitucion = e; 
        this.amortiguamiento = d; 
    }
}
