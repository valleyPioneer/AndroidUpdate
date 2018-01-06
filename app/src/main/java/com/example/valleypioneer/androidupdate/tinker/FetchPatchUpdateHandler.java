package com.example.valleypioneer.androidupdate.tinker;

import android.os.Handler;
import android.os.Message;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tinkerpatch.sdk.TinkerPatch;

/**
 * Created by valleypioneer on 2018/1/6.
 */

public class FetchPatchUpdateHandler extends Handler {
    private static final int HOUR_INTERVAL = 3600 * 1000;
    private long checkInterval;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        TinkerPatch.with().fetchPatchUpdate(false);/** 非强制刷新 */
        sendEmptyMessageDelayed(0,checkInterval + 10 * 60 * 1000);
    }

    public void fetchPatchWithInterval(int hour){
        TinkerPatch.with().setFetchPatchIntervalByHours(hour);
        checkInterval = hour * HOUR_INTERVAL;
        sendEmptyMessage(0);
    }

}
