package hu.ait.recylerviewdemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import hu.ait.recylerviewdemo.adapter.TodoRecyclerAdapter;
import hu.ait.recylerviewdemo.data.Todo;
import hu.ait.recylerviewdemo.touch.TodoItemTouchHelperCallback;

public class MainActivity extends AppCompatActivity {

    private TodoRecyclerAdapter todoRecyclerAdapter;
    private RecyclerView recyclerTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupUI();
    }

    private void setupUI() {
        setUpToolBar();
        setUpAddTodoUI();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerTodo = (RecyclerView) findViewById(R.id.recyclerTodo);
        recyclerTodo.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerTodo.setLayoutManager(layoutManager);

        todoRecyclerAdapter = new TodoRecyclerAdapter(this);
        recyclerTodo.setAdapter(todoRecyclerAdapter);

        // adding touch support
        ItemTouchHelper.Callback callback = new TodoItemTouchHelperCallback(todoRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerTodo);
    }

    private void setUpAddTodoUI() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                showAddTodoDialog();
            }
        });
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void showAddTodoDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Todo");

        final EditText etTodoText = new EditText(this);
        builder.setView(etTodoText);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoRecyclerAdapter.addTodo(new Todo(etTodoText.getText().toString(), false));

                recyclerTodo.scrollToPosition(0);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
