package com.example.notifications.mvp.v;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.notifications.CustomActivity;
import com.example.notifications.ProgressAcitivty;
import com.example.notifications.R;
import com.example.notifications.base.BaseActivity;
import com.example.notifications.mvp.p.Main_p;
/*
 * 通知栏应用
 */
public class MainActivity extends BaseActivity implements OnClickListener{
	private Button btn_show;
	private Button btn_bigstyle_show;
	private Button btn_show_progress;
	private Button btn_show_cz;
	private Button btn_clear;
	private Button btn_clear_all;
	private Button btn_show_custom;
	/** 点击跳转到指定的界面 */
	private Button btn_show_intent_act;
	/** 点击打开指定的界apk */
	private Button btn_show_intent_apk;
	/** Notification构造器 */
	NotificationCompat.Builder mBuilder;
	/** Notification的ID */
	public int notifyId = 100;
	private Main_p mMain_p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
		initMVP();
		mMain_p.initNotify();
	}
	//初始化MVP
	private void initMVP() {
		mMain_p = new Main_p(this);

	}

	private void initView() {
		btn_show = (Button)findViewById(R.id.btn_show);
		btn_bigstyle_show = (Button)findViewById(R.id.btn_bigstyle_show);
		btn_show_progress = (Button)findViewById(R.id.btn_show_progress);
		btn_show_cz = (Button)findViewById(R.id.btn_show_cz);
		btn_clear = (Button)findViewById(R.id.btn_clear);
		btn_clear_all = (Button)findViewById(R.id.btn_clear_all);
		btn_show_custom = (Button)findViewById(R.id.btn_show_custom);
		btn_show_intent_act = (Button)findViewById(R.id.btn_show_intent_act);
		btn_show_intent_apk = (Button)findViewById(R.id.btn_show_intent_apk);
		btn_show.setOnClickListener(this);
		btn_bigstyle_show.setOnClickListener(this);
		btn_show_progress.setOnClickListener(this);
		btn_show_cz.setOnClickListener(this);
		btn_clear.setOnClickListener(this);
		btn_clear_all.setOnClickListener(this);
		btn_show_custom.setOnClickListener(this);
		btn_show_intent_act.setOnClickListener(this);
		btn_show_intent_apk.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_show:
			mMain_p.showNotify();
			break;
		case R.id.btn_bigstyle_show:
			mMain_p.showBigStyleNotify();
			break;
		case R.id.btn_show_cz:
			mMain_p.showCzNotify();
			break;
		case R.id.btn_clear:
			clearNotify(notifyId);
			break;
		case R.id.btn_clear_all:
			clearAllNotify();
			break;
		case R.id.btn_show_intent_act:
			mMain_p.showIntentActivityNotify();
			break;
		case R.id.btn_show_intent_apk:
			mMain_p.showIntentApkNotify();
			break;
		case R.id.btn_show_progress:
			startActivity(new Intent(getApplicationContext(), ProgressAcitivty.class));
			break;
		case R.id.btn_show_custom:
			startActivity(new Intent(getApplicationContext(), CustomActivity.class));
			break;
		default:
			break;
		}
	}

}
