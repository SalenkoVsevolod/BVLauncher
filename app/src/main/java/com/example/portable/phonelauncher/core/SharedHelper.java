package com.example.portable.phonelauncher.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Salenko Vsevolod on 18.05.2017.
 */

public class SharedHelper {
    private static final String PREFS = "prefs", FILE_PATH = "filePath", LAST_TIME = "lastTime";
    private static SharedHelper instance;
    private SharedPreferences preferences;

    private SharedHelper() {
        preferences = PhoneLauncherApp.getInstance().getSharedPreferences(PREFS, Context.MODE_PRIVATE);

    }

    public static SharedHelper getInstance() {
        if (instance == null) {
            instance = new SharedHelper();
        }
        return instance;
    }

    public void writeLastVideo(@NonNull String filePath) {
        preferences.edit().putString(FILE_PATH, filePath).apply();
    }

    public void writeLastMillis(int millis) {
        preferences.edit().putInt(LAST_TIME, millis).apply();
    }

    public int getLastMillis() {
        return preferences.getInt(LAST_TIME, 0);
    }

    @Nullable
    public String getLastVideo() {
        return preferences.getString(FILE_PATH, null);
    }
}
