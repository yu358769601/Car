//package com.qichen.weixinzhifu;
//
//import android.util.Log;
//
//import com.alibaba.fastjson.JSONObject;
//import com.tencent.mm.sdk.modelpay.PayReq;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.security.KeyManagementException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.UnrecoverableKeyException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
///**
// * Created by 35876 于萌萌
// * 创建日期: 11:02 . 2016年11月11日
// * 描述:
// * <p>
// * <p>
// * 备注:
// */
//
//public class Utilsweixin {
//
//    /**
//     *
//     * @Title: getOrderHttp
//     * @Description: TODO(拿到订单ID)
//     * @param  参数 @param money
//     * @param  参数 @param uid
//     * @return 返回类型  void
//     * @throws
//     */
//    private void getOrderHttp(String money,String uid){
//        String url="http://webui.430.com.cn:81/AppPay/PayWeixin.aspx";
//        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//        parameters.add(new BasicNameValuePair("uid", uid));
//        parameters.add(new BasicNameValuePair("money", money));
//        JSONObject json = HttpUtils.getHttp(url, parameters);
//        if(null != json){
//            String orderID = json.optString("orderID");
//            getWXHttp(orderID,money);
//        }else {
//            Log.e("PAY_GET","处理结果异常");
//        }
//
//    }
//
//    /**
//     *
//     * @Title: getWXHttp
//     * @Description: TODO(得到微信支付预处理的一些参数并跳转到微信支付)
//     * @param  参数 @param orderID
//     * @param  参数 @param money
//     * @return 返回类型  void
//     * @throws
//     */
//    private void getWXHttp(String orderID,String money){
//        Map<String,String> xml=new HashMap<String,String>();
//        String url="https://api.mch.weixin.qq.com/pay/unifiedorder"; //这个地址就是微信支付文档中请求的地址
//        String entity = genProductArgs(orderID,money);
//        byte[] buf = HttpUtils.httpPost(url, entity);
//        if(buf != null){
//            String content = new String(buf);
//            try {
//                Document doc = (Document)DocumentHelper.parseText(content);
//                Element root = doc.getRootElement();
//                List<Element> list = root.elements();
//                for (Element e : list) {
//                    xml.put(e.getName(), e.getText());
//                }
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//            if(xml != null){
//                PayReq req = new PayReq();
//                req.appId			= xml.get("appid");
//                req.partnerId       = xml.get("mch_id");
//                req.prepayId        = xml.get("prepay_id");
//                req.packageValue    = "Sign=WXPay";
//                req.nonceStr        = xml.get("nonce_str");
//                req.timeStamp = String.valueOf(StringUtils.genTimeStamp());
//                List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//                signParams.add(new BasicNameValuePair("appid", req.appId));
//                signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//                signParams.add(new BasicNameValuePair("package",req.packageValue));
//                signParams.add(new BasicNameValuePair("partnerid",req.partnerId));
//                signParams.add(new BasicNameValuePair("prepayid",req.prepayId));
//                signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//                req.sign =StringUtils.genAppSign(signParams);
//                m_IWxAPI.sendReq(req);
//            }
//        }
//
//    }
//
//
//    /**
//     *
//     * @Title: genProductArgs
//     * @Description: TODO(拼接参数)
//     * @param  参数 @param orderID
//     * @param  参数 @param money
//     * @param  参数 @return
//     * @return 返回类型  String
//     * @throws
//     */
//    private static String genProductArgs(String orderID,String money) {
//        StringBuffer xml = new StringBuffer();
//        try {
//            xml.append("</xml>");
//            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//            packageParams.add(new BasicNameValuePair("appid", ""));
//            packageParams.add(new BasicNameValuePair("body", ""));//商品描述，商品或支付单简要描述，必填
//            packageParams.add(new BasicNameValuePair("mch_id", ""));   //商户ID
//            packageParams.add(new BasicNameValuePair("nonce_str", StringUtils.genNonceStr()));//随机字符串，不长于32位。必填
//            packageParams.add(new BasicNameValuePair("notify_url", ""));//接收微信支付异步通知回调地址.必填
//            packageParams.add(new BasicNameValuePair("out_trade_no",));//商户系统内部的订单号,32个字符内、可包含字母,必填
//            packageParams.add(new BasicNameValuePair("spbill_create_ip",StringUtils.getLocalIpAddress(AppActivity.getContext())));//APP和网页支付提交用户端ip.必填
//            packageParams.add(new BasicNameValuePair("total_fee", (Integer.parseInt(money)*100)+""));//订单总金额，只能为整数.必填
//            packageParams.add(new BasicNameValuePair("trade_type", "APP"));//取值如下：JSAPI，NATIVE，APP，WAP,必填
//            String sign = StringUtils.genPackageSign(packageParams);
//            packageParams.add(new BasicNameValuePair("sign",sign));  //签名
//            String xmlstring =toXml(packageParams);
//            xmlstring = new String(xmlstring.getBytes("UTF-8"), "ISO-8859-1");
//            return xmlstring;
//        } catch (Exception e) {
//            Log.e("e", "genProductArgs fail, 异常: " + e.getMessage());
//            return null;
//        }
//    }
//    /**
//     *
//     * @Title: toXml
//     * @Description: TODO(转换成String格式的xml)
//     * @param  参数 @param params
//     * @param  参数 @return
//     * @return 返回类型  String
//     * @throws
//     */
//    private static String toXml(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<xml>");
//        for (int i = 0; i < params.size(); i++) {
//            sb.append("<"+params.get(i).getName()+">");
//            sb.append(params.get(i).getValue());
//            sb.append("</"+params.get(i).getName()+">");
//        }
//        sb.append("</xml>");
//        return sb.toString();
//    }
//
//    public static byte[] httpPost(String url, String entity) {
//        if (url == null || url.length() == 0) {
//            //Log.e(TAG, "httpPost, url is null");
//            return null;
//        }
//        HttpClient httpClient = getNewHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//        try {
//            httpPost.setEntity(new StringEntity(entity));
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-type", "application/json");
//            HttpResponse resp = httpClient.execute(httpPost);
//            if (resp.getStatusLine().getStatusCode() != 200) {
//                //Log.e(TAG, "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
////				return null;
//                return EntityUtils.toByteArray(resp.getEntity());
//            }
//            return EntityUtils.toByteArray(resp.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private static HttpClient getNewHttpClient() {
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            HttpParams params = new BasicHttpParams();
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//            return new DefaultHttpClient(ccm, params);
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//    }
//
//    private static class SSLSocketFactoryEx extends SSLSocketFactory {
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//            super(truststore);
//            TrustManager tm = new X509TrustManager() {
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//                public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
//                }
//                public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
//                }
//            };
//            sslContext.init(null, new TrustManager[] { tm }, null);
//        }
//        @Override
//        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
//            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
//        }
//
//        @Override
//        public Socket createSocket() throws IOException {
//            return sslContext.getSocketFactory().createSocket();
//        }
//    }
//}
