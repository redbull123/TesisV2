package com.example.android.tesis.model;

/**
 * Created by rjsan on 7/20/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Itinerario {

    @SerializedName("barcoId")
    @Expose
    private Barco barcoId;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("rutaId")
    @Expose
    private Ruta rutaId;
    @SerializedName("time")
    @Expose
    private String time;

    /**
     * No args constructor for use in serialization
     */
    public Itinerario() {
    }

    /**
     * @param id
     * @param time
     * @param barcoId
     * @param fecha
     * @param rutaId
     */
    public Itinerario(Barco barcoId, String fecha, int id, Ruta rutaId, String time) {
        super();
        this.barcoId = barcoId;
        this.fecha = fecha;
        this.id = id;
        this.rutaId = rutaId;
        this.time = time;
    }

    public Barco getBarcoId() {
        return barcoId;
    }

    public void setBarcoId(Barco barcoId) {
        this.barcoId = barcoId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ruta getRutaId() {
        return rutaId;
    }

    public void setRutaId(Ruta rutaId) {
        this.rutaId = rutaId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}