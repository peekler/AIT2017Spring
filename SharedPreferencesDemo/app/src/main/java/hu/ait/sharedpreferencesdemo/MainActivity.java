package hu.ait.sharedpreferencesdemo;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_START_DATE = "KEY_START_DATE";
    private TextView tvStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStartDate = (TextView) findViewById(R.id.tvStartDate);

        showLastUseTime();
    }

    @Override
    protected void onResume() {
        super.onResume();

        saveUseTime();
    }

    private void saveUseTime() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_START_DATE,
                new Date(System.currentTimeMillis()).toString());

        editor.commit();
    }

    private void showLastUseTime() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String lastStartDate = sp.getString(KEY_START_DATE, "This is the first usage");

        tvStartDate.setText(lastStartDate);
    }

}
