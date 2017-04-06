package hu.ait.recylerviewdemo.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Peter on 2017. 03. 20..
 */

public class Todo extends RealmObject {

    @PrimaryKey
    private String todoID;

    private String todoText;
    private boolean done;


    public Todo() {}

    public Todo(String todoText, boolean done) {
        this.todoText = todoText;
        this.done = done;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTodoID() {
        return todoID;
    }
}
