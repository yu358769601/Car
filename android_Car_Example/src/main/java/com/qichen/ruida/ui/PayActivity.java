package com.qichen.ruida.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.ruida.R;
import com.qichen.ruida.bean.WeixinPayBean;
import com.qichen.ruida.request.payWeixin;
import com.qichen.ruida.view.LoadingView_09;
import com.qichen.zhifubao.demo.PayDemoActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

//支付选择activity
public class PayActivity extends Activity implements View.OnClickListener, LoadingView_09.OnRefreshListener {

    private TextView mTv_pay_weixin;
    private TextView mTv_pay_zhifubao;
    private Bundle mBundle;
    private Bundle mExtras;
    private LoadingView_09 mLoading_view;


    private int tag = 0;
    //充值金额
    public String mPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        getBox();
        initView();
    }

    /**
     * 前一个界面来的数据
     */
    private void getBox() {
        mExtras = getIntent().getExtras();

    }

    private void initView() {
        //微信支付
        mTv_pay_weixin = (TextView) findViewById(R.id.tv_pay_weixin);
        //支付宝支付
        mTv_pay_zhifubao = (TextView) findViewById(R.id.tv_pay_zhifubao);

        mTv_pay_weixin.setOnClickListener(this);
        mTv_pay_zhifubao.setOnClickListener(this);

        //等待进度条
        mLoading_view = (LoadingView_09) findViewById(R.id.loading_view);
        mLoading_view.setRefrechListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_pay_weixin:
            sendWeixin();
                tag = 1;
            break;
            case R.id.tv_pay_zhifubao:
            sendZhifuBao();
                tag = 2;
            break;
        }
    }

    private void sendZhifuBao() {
        Intent intent = new Intent(this, PayDemoActivity.class);
        mPayment = mExtras.getString("payment");

        //如果有充值金额
        if (!TextUtils.isEmpty(mPayment)){
            intent.putExtras(mExtras);
            startActivity(intent);
        }else{
            UtilsToast.showToast(this, "金额数据没有");
        }


    }

    private void sendWeixin() {
        mPayment = mExtras.getString("payment");

        //WXPayUtils wxPayUtils = new WXPayUtils(this, "http://www.weixin.qq.com/wxpay/pay.php");
        mLoading_view.setStatue(LoadingView_09.LOADING);

        //wxPayUtils.pay("美丽的","0.01","247632115");
        LogUtils.i("点了微信支付");
        payWeixin.request(this, new NetAesCallBack() {
            @Override
            public void onSucceed(JSONObject jsonObject) {

                mLoading_view.setStatue(LoadingView_09.GONE);

                LogUtils.i("微信支付回来的订单"+jsonObject);
                WeixinPayBean prePaymentBean = jsonObject.getObject("data", WeixinPayBean.class);
                IWXAPI api = WXAPIFactory.createWXAPI(PayActivity.this, prePaymentBean.appid, true);
                api.registerApp(prePaymentBean.appid);
                //等待回调
                if (!api.isWXAppInstalled()) {
                    Toast.makeText(PayActivity.this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 将该app注册到微信
                PayReq req = new PayReq();
                req.appId = prePaymentBean.appid;
                req.partnerId = prePaymentBean.partnerid;
                req.prepayId = prePaymentBean.prepayid;
                req.packageValue = prePaymentBean.packageValue;
                req.nonceStr = prePaymentBean.noncestr;
                req.timeStamp = prePaymentBean.timestamp+"";
                req.sign = prePaymentBean.sign;
                api.sendReq(req);

            }

            @Override
            public void onError(String errorString) {
                mLoading_view.setStatue(LoadingView_09.NO_NETWORK);
            }
        });
    }

    @Override
    public void refresh() {
        if (tag==1){
            sendWeixin();
        }

    }
}
