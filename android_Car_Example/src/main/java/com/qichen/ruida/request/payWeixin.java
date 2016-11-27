package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.ui.PayActivity;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 11:52 . 2016年11月23日
 * 描述:微信支付请求
 * <p>
 * <p>
 * 备注:
 */

public class payWeixin {
    public static void request(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof PayActivity) {
            final PayActivity mActivity1 = (PayActivity) activity;

            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("passenger_id", SQUtils.getId(activity));
            hashtable.put("money", mActivity1.mPayment);
            //hashtable.put("money", "0.01");
            LogUtils.i("发送的数据是"+hashtable);
            NetMessage.get(mActivity1)
                    .sendMessage(Constants.weixin_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
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
