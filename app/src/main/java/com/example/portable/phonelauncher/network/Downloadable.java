package com.example.portable.phonelauncher.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by Salenko Vsevolod on 16.05.2017.
 */

public interface Downloadable {
    @GET("/{path}")
    @Streaming
    Call<ResponseBody> downloadFile(@Path("path") String path);
}
