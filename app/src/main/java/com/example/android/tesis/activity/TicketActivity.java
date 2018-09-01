package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.ScheduleModelAdapter;
import com.example.android.tesis.model.Barco;
import com.example.android.tesis.model.Boleto;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {

    private static final String LOG_TAG = TicketActivity
            .class.getSimpleName();
    private APIService apiService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mantenimiento);

        Log.d(LOG_TAG, "valor en el listener" + Schedule.selectedRoute);

        new TicketActivity.AsyncCaller().execute();

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
            callTicket();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }

    }

    public void callShip(){

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<Barco> call = apiService.doGetCapacityPuesto(Schedule.selectedRoute);
        call.enqueue(new Callback<Barco>() {
            @Override
            public void onResponse(Call<Barco> call, Response<Barco> response) {

                Log.d(LOG_TAG, "respuesta del servidor"+ response.body());
            }
            @Override
            public void onFailure(Call<Barco> call, Throwable t) {
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                call.cancel();
                Toast.makeText(TicketActivity.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public void callTicket(){

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<List<Boleto>> call = apiService.doGetTicket(Schedule.selectedRoute);
        call.enqueue(new Callback<List<Boleto>>() {
            @Override
            public void onResponse(Call<List<Boleto>> call, Response<List<Boleto>> response) {

                Log.d(LOG_TAG, "respuesta del servidor"+ response.body());
            }
            @Override
            public void onFailure(Call<List<Boleto>> call, Throwable t) {
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                call.cancel();
                Toast.makeText(TicketActivity.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
}
