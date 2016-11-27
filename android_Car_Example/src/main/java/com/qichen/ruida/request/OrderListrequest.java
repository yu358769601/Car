package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.ui.Hodometer;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:05 . 2016年11月20日
 * 描述:获取订单列表的网络请求
 * <p>
 * <p>
 * 备注:
 */

public class OrderListrequest {
    public static int pn = 1;
    //第一次请求
    public static void request(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof Hodometer) {
            final Hodometer mActivity1 = (Hodometer) activity;
            pn = 1;
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "getPassengerOverOrder");
            LogUtils.i("人员ID"+ SQUtils.getId(activity)+"");
            hashtable.put("passenger_id", SQUtils.getId(activity));
            hashtable.put("pageNo", pn+"");


            NetMessage.get(mActivity1)
                    .sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
                        @Override
                        public void onSucceed(JSONObject jsonObject) {
                            try {
                                LogUtils.i("获取订单列表的网络请求json数据是啥" + jsonObject);
                                //Carbean carbean = JSONArray.toJavaObject(jsonObject, Carbean.class);
                                if (null != jsonObject) {
                                    //addMarkerData(carbean);
                                    netAesCallBack.onSucceed(jsonObject);
                                }
                            } catch (Exception e) {
                                UtilsToast.showToast(mActivity1, "json解析出错" + jsonObject.toString());
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(String errorString) {
                            LogUtils.i("获取订单列表的网络请求json数据是啥" + errorString);
                            netAesCallBack.onError(errorString);
                        }
                    });
        }
    }
    //加载更多请求
    public static void requestmore(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof Hodometer) {
            pn ++;
            final Hodometer mActivity1 = (Hodometer) activity;

            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "getPassengerOverOrder");
            hashtable.put("passenger_id", SQUtils.getId(activity));
            LogUtils.i("pn是多少"+pn);
            hashtable.put("pageNo", pn+"");


            NetMessage.get(mActivity1)
                    .sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
                        @Override
                        public void onSucceed(JSONObject jsonObject) {
                            try {
                                LogUtils.i("json数据是啥加载更多" + jsonObject);
                                //Carbean carbean = JSONArray.toJavaObject(jsonObject, Carbean.class);
                                if (null != jsonObject) {
                                    //addMarkerData(carbean);
                                    netAesCallBack.onSucceed(jsonObject);
                                }
                            } catch (Exception e) {
                                UtilsToast.showToast(mActivity1, "json解析出错" + jsonObject.toString());
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(String errorString) {
                            LogUtils.i("json数据是啥加载更多" + errorString);
                            netAesCallBack.onError(errorString);
                        }
                    });
        }
    }
}
