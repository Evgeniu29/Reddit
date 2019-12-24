package com.paad.reddit.retrofit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    private static final String BASE_URL = "https://www.reddit.com";

    private static OkHttpClient getOKClient() {
        OkHttpClient client;
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.followRedirects(true);

        client = clientBuilder.build();
        return client;
    }

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOKClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }


}