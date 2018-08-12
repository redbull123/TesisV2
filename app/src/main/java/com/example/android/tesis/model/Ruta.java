package com.example.android.tesis.model;

/**
 * Created by rjsan on 7/20/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ruta {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("ruta")
    @Expose
    private String ruta;

    /**
     * No args constructor for use in serialization
     */
    public Ruta() {
    }

    /**
     * @param id
     * @param ruta
     */
    public Ruta(int id, String ruta) {
        super();
        this.id = id;
        this.ruta = ruta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}