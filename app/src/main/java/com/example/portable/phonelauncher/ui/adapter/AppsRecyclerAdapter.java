package com.example.portable.phonelauncher.ui.adapter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.portable.phonelauncher.R;
import com.example.portable.phonelauncher.core.PhoneLauncherApp;

import java.util.List;

/**
 * Created by Salenko Vsevolod on 19.05.2017.
 */

public class AppsRecyclerAdapter extends RecyclerView.Adapter<AppsRecyclerAdapter.AppsVH> {
    private List<ApplicationInfo> applications;
    private PackageManager packageManager;
    private OnApplicationInteraction onApplicationInteraction;

    public AppsRecyclerAdapter(List<ApplicationInfo> applications, OnApplicationInteraction onApplicationInteraction) {
        this.applications = applications;
        packageManager = PhoneLauncherApp.getInstance().getPackageManager();
        this.onApplicationInteraction = onApplicationInteraction;
    }

    @Override
    public AppsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppsVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_application, parent, false));
    }

    @Override
    public void onBindViewHolder(AppsVH holder, int position) {
        final ApplicationInfo app = applications.get(holder.getAdapterPosition());
        holder.appName.setText(app.loadLabel(packageManager));
        holder.appIcon.setImageDrawable(app.loadIcon(packageManager));
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    class AppsVH extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;

        public AppsVH(View itemView) {
            super(itemView);
            appIcon = (ImageView) itemView.findViewById(R.id.appImage);
            appName = (TextView) itemView.findViewById(R.id.appName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onApplicationInteraction.onClick(applications.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onApplicationInteraction.onLongClick(applications.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }

    public interface OnApplicationInteraction {
        void onClick(ApplicationInfo applicationInfo);

        void onLongClick(ApplicationInfo applicationInfo);
    }
}
