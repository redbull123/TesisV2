package com.example.android.tesis.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.RecyclerViewAdapter;
import com.example.android.tesis.model.Cliente;
import com.example.android.tesis.model.MovimientoCredito;
import com.example.android.tesis.model.TarjetaCredito;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;
import com.example.android.tesis.utils.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayCredit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private static final String LOG_TAG = Schedule.class.getSimpleName();
    private APIService apiService;  //OJO
    static String tipoTDC;
    static TarjetaCredito mTarjetaCredito =new TarjetaCredito();
    Timer myTimer = new Timer();
    WayPay wayPay = new WayPay();
    int second =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_credit_layout);

        //get the spinner from the xml.
        Spinner dropdown = (Spinner) findViewById(R.id.marcas_credit_select);
//create a list of items for the spinner.
        String[] items = new String[]{"Seleccione","VISA", "MASTERCARD"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        TextView textMonto = (TextView) findViewById(R.id.total_montotdc);
        textMonto.setText(""+(RecyclerViewAdapter.totalPersonas+RecyclerViewAdapter.totalAutos+RecyclerViewAdapter.totalAutobuses
                        +RecyclerViewAdapter.totalMotos+RecyclerViewAdapter.totalCargas));
        Button dateSelector = (Button) findViewById(R.id.fecha_vencimiento_credit);

        dateSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.marcas_credit_select);
        spinner.setOnItemSelectedListener(this);


        TimerTask task = new TimerTask(){
        @Override
        public void run(){
            second++;
            System.out.println("segundos B= "+second);
            if(second==15){
                myTimer.cancel();
                myTimer.purge();
            }
        }
    };
                            myTimer.scheduleAtFixedRate(task, 1000, 1000);

    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void continuePay(View view) {
        String numbercard;
        String expirationdate;
        int cvc;
        String tarjetahabiente;
        String tarjetahabienteApellido;

        closeTimer();

        EditText nameText = (EditText) findViewById(R.id.number_card);
        numbercard = nameText.getText().toString();

        Button expirationdateText = (Button) findViewById(R.id.fecha_vencimiento_credit);
        expirationdate = expirationdateText.getText().toString();

        EditText cvcText = (EditText) findViewById(R.id.cvc);
        cvc = Integer.parseInt(cvcText.getText().toString());

        EditText tarjetahabienteText = (EditText) findViewById(R.id.tarjeta_habiente);
        tarjetahabiente = tarjetahabienteText.getText().toString();

        EditText tarjetahabienteTextApellido = (EditText) findViewById(R.id.tarjeta_habiente_apellido);
        tarjetahabienteApellido = tarjetahabienteTextApellido.getText().toString();

        SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");

        Cliente cliente = new Cliente();
        cliente.setNombre(tarjetahabiente);
        cliente.setApellido(tarjetahabienteApellido);
        TarjetaCredito tarjetaCredito = null;
        try {
            tarjetaCredito = new TarjetaCredito(cliente, getTipoTDC(), numbercard, cvc, formateador.parse(expirationdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fillObject(tarjetaCredito);
        new AsyncCaller().execute();
    }

    private void fillObject(TarjetaCredito tc){
        this.mTarjetaCredito=tc;
    }
    private TarjetaCredito obtainObject(){
        return mTarjetaCredito;
    }

    private String getTipoTDC(){
        return tipoTDC;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                    tipoTDC="Visa";

                break;
            case 2:
                tipoTDC="MasterCard";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat formateador = new SimpleDateFormat("MMM d, yyyy");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Button dateSelector = (Button) findViewById(R.id.fecha_vencimiento_credit);
        dateSelector.setText(formateador.format(c.getTime()));

    }


    private class AsyncCaller extends AsyncTask<Void, Void, Void> {

        ProgressDialog pdLoading = new ProgressDialog(PayCredit.this);

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

            TarjetaCredito tdcccc= obtainObject();
            Log.d(LOG_TAG, " LA TARJETA ES ESTAAAAAAA ="+ tdcccc.getNumero()
                    +"fecha del veceiemiento"+ tdcccc.getFechaVencimiento()+
            "tipo de tarejta de credito"+ tdcccc.getMarca());

            SimpleDateFormat formateador = new SimpleDateFormat("MMM dd, yyyy");

            Call<MovimientoCredito> call = apiService.doCheckPayCredit((RecyclerViewAdapter.totalPersonas+RecyclerViewAdapter.totalAutos+RecyclerViewAdapter.totalAutobuses
                    +RecyclerViewAdapter.totalMotos+RecyclerViewAdapter.totalCargas),formateador.format(tdcccc.getFechaVencimiento()), mTarjetaCredito);

            call.enqueue(new Callback<MovimientoCredito>() {
                @Override
                public void onResponse(Call<MovimientoCredito> call, Response<MovimientoCredito> response) {
                    Log.d(LOG_TAG, "codigo resultante"+response.code());
                    if(response.isSuccessful()){
                        if(response.body().isComprobante()){
                    Log.d(LOG_TAG, "ESTA FUE LA RESPUESTA DEL SERVIDOR FUE TRUE");

                            Toast.makeText(PayCredit.this, "TRANSACCION EXITOSA"+ response.body().getNumeroReferencia(),
                                    Toast.LENGTH_LONG).show();

                         creditoPaso(response.body().getNumeroReferencia());

                        }
                    }
                }

                @Override
                public void onFailure(Call<MovimientoCredito> call, Throwable t) {
                    Log.e(LOG_TAG, "fallo con " + t.getMessage());
                    call.cancel();
                    Toast.makeText(PayCredit.this, "Problemas de Conexión",
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

    public void creditoPaso(int numeroReferencia){
        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<Boolean> call = apiService.doMovimientoPayCredit(numeroReferencia, HomeUser.idUser);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.d(LOG_TAG, "codigo resultante"+response.code());
                if(response.isSuccessful()){
                    if(response.body().booleanValue()){
                        Toast.makeText(PayCredit.this, "TRANSACCION EXITOSA",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                call.cancel();
                Toast.makeText(PayCredit.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
            }
        });

    }
    public void popUp(String issue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PayCredit.this);
        builder.setMessage(issue)
                .setTitle("NOTIFICACION")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(PayCredit.this, TicketActivity.class);
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

