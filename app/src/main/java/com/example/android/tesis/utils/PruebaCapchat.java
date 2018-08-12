package com.example.android.tesis.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.tesis.R;
import com.example.android.tesis.model.ReCapchat;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.RetrofitInstance;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.android.tesis.R.id.result;


/**
 * Created by rjsan on 7/28/2018.
 */

public class PruebaCapchat extends AppCompatActivity {

    private static final String LOG_TAG = PruebaCapchat.class.getSimpleName();
    final String SiteKey = "6Len6mYUAAAAAEPuzmTeEHECH5OPLrzF-3xdr_VU";
    final String SecretKey = "6Len6mYUAAAAANUMyCB1uPdGgpzP5NvO2kXz08-c";

    Button btnRequest;
    TextView tvResult;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catchap);

        tvResult = (TextView) findViewById(result);
        btnRequest = (Button) findViewById(R.id.request);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    SafetyNet.getClient(PruebaCapchat.this).verifyWithRecaptcha(SiteKey)
            .addOnSuccessListener(PruebaCapchat.this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                @Override
                public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                    if (!response.getTokenResult().isEmpty()) {
                        //Validate the user response token using the
                        //reCAPTCHA siteverify API
                        //new SendPostRequest.execute
                        siteVerify(response.getTokenResult());
                    }
                }
            })
            .addOnFailureListener(PruebaCapchat.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ApiException) {
                        ApiException apiException = (ApiException) e;
                        Log.d(LOG_TAG, "Error message: " +
                                CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
                    } else {
                        Log.d(LOG_TAG, "Unknown type of error: " + e.getMessage());
                    }
                }
            });

            }
        });
    }


    public void siteVerify(String response) {

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance("https://www.google.com/recaptcha/api/").create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<ReCapchat> call = apiService.verifyCaptcha(SecretKey, response);
        call.enqueue(new Callback<ReCapchat>() {

            @Override
            public void onResponse(Call<ReCapchat> call, Response<ReCapchat> response) {
                Log.i(LOG_TAG, "SI DIO TODO:" + response.body().isSuccess());
                Log.i(LOG_TAG, "SI DIO TODO con llamada:" + call.request().body());


            }

            @Override
            public void onFailure(Call<ReCapchat> call, Throwable t) {
                Log.e(LOG_TAG, "fallo con " + t.getMessage());
                call.cancel();
            }
        });

    }


}

