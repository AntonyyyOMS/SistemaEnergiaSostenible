package vista;

import modelo.ProyectoEnergia;
import javax.swing.JTextArea;

public class ProyectoEnergiaVista implements IMostradorMensajes {
    private JTextArea outputArea;

    public ProyectoEnergiaVista(JTextArea outputArea) {
        this.outputArea = outputArea;
    }

    public ProyectoEnergiaVista() {
        this.outputArea = null;
    }

    public void mostrarDetallesProyecto(ProyectoEnergia proyecto) {
        StringBuilder sb = new StringBuilder("Detalles del Proyecto:\n");
        sb.append("ID: ").append(proyecto.getId()).append("\n");
        sb.append("Nombre: ").append(proyecto.getNombreProyecto()).append("\n");
        sb.append("Tipo de Fuente: ").append(proyecto.getTipoFuente()).append("\n");
        sb.append("Capacidad (MW): ").append(proyecto.getCapacidadMW()).append("\n");
        mostrarMensaje(sb.toString());
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        if (outputArea != null) {
            outputArea.append(mensaje + "\n");
            outputArea.setCaretPosition(outputArea.getDocument().getLength());
        } else {
            System.out.println(mensaje);
        }
    }
}