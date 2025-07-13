
package modelo;

/**
 *
 * @author MI PC
 */
class ProyectoEolico implements TipoProyectoEnergia {
    @Override
    public ProyectoEnergia crearProyecto() {
        return new ProyectoEnergia("Eolico");
    }
}
