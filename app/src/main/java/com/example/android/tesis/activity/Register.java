package com.example.android.tesis.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.tesis.R;
import com.example.android.tesis.model.Usuario;
import com.example.android.tesis.my_interface.APIService;
import com.example.android.tesis.network.ApiUtils;
import com.example.android.tesis.network.RetrofitInstance;
import com.example.android.tesis.utils.DatePickerFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rjsan on 7/5/2018.
 */

public class Register extends AppCompatActivity {
    private static final String LOG_TAG = Register.class.getSimpleName();
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        Button buttonReg = (Button) findViewById(R.id.reg);
        buttonReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Register.AsyncCaller().execute();
            }
        });
    }


    private class AsyncCaller extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(Register.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            registerUser();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pdLoading.dismiss();
        }
    }

    public void registerUser() {

        String name;
        String lastName;
        int ci;
        String address;
        String email;
        String usuario;
        String password;

        EditText nameText = (EditText) findViewById(R.id.ingName);
        name = nameText.getText().toString();

        EditText lastNameText = (EditText) findViewById(R.id.ingLast);
        lastName = lastNameText.getText().toString();

        EditText ciText = (EditText) findViewById(R.id.ingCi);
        ci = Integer.parseInt(ciText.getText().toString());

        EditText addressText = (EditText) findViewById(R.id.ingAddress);
        address = addressText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.ingEmail);
        email = emailText.getText().toString();

        EditText userNameText = (EditText) findViewById(R.id.ingUser);
        usuario = userNameText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.ingPass);
        password = passwordText.getText().toString();


        if (!name.equals(null) && !lastName.equals(null) && ci != 0 && !address.equals(null) &&
                !email.equals("") && !usuario.equals("") && !password.equals(null)) {

            Usuario user = new Usuario(lastName, ci, name, password, "us", 1, usuario);

            if (apiService == null) {
                apiService = RetrofitInstance.getRetrofitInstance(ApiUtils.BASE_URL).create(APIService.class);
            } else {
                Log.d(LOG_TAG, "el apiService está inicializado");
            }

            Log.i(LOG_TAG,"Va a usar el ApiService: " + apiService.toString());

            Call<Usuario> call = apiService.doCreateUser(user);
            call.enqueue(new Callback<Usuario>() {

                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    popUp("Usuario creado con 'exito");
                    Button continueLogin = (Button) findViewById(R.id.reg);

                    continueLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    call.cancel();
                    popUp("Falla al crear el usuario");
                }
            });
        }

        else {
            Toast.makeText(Register.this, "Campo Vacio",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void popUp(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
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

    public void cancelar(View view) {

        Intent route = new Intent(this, MainActivity.class);
        startActivity(route);
    }


}
