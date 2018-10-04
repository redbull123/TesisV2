package com.example.android.tesis.model;

public class MovimientoCredito {

    boolean comprobante = false;
    int numeroReferencia;
    String numeroTarjeta;

    public MovimientoCredito() {
    }

    public MovimientoCredito(boolean comprobante, int numeroReferencia, String numeroTarjeta) {
        this.comprobante = comprobante;
        this.numeroReferencia = numeroReferencia;
        this.numeroTarjeta = numeroTarjeta;
    }

    public boolean isComprobante() {
        return comprobante;
    }

    public void setComprobante(boolean comprobante) {
        this.comprobante = comprobante;
    }

    public int getNumeroReferencia() {
        return numeroReferencia;
    }

    public void setNumeroReferencia(int numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
}
