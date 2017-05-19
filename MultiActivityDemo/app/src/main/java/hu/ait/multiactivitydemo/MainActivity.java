package hu.ait.multiactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_LIFE = "TAG_A_LIFE";

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

            }
        });
        Log.d(TAG_LIFE, "onCreate() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG_LIFE, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG_LIFE, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG_LIFE, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG_LIFE, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG_LIFE, "onDestroy() called");
    }
}
