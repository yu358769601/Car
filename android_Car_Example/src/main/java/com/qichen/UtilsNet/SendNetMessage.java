package com.qichen.UtilsNet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.rongzhiheng.util.AES;
import com.rongzhiheng.util.Constants;
import com.rongzhiheng.util.Utils;

import java.util.Hashtable;

/**
 * Created by 35876 于萌萌
 * 创建日期: 9:05 . 2016年11月08日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */
public class SendNetMessage  implements Runnable {
    Context activity;
    String url;
    Handler handler;
    Hashtable<String,String>stringHashMap ;
    private Handler sendPositionHandler;
    private String mAesString;
    NetAesCallBack netAesCallBack;
    public static final int SENDORDER_CALLBACK = 0;
    public static final int SENDORDER_HANDLER = 1;
    int status = 0;
    private JSONObject mJsonObject;
    private String mResult;
    int  type;
    public SendNetMessage(Context activity, String url, Hashtable<String, String> stringHashMap, int type,Handler sendPositionHandler){
        this.type =type;
        this.activity =activity;
        this.url=url;
        this.stringHashMap =stringHashMap;
        this.sendPositionHandler =sendPositionHandler;
        status = SENDORDER_HANDLER;
    }
    public SendNetMessage(Context activity, String url, Hashtable<String,String>stringHashMap, int type, Handler handler, NetAesCallBack netAesCallBack){
        this.url=url;
        this.type =type;
        this.stringHashMap =stringHashMap;
        this.netAesCallBack = netAesCallBack;
        this.handler = handler;
        status = SENDORDER_CALLBACK;
    }

    // msgObject.put("action", "SendNetMessage");
    // msgObject.put("passenger_id", passenger_id); //乘客Id
    // msgObject.put("begion_address", begion_address); //开始地址
    // msgObject.put("end_address", end_address); //终点地址
    // msgObject.put("begion_lat", begion_lat); //开始纬度
    // msgObject.put("begion_lon", begion_lon); //开始经度
    // msgObject.put("end_lat", end_lat); //终点纬度
    // msgObject.put("end_lon", end_lon); //终点经度
    // msgObject.put("order_number", order_number); //打车人数
    // msgObject.put("order_type", order_type); //0、马上打车 1、预约打车
    // msgObject.put("order_compute_mileage", order_compute_mileage); //预估里程
    // msgObject.put("order_compute_money", order_compute_money); //预估价格
    // msgObject.put("order_compute_time", order_compute_time); //预估时间

    //            msgObject.put("action", "SendNetMessage");
//            msgObject.put("passenger_id", "1");
//            msgObject.put("begion_address", "共乐街道通达街262号聚贤花园");
//            msgObject.put("end_address", "太平国际机场");
//            msgObject.put("begion_lat", "45.746542");
//            msgObject.put("begion_lon", "126.605064");
//            msgObject.put("end_lat", "45.622432");
//            msgObject.put("end_lon", "126.243605");
//            msgObject.put("order_number", "1");
//            msgObject.put("order_type", "1");
//            msgObject.put("order_compute_mileage", "34.5");
//            msgObject.put("order_compute_money", "90.28");
//            msgObject.put("order_compute_time", "45");
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {

            JSONObject msgObject = new JSONObject();
            if (null!=stringHashMap)
            msgObject.putAll(stringHashMap);
            if (type== Constants.NORMAL){
                // 上传的参数
                String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
               // System.out.println(msgObject.toString());
                LogUtils.i("加密之后网络字符串"+para);
                // 返回的加密串url
                mAesString = Utils.postUrlData(url, para);
                LogUtils.i("网络数据返回来的加密网络字符串"+mAesString);
                //解谜之后
                String decrypt = AES.decrypt(Constants.key, Constants.iv, mAesString);
                LogUtils.i("正常解密原始网络数据"+decrypt);
                //容易出错的地方
                mJsonObject = JSON.parseObject(decrypt);
                mResult = mJsonObject.getString("result");
                LogUtils.i("结果码"+mResult);

//                float s =1.0;
//                int s1 = (int) s;
            }else{
                //除了正常以外的都是 测试
                // 返回的加密串url
                mAesString = Utils.postUrlData(url, msgObject.toString());
                LogUtils.i("返回来的数据"+mAesString);
                //容易出错的地方
                mJsonObject = JSON.parseObject(mAesString);
                if (TextUtils.isEmpty(mAesString) && null!=netAesCallBack){

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            netAesCallBack.onError("网络数据返回为空");
                        }
                    });
                    return;
                }

                if (null!=mJsonObject)
                mResult = mJsonObject.getString("result");
                LogUtils.i("返回来的数据解析"+mJsonObject);
            }



            // mAesString= msgObject.toString();
            if (status ==SENDORDER_CALLBACK){

              //  new getJson().execute(mJsonObject);
                //如果是测试
                if(type!= Constants.NORMAL){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                LogUtils.i("目前线程名字"+Thread.currentThread().getName());
                                if (null!=mJsonObject){

                                    netAesCallBack.onSucceed(mJsonObject);
                                }else{

                                    netAesCallBack.onError(mResult +"json数据错误"+ mJsonObject.toString());
                                }
                            }catch (Exception e){
                                netAesCallBack.onError(mResult +"json数据错误"+ mJsonObject.toString());
                            }


                        }
                    });

                }else{
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                LogUtils.i("目前线程名字"+Thread.currentThread().getName());
                                if (mResult.equals("1")||type!= Constants.NORMAL){

                                    netAesCallBack.onSucceed(mJsonObject);
                                }else{

                                    netAesCallBack.onError(mResult +"json数据错误"+ mJsonObject.toString());
                                }
                            }catch (Exception e){
                                netAesCallBack.onError(mResult +"json数据错误"+ mJsonObject.toString());
                            }


                        }
                    });

                }
            }
           if (status ==SENDORDER_HANDLER){
               // 构建handler
               Message msg = sendPositionHandler.obtainMessage();
               Bundle bundle = new Bundle();
               bundle.putString("sendPosition", mAesString);
               msg.setData(bundle);
               sendPositionHandler.sendMessage(msg);
           }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (null!=netAesCallBack)
                        netAesCallBack.onError("json解析数据错误");
                }
            });
            e.printStackTrace();
        }
    }
//    Param:发送给新开的线程的参数类型
//    Progress：表征任务处理进度的类型。
//    Result:线程任务处理完之后，返回给UI线程的值的类型。
//        class getJson extends AsyncTask<JSONObject, Void, JSONObject> {
//
//    @Override
//    protected JSONObject doInBackground(JSONObject... jsonObjects) {
//        return jsonObjects[0];
//    }
//
//    @Override
//    protected void onPostExecute(JSONObject jsonObject) {
//
//    }
//}
}

//    /**
//     * 界面handler
//     */
//    public Handler sendPositionHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("sendPosition"));
//                JSONObject obj = new JSONObject(str);
//                Log.i("str", str);
//                // 有数据
//                if (obj.getString("result").equals("1")) {
//                    Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    };
