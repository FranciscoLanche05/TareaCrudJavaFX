package com.crudjavafx.modelo;

import java.time.LocalDate;

public class Paciente {
    private String cedula;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String genero;
    private String email;
    private String tipoSangre;
    private String alergias;
    private boolean activo = true;

    public Paciente(String cedula, String nombres, String apellidos, LocalDate fechaNacimiento, String genero, String email, String tipoSangre, String alergias) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.email = email;
        this.tipoSangre = tipoSangre;
        this.alergias = alergias;
    }

    public String getCedula() {return cedula;}
    public String getNombres() {return nombres;}
    public String getApellidos() {return apellidos;}
    public LocalDate getFechaNacimiento() {return fechaNacimiento;}
    public String getGenero() {return genero;}
    public String getEmail() {return email;}
    public String getTipoSangre() {return tipoSangre;}
    public String getAlergias() {return alergias;}
    public boolean isActivo() {return activo;}

    public void setCedula(String cedula) {this.cedula = cedula;}
    public void setNombres(String nombres) {this.nombres = nombres;}
    public void setApellidos(String apellidos) {this.apellidos = apellidos;}
    public void setFechaNacimiento(LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}
    public void setGenero(String genero) {this.genero = genero;}
    public void setEmail(String email) {this.email = email;}
    public void setTipoSangre(String tipoSangre) {this.tipoSangre = tipoSangre;}
    public void setAlergias(String alergias) {this.alergias = alergias;}
    public void setActivo(boolean activo) {this.activo = activo;}

    public String toString() {
        return "Paciente{" +
                "cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", genero='" + genero + '\'' +
                ", email='" + email + '\'' +
                ", tipoSangre='" + tipoSangre + '\'' +
                ", alergias='" + alergias + '\'' +
                ", activo=" + activo +
                '}';
    }
}
