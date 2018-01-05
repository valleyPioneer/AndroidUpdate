package com.example.valleypioneer.androidupdate;

import android.os.Environment;


import java.io.File;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class Constants {
    public static final String VERSION = "1.4";
    public static final String FORCED_ESCRIPTION = "当前版本过低，请立即升级！";
    public static final String NONEED_DESCRIPTION = "当前版本无需更新";
    public static final String ERROR_DESCRIPTION = "版本号错误";
    public static String newVersion;
    public static final String APPLICATION_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "AndroidUpdate" + File.separator;
    public static String apkPath;
}
