package com.open.source.util;

import java.io.File;
import java.math.BigDecimal;

/**
 * 文件工具类
 * Created by feimeng on 2017/8/16.
 */
public class FileUtil {
    /**
     * 得到文件或文件夹的大小
     *
     * @param file 文件
     * @return 字节
     */
    public static long getFileSize(File file) {
        if (file.isFile()) return file.length();
        File[] children = file.listFiles();
        if (children == null) return 0;
        long total = 0;
        for (File child : children)
            total += getFileSize(child);
        return total;
    }

    /**
     * 删除文件或文件夹
     *
     * @param file 文件
     * @return 是否成功
     */
    public static boolean delFile(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children != null)// 递归删除目录中的子目录下
                for (String aChildren : children) {
                    boolean success = delFile(new File(file, aChildren));
                    if (!success) {
                        return false;
                    }
                }
        }
        return file.delete();// 目录此时为空，可以删除
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return_edit size + "Byte";
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "K";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "M";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
