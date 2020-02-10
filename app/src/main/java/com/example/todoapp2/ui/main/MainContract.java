package com.example.todoapp2.ui.main;

import com.example.todoapp2.BasePresenter;
import com.example.todoapp2.data.model.Task;

import io.realm.RealmResults;

public interface MainContract {

    interface View {
        void showTasks(RealmResults<Task> tasks);
    }

    interface Presenter extends BasePresenter {
        void setTasks();
    }
}
