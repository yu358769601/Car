package com.qichen.ruida.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qichen.Utils.UtilsMyText;
import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.bean.OrderInfo_Details;
import com.qichen.ruida.view.initAction_Bar;

//订单详情的activity
public class OrderInfp_Details_activity extends BaseActivity{

    private initAction_Bar mRelativeLayout_title;
    private OrderInfo_Details mOrderInfo_details;
    private TextView mOrderinfo_details_tv1;
    private TextView mOrderinfo_details_tv2;
    private TextView mOrderinfo_details_tv3;
    private TextView mOrderinfo_details_tv4;
    private TextView mOrderinfo_details_tv5;
    private TextView mOrderinfo_details_tv6;
    private TextView mOrderinfo_details_tv7;
    private TextView mOrderinfo_details_tv8;
    private TextView mOrderinfo_details_tv9;
    private TextView mOrderinfo_details_tv10;
    private TextView mOrderinfo_details_tv11;
    private TextView mOrderinfo_details_tv12;
    private TextView mOrderinfo_details_tv13;
    private TextView mOrderinfo_details_tv14;
    private TextView mOrderinfo_details_tv_pingjia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBox();
        initView();
    }

    private void getBox() {
        Bundle extras = getIntent().getExtras();
        mOrderInfo_details = (OrderInfo_Details) extras.getSerializable("OrderInfo_Details");

    }

    @Override
    public int getlayouXML() {
        return R.layout.activity_order_infp__details_activity;
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
                textView.setText("订单详情");
            }
        });
        if (null!=mOrderInfo_details){
            mOrderinfo_details_tv1 = (TextView) findViewById(R.id.orderinfo_details_tv1);
            mOrderinfo_details_tv2 = (TextView) findViewById(R.id.orderinfo_details_tv2);
            mOrderinfo_details_tv3 = (TextView) findViewById(R.id.orderinfo_details_tv3);
            mOrderinfo_details_tv4 = (TextView) findViewById(R.id.orderinfo_details_tv4);
            mOrderinfo_details_tv5 = (TextView) findViewById(R.id.orderinfo_details_tv5);
            mOrderinfo_details_tv6 = (TextView) findViewById(R.id.orderinfo_details_tv6);
            mOrderinfo_details_tv7 = (TextView) findViewById(R.id.orderinfo_details_tv7);
            mOrderinfo_details_tv8 = (TextView) findViewById(R.id.orderinfo_details_tv8);
            mOrderinfo_details_tv9 = (TextView) findViewById(R.id.orderinfo_details_tv9);
            mOrderinfo_details_tv10 = (TextView) findViewById(R.id.orderinfo_details_tv10);
            mOrderinfo_details_tv11 = (TextView) findViewById(R.id.orderinfo_details_tv11);
            mOrderinfo_details_tv12 = (TextView) findViewById(R.id.orderinfo_details_tv12);
            mOrderinfo_details_tv13 = (TextView) findViewById(R.id.orderinfo_details_tv13);
            mOrderinfo_details_tv14 = (TextView) findViewById(R.id.orderinfo_details_tv14);


            mOrderinfo_details_tv1.setText("订单号:"+mOrderInfo_details.order_id);
            mOrderinfo_details_tv2.setText("起点:"+mOrderInfo_details.begion_address);
            mOrderinfo_details_tv3.setText("终点:"+mOrderInfo_details.end_address);

            mOrderinfo_details_tv4.setText("司机电话:"+UtilsMyText.hide4Num(mOrderInfo_details.driver_telephone));

            mOrderinfo_details_tv5.setText("司机姓名:" + UtilsMyText.getfristString(mOrderInfo_details.driver_realname)+"师傅");

            mOrderinfo_details_tv6.setText("开始时间:"+mOrderInfo_details.up_time1);
            mOrderinfo_details_tv7.setText("结束时间:"+mOrderInfo_details.down_time1);
            mOrderinfo_details_tv8.setText("里程数:"+mOrderInfo_details.order_mileage);
            mOrderinfo_details_tv9.setText("路桥费:"+mOrderInfo_details.order_luqiao);
            mOrderinfo_details_tv10.setText("停车费:"+mOrderInfo_details.order_tingche);
            mOrderinfo_details_tv11.setText("清洁费:"+mOrderInfo_details.order_qingjie);
            mOrderinfo_details_tv12.setText("高峰倍:"+mOrderInfo_details.order_scale);
            mOrderinfo_details_tv13.setText("时长:"+UtilsMyText.get2DoubleString(mOrderInfo_details.chengche_time)+"分");
            mOrderinfo_details_tv14.setText("价格:"+mOrderInfo_details.order_money+"元");


        }
        //评价
        mOrderinfo_details_tv_pingjia = (TextView) findViewById(R.id.orderinfo_details_tv_pingjia);


        initListener();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        //点击了评价
        if (null!=mOrderinfo_details_tv_pingjia)
        mOrderinfo_details_tv_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderInfp_Details_activity.this, AssessActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("order_id","1");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
