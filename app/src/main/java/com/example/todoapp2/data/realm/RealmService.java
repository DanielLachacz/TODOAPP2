package com.example.todoapp2.data.realm;

import android.util.Log;

import com.example.todoapp2.data.model.Task;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService {

    private final Realm realm;

    public RealmService(final Realm realm) {
        this.realm = realm;
    }

    public void closeRealm() {
        realm.close();
    }

    public RealmResults<Task> getAllTasks() {
        return realm.where(Task.class).findAll();
    }

    public void addTask(final String name, final String date, final String category, final OnTransactionCallback onTransactionCallback) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Task task = new Task();
                task.setId(realm.where(Task.class).findAll().size());
                task.setName(name);
                task.setDate(date);
                task.setCategory(category);
                realm.copyToRealm(task);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onTransactionCallback != null) {
                    onTransactionCallback.onRealmSuccess();
                    Log.e("Realm Service", "Success");
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (onTransactionCallback == null) {
                    onTransactionCallback.onRealmError(error);
                    Log.e("Realm Service", "Error");
                }
            }
        });

    }

    public interface OnTransactionCallback {
        void onRealmSuccess();
        void onRealmError(final Throwable error);
    }
}
