package com.qichen.ruida.WX;

import android.app.Activity;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class Constants {

	//APP_ID
    public static final String APP_ID = "wx61f9402d62035e9b";

    //商户号
    public static final String MCH_ID = "1413235402";

    //应用签名 MD5值
   // public static final String API_KEY = "16f44fa1c5d8893935748bf7809cacc6";
    public static final String API_KEY = "645c401019cff3bb0727bb51d83c8e29";
   // b98f07da9b358919f9158599388394c0
    // public static final String API_KEY = "b98f07da9b35891919f9158599388394c0";

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
