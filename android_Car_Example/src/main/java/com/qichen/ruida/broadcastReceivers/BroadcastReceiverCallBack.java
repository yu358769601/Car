package com.qichen.ruida.broadcastReceivers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by 35876 于萌萌
 * 创建日期: 17:46 . 2016年11月21日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public interface BroadcastReceiverCallBack {
    void CallBack(Context context, Intent intent);
}
