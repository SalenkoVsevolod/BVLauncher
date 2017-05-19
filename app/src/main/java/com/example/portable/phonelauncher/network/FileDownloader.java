package com.example.portable.phonelauncher.network;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Salenko Vsevolod on 16.05.2017.
 */

public class FileDownloader {
    private String url;

    public FileDownloader(String url) {
        this.url = url;
    }

    public void getVideoFromServer(final OnServerResponse onServerResponse) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getBaseUrl()).build();
        Downloadable downloadable = retrofit.create(Downloadable.class);
        Call<ResponseBody> call = downloadable.downloadFile(getFileUrl());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                onServerResponse.onResponse(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("myTag", "file downloading: " + t);
            }
        });
    }

    public interface OnServerResponse {
        void onResponse(Response<ResponseBody> response);
    }

    private String getBaseUrl() {
        String res;
        res = url.replace("http://", "");
        res = res.substring(0, res.indexOf('/')+1);
        res = "http://" + res;
        Log.i("myTag", "baseUri: " + res);
        return res;
    }

    private String getFileUrl() {
        String res = url.replace(getBaseUrl(), "");
        Log.i("myTag", "fileUri: " + res);
        return res;
    }
}
