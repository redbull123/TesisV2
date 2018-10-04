package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovimientosCuenta {

    @SerializedName("cuentasId")
    @Expose
    private Cuentas cuentasId;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("monto")
    @Expose
    private double monto;
    @SerializedName("tipo")
    @Expose
    private int tipo;

    public Cuentas getCuentasId() {
        return cuentasId;
    }

    public void setCuentasId(Cuentas cuentasId) {
        this.cuentasId = cuentasId;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}