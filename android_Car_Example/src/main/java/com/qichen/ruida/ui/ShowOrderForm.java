package com.qichen.ruida.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsMyText;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.ruida.R;
import com.qichen.ruida.adapter.itemRecyclerview;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.bean.Order;
import com.qichen.ruida.bean.oderinfostatus;
import com.qichen.ruida.broadcastReceivers.UtilsBroadcastReceiver;
import com.qichen.ruida.request.CancellationOfOrder;
import com.qichen.ruida.request.GetOrderDetail;
import com.qichen.ruida.view.Myrecyclerview_12;
import com.qichen.ruida.view.initAction_Bar;

import java.util.Hashtable;


//查看订单activity
public class ShowOrderForm extends BaseActivity {

    private initAction_Bar mRelativeLayout_title;
    private TextView mShowOrderForm_tv;
    //订单编号 传给 只给取消用的
    public String mOder_id;
    private Myrecyclerview_12 mMyrecyclerview_12_rc;
    private TextView mItem_tv_time;
    private TextView mItem_tv_start;
    private TextView mItem_tv_end;
    private TextView item_tv_status;
    private itemRecyclerview mItemRecyclerview;
    private TextView mShowOrderForm_tv1;
    private oderinfostatus mMsg1;
    private TextView item_tv_carnumber;
    private TextView item_tv_carname;
    private TextView item_tv_carphone;
    private int mOrder_tag;
    private TextView item_tv_timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBox();
        initView();

        //接收方
        //生命周期方法里面注册广播
        //注册广播接收者
        IntentFilter filter = new IntentFilter("order_close");
        registerReceiver(receiver, filter);


                //生命周期方法里面注册广播 获取订单号
                //注册广播接收者
                IntentFilter filter1 = new IntentFilter("data");
        registerReceiver(receiver1, filter1);

