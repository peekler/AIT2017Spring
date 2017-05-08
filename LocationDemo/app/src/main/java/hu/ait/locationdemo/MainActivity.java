package hu.ait.locationdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements AITLocationManager.OnNewLocationAvailable {

    private TextView tvData;
    private AITLocationManager aitLocationManager = new AITLocationManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = (TextView) findViewById(R.id.tvData);

        requestNeededPermission();
    }

    private void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Toast...
            }

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        } else {
            // start our job
            aitLocationManager.startLocationMonitoring(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permission granted, jupeee!", Toast.LENGTH_SHORT).show();

                // start our job
                aitLocationManager.startLocationMonitoring(this);
            } else {
                Toast.makeText(this, "Permission not granted :(", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onNewLocation(Location location) {



        tvData.setText("Lat: "+location.getLatitude()+"\n"+
            "Lng: "+location.getLongitude()+"\n"+
            "Acc: "+location.getAccuracy()+"\n"+
            "Provider: "+location.getProvider());

        // speed, accuracy, altitude...
    }

    @Override
    protected void onDestroy() {
        if (aitLocationManager != null) {
            aitLocationManager.stopLocationMonitorinig();
        }
        super.onDestroy();
    }
}
