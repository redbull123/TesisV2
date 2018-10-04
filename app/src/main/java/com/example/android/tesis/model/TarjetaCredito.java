package com.example.android.tesis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TarjetaCredito {

    @SerializedName("clienteId")
    @Expose
    private Cliente clienteId;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("limite")
    @Expose
    private double limite;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("saldo")
    @Expose
    private double saldo;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("cvc")
    @Expose
    private int cvc;
    @SerializedName("fechaVencimiento")
    @Expose
    private Date fechaVencimiento;

    public TarjetaCredito() {
    }

    public TarjetaCredito(Cliente clienteId, String marca, String numero, int cvc, Date fechaVencimiento) {
        this.clienteId = clienteId;
        this.marca = marca;
        this.numero = numero;
        this.cvc = cvc;
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public Cliente getClienteId() {
        return clienteId;
    }

    public void setClienteId(Cliente clienteId) {
        this.clienteId = clienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
