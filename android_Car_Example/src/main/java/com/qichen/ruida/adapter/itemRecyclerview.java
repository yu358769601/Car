package com.qichen.ruida.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qichen.ruida.R;
import com.qichen.ruida.base.baseRecyclerview;
import com.qichen.ruida.bean.Order;


/**
 * Created by 35876 于萌萌
 * 创建日期: 13:47 . 2016年10月21日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class itemRecyclerview extends baseRecyclerview<Order> {
    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;
    private static final int TYPE_3 = 3;
    private static final int TYPE_4 = 4;
    private static final int TYPE_5 = 5;
    private static final int TYPE_6 = 6;
    private static final int TYPE_7 = 7;


    private TextView item_tv_time;
    private TextView item_tv_start;
    private TextView item_tv_end;
    public CallBack mCallBack;
    private TextView item_tv_prder_status;
//    private final MainActivity mGetactivity;
//    private final MyMsgService mMyMsgService;


    public interface CallBack {
        void itemOnClick(View view);
    }


    public void setCallBack(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public itemRecyclerview(Context context, int card, Order json, CallBack mCallBack) {
        super(context, card, json);
        this.mCallBack = mCallBack;


//        mGetactivity = MainActivity.getactivity();
//        mMyMsgService = mGetactivity.mMyMsgService;
//
//        mMyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {
//            @Override
//            public void callBackOrder(JSONObject jsonObject) {
//                LogUtils.i("静态回调 主页面 订单数据" + jsonObject);
//                    oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
//                    LogUtils.i("主页面设置订单号" + data.order_id);
//                    //订单状态 0 未被接单  1 已接单  2 乘客已上车 3 订单完成  4 乘客取消订单 5 司机到达目的地(乘客未上车) 6司机取消订单
//                    LogUtils.i("详情"+data);
//
//
//        });
    }


    @Override
    protected itemView initonCreateViewHolder(ViewGroup parent, int viewType) {
        myItemView itemView = null;
        switch (viewType) {
            case TYPE_0:
                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_0_12, parent, false));
                itemView.initView00();
                break;
            case TYPE_1:
                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_1_12, parent, false));
                itemView.initView00();
                break;
//            case TYPE_2:
//                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_2_12,parent,false));
//                itemView.initView02();
//                break;
//            case TYPE_3:
//                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_3_12,parent,false));
//                itemView.initView03();
//                break;
//            case TYPE_4:
//                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_4_12,parent,false));
//                itemView.initView04();
//                break;
//            case TYPE_5:
//                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_5_12,parent,false));
//                itemView.initView05();
//                break;
//            case TYPE_6:
//                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_6_12,parent,false));
//                itemView.initView06();
//                break;
//            case TYPE_7:
//                itemView = new myItemView(mLayoutInflater.inflate(R.layout.item_7_12,parent,false));
//                itemView.initView07();
//                break;
        }
        return itemView;
    }


    @Override
    protected void initonBindViewHolder(RecyclerView.ViewHolder holder, int position, Order item_json) {
        if (holder instanceof myItemView) {
            myItemView holder1 = (myItemView) holder;
            int itemViewType = getItemViewType(position);
            switch (itemViewType) {
                case TYPE_0:
                    holder1.initData00(item_json);
                    break;
                case TYPE_1:
                    holder1.initView00();
                    break;
//                case TYPE_2:
//                    holder1.initData02();
//                    break;
//                case TYPE_3:
//                    holder1.initData03();
//                break;
//                case TYPE_4:
//                    holder1.initData04();
//                break;
//                case TYPE_5:
//                    holder1.initData05();
//                break;
//                case TYPE_6:
//                    holder1.initData06();
//                break;
//                case TYPE_7:
//                    holder1.initData07();
//                break;
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_0;
        } else if (position == 1) {
            return TYPE_1;
        }
//        else if (position ==2){
//            return TYPE_2;
//        }else if (position ==3){
//            return TYPE_3;
//        }else if (position ==4){
//            return TYPE_4;
//        }else if (position ==5){
//            return TYPE_5;
//        }else if (position ==6) {
//            return TYPE_6;
//        }else if (position ==7) {
//            return TYPE_7;
//        }


        return super.getItemViewType(position);
    }


//    //代理人对象
//    private MyMsgService.MyBind myBinder;
//
//    private ServiceConnection conn = new ServiceConnection() {
//        //当Activity和Service连接成功时会调用该方法
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            // TODO Auto-generated method stub
//            //在这里通过自定义的Binder与Service通信  代理人对象
//            myBinder = (MyMsgService.MyBind) service;
//            final MyMsgService myMsgService = myBinder.getMyMsgService();
//
//            myMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {
//                @Override
//                public void callBackOrder(JSONObject jsonObject) {
//                    LogUtils.i("静态回调 主页面 订单数据" + jsonObject);
//                    oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
//                    LogUtils.i("主页面设置订单号" + data.order_id);
//                    //订单状态 0 未被接单  1 已接单  2 乘客已上车 3 订单完成  4 乘客取消订单 5 司机到达目的地(乘客未上车) 6司机取消订单
//                    LogUtils.i("详情"+data);
//
//                    String tv = "";
//                    if ("0".equals(data.order_state)){
//                        tv = "未接单";
//                    }else if ("1".equals(data.order_state)){
//                        tv = "已接单";
//                    }else if ("2".equals(data.order_state)){
//                        tv = "乘客已上车";
//                    }else if ("3".equals(data.order_state)){
//                        tv = "订单完成";
//                    }else if ("4".equals(data.order_state)){
//                        tv = "乘客取消订单";
//                    }else if ("5".equals(data.order_state)){
//                        tv = "司机到达目的地";
//                    }else if ("6".equals(data.order_state)){
//                        tv = "司机取消订单";
//                    }
//                    item_tv_prder_status.setText("订单状态:"+tv);
//                    item_tv_time.setText("时间:" + data.order_add_time);
//                    item_tv_start.setText("起点:" + data.begion_address);
//                    item_tv_end.setText("终点:" + data.end_address);
//
//                }
//            });
//        }

    //        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };
    public class myItemView extends baseRecyclerview.itemView {

        private TextView mItem_0_12_tv;
        private TextView mItem_1_12_tv;
        private TextView mItem_2_12_tv;
        private ImageView item_0_12_iv;
        private ImageView item_3_12_iv;
        private ImageView item_2_12_iv;
        private ImageView item_4_12_iv;
        private ImageView item_5_12_iv;
        private ImageView item_6_12_iv;
        private ImageView item_7_12_iv;
        private ImageView mItem_1_12_iv;

        private ImageView item_iv_1;

        private TextView ShowOrderForm_tv_cancel_an_order;

        public myItemView(View itemView) {
            super(itemView);
        }

        public void initView00() {
            //时间
            item_tv_time = (TextView) itemView.findViewById(R.id.item_tv_time);
            //开始地点
            item_tv_start = (TextView) itemView.findViewById(R.id.item_tv_start);
            //结束地点
            item_tv_end = (TextView) itemView.findViewById(R.id.item_tv_end);
            //右上角的标志
            item_iv_1 = (ImageView) itemView.findViewById(R.id.item_iv_1);
            //订单状态
            item_tv_prder_status = (TextView) itemView.findViewById(R.id.item_tv_prder_status);

        }


        public void initData00(Order item_json) {
//            item_tv_prder_status.setText("订单状态:"+item_json.order_state);
//            item_tv_time.setText("时间:"+item_json.order_add_time1);
//            item_tv_start.setText("起点:"+item_json.begion_address);
//            item_tv_end.setText("终点:"+item_json.end_address);


            String tv = "";
            if ("0".equals(item_json.order_state)) {
                tv = "未接单";
            } else if ("1".equals(item_json.order_state)) {
                tv = "已接单";
            } else if ("2".equals(item_json.order_state)) {
                tv = "乘客已上车";
            } else if ("3".equals(item_json.order_state)) {
                tv = "订单完成";
            } else if ("4".equals(item_json.order_state)) {
                tv = "乘客取消订单";
            } else if ("5".equals(item_json.order_state)) {
                tv = "司机到达目的地";
            } else if ("6".equals(item_json.order_state)) {
                tv = "司机取消订单";
            }
            if (null != item_tv_prder_status)
                item_tv_prder_status.setText("订单状态:" + tv);
            if (null != item_tv_time)
                // item_tv_time.setText("时间:" + item_json.order_add_time);
                if (null != item_tv_start)
                    item_tv_start.setText("起点:" + item_json.begion_address);
            if (null != item_tv_end)
                item_tv_end.setText("终点:" + item_json.end_address);
        }
    }
            public void initView01(){

            }
            public void initData02(){

            }
//        public void initView02(){
//            mItem_2_12_tv = (TextView) itemView.findViewById(R.id.item_2_12_tv);
//            item_2_12_iv = (ImageView) itemView.findViewById(item_2_12_iv);
//        }
//        public void initData02(){
//            mItem_2_12_tv.setText(json.titile2);
//            Glide
//                    .with(context)
//                    .load(json.url2)
//                    .into(item_2_12_iv);
//        }
//        public void initView03(){
//            item_3_12_iv = (ImageView) itemView.findViewById(R.id.item_3_12_iv);
//        }
//        public void initData03(){
//            Glide
//                    .with(context)
//                    .load(json.url3)
//                    .into(item_3_12_iv);
//        }
//        public void initView04(){
//            item_4_12_iv = (ImageView) itemView.findViewById(R.id.item_4_12_iv);
//        }
//        public void initData04(){
//            Glide
//                    .with(context)
//                    .load(json.url4)
//                    .into(item_4_12_iv);
//        }
//        public void initView05(){
//            item_5_12_iv = (ImageView) itemView.findViewById(R.id.item_5_12_iv);
//        }
//        public void initData05(){
//            Glide
//                    .with(context)
//                    .load(json.url5)
//                    .into(item_5_12_iv);
//        }
//        public void initView06(){
//            item_6_12_iv = (ImageView) itemView.findViewById(R.id.item_6_12_iv);
//        }
//        public void initData06(){
//            Glide
//                    .with(context)
//                    .load(json.url6)
//                    .into(item_6_12_iv);
//        }
//        public void initView07(){
//            item_7_12_iv = (ImageView) itemView.findViewById(R.id.item_7_12_iv);
//
//        }
//        public void initData07(){
//            Glide
//                    .with(context)
//                    .load(json.url7)
//                    .into(item_7_12_iv);
//        }
//        public void initView08(){
//
//        }
//        public void initData08(){
//
//        }
//        public void initView09(){
//
//        }
//        public void initData09(){
//
//        }
}