package com.wise.siyuan.net;

import org.apache.http.client.ResponseHandler;

import com.rongzhiheng.util.HTTPRequestHelper;

import android.app.ProgressDialog;
import android.os.Handler;
import android.util.Log;

/**
 * 业务逻辑 工具类 封装 请求发送 ，反馈 Thread 一个 要么wait 要么 联网
 * 
 * @author
 * 
 */
public class BusinessHelper1 implements Runnable {
	private static Handler handler;
	private static String url;
	private Thread thread;
	private static BusinessHelper1 helper;
	private boolean isSleeping;
	private static ProgressDialog pdialog;
	private static String data;

	private BusinessHelper1() {
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
	public static BusinessHelper1 getInstance(Handler _handler, String _url,
			String _data, ProgressDialog _pdialog) {
		if (helper == null) {
			helper = new BusinessHelper1();
		}
		handler = _handler;
		url = _url;
		data = _data;
		pdialog = _pdialog;
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
				HTTPRequestHelper helper = new HTTPRequestHelper(

				responseHandler);
				helper.performPostString(url, null, null, null, data);

				/*
				 * Map <String,String>a = new HashMap<String,String>();
				 * a.put("username", "gwd"); helper.performPost(url, null, null,
				 * null, a);
				 */

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
