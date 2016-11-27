//package com.qichen.weixinzhifu;
//
//import android.util.Log;
//import android.util.Xml;
//
//import com.google.gson.Gson;
//import com.qichen.ruida.WX.Constants;
//import com.qichen.ruida.WX.Util;
//import com.tencent.mm.sdk.modelpay.PayReq;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.xmlpull.v1.XmlPullParser;
//
//import java.io.StringReader;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
///**
// * Created by Administrator on 2016/6/13 0013.
// */
//public class WeiChatPayUtils {
//
//
//    public static final String NOTIFY_URL = "http://apis.chongfa.com/api/pay/wxpayNotify";
//
//    /**
//     * 第一次请求，如果返回成功，进行第二次请求拉取支付页面
//     * 传入
//     * 无要求时传入
//     * order_no  订单号
//     * price   金额
//     */
//    public void payOrder(final IWXAPI api , final ResponseWechatDownOrder.WechatDownOrderInfo wechatDownOrderInfo){
//
//        final String entity = genProductArgs(wechatDownOrderInfo);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//
//                    byte[] buf = Util.httpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", entity);
//                    String content = new String(buf);
//                    Map<String, String> xml = decodeXml(content);
//
//                    Gson gson = new Gson();
//                    Log.e("aaa","xml:"+xml.toString());
//                    WeixinModel weixinModel = gson.fromJson(xml.toString() , WeixinModel.class);
//                    Log.e("aaa","weixinModel:"+weixinModel.getReturn_code());
//                    if(weixinModel.getReturn_code().equals("SUCCESS")){
//
//                        PayReq req = new PayReq();
//
//                        req.appId= weixinModel.getAppid();
//                        req.partnerId= weixinModel.getMch_id();
//                        req.prepayId= weixinModel.getPrepay_id();
//                        req.nonceStr= weixinModel.getNonce_str();
//                        req.timeStamp= getTimeStamp();
//                        req.packageValue= "Sign=WXPay";
//
//                        req.sign= genSign(req);
//                        api.sendReq(req);
//                        AppBus.getInstance().post(wechatDownOrderInfo);
//                    }else{
//
//                        Log.e("aaa" , "get order error");
//
//                    }
//                }catch (Exception e){
//
//                    Log.e("aaa" , "network error");
//                }
//            }
//        }).start();
//    }
//
//    /**
//     * 生成xml
//     * @param content
//     * @return
//     */
//    private Map<String,String> decodeXml(String content) {
//
//        try {
//            Map<String, String> xml = new HashMap<String, String>();
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(new StringReader(content));
//            int event = parser.getEventType();
//            while (event != XmlPullParser.END_DOCUMENT) {
//
//                String nodeName=parser.getName();
//                switch (event) {
//                    case XmlPullParser.START_DOCUMENT:
//
//                        break;
//                    case XmlPullParser.START_TAG:
//
//                        if("xml".equals(nodeName)==false){
//                            //实例化student对象
//                            xml.put(nodeName,parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                }
//                event = parser.next();
//            }
//            return xml;
//        } catch (Exception e) {
//            Log.e("aaa",e.toString());
//        }
//        return null;
//    }
//
//    /**
//     * 第一次请求
//     * 设置相关参数
//     * 如果需要传入汉字，把返回中的字符编码格式改为iso-8859-1
//     * @param
//     * @param
//     * @return
//     *
//     * String order_sn,String money
//     */
//    private String genProductArgs(ResponseWechatDownOrder.WechatDownOrderInfo wechatDownOrderInfo) {
//        StringBuffer xml = new StringBuffer();
//
//        try {
//            String	nonceStr = genNonceStr();
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//
//            packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
//            packageParams.add(new BasicNameValuePair("body", wechatDownOrderInfo.getBody()));
//            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
//            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//            packageParams.add(new BasicNameValuePair("notify_url", NOTIFY_URL));
//            packageParams.add(new BasicNameValuePair("out_trade_no",wechatDownOrderInfo.getOuttradeno()));
//            packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
//            packageParams.add(new BasicNameValuePair("total_fee", ADIWebUtils.toInt((ADIWebUtils.toDouble(wechatDownOrderInfo.getTotalfee())*100))+""));
//            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//            String sign = genPackageSign(packageParams);
//            packageParams.add(new BasicNameValuePair("sign", sign));
//            String xmlstring =toXml(packageParams);
//            return new String(xmlstring.getBytes(), "ISO-8859-1");
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 第二次请求设置相关参数
//     * 获取签名
//     * @param req
//     * @return
//     */
//    private String genSign(PayReq req) {
//        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//        packageParams.add(new BasicNameValuePair("appid", req.appId));
//        packageParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        packageParams.add(new BasicNameValuePair("package", req.packageValue));
//        packageParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        packageParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        packageParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//        return genPackageSign(packageParams);
//    }
//
//    /**
//     * 把字符串转为xml
//     * @param params
//     * @return
//     */
//    private String toXml(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<xml>");
//        for (int i = 0; i < params.size(); i++) {
//            sb.append("<"+params.get(i).getName()+">");
//            sb.append(params.get(i).getValue());
//            sb.append("</"+params.get(i).getName()+">");
//        }
//        sb.append("</xml>");
//        Log.e("aaa","toXml:"+sb.toString());
//        return sb.toString();
//    }
//
//    /**
//     * 第一次请求
//     * 把参数尾部加入支付key
//     * @param params
//     * @return
//     */
//    private String genPackageSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constants.WEIXIN_KEY);
//        Log.e("aaa","sb:"+sb.toString());
//        //String packageSign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
//        String packageSign = MD5Util.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("aaa","genPackageSign:"+packageSign);
//        return packageSign;
//    }
//
//    /**
//     * 获取时间
//     * @return
//     */
//    private String getTimeStamp() {
//        return (System.currentTimeMillis() / 1000) + "";
//    }
//
//    /**
//     * 获取随机字符串
//     * @return
//     */
//    private String genNonceStr() {
//        Random random = new Random();
//        return (random.nextInt(10000)) + "";
//    }
//
//    public interface PayListener{
//        void payFail();
//    }
//}
