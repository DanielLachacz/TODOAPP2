package com.example.todoapp2.di.modules;

import com.example.todoapp2.data.realm.RealmService;
import com.example.todoapp2.ui.main.MainContract;
import com.example.todoapp2.ui.main.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    public MainContract.View provideView() {
        return view;
    }

    @Provides
    public MainContract.Presenter providePresenter(MainContract.View view, final RealmService realmService) {
        return new MainPresenter(view, realmService);
    }
}
