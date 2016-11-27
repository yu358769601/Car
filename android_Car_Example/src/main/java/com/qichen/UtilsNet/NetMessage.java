package com.qichen.UtilsNet;

import android.content.Context;
import android.os.Handler;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 35876 于萌萌
 * 创建日期: 9:09 . 2016年11月08日
 * 描述:访问网络主要方法 处理网络请求 获取json 访问网络
 * <p>
 * <p>
 * 备注:
 */

public class NetMessage {
    static NetMessage mNetMessage;
    static Context mContext;
    static ExecutorService sExecutorService;
    static Handler mHandler;
    public static NetMessage get(Context context){
        mContext = context;
        if (null==mNetMessage){
            sExecutorService = Executors.newFixedThreadPool(4);
            mNetMessage = new NetMessage();
             mHandler =  new Handler();

        }

        return mNetMessage;
    }
    public void sendMessage(String url,Hashtable<String,String> stringHashMap,int type,NetAesCallBack netAesCallBack){
        SendNetMessage sendNetMessage = new SendNetMessage(mContext, url,stringHashMap, type,mHandler,netAesCallBack);
        sExecutorService.execute(sendNetMessage);

    }
    public void sendMessage(String url,Hashtable<String,String> stringHashMap, int type,Handler sendPositionHandler){
        SendNetMessage sendNetMessage = new SendNetMessage(mContext,url,stringHashMap, type,sendPositionHandler);
        sExecutorService.execute(sendNetMessage);

    }





}
