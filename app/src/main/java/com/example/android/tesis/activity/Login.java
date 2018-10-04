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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.model.Usuario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;
import com.example.android.tesis.utils.SecurePassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rjsan on 7/5/2018.
 */

public class Login extends AppCompatActivity {
    private static final String LOG_TAG = Login.class.getSimpleName();
    String user;
    String password;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        final EditText userEditText = (EditText) findViewById(R.id.user);
        final EditText passEditText = (EditText) findViewById(R.id.pass);

        Button buttonContinue = (Button) findViewById(R.id.login);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = userEditText.getText().toString();
                password = passEditText.getText().toString();
                new Login.AsyncCaller().execute();
            }
        });
    }

    @Override
    public void onBackPressed(){

        Login.super.onBackPressed();
    }

    public void login() {
        SecurePassword sp = new SecurePassword();
        final String encrytPassword = sp.getPasswordHash(password);

        if (conteoPalabrasEspacio(user) >= 2) {
            PopUp("No se puede ingresar un usuario con espacios");
        }
        if (password.length() == 0) {
            PopUp("Campo del password vacio");
        }

        if (apiService == null) {
            apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
        } else {
            Log.d(LOG_TAG, "el apiService está inicializado");
        }

        Call<Usuario> call = apiService.doGetUsuarios(user);

        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    if (user.equals(response.body().getUsuario())) {
                        if (encrytPassword.equals(response.body().getPassword())) {
                            HomeUser.onLogin(1);
                            HomeUser.whoLogin(response.body().getId());
                            if(Schedule.callSchedule==0){
                            Intent iti = new Intent(Login.this, HomeUser.class);
                            startActivity(iti);}
                            else if(Schedule.callSchedule==1){
                                Schedule.callSchedule=0;
                                onBackPressed();}
                        } else {
                            PopUp("Password Incorrecto");
                        }
                    } else {
                        PopUp("Usuario Incorrecto");
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(Login.this, "Problemas de Conexión",
                        Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }


    public int conteoPalabrasEspacio(String user) {

        int conteoPalabras = 0;
        boolean palabra = false;
        int finDeLinea = user.length() - 1;

        for (int i = 0; i < user.length(); i++) {
            // Si el char is una letra, word = true.
            if (Character.isLetter(user.charAt(i)) && i != finDeLinea) {
                palabra = true;
                // Si el char no es una letra y aún hay más letras,
                // el contador continua.
            } else if (!Character.isLetter(user.charAt(i)) && palabra) {
                conteoPalabras++;
                palabra = false;
                // última palabra de la cadena; si no termina con una no letra ,
            } else if (Character.isLetter(user.charAt(i)) && i == finDeLinea) {
                conteoPalabras++;
            }
        }
        return conteoPalabras;
    }

    public void PopUp(String issue) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(issue)
                .setTitle("NOTIFICACION")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(Login.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            login();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }
    }

    public void continueRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

}
