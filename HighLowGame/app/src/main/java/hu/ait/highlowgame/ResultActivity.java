package hu.ait.highlowgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    public void onBackPressed() {
        Intent showMain = new Intent(this, MainActivity.class);
        showMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(showMain);
    }
}
