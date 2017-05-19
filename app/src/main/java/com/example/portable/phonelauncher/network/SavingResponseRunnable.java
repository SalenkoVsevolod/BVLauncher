package com.example.portable.phonelauncher.network;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Salenko Vsevolod on 17.05.2017.
 */

public class SavingResponseRunnable implements Runnable {
    private ResponseBody responseBody;
    private String filePath;
    private OnProgressChanged onProgressChanged;
    private OnFileSaved onFileSaved;

    public SavingResponseRunnable(String filePath, ResponseBody responseBody) {
        this.responseBody = responseBody;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        File fl = new File(filePath);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            byte[] fileReader = new byte[4096];
            long fileSize = responseBody.contentLength();
            long fileSizeDownloaded = 0;

            inputStream = responseBody.byteStream();
            outputStream = new FileOutputStream(fl);

            while (true) {
                int read = inputStream.read(fileReader);
                if (read == -1) {
                    break;
                }
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += read;
                if (onProgressChanged != null) {
                    onProgressChanged.onChanged(getProgress((int) fileSize, (int) fileSizeDownloaded));
                }
            }
            outputStream.flush();
            onFileSaved.onSaved(Uri.fromFile(fl));
        } catch (IOException e) {
            Log.e("myTag", "File downloading: " + e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e("myTag", "stream closing: " + e.toString());
            }
        }

    }

    public void setOnProgressChanged(OnProgressChanged onProgressChanged) {
        this.onProgressChanged = onProgressChanged;
    }

    public void setOnFileSaved(OnFileSaved onFileSaved) {
        this.onFileSaved = onFileSaved;
    }

    public interface OnProgressChanged {
        void onChanged(int progress);
    }

    public interface OnFileSaved {
        void onSaved(Uri uri);
    }

    private int getProgress(int max, int downloaded) {
        double res = downloaded / (double) max;
        res *= 100;
        return (int) res;
    }
}