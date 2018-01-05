package com.example.valleypioneer.androidupdate.model.bean;

/**
 * Created by valleypioneer on 2018/1/5.
 */

public class VersionInfo {

    private String lastestVersion;
    private String minimumVersion;
    private String downloadUrl;
    private String title;
    private String description;
    private String MD5;

    public VersionInfo(String lastestVersion, String minimumVersion, String downloadUrl, String title, String description, String MD5) {
        this.lastestVersion = lastestVersion;
        this.minimumVersion = minimumVersion;
        this.downloadUrl = downloadUrl;
        this.title = title;
        this.description = description;
        this.MD5 = MD5;
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
