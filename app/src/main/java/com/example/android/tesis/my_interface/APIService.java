package com.example.android.tesis.my_interface;

import com.example.android.tesis.model.Barco;
import com.example.android.tesis.model.Boleto;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.model.Pasajero;
import com.example.android.tesis.model.ReCapchat;
import com.example.android.tesis.model.Ruta;
import com.example.android.tesis.model.TipoBoleto;
import com.example.android.tesis.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by rjsan on 7/9/2018.
 */

public interface APIService {

    String SERVER_API_URL = "tesis/api";

    @GET(SERVER_API_URL + "/barco")
    Call<List<Barco>> doGetBarcosList();

    @GET(SERVER_API_URL + "/itinerario")
    Call<List<Itinerario>> doGetItinerariosList();

    @GET(SERVER_API_URL + "/pasajero")
    Call<List<Pasajero>> doGetPasajerosList();

    @GET(SERVER_API_URL + "/ruta")
    Call<List<Ruta>> doGetRutasList();

    @GET(SERVER_API_URL + "/boleta")
    Call<List<Boleto>> doGetBoletasList();

    @GET(SERVER_API_URL + "/usuario")
    Call<List<Usuario>> doGetUsuariosList();

    @POST(SERVER_API_URL + "/usuario")
    Call<Usuario> doCreateUser(@Body Usuario user);

    @GET(SERVER_API_URL + "/tipoboleto")
    Call<List<TipoBoleto>> doGetTipoBoletoList();

    @FormUrlEncoded
    @POST("siteverify")
    Call<ReCapchat> verifyCaptcha(@Field("secret") String secret, @Field("response") String response);
}
