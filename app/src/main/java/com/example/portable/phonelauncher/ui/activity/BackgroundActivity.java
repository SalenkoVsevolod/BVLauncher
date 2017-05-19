package com.example.portable.phonelauncher.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.example.portable.phonelauncher.R;
import com.example.portable.phonelauncher.core.VideoManager;

public class BackgroundActivity extends AppCompatActivity {
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = (VideoView) findViewById(R.id.mainVideoView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        VideoManager.getInstance().setVideoView(mVideoView);
        VideoManager.getInstance().playLast();
        startActivity(new Intent(this, AppsListActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoManager.getInstance().stop();
    }
}

