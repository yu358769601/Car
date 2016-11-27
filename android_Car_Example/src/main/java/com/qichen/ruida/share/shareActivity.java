package com.qichen.ruida.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qichen.Utils.LogUtils;
import com.qichen.ruida.R;
import com.qichen.ruida.WX.Constants;
import com.qichen.ruida.WX.Util;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class shareActivity extends Activity implements View.OnClickListener{

    private TextView tv_send_pengyouquan;
    private TextView tv_send_pengyou;
    private LinearLayout pop_layout;
    private IWXAPI api;
    private String total;
    private String tagName;
    private String tagId;
    private String seller_id;
    private String short_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        //注册微信
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID,true);
        //注册 app 到微信
        api.registerApp(Constants.APP_ID);
        getBox();
        initView();
        initListener();
    }

    private void getBox() {
//        bundle.putString("total",total);
//        bundle.putString("tagName",tagName);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            total = extras.getString("total");
            tagName = extras.getString("tagName");
            tagId = extras.getString("tagId");

            //商家列表分享
            seller_id = extras.getString("seller_id");
            short_name = extras.getString("short_name");

        }



    }

    private void initListener() {
        tv_send_pengyou.setOnClickListener(this);
        tv_send_pengyouquan.setOnClickListener(this);
        pop_layout.setOnClickListener(this);
    }

    private void initView() {
        tv_send_pengyou = (TextView) findViewById(R.id.tv_send_pengyou);
        tv_send_pengyouquan = (TextView) findViewById(R.id.tv_send_pengyouquan);
        pop_layout = (LinearLayout) findViewById(R.id.pop_layout);

    }
    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_layout:
                LogUtils.i("点的是布局");

            break;
            case R.id.tv_send_pengyou:
                LogUtils.i("点的是发送到朋友");
                sendpengyou();
                //sendpengyoutu();

            break;
            case R.id.tv_send_pengyouquan:
                LogUtils.i("点的是发送到朋友圈");
                sendpengyouquan();
                //sendpengyouquantest();
            break;

        }
        finish();
    }

    private void sendpengyou() {
         //初始化一个WXTextObject对象
       // String text = "www.9ac.com.cn/download.html";
        String text = "www.9ac.com.cn/download.html";
        if (!TextUtils.isEmpty(total)&&!TextUtils.isEmpty(tagName)){
           // text = "www.baidu.com";
            text = "http://www.9ac.com.cn/share.php?"+"tagId"+"="+tagId;
        }
        if (!TextUtils.isEmpty(seller_id)&&!TextUtils.isEmpty(short_name)){
           //msg.title = "真的接地气还好用的汽配平台"+"商家:"+"\t"+short_name+"详情";
            text = "http://seller.beta.9ac.com.cn/share/share_seller.php?"+"seller"+"="+seller_id+"\n"+"真的接地气还好用的汽配平台";
            //http://seller.beta.9ac.com.cn/share/share_seller.php?seller=3

        }
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        WXMediaMessage msg = new WXMediaMessage(textObj);
        msg.mediaObject = textObj;
        msg.description = text;
        msg.title = "来自就爱车精心为您推荐";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;

         api.sendReq(req);
         LogUtils.i("发送请求的返回值是"+api.sendReq(req));

                // 初始化一个WXTextObject对象
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = "输入框里面的内容";
//
//        // 用WXTextObject对象初始化一个WXMediaMessage对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        // 发送文本类型的消息时，title字段不起作用
//        // msg.title = "Will be ignored";
//        msg.description = "输入框里面的内容";
//
//        // 构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());  // 毫秒transaction字段用于唯一标识一个请求
//        req.message = msg;
//
//
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//
//         //调用api接口发送数据到微信
//          api.sendReq(req);
//        LogUtils.i("发送请求的返回值是"+api.sendReq(req));
    }
    private void sendpengyoutu() {
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.code);
//        //初始化一个WXTextObject对象
//        WXImageObject imageObj = new WXImageObject(bitmap);
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = imageObj;
//        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 30, 30, true);
//        bitmap.recycle();
//        msg.thumbData = Util.bmpToByteArray(thumbBmp,true);
//
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("img");
//       // req.transaction = String.valueOf(System.currentTimeMillis());
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
//         api.sendReq(req);
//         LogUtils.i("发送请求的返回值是"+api.sendReq(req));

                // 初始化一个WXTextObject对象
