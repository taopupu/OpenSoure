package com.open.source.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by feimeng on 2017/5/3.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileManager {
    public static final int picture = 1;
    public static final int audio = 2;
    public static final int video = 3;
    public static final int all = 0;

    public static File getDirectory(int type) {
        String name;
        File silkroad = new File(Environment.getExternalStorageDirectory(), "dianjing");
        switch (type) {
            case picture:
                name = "picture";
                break;
            case audio:
                name = "audio";
                break;
            case video:
                name = "video";
                break;
            case all:
            default:
                if (!silkroad.exists()) {
                    silkroad.mkdirs();
                }
                return silkroad;
        }
        File file = new File(silkroad, name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
