package com.qichen.ruida.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 35876 于萌萌
 * 创建日期: 17:34 . 2016年11月21日
 * 描述:公共 广播类 在成员变量位置声明
 * <p>
 * <p>
 * 备注:
 */

public class My_broadcastReceiver extends BroadcastReceiver {


    BroadcastReceiverCallBack broadcastReceiverCallBack;
    //
    public My_broadcastReceiver(BroadcastReceiverCallBack broadcastReceiverCallBack){
        this.broadcastReceiverCallBack = broadcastReceiverCallBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        broadcastReceiverCallBack.CallBack(context, intent);
    }


}
