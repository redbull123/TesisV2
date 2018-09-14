package com.example.android.tesis.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.adapter.ScheduleModelAdapter;
import com.example.android.tesis.model.Credit_Card;
import com.example.android.tesis.model.Itinerario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayCredit extends AppCompatActivity{

    private APIService apiService;  //OJO
    private static final String LOG_TAG = Schedule.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_credit_layout);
    }


    public void continuePay(View view){
        String numbercard;
        String expirationdate;
        String cvc;
        String tarjetahabiente;

        EditText nameText = (EditText) findViewById(R.id.number_card);
        numbercard = nameText.getText().toString();

        EditText expirationdateText = (EditText) findViewById(R.id.expiration_date);
        expirationdate = expirationdateText.getText().toString();

        EditText cvcText = (EditText) findViewById(R.id.cvc);
        cvc = cvcText.getText().toString();

        EditText tarjetahabienteText = (EditText) findViewById(R.id.tarjeta_habiente);
        tarjetahabiente = tarjetahabienteText.getText().toString();


        Credit_Card creditCard = new Credit_Card(numbercard, expirationdate,cvc, tarjetahabiente);
//        AsyncCaller();
    }

//
//    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
//
//        ProgressDialog pdLoading = new ProgressDialog(PayCredit.this);
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
//            if (apiService == null) {
//                apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
//            } else {
//                Log.d(LOG_TAG, "el apiService está inicializado");
//            }
//            Call<Credit_Card> call = apiService.doGetItinerariosList();
//
//            call.enqueue(new Callback<Credit_Card>() {
//                @Override
//                public void onResponse(Call<Credit_Card> call, Response<Credit_Card> response) {
//                    Log.d(LOG_TAG, response.code() + " ");
//
//                }
//
//                @Override
//                public void onFailure(Call<Credit_Card> call, Throwable t) {
//                    Log.e(LOG_TAG, "fallo con " + t.getMessage());
//                    call.cancel();
//                    Toast.makeText(PayCredit.this, "Problemas de Conexión",
//                            Toast.LENGTH_LONG).show();
//                }
//            });
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
//

}
