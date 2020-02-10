package com.example.todoapp2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp2.R;
import com.example.todoapp2.data.model.Task;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ListViewHolder>
implements RealmChangeListener<RealmResults<Task>> {

    private RealmResults<Task> tasks;

    @Override
    public void onChange(RealmResults<Task> tasks) {
        notifyDataSetChanged();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, dateTextView, categoryTextView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_item_text_view);
            dateTextView = itemView.findViewById(R.id.date_item_text_view);
            categoryTextView = itemView.findViewById(R.id.category_item_text_view);
        }
    }

    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ListViewHolder holder, int position) {
        final Task task = tasks.get(position);

        holder.nameTextView.setText(task.getName());
        holder.dateTextView.setText(task.getDate().toString());
        holder.categoryTextView.setText(task.getCategory());
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0: tasks.size();
    }

    public void setTasks(RealmResults<Task> tasks) {
        this.tasks = tasks;
        tasks.addChangeListener(this);
        notifyDataSetChanged();
    }
}
