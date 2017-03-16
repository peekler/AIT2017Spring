package hu.ait.threadtimerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvData;

    private boolean enabled = false;

    private class MyTimeThread extends Thread {
        public void run() {
            while (enabled) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvData.append("#");
                    }
                });


                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Timer mainTimer = null;

    private class MyShowTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvData.append("#");
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);

        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*enabled = true;
                new MyTimeThread().start();

                tvData.setText("DEMO");*/

                if (mainTimer == null) {
                    mainTimer = new Timer();

                    mainTimer.schedule(new MyShowTimerTask(),
                            0, 1000);
                }

            }
        });

        Button btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*enabled = false;
                tvData.setText("");*/

                if (mainTimer != null) {
                    mainTimer.cancel();
                    mainTimer = null;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        enabled = false;

        if (mainTimer != null) {
            mainTimer.cancel();
            mainTimer = null;
        }
    }
}
