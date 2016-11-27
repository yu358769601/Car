package com.qichen.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loveplusplus.update.AppUtils;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.ruida.R;
import com.qichen.ruida.ui.Hodometer;
import com.rongzhiheng.util.AES;
import com.rongzhiheng.util.BitmapManager;
import com.rongzhiheng.util.CircleImageView;
import com.rongzhiheng.util.Constants;
import com.rongzhiheng.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

//个人中心activity
public class PersonCenterActivity extends Activity {

	Bitmap bitmap;
	BitmapManager bmpManager;
	CircleImageView personcenter_photo;
	private TextView mTv_num;
	private TextView mText_name;
	//private SharedPreferences mSettings;
	//String login;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_center);

		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.person_photo);
		bmpManager = new BitmapManager(bitmap);
		CircleImageView.style = 1;

		personcenter_photo = (CircleImageView) findViewById(R.id.personcenter_photo);

		TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
		relativeLayout_title_titleview.setText("个人中心");

		Button relativeLayout_title_leftbutton = (Button) findViewById(R.id.relativeLayout_title_leftbutton);
		relativeLayout_title_leftbutton.setVisibility(View.VISIBLE);
		relativeLayout_title_leftbutton.setBackgroundResource(R.drawable.title_leftbutton);

		Button relativeLayout_title_rightbutton = (Button) findViewById(R.id.relativeLayout_title_rightbutton);
		relativeLayout_title_rightbutton.setVisibility(View.GONE);

		relativeLayout_title_leftbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		mText_name = (TextView) findViewById(R.id.text_name);
		mText_name.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonCenterActivity.this, PersonCenterLoginActivity.class);
				startActivityForResult(intent,0);
			}
		});



//		SharedPreferences.Editor edit = mSettings.edit();
//		edit.putString("")
		//如果是登陆状态
		if (SQUtils.isLogin(this)) {
			String passenger_telephone = SQUtils.getStrings(this, "passenger_telephone", "");
			if (!TextUtils.isEmpty(passenger_telephone)){
				//如果有电话数据
				mText_name.setText("RD_" + passenger_telephone);

				mText_name.setClickable(false);
			}

			RelativeLayout person_relativeLayout_4 = (RelativeLayout) findViewById(R.id.person_relativeLayout_4);
			//让这个显示
			person_relativeLayout_4.setVisibility(View.VISIBLE);
			//在获取网络数据
			Thread thread = new Thread(new userRegist());
			thread.start();
		}
		//点了我的钱包
		Button person_button_1 = (Button) findViewById(R.id.person_button_1);
		person_button_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SQUtils.isLogin(PersonCenterActivity.this)) {
					Intent intent = new Intent(PersonCenterActivity.this, PersonCenterQianBaoActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(PersonCenterActivity.this, PersonCenterLoginActivity.class);
					startActivity(intent);
				}
			}
		});
		Button person_button_2 = (Button) findViewById(R.id.person_button_2);
		person_button_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SQUtils.isLogin(PersonCenterActivity.this)) {
					Intent intent = new Intent(PersonCenterActivity.this, Hodometer.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(PersonCenterActivity.this, PersonCenterLoginActivity.class);
					startActivity(intent);
				}
			}
		});

		Button person_button_3 = (Button) findViewById(R.id.person_button_3);
		person_button_3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (SQUtils.isLogin(PersonCenterActivity.this)) {
					Intent intent = new Intent(PersonCenterActivity.this, PersonCenterXinXiActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(PersonCenterActivity.this, PersonCenterLoginActivity.class);
					startActivity(intent);
				}
			}
		});

		Button person_button_4 = (Button) findViewById(R.id.person_button_4);
		person_button_4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(PersonCenterActivity.this);
				builder.setTitle("系统提示");
				builder.setMessage("确定退出登录吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 退出程序
						mText_name.setText("注册/登录");
						mText_name.setClickable(true);

//						SharedPreferences settings = getSharedPreferences("settings", 0);
//						SharedPreferences.Editor editor = settings.edit();
//						editor.putString("login", "false");
//						editor.commit();
						//设定为退出登录
						SQUtils.setString(PersonCenterActivity.this, "login", "false");



						RelativeLayout person_relativeLayout_4 = (RelativeLayout) findViewById(R.id.person_relativeLayout_4);
						person_relativeLayout_4.setVisibility(View.INVISIBLE);
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});

		Button person_button_guanyu = (Button) findViewById(R.id.person_button_guanyu);
		person_button_guanyu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogUtils.i("点了关于我们");
				startActivity(new Intent(PersonCenterActivity.this, PersonCenter_About_Us.class));
			}
		});


		registerBoradcastReceiver();


