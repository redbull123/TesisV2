package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.RecyclerViewAdapter;
import com.example.android.tesis.model.Confirmation;
import com.example.android.tesis.model.Ticket;
import com.example.android.tesis.model.TipoBoleto;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.MainBluetooth;
import com.example.android.tesis.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = TicketActivity
            .class.getSimpleName();
    static double totalCompra;
    private APIService apiService;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_layout);
        new TicketActivity.AsyncCaller().execute();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(TicketActivity.this);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        callShip();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, Schedule.class);
        startActivity(myIntent);
    }

    public void callShip() {

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<Ticket> call = apiService.doGetCapacityPuesto(Schedule.selectedRoute);

        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.isSuccessful()) {
                    callRecyclershow(response.body());
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                call.cancel();
                Toast.makeText(TicketActivity.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callRecyclershow(Ticket ticket) {

        final Ticket TICKET = ticket;
        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<List<TipoBoleto>> callTipoBoleto = apiService.doGetTipoBoletoList();

        callTipoBoleto.enqueue(new Callback<List<TipoBoleto>>() {
            @Override
            public void onResponse(Call<List<TipoBoleto>> call, Response<List<TipoBoleto>> response) {
                callRecycler(response.body(), TICKET);
            }

            @Override
            public void onFailure(Call<List<TipoBoleto>> call, Throwable t) {

            }
        });
    }

    public void callRecycler(List<TipoBoleto> tipoBoletoList, Ticket ticket) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ticket, (ArrayList<TipoBoleto>) tipoBoletoList);
        rv.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        quizPrueba(rv);
    }

    public void quizPrueba(final RecyclerView recyclerView) {

        TextView textView = (TextView) findViewById(R.id.informacion);
        textView.setText(Schedule.route + "\n" +
                Schedule.fecha + "\n" +
                Schedule.hora);

        Button continueButton = (Button) findViewById(R.id.continue_ticket);
        continueButton.setEnabled(true);

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TicketActivity.this);
                builder.setMessage("Boletos: \n" +
                        "Personas: " + RecyclerViewAdapter.personas + "\n" +
                        "Vehiculo: " + RecyclerViewAdapter.autos + "\n" +
                        "Camionetas: " + RecyclerViewAdapter.autobuses + "\n" +
                        "Motos: " + RecyclerViewAdapter.motos + "\n" +
                        "Carga: " + RecyclerViewAdapter.cargas + "\n" +
                        "TOTAL : BsS." + (RecyclerViewAdapter.totalPersonas +
                        RecyclerViewAdapter.totalAutos + RecyclerViewAdapter.totalAutobuses
                        + RecyclerViewAdapter.totalMotos + RecyclerViewAdapter.totalCargas))
                        .setTitle("Confirmación de Compra")
                        .setCancelable(false)
                        .setNeutralButton("Continuar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        confirmationTicket();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void confirmationTicket() {

        Ticket ticket = new Ticket();

        ticket.setCapacidadPersonas(RecyclerViewAdapter.personas);
        ticket.setCapacidadAutos(RecyclerViewAdapter.autos);
        ticket.setCapacidadAutobus(RecyclerViewAdapter.autobuses);
        ticket.setCapacidadMotos(RecyclerViewAdapter.motos);
        ticket.setCapacidadCarga(RecyclerViewAdapter.cargas);

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<Confirmation> call = apiService.doCheckConfTicket(Schedule.selectedRoute, ticket, HomeUser.idUser);

        call.enqueue(new Callback<Confirmation>() {
            @Override
            public void onResponse(Call<Confirmation> call, Response<Confirmation> response) {
                if (response.isSuccessful()) {
                    if (response.body().isConfirmation()) {
                        Intent route = new Intent(TicketActivity.this, WayPay.class);
                        startActivity(route);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TicketActivity.this);
                        builder.setMessage("Cantidad de Boleto no disponible")
                                .setTitle("Informacion de Compra")
                                .setCancelable(false)
                                .setNeutralButton("Cerrar",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Confirmation> call, Throwable t) {
                call.cancel();
                Toast.makeText(TicketActivity.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double total) {
        this.totalCompra = total;
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(TicketActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            callShip();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }

    }


}
