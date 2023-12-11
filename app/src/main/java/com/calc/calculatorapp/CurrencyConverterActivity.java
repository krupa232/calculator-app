package com.calc.calculatorapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CurrencyConverterActivity extends AppCompatActivity {

    private static final String NBP_BASE_URL = "https://api.nbp.pl/api/exchangerates/";
    private List<String> conversionHistory;
    private ArrayAdapter<String> historyAdapter;

    private interface NBPApi {
        @GET("rates/a/{currencyCode}/")
        Call<ExchangeRateResponse> getExchangeRate(@Path("currencyCode") String currencyCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionHistory = new ArrayList<>();
        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, conversionHistory);

        final EditText amountEditText = findViewById(R.id.amountEditText);
        final EditText currencyCodeEditText = findViewById(R.id.currencyCodeEditText);
        final ListView historyListView = findViewById(R.id.historyListView);
        final Button convertButton = findViewById(R.id.convertButton);

        historyListView.setAdapter(historyAdapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = amountEditText.getText().toString();
                String currencyCode = currencyCodeEditText.getText().toString();

                if (!amountStr.isEmpty() && !currencyCode.isEmpty()) {
                    double amount = Double.parseDouble(amountStr);
                    new CurrencyConversionTask().execute(amount, currencyCode);
                }
            }
        });
    }

    private class CurrencyConversionTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            double amount = (double) params[0];
            String currencyCode = (String) params[1];

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NBP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NBPApi nbpApi = retrofit.create(NBPApi.class);

            try {
                Call<ExchangeRateResponse> call = nbpApi.getExchangeRate(currencyCode);
                ExchangeRateResponse exchangeRateResponse = call.execute().body();

                if (exchangeRateResponse != null && exchangeRateResponse.getRates().size() > 0) {
                    ExchangeRate rate = exchangeRateResponse.getRates().get(0);

                    String currency = exchangeRateResponse.getCurrency();
                    String code = exchangeRateResponse.getCode();
                    double exchangeRate = rate.getMid();

                    double result = amount * exchangeRate;

                    conversionHistory.add(amount + " " + currency + " (" + code + ") = " + result + " PLN");
                    return String.valueOf(result);
                } else {
                    return "Error: No exchange rate data available.";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            historyAdapter.notifyDataSetChanged();
        }
    }
}
