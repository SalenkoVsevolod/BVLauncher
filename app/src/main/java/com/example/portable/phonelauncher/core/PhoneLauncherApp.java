package com.example.portable.phonelauncher.core;

import android.app.Application;

/**
 * Created by Salenko Vsevolod on 16.05.2017.
 */

public class PhoneLauncherApp extends Application {
    private static PhoneLauncherApp instance;

    public static PhoneLauncherApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
