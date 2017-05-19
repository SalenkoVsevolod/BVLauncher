package com.example.portable.phonelauncher.ui.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.portable.phonelauncher.R;
import com.example.portable.phonelauncher.ui.adapter.AppsRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AppsListActivity extends BaseTransparentActivity {
    private RecyclerView appsRecycler;
    private FloatingActionButton floatingActionButton;
    private final ArrayList<ApplicationInfo> applications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        appsRecycler = (RecyclerView) findViewById(R.id.applicationsRecycler);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.settingsFAB);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppsListActivity.this, SettingsActivity.class));
            }
        });
        loadUserApps();
        appsRecycler.setAdapter(new AppsRecyclerAdapter(applications, new AppsRecyclerAdapter.OnApplicationInteraction() {
            @Override
            public void onClick(ApplicationInfo applicationInfo) {
                startActivity(getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName));
            }

            @Override
            public void onLongClick(ApplicationInfo applicationInfo) {
                Toast.makeText(AppsListActivity.this, "long click on " + applicationInfo.loadLabel(getPackageManager()), Toast.LENGTH_SHORT).show();
            }
        }));
        appsRecycler.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private void loadUserApps() {
        applications.clear();
        List<ApplicationInfo> allApps = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : allApps) {
            if (!applicationInfo.packageName.equals(getPackageName()) && getPackageManager().getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                applications.add(applicationInfo);
            }
        }
        Collections.sort(applications, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo o1, ApplicationInfo o2) {
                String l1 = o1.loadLabel(getPackageManager()).toString();
                String l2 = o2.loadLabel(getPackageManager()).toString();
                return l1.compareTo(l2);
            }
        });
    }

    @Override
    protected int getRootId() {
        return R.id.video_list_root;
    }
}
