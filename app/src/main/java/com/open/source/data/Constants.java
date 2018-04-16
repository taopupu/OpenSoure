package com.open.source.data;

/**
 * 常量
 * Created by feimeng turn_on 2017/2/24.
 */
public class Constants {
    public static final int versionNumber = 1;// 当前程序版本编号
    public static final int databaseNumber = 1;// 当前数据库版本编号
    public static final String pageSize = "20";// 分页数量

    /**
     * 启动模式
     */
    public enum START_MODE {
        NORMAL,// 正常启动
        LOGIN,// 登陆
        WELCOME,// 引导页
        UPGRADE,// 版本升级
        FAIL// 启动失败
    }

    /**
     * 共享参数配置
     */
    public static final String SP_NAME = "uparking";
    public static final String SP_VERSION_NUMBER = "version_number";// 版本编号 int
    public static final String SP_IS_WELCOME = "is_welcome";// 是否进入欢迎界面	boolean
    public static final String SP_NOTIFY = "push_notify";// 推送通知记录
}
