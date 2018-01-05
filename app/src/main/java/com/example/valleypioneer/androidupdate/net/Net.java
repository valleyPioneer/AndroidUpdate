package com.example.valleypioneer.androidupdate.net;

import com.example.valleypioneer.androidupdate.model.gson.ResponseVersion;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public interface Net {
    Net instance = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://raw.githubusercontent.com/valleyPioneer/VersionController/master/")
            .build()
            .create(Net.class);

    @GET("versionInfo.json")
    Call<ResponseVersion> getVersionInfo();

    @GET
    Call<ResponseBody> downloadNewVersionApk(@Url String downloadUrl);
}
