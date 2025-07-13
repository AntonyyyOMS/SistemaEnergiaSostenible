package modelo;

import java.util.HashMap; // [cite: 2]
import java.util.Map; // [cite: 2]

public class ProyectoEnergiaFactory { // [cite: 2]
    private static Map<String, ProyectoEnergia> tiposProyectos = new HashMap<>(); 

    static { // [cite: 2]
        tiposProyectos.put("Solar", new ProyectoEnergia("Solar")); 
        tiposProyectos.put("Eólica", new ProyectoEnergia("Eólica")); 
        tiposProyectos.put("Hidroeléctrica", new ProyectoHidroelectrico("Hidroeléctrica")); 
        tiposProyectos.put("Geotérmica", new ProyectoEnergia("Geotérmica")); 
        tiposProyectos.put("Biomasa", new ProyectoEnergia("Biomasa")); 
    }

    public static ProyectoEnergia crearProyecto(String tipoFuente) { // [cite: 2]
        ProyectoEnergia tipoProyecto = tiposProyectos.get(tipoFuente); 
        if (tipoProyecto != null) { // [cite: 2]
            try {
                return (ProyectoEnergia) tipoProyecto.clone(); // [cite: 2]
            } catch (CloneNotSupportedException e) { // [cite: 2]
                throw new RuntimeException("Error al clonar el proyecto: " + e.getMessage()); 
            }
        } else {
            throw new IllegalArgumentException("Tipo de fuente de energía no válido: " + tipoFuente); 
        }
    }
}