package com.rongzhiheng.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.qichen.ruida.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 应用程序更新工具包
 * @author illdvm
 * @version 1.0
 * @created 2012-10-24
 */
public class UpdateManager {

	private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
	
    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;
    
	private static UpdateManager updateManager;
	
	private Context mContext;
	//通知对话框
	private Dialog noticeDialog;
	//下载对话框
	private Dialog downloadDialog;
	//'已经是最新' 或者 '无法获取最新版本' 的对话框
	private Dialog latestOrFailDialog;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    //查询动画
    //private ProgressDialog mProDialog;
    //进度值
    private int progress;
    //下载线程
    private Thread downLoadThread;
    //终止标记
    private boolean interceptFlag;
	//提示语
	private String updateMsg = "1、申请借款流程优化！\n2、申请时间和额度增加！\n3、统计申请信息！";
	private String apkUrl = "http://app.vivibank.com/appdownload/zhuxuele.apk";
	
	//下载包保存路径
    private String savePath = "";
	//apk保存完整路径
	private String apkFilePath = "";
	//临时下载文件路径
	private String tmpFilePath = "";
	//下载文件大小
	private String apkFileSize;
	//已下载文件大小
	private String tmpFileSize;
	
	private String curVersionName = "";
	private int curVersionCode;
	private int versionCode;
	
	private Update mUpdate;
	BusinessHelper helper_update;
	Message msge;
    private Handler mHandler = new Handler(){
    	@Override
		public void handleMessage(Message msg) {
    		switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				mProgressText.setText(tmpFileSize + "/" + apkFileSize);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			case DOWN_NOSDCARD:
				downloadDialog.dismiss();
				Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", 3000).show();
				break;
			}
    	};
    };
    
	public static UpdateManager getUpdateManager() {
		if(updateManager == null){
			updateManager = new UpdateManager();
		}
		updateManager.interceptFlag = false;
		return updateManager;
	}
	
	/**
	 * 检查App更新
	 * @param context
	 * @param isShowMsg 是否显示提示消息
	 */
	public void checkAppUpdate(Context context, final boolean isShowMsg){
		this.mContext = context;
		getCurrentVersion();
		if(isShowMsg){
/*			if(mProDialog == null)
				mProDialog = ProgressDialog.show(mContext, null, "正在检测，请稍后...", true, true);
			else if(mProDialog.isShowing() || (latestOrFailDialog!=null && latestOrFailDialog.isShowing()))
				return;*/
		}

		new Thread(){
			@Override
			public void run() {
				try {	

//					Get_Update gu=new Get_Update();
//					byte[] buf=gu.getContent(Constants.down_url + "?type=0");
//					String rs_s = new String(buf, "UTF-8");
//					JSONObject item = new JSONObject(rs_s);
//				    JSONObject jo0=item.getJSONObject("update");
//				    JSONObject jo=new JSONObject(jo0.toString()).getJSONObject("android");
//						String s_versionCode = jo.getString("versionCode");
//						String s_versionName = jo.getString("versionName");
//						String s_downloadUrl = jo.getString("downloadUrl");
//						String s_updateLog = jo.getString("updateLog");
//						
//						Update update=new Update();
//						update.setVersionCode(s_versionCode);
//						update.setVersionName(s_versionName);
//						update.setDownloadUrl(s_downloadUrl);
//						update.setUpdateLog(s_updateLog);
//						msge = new Message();
//						msge.what = 1;
//						msge.obj=update;
//						//System.out.println("msge1--------------"+msge);
//						handler.sendMessage(msge);
					
					JSONObject msgObject = new JSONObject();
					msgObject.put("action", "getVersionCode");
					msgObject.put("project", "zhuxuele");
					// 上传的参数
					String para = "para="+ AES.encrypt(Constants.key, Constants.iv,msgObject.toString());
					System.out.println(msgObject.toString());
					// 返回的加密串
					String str = Utils.postUrlData(Constants.new_url, para);
					// 构建handler
					Message msg = getVersionCodeHandler.obtainMessage();
					Bundle bundle = new Bundle();
					bundle.putString("getVersionCode", str);
					msg.setData(bundle);
					getVersionCodeHandler.sendMessage(msg);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
 
			}			
		}.start();		
	}	
	
	/**
	 * 界面handler
	 */
	Handler getVersionCodeHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("getVersionCode"));
				JSONObject obj = new JSONObject(str);
				Log.i("str", str);
				// 有数据
				if (obj.getString("result").equals("1")) {	
					
					JSONObject obj_ = new JSONObject(obj.getString("data"));
					
					versionCode = Integer.parseInt(obj_.getString("version"));
								
					Log.d("curVersionCode", curVersionCode+"");
					Log.d("versionCode", versionCode+"");

					if(curVersionCode<versionCode){
						
//						apkUrl = "http://app.vivibank.com/appdownload/zhuxuele.apk";
//						updateMsg = "1、最新版本\n2、哈哈哈\n3、大法师的法师的";
						showNoticeDialog();
						
//						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//						builder.setTitle("新版本升级");
//						builder.setMessage("您目前没有登陆！请先到用户中心进行登陆！");
//						builder.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										
//									}
//								});
//						builder.setNegativeButton("取消", null);
//						builder.show();	
//						Log.d("curVersionCode8989898989", curVersionCode+"");
					}	
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	};

	
	final Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			//进度条对话框不显示 - 检测结果也不显示
