package hu.ait.moneyexchangerates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import hu.ait.moneyexchangerates.data.MoneyResult;
import hu.ait.moneyexchangerates.network.AsyncTaskHttpGet;
import hu.ait.moneyexchangerates.network.MoneyResultListener;

public class MainActivity extends AppCompatActivity implements MoneyResultListener {

    private TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);
        Button btnGet = (Button) findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskHttpGet(MainActivity.this).execute("http://api.fixer.io/latest?base=USD");
            }
        });
    }

    @Override
    public void moneyResultArrived(String rawJson) {

        try {
            /*JSONObject rootJson = new JSONObject(rawJson);
            JSONObject rates = rootJson.getJSONObject("rates");
            String huf = rates.getString("HUF");
            String eur = rates.getString("EUR");
            tvData.setText(huf+"\n"+eur);*/

            Gson gson = new Gson();
            MoneyResult moneyResult = gson.fromJson(rawJson, MoneyResult.class);
            tvData.setText(moneyResult.getRates().getHUF()+"\n"+
                moneyResult.getRates().getEUR());



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
