package com.qichen.ruida.broadcastReceivers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 17:50 . 2016年11月21日
 * 描述:广播 工具类
 * <p>
 * <p>
 * 备注:
 */
//msg_My  广播发送工具类
public class UtilsBroadcastReceiver {

    /**
     * 发送广播的方法
     * @param context 上下文
     * @param IntentFilter 频道
     * @param boxNum 包裹号
     * @param boxInfo 包裹内容
     */
    public static void sendBroadcastReceiver(Context context, String IntentFilter, String boxNum , String boxInfo){
        mySendBroadcast(context, IntentFilter, boxNum, boxInfo);
    }
    /**
     * 发送广播的方法
     * @param context 上下文
     * @param IntentFilter 频道
     * @param boxNum 包裹号
     * @param boxInfo 包裹内容
     */
    public static void sendBroadcastReceiver(Context context, String IntentFilter, String boxNum , Serializable boxInfo){
        mySendBroadcast(context, IntentFilter, boxNum, boxInfo);
    }

    /**
     * 发送字符串消息
     * @param context
     * @param IntentFilter
     * @param boxNum
     * @param boxInfo
     */
    private static void mySendBroadcast(Context context, String IntentFilter, String boxNum , String boxInfo){
        Intent intent = new Intent(IntentFilter);
        intent.putExtra(boxNum,boxInfo) ;
        sendBroadcast(context,intent);
    }
    /**
     * 发送字符串消息
     * @param context
     * @param IntentFilter
     * @param boxNum
     * @param boxInfo
     */
    private static void mySendBroadcast(Context context, String IntentFilter, String boxNum , int boxInfo){
        Intent intent = new Intent(IntentFilter);
        intent.putExtra(boxNum,boxInfo) ;
        sendBroadcast(context,intent);
    }

    /**
     * 发送序列化消息
     * @param context
     * @param IntentFilter
     * @param boxNum
     * @param boxInfo
     */
    private static void mySendBroadcast(Context context, String IntentFilter, String boxNum , Serializable boxInfo){
        Intent intent = new Intent(IntentFilter);
        Bundle bundle = new Bundle();
        bundle.putSerializable(boxNum,boxInfo);
        intent.putExtras(bundle);
      sendBroadcast(context,intent);
    }

    /**
     * 发送消息
     * @param context
     * @param intent
     */
    private static void sendBroadcast(Context context,   Intent intent){
        context.sendBroadcast(intent);
    }






}
