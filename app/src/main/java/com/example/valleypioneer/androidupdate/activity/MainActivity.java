package com.example.valleypioneer.androidupdate.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.permission.PermissionChecker;
import com.example.permission.ResultListener;
import com.example.valleypioneer.androidupdate.Constants;
import com.example.valleypioneer.androidupdate.R;
import com.example.valleypioneer.androidupdate.model.bean.VersionInfo;
import com.example.valleypioneer.androidupdate.model.gson.ResponseVersion;
import com.example.valleypioneer.androidupdate.net.MyAsyncTask;
import com.example.valleypioneer.androidupdate.net.Net;
import com.example.valleypioneer.androidupdate.util.VersionUtils;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private TextView version;
    private Button update;
    private String apkMD5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResourceID(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void findViews() {
        super.findViews();
        version = findViewById(R.id.version);
        update =  findViewById(R.id.update);
    }

    @Override
    protected void initViews() {
        super.initViews();
        version.setText(Constants.VERSION);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update:
                Net.instance.getVersionInfo().enqueue(new Callback<ResponseVersion>() {
                    @Override
                    public void onResponse(Call<ResponseVersion> call, Response<ResponseVersion> response) {
                        if(response.isSuccessful()){
                            ResponseVersion res = response.body();
                            if(res.getState().equals("successful")){
                                Log.i(TAG,"versionInfo response success");
                                Constants.newVersion = res.getLastestVersion(); /** 设定新版本号 */
                                Constants.apkPath = Constants.APPLICATION_PATH + File.separator + "new_" + Constants.newVersion + ".apk";
                                apkMD5 = res.getMD5();
                                checkVersionInfo(new VersionInfo(res.getLastestVersion(),res.getMinimumVersion(),res.getDownloadUrl(),res.getTitle(),res.getDescription(),res.getMD5()));
                            }else{
                                Log.i(TAG,"versionInfo response failed");
                            }

                        }else{
                            Log.i(TAG,"versionInfo response failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseVersion> call, Throwable t) {
                        Log.i(TAG,"versionInfo response failed");
                    }
                });
        }
    }

    private void checkVersionInfo(final VersionInfo versionInfo){
        int updateCompareRes = VersionUtils.compareVersions(Constants.VERSION,versionInfo.getLastestVersion());
        if(updateCompareRes == VersionUtils.OLDER){
            /** 弹出升级提示 */
            int forceUpdateCompareRes = VersionUtils.compareVersions(Constants.VERSION,versionInfo.getMinimumVersion());
            if(forceUpdateCompareRes == VersionUtils.NEWER || forceUpdateCompareRes == VersionUtils.EQUAL){
                new AlertDialog.Builder(this)
                        .setTitle(versionInfo.getTitle())
                        .setMessage(versionInfo.getDescription())
                        .setPositiveButton("马上升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadNewVersion(versionInfo.getDownloadUrl());
                            }
                        })
                        .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }else{
                /** 强制升级提示 */
                new AlertDialog.Builder(this)
                        .setTitle(versionInfo.getTitle())
                        .setMessage(Constants.FORCED_ESCRIPTION)
                        .setCancelable(false)
                        .setPositiveButton("马上升级", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloadNewVersion(versionInfo.getDownloadUrl());
                            }
                        })
                        .show();
            }

        }
        else if(updateCompareRes == VersionUtils.EQUAL){
            /** 弹出"不用升级"提示 */
            new AlertDialog.Builder(this)
                    .setMessage(Constants.NONEED_DESCRIPTION)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }else{
            /** 弹出"版本号错误"提示 */
            new AlertDialog.Builder(this)
                    .setMessage(Constants.ERROR_DESCRIPTION)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }

    private void downloadNewVersion(String downloadUrl){
        PermissionChecker.checkPermission(this, new ResultListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"读写权限申请成功！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this,"读写权限申请失败！",Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        new MyAsyncTask().execute(downloadUrl,apkMD5);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
