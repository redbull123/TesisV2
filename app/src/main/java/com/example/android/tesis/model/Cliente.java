package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cliente {

    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("ci")
    @Expose
    private String ci;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("telefono")
    @Expose
    private String telefono;

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}