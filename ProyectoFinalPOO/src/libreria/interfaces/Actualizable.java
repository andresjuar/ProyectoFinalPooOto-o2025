package libreria.interfaces;

@FunctionalInterface
public interface Actualizable {
    // Actualiza el objeto dependiendo del tiempo transcurrido
    void actualizar(double dt);
}
