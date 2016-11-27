package com.qichen.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.ruida.R;
import com.rongzhiheng.util.AES;
import com.rongzhiheng.util.CheckOut;
import com.rongzhiheng.util.Constants;
import com.rongzhiheng.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;
//验证手机activity
public class PersonCenterLoginActivity extends Activity {

	private String yanZhengMa ="";

	private EditText zhuce_userTelephone, zhuce_useryanzhengma;

	private int second = 60;

	private Button zhuce_button_fasongyanzhengma;

	private Handler handler = new Handler();

	private Runnable runnable = new Runnable() {

		public void run() {
			second--;
			LogUtils.i("倒计时"+second);
			if (second == 0) {
				if (yanZhengMa.equals("")) {
					LogUtils.i("倒计时结束显示弹窗");
					AlertDialog.Builder builder = new AlertDialog.Builder(PersonCenterLoginActivity.this);
					builder.setTitle("系统提示");
					builder.setMessage("如果没有收到短信验证码，可以拨打400客服电话获取验证码。");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 退出程序
							dialog.dismiss();
						}
					});

					builder.show();
				}
				zhuce_button_fasongyanzhengma.setText("发送验证码");
				zhuce_button_fasongyanzhengma.setClickable(true);
				handler.removeCallbacks(runnable); // 停止Timer
				second = 60;
			} else {
				zhuce_button_fasongyanzhengma.setText("等待(" + second + ")秒");
				handler.postDelayed(this, 1000);
			}
			// postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
		}
	};

	private Handler quitHandler = new Handler();

	private Runnable quitRunnable = new Runnable() {

		public void run() {

			Intent mIntent = new Intent("registSuccess");
			sendBroadcast(mIntent);

			quitHandler.removeCallbacks(quitRunnable); // 停止Timer
			finish();
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_center_login);

		TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
		relativeLayout_title_titleview.setText("验证手机");

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

		zhuce_userTelephone = (EditText) findViewById(R.id.zhuce_userTelephone);
		zhuce_useryanzhengma = (EditText) findViewById(R.id.zhuce_useryanzhengma);

		zhuce_button_fasongyanzhengma = (Button) findViewById(R.id.zhuce_button_fasongyanzhengma);
		zhuce_button_fasongyanzhengma.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (CheckOut.isEmpty(zhuce_userTelephone)) {
					Toast.makeText(PersonCenterLoginActivity.this, "手机号不能为空~", Toast.LENGTH_LONG).show();
				} else if (!CheckOut.isMobileNO(zhuce_userTelephone.getText().toString())) {
					Toast.makeText(PersonCenterLoginActivity.this, "手机号格式不正确~", Toast.LENGTH_LONG).show();
				} else {
					yanZhengMa = "";
					Toast.makeText(PersonCenterLoginActivity.this, "正在获取验证码~", Toast.LENGTH_LONG).show();
					Thread thread = new Thread(new getYanZhengMa());
					thread.start();
					zhuce_button_fasongyanzhengma.setClickable(false);
					handler.postDelayed(runnable, 1000); // 开始Timer
				}

			}
		});

		Button regist_queding = (Button) findViewById(R.id.regist_queding);
		regist_queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (CheckOut.isEmpty(zhuce_userTelephone)) {
					Toast.makeText(PersonCenterLoginActivity.this, "手机号码不能为空~", Toast.LENGTH_LONG).show();
				} else if (!CheckOut.isMobileNO(zhuce_userTelephone.getText().toString())) {
					Toast.makeText(PersonCenterLoginActivity.this, "手机号码格式不正确~", Toast.LENGTH_LONG).show();
				} else if (CheckOut.isEmpty(zhuce_useryanzhengma)) {
					Toast.makeText(PersonCenterLoginActivity.this, "验证码不能为空~", Toast.LENGTH_LONG).show();
				} else
