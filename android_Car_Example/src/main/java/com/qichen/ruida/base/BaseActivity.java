package com.qichen.ruida.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by 35876 于萌萌
 * 创建日期: 22:22 . 2016年10月02日
 * 描述:
 * <p/>
 * <p/>
 * 备注:
 */
public abstract class BaseActivity extends FragmentActivity  {
//    public String stringFilter;
//    public GetCallBackbroadcastReceiver mGetCallBackbroadcastReceiver;
//    private My_broadcastReceiver mMy_broadcastReceiver;

//    //stringFilter  msg_My
//    public void setCallBackbroadcastReceiver(String stringFilter,GetCallBackbroadcastReceiver getCallBackbroadcastReceiver){
//        this.mGetCallBackbroadcastReceiver = getCallBackbroadcastReceiver;
//        this.stringFilter = stringFilter;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayouXML());


    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (null!=mGetCallBackbroadcastReceiver){
//            LogUtils.i("广播调好频道"+"频道号是"+stringFilter);
//            IntentFilter filter = new IntentFilter(stringFilter);
//            mMy_broadcastReceiver = mGetCallBackbroadcastReceiver.getbroadcastReceiver();
//            registerReceiver(mMy_broadcastReceiver, filter);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (null!=mMy_broadcastReceiver)
//        unregisterReceiver(mMy_broadcastReceiver);

    }

    public abstract int getlayouXML();

    public abstract void initView();
    public abstract void initData();
    public abstract void initListener();


}
