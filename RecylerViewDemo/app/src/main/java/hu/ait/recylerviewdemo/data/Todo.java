package hu.ait.recylerviewdemo.data;

/**
 * Created by Peter on 2017. 03. 20..
 */

public class Todo {

    private String todoText;
    private boolean done;

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
}
