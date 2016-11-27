package com.qichen.ruida.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	

	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.entry);
//        PrePaymentBean prePaymentBean = (PrePaymentBean) getIntent().getSerializableExtra("payment");
//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//    	api = WXAPIFactory.createWXAPI(this, prePaymentBean.appid, false);
////		api.registerApp(prePaymentBean.appid);
//		//等待回调
//		api.handleIntent(getIntent(), this);
//		if (!api.isWXAppInstalled()) {
//			Toast.makeText(this, "您还未安装微信客户端",
//					Toast.LENGTH_SHORT).show();
//			return;
//		}
//		// 将该app注册到微信
//		PayReq req = new PayReq();
//
//		req.appId = prePaymentBean.appid;
//		req.partnerId = prePaymentBean.partnerid;
//		req.prepayId = prePaymentBean.prepayid;
//		req.packageValue = prePaymentBean.packageValue;
//		req.nonceStr = prePaymentBean.noncestr;
//		req.timeStamp = prePaymentBean.timestamp;
//		req.sign = prePaymentBean.sign;
//		api.sendReq(req);
    }


	@Override
	public void onReq(BaseReq resp) {
		int result = 0;

		switch (resp.getType()) {
			case BaseResp.ErrCode.ERR_OK:
				//result = R.string.errcode_success;
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				//result = R.string.errcode_cancel;
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				//result = R.string.errcode_deny;
				break;
			default:
				//result = R.string.errcode_unknown;
				break;
		}

		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		finish();
	}

	@Override
	public void onResp(BaseResp baseResp) {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
		api.handleIntent(intent, this);
	}

//	private void goToGetMsg() {
//		Intent intent = new Intent(this, GetFromWXActivity.class);
//		intent.putExtras(getIntent());
//		startActivity(intent);
//		finish();
//	}

//	private void goToShowMsg(ShowMessageFromWX.Req showReq) {
//		WXMediaMessage wxMsg = showReq.message;
//		WXAppExtendObject obj = (WXAppExtendObject) wxMsg.mediaObject;
//
//		StringBuffer msg = new StringBuffer(); // 组织一个待显示的消息内容
//		msg.append("description: ");
//		msg.append(wxMsg.description);
//		msg.append("\n");
//		msg.append("extInfo: ");
//		msg.append(obj.extInfo);
//		msg.append("\n");
//		msg.append("filePath: ");
//		msg.append(obj.filePath);
//
//		Intent intent = new Intent(this, ShowFromWXActivity.class);
//		intent.putExtra(Constants.ShowMsgActivity.STitle, wxMsg.title);
//		intent.putExtra(Constants.ShowMsgActivity.SMessage, msg.toString());
//		intent.putExtra(Constants.ShowMsgActivity.BAThumbData, wxMsg.thumbData);
//		startActivity(intent);
//		finish();
//	}
}