//		MyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {
//			@Override
//			public void callBackOrder(com.alibaba.fastjson.JSONObject jsonObject) {
//				LogUtils.i("静态回调 个人中心 订单数据"+jsonObject);
//			}
//		});

		//显示版本号
		mTv_num = (TextView) findViewById(R.id.tv_num);

		mTv_num.setText("当前版本:"+AppUtils.getVersionName(this));
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null!=mBroadcastReceiver)
		unregisterReceiver(mBroadcastReceiver);
	}

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("registSuccess");
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("registSuccess")) {

				SharedPreferences settings = getSharedPreferences("settings", 0);
				String passenger_nickname = settings.getString("passenger_nickname", "");
				LogUtils.i("名字人员"+passenger_nickname);
				if (!TextUtils.isEmpty(passenger_nickname)){
					//如果有名字 显示名字
					mText_name.setText(passenger_nickname);

				}else{
					mText_name.setText("RD_" + settings.getString("passenger_telephone", ""));
				}
				mText_name.setClickable(false);

				SharedPreferences.Editor editor = settings.edit();
				editor.putString("login", "true");
				editor.commit();

				RelativeLayout person_relativeLayout_4 = (RelativeLayout) findViewById(R.id.person_relativeLayout_4);
				person_relativeLayout_4.setVisibility(View.VISIBLE);


				//开始网络请求
//				getUserRegist.request(context, new NetAesCallBack() {
//					@Override
//					public void onSucceed(com.alibaba.fastjson.JSONObject jsonObject) {
//						SQUtils.setString(context,"","");
////						editor.putString("passenger_id", obj_.getString("passenger_id"));
////						editor.putString("passenger_telephone", settings.getString("passenger_telephone", ""));
////						editor.putString("passenger_nickname", obj_.getString("passenger_nickname"));
////						editor.putString("passenger_photo", obj_.getString("passenger_photo"));
////						editor.putString("passenger_sex", obj_.getString("passenger_sex"));
////						editor.putString("passenger_industry", obj_.getString("passenger_industry"));
////						editor.putString("passenger_job", obj_.getString("passenger_job"));
////						editor.putString("passenger_money", obj_.getString("passenger_money"));
////						editor.commit();
////
////						if (!obj_.getString("passenger_photo").equals("")) {
////							bmpManager.loadBitmap(Constants.fangwen_image_url + obj_.getString("passenger_photo"), personcenter_photo);
////						}
////						LogUtils.i("个人中心回显网络数据"+obj);
//					}
//
//					@Override
//					public void onError(String errorString) {
//
//					}
//				});


				Thread thread = new Thread(new userRegist());
				thread.start();
			}
		}
	};



	class userRegist extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				SharedPreferences settings = getSharedPreferences("settings", 0);
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "getXinXi");
				msgObject.put("passenger_telephone", settings.getString("passenger_telephone", ""));
				// 上传的参数
				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
				System.out.println(msgObject.toString());
				// 返回的加密串
				String str = Utils.postUrlData(Constants.new_url, para);
				// 构建handler
				Message msg = userRegistHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("userRegist", str);
				msg.setData(bundle);
				userRegistHandler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}



	/**
	 * 界面handler
	 */
	Handler userRegistHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("userRegist"));
				JSONObject obj = new JSONObject(str);
				Log.i("str", str);
				// 有数据
				if (obj.getString("result").equals("1")) {
					JSONObject obj_ = new JSONObject(obj.getString("data"));
					SharedPreferences settings = getSharedPreferences("settings", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("passenger_id", obj_.getString("passenger_id"));
					editor.putString("passenger_telephone", settings.getString("passenger_telephone", ""));
					editor.putString("passenger_nickname", obj_.getString("passenger_nickname"));
					editor.putString("passenger_photo", obj_.getString("passenger_photo"));
					editor.putString("passenger_sex", obj_.getString("passenger_sex"));
					editor.putString("passenger_industry", obj_.getString("passenger_industry"));
					editor.putString("passenger_job", obj_.getString("passenger_job"));
					editor.putString("passenger_money", obj_.getString("passenger_money"));
					editor.commit();
					
					if (!obj_.getString("passenger_photo").equals("")) {
						bmpManager.loadBitmap(Constants.fangwen_image_url + obj_.getString("passenger_photo"), personcenter_photo);
					}
					LogUtils.i("个人中心回显网络数据"+obj);

				} else {
					Toast.makeText(PersonCenterActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	protected void onResume() {
		super.onResume();
		CircleImageView.style = 1;


	};

	protected void onRestart() {
		super.onRestart();
		CircleImageView.style = 1;
		String passenger_nickname = SQUtils.getStrings(this, "passenger_nickname", "");
		LogUtils.i("经过我了"+passenger_nickname);
		if (null!=mText_name){
			if (SQUtils.isLogin(this)){
				if (!TextUtils.isEmpty(passenger_nickname)){
					mText_name.setText(passenger_nickname);
				}else{
					mText_name.setText("RD_"+SQUtils.getStrings(this, "passenger_telephone",""));
				}
			}

		}

		Thread thread = new Thread(new userRegist());
		thread.start();
	};

	@Override
	protected void onStart() {
		super.onStart();
		String passenger_nickname = SQUtils.getStrings(this, "passenger_nickname", "");
		LogUtils.i("经过我了"+passenger_nickname);
		if (null!=mText_name){
			if (SQUtils.isLogin(this)){
				if (!TextUtils.isEmpty(passenger_nickname)){
					mText_name.setText(passenger_nickname);
				}else{
					mText_name.setText("RD_"+SQUtils.getStrings(this, "passenger_telephone",""));
				}
			}

		}
	}





}
