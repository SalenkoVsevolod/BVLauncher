package com.example.portable.phonelauncher.network;

import android.net.Uri;

import java.util.HashMap;

/**
 * Created by Salenko Vsevolod on 17.05.2017.
 */

public class FileDownloadedNotifier {
    private static FileDownloadedNotifier instance;
    private HashMap<String, SavingResponseRunnable.OnFileSaved> listeners;

    private FileDownloadedNotifier() {
        listeners = new HashMap<>();
    }

    public static FileDownloadedNotifier getInstance() {
        if (instance == null) {
            instance = new FileDownloadedNotifier();
        }
        return instance;
    }

    public void subscribe(String path, SavingResponseRunnable.OnFileSaved onFileSaved) {
        listeners.put(path, onFileSaved);
    }

    public void notify(String path, Uri uri) {
        listeners.get(path).onSaved(uri);
    }
}