//				if (!zhuce_useryanzhengma.getText().toString().endsWith(yanZhengMa)) {
//					Toast.makeText(PersonCenterLoginActivity.this, "验证码错误~", Toast.LENGTH_LONG).show();
//				}
				if (!zhuce_useryanzhengma.getText().toString().trim().equals(yanZhengMa)) {
					Toast.makeText(PersonCenterLoginActivity.this, "验证码错误~", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(PersonCenterLoginActivity.this, "正在提交信息，请稍候~", Toast.LENGTH_LONG).show();
					Thread thread = new Thread(new userRegist());
					thread.start();
				}
			}
		});

	}

	class getYanZhengMa extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "registSms");
				msgObject.put("passenger_telephone", zhuce_userTelephone.getText().toString());
				// 上传的参数
				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
				System.out.println(msgObject.toString());
				LogUtils.i("加密_解密_加密之前的网络数据"+msgObject.toString());
				// 返回的加密串
				String str = Utils.postUrlData(Constants.new_url, para);
				LogUtils.i("加密_解密_加密之后的字符串"+str);
				// 构建handler
				Message msg = getYanZhengMaHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("getYanZhengMa", str);
				msg.setData(bundle);
				getYanZhengMaHandler.sendMessage(msg);
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
	Handler getYanZhengMaHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("getYanZhengMa"));
				JSONObject obj = new JSONObject(str);
			//	Log.i("str", str);
				LogUtils.i("加密_解密_解密之后的数据网络数据"+str);
				// 有数据
				if (obj.getString("result").equals("1")) {
					yanZhengMa = new JSONObject(obj.getString("data")).getString("code");
					zhuce_userTelephone.setEnabled(false);
					Toast.makeText(PersonCenterLoginActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(PersonCenterLoginActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	class userRegist extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "userRegist");
				msgObject.put("passenger_telephone", zhuce_userTelephone.getText().toString());
				msgObject.put("passenger_from", "Android");
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null!=zhuce_button_fasongyanzhengma && null!=handler && null!=runnable){
			//zhuce_button_fasongyanzhengma.setText("发送验证码");
			//zhuce_button_fasongyanzhengma.setClickable(true);
			handler.removeCallbacks(runnable); // 停止Timer
			//second = 60;
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


//					SharedPreferences settings = getSharedPreferences("settings", 0);
//					SharedPreferences.Editor editor = settings.edit();
//					editor.putString("passenger_id", obj_.getString("passenger_id"));
//					editor.putString("passenger_telephone", zhuce_userTelephone.getText().toString());
//					editor.putString("passenger_nickname", obj_.getString("passenger_nickname"));
//					editor.putString("passenger_photo", obj_.getString("passenger_photo"));
//					editor.putString("passenger_sex", obj_.getString("passenger_sex"));
//					editor.putString("passenger_industry", obj_.getString("passenger_industry"));
//					editor.putString("passenger_job", obj_.getString("passenger_job"));
//					editor.putString("passenger_money", obj_.getString("passenger_money"));
//					editor.commit();
					//设定身份认证
					SQUtils.setId(PersonCenterLoginActivity.this, obj_.getString("passenger_id"));
					SQUtils.setLogin(PersonCenterLoginActivity.this);
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_id", obj_.getString("passenger_id"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_telephone", zhuce_userTelephone.getText().toString());
					//LogUtils.i("身份"+obj_.getString("passenger_nickname"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_nickname", obj_.getString("passenger_nickname"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_photo", obj_.getString("passenger_photo"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_sex", obj_.getString("passenger_sex"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_industry", obj_.getString("passenger_industry"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_job", obj_.getString("passenger_job"));
					SQUtils.setString(PersonCenterLoginActivity.this, "passenger_money", obj_.getString("passenger_money"));

					UtilsToast.showToast(PersonCenterLoginActivity.this, "验证成功！");
					//Toast.makeText(PersonCenterLoginActivity.this, "验证成功！", Toast.LENGTH_LONG).show();
					quitHandler.postDelayed(quitRunnable, 2000); // 开始Timer

				} else {
					Toast.makeText(PersonCenterLoginActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
