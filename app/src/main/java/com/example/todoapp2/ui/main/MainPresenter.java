package com.example.todoapp2.ui.main;

import com.example.todoapp2.data.realm.RealmService;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View contractView;
    private final RealmService realmService;

    public MainPresenter(MainContract.View view, final RealmService realmService) {
        this.contractView = view;
        this.realmService = realmService;
    }

    @Override
    public void setTasks() {
        getTasks();
    }

    @Override
    public void onDestroy() {
        contractView = null;
    }

    @Override
    public void closeRealm() {
        realmService.closeRealm();
    }

    private void getTasks() {
        contractView.showTasks(realmService.getAllTasks());
    }
}
