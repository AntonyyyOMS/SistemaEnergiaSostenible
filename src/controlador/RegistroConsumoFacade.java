package controlador;

import modelo.RegistroConsumo;
import modelo.RegistroConsumoDAO;
import java.util.List;

public class RegistroConsumoFacade {

    private RegistroConsumoDAO registroConsumoDAO;

    public RegistroConsumoFacade() {
        this.registroConsumoDAO = new RegistroConsumoDAO();
    }

    public int crearRegistroConsumo(RegistroConsumo registro) {
        return registroConsumoDAO.crearRegistroConsumo(registro);
    }

    public List<RegistroConsumo> leerTodosRegistrosConsumo() {
        return registroConsumoDAO.leerTodosRegistrosConsumo();
    }

    public RegistroConsumo leerRegistroConsumo(int id) {
        return registroConsumoDAO.leerRegistroConsumo(id);
    }

    public boolean actualizarRegistroConsumo(RegistroConsumo registro) {
        return registroConsumoDAO.actualizarRegistroConsumo(registro);
    }

    public boolean eliminarRegistroConsumo(int id) {
        return registroConsumoDAO.eliminarRegistroConsumo(id);
    }
}

