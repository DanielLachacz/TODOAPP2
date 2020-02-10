package com.example.todoapp2.ui.add;

import com.example.todoapp2.data.realm.RealmService;


public class AddPresenter implements AddContract.Presenter, RealmService.OnTransactionCallback {

    private AddContract.View contractView;
    private final RealmService realmService;

    public AddPresenter(AddContract.View view, final RealmService realmService) {
        this.contractView = view;
        this.realmService = realmService;
    }

    @Override
    public void addTaskClick(String name, String date, String category) {

            realmService.addTask(name, date, category, this);
    }

    @Override
    public void onDestroy() {
        contractView = null;
    }

    @Override
    public void closeRealm() {
        realmService.closeRealm();
    }

    @Override
    public void onRealmSuccess() {
        contractView.addTaskSuccess();
    }

    @Override
    public void onRealmError(Throwable error) {
        contractView.addTaskError();
        error.printStackTrace();
    }
}
