package com.example.todoapp2.ui.add;

import com.example.todoapp2.BasePresenter;

public interface AddContract {

    interface View {
        void addTaskSuccess();
        void addTaskError();
    }

    interface Presenter extends BasePresenter {
        void addTaskClick(final String text, final String date, final String category);
    }
}
