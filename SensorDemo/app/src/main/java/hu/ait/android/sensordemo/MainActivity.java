package hu.ait.android.sensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvSensors;
    private TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSensors = (TextView) findViewById(R.id.tvSensors);
        tvValue = (TextView) findViewById(R.id.tvValue);

        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Button btnListSensors = (Button) findViewById(R.id.btnListSensors);
        btnListSensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
                tvSensors.setText("");
                for (Sensor sensor : sensorList) {
                    tvSensors.append(sensor.getName()+"\n");
                }
            }
        });

        Button btnStartSensor = (Button) findViewById(R.id.btnStartSensor);
        btnStartSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sensor accelero = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                sensorManager.registerListener(MainActivity.this,
                        accelero,
                        SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
    }

    @Override
    protected void onDestroy() {
        ((SensorManager)getSystemService(SENSOR_SERVICE)).unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tvValue.setText(
                "X: "+event.values[0]+"\n"+
                "Y: "+event.values[1]+"\n"+
                "Z: "+event.values[2]
        );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
