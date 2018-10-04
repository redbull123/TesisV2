package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovimientoTarjeta {

    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("monto")
    @Expose
    private double monto;
    @SerializedName("tarjetaCreditoId")
    @Expose
    private TarjetaCredito tarjetaCreditoId;
    @SerializedName("tipo")
    @Expose
    private int tipo;

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

    public TarjetaCredito getTarjetaCreditoId() {
        return tarjetaCreditoId;
    }

    public void setTarjetaCreditoId(TarjetaCredito tarjetaCreditoId) {
        this.tarjetaCreditoId = tarjetaCreditoId;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}