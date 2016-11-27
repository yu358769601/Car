package com.qichen.ruida.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.qichen.Utils.LogUtils;
import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.view.initAction_Bar;


//评价activity
public class AssessActivity extends BaseActivity implements View.OnClickListener{

   // private OrderForm.ResultBean result;
    private RelativeLayout reTop;
    private TextView tv1;
    private TextView tv2;
    private RelativeLayout reMid;
    private TextView tvPingfen;
    private RatingBar ratingBar1;
    private RelativeLayout reDown;
    private TextView tvMsg;
    private TextView tvComtie;
    private EditText etmsg;
    //默认是  1 选中  2 未选
    boolean status = true;
    private int pos;
    private ScrollView root;
    private initAction_Bar mRelativeLayout_title;
    private TextView mTv_order;
    private String mOrder_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBox();
        initView();
        initListener();

        //UtilsBroadcastReceiver.sendBroadcastReceiver(this, "m1","wangwang","我是主界面");



    }



    @Override
    public int getlayouXML() {
        return R.layout.activity_assess;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    public void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tvComtie.setOnClickListener(this);

        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                LogUtils.i("滑动"+"rating"+rating+"fromUser"+fromUser);

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
            imm.hideSoftInputFromWindow(etmsg.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public void initView() {
        mRelativeLayout_title = (initAction_Bar) findViewById(R.id.relativeLayout_title);
        mRelativeLayout_title.setCallBack(new initAction_Bar.Action_bar_call_back() {
            @Override
            public void getAction_barView_backbutton(Button button) {

            }

            @Override
            public void getAction_barView_title(TextView textView) {
                textView.setText("评价");
            }
        });

        mTv_order = (TextView) findViewById(R.id.tv_order);


        reTop = (RelativeLayout) findViewById(R.id.re_top);
        root = (ScrollView) findViewById(R.id.root);
        //上面两个可以变化的字
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);

        reMid = (RelativeLayout) findViewById(R.id.re_mid);

        tvPingfen = (TextView) findViewById(R.id.tv_pingfen);
        //星星
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);

        reDown = (RelativeLayout) findViewById(R.id.re_down);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvComtie = (TextView) findViewById(R.id.tv_comtie);

        //输入框
        etmsg=(EditText) findViewById(R.id.et_msg);

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                root.setFocusable(true);
//                root.setFocusableInTouchMode(true);
//                root.requestFocus();
//                StoneBreaker();


                return false;
            }
        });
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        initData();
    }

    @Override
    public void initData() {
        mTv_order.setText("订单号:"+mOrder_id);
    }

    private void StoneBreaker() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void getBox() {
        Bundle extras = getIntent().getExtras();

        mOrder_id = extras.getString("order_id");
       // pos = extras.getInt("pos");
        LogUtils.i("前一个页面的数据订单号码"+ mOrder_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                status = true;
                Drawable drawable1= getResources().getDrawable(R.drawable.selcetno);
                tv1.setCompoundDrawablesWithIntrinsicBounds(drawable1,null,null,null);
                tv1.setTextColor(getResources().getColor(R.color.red));
                Drawable drawable2= getResources().getDrawable(R.drawable.selcetoff);
                tv2.setCompoundDrawablesWithIntrinsicBounds(drawable2,null,null,null);
                tv2.setTextColor(getResources().getColor(R.color.grey));
            break;
            case R.id.tv_2:
                status = false;
                 Drawable drawable11= getResources().getDrawable(R.drawable.selcetno);
                tv2.setCompoundDrawablesWithIntrinsicBounds(drawable11,null,null,null);
                tv2.setTextColor(getResources().getColor(R.color.red));
                Drawable drawable22= getResources().getDrawable(R.drawable.selcetoff);
                tv1.setCompoundDrawablesWithIntrinsicBounds(drawable22,null,null,null);
                tv1.setTextColor(getResources().getColor(R.color.grey));
                break;
                //点击提交
            case R.id.tv_comtie:
                ischeckd();
               // loadData();
                break;
        }
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
    private void ischeckd() {
        if (TextUtils.isEmpty(etmsg.getText())){
            Toast.makeText(AssessActivity.this, "请评论", Toast.LENGTH_SHORT).show();
            return;
        }


        loadData();
    }

    /**
     *
     * 访问网络的方法
     */
    private void loadData() {

    }


//    private void setStatusandColor(TextView tv1, TextView tv2) {
//        TextView tt1 = null;
//        TextView tt2 = null;
//        if (tv1==this.tv1&&tv2==this.tv2){
//            tt1 = tv1;
//            tt2 = tv2;
//        }
//        if (tv1==this.tv2&&tv2==this.tv1){
//            tt1 = tv2;
//            tt2 = tv1;
//        }
//        Drawable drawable1= getResources().getDrawable(R.drawable.selcetno);
//        tt1.setCompoundDrawablesWithIntrinsicBounds(drawable1,null,null,null);
//        tt1.setTextColor(getResources().getColor(R.color.red_light));
//        Drawable drawable2= getResources().getDrawable(R.drawable.selcetoff);
//        tt2.setCompoundDrawablesWithIntrinsicBounds(drawable2,null,null,null);
//        tt2.setTextColor(getResources().getColor(R.color.gray_fu));
//    }


}
