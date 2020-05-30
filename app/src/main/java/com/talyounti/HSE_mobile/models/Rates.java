package com.talyounti.HSE_mobile.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {
    @SerializedName("RUB")
    @Expose
    private Float rub;
    @SerializedName("USD")
    @Expose
    private Float usd;
    @SerializedName("EUR")
    @Expose
    private Float eur;

    public Float getRub() {
        return rub;
    }

    public void setRub(Float rub) {
        this.rub = rub;
    }

    public Float getUsd() {
        return usd;
    }

    public void setUsd(Float usd) {
        this.usd = usd;
    }

    public Float getEur() {
        return eur;
    }

    public void setEur(Float eur) {
        this.eur = eur;
    }
}
