package hu.ait.aitlifecycledemo;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_LIFE = "TAG_LIFE";

    private int score = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            score = savedInstanceState.getInt("KEY_SCORE");
        }

        Log.d(TAG_LIFE, "onCreate called");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("KEY_SCORE", score);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG_LIFE, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG_LIFE, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG_LIFE, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG_LIFE, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG_LIFE, "onDestroy called");
    }
}
