package com.gimnasio.planes.dto;

import java.math.BigDecimal;

public class PlanDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer diasDuracion;
    private boolean accesoClases;
    private boolean accesoPiscina;
    private boolean activo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getDiasDuracion() { return diasDuracion; }
    public void setDiasDuracion(Integer diasDuracion) { this.diasDuracion = diasDuracion; }
    public boolean isAccesoClases() { return accesoClases; }
    public void setAccesoClases(boolean accesoClases) { this.accesoClases = accesoClases; }
    public boolean isAccesoPiscina() { return accesoPiscina; }
    public void setAccesoPiscina(boolean accesoPiscina) { this.accesoPiscina = accesoPiscina; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
