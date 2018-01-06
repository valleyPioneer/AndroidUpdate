package com.example.valleypioneer.androidupdate;


import android.os.Build;

import com.example.valleypioneer.androidupdate.tinker.FetchPatchUpdateHandler;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class Application extends android.app.Application {
    private static Application instance;
    private ApplicationLike applicationLike;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if(BuildConfig.TINKER_ENABLE){
            applicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            TinkerPatch.init(applicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true);
            new FetchPatchUpdateHandler().fetchPatchWithInterval(3); /** 每三个小时轮询一次 */
        }

    }

    public static Application getInstance(){
        return instance;
    }
}
