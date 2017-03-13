package hu.ait.todoinflaterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;

    @BindView(R.id.etTodo)
    EditText etTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSave)
    public void savePressed(Button btn) {
        View viewTodo =
                getLayoutInflater().inflate(R.layout.layout_todo, null);

        TextView tvTodo = (TextView) viewTodo.findViewById(R.id.tvTodo);
        tvTodo.setText(etTodo.getText().toString());

        layoutContent.addView(viewTodo);
    }
}
