package com.example.portable.phonelauncher.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Salenko Vsevolod on 19.05.2017.
 */

public abstract class BaseTransparentActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        findViewById(getRootId()).setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(getRootId()).setVisibility(View.VISIBLE);
    }

    protected abstract int getRootId();
}
