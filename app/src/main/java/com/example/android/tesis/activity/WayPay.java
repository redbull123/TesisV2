package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.model.Ticket;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.MainBluetooth;
import com.example.android.tesis.network.RetrofitInstance;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rjsan on 7/25/2018.
 */

public class WayPay extends AppCompatActivity {

    int secondPassed=0;
    Timer myTimer = new Timer();
    private APIService apiService;
    private static final String LOG_TAG = WayPay.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.way_pay_layout);

        new WayPay.AsyncCaller().execute();

    }
    public void payTDD(View view){
        closeTimer();
        Intent intent = new Intent(this, MainBluetooth.class);
        startActivity(intent);
    }

    public void payTDC(View view){
        closeTimer();
        Intent intent = new Intent(this, PayCredit.class);
        startActivity(intent);
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(WayPay.this);

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

            Call<Boolean> call = apiService.doStartProcesoBancario();

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    Log.d(LOG_TAG, "codigo resultante"+response.code());
                    if(response.isSuccessful()){
                        if(response.body().booleanValue()){
                            TimerTask task = new TimerTask(){
                                @Override
                                public void run(){
                                    secondPassed++;
                                    System.out.println("segundos A= "+secondPassed);
                                    if(secondPassed==8){

                                        myTimer.cancel();
                                        myTimer.purge();
                                       }
                                }
                            };
                            myTimer.scheduleAtFixedRate(task, 1000, 1000);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e(LOG_TAG, "fallo con " + t.getMessage());
                    call.cancel();
                    popUp("Fallo la conexión al banco");
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

    public void popUp(String issue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WayPay.this);
        builder.setMessage(issue)
                .setTitle("NOTIFICACION")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(WayPay.this, TicketActivity.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void closeTimer(){
        myTimer.cancel();
        myTimer.purge();

    }

}
