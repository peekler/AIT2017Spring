package hu.ait.dialogfragmentdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hu.ait.dialogfragmentdemo.fragment.MessageFragment;
import hu.ait.dialogfragmentdemo.fragment.OnMessageFragmentAnswer;
import hu.ait.dialogfragmentdemo.fragment.SelectFruitFragment;

public class MainActivity extends AppCompatActivity
        implements OnMessageFragmentAnswer, SelectFruitFragment.OptionsFragmentInterface {

    public static final String KEY_MSG = "KEY_MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDialog1 = (Button) findViewById(R.id.btnDialog1);
        btnDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageFragment messageFragment = new MessageFragment();
                messageFragment.setCancelable(false);

                Bundle bundle = new Bundle();
                bundle.putString(KEY_MSG,"You have press button 1, ok?");
                messageFragment.setArguments(bundle);

                messageFragment.show(getSupportFragmentManager(),
                        "MessageFragment");
            }
        });
        Button btnDialog2 = (Button) findViewById(R.id.btnDialog2);
        btnDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectFruitFragment().show(getSupportFragmentManager(),SelectFruitFragment.TAG);
            }
        });
    }

    @Override
    public void onPositiveSelected() {
        Toast.makeText(this, "OK was selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeSelected() {
        Toast.makeText(this, "NOPE was selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionsFragmentResult(String fruit) {
        Toast.makeText(this, "Selected fruit: "+fruit, Toast.LENGTH_LONG).show();
    }
}
