package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.service.MyMsgService;
import com.qichen.ruida.ui.MainActivity;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 18:23 . 2016年11月18日
 * 描述:获取订单状态的 网络请求
 * <p> 如果 data 是 false  就是没有订单
 *      data
 * <p>
 * 备注:
 */

public class getOrder {
    public static void request(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof MainActivity) {
            final MyMsgService mActivity1 = (MyMsgService) activity;

            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "passengerGetOrder");
            hashtable.put("passenger_id", SQUtils.getId(mActivity1));

            NetMessage.get(activity)
                    .sendMessage(Constants.urlorder, null, Constants.TEST, new NetAesCallBack() {
                        @Override
                        public void onSucceed(JSONObject jsonObject) {
                            try {
                                LogUtils.i("获取订单状态的" + jsonObject);
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
                            LogUtils.i("json数据是啥" + errorString);
                            netAesCallBack.onError(errorString);
                        }
                    });
        }
    }
}
