package com.example.portable.phonelauncher.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.portable.phonelauncher.R;
import com.example.portable.phonelauncher.core.SharedHelper;
import com.example.portable.phonelauncher.core.VideoManager;
import com.example.portable.phonelauncher.ui.adapter.FilesRecyclerAdapter;
import com.example.portable.phonelauncher.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private RecyclerView mFilesRecyclerView;
    private final ArrayList<File> mFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mFilesRecyclerView = (RecyclerView) findViewById(R.id.filesRecycler);
        mFiles.addAll(FileUtils.getFilesFromFolder(getExternalFilesDir(null)));
        mFilesRecyclerView.setAdapter(new FilesRecyclerAdapter(mFiles, new FilesRecyclerAdapter.OnFileClicked() {
            @Override
            public void fileClicked(String filePath) {
                String previousPath = SharedHelper.getInstance().getLastVideo();
                if (previousPath == null || !previousPath.equals(filePath)) {
                    SharedHelper.getInstance().writeLastVideo(filePath);
                    VideoManager.getInstance().play(filePath, 0);
                }
            }
        }));
        mFilesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFilesRecyclerView.getAdapter().notifyDataSetChanged();
    }

}
