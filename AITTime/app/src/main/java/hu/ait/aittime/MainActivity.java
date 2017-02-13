package hu.ait.aittime;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final LinearLayout layoutRoot = (LinearLayout) findViewById(R.id.activity_main);

        Button btnTime = (Button) findViewById(R.id.btnTime);
        final TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        final EditText etName = (EditText) findViewById(R.id.etName);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etName.getText().toString();

                //String time = getString(R.string.txt_date_message,
                //        user, new Date(System.currentTimeMillis()).toString());

                String time = "Hi, "+user+", the time is: "+getString(R.string.txt_date)+
                        new Date(System.currentTimeMillis()).toString();

                Toast.makeText(
                        MainActivity.this,
                        time,
                        Toast.LENGTH_LONG
                        ).show();

                tvStatus.setText(time);

                Snackbar.make(layoutRoot,time,Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
