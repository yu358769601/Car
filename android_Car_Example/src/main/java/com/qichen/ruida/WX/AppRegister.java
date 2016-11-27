package com.qichen.ruida.WX;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 可以在程序入口Activity的onCreate回调函数处，
 * 或其他合适的地方将你的应用id注册到微信。
 *
 */
public class AppRegister extends BroadcastReceiver {
//
	@Override
	public void onReceive(Context context, Intent intent) {
		//注册 id
		final IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID,true);

		// 将该app注册到微信
		api.registerApp(Constants.APP_ID);
	}
}
