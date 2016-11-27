package com.qichen.ruida.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.qichen.Utils.LogUtils;
import com.qichen.ruida.WX.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private IWXAPI api;
	private String orderId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {
	}

	@Override
	public void onResp(BaseResp baseResp) {
		if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (baseResp.errCode == 0) {
//				Toast.makeText(this, "成功" + baseResp.errCode,
//						Toast.LENGTH_SHORT).show();

				LogUtils.i("微信支付成功的回调"+baseResp.errCode);
			} else {
//				Toast.makeText(this, "失败" + baseResp.errCode,
//						Toast.LENGTH_SHORT).show();
				LogUtils.i("微信支付失败的回调"+baseResp.errCode);

			}
			finish();
		}

	}


}