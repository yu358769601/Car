package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 10:53 . 2016年11月18日
 * 描述:取消订单 网络请求
 * <p>
 * <p>
 * 备注:
 */

public class CancellationOfOrder {


    public static void request(final Context activity, Hashtable<String, String> hashtable , final NetAesCallBack netAesCallBack) {
            NetMessage.get(activity)
                    .sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
                        @Override
                        public void onSucceed(JSONObject jsonObject) {
                            try {
                                LogUtils.i("json数据是啥" + jsonObject);
                                //Carbean carbean = JSONArray.toJavaObject(jsonObject, Carbean.class);
                                if (null != jsonObject) {
                                    //addMarkerData(carbean);
                                    netAesCallBack.onSucceed(jsonObject);
                                }
                            } catch (Exception e) {
                                UtilsToast.showToast(activity, "json解析出错" + jsonObject.toString());
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(String errorString) {
                            LogUtils.i("json数据是啥" + errorString);
                            netAesCallBack.onError(errorString);
                        }
                    });
        }
    }
