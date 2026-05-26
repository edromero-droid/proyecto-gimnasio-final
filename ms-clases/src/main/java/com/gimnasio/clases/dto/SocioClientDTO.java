package com.gimnasio.clases.dto;

import java.time.LocalDate;

public class SocioClientDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String estado;
    private String nivelMembresia;
    private String tipoDocumento;
    private String numeroDocumento;
    private LocalDate membresiaFin;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNivelMembresia() { return nivelMembresia; }
    public void setNivelMembresia(String nivelMembresia) { this.nivelMembresia = nivelMembresia; }
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public LocalDate getMembresiaFin() { return membresiaFin; }
    public void setMembresiaFin(LocalDate membresiaFin) { this.membresiaFin = membresiaFin; }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public void validarDatos(String emailIngresado, String nombreIngresado, String documentoIngresado) {
        if (emailIngresado != null && !emailIngresado.isEmpty() &&
            !emailIngresado.equalsIgnoreCase(this.email)) {
            throw new RuntimeException(
                "El email ingresado '" + emailIngresado + "' no corresponde al socio con ID " + this.id);
        }
        if (nombreIngresado != null && !nombreIngresado.isEmpty()) {
            String nombreReal = this.getNombreCompleto().toLowerCase().trim();
            String nombreIngresadoLower = nombreIngresado.toLowerCase().trim();
            if (!nombreReal.contains(nombreIngresadoLower) &&
                !nombreIngresadoLower.contains(this.nombre.toLowerCase())) {
                throw new RuntimeException(
                    "El nombre ingresado '" + nombreIngresado + "' no corresponde al socio con ID " + this.id);
            }
        }
        if (documentoIngresado != null && !documentoIngresado.isEmpty() &&
            this.numeroDocumento != null &&
            !documentoIngresado.equalsIgnoreCase(this.numeroDocumento)) {
            throw new RuntimeException(
                "El documento ingresado '" + documentoIngresado + "' no corresponde al socio con ID " + this.id);
        }
    }
}
