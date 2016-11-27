package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.bean.OrderListBean;
import com.qichen.ruida.ui.Hodometer;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 14:03 . 2016年11月21日
 * 描述:订单详情网络请求 我的行程 item 点击之后的
 * <p>
 * <p>
 * 备注:
 */

public class getOrderInfo_Details {
    public static void request(Context activity, OrderListBean.DataBean oders, final NetAesCallBack netAesCallBack) {
        if (activity instanceof Hodometer) {
            final Hodometer mActivity1 = (Hodometer) activity;

            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "getOrderDetail");
            hashtable.put("order_id",oders.order_id );

            NetMessage.get(mActivity1)
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
