package com.example.valleypioneer.androidupdate.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class FileUtils {
    /** 创建新文件 */
    public static void createNewFile(String parentPath,String newFilePath){
        File parentFile = new File(parentPath);
        parentFile.mkdirs();
        File newFile = new File(newFilePath);
        if(newFile.exists())
            newFile.delete();
        try {
            newFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
