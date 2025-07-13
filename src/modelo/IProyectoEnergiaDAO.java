package modelo;

import java.util.List;

public interface IProyectoEnergiaDAO {
    int crearProyectoEnergia(ProyectoEnergia proyecto);
    ProyectoEnergia leerProyectoEnergia(int id);
    List<ProyectoEnergia> leerTodosProyectosEnergia();
    boolean actualizarProyectoEnergia(ProyectoEnergia proyecto);
    boolean eliminarProyectoEnergia(int id);
}