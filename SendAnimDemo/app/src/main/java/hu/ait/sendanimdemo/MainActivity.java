package hu.ait.sendanimdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation sendAnim = AnimationUtils.loadAnimation(this,
                R.anim.send_anim);

        final LinearLayout rootLayout = (LinearLayout) findViewById(R.id.activity_main);

        final TextView tvText = (TextView) findViewById(R.id.tvText);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootLayout.startAnimation(sendAnim);
                tvText.startAnimation(sendAnim);
            }
        });
    }
}
