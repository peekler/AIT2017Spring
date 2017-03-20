package hu.ait.recylerviewdemo.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class TodoItemTouchHelperCallback extends
        ItemTouchHelper.Callback {

    private TodoTouchHelperAdapter todoTouchHelperAdapter;

    public TodoItemTouchHelperCallback(TodoTouchHelperAdapter todoTouchHelperAdapter) {
        this.todoTouchHelperAdapter = todoTouchHelperAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        todoTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition()
        );
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        todoTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}

