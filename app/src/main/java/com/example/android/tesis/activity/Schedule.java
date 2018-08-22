package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.ScheduleModelAdapter;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rjsan on 5/13/2018.
 */
public class Schedule extends AppCompatActivity {

    private static final String LOG_TAG = Schedule.class.getSimpleName();
    private List<Itinerario> scheduleList = new ArrayList<>();
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_layout);

        new AsyncCaller().execute();

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
    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(Schedule.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (apiService == null) {
                apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
            } else {
                Log.d(LOG_TAG, "el apiService está inicializado");
            }
            Call<List<Itinerario>> call = apiService.doGetItinerariosList();
            call.enqueue(new Callback<List<Itinerario>>() {
                @Override
                public void onResponse(Call<List<Itinerario>> call, Response<List<Itinerario>> response) {
                    Log.d(LOG_TAG, response.code() + " ");
                    scheduleList = response.body();
                    int i = 0;
                    for(Itinerario iti : scheduleList){
                        iti.setFecha(parseDateToddMMyyyy(iti.getFecha()));
                        iti.setTime(parseTimeToddMMyyyy(iti.getTime()));
                        scheduleList.set(i,iti);
                        i++; }
                    ListView listView = (ListView) findViewById(R.id.list);
                    ScheduleModelAdapter adapter = new ScheduleModelAdapter(Schedule.this, 0, scheduleList);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<Itinerario>> call, Throwable t) {
                    Log.e(LOG_TAG, "fallo con " + t.getMessage());
                    call.cancel();
                    Toast.makeText(Schedule.this, "Problemas de Conexión",
                            Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        String outputPattern = "EEE, MMM d";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String parseTimeToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ssZ";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
