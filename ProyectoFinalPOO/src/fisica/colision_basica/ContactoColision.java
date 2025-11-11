package fisica.colision_basica;

import fisica.main.Cuerpo;
import fisica.main.Vec2D;

public class ContactoColision{
    public final Cuerpo A, B;
    public Vec2D normalColision = Vec2D.crearVectorNulo();
    public double profundidadPenetracion = 0.0;
    public double velocidadRelativaNormal = 0.0;

    public ContactoColision(Cuerpo A, Cuerpo B){
        this.A = A;
        this.B = B;
    }
}
