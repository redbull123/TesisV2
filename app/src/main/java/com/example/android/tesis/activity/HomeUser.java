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

    public static void onLoggeado(int flag) {

        var = flag;
    }

    public static void offloggeado() {

        var = 0;
    }

    public static int ifLoggeado() {
        return var;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text1 = (TextView) findViewById(R.id.perfil);
        text1.setText("PERFIL");
        ImageView ima2 = (ImageView) findViewById(R.id.outside_image);
        ima2.setImageResource(R.drawable.ic_clear_black_48dp);
        TextView text2 = (TextView) findViewById(R.id.outside);
        text2.setText("FINALIZAR SESION");

        View perfil = findViewById(R.id.log);
        perfil.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent iti = new Intent(HomeUser.this, PerfilPrueba.class);
                startActivity(iti);
            }
        });

        View out = findViewById(R.id.regis);
        out.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                offloggeado();
                Intent iti = new Intent(HomeUser.this, MainActivity.class);
                startActivity(iti);
            }
        });

        View schedule = findViewById(R.id.itine);
        schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent iti = new Intent(HomeUser.this, Schedule.class);
                startActivity(iti);
            }
        });

    }
}
