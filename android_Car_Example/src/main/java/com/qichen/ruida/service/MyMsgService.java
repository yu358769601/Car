package com.qichen.ruida.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.R;
import com.qichen.ruida.bean.oderinfostatus;
import com.qichen.ruida.broadcastReceivers.UtilsBroadcastReceiver;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 35876 于萌萌
 * 创建日期: 15:34 . 2016年11月19日
 * 描述: 轮询 获取单个 用户的 订单状态 这个是工作在子线程的
 * <p>
 * <p>
 * 备注:
 */

public class MyMsgService extends Service {
    //没进去过
    boolean order_stuatus_frist = true;
    String order_stuatus = "";
   public int i;
 public   CallBackOrderInfo mCallBackOrderInfo;
    public oderinfostatus mData;
    private SoundPool soundPool;

    public interface CallBackOrderInfo {
        void callBackOrder(JSONObject jsonObject);
    }

    public  void setCallBackOrderInfo(CallBackOrderInfo callBackOrderInfo){
        mCallBackOrderInfo = callBackOrderInfo;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                        i++;
                        LogUtils.i("服务任务时间"+i);
                    if (SQUtils.isLogin(MyMsgService.this))
                   {
                        //获取订单状态
                        getOrderInfo();
                    }

                        if (null!= mExecutorService){
                            if (!mExecutorService.isShutdown())
                                mExecutorService.submit(new loopNet());
                            //mExecutorService.shutdown();
                        }



                    break;
            }
            }


    };
    private ExecutorService mExecutorService;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */


    public MyMsgService() {
      //  super("MyMsgService");
    }


   public class  MyBind extends Binder {
        public MyMsgService getMyMsgService()
        {
            return MyMsgService.this;
        }
    }



    /**
     * Service被创建是会回调该方法
     */
    @Override
    public void onCreate()
    {
        //Log.i(TAG, "onCreate()");
        LogUtils.i("服务创建");
        super.onCreate();
        initsound();

        initData();


    }

    private void initsound() {
        //创建一个声音池子
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(MyMsgService.this, R.raw.collide, 1);
    }

    private void initData() {
        //创建一个线程池
        mExecutorService = Executors.newSingleThreadExecutor();
        mExecutorService.submit(new loopNet());
    }

    //循环体
    public   	class loopNet implements Runnable{
        @Override
        public void run() {
            //现在是开启状态
                Message message = mHandler.obtainMessage();
                message.what = 1;
                mHandler.sendMessage(message);
                SystemClock.sleep(2000);

        }

    }



    public void getOrderInfo(){

            LogUtils.i("服务开启了" + i);

        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("action", "passengerGetOrder");
        hashtable.put("passenger_id", SQUtils.getId(this));
        LogUtils.i("轮询获取后台数据的id"+SQUtils.getId(this));
        NetMessage.get(this)
                .sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        try {
                            LogUtils.i("MyMsgService服务订单状态jsonObject" + jsonObject);
                            //Carbean carbean = JSONArray.toJavaObject(jsonObject, Carbean.class);
                            if (null != jsonObject) {
                                //addMarkerData(carbean);
                                //netAesCallBack.onSucceed(jsonObject);
                                //创建一个意图 然后放进去数据 发送
                                Intent intent1 = new Intent("data");
                                oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
                                intent1.putExtra("msg1", data);
                                LogUtils.i("发送广播服务广播");
                                sendBroadcast(intent1);

                                UtilsBroadcastReceiver.sendBroadcastReceiver(MyMsgService.this, "order_Info","order","服务器后台发数据了 附近的车辆信息");

                                mCallBackOrderInfo.callBackOrder(jsonObject);
                                mData = jsonObject.getObject("data", oderinfostatus.class);
                                SQUtils.setCallCar(MyMsgService.this, mData.order_state );
                                SQUtils.setCallCarOrder_id(MyMsgService.this, mData.order_id );
                                //发了订单 未接
                                if ("0".equals(data.order_state)){
                                    order_stuatus_frist = true;

                                    LogUtils.i("订单状态不是-1"+"号码是"+data.order_id);


                                }else if ("1".equals(data.order_state)){
                                    LogUtils.i("来订单了响"+order_stuatus_frist);
                                    if (order_stuatus_frist){
                                        soundPool.play(1, 1, 1, 0, 0, 1);
                                        order_stuatus_frist = false;
                                        order_stuatus = data.order_state;
                                    }
                                    //mDestinationButton.setText("查看订单");

                                }else if ("-1".equals(data.order_state)){
                                    //mDestinationButton.setText("我要用车");
                                    order_stuatus_frist = true;
                                }


                            }
                        } catch (Exception e) {
                            UtilsToast.showToast(MyMsgService.this, "json解析出错" + jsonObject.toString());
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(String errorString) {
                        LogUtils.i("json数据是啥" + errorString);
                        //netAesCallBack.onError(errorString);
                    }
                });


        }


    /**
     * Service被启动后会回调该方法
     */
    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {

        //Log.i(TAG, "onStartCommand()");
        LogUtils.i("服务启动");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    /**
     * 绑定Service时会回调该方法
     */
    @Override
    public IBinder onBind(Intent intent) {
       // Log.i(TAG, "onBind()");
        // 返回Binder的子类MyBinder和Activity通信
        return new MyBind();
    }

    /**
     * 如果前面是通过bindService()来绑定启动Service的,
     * 那么当解绑停止Service时会调用该方法
     */
    @Override
    public boolean onUnbind(Intent intent)
    {
       // Log.i(TAG, "onUnbind()");
        LogUtils.i("通过绑定开启服务的解绑");
        return true;
    }

    /**
     * Service被销毁前会回调该方法
     */
    @Override
    public void onDestroy()
    {
        //Log.i(TAG, "onDestroy()");
        LogUtils.i("服务关闭");
        super.onDestroy();
        if (null!=mExecutorService){
            LogUtils.i("关闭了程序 关闭线程服务关闭 获取订单的");
            mExecutorService.shutdownNow();
        }
    }

}