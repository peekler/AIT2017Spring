package hu.ait.recylerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import hu.ait.recylerviewdemo.MainActivity;
import hu.ait.recylerviewdemo.R;
import hu.ait.recylerviewdemo.data.Todo;
import hu.ait.recylerviewdemo.touch.TodoTouchHelperAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class TodoRecyclerAdapter
        extends RecyclerView.Adapter<TodoRecyclerAdapter.ViewHolder>
        implements TodoTouchHelperAdapter {

    private List<Todo> todoList;
    private Context context;

    private Realm realmTodo;


    public TodoRecyclerAdapter(Context context, Realm realmTodo) {
        this.context = context;

        this.realmTodo = realmTodo;

        RealmResults<Todo> todoResult =
                realmTodo.where(Todo.class).findAll().sort("todoText",
                        Sort.ASCENDING);

        todoList = new ArrayList<Todo>();

        for (int i = 0; i < todoResult.size(); i++) {
            todoList.add(todoResult.get(i));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.todo_row, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvTodo.setText(todoList.get(position).getTodoText());
        holder.cbDone.setChecked(todoList.get(position).isDone());

        holder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmTodo.beginTransaction();
                todoList.get(holder.getAdapterPosition()).setDone(holder.cbDone.isChecked());
                realmTodo.commitTransaction();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).openEditActivity(holder.getAdapterPosition(),
                        todoList.get(holder.getAdapterPosition()).getTodoID()
                        );
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        realmTodo.beginTransaction();
        todoList.get(position).deleteFromRealm();
        realmTodo.commitTransaction();


        todoList.remove(position);

        // refreshes the whole list
        //notifyDataSetChanged();
        // refreshes just the relevant part that has been deleted
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        /*todoList.add(toPosition, todoList.get(fromPosition));
        todoList.remove(fromPosition);*/

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(todoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(todoList, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
    }

    public void addTodo(String todoTitle) {
        realmTodo.beginTransaction();
        Todo newTodo = realmTodo.createObject(Todo.class, UUID.randomUUID().toString());
        newTodo.setTodoText(todoTitle);
        newTodo.setDone(false);

        realmTodo.commitTransaction();

        todoList.add(0, newTodo);
        notifyItemInserted(0);
    }

    public void updateTodo(String todoID, int positionToEdit) {
        Todo todo = realmTodo.where(Todo.class)
                .equalTo("todoID", todoID)
                .findFirst();

        todoList.set(positionToEdit, todo);

        notifyItemChanged(positionToEdit);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbDone;
        private TextView tvTodo;

        public ViewHolder(View itemView) {
            super(itemView);

            cbDone = (CheckBox) itemView.findViewById(R.id.cbDone);
            tvTodo = (TextView) itemView.findViewById(R.id.tvTodo);
        }
    }



}
