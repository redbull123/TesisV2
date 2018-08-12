package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rjsan on 7/11/2018.
 */

public class Barco {


    @SerializedName("nombre")
    @Expose
    String nombre;
    @SerializedName("capacidadAutobus")
    @Expose
    private int capacidadAutobus;
    @SerializedName("capacidadAutos")
    @Expose
    private int capacidadAutos;
    @SerializedName("capacidadCarga")
    @Expose
    private int capacidadCarga;
    @SerializedName("capacidadMoto")
    @Expose
    private int capacidadMoto;
    @SerializedName("capacidadPersonas")
    @Expose
    private int capacidadPersonas;
    @SerializedName("id")
    @Expose
    private int id;

    public int getCapacidadAutobus() {
        return capacidadAutobus;
    }

    public void setCapacidadAutobus(int capacidadAutobus) {
        this.capacidadAutobus = capacidadAutobus;
    }

    public int getCapacidadAutos() {
        return capacidadAutos;
    }

    public void setCapacidadAutos(int capacidadAutos) {
        this.capacidadAutos = capacidadAutos;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getCapacidadMoto() {
        return capacidadMoto;
    }

    public void setCapacidadMoto(int capacidadMoto) {
        this.capacidadMoto = capacidadMoto;
    }

    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
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

    @Override
    public String toString() {
        return "Barco{" +
                "capacidadAutobus=" + capacidadAutobus +
                ", capacidadAutos=" + capacidadAutos +
                ", capacidadCarga=" + capacidadCarga +
                ", capacidadMoto=" + capacidadMoto +
                ", capacidadPersonas=" + capacidadPersonas +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
