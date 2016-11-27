package com.qichen.ruida.WX;//package com.jac.qpsc.WX;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.jac.qpsc.JacApplication;
//import com.jac.qpsc.R;
//import com.jac.qpsc.entity.PrePaymentBean;
//import com.tencent.mm.sdk.modelpay.PayReq;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//
///**
// * Created by Administrator 于萌萌
// * 创建日期: 16:57 . 2016年06月30日
// * 描述:微信支付  自己下单  自己 获取 信息  自己调用支付接口
// * <p>
// * <p>
// * 备注:
// */
//public class WXPayMoney extends Activity {
//    private IWXAPI msgApi = null;
//    private Context mContext;
//    private PayReq req;
//    //网络数据 对象
//    PrePaymentBean prePaymentBean;
//
//
//    //    //app注册的
////    public static final String APP_ID = "wx6c59695496930a2f";
////    //public static final String SECRET = "656739c79f8661e422f31e12de748809";
////    //秘钥
////    public static final String KEY = "xiwangjiuaicheshengyirijindoujin";
////    //商家 ID
////    public static final String MCH_ID = "1354475502";
////    // 微信支付
////    private static final int WEIXINPAY = 1;
//    private Button bt_WXpay;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxpay);
//        bt_WXpay = (Button) findViewById(R.id.bt_WXpay);
//        req = new PayReq();
//        prePaymentBean = (PrePaymentBean) getIntent().getSerializableExtra("prePaymentBean");
//        initListener();
//    }
//
//    private void initListener() {
//        bt_WXpay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                //最后直接发送请求 带着所有数据
//                payByWechat();
//
//            }
//
//
//        });
//    }
//
//    public void payByWechat() {
//        genPayReq();
//        msgApi = WXAPIFactory.createWXAPI(JacApplication.getContext(), WeChatPayModel.APP_ID, false);
//        msgApi.registerApp(req.appId);
//        msgApi.sendReq(req);
//    }
//
//    private void genPayReq() {
//
//        req.appId = WeChatPayModel.APP_ID;
//        req.partnerId = WeChatPayModel.MCH_ID;
//        req.prepayId = prePaymentBean.prepayid;
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = prePaymentBean.noncestr;
//        req.timeStamp = prePaymentBean.timestamp;
//        req.sign = prePaymentBean.sign;
//
//    }
//
////    private String genPayReqmy() {
////
////
////        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
////        signParams.add(new BasicNameValuePair("appid", req.appId));
////        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
////        signParams.add(new BasicNameValuePair("package", req.packageValue));
////        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
////        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
////        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
////
////        return genAppSign(signParams);
////    }
//
//
//
////    private String genAppSign(List<NameValuePair> params) {
////        StringBuilder sb = new StringBuilder();
////
////        for (int i = 0; i < params.size(); i++) {
////            sb.append(params.get(i).getName());
////            sb.append('=');
////            sb.append(params.get(i).getValue());
////            sb.append('&');
////        }
////        sb.append("key=");
////        sb.append(WeChatPayModel.KEY);
////
////        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
////
////        return appSign;
////    }
//
//}
//
//
//
