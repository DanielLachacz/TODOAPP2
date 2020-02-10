package com.example.todoapp2.di.modules;

import android.app.Application;

import com.example.todoapp2.BaseApplication;
import com.example.todoapp2.data.realm.RealmService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class AppModule {

    private BaseApplication baseApplication;

    public AppModule(BaseApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return baseApplication;
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    public RealmService provideRealmService(final Realm realm) {
        return new RealmService(realm);
    }
}
