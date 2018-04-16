package com.open.source.data.bean;

import org.litepal.crud.DataSupport;

public class AppVersion extends DataSupport {
    private String code;
    private String name;
    private String description;
    private String url;
    private boolean force;// 是否强制更新

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public static AppVersion get() {
        return DataSupport.findFirst(AppVersion.class);
    }

    public static boolean isExist() {
        return DataSupport.findFirst(AppVersion.class) != null;
    }

    public static void clear() {
        DataSupport.deleteAll(AppVersion.class);
    }
}
