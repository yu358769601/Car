package com.wise.siyuan.net;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rongzhiheng.util.AES;
import com.rongzhiheng.util.Constants;
import com.rongzhiheng.util.Utils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class ChatService extends Service{
	Bundle bundle;
	private boolean threadDisable = false;
	private int count = 0;
	BusinessHelper date;
	private ProgressDialog progressDialog = null;
	private NotificationManager notificationManager;
	String uid,student_id,para,req0;
	String s_fromuserid;
	String s_fromname;
	String s_touserid;
	String s_message;
	String s_mtype;
	String s_status;
	String s_createtime;
	int i = 0;
	public static SharedPreferences sharedata ;
	public String user_id,name;
	//AES加密
	AES aes;
	JSONArray jarr,msgid;
	
	 Boolean isInternetPresent = false;
	 ConnectionDetector cd;
	 
	 
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	protected void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
		super.dump(fd, writer, args);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sharedata = getSharedPreferences("data", 0);  
		uid = sharedata.getString("userid", null);

		
		notificationManager = (NotificationManager) this
				.getSystemService(NOTIFICATION_SERVICE);// 获取系统服务（消息管理）
		
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if(isInternetPresent)
		{
			// 开启一个线程，每3s访问网络一次
			thread.start();
			
		}
		else
		{
			//showDialog("未接入互联网,请设置网络");
			//setWireless();
			threadDisable = true;

			notificationManager.cancel(0);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("service", "service destroy");
		/*Toast.makeText(ChatService.this, "ChatService_destroy", Toast.LENGTH_SHORT)
				.show();*/
		System.out.println("ChatService---------------destroy");
		threadDisable = true;

		notificationManager.cancel(0);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		//接收activity数据
		student_id=intent.getStringExtra("student_id");
		//System.out.println("sevice_student_id---------------"+student_id);
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	// 创建一个线程
	public Thread thread = new Thread() {

		@Override
		public void run() {

			while (!threadDisable) {
			System.out.println("ChatService---------------start");
				// 在状态栏显示提示
				// showNotification();
				if (Constants.i != 0) {
					try {
					//接收未读消息
					JSONObject	item0 = new JSONObject();
					item0.put("action", "chat_new");
					item0.put("uid", uid);
					item0.put("student_id", student_id);
					//item0.put("uid", student_id);
					//item0.put("student_id", uid);
					para =  "para=" + aes.encrypt(Constants.key,Constants.iv,item0.toString());
					String req0=Utils.postUrlData(Constants.new_url, para);
					req0=aes.decrypt(Constants.key,Constants.iv,req0.toString());
					JSONObject	jsonobj = new JSONObject(req0);
					jarr=jsonobj.getJSONArray("data");
					// 发送一个广播 把jarr传给activity 做存库处理
					Intent mIntent = new Intent("com.service.chat");
					mIntent.putExtra("jarr", jarr.toString());
					ChatService.this.sendBroadcast(mIntent);
					
					System.out.println("ChatService_para--------------"+para);
					System.out.println("ChatService_req0--------------"+jarr);
										
					msgid= new JSONArray();
					for (int a = 0; a < jarr.length(); a++) {
						JSONObject item = jarr.getJSONObject(a);
						
						String s_id = item.getString("id");
						msgid.put(a,s_id);
						
					}
					if(!jarr.toString().equals("[]"))
					{
						//发送已收回执
						JSONObject	item1 = new JSONObject();
						item1.put("action", "chat_reply");
						item1.put("uid", uid);
						item1.put("student_id", student_id);
						//item1.put("uid", student_id);
						//item1.put("student_id", uid);
						item1.put("msgid", msgid);
						para =  "para=" + aes.encrypt(Constants.key,Constants.iv,item1.toString());
						String req1=Utils.postUrlData(Constants.new_url, para);
						req1=aes.decrypt(Constants.key,Constants.iv,req1.toString());
						System.out.println("ChatService_para1--------------"+para);
						System.out.println("ChatService_req1--------------"+req1);
					}
					

						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {;
					Constants.i = 1;
					
				}


			}

		}
	};


	private void showNotification() {

		// 点击通知时转移内容
//		Intent intent = new Intent(this, MainTabActivity.class);
//		// 设置点击通知时显示内容的类
//		PendingIntent pendIntent = PendingIntent
//				.getActivity(this, 0, intent, 0);
//		Notification notification = new Notification();
//		notification.icon = android.R.drawable.ic_delete;// 设置在状态栏显示的图标
//		notification.tickerText = "你妹你妹.......";// 设置在状态栏显示的内容
//		notification.defaults = Notification.DEFAULT_SOUND;// 默认的声音
//		// 设置通知显示的参数
//		notification.setLatestEventInfo(ChatService.this, "你妹你妹",
//				"Button1通知你妹", pendIntent);
//
//		notificationManager.notify(0, notification);// 执行通知.
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	private void showDialog(String mess) {
		new AlertDialog.Builder(this).setTitle("信息").setMessage(mess)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						int currentVersion = android.os.Build.VERSION.SDK_INT;
						if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
							Intent startMain = new Intent(Intent.ACTION_MAIN);
							startMain.addCategory(Intent.CATEGORY_HOME);
							startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(startMain);
							System.exit(0);
						} else {// android2.1
							ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
							am.restartPackage(getPackageName());
						}
					}
				}).show();
	}
}
