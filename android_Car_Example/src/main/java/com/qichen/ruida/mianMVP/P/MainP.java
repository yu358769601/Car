package com.qichen.ruida.mianMVP.P;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.ruida.MVP_base.MVP_base;
import com.qichen.ruida.Utils_1;
import com.qichen.ruida.bean.DriverPositon;
import com.qichen.ruida.bean.PeripheralInfo;
import com.qichen.ruida.bean.oderinfostatus;
import com.qichen.ruida.mianMVP.interface_.MainCallBack;
import com.qichen.ruida.service.MyMsgService;
import com.qichen.ruida.ui.MainActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:51 . 2016年11月28日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */
// 父类是 设置 子类的回调  泛型 是 子类的 应该实现的方法
public class MainP extends MVP_base<MainCallBack> {
    public ExecutorService mStartlogo;
    public ExecutorService mDownTime;
    public ExecutorService mExecutorService;
    MainCallBack listener ;
    Context context;

    private Intent mBindIntent;

    //回调回来的服务
    public MyMsgService mMyMsgService;
    private final MainActivity mContext;

    public MainP(Context context){
        this.context = context;
        mContext = (MainActivity)context;
    }
    //本页 回调
    @Override
    public void setListener(MainCallBack listener) {
        this.listener = listener;
    }

    /**
     * 计费算法
     * @param distance 公里数
     * @param duration 时间
     * @return
     */
    @Override
    public double[] sun(float distance, int duration) {
        // 公里数小于 <=3 公里  9元 +时间(分钟) * 0.3元
        //   >3  <=15 公里  9元  + (当前公里数 -3) *2 +时间(分钟) *0.3元
        // >15公里  9元 + 12公里 *2  + (当前公里数 -15) *2.8 +时间(分钟) *0.3元
        double[] doubles = {};
        double Result =0.0;
        if (distance<=3){
            // 公里数小于 <=3 公里  9元 +时间(分钟) * 0.3元
            Result =9+(duration*0.3);
        }else if (distance>3 && distance<=15){
            //   >3  <=15 公里  9元  + (当前公里数 -3) *2 +时间(分钟) *0.3元
            Result =(9+(distance-3)*2)+(duration*0.3);
        }else{
            Result =(9+(12*2)+(distance-15) *2.8)+(duration*0.3);
        }
        doubles = new double[]{Result,9,0.3};
        //listener.succeed("目前的是成功"+doubles.toString());
        return doubles;
    }

    @Override
    public void initThreadPool() {

        //创建一个线程池 这个线程池的作用是 轮询访问网络  获取周边信息
        mExecutorService = Executors.newSingleThreadExecutor();
        LogUtils.i("debug 测试"+"线程池"+mExecutorService+"上下文"+mContext);

        mExecutorService.submit(mContext.getloopNetClass());


        //创建一个线程池 延时 改变logo
        mStartlogo = Executors.newSingleThreadExecutor();
        mStartlogo.submit(mContext.getLogoClass());

        //倒计时的线程池
        mDownTime = Executors.newFixedThreadPool(4);
    }

    //代理人对象
    private MyMsgService.MyBind myBinder;

    private ServiceConnection conn = new ServiceConnection() {
        boolean tag = false;

        //当Activity和Service连接成功时会调用该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            //在这里通过自定义的Binder与Service通信  代理人对象
            myBinder = (MyMsgService.MyBind)service;
            mMyMsgService = myBinder.getMyMsgService();
            mMyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {

                //服务回调
                @Override
                public void callBackOrder(JSONObject jsonObject) {
                    LogUtils.i("轮询获取订单数据"+jsonObject);
                    oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
                    LogUtils.i("主页面设置订单号"+data.order_id);
                    //订单号(重要)
                    //mOder_id =data.order_id;
                    listener.getOrder_id(data.order_id);
                    LogUtils.i("订单状态轮询"+data.order_state);
                    //乘客订单状态
                    //mOrder_state = data.order_state;
                    listener.getOrder_state(data.order_state);
                //订单状态 0 未被接单  1 已接单  2 乘客已上车 3 订单完成
                //  4 乘客取消订单 5 司机到达目的地(乘客未上车) 6司机取消订单  -1没订单

//					if (!order_stuatus.equals(data.order_state)){
//								//有订单  如果是未接单
//								order_stuatus_frist = false;
//					}

                    //发了订单 未接
                    if ("0".equals(data.order_state)){
                        //order_stuatus_frist = false;
                        LogUtils.i("订单状态不是-1"+"号码是"+data.order_id);
                        //用这个数值 去打开 查看订单
                        mContext.Tagclass = 1;
                        //mDestinationButton.setText("查看订单");
                        //mDestinationButton.setText("查看订单"+timers);
                        tag = true;
                    }else if ("1".equals(data.order_state)){
                        //mDestinationButton.setText("查看订单");
                        //mDestinationButton.setText("查看订单"+timers);
                    }else if ("-1".equals(data.order_state)){
                        //mDestinationButton.setText("我要用车");
                        //没进来过
                        if (tag){
                            tag =false;
                            mContext.mDestinationButton.setText("输入目的地");
                        }
                    }



                }
            });
            //服务回调
//			MyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {
//				@Override
//				public void callBackOrder(JSONObject jsonObject) {
//					LogUtils.i("静态回调 主页面 订单数据"+jsonObject);
//					oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
//					LogUtils.i("主页面设置订单号"+data.order_id);
//					mOrder_id = data.order_id;
//					if (SQUtils.isCallCar(MainActivity.this)){
//						mDestinationButton.setText("查看订单");
//					}
//				}
//			});
        }


        //当Activity和Service断开连接时会调用该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }

    };


    @Override
    public void doBindSerive() {
        mBindIntent = new Intent(context, MyMsgService.class);
        //bindIntent.setAction("com.test.service.My_MSG");
        //当Service还没创建时,
        //第三个参数如果为0则不自动创建Service,为Service.BIND_AUTO_CREATE则自动创建
        context.bindService(mBindIntent, conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void doUnbindService() {
        context.unbindService(conn);
        context.stopService(mBindIntent);
    }

    @Override
    public void addMarkerData(AMap mAmap,DriverPositon driverPositon) {
        LogUtils.i("添加单个覆盖物"+driverPositon);
        if (null!=driverPositon){
            Utils_1.addEmulateData(mAmap, driverPositon);
            return;
        }

    UtilsToast.showToast(context, "没有获取到周边车辆信息");
    }

    @Override
    public void addMarkerData(AMap mAmap,PeripheralInfo peripheralInfo) {
        if (null!=peripheralInfo.data  ){
            List<PeripheralInfo.DataBean> data = peripheralInfo.data;
            if (data.size()>0){
                Utils_1.addEmulateData(mAmap, data);
                return;
            }
        }

        UtilsToast.showToast(context, "没有获取到周边车辆信息");
    }


}
