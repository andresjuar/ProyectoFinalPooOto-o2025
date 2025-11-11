package fisica.mundo;

public class IntegradorSemiImplicito implements  Integrador{
    @Override
    public void integrar(Actualizable obj, double dt){ 
        obj.actualizar(dt);
    }
}