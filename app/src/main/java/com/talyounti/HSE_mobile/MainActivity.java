package com.talyounti.HSE_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.talyounti.HSE_mobile.models.Currency;
import com.talyounti.HSE_mobile.services.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner currentCurrency;
    private ImageView currentCurrencyIcon;
    private EditText currentCurrencyValue;
    private Spinner toCurrency;
    private ImageView toCurrencyIcon;
    private Button convertButton;
    private TextView resultText;

    private String cur;
    private String to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentCurrencyValue = (EditText) findViewById(R.id.currentCurrencyValue);
        currentCurrency = (Spinner) findViewById(R.id.currentCurrency);
        currentCurrencyIcon = (ImageView) findViewById(R.id.currentCurrencyIcon);
        toCurrency = (Spinner) findViewById(R.id.toCurrency);
        toCurrencyIcon = (ImageView) findViewById(R.id.toCurrencyIcon);
        convertButton = (Button) findViewById(R.id.convertButton);
        resultText = (TextView) findViewById(R.id.result);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter1);
        currentCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                Log.d("DEBUG", item.toString());
                cur = item.toString();
                switch (item.toString()) {
                    case "EUR":
                        currentCurrencyIcon.setImageResource(R.drawable.eur);
                        break;
                    case "RUB":
                        currentCurrencyIcon.setImageResource(R.drawable.rus);
                        break;
                    case "USD":
                        currentCurrencyIcon.setImageResource(R.drawable.usa);
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        toCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                Log.d("DEBUG", item.toString());
                to = item.toString();
                switch (item.toString()) {
                    case "EUR":
                        toCurrencyIcon.setImageResource(R.drawable.eur);
                        break;
                    case "RUB":
                        toCurrencyIcon.setImageResource(R.drawable.rus);
                        break;
                    case "USD":
                        toCurrencyIcon.setImageResource(R.drawable.usa);
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        convertButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (to.equals(cur)) {
            Toast.makeText(this, R.string.converterror, Toast.LENGTH_LONG).show();
        } else if(currentCurrencyValue.getText().length() == 0) {
            Toast.makeText(this, R.string.inputmoneyerror, Toast.LENGTH_SHORT).show();
        } else {
            resultText.setText(R.string.loading);
            ApiService.getInstance().getApi()
                    .getLatest(cur)
                    .enqueue(new Callback<Currency>() {

                        @Override
                        public void onResponse(Call<Currency> call, Response<Currency> response) {
                            Currency currency = response.body();
                            Float toValue;
                            if (currency != null) {
                                switch (to) {
                                    case "USD":
                                        toValue = currency.getRates().getUsd() * Float.parseFloat(String.valueOf(currentCurrencyValue.getText()));
                                        break;
                                    case "EUR":
                                        toValue = currency.getRates().getEur() * Float.parseFloat(String.valueOf(currentCurrencyValue.getText()));
                                        break;
                                    case "RUB":
                                        toValue = currency.getRates().getRub() * Float.parseFloat(String.valueOf(currentCurrencyValue.getText()));
                                        break;
                                    default:
                                        throw new IllegalStateException("Unexpected value: " + to);
                                }
                                resultText.setText(toValue.toString() + " " + to);
                            } else {
                                resultText.setText("Данные отсутствуют");
                            }
                        }

                        @Override
                        public void onFailure(Call<Currency> call, Throwable t) {
                            resultText.setText("Произошла ошибка...");
                        }
                    });
        }
    }
}
