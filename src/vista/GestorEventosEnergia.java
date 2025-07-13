package vista;

import java.util.ArrayList;
import java.util.List;

public class GestorEventosEnergia implements Subject {
    private List<Observer> observers;
    private static GestorEventosEnergia instancia;

    private GestorEventosEnergia() {
        this.observers = new ArrayList<>();
    }

    public static GestorEventosEnergia getInstancia() {
        if (instancia == null) {
            instancia = new GestorEventosEnergia();
        }
        return instancia;
    }

    @Override
    public void agregarObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer agregado: " + observer.getClass().getSimpleName());
        }
    }

    @Override
    public void removerObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("Observer removido: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notificarObservers(String evento, Object datos) {
        System.out.println("Notificando evento: " + evento + " a " + observers.size() + " observers");
        for (Observer observer : observers) {
            try {
                observer.actualizar(evento, datos);
            } catch (Exception e) {
                System.err.println("Error al notificar observer: " + e.getMessage());
            }
        }
    }

    public void notificarProyectoCreado(modelo.ProyectoEnergia proyecto) {
        notificarObservers("PROYECTO_CREADO", proyecto);
    }

    public void notificarProyectoActualizado(modelo.ProyectoEnergia proyecto) {
        notificarObservers("PROYECTO_ACTUALIZADO", proyecto);
    }

    public void notificarProyectoEliminado(modelo.ProyectoEnergia proyecto) {
        notificarObservers("PROYECTO_ELIMINADO", proyecto);
    }

    public void notificarConexionBD(boolean exitosa) {
        if (exitosa) {
            notificarObservers("CONEXION_BD_ESTABLECIDA", "Conexión exitosa");
        } else {
            notificarObservers("ERROR_CONEXION_BD", "Error en conexión");
        }
    }

    public void notificarValidacionEnergia(String mensaje) {
        notificarObservers("VALIDACION_ENERGIA", mensaje);
    }
}