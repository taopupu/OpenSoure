package com.open.source.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * BigDecimal加减乘除
 */

public class MathUtil {
    // 进行加法运算
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }

    // 进行减法运算
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }

    // 进行乘法运算
    public static double mul(double d1, double d2, double d3) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal b3 = new BigDecimal(d3);
        return b1.multiply(b2).multiply(b3).doubleValue();
    }

    public static double mulDouble(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }

    // 进行除法运算
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    // 进行四舍五入操作
    public static double round(double d, int len) {
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    //保留两位小数
    public static String holdTwo(double d) {
        DecimalFormat myformat = new DecimalFormat("0.00");
        return myformat.format(d);
    }
}
