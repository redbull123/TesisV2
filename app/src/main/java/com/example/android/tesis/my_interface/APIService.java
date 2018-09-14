package com.example.android.tesis.my_interface;

import com.example.android.tesis.model.Barco;
import com.example.android.tesis.model.Boleto;
import com.example.android.tesis.model.Confirmation;
import com.example.android.tesis.model.Credit_Card;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.model.Pasajero;
import com.example.android.tesis.model.Ruta;
import com.example.android.tesis.model.Ticket;
import com.example.android.tesis.model.TipoBoleto;
import com.example.android.tesis.model.Usuario;

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

    @GET(SERVER_API_URL + "/barco")
    Call<List<Barco>> doGetBarcosList();

    @GET(SERVER_API_URL + "/itinerario/findschedule/{itinerario}/")
    Call<List<Itinerario>> doGetItinerariosList(@Path("itinerario") String date);

    @GET(SERVER_API_URL + "/boleta/capacidadPuesto/{id}/")         // Se trae un objeto tipo Ticket
    Call<Ticket> doGetCapacityPuesto(@Path("id") int id);

    @GET(SERVER_API_URL + "/pasajero")
    Call<List<Pasajero>> doGetPasajerosList();

    @GET(SERVER_API_URL + "/ruta")
    Call<List<Ruta>> doGetRutasList();

    @GET(SERVER_API_URL + "/boleta")
    Call<List<Boleto>> doGetBoletasList();

    @GET(SERVER_API_URL + "/usuario/finduser/{usuario}/")
    Call<Usuario> doGetUsuarios(@Path("usuario") String user);

    @POST(SERVER_API_URL + "/usuario")
    Call<Usuario> doCreateUser(@Body Usuario user);

    @GET(SERVER_API_URL + "/tipoboleto")
    Call<List<TipoBoleto>> doGetTipoBoletoList();

    @GET(SERVER_API_URL + "/tipoboleto")
    Call<Credit_Card> doCheckCreditCard();

    @POST(SERVER_API_URL + "/boleta/confirmation/{id}/{uid}")
    Call<Confirmation> doCheckConfTicket(@Path("id") int id, @Body Ticket ticket, @Path("uid") int uid);


}
