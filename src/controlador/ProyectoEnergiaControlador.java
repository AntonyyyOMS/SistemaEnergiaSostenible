
package controlador;

import modelo.ProyectoEnergia;
import vista.IMostradorMensajes;
import java.util.List;

public class ProyectoEnergiaControlador {
    private final ProyectoEnergiaFacade proyectoEnergiaFacade;
    private final IMostradorMensajes mostradorMensajes;

    public ProyectoEnergiaControlador(IMostradorMensajes mostradorMensajes) {
        this.proyectoEnergiaFacade = new ProyectoEnergiaFacade(); // Usa Proxy
        this.mostradorMensajes = mostradorMensajes;
    }

    public int crearProyectoEnergia(String nombre, String tipoFuente, double capacidad) {
        int idGenerado = proyectoEnergiaFacade.crearProyectoEnergia(nombre, tipoFuente, capacidad);
        if (idGenerado != -1) {
            mostrar("✅ Proyecto creado exitosamente. ID: " + idGenerado);
        } else {
            mostrar("❌ Error al crear el proyecto");
        }
        return idGenerado;
    }

    public ProyectoEnergia leerProyectoEnergia(int id) {
        ProyectoEnergia proyecto = proyectoEnergiaFacade.leerProyectoEnergia(id);
        if (proyecto != null) {
            mostrar(proyecto.toString());
        } else {
            mostrar("⚠️ No se encontró el proyecto con ID: " + id);
        }
        return proyecto;
    }

    public List<ProyectoEnergia> leerTodosProyectosEnergia() {
        List<ProyectoEnergia> proyectos = proyectoEnergiaFacade.leerTodosProyectosEnergia();
        if (proyectos != null && !proyectos.isEmpty()) {
            mostrar("📋 Total de proyectos: " + proyectos.size());
        } else {
            mostrar("ℹ️ No hay proyectos registrados");
        }
        return proyectos;
    }

    public boolean actualizarProyectoEnergia(int id, String nombre, String tipoFuente, double capacidad) {
        boolean exito = proyectoEnergiaFacade.actualizarProyectoEnergia(id, nombre, tipoFuente, capacidad);
        if (exito) {
            mostrar("🔄 Proyecto actualizado correctamente");
        } else {
            mostrar("⛔ Error al actualizar el proyecto");
        }
        return exito;
    }

    public boolean eliminarProyectoEnergia(int id) {
        boolean exito = proyectoEnergiaFacade.eliminarProyectoEnergia(id);
        if (exito) {
            mostrar("🗑️ Proyecto eliminado correctamente");
        } else {
            mostrar("⛔ Error al eliminar el proyecto");
        }
        return exito;
    }

    private void mostrar(String mensaje) {
        if (mostradorMensajes != null) {
            mostradorMensajes.mostrarMensaje(mensaje);
        } else {
            System.out.println(mensaje);
        }
    }
}
