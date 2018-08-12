package com.example.android.tesis.model;

/**
 * Created by rjsan on 7/20/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Boleto {

    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("hora")
    @Expose
    private String hora;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("itinerarioId")
    @Expose
    private Itinerario itinerarioId;
    @SerializedName("tipoBoletoId")
    @Expose
    private TipoBoleto tipoBoletoId;
    @SerializedName("usuarioId")
    @Expose
    private Usuario usuarioId;

    /**
     * No args constructor for use in serialization
     */
    public Boleto() {
    }

    /**
     * @param id
     * @param hora
     * @param fecha
     * @param tipoBoletoId
     * @param usuarioId
     * @param itinerarioId
     */
    public Boleto(String fecha, String hora, int id, Itinerario itinerarioId, TipoBoleto tipoBoletoId, Usuario usuarioId) {
        super();
        this.fecha = fecha;
        this.hora = hora;
        this.id = id;
        this.itinerarioId = itinerarioId;
        this.usuarioId = usuarioId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Itinerario getItinerarioId() {
        return itinerarioId;
    }

    public void setItinerarioId(Itinerario itinerarioId) {
        this.itinerarioId = itinerarioId;
    }

    public TipoBoleto getTipoBoletoId() {
        return tipoBoletoId;
    }

    public void setTipoBoletoId(TipoBoleto tipoBoletoId) {
        this.tipoBoletoId = tipoBoletoId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

}