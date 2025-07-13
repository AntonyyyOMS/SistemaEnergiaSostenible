
package modelo;

class ProyectoSolar implements TipoProyectoEnergia {
    @Override
    public ProyectoEnergia crearProyecto() {
        return new ProyectoEnergia("Solar");
    }
}