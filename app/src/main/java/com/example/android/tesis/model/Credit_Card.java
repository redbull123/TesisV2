package com.example.android.tesis.model;

public class Credit_Card {
    String numbercard;
    String expirationdate;
    String cvc;
    String tarjetahabiente;

    public Credit_Card(String numbercard, String expirationdate, String cvc, String tarjetahabiente) {
        this.numbercard = numbercard;
        this.expirationdate = expirationdate;
        this.cvc = cvc;
        this.tarjetahabiente = tarjetahabiente;
    }

    public String getNumbercard() {
        return numbercard;
    }

    public void setNumbercard(String numbercard) {
        this.numbercard = numbercard;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getTarjetahabiente() {
        return tarjetahabiente;
    }

    public void setTarjetahabiente(String tarjetahabiente) {
        this.tarjetahabiente = tarjetahabiente;
    }
}
