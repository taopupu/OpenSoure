package com.open.source.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 56890 on 2017/11/28.
 */

public class DateUtil {
    //比较给定时间与当前时间间隔  返回自定义格式
    public static String timeSpan(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date dt1 = format.parse(date);
            long timeStamp = dt1.getTime() - new Date().getTime();
            long abs = Math.abs(timeStamp);
            long days = abs / 1000 / 3600 / 24;
            long hour = abs / 1000 / 3600 % 24;
            long min = abs / 1000 % 3600 / 60;
            long s = abs / 1000 % 3600 % 60;
            if (days != 0) {
                return String.valueOf(days + "天" + hour + "小时");
            }
            if (hour != 0) {
                return String.valueOf(hour + "小时");
            }
            if (min != 0) {
                return String.valueOf(min + "分钟" + s + "秒");
            }
            return String.valueOf(s + "秒");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    //比较时间大小
    public static boolean dateCompare(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date dt1 = format.parse(date1);
            Date dt2 = format.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    //判断当前时间是否在指定范围之间
    public static boolean dateScope(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date dt1 = format.parse(date1);
            Date dt2 = format.parse(date2);
            Date date = new Date();
            if (date.getTime() >= dt1.getTime() && date.getTime() <= dt2.getTime()) {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
