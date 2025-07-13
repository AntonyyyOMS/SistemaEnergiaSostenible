package controlador;

import modelo.IProyectoEnergiaDAO;
import modelo.ProyectoEnergia;
import modelo.ProyectoEnergiaDAOProxy;
import java.util.List;

public class ProyectoEnergiaFacade {
    private final IProyectoEnergiaDAO proyectoEnergiaDAO;

    public ProyectoEnergiaFacade() {
        // Usamos el Proxy en lugar del DAO directo
        this.proyectoEnergiaDAO = new ProyectoEnergiaDAOProxy();
    }

    public int crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        ProyectoEnergia nuevoProyecto = new ProyectoEnergia(0, nombre, tipoFuente, capacidad);
        int idGenerado = proyectoEnergiaDAO.crearProyectoEnergia(nuevoProyecto);
        
        if (idGenerado != -1) {
            System.out.println("[Fachada] Proyecto creado con ID: " + idGenerado);
        } else {
            System.err.println("[Fachada] Error al crear proyecto");
        }
        
        return idGenerado;
    }

    public ProyectoEnergia leerProyectoEnergia(int id) {
        System.out.println("[Fachada] Solicitando proyecto con ID: " + id);
        return proyectoEnergiaDAO.leerProyectoEnergia(id);
    }

    public List<ProyectoEnergia> leerTodosProyectosEnergia() {
        System.out.println("[Fachada] Solicitando todos los proyectos");
        return proyectoEnergiaDAO.leerTodosProyectosEnergia();
    }

    public boolean actualizarProyectoEnergia(int id, String nombre, String tipoFuente, double capacidad) {
        System.out.println("[Fachada] Actualizando proyecto ID: " + id);
        ProyectoEnergia proyecto = new ProyectoEnergia(id, nombre, tipoFuente, capacidad);
        return proyectoEnergiaDAO.actualizarProyectoEnergia(proyecto);
    }

    public boolean eliminarProyectoEnergia(int id) {
        System.out.println("[Fachada] Eliminando proyecto ID: " + id);
        return proyectoEnergiaDAO.eliminarProyectoEnergia(id);
    }

    // Método adicional para limpiar la caché (útil para testing)
    public void limpiarCache() {
        if (proyectoEnergiaDAO instanceof ProyectoEnergiaDAOProxy) {
            ((ProyectoEnergiaDAOProxy) proyectoEnergiaDAO).limpiarCache();
        }
    }
}