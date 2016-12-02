package com.example.notifications.mvp.p;

/**
 * Created by 35876 于萌萌
 * 创建日期: 14:24 . 2016年11月29日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public interface Mian_interface {
    //初始化通知栏
    void initNotify();
    //显示通知栏
    void showNotify();
    //显示大通知栏
    void showBigStyleNotify();
    //常驻通知栏
    void showCzNotify();
    /** 显示通知栏点击跳转到指定Activity */
     void showIntentActivityNotify();
    /** 显示通知栏点击打开Apk */
     void showIntentApkNotify();



}