                //获取倒计时时间
                IntentFilter filter2 = new IntentFilter("timerStatus");
        registerReceiver(receiver2, filter2);



    }
    //获取前一个界面过来的消息
    private void getBox() {
        Bundle extras = getIntent().getExtras();
        mOder_id = extras.getString("order_id");
        LogUtils.i("查看订单订单号码是"+mOder_id);
        mOrder_tag = extras.getInt("order_tag");
    }


    //在成员变量的位置 创建一个  广播接收类 接收倒计时时间
    private InnerReceiver2 receiver2 = new InnerReceiver2();

    public class InnerReceiver2 extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, Intent intent) {
            int timeRun = intent.getIntExtra("timeRun", 0);
            // String timeStop = intent.getStringExtra("timeStop");
            LogUtils.i("timeRun"+"\t"+timeRun);
            if (timeRun>0){
                item_tv_timer.setText("订单有效期:" + timeRun);
//                if ("0".equals(mMsg1.order_state)) {
//                }else{
//                    item_tv_timer.setText("");
//                }

            }else{
                item_tv_timer.setText("");
               if ("0".equals(mMsg1.order_state)){
                   LogUtils.i("查看订单开始取消订单");
                   Hashtable<String, String> hashtable = new Hashtable<String, String>();
                   hashtable.put("action", "passengerCancleOrder");
                   hashtable.put("order_id", mOder_id);
                   hashtable.put("order_cancle_describe", "超时");
                   CancellationOfOrder.request(context, hashtable, new NetAesCallBack() {
                       @Override
                       public void onSucceed(JSONObject jsonObject) {
                           LogUtils.i("取消订单回来的json"+jsonObject);
                           if (null!=jsonObject){
                               // UtilsToast.showToast(Cancellation_order.this, jsonObject.getString("message"));
                               //创建一个意图 然后放进去数据 发送
                               Intent intent1 = new Intent("order_close");
                               intent1.putExtra("msg","close");
                               LogUtils.i("发送广播"+"关闭查看订单");
                               sendBroadcast(intent1);
                               Intent intent2 = new Intent("setCar");
                               intent2.putExtra("msg","输入目的地");
                               LogUtils.i("发送广播"+"设置字体");
                               sendBroadcast(intent2);


                               UtilsBroadcastReceiver.sendBroadcastReceiver(context, "timerStatus","timeRun",0);
                               finish();
                           }

                       }

                       @Override
                       public void onError(String errorString) {
                           LogUtils.i("取消订单回来的json"+errorString);

                       }
                   });
               }


            }


        }
    }





    //在成员变量的位置 创建一个  广播接收类
    private InnerReceiver1 receiver1 = new InnerReceiver1();

    //接收别的地方过来的数据 写一个内容类
    public class InnerReceiver1 extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {


            //使用intent获取发送过来的数据
            mMsg1 = (oderinfostatus) intent.getSerializableExtra("msg1");
            //String msg = intent.getStringExtra("msg1");
            //JSONObject jsonObject = JSONObject.parseObject(msg);
           // jsonObject.getObject("");
            LogUtils.i("过来的数据服务器是"+ mMsg1);
//            mItem_tv_time = (TextView) findViewById(R.id.item_tv_time);
//            mItem_tv_start = (TextView) findViewById(R.id.item_tv_start);
//            mItem_tv_end = (TextView) findViewById(R.id.item_tv_end);
//            item_tv_status = (TextView) findViewById(R.id.item_tv_status);
//            mShowOrderForm_tv1
            LogUtils.i("轮询订单号是"+ mMsg1.order_id);
             if (null!=mItem_tv_time)
            mItem_tv_time.setText("时间:"+ mMsg1.order_add_time);
            if (null!=mItem_tv_start)
                mItem_tv_start.setText("起点:"+ mMsg1.begion_address);
            if (null!=mItem_tv_end)
                mItem_tv_end.setText("终点:"+ mMsg1.end_address);
            if (null!=item_tv_status)
                item_tv_status.setText("订单状态:"+ mMsg1.order_state);

            String tv = "";
            if ("0".equals(mMsg1.order_state)) {
                tv = "未接单";
                item_tv_timer.setVisibility(View.VISIBLE);
            } else if ("1".equals(mMsg1.order_state)) {
                tv = "已接单";
                item_tv_timer.setVisibility(View.GONE);
            } else if ("2".equals(mMsg1.order_state)) {
                tv = "乘客已上车";
                item_tv_timer.setVisibility(View.GONE);
            } else if ("3".equals(mMsg1.order_state)) {
                tv = "订单完成";
                item_tv_timer.setVisibility(View.GONE);
                //订单完成 关掉界面
                ShowOrderForm.this.finish();
                LogUtils.i("订单完成 关掉界面");
            } else if ("4".equals(mMsg1.order_state)) {
                tv = "乘客取消订单";
            } else if ("5".equals(mMsg1.order_state)) {
                tv = "司机到达目的地";
            } else if ("6".equals(mMsg1.order_state)) {
                tv = "司机取消订单";
                ShowOrderForm.this.finish();
                LogUtils.i("订单取消 关掉界面");
            }else {
                finish();
                LogUtils.i("关掉界面");
            }
            //设定订单状态
            if (null!=item_tv_status)
                item_tv_status.setText("订单状态:"+tv);
            //只要订单不是未接单 我就显示司机信息
            if (!"0".equals(mMsg1.order_state)) {
                //车牌号
                item_tv_carnumber.setText("车牌号:"+mMsg1.driver_car);
                item_tv_carnumber.setVisibility(View.VISIBLE);

                    item_tv_carname.setText("司机名字:"+UtilsMyText.getfristString(mMsg1.driver_realname)+"师傅");
                    item_tv_carname.setVisibility(View.VISIBLE);
//                //司机名字
//                String driver_realname = mMsg1.driver_realname;
//                String substring = driver_realname.substring(0,1);
//                //char c = driver_realname.charAt(0);
//                final char[] chars = driver_realname.toCharArray();
//                item_tv_carname.setText("司机名字:"+substring+"师傅");
                //车电话
                item_tv_carphone.setText("联系方式:"+mMsg1.driver_telephone);
                item_tv_carphone.setVisibility(View.VISIBLE);
                item_tv_carphone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        call(context, mMsg1.driver_telephone);
                    }
                });
            }else{
                item_tv_carname.setVisibility(View.GONE);
                item_tv_carphone.setVisibility(View.GONE);
                item_tv_carnumber.setVisibility(View.GONE);
            }


            if (null!=mShowOrderForm_tv1)
                mShowOrderForm_tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // LogUtils.i("在订单详情里面点击了取消订单"+mMsg1.order_state);
                        if ("2".equals(mMsg1.order_state)){
                            UtilsToast.showToast(ShowOrderForm.this, "乘客上车不能取消订单");
                            return;
                        }
                        Intent intent1 = new Intent(ShowOrderForm.this, Cancellation_order.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("order_id",mOder_id);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                    }
                });

        }

    }

    private  void call(Context context, String num){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
        context.startActivity(intent);
    }



    @Override
    public int getlayouXML() {
        return R.layout.activity_show_order_form;
    }

    @Override
    public void initView() {
        mRelativeLayout_title = (initAction_Bar) findViewById(R.id.relativeLayout_title);
        mRelativeLayout_title.setCallBack(new initAction_Bar.Action_bar_call_back() {
            @Override
            public void getAction_barView_backbutton(Button button) {

            }

            @Override
            public void getAction_barView_title(TextView textView) {
                textView.setText("查看订单");
            }
        });






//        mMyrecyclerview_12_rc = (Myrecyclerview_12) findViewById(R.id.Myrecyclerview_12_rc);
//        UtilsSetManager.LinearLayoutManager(this, RecyclerView.VERTICAL, mMyrecyclerview_12_rc);
//       String url0 ="http://h.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=560bd84239c79f3d8fe1e336829aaa2c/6a63f6246b600c3318724375184c510fd9f9a100.jpg";
//       String url1 ="http://pic.yesky.com/uploadImages/2015/026/38/MG734XC8AM7T.jpg";
//        String url2 ="http://www.gamemei.com/background/uploads/160829/30-160R9143H3343.jpg";
//        String url3 ="http://www.gamemei.com/background/uploads/160829/30-160R9143612P5.jpg";
//        String url4 ="https://img.alicdn.com/imgextra/i3/679481884/TB2VHzLafSM.eBjSZFNXXbgYpXa_!!679481884.jpg_430x430q90.jpg";
//        String url5 ="http://img3.duitang.com/uploads/item/201608/20/20160820091508_diTXf.thumb.700_0.jpeg";
//        String url6 ="http://joymepic.joyme.com/article/uploads/allimg/201609/1473906749455425.jpg?watermark/1/image/aHR0cDovL2pveW1lcGljLmpveW1lLmNvbS9hcnRpY2xlL3VwbG9hZHMvMTYwODE5LzgwLTE2MFE5MUZaMzQzOC5wbmc=/dissolve/70";
//        String url7 ="http://d.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc492d491153ddbb6fd52663328.jpg";

        mItem_tv_time = (TextView) findViewById(R.id.item_tv_time);
        mItem_tv_start = (TextView) findViewById(R.id.item_tv_start);
        mItem_tv_end = (TextView) findViewById(R.id.item_tv_end);
        item_tv_status = (TextView) findViewById(R.id.item_tv_status);
        //有效期
        item_tv_timer = (TextView) findViewById(R.id.item_tv_timer);
        mShowOrderForm_tv1 = (TextView) findViewById(R.id.ShowOrderForm_tv);

        //车牌号
        item_tv_carnumber = (TextView) findViewById(R.id.item_tv_carnumber);
        //司机名字
        item_tv_carname = (TextView) findViewById(R.id.item_tv_carname);
        //车电话
        item_tv_carphone = (TextView) findViewById(R.id.item_tv_carphone);
        //未接单
        if (mOrder_tag ==-1){
            item_tv_carnumber.setVisibility(View.GONE);
            item_tv_carname.setVisibility(View.GONE);
            item_tv_carphone.setVisibility(View.GONE);
            //item_tv_timer.setVisibility(View.GONE);
        }


        if (null!=mOder_id)
        GetOrderDetail.request(this, new NetAesCallBack() {
            @Override
            public void onSucceed(JSONObject jsonObject) {
                LogUtils.i("订单详情网络数据"+jsonObject);
                //jsonObject.getObject("",);

                Order data = jsonObject.getObject("data", Order.class);
                if (!"0".equals(data.order_state)){

                    item_tv_timer.setVisibility(View.GONE);
                }
//                if (null!=data){
//
//                    mItemRecyclerview = new itemRecyclerview(ShowOrderForm.this, 2, data, new itemRecyclerview.CallBack() {
//                        @Override
//                        public void itemOnClick(View view) {
//                            switch (view.getId()){
//                                case R.id.ShowOrderForm_tv_cancel_an_order:
//                                    LogUtils.i("在订单详情里面点击了取消订单");
//                                    Intent intent1 = new Intent(ShowOrderForm.this, Cancellation_order.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("order_id",mMsg1.order_id);
//                                    intent1.putExtras(bundle);
//                                    startActivity(intent1);
//                                    break;
//                            }
//                        }
//                    });
//
//
//                    //mMyrecyclerview_12_rc.setAdapter(mItemRecyclerview);
//
//                }

            }

            @Override
            public void onError(String errorString) {

            }
        });




        initListener();
        initData();
    }

    @Override
    public void initData() {
        //取消订单




        //mShowOrderForm_tv = (TextView) findViewById(R.id.ShowOrderForm_tv);
        //获取订单的请求
//        GetOrderDetail.request(this, new NetAesCallBack() {
//            @Override
//            public void onSucceed(JSONObject jsonObject) {
//                LogUtils.i("获取订单网络数据是啥"+jsonObject);
//                OrderInfo orderInfo = jsonObject.toJavaObject(OrderInfo.class);
//                String begion_address = orderInfo.begion_address;
//                String end_address = orderInfo.end_address;
//                String order_add_time1 = orderInfo.order_add_time1;
//                String order_state = orderInfo.order_state;
//                mItem_tv_time.setText(mItem_tv_time.getText().toString()+order_add_time1);
//                mItem_tv_start.setText(mItem_tv_start.getText().toString()+begion_address);
//                mItem_tv_end.setText(mItem_tv_end.getText().toString()+end_address);
//                item_tv_status.setText("订单状态"+order_state);
//
//            }
//
//            @Override
//            public void onError(String errorString) {
//                LogUtils.i("获取订单网络数据是啥"+errorString);
//            }
//        });



    }

    @Override
    public void initListener() {
//        if (null!=mShowOrderForm_tv)
//        mShowOrderForm_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(ShowOrderForm.this, Cancellation_order.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("order_id",mOder_id);
//                intent1.putExtras(bundle);
//                startActivity(intent1);
//                LogUtils.i("点了查看订单");
//            }
//        });

      //  R.id.ShowOrderForm_tv_cancel_an_order;
//        if (null!=mItemRecyclerview)
//        mItemRecyclerview.setCallBack(new itemRecyclerview.CallBack() {
//            @Override
//            public void itemOnClick(View view) {
//                switch (view.getId()){
//                    case R.id.ShowOrderForm_tv_cancel_an_order:
//                    LogUtils.i("在订单详情里面点击了取消订单");
//                        Intent intent1 = new Intent(ShowOrderForm.this, Cancellation_order.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("order_id",mOder_id);
//                        intent1.putExtras(bundle);
//                        startActivity(intent1);
//                    break;
//                }
//            }
//        });


    }





    //在成员变量的位置 创建一个  广播接收类
    private InnerReceiver receiver = new InnerReceiver();

    //接收别的地方过来的数据 写一个内容类
    public class InnerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //使用intent获取发送过来的数据

            String msg = intent.getStringExtra("msg");
            LogUtils.i("过来的数据是"+msg);
            if (!TextUtils.isEmpty(msg)){
                if ("close".equals(msg)){
                    finish();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消广播
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
        unregisterReceiver(receiver2);
    }

}
