package hu.ait.datetimepickerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hu.ait.datetimepickerdemo.fragment.DatePickerDialogFragment;
import hu.ait.datetimepickerdemo.fragment.OnDateTimeSelected;
import hu.ait.datetimepickerdemo.fragment.TimePickerDialogFragment;

public class MainActivity extends AppCompatActivity implements OnDateTimeSelected {

    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.tvStatus);

        Button btnPickTime = (Button) findViewById(R.id.btnPickTime);
        btnPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialogFragment().show(getSupportFragmentManager(),"TIMETAG");
            }
        });
        Button btnPickDate = (Button) findViewById(R.id.btnPickDate);
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialogFragment().show(getSupportFragmentManager(), "DATETAG");
            }
        });
    }

    @Override
    public void onTimeSelected(int hour, int min) {
        tvStatus.setText("hour: "+hour+", min: "+min);
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        tvStatus.setText("year: "+year+", month: "+(month+1)+", day: "+day);
    }
}
