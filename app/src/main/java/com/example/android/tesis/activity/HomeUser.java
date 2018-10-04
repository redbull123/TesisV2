package com.example.android.tesis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.tesis.R;

/**
 * Created by rjsan on 7/24/2018.
 */

public class HomeUser extends AppCompatActivity {

    static int var = 0;
    static int idUser = 0;

    public static void onLogin(int flag) {

        var = flag;
    }

    public static void whoLogin(int id) {
        idUser = id;
    }

    public static int ifLogin() {
        return var;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);
    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    public void continueSchedule(View view){
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void continueBuy(View view){
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    public void logout(View view){
        var=0;
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
