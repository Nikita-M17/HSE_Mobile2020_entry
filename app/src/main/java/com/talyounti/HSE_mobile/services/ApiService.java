package com.talyounti.HSE_mobile.services;

import com.talyounti.HSE_mobile.interfaces.APIEndpoints;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    private static ApiService mInstance;
    private static final String BASE_URL = "https://api.exchangeratesapi.io";
    private Retrofit mRetrofit;


    private ApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static ApiService getInstance() {
        if(mInstance == null) {
            mInstance = new ApiService();
        }
        return mInstance;
    }
    public APIEndpoints getApi() {
        return mRetrofit.create(APIEndpoints.class);
    }
}
