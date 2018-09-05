package com.example.android.tesis.model;

public class Ticket {

    int capacidadPersonas;
    int capacidadAutos;
    int capacidadCarga;
    int capacidadAutobus;
    int capacidadMotos;

    public Ticket() {
    }

    public Ticket(int capacidadPersonas, int capacidadAutos, int capacidadCarga, int capacidadAutobus, int capacidadMotos) {
        this.capacidadPersonas = capacidadPersonas;
        this.capacidadAutos = capacidadAutos;
        this.capacidadCarga = capacidadCarga;
        this.capacidadAutobus = capacidadAutobus;
        this.capacidadMotos = capacidadMotos;
    }

    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
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

    public int getCapacidadAutobus() {
        return capacidadAutobus;
    }

    public void setCapacidadAutobus(int capacidadAutobus) {
        this.capacidadAutobus = capacidadAutobus;
    }

    public int getCapacidadMotos() {
        return capacidadMotos;
    }

    public void setCapacidadMotos(int capacidadMotos) {
        this.capacidadMotos = capacidadMotos;
    }



}
