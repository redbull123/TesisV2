package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.ModelItinerarioAdapter;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rjsan on 5/13/2018.
 */
public class Schedule extends AppCompatActivity {

    private static final String LOG_TAG = Schedule.class.getSimpleName();
    private List<Itinerario> listItinerarios = new ArrayList<>();
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<List<Itinerario>> call = apiService.doGetItinerariosList();

        // Set up progress before call
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Schedule.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Cargando....");
        progressDialog.setTitle("Itinerario de Conferry C.A.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        call.enqueue(new Callback<List<Itinerario>>() {
            @Override
            public void onResponse(Call<List<Itinerario>> call, Response<List<Itinerario>> response) {
                Log.d(LOG_TAG, response.code() + " ");
                listItinerarios = response.body();
                ListView listView = (ListView) findViewById(R.id.list);
                ModelItinerarioAdapter adapter = new ModelItinerarioAdapter(Schedule.this, 0, listItinerarios);
                progressDialog.dismiss();
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Itinerario>> call, Throwable t) {
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                progressDialog.dismiss();
                call.cancel();
            }
        });

        ListView list = (ListView) findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (HomeUser.ifLoggeado() == 1) {
                    Intent iti = new Intent(Schedule.this, PerfilPrueba.class);
                    startActivity(iti);
                } else if (HomeUser.ifLoggeado() == 0) {
                    Intent iti = new Intent(Schedule.this, Login.class);
                    startActivity(iti);
                }

            }
        });

    }

}
