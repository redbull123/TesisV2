package com.example.android.tesis.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rjsan on 7/19/2018.
 */

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(String baseUrl) {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
