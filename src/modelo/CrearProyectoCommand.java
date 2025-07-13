package modelo;

import controlador.ProyectoEnergiaControlador;

public class CrearProyectoCommand implements Command {
    private ProyectoEnergiaControlador controlador;
    private String nombre;
    private String tipoFuente;
    private double capacidad;
    private int idGenerado = -1;

    public CrearProyectoCommand(ProyectoEnergiaControlador controlador, String nombre, String tipoFuente, double capacidad) {
        this.controlador = controlador;
        this.nombre = nombre;
        this.tipoFuente = tipoFuente;
        this.capacidad = capacidad;
    }

    @Override
    public void execute() {
        idGenerado = controlador.crearProyectoEnergia(nombre, tipoFuente, capacidad);
    }

    @Override
    public void undo() {
        if (idGenerado != -1) {
            controlador.eliminarProyectoEnergia(idGenerado);
        }
    }
}