package com.example.bloodbank.database;

import com.example.bloodbank.util.Url;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {
    private static Retrofit retrofit;

    private static Retrofit getInstance(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }

    public static WebServices getApis(){
        return getInstance().create(WebServices.class);
    }
}
