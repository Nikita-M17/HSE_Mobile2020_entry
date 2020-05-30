package com.talyounti.HSE_mobile.interfaces;

import com.talyounti.HSE_mobile.models.Currency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndpoints {
    @GET("/latest")
    public Call<Currency> getLatest(@Query("base") String currency);
}
