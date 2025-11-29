package libreria.colision_basica;

import libreria.dinamica.Cuerpo;
import libreria.interfaces.BordesDelimitados;
import libreria.interfaces.CuerpoCircular;
import libreria.matematicas.Vec2D;

public class DetectorColisiones {
    // Detecta si existe una colisión entre una bola y las paredes de la mesa.
    public static boolean circuloVsBordes(CuerpoCircular c, BordesDelimitados bordes, ContactoColision m){
        // Centro actual de la bola
        Vec2D posicion = ((Cuerpo) c).getPosicion();
        // Radio de la mesa
        double radio = c.getRadio();

        //Posición de las paredes de la mesa
        double izquierda = bordes.getBordeIzquierdo(), derecha = bordes.getBordeDerecho(),
        arriba = bordes.getBordeSuperior(), abajo = bordes.getBordeInferior();

        /*Cuánto se "mete" la bola en cada pared.
        Un valor > 0 indica que ya se encuentra dentro de la zona de la pared. */
        double penetracionParedIzq = izquierda - (posicion.x - radio);
        double penetracionParedDer = (posicion.x + radio) - derecha;
        double penetracionParedSup = arriba - (posicion.y - radio);
        double penetracionParedInf = (posicion.y + radio) - abajo;

        // Seguimiento de la mayor penetración detectada y su normal asociada.
        double maxPen = 0;
        Vec2D colisionNormal = Vec2D.crearVectorNulo();

        // MUY IMPORTANTE: la normal apunta de A (bola) hacia B (pared)

        // Colisión con pared izquierda.
        if (penetracionParedIzq > maxPen) {
            maxPen = penetracionParedIzq;
            colisionNormal = Vec2D.crearVector(-1, 0); 
        }
        
        // Colisión con pared derecha.
        if (penetracionParedDer > maxPen) {
            maxPen = penetracionParedDer;
            colisionNormal = Vec2D.crearVector(1, 0);
        }

        // Colisión con pared superior.
        if (penetracionParedSup > maxPen) {
            maxPen = penetracionParedSup;
            colisionNormal = Vec2D.crearVector(0,-1);
        }

        // Colisión con pared inferior.
        if (penetracionParedInf > maxPen) {
            maxPen = penetracionParedInf;
            colisionNormal = Vec2D.crearVector(0, 1);
        }

        // Si alguna penetración es positiva, hubo colisión contra alguna pared.
        if (maxPen > 0){
            m.vectorNormal = colisionNormal; // Normal de colisión usada para corregir la posición de la bola.
            m.traslape = maxPen; // Qué tanto se ha metido dentro de la pared.
            // vRel = vB - vA; para pared vB=0 -> vRel = -vA
            m.proyVelocidadRelEnNormal = -((Cuerpo) c).getVel().productoPunto(colisionNormal);
            return true;
        }
        // No se detectó colisión con ninguna pared.
        return false;
    }

    // Detecta si existe colisión entre dos bolas cuando sus radios se sobreponen
    public static boolean circuloVsCirculo(CuerpoCircular x, CuerpoCircular y, ContactoColision contacto){
        // Vector que va desde el centro de A al centro de B
        Cuerpo a = (Cuerpo)x;
        Cuerpo b = (Cuerpo)y;
        Vec2D vectorCentroA2CentroB = b.getPosicion().restaVectores(a.getPosicion());

        // Distancia entre los centros
        double distanciaEntreCentros = vectorCentroA2CentroB.magnitudVector();
        double sumaDeRadios = x.getRadio() + y.getRadio();

        // Si la distancia entre radios es mayor o igual que la suma de sus radios,
        // no existe colisión
        if (distanciaEntreCentros >= sumaDeRadios) return false;

        // Cálculo de la colisión
        contacto.vectorNormal = (distanciaEntreCentros == 0) ? Vec2D.crearVector(1, 0) : vectorCentroA2CentroB.escalarXVector(1.0/distanciaEntreCentros);
        
        // Cáluclo del que tanto se sobrepineron sus radios
        contacto.traslape = sumaDeRadios - distanciaEntreCentros;

        // Cálculo de la velocidad relativa, proyectada sobre la normal
        contacto.proyVelocidadRelEnNormal = b.getVel().restaVectores(a.getVel()).productoPunto(contacto.vectorNormal);
        return true;
    }
}