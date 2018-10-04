package com.example.android.tesis.my_interface;

import com.example.android.tesis.model.Barco;
import com.example.android.tesis.model.Boleto;
import com.example.android.tesis.model.Confirmation;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.model.MovimientoCredito;
import com.example.android.tesis.model.Pasajero;
import com.example.android.tesis.model.Ruta;
import com.example.android.tesis.model.TarjetaCredito;
import com.example.android.tesis.model.TarjetaDebito;
import com.example.android.tesis.model.Ticket;
import com.example.android.tesis.model.TipoBoleto;
import com.example.android.tesis.model.Usuario;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by rjsan on 7/9/2018.
 */

public interface APIService {

    String SERVER_API_URL = "tesis/api";

    @GET(SERVER_API_URL + "/itinerario/findschedule/{itinerario}/")
    Call<List<Itinerario>> doGetItinerariosList(@Path("itinerario") String date);

    @GET(SERVER_API_URL + "/boleta/capacidadPuesto/{id}/")
    Call<Ticket> doGetCapacityPuesto(@Path("id") int id);

    @GET(SERVER_API_URL + "/usuario/finduser/{usuario}/")
    Call<Usuario> doGetUsuarios(@Path("usuario") String user);

    @POST(SERVER_API_URL + "/usuario")
    Call<Usuario> doCreateUser(@Body Usuario user);

    @GET(SERVER_API_URL + "/tipoboleto")
    Call<List<TipoBoleto>> doGetTipoBoletoList();

    @POST(SERVER_API_URL + "/boleta/confirmation/{id}/{uid}")
    Call<Confirmation> doCheckConfTicket(@Path("id") int itinerarioId, @Body Ticket ticket, @Path("uid") int ususarioId);

    @POST("BancoTesisFinal/apibanco/tarjetacredito/check/{monto}/{fecha}")
    Call<MovimientoCredito> doCheckPayCredit(@Path("monto") double monto, @Path("fecha") String fecha, @Body TarjetaCredito tarjetaCredito);

    @POST(SERVER_API_URL+"/boleta/movimiento/{numeroReferencia}/{usuarioId}")
    Call<Boolean> doMovimientoPayCredit(@Path("numeroReferencia") int refencia, @Path("usuarioId") int usuarioId);

    @POST("BancoTesisFinal/apibanco/tarjetacredito/start")
    Call<Boolean> doStartProcesoBancario();

}