//        WXTextObject textObj = new WXTextObject();
//        textObj.text = "输入框里面的内容";
//
//        // 用WXTextObject对象初始化一个WXMediaMessage对象
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = textObj;
//        // 发送文本类型的消息时，title字段不起作用
//        // msg.title = "Will be ignored";
//        msg.description = "输入框里面的内容";
//
//        // 构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(System.currentTimeMillis());  // 毫秒transaction字段用于唯一标识一个请求
//        req.message = msg;
//
//
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//
//         //调用api接口发送数据到微信
//          api.sendReq(req);
//        LogUtils.i("发送请求的返回值是"+api.sendReq(req));
    }

    private void sendpengyouquan() {
        WXWebpageObject webpage = new WXWebpageObject();
      //  http://seller.9ac.com.cn/Share/index/参数名称/参数值/参数名称/参数值/参数名称/参数值
        webpage.webpageUrl = "www.9ac.com.cn/download.html";

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (total!=null){
            LogUtils.i("目前的商家个数total"+total);
        }
        msg.title = "真的接地气还好用的汽配平台"+"欢迎大家使用";
        if (!TextUtils.isEmpty(total)&&!TextUtils.isEmpty(tagName)){
            msg.title = "真的接地气还好用的汽配平台"+"车型:"+tagName+"共有"+total+"个精品商家";
            webpage.webpageUrl = "http://www.9ac.com.cn/share.php?"+"tagId"+"="+tagId;
        }
        if (!TextUtils.isEmpty(seller_id)&&!TextUtils.isEmpty(short_name)){
            msg.title = "真的接地气还好用的汽配平台"+"商家:"+"\t"+short_name+"的详细情况";
            webpage.webpageUrl = "http://seller.beta.9ac.com.cn/share/share_seller.php?"+"seller"+"="+seller_id;
            //http://seller.beta.9ac.com.cn/share/share_seller.php?seller=3

        }
        if (total==null||"0".equals(total)||"null".equals(total)){
            if (!TextUtils.isEmpty(tagName)){
                msg.title = "真的接地气还好用的汽配平台"+"\t"+"车型:"+tagName+"等着你们入驻";
               webpage.webpageUrl = "http://www.9ac.com.cn/share.php?"+"tagId"+"="+tagId;
            }
        }


        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = "不下载等啥呢";
        //应该是  预览图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        msg.thumbData = Util.bmpToByteArray(bitmap,true);

        //  构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
        LogUtils.i("发送请求的返回值是"+api.sendReq(req));
    }
    private void sendpengyouquantest() {
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "www.9ac.com.cn/download.html";
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
//        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.maike);
//        msg.thumbData = Util.bmpToByteArray(thumb, true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
//        LogUtils.i("发送请求的返回值是"+api.sendReq(req));
//        finish();

//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "www.9ac.com.cn/download.html";
//        // 用WXTextObject对象初始化一个WXMediaMessage对象
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        LogUtils.i("目前的商家个数total"+total);
//        if (!TextUtils.isEmpty(total)&&!TextUtils.isEmpty(tagName)){
//            msg.title = "真的接地气还好用的汽配平台"+"车型:"+tagName+"共有"+total+"个精品商家";
//
//        }
//        if ("0".equals(total)||"null".equals(total)||total==null){
//            msg.title = "真的接地气还好用的汽配平台"+"\t"+"车型:"+tagName+"等着你们入驻";
//        }
//
//
//        // 发送文本类型的消息时，title字段不起作用
//        // msg.title = "Will be ignored";
//        msg.description = "不下载等啥呢";
//        //应该是  预览图
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.logo1);
//        msg.thumbData = com.jac.qpsc.WX.Util.bmpToByteArray(bitmap,true);
//
//        //  构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        //transaction字段用于唯一标识一个请求
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
//        LogUtils.i("发送请求的返回值是"+api.sendReq(req));
    }



    /**
     *
     * 自己写的工具类  就是 毫秒数 加上 请求的  字符串 为了就是 唯一
     * @param type
     * @return
     */
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
