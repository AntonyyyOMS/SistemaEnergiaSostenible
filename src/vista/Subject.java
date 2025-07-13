package vista;

public interface Subject {
    void agregarObserver(Observer vista);
    void removerObserver(Observer vista);
    void notificarObservers(String evento, Object datos);
}