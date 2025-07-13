package modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProyectoEnergiaDAOProxy implements IProyectoEnergiaDAO {
    private final IProyectoEnergiaDAO daoReal;
    private final Map<Integer, ProyectoEnergia> cache;

    public ProyectoEnergiaDAOProxy() {
        this.daoReal = new ProyectoEnergiaDAO(); // DAO real que hace las consultas a BD
        this.cache = new HashMap<>();
    }

    @Override
    public int crearProyectoEnergia(ProyectoEnergia proyecto) {
        int id = daoReal.crearProyectoEnergia(proyecto);
        if (id != -1) {
            proyecto.setId(id);
            cache.put(id, proyecto); // Almacena en caché después de crear
        }
        return id;
    }

    @Override
    public ProyectoEnergia leerProyectoEnergia(int id) {
        // Primero verifica la caché
        if (cache.containsKey(id)) {
            System.out.println("[Proxy] Obteniendo proyecto desde caché (ID: " + id + ")");
            return cache.get(id);
        }

        // Si no está en caché, consulta al DAO real
        ProyectoEnergia proyecto = daoReal.leerProyectoEnergia(id);
        if (proyecto != null) {
            cache.put(id, proyecto); // Almacena en caché para futuras consultas
        }
        return proyecto;
    }

    @Override
    public List<ProyectoEnergia> leerTodosProyectosEnergia() {
        // No cacheamos la lista completa por posibles cambios frecuentes
        return daoReal.leerTodosProyectosEnergia();
    }

    @Override
    public boolean actualizarProyectoEnergia(ProyectoEnergia proyecto) {
        boolean resultado = daoReal.actualizarProyectoEnergia(proyecto);
        if (resultado) {
            // Actualiza la caché si la operación en BD fue exitosa
            cache.put(proyecto.getId(), proyecto);
        }
        return resultado;
    }

    @Override
    public boolean eliminarProyectoEnergia(int id) {
        boolean resultado = daoReal.eliminarProyectoEnergia(id);
        if (resultado) {
            // Elimina de la caché si la operación en BD fue exitosa
            cache.remove(id);
        }
        return resultado;
    }

    public void limpiarCache() {
        this.cache.clear();
        System.out.println("[Proxy] Caché limpiada");
    }
}