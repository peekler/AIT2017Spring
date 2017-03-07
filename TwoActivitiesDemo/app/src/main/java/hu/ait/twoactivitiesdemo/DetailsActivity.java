package hu.ait.twoactivitiesdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView tvData = (TextView) findViewById(R.id.tvData);

        if (getIntent().getExtras().containsKey(MainActivity.KEY_DATA)) {
            int v = Preferences.getInstance().getValue();
            tvData.setText(getIntent().getStringExtra(MainActivity.KEY_DATA));
        }
    }
}
