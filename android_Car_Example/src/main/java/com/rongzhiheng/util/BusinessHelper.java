package com.rongzhiheng.util;

import org.apache.http.client.ResponseHandler;


import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;



/**
 * 业务逻辑 工具类 封装 请求发送 ，反馈 Thread 一个 要么wait 要么 联网
 * 
 * @author
 * 
 */
public class BusinessHelper implements Runnable {
	private static Handler handler;
	private static String url,spdialog;
	private Thread thread;
	private static BusinessHelper helper;
	private boolean isSleeping;
	private static ProgressDialog pdialog;

	private BusinessHelper() {
		thread = new Thread(this);
		isSleeping = true;
		thread.start();
	}

	/**
	 * 单例
	 * 
	 * @param _handler
	 * @param _url
	 * @return
	 */
	
	public static BusinessHelper getInstance(Handler _handler, String _url,
			ProgressDialog _pdialog) {
		if (helper == null) {
			helper = new BusinessHelper();
		}
		handler = _handler;
		url = _url;
		pdialog = _pdialog;
		return helper;
	}
	
	public static BusinessHelper getInstance(Handler _handler, String _url,
			String _pdialog) {
		if (helper == null) {
			helper = new BusinessHelper();
		}
		handler = _handler;
		url = _url;
		spdialog = _pdialog;
		return helper;
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					if (isSleeping) {
						Log.d("helper===", "wait...");
						wait();
					}
				}
				Log.d("helper===", "go!!!");
				// TODO Auto-generated method stub
				final ResponseHandler<String> responseHandler = HTTPRequestHelper
						.getResponseHandlerInstance(handler);
			 
				// 访问工具类
				HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
				if(spdialog=="1")
				{
					helper.performPost(url, null, null, null, null);
					
				}
				else
				{
					helper.performGet(url, null, null, null);
				}
				
				isSleeping = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public synchronized void go() {
		isSleeping = false;
		notify();
	}

	public synchronized void cancel() {
		isSleeping = true;
		notify();
	}

}
