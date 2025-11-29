package juego;

import fisica.main.GrupoBola;

public class Jugador {
    private final String nombre;
    private GrupoBola grupo = GrupoBola.SIN_ASIGNAR;

    public Jugador(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public GrupoBola getGrupo(){
        return grupo;
    }

    public void setGrupo(GrupoBola grupo){
        this.grupo = grupo;
    }

    public boolean tieneGrupoAsignado(){
        return grupo != GrupoBola.SIN_ASIGNAR;
    }
}
