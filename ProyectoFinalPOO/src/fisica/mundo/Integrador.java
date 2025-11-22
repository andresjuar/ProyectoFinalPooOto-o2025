package fisica.mundo;

@FunctionalInterface
public interface Integrador {
    void integrar(Actualizable obj, double dt);
}
