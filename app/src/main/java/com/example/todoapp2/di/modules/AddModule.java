package com.example.todoapp2.di.modules;

import com.example.todoapp2.data.realm.RealmService;
import com.example.todoapp2.ui.add.AddContract;
import com.example.todoapp2.ui.add.AddPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AddModule {

    private AddContract.View view;

    public AddModule(AddContract.View view) {
        this.view = view;
    }

    @Provides
    public AddContract.View provideView() {
        return view;
    }

    @Provides
    public AddContract.Presenter providePresenter(AddContract.View view, final RealmService realmService) {
        return new AddPresenter(view, realmService);
    }

}
