package com.example.valleypioneer.androidupdate.model.gson;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class ResponseVersion {

    /**
     * state : successful
     * lastestVersion : 1.5
     * minimumVersion : 1.1
     * downloadUrl : http://www.baidu.com
     * title : BIG NEWS!!!
     * description : 新版本更新
     * MD5 : adfsfdsf34345
     */

    private String state;
    private String lastestVersion;
    private String minimumVersion;
    private String downloadUrl;
    private String title;
    private String description;
    private String MD5;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastestVersion() {
        return lastestVersion;
    }

    public void setLastestVersion(String lastestVersion) {
        this.lastestVersion = lastestVersion;
    }

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }
}
