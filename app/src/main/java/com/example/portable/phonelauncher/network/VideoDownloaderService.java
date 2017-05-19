package com.example.portable.phonelauncher.network;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.portable.phonelauncher.R;
import com.example.portable.phonelauncher.utils.ToastUtils;

import java.io.File;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class VideoDownloaderService extends Service {
    private static final int NOTIFICATION_ID = 18;
    private static final String FILE_URL = "fileUrl";
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mNotifyBuilder;
    private String mFileUrl;


    public static void start(Context context, String fileUrl) {
        Intent starter = new Intent(context, VideoDownloaderService.class);
        starter.putExtra(FILE_URL, fileUrl);
        context.startService(starter);
    }

    public VideoDownloaderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFileUrl = intent.getStringExtra(FILE_URL);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyBuilder = new NotificationCompat.Builder(this);
        mNotifyBuilder.setContentTitle("Video downloading");
        mNotifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mNotifyBuilder.setProgress(100, 0, false);
        final File externalStorage = getExternalFilesDir(null);
        FileDownloader downloader = new FileDownloader(mFileUrl);

        if (externalStorage != null) {
            downloader.getVideoFromServer(new FileDownloader.OnServerResponse() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        saveFileToDisk(externalStorage.toString(), response.body());
                    } else {
                            Log.e("myTag", "response"+response.code());
                        ToastUtils.showErrorToast("Wrong url");
                    }
                }
            });
            startForeground(NOTIFICATION_ID, mNotifyBuilder.build());

        } else {
            ToastUtils.showErrorToast("Internal storage error");
        }

        return START_NOT_STICKY;
    }

    private void saveFileToDisk(String externalStorage, ResponseBody body) {
        SavingResponseRunnable savingResponseRunnable = new SavingResponseRunnable(externalStorage + File.separator + mFileUrl.substring(mFileUrl.lastIndexOf('/') + 1, mFileUrl.length()), body);
        savingResponseRunnable.setOnProgressChanged(new SavingResponseRunnable.OnProgressChanged() {
            @Override
            public void onChanged(int progress) {
                mNotifyBuilder.setProgress(100, progress, false);
                mNotificationManager.notify(NOTIFICATION_ID, mNotifyBuilder.build());
            }
        });
        savingResponseRunnable.setOnFileSaved(new SavingResponseRunnable.OnFileSaved() {
            @Override
            public void onSaved(Uri uri) {
                FileDownloadedNotifier.getInstance().notify(mFileUrl, uri);
                stopForeground(true);
                stopSelf();
            }
        });
        Executors.newSingleThreadExecutor().execute(savingResponseRunnable);
    }

}
