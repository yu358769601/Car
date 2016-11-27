package com.qichen.ruida.ui;

import android.app.Activity;
import android.os.Bundle;

import com.qichen.ruida.R;
import com.qichen.ruida.broadcastReceivers.UtilsBroadcastReceiver;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);



        //发送了一个广播
        UtilsBroadcastReceiver.sendBroadcastReceiver(this, "sss","msgs","我发送了一个广播");

    }
}
