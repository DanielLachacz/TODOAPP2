package com.example.todoapp2.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.todoapp2.BaseApplication;
import com.example.todoapp2.R;
import com.example.todoapp2.adapter.MainAdapter;
import com.example.todoapp2.data.model.Task;
import com.example.todoapp2.di.component.DaggerMainComponent;
import com.example.todoapp2.di.modules.MainModule;
import com.example.todoapp2.ui.add.AddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject
    MainContract.Presenter mainPresenter;
    @Inject
    Context context;

    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private TextView statementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        floatingActionButton = findViewById(R.id.add_fab);
        statementTextView = findViewById(R.id.statement_text_view);

        DaggerMainComponent.builder()
                .appComponent(BaseApplication.get(this).component())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        initList();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddActivity();
            }
        });
    }

    private void initList() {
        mainAdapter = new MainAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void onAddActivity() {
        startActivity(new Intent(MainActivity.this, AddActivity.class));
    }

    @Override
    public void showTasks(RealmResults<Task> tasks) {
        if (tasks == null || tasks.size() == 0) {
            statementTextView.setVisibility(View.VISIBLE);
        }
        else {
            mainAdapter.setTasks(tasks);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.setTasks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.closeRealm();
    }


}
