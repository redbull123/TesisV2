package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuentas {

    @SerializedName("clienteId")
    @Expose
    private Cliente clienteId;
    @SerializedName("fechaApertura")
    @Expose
    private String fechaApertura;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("tipo")
    @Expose
    private int tipo;

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}