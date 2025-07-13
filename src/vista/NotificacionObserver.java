package vista;

import modelo.ProyectoEnergia;
import javax.swing.JTextArea;

public class NotificacionObserver implements Observer {
    private JTextArea notificacionArea;

    public NotificacionObserver(JTextArea notificacionArea) {
        this.notificacionArea = notificacionArea;
    }

    @Override
    public void actualizar(String evento, Object datos) {
        String notificacion = "";
        switch (evento) {
            case "PROYECTO_CREADO":
                if (datos instanceof ProyectoEnergia) {
                    ProyectoEnergia proyecto = (ProyectoEnergia) datos;
                    notificacion = String.format(
                        "🔔 ¡Éxito! Proyecto de energía '%s' creado correctamente.\n" +
                        "   Contribuyendo al ODS 7 con %.2f MW de energía limpia.\n",
                        proyecto.getNombreProyecto(), proyecto.getCapacidadMW()
                    );
                    if (proyecto.getCapacidadMW() > 100) {
                        notificacion += "⚡ ¡PROYECTO DE GRAN ESCALA DETECTADO! Impacto ambiental significativo.\n";
                    }
                }
                break;
            case "CONEXION_BD_ESTABLECIDA":
                notificacion = "✅ Conexión a base de datos establecida correctamente.\n";
                break;
            case "ERROR_CONEXION_BD":
                notificacion = "❌ Error en la conexión a la base de datos. Verifique la configuración.\n";
                break;
            case "VALIDACION_ENERGIA":
                notificacion = String.format("🌱 Validación: %s\n", datos.toString());
                break;
        }
        mostrarNotificacion(notificacion);
    }

    private void mostrarNotificacion(String notificacion) {
        if (notificacionArea != null) {
            notificacionArea.append(notificacion);
            notificacionArea.setCaretPosition(notificacionArea.getDocument().getLength());
        } else {
            System.out.println(notificacion);
        }
    }
}