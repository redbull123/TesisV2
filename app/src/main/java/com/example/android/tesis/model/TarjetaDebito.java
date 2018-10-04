package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TarjetaDebito {

    @SerializedName("codigoSeguridad")
    @Expose
    private int codigoSeguridad;
    @SerializedName("cuentasId")
    @Expose
    private Cuentas cuentasId;
    @SerializedName("fechaVencimiento")
    @Expose
    private String fechaVencimiento;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("numero")
    @Expose
    private String numero;

    public TarjetaDebito(int codigoSeguridad, Cuentas cuentasId, String fechaVencimiento, String marca, String numero) {
        this.codigoSeguridad = codigoSeguridad;
        this.cuentasId = cuentasId;
        this.fechaVencimiento = fechaVencimiento;
        this.marca = marca;
        this.numero = numero;
    }

    public int getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(int codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public Cuentas getCuentasId() {
        return cuentasId;
    }

    public void setCuentasId(Cuentas cuentasId) {
        this.cuentasId = cuentasId;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