/*			if(mProDialog != null && !mProDialog.isShowing()){
				return;
			}
			mProDialog.dismiss();
			mProDialog = null;*/
			//关闭并释放释放进度条对话框
/*			if(isShowMsg && mProDialog != null){
				mProDialog.dismiss();
				mProDialog = null;
			}*/
			//显示检测结果
			if(msg.what == 1){
				mUpdate = (Update)msg.obj;
				if(mUpdate != null){
					if(curVersionCode < Integer.parseInt(mUpdate.getVersionCode()) ){
						apkUrl = mUpdate.getDownloadUrl();
						updateMsg = mUpdate.getUpdateLog();
						showNoticeDialog();
					}
				}
			}
		}
	};
	/**
	 * 显示'已经是最新'或者'无法获取版本信息'对话框
	 */
	private void showLatestOrFailDialog(int dialogType) {
		if (latestOrFailDialog != null) {
			//关闭并释放之前的对话框
			latestOrFailDialog.dismiss();
			latestOrFailDialog = null;
		}
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("系统提示");
		if (dialogType == DIALOG_TYPE_LATEST) {
			builder.setMessage("您当前已经是最新版本");
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			builder.setMessage("无法获取版本更新信息");
		}
		builder.setPositiveButton("确定", null);
		latestOrFailDialog = builder.create();
		latestOrFailDialog.show();
	}

	/**
	 * 获取当前客户端版本信息
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	curVersionName = info.versionName;
        	curVersionCode = info.versionCode;
        	System.out.println("curVersionName--------------"+curVersionName);//1.0
        	System.out.println("curVersionCode--------------"+curVersionCode);//1
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * 显示版本更新通知对话框
	 */
	private void showNoticeDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新_V"+(1+versionCode/100)+"."+versionCode/10%10+"."+versionCode%10);
		builder.setMessage(updateMsg);
		builder.setPositiveButton("立即更新", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();			
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}
	
	/**
	 * 显示下载对话框
	 */
	private void showDownloadDialog(){
		
		AlertDialog.Builder builder = new Builder(mContext);	
		builder.setTitle("正在下载新版本");
		final LayoutInflater inflater = LayoutInflater.from(mContext);	
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
		
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();
		
		downloadApk();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
//				String apkName = "zhuxuele_"+mUpdate.getVersionName()+".apk";
//				String tmpApk = "zhuxuele_"+mUpdate.getVersionName()+".tmp";
				
				String apkName = "zhuxuele_"+versionCode+".apk";
				String tmpApk = "zhuxuele_"+versionCode+".tmp";
				//判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();		
				if(storageState.equals(Environment.MEDIA_MOUNTED)){
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhuxuele/Update/";
					File file = new File(savePath);
					if(!file.exists()){
						file.mkdirs();
					}
					apkFilePath = savePath + apkName;
					tmpFilePath = savePath + tmpApk;
				}
				
				//没有挂载SD卡，无法下载文件
				if(apkFilePath == null || apkFilePath == ""){
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					return;
				}
				
				File ApkFile = new File(apkFilePath);
				
				//是否已下载更新文件
				if(ApkFile.exists()){
					downloadDialog.dismiss();
					installApk();
					return;
				}
				
				//输出临时下载文件
				File tmpFile = new File(tmpFilePath);
				FileOutputStream fos = new FileOutputStream(tmpFile);
				
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				
				//显示文件大小格式：2个小数点显示
		    	DecimalFormat df = new DecimalFormat("0.00");
		    	//进度条下面显示的总文件大小
		    	apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
				
				int count = 0;
				byte buf[] = new byte[1024];
				
				do{   		   		
		    		int numread = is.read(buf);
		    		count += numread;
		    		//进度条下面显示的当前下载文件大小
		    		tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
		    		//当前进度值
		    	    progress =(int)(((float)count / length) * 100);
		    	    //更新进度
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			//下载完成 - 将临时下载文件转成APK文件
						if(tmpFile.renameTo(ApkFile)){
							//通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
						}
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//点击取消就停止下载
				
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
			
		}
	};
	
	/**
	* 下载apk
	* @param url
	*/	
	private void downloadApk(){
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}
	
	/**
    * 安装apk
    * @param url
    */
	private void installApk(){
		File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setAction(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i);
	}
}
