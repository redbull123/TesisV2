package com.example.android.tesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.tesis.R;

/**
 * Created by rjsan on 7/24/2018.
 */

public class HomeUser extends AppCompatActivity {

    static int var = 0;
    static int idUser = 0;

    public static void onLoggeado(int flag) {

        var = flag;
    }

    public static void offloggeado() {

        var = 0;
    }

    public static void whoLogin(int id){
        idUser=id;
    }

    public static int ifLoggeado() {
        return var;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
    }
}
