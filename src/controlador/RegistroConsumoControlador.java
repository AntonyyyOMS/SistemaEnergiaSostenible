package controlador;

import modelo.RegistroConsumo;
import java.util.List;

public class RegistroConsumoControlador {

    private RegistroConsumoFacade registroConsumoFacade;

    public RegistroConsumoControlador() {
        this.registroConsumoFacade = new RegistroConsumoFacade();
    }

    public int registrarConsumo(RegistroConsumo registro) {
        return registroConsumoFacade.crearRegistroConsumo(registro);
    }

    public List<RegistroConsumo> obtenerHistorialConsumo() {
        return registroConsumoFacade.leerTodosRegistrosConsumo();
    }

    public RegistroConsumo obtenerRegistroConsumoPorId(int id) {
        return registroConsumoFacade.leerRegistroConsumo(id);
    }

    public boolean actualizarConsumo(RegistroConsumo registro) {
        return registroConsumoFacade.actualizarRegistroConsumo(registro);
    }

    public boolean eliminarConsumo(int id) {
        return registroConsumoFacade.eliminarRegistroConsumo(id);
    }
}