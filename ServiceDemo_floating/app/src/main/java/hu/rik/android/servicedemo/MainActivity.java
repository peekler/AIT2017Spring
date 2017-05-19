package hu.rik.android.servicedemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String KEY_MESSENGER = "KEY_MESSENGER";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == RESULT_OK) {
                String status = (String) msg.obj;
                Toast.makeText(MainActivity.this, status,
                        Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*Intent i = new Intent(MainActivity.this, IntentServiceDemo.class);
                Messenger messenger = new Messenger(handler);
                i.putExtra(KEY_MESSENGER, messenger);
                startService(i);*/

                /*Intent intent = new Intent(
                        MainActivity.this,
                        StartedService.class);
                startService(intent);*/

                Intent intent = new Intent(
                        MainActivity.this,
                        MyTimeServiceWindow.class);
                startService(intent);
            }
        });
        Button btnStop = (Button)findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*Intent intent = new Intent(
                        MainActivity.this,
                        StartedService.class);
                stopService(intent);*/

                Intent intent = new Intent(
                        MainActivity.this,
                        MyTimeServiceWindow.class);
                stopService(intent);
            }
        });

        //requestNeededPermission();
        requestOverlay();
    }

    public void requestOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);
        }
    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                Toast.makeText(MainActivity.this,
                        "I need it for window", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                    101);
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "SYSTEM_ALERT_WINDOW perm granted", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(MainActivity.this,
                            "SYSTEM_ALERT_WINDOW perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
