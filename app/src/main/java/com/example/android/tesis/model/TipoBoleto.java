package com.example.android.tesis.model;

/**
 * Created by rjsan on 7/20/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoBoleto {

    @SerializedName("fechaStatus")
    @Expose
    private String fechaStatus;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("precio")
    @Expose
    private double precio;
    @SerializedName("status")
    @Expose
    private int status;

    /**
     * No args constructor for use in serialization
     */
    public TipoBoleto() {
    }

    /**
     * @param nombre
     * @param id
     * @param precio
     * @param fechaStatus
     * @param status
     */
    public TipoBoleto(String fechaStatus, int id, String nombre, double precio, int status) {
        super();
        this.fechaStatus = fechaStatus;
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.status = status;
    }

    public String getFechaStatus() {
        return fechaStatus;
    }

    public void setFechaStatus(String fechaStatus) {
        this.fechaStatus = fechaStatus;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}