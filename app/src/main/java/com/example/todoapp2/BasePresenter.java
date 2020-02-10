package com.example.todoapp2;

public interface BasePresenter<T> {

    void onDestroy();

    void closeRealm();
}
