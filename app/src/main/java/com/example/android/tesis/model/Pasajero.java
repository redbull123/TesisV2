package com.example.android.tesis.model;

/**
 * Created by rjsan on 7/20/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pasajero {

    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("boletoId")
    @Expose
    private Boleto boletoId;
    @SerializedName("cedulaIdentidad")
    @Expose
    private String cedulaIdentidad;
    @SerializedName("fechaNacimiento")
    @Expose
    private String fechaNacimiento;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    /**
     * No args constructor for use in serialization
     */
    public Pasajero() {
    }

    /**
     * @param nombre
     * @param id
     * @param apellido
     * @param boletoId
     * @param cedulaIdentidad
     * @param fechaNacimiento
     */
    public Pasajero(String apellido, Boleto boletoId, String cedulaIdentidad, String fechaNacimiento, int id, String nombre) {
        super();
        this.apellido = apellido;
        this.boletoId = boletoId;
        this.cedulaIdentidad = cedulaIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.id = id;
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Boleto getBoletoId() {
        return boletoId;
    }

    public void setBoletoId(Boleto boletoId) {
        this.boletoId = boletoId;
    }

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

}