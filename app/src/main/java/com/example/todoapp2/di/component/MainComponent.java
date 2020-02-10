package com.example.todoapp2.di.component;

import com.example.todoapp2.di.modules.MainModule;
import com.example.todoapp2.di.scope.ActivityScope;
import com.example.todoapp2.ui.main.MainActivity;
import com.example.todoapp2.ui.main.MainContract;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);

    MainContract.Presenter getMainPresenter();
}
