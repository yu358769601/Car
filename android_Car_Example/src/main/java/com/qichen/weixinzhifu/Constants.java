package com.qichen.weixinzhifu;

import android.app.Activity;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class Constants {

	//商户ID
    public static final String APP_ID = "wx61f9402d62035e9b";

    
    public static final String MCH_ID = "1413235402";

    //应用签名 MD5值
    public static final String API_KEY = "645c401019cff3bb0727bb51d83c8e29";

    // 判断手机是否安装微信
    public static boolean isInstallWX(Activity activity) {
        IWXAPI api = WXAPIFactory.createWXAPI(activity, Constants.APP_ID, true);
        if(api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
            return true;
        }else {
            return false;
        }
    }
}
