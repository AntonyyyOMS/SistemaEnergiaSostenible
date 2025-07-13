package modelo;

public class ProyectoEnergia implements Cloneable {
    private int id;
    private String nombreProyecto;
    private String tipoFuente;
    private double capacidadMW;

    public ProyectoEnergia() {}

    public ProyectoEnergia(String tipoFuente) {
        this.tipoFuente = tipoFuente;
    }

    public ProyectoEnergia(int id, String nombreProyecto, String tipoFuente, double capacidadMW) {
        this.id = id;
        this.nombreProyecto = nombreProyecto;
        this.tipoFuente = tipoFuente;
        this.capacidadMW = capacidadMW;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombreProyecto() { return nombreProyecto; }
    public void setNombreProyecto(String nombreProyecto) { this.nombreProyecto = nombreProyecto; }
    public String getTipoFuente() { return tipoFuente; }
    public void setTipoFuente(String tipoFuente) { this.tipoFuente = tipoFuente; }
    public double getCapacidadMW() { return capacidadMW; }
    public void setCapacidadMW(double capacidadMW) { this.capacidadMW = capacidadMW; }

    @Override
    public String toString() {
        return "ProyectoEnergia{" +
                "id=" + id +
                ", nombreProyecto='" + nombreProyecto + '\'' +
                ", tipoFuente='" + tipoFuente + '\'' +
                ", capacidadMW=" + capacidadMW +
                "}";
    }
}