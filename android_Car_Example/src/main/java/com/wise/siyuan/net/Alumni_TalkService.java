package com.wise.siyuan.net;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class Alumni_TalkService extends Service {
	private boolean threadDisable = false;
	private int count = 0;
	BusinessHelper date;
	private ProgressDialog progressDialog = null;
	private NotificationManager notificationManager;
	String s_id;
	String s_fromuserid;
	String s_fromname;
	String s_touserid;
	String s_message;
	String s_mtype;
	String s_status;
	String s_createtime;
	int i = 0;
	public static SharedPreferences sharedata;
	public String user_id, name;

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
		user_id = sharedata.getString("userid", null);
		notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);// 获取系统服务（消息管理）

		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			// 开启一个线程，每1分钟访问网络一次
			// thread.start();
			Thread thread = new Thread(new AlumniTalk());
			thread.start();
		} else {
			// showDialog("未接入互联网,请设置网络");
			// setWireless();
			threadDisable = true;
			notificationManager.cancel(0);
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("Alumni_TalkService", "Alumni_TalkService destroy");
		// Toast.makeText(Alumni_TalkService.this, "destroy", Toast.LENGTH_SHORT)
		// .show();
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
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	class AlumniTalk extends Thread implements Runnable {
		@Override
		public void run() {
			while (!threadDisable) {
				System.out.println("Alumni_TalkSevice---------------start");
				// 获取给自己的消息

				// 在状态栏显示提示
				// showNotification();
				if (Constants.alumni_talk != 0) {
					try {
						JSONObject msgObject = new JSONObject();
						msgObject.put("uid", user_id);
						msgObject.put("action", "chat_users_list_new");
						// msgObject.put("action", "chat_users_list");
						// 上传的参数
						String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
						// 返回的加密串
						String str = Utils.postUrlData(Constants.new_url, para);
						// 构建handler
						Message msg = alumniListHandler.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putString("alumni_talk", str);
						msg.what = 0;
						msg.setData(bundle);
						alumniListHandler.sendMessage(msg);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(5000);
						// Thread.sleep(20000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					Constants.alumni_talk = 1;
					try {
						JSONObject msgObject = new JSONObject();
						msgObject.put("uid", user_id);
						msgObject.put("action", "chat_users_list_new");
						// msgObject.put("action", "chat_users_list");
						// 上传的参数
						String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
						// 返回的加密串
						String str = Utils.postUrlData(Constants.new_url, para);
						// 构建handler
						Message msg = alumniListHandler.obtainMessage();
						Bundle bundle = new Bundle();
						bundle.putString("alumni_talk", str);
						msg.what = 0;
						msg.setData(bundle);
						alumniListHandler.sendMessage(msg);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	Handler alumniListHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 有数据
			case 0:
				try {
					String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("alumni_talk"));
					JSONObject obj = new JSONObject(str);
					// 有新数据
					if (obj.getString("result").equals("0")) {
						JSONArray mArray = new JSONArray(obj.getString("data"));
						for (int i = 0; i < mArray.length(); i++) {
							JSONObject obj_ = mArray.optJSONObject(i);
							System.out.println(obj_);
						}
						// 发送一个广播
						Intent mIntent = new Intent("com.service.alumni_talk");
						// mIntent.putExtra("count", count++);//
						// 这个count可以通过网络从服务器取的，这里就省略了
						mIntent.putExtra("alumni_talk_message", obj.getString("data"));
						Alumni_TalkService.this.sendBroadcast(mIntent);
					}
					// 无新消息
					if (obj.getString("result").equals("2")) {
						System.out.println("alumni_Talk----------NO_DATA");
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 1:
				break;
			case 2:
				break;
			}
		}
	};
	// 创建一个线程
	// public Thread thread = new Thread() {
	//
	// @Override
	// public void run() {
	//
	// while (!threadDisable) {
	// System.out.println("Alumni_TalkSevice---------------start");
	// // 获取给自己的消息
	//
	// // 可以在这里加个方法用来获取count的值
	//
	// // 在状态栏显示提示
	// // showNotification();
	// if (Constants.alumni_talk != 0) {
	// date = BusinessHelper
	// .getInstance(data_handler,
	// Constants.base_url
	// + "&ContentType=message_new&uid="
	// + user_id, progressDialog);
	// date.go();
	// try {
	// Thread.sleep(180000);
	// // Thread.sleep(20000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// } else {
	// ;
	// Constants.alumni_talk = 1;
	// date = BusinessHelper
	// .getInstance(data_handler,
	// Constants.base_url
	// + "&ContentType=message_new&uid="
	// + user_id, progressDialog);
	// date.go();
	// }
	//
	// }
	//
	// }
	// };
	private final Handler data_handler = new Handler() {

		@Override
		public void handleMessage(final Message msg) {
			try {
				byte[] buf = msg.getData().getByteArray("http_response");
				if (buf == null) {
					return;
				}
				String rs_s = new String(buf, "UTF-8");
				System.out.println("sevice---------------:" + rs_s);
				if (rs_s.equals("nodata")) {
					return;
				}
				rs_s = rs_s.replace("[", "");
				rs_s = rs_s.replace("]", "");

				JSONObject item = new JSONObject(rs_s);
				s_id = item.getString("id");
				s_fromuserid = item.getString("fromuserid");// 对方
				s_fromname = item.getString("fromname");// 对方名字
				s_touserid = item.getString("touserid");// 自己
				s_message = item.getString("message");
				s_mtype = item.getString("mtype");
				s_status = item.getString("status");
				s_createtime = item.getString("createtime");
				date = BusinessHelper.getInstance(ismessage, Constants.new_url + "&ContentType=message_recvok&msgid=" + s_id, progressDialog);
				date.go();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};
	private final Handler ismessage = new Handler() {

		@Override
		public void handleMessage(final Message msg) {

			byte[] buf = msg.getData().getByteArray("http_response");

			try {
				String rs_s = new String(buf, "UTF-8");
				if (rs_s.equals("OK")) {
					// 发送一个广播
					Intent mIntent = new Intent("com.service.test");
					// mIntent.putExtra("count", count++);//
					// 这个count可以通过网络从服务器取的，这里就省略了
					mIntent.putExtra("id", s_id);
					mIntent.putExtra("fromuserid", s_fromuserid);
					mIntent.putExtra("fromname", s_fromname);
					mIntent.putExtra("touserid", s_touserid);
					mIntent.putExtra("message", s_message);
					mIntent.putExtra("mtype", s_mtype);
					mIntent.putExtra("status", s_status);
					mIntent.putExtra("createtime", s_createtime);
					Alumni_TalkService.this.sendBroadcast(mIntent);
				}

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	private void showNotification() {

//		// 点击通知时转移内容
//		Intent intent = new Intent(this, MainTabActivity.class);
//		// 设置点击通知时显示内容的类
//		PendingIntent pendIntent = PendingIntent.getActivity(this, 0, intent, 0);
//		Notification notification = new Notification();
//		notification.icon = android.R.drawable.ic_delete;// 设置在状态栏显示的图标
//		notification.tickerText = "你妹你妹.......";// 设置在状态栏显示的内容
//		notification.defaults = Notification.DEFAULT_SOUND;// 默认的声音
//		// 设置通知显示的参数
//		notification.setLatestEventInfo(Alumni_TalkService.this, "你妹你妹", "Button1通知你妹", pendIntent);
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
		new AlertDialog.Builder(this).setTitle("信息").setMessage(mess).setNegativeButton("确定", new DialogInterface.OnClickListener() {
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
