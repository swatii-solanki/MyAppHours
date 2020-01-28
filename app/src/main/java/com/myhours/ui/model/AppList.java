package com.myhours.ui.model;

import android.graphics.drawable.Drawable;

public class AppList {
    private String appName;
    private  String appPackageName;
    private Drawable appIcon;

    public AppList(String appName, String appPackageName, Drawable appIcon) {
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.appIcon = appIcon;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }
}
