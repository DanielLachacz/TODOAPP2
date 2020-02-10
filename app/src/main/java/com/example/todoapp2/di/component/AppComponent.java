package com.example.todoapp2.di.component;

import android.app.Application;
import android.content.Context;

import com.example.todoapp2.BaseApplication;
import com.example.todoapp2.data.realm.RealmService;
import com.example.todoapp2.di.modules.AppModule;
import com.example.todoapp2.di.modules.ContextModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton()
@Component(modules = {AppModule.class,
        ContextModule.class})
public interface AppComponent {

    void inject(BaseApplication baseApplication);

    Context context();

    RealmService realmService();

    Application application();
}
