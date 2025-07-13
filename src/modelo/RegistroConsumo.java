package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroConsumo {
    private int id;
    private LocalDate fecha;
    private String tipo;
    private double consumo;
    private String unidad;
    private BigDecimal costo;

    public RegistroConsumo(int id, LocalDate fecha, String tipo, double consumo, String unidad, BigDecimal costo) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.consumo = consumo;
        this.unidad = unidad;
        this.costo = costo;
    }

    public RegistroConsumo(LocalDate fecha, String tipo, double consumo, String unidad, BigDecimal costo) {
        this(-1, fecha, tipo, consumo, unidad, costo);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getConsumo() { return consumo; }
    public void setConsumo(double consumo) { this.consumo = consumo; }
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    @Override
    public String toString() {
        return "RegistroConsumo{" +
               "id=" + id +
               ", fecha=" + fecha +
               ", tipo='" + tipo + '\'' +
               ", consumo=" + consumo +
               ", unidad='" + unidad + '\'' +
               ", costo=" + costo +
               '}';
    }
}