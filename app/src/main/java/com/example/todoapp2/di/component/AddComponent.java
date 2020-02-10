package com.example.todoapp2.di.component;

import com.example.todoapp2.di.modules.AddModule;
import com.example.todoapp2.di.modules.AppModule;
import com.example.todoapp2.di.scope.ActivityScope;
import com.example.todoapp2.ui.add.AddActivity;
import com.example.todoapp2.ui.add.AddContract;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = AddModule.class)
public interface AddComponent {
    void inject(AddActivity addActivity);

    AddContract.Presenter getAddPresenter();
}
