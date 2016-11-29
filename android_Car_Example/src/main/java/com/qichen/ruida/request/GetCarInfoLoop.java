package com.qichen.ruida.request;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.bean.PeripheralInfo;
import com.qichen.ruida.ui.Cancellation_order;
import com.qichen.ruida.ui.MainActivity;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:31 . 2016年11月18日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class GetCarInfoLoop {
    public static void request(Context activity, final NetAesCallBack netAesCallBack) {
        if (activity instanceof Cancellation_order) {
            final MainActivity mActivity1 = (MainActivity) activity;


            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            //必要字段
            //					passengerPosition action
            //					passenger_lon 经度
            //					passenger_lat 纬度
            //					passenger_id 用户身份
            hashtable.put("action","passengerPosition");
            hashtable.put("passenger_lon",mActivity1.mStartPosition.longitude+"");
            hashtable.put("passenger_lat",mActivity1.mStartPosition.latitude+"");
            hashtable.put("passenger_id", SQUtils.getId(mActivity1));

            NetMessage.get(mActivity1)
                    .sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
                        @Override
                        public void onSucceed(JSONObject jsonObject) {
                            LogUtils.i("网络过来的数据"+jsonObject);
                            if (null!=jsonObject){
                                try{
                                    PeripheralInfo peripheralInfo = jsonObject.toJavaObject(PeripheralInfo.class);
                                    LogUtils.i("周边数据是"+peripheralInfo);
                                    Double message = jsonObject.getDouble("message");
                                    mActivity1.msgs = message;
                                    netAesCallBack.onSucceed(jsonObject);
                                    //Double.parseDouble(message);
                                    //jsonObject.get();
                                    //添加覆盖物
                                    //mActivity1.addMarkerData(peripheralInfo);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                            }


                        }

                        @Override
                        public void onError(String errorString) {
                            LogUtils.i("网络过来的数据"+errorString);
                            netAesCallBack.onError(errorString);
                        }
                    });



            mActivity1.i++;
            LogUtils.i("时间"+mActivity1.i);
            if (null!=mActivity1.mMainC.mExecutorService){
                if (!mActivity1.mMainC.mExecutorService.isShutdown()){
                    LogUtils.i("提交出来的");
                    mActivity1.mMainC.mExecutorService.submit(mActivity1.getloopNetClass());
                }
                //mExecutorService.shutdown();
            }
        }
    }
}
