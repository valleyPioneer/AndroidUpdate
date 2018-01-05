package com.example.valleypioneer.androidupdate.util;

import java.util.List;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class VersionUtils {
    public static final int NEWER = 0;
    public static final int OLDER = 1;
    public static final int EQUAL = 2;

    public static int compareVersions(String currentVersion,String newVersion){
        String[] currentList = currentVersion.split("\\.");
        String[] newList = newVersion.split("\\.");
        int index = 0;
        for(;index <= currentList.length - 1 && index <= newList.length - 1;index++){
            int currentNum = Integer.parseInt(currentList[index]);
            int newNum = Integer.parseInt(newList[index]);
            if(currentNum > newNum)
                return NEWER;
            else if(currentNum < newNum)
                return OLDER;
        }
        if(currentList.length > newList.length) return NEWER;
        else if(currentList.length < newList.length) return OLDER;
        else return EQUAL;
    }
}
