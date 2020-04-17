package com.cleanup.todoc;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TodocApp extends Application {

    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
