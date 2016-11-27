package com.qichen.ruida.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsMyText;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.broadcastReceivers.UtilsBroadcastReceiver;
import com.qichen.ruida.request.CancellationOfOrder;
import com.qichen.ruida.view.initAction_Bar;

import java.util.Hashtable;

//取消原因activity
public class Cancellation_order extends BaseActivity {
   public String order_id;
    private RadioGroup mCancellation_rg;
    private EditText mCancellation_ed;
    private TextView cancellation_tv_sub;
    private initAction_Bar mRelativeLayout_title;
    public String mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBox();
        initView();
    }
    //获取前一个界面过来的数据
    private void getBox() {
        Bundle extras = getIntent().getExtras();
         order_id = extras.getString("order_id","");
        LogUtils.i("订单号码是"+order_id);
    }

    @Override
    public int getlayouXML() {
        return R.layout.activity_cancellation_order;
    }

    @Override
    public void initView() {
        //选择框 的组
        mCancellation_rg = (RadioGroup) findViewById(R.id.cancellation_rg);
        //输入框 input 原因
        mCancellation_ed = (EditText) findViewById(R.id.cancellation_ed);
        //提交
        cancellation_tv_sub = (TextView) findViewById(R.id.cancellation_tv_sub);

        mRelativeLayout_title = (initAction_Bar) findViewById(R.id.relativeLayout_title);
        mRelativeLayout_title.setCallBack(new initAction_Bar.Action_bar_call_back() {
            @Override
            public void getAction_barView_backbutton(Button button) {

            }

            @Override
            public void getAction_barView_title(TextView textView) {
                textView.setText("取消原因");
            }
        });

        initListener();
    }
    @Override
    public void initListener() {
        mCancellation_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LogUtils.i("我点了组里面的"+checkedId);
            }
        });

        cancellation_tv_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

    }

    @Override
    public void initData() {
        //访问网络把本地的数据提交上去
        if ( mCancellation_rg.getCheckedRadioButtonId()==-1&& TextUtils.isEmpty(mCancellation_ed.getText())){
            UtilsToast.showToast(this, "没有选中条目或没有填写原因");
            return;
        }
        //
        //int checkedRadioButtonId = mCancellation_rg.getCheckedRadioButtonId();

        int childAtCheckedNum = 0;
        for (int i = 0; i <mCancellation_rg.getChildCount() ; i++) {
            RadioButton childAt = (RadioButton) mCancellation_rg.getChildAt(i);
            if (childAt.isChecked()){
                LogUtils.i("现在被选中的事哦"+i);
                //当前选中的数
                childAtCheckedNum = i;
                 mTextView = UtilsMyText.getTextView(childAt);
            }
        }
//        MyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {
//            @Override
//            public void callBackOrder(JSONObject jsonObject) {
//                oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
//                order_id = data.order_id;
//            }
//        });

        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("action", "passengerCancleOrder");
        hashtable.put("order_id", order_id);
        hashtable.put("order_cancle_describe", mTextView);
        CancellationOfOrder.request(this,hashtable, new NetAesCallBack() {
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


                    UtilsBroadcastReceiver.sendBroadcastReceiver(Cancellation_order.this, "timerStatus","timeRun",0);
                    finish();
                }

            }

            @Override
            public void onError(String errorString) {
                LogUtils.i("取消订单回来的json"+errorString);

            }
        });


    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }




}
