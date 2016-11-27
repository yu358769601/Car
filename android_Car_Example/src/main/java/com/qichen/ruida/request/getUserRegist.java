package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.person.PersonCenterActivity;
import com.qichen.ruida.ui.ShowOrderForm;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 9:40 . 2016年11月20日
 * 描述:获取个人信息的 网络请求
 * <p>
 * <p>
 * 备注:
 */

public class getUserRegist {
    public static void request(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof ShowOrderForm) {
            final PersonCenterActivity mActivity1 = (PersonCenterActivity) activity;

            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "getXinXi");
            hashtable.put("order_id", SQUtils.getStrings(activity,"passenger_telephone", "" ));

            NetMessage.get(mActivity1)
                    .sendMessage(Constants.urlUserInfo, null, Constants.TEST, new NetAesCallBack() {
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
