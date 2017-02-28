package hu.ait.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show 2nd Activity

                Intent intenStartSecond = new Intent();
                intenStartSecond.setClass(MainActivity.this, SecondActivity.class);

                startActivity(intenStartSecond);

                finish();
            }
        });
    }
}
