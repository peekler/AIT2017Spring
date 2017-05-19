package hu.ait.multiactivitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG_LIFE = "TAG_B_LIFE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
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
