package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.ui.ShowOrderForm;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 17:39 . 2016年11月18日
 * 描述:获取订单详情 的网络请求
 * <p>{"data":{"order_state":"0","order_add_time1":"2016-11-02 14:05:40","begion_address":"抚顺中医医院","positon_lat":"45.7465790","driver_telephone":"13664608180","driver_car":"黑A 135486","end_address":"太平国际机场","positon_lon":"126.6046910"},"message":"成功","result":1}
 * <p>
 * 备注:
 */

public class GetOrderDetail {
    public static void request(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof ShowOrderForm) {
            final ShowOrderForm mActivity1 = (ShowOrderForm) activity;

            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "passengerGetOrderDetail");
            hashtable.put("order_id",mActivity1.mOder_id );

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

/*
{"data":{"order_mileage":"","order_scale":"1.00","order_exception":"0","order_from":"0","order_pingfen":"0","order_receive_address":"","order_driver_address":"","driver_id":"0","order_number":"","order_cancle_describe":"","end_lat":"45.7739410","order_tingche":"0.00","down_time":"0","begion_lon":"126.6051400","end_lon":"126.6189580","begion_address":"共乐街道通达街262号铂宫","passenger_id":"5","order_pingjia":"","up_lon":"0.0000000","order_receive_time":"0","down_address":"","order_receive_lon":"0.0000000","order_type":"0","up_time":"0","order_state":"0","up_lat":"0.0000000","order_driver_lat":"0.0000000","order_money":"0.00","down_lon":"0.0000000","order_wait_time":"0","order_luqiao":"0.00","up_address":"","order_id":"85","begion_lat":"45.7465100","order_get_order":"0","end_address":"中央大街","order_qingjie":"0.00","order_compute_time":"16","order_describe":"","order_receive_lat":"0.0000000","down_lat":"0.0000000","order_add_time":"1479466894","order_compute_mileage":"4.623","order_compute_money":"17.05","order_driver_lon":"0.0000000"},"message":"成功","result":1}

* */
