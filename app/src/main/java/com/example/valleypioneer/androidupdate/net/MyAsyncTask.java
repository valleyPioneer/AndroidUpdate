package com.example.valleypioneer.androidupdate.net;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.example.valleypioneer.androidupdate.Application;
import com.example.valleypioneer.androidupdate.Constants;
import com.example.valleypioneer.androidupdate.R;
import com.example.valleypioneer.androidupdate.util.FileUtils;
import com.example.valleypioneer.androidupdate.util.MD5Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class MyAsyncTask extends AsyncTask<String,Long,Void>{
    private NotificationManager notificationManager;
    private Notification.Builder builder;
    private Context context;
    private boolean downLoadState;
    private String apkMD5;
    private static final String TAG = "MyAsyncTask";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        context = Application.getInstance();
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(context);
        builder.setTicker("升级通知...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("下载中...")
                .setContentText("应用升级中...")
                .setOngoing(true); /** 用户无法清除 */
        downLoadState = false;
    }

    @Override
    protected Void doInBackground(String... strings) {
        /** 下载新版apk */
        apkMD5 = strings[1];
        Call<ResponseBody> call = Net.instance.downloadNewVersionApk(strings[0]);
        //同步返回，注意文件流的走向
        try {
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                downLoadState = writeToDisk(response.body());
                Log.i(TAG,"download success");
            }else{
                Log.i(TAG,"download failed");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        /** 更新通知栏中的进度条 */
        builder.setProgress(100,(int)((double)values[0]/values[1] * 100),false);
        notificationManager.notify(0,builder.build());
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (downLoadState && MD5Utils.getApkMD5(Constants.apkPath).equals(apkMD5)) {
            builder.setContentTitle("下载完成!")
                    .setContentText("")
                    .setProgress(0, 0, false)
                    .setOngoing(false);
            Toast.makeText(context, "下载成功！", Toast.LENGTH_SHORT).show();
            installApk(Constants.apkPath);
        }else{
            builder.setContentTitle("下载失败，请重试！")
                    .setContentText("")
                    .setProgress(0,0,true)
                    .setOngoing(false);
            Toast.makeText(context,"下载更新包失败，请重试！",Toast.LENGTH_SHORT).show();
        }
        notificationManager.notify(0,builder.build());

    }

    /** 将得到的文件流输出到SD卡中 */
    private boolean writeToDisk(ResponseBody body){
        FileUtils.createNewFile(Constants.APPLICATION_PATH,Constants.apkPath);
        try{
            BufferedInputStream bufferedInputStream = new BufferedInputStream(body.byteStream());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(Constants.apkPath)));

            try{
                byte[] fileReader = new byte[1024];
                long totalSize = body.contentLength();
                long downloadedSize = 0;
                int readed  = 0;

                while(true){
                    readed = bufferedInputStream.read(fileReader);
                    if(readed == -1)
                        break;
                    bufferedOutputStream.write(fileReader,0,readed); /** 防止最后一次读取到上一次数组末尾的数据 */
                    downloadedSize += readed;
                    Log.i(TAG,"downloadedSize is " + downloadedSize + "|totalSize is " + totalSize);
                    publishProgress(downloadedSize,totalSize); /** 通知ui线程更新下载进度 */
                }
                bufferedOutputStream.flush();
                return true;
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }finally {
                if(bufferedInputStream != null)
                    bufferedInputStream.close();
                if(bufferedOutputStream != null)
                    bufferedOutputStream.close();
            }

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private void installApk(String apkPath){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(context,"com.example.valleypioneer.androidupdate.fileProvider",new File(apkPath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);/** 添加这一句表示对安装程序临时授权该Uri所代表的文件 */
        }
        else
            uri = Uri.fromFile(new File(apkPath));
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
