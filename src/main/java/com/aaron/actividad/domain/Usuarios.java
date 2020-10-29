package com.aaron.actividad.domain;

public class Usuarios {

    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String subs;

    public Usuarios(String nombre, String apellidos, String dni, String telefono, String subs) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.subs = subs;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String matricula) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSubs() {
        return subs;
    }

    public void setSubs(String subs) {
        this.subs = subs;
    }
}
