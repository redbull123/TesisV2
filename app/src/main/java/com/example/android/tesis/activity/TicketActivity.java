package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.model.Confirmation;
import com.example.android.tesis.model.Ticket;
import com.example.android.tesis.model.Usuario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {

    private static final String LOG_TAG = TicketActivity
            .class.getSimpleName();
    private APIService apiService;
    int incrementOneBottom=0;
    int incrementTwoBottom=0;
    int incrementThreeBottom=0;
    int incrementFourBottom=0;
    int incrementFiveBottom=0;
    int incrementSixBottom=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets_layout);
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

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }

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
               if(response.isSuccessful()){
                quizPrueba(response.body());}
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                call.cancel();
                Toast.makeText(TicketActivity.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void quizPrueba(Ticket ticket){

                TextView textOne = (TextView) findViewById(R.id.avaibable_one);
                String var = String.valueOf(ticket.getCapacidadPersonas());
                textOne.setText(var);

                TextView textTwo = (TextView) findViewById(R.id.avaibable_two);
                String varTwo = String.valueOf(ticket.getCapacidadPersonas());
                textTwo.setText(varTwo);

                TextView textThree = (TextView) findViewById(R.id.avaibable_three);
                String varThree = String.valueOf(ticket.getCapacidadAutos());
                textThree.setText(varThree);

                TextView textFour = (TextView) findViewById(R.id.avaibable_four);
                String varFour = String.valueOf(ticket.getCapacidadAutobus());
                textFour.setText(varFour);

                TextView textFive = (TextView) findViewById(R.id.avaibable_five);
                String varFive = String.valueOf(ticket.getCapacidadMotos());
                textFive.setText(varFive);

                TextView textSix = (TextView) findViewById(R.id.avaibable_six);
                String varSix = String.valueOf(ticket.getCapacidadCarga());
                textSix.setText(varSix);

        Button continueButton = (Button) findViewById(R.id.continue_ticket);
        continueButton.setEnabled(true);

        continueButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TicketActivity.this);
                    builder.setMessage("Boletos: \n"+
                                    "Personas: "+ (incrementOneBottom+incrementTwoBottom)+"\n"+
                            "Vehiculo: "+ incrementThreeBottom+"\n"+
                            "Camionetas: "+ incrementFourBottom+"\n"+
                            "Motos: "+ incrementFiveBottom+"\n"+
                            "Carga: "+ incrementSixBottom+"\n")
                            .setTitle("Confirmación de Compra")
                            .setCancelable(false)
                            .setNeutralButton("Continuar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
//                                            Intent route = new Intent(TicketActivity.this, PerfilPrueba.class);
//                                            startActivity(route);
                                            confirmationTicket();

                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();


            }
        });


    }


//
//    private class AsyncCallerTicket extends AsyncTask<Void, Void, Void> {
//        ProgressDialog pdLoading = new ProgressDialog(TicketActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pdLoading.setMessage("\tLoading...");
//            pdLoading.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
//            confirmationTicket();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            pdLoading.dismiss();
//        }
//
//    }

    public void confirmationTicket(){

        Ticket ticket = new Ticket();

        ticket.setCapacidadPersonas(incrementOneBottom+incrementTwoBottom);
        ticket.setCapacidadAutos(incrementThreeBottom);
        ticket.setCapacidadAutobus(incrementFourBottom);
        ticket.setCapacidadMotos(incrementFiveBottom);
        ticket.setCapacidadCarga(incrementSixBottom);

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<Confirmation> call = apiService.doCheckConfTicket(Schedule.selectedRoute, ticket, HomeUser.idUser);

        call.enqueue(new Callback<Confirmation>() {
            @Override
            public void onResponse(Call<Confirmation> call, Response<Confirmation> response) {
                if(response.isSuccessful()){
                    Log.d(LOG_TAG, "Respuesta del servidor"+ response.body().isConfirmation());
                    if(response.body().isConfirmation()){

                        Toast.makeText(TicketActivity.this, "LISTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO",
                                Toast.LENGTH_LONG).show();

                        Intent route = new Intent(TicketActivity.this, PayCredit.class);
                                           startActivity(route);
                    }
                    else{
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
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                call.cancel();
                Toast.makeText(TicketActivity.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public void incrementOne(View view){
        incrementOneBottom=++incrementOneBottom;
        TextView display = (TextView) findViewById(R.id.count_one);
        display.setText(" "+ incrementOneBottom);
    }
    public void decrementOne(View view){
        incrementOneBottom=--incrementOneBottom;

        TextView display = (TextView) findViewById(R.id.count_one);

        if(incrementOneBottom>1){
            display.setText(" "+ incrementOneBottom);}
        else if(incrementOneBottom<=0){display.setText(" "+ 1);
            incrementOneBottom=1;}
    }


    public void incrementTwo(View view){
        incrementTwoBottom=++incrementTwoBottom;
        TextView display = (TextView) findViewById(R.id.count_two);
        display.setText(" "+ incrementTwoBottom);
    }
    public void decrementTwo(View view){
        incrementTwoBottom=--incrementTwoBottom;

        TextView display = (TextView) findViewById(R.id.count_two);

        if(incrementTwoBottom>1){
            display.setText(" "+ incrementTwoBottom);}
        else if(incrementTwoBottom<=0){display.setText(" "+ 1);
            incrementTwoBottom=1;}
    }

    public void incrementThree(View view){
        incrementThreeBottom=++incrementThreeBottom;
        TextView display = (TextView) findViewById(R.id.count_three);
        display.setText(" "+ incrementThreeBottom);
    }
    public void decrementThree(View view){
        incrementThreeBottom=--incrementThreeBottom;

        TextView display = (TextView) findViewById(R.id.count_three);

        if(incrementThreeBottom>1){
            display.setText(" "+ incrementThreeBottom);}
        else if(incrementThreeBottom<=0){display.setText(" "+ 1);
            incrementThreeBottom=1;}
    }

    public void incrementFour(View view){
        incrementFourBottom=++incrementFourBottom;
        TextView display = (TextView) findViewById(R.id.count_four);
        display.setText(" "+ incrementFourBottom);
    }
    public void decrementFour(View view){
        incrementFourBottom=--incrementFourBottom;

        TextView display = (TextView) findViewById(R.id.count_four);

        if(incrementFourBottom>1){
            display.setText(" "+ incrementFourBottom);}
        else if(incrementFourBottom<=0){display.setText(" "+ 1);
            incrementFourBottom=1;}    }

    public void incrementFive(View view){
        incrementFiveBottom=++incrementFiveBottom;
        TextView display = (TextView) findViewById(R.id.count_five);
        display.setText(" "+ incrementFiveBottom);
    }
    public void decrementFive(View view){
        incrementFiveBottom=--incrementFiveBottom;

        TextView display = (TextView) findViewById(R.id.count_five);

        if(incrementFiveBottom>1){
            display.setText(" "+ incrementFiveBottom);}
        else if(incrementFiveBottom<=0){display.setText(" "+ 1);
            incrementFiveBottom=1;}
    }

    public void incrementSix(View view){
        incrementSixBottom=++incrementSixBottom;
        TextView display = (TextView) findViewById(R.id.count_six);
        display.setText(" "+ incrementSixBottom);
    }
    public void decrementSix(View view){
        incrementSixBottom=--incrementSixBottom;

        TextView display = (TextView) findViewById(R.id.count_six);

        if(incrementSixBottom>1){
            display.setText(" "+ incrementSixBottom);}
        else if(incrementSixBottom<=0){display.setText(" "+ 1);
            incrementSixBottom=1;}    }
}
