package com.qichen.ruida.request;

import android.app.Activity;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.ui.MainActivity;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

import static com.rongzhiheng.util.Constants.new_url;

/**
 * Created by 35876 于萌萌
 * 创建日期: 16:22 . 2016年11月16日
 * 描述:发送订单 网络请求
 * <p>
 * <p>
 * 备注:
 */

public class SendOrder {
//    Activity mActivity;
//    private MainActivity mActivity1;
//
//    public SendOrder(Activity activity) {
//        this.mActivity = activity;
//    }

    public static void request(Activity activity,final NetAesCallBack netAesCallBack) {
        if (activity instanceof MainActivity) {
            final MainActivity mActivity1 = (MainActivity) activity;

            //发送打车需求
            //如果是我要用车
            Toast.makeText(mActivity1, "打车", Toast.LENGTH_SHORT).show();
            //	UtilsToast.showToast(this, "打车");
            //访问网络 发送打车消息
//				Thread thread = new Thread(new SendOrder());
//				thread.start();

//				hashtable.put("action", "SendOrder");
//				hashtable.put("passenger_id", "1");
//				hashtable.put("begion_address", "共乐街道通达街262号聚贤花园");
//				hashtable.put("end_address", "太平国际机场");
//				hashtable.put("begion_lat", "45.746542");
//				hashtable.put("begion_lon", "126.605064");
//				hashtable.put("end_lat", "45.622432");
//				hashtable.put("end_lon", "126.243605");
//				hashtable.put("order_number", "1");
//				hashtable.put("order_type", "1");
//				hashtable.put("order_compute_mileage", "34.5");
//				hashtable.put("order_compute_money", "90.28");
//				hashtable.put("order_compute_time", "45");
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            hashtable.put("action", "sendOrder");
            hashtable.put("passenger_id", SQUtils.getId(mActivity1));
            hashtable.put("begion_address", mActivity1.getStartPointString());
            hashtable.put("end_address", mActivity1.getEndPointString());
            String[] startPoint = mActivity1.getStartPoint();
            hashtable.put("begion_lat", startPoint[0]);
            hashtable.put("begion_lon", startPoint[1]);
            String[] endPoint = mActivity1.getEndPoint();
            hashtable.put("end_lat", endPoint[0]);
            hashtable.put("end_lon", endPoint[1]);
            hashtable.put("order_time", mActivity1.mYuyue_text.getText().toString());
            hashtable.put("order_type", mActivity1.order_type+"");
            hashtable.put("order_compute_money", mActivity1.yuan);
            hashtable.put("order_compute_mileage", mActivity1.juli);
            hashtable.put("order_compute_time", mActivity1.fen);

            LogUtils.i("发送订单传到后台的数据"+hashtable);
//				Hashtable<String, String> hashMap = new Hashtable<String, String>();
//				//必要字段
//				//"action", "getXinXi"
//				//"passenger_telephone", "13664608180"
////                hashMap.put("action","getXinXi");
////                hashMap.put("passenger_telephone","13664608180");
//
//				  hashMap.put("action", "sendPosition");
//				hashMap.put("driver_id", "2");
//				hashMap.put("positon_lat", "45.746566");
//				hashMap.put("positon_lon", "126.604894");
//				hashMap.put("positon_name", "测试");
//				hashMap.put("positon_passenger", "0");

            NetMessage.get(mActivity1)
                    .sendMessage(new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
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
//				NetMessage.get(this)
//						.sendMessage(Constants.new_url, hashtable, new NetAesCallBack() {
//							@Override
//							public void onSucceed(JSONObject jsonObject) {
//								LogUtils.i("json数据是"+jsonObject);
//							}
//
//							@Override
//							public void onError(String errorString) {
//
//							}
//						});
        }
    }
}
