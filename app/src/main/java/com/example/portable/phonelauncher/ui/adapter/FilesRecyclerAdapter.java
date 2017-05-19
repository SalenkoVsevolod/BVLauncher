package com.example.portable.phonelauncher.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.portable.phonelauncher.R;

import java.io.File;
import java.util.List;

/**
 * Created by Salenko Vsevolod on 18.05.2017.
 */

public class FilesRecyclerAdapter extends RecyclerView.Adapter<FilesRecyclerAdapter.VideoVH> {
    private List<File> files;
    private OnFileClicked onFileClicked;

    public FilesRecyclerAdapter(List<File> files, OnFileClicked onFileClicked) {
        this.files = files;
        this.onFileClicked = onFileClicked;
    }

    @Override
    public VideoVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoVH holder, int position) {
        String filePath = files.get(holder.getAdapterPosition()).getAbsolutePath();
        holder.fileName.setText(filePath.substring(filePath.lastIndexOf('/') + 1));
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class VideoVH extends RecyclerView.ViewHolder {
        TextView fileName;

        public VideoVH(View itemView) {
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.fileName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onFileClicked.fileClicked(files.get(getAdapterPosition()).getAbsolutePath());
                }
            });
        }
    }

    public interface OnFileClicked {
        void fileClicked(String file);
    }
}
