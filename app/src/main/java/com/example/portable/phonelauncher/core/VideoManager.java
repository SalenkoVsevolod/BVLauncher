package com.example.portable.phonelauncher.core;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.VideoView;

/**
 * Created by Salenko Vsevolod on 18.05.2017.
 */

public class VideoManager {
    private static VideoManager instance;
    private VideoView videoView;

    private VideoManager() {
    }

    public static VideoManager getInstance() {
        if (instance == null) {
            instance = new VideoManager();
        }
        return instance;
    }

    public void setVideoView(VideoView videoView) {
        this.videoView = videoView;
    }

    public void playLast() {
        String video = SharedHelper.getInstance().getLastVideo();
        int lastMillis = SharedHelper.getInstance().getLastMillis();
        if (video != null) {
            Log.i("myTag", "play last: " + video.substring(video.lastIndexOf('/') + 1) + " from: " + lastMillis);
            play(video, lastMillis);
        } else {
            Log.e("myTag", "no last video");
        }
    }

    public void play(String path, final int startMillis) {
        videoView.setVideoPath(path);
        if (startMillis != 0) {
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    mp.setVolume(0f, 0f);
                    mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                        @Override
                        public void onSeekComplete(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                }
            });
            videoView.seekTo(startMillis);
        } else {
            videoView.start();
        }
        SharedHelper.getInstance().writeLastVideo(path);
    }

    public void stop() {
        int currentPosition = videoView.getCurrentPosition();
        SharedHelper.getInstance().writeLastMillis(currentPosition);
    }
}
