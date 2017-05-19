package com.example.portable.phonelauncher.utils;

import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.portable.phonelauncher.R;
import com.example.portable.phonelauncher.core.PhoneLauncherApp;

/**
 * Created by Salenko Vsevolod on 17.05.2017.
 */

public class ToastUtils {
    public static void showErrorToast(String error) {
        Toast toast = Toast.makeText(PhoneLauncherApp.getInstance(), error, Toast.LENGTH_LONG);
        toast.getView().setBackground(ContextCompat.getDrawable(PhoneLauncherApp.getInstance(), R.drawable.toast_error_background));
        toast.show();
    }
}


