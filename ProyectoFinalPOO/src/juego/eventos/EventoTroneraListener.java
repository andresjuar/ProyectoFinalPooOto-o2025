package juego.eventos;

import juego.entidades.Bola;

public interface EventoTroneraListener {
    /* 
        Se llama cuando una bola entra en una tronera
        Permite que el juego actualice sus datos.
    */
    public void bolaEmbolsada(Bola bola);
}
