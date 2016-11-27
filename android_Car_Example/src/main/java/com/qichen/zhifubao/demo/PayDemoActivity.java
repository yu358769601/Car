package com.qichen.zhifubao.demo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.R;
import com.qichen.ruida.bean.Order_form_zhifubao;
import com.qichen.ruida.view.LoadingView_09;
import com.rongzhiheng.util.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;

//支付宝支付activity
public class PayDemoActivity extends FragmentActivity implements LoadingView_09.OnRefreshListener {
	//生成订单的类型  0 是自己下订单   1 是 后台给订单
	public static final int TEST_ORDER_FORM = 0;
	public static final int OFFICIAL_ORDER_FORM = 1;
	//设定 状态 为
	//public   int STATUS = TEST_ORDER_FORM;
	public   int STATUS = OFFICIAL_ORDER_FORM;


	// 商户PID
	public static final String PARTNER = "2088521039496674";

	//public static final String PARTNER = "2088421369703278";
	//public static final String PARTNER = "2088902778511394";


	// 商户收款账号
	public static final String SELLER = "xinyanruida@163.com";

	//public static final String SELLER = "18603663028";
	//public static final String SELLER = "duobaozhifubao@fengshuwx.com";

	//public static final String SELLER = "2088521039496674";
	//用新软件 去生成 公钥私钥都有了
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE =
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANKaOcNNXRIXrWBK\n" +
					"ycB08kjak87YzrHwfOUdLKEd4zjaMrCs6JiilIJCBPwhcz0dL7jPP5zix2AL3iQo\n" +
					"aSMJptaKoGSOy61UpM3dHQEvCo46mTu1SHyb4UFMcSSBJyWO1FYfNOvTa0e1rjKp\n" +
					"FZXfGRe7aCck+gZn9rQebDdML/XdAgMBAAECgYEAiYL6RHO3WaDsK/upUGkL6lYO\n" +
					"PPmDvNvyMQejpHAHdY/DmWjNVxyRK5w/9QFCZZ0xE9L/DkllNWmKWsGoJKCgtjYC\n" +
					"ceunQPVp62Mur/+zZ1VD5K4/paeYoxz/+nrBq9tKtbZ1lzlDbMgusCtRNRyDNnxh\n" +
					"i41p9d7CxiHfwQQoHWECQQDp7ltxOnQrxhjKgxD/ZNxbS6hJ6796WSTGVrqIq8zM\n" +
					"Ym/GreCVkym6PxtJi/rHesyzYty/3uRbIAPTHdMH6otfAkEA5nh2Dg3GE+PrUJEc\n" +
					"8b6ipiO8LstRjCw0g/qXLnFBtaCOhEyS1Yrue+psQ4gSXx5MkLT7n+Ro+TZg0doH\n" +
					"NGAEQwJBAMIGTdfTKMmpy6QkI3QA3vfl/5YYL7+1slfDVYHIJNwchSMf0pec8M4m\n" +
					"S2ar8HuFccUTqY6Gu5aNLj7rjL4THjcCQDM4UFxdOUlQXQrBxWIAkRtFU9w+eXgi\n" +
					"luVB+vlqWEUqUqIadoOY5dmKRtdXVWXoUPHUFxv44k+Ig0oAH5vHPwsCQB4EACBt\n" +
					"LnZurysNc82irCMP7RuKMGBUzOW9vWKhxJcajvr7DsqbKiiWBsHGX2w6BEDrb2In\n" +
					"BDqy6DSEwUrc2hs=";



//	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANbobmJ/9qnB6sqS\n" +
//			"ig20z/WBMduCZfskDMS616LPIhmtJIJO7cxiI1eOETAj6YGzSdB3E+vg1SO4w8Yg\n" +
//			"L1BPCi1ZU1SWFIpAWev3GPhHKfWk9PaxDaTx5Ow9pQO6W6mi/xODOnYI7JmWSBKm\n" +
//			"jnyuxfoHvz3fIQkyMxHEXEyvpA59AgMBAAECgYBaLJ8cr4rlghutsj1BMQxNfE9a\n" +
//			"RuXZsi+1YJzYkMdDNNZr4wh+yPMBavglVfxF0t/3G+/8+pMUcROcj6aqsO+YgMYe\n" +
//			"3DtggAWdtFw20sQbED5jjfhQXsUTVvUa8EMFS5bcZeo+/LEOx78KZSCSZuA6elF9\n" +
//			"SEd/0e39V/TzMW9p9QJBAPFYolPnOn9UfX4b+Ad9C/uBGjbuV2bZtd4hA0guJycu\n" +
//			"mI3dEMPLqsT3OAwy5sh0rYN41lwSh8zqJhcr1hx7YEsCQQDj9Np40jZC1LCdx6f+\n" +
//			"2sNlnE2jnZnLv97DArtin0GlqKeZaNs9XTnFCdJYDAgTXo1e0tAYoh/IQ7Rs8TRH\n" +
//			"X99XAkEAlEiK49Yr4NO+jt8MUz2HQS2fN6TU2oJk9Wd/JrgEolREstTiWgomkAH8\n" +
//			"07HqjbryaNazjbps6X/ri00JrlI3ZQJARTfITv+lsk1bA83oWR6cTnJTsObeGFw+\n" +
//			"jAB8Xcn16RN5MMACMnPoYshEsq+UlC0VyEFDJvezpt/IHXxwX2vhKQJBAOzBTznO\n" +
//			"Vnf4ebmH3reEergplEj8eO0NE1I6ZLrrs0e6GQgvBWdQSaa09eGN+9Lg+RjkfaGQ\n" +
//			"5wQNdWgCwkagMGY=\n" +
//			"\n";
//	public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBANeVYjRipVveNa8aVfmn2IT7XRgkRA3BHT2F7iDKuM0RBsxgCiR1p6KhCcWzrAkOGheT19BoZExpH8OB/9Pitqku5lVmYR0QQo5j9cSgMxs6fzD6GbXc6v56pA2T8rD8Ph33HhiO7ruj77PENwoeLL10rDHu8K1UQ61KyhyJ4xcDAgMBAAECgYEAxrYzJwYmVV+lLBmiVdSYhzsKvE3xTJxMT4E71Y4GeT7QfWGo96JbOlQlpc3PLSmKc2Ea9EbE7ImZdaRGIxA+PFFbUK08qlRkUQ3JH1UsNUWzT/K1g7r7t8wuMjysYBa9GbQ6PzCZSoh/liPiDp9rdofA1EGCa2TRksH9cTAphkECQQD9ond+2TwjnSVCeoFrikqgLSYhmo7DW+7PPlhJC3dfqTGL3kNM7ucl8ZktNhpawFWyr7IIjJ7Bu/snd42XPS83AkEA2ZgSnvTGtosC3v0oBdvLVvyvYmPeGuIlKhEPQimEy3swfXjpLjtiTMpoan0LSUNKNvzSV3ZX+aQtDe3R2SRElQJBAJJ6ttpqaUIEKpWXHJFX/7s79+6mTVbkDCpiTih5V8rGcxfdGC8hNhuCyT0EJvpMY/HbaE1psPIgN29Wq04yBscCQQC1g4RVEYAKmt/kQG54pKd1gwiTHRgUeP3jn6OI37WlQLhsksaCCIux309HNdSKYxWv1StqRpCQ8YGcEv1xFC4ZAkEApFnSK0KIBHcbQQN44OzmbFKbj05Du0IePCy3FA7/rBnouDr0UGtx24D41rgZ5JqjEM7HCSvfe57QoI4698HSvA==";


	// 支付宝公钥
	//public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	private static final int SDK_PAY_FLAG = 1;
	public CallBack callBack;
	private LoadingView_09 mLoading_view;

	@Override
	public void refresh() {
		//再加载一次
		ifStatus();
	}

	public interface CallBack{
		void callBack(Bundle mData);
	}
	public void setCallBack(CallBack callBack){
		this.callBack = callBack;
	}
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				//完整的符合支付宝参数规范的订单信息1
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				//支付结果 有两个 一个是
				// 9000支付成功
				// 8000支付确认中
				String resultStatus = payResult.getResultStatus();
				Log.i("PayDemoActivity", "handleMessage: payResult"+payResult);
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		}
	};
	//前一个界面的充值金
	private String mPayment;
	private String mPayInfo;
	private Bundle mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		initView();
		getBox();
		//如果是 测试 如果是正式
		ifStatus();

	}

	private void initView() {
		mLoading_view = (LoadingView_09) findViewById(R.id.loading_view);
		mLoading_view.setRefrechListener(this);

	}

	//获取前一个界面额的充值金额
	private void getBox() {
		Bundle extras = getIntent().getExtras();
		mPayment = extras.getString("payment");



	}

	//如果是正式 如果是 测试
	public void ifStatus() {
		if (STATUS == TEST_ORDER_FORM) {
			mLoading_view.setStatue(LoadingView_09.LOADING);
			//自己下订单是需要判断的
			//非空判断 自己订单
			if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
				new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialoginterface, int i) {
								//
								finish();
							}
						}).show();
				return;
			}
			//拼接一个订单字符串 自己订单
			String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", mPayment);
			Bundle bundle = new Bundle();
			bundle.putString("String1", "测试的商品1");
			bundle.putString("String2", "该测试商品的详细描述2");
			bundle.putString("String3", mPayment);
			setData(bundle);
			if (null!=callBack)
			callBack.callBack(bundle);
			/**
			 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
			 */
			String sign = sign(orderInfo);
			try {
				/**
				 * 仅需对sign 做URL编码
				 */
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			/**
			 * 完整的符合支付宝参数规范的订单信息
			 */
			mPayInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
			mLoading_view.setStatue(LoadingView_09.GONE);

			//正式后台订单
		} else
		if (STATUS == OFFICIAL_ORDER_FORM) {
			mLoading_view.setStatue(LoadingView_09.LOADING);
			Hashtable<String, String> hashtable = new Hashtable<String, String>();
			//网络请求 TAG
			//action 告诉后台 这是一个什么请求 zhifubaoPay 支付宝交易
			hashtable.put("action", "zhifubaoPay");
			//payment 支付金额
			hashtable.put("money", mPayment);
			//哪个用户支付的
			//hashtable.put("passenger_id", SQUtils.getStrings(this, "passenger_id", ""));
			hashtable.put("passenger_id", SQUtils.getId(this));


			NetMessage.get(this)
					.sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
						@Override
						public void onSucceed(JSONObject jsonObject) {
							mLoading_view.setStatue(LoadingView_09.GONE);
							try {
								if (null != jsonObject) {
									LogUtils.i("返回来的数据是啥" + jsonObject);
									//JSONObject.parseObject("",)
									//订单信息 测试订单信息
									Order_form_zhifubao data = jsonObject.getObject("data", Order_form_zhifubao.class);
									LogUtils.i("订单对象内容是"+data);
									//支付金钱
									mPayInfo = data.pay;
									Bundle bundle = new Bundle();
									bundle.putString("String1", data.subject);
									bundle.putString("String2", data.body);
									bundle.putString("String3", mPayment);
								//	setData(bundle);
									if (null!=callBack)
										callBack.callBack(bundle);

//									String moeny = jsonObject.getString("moeny");
//									mPayment = moeny;
//									Bundle bundle = new Bundle();
//									bundle.putString("String1", "测试的商品1");
//									bundle.putString("String2", "该测试商品的详细描述2");
//									bundle.putString("String3", mPayment);
//									setData(bundle);
//									if (null!=callBack)
//										callBack.callBack(bundle);
								}
							} catch (Exception e) {
								e.printStackTrace();

							}
						}
						@Override
						public void onError(String errorString) {
							LogUtils.i(errorString);
							mLoading_view.setStatue(LoadingView_09.NO_NETWORK);
						}
					});
		}
	}
	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		if (!TextUtils.isEmpty(mPayInfo)){
			Runnable payRunnable = new Runnable() {
				@Override
				public void run() {

					// 构造PayTask 对象
					PayTask alipay = new PayTask(PayDemoActivity.this);
					LogUtils.i("支付字符串"+mPayInfo);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(mPayInfo, true);

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					//完整的符合支付宝参数规范的订单信息
					mHandler.sendMessage(msg);
				}
			};


			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();

		}else{
			ifStatus();
			UtilsToast.showToast(this, "正在获取订单信息");
		}





	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// OrderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

	public void setData(Bundle data) {
		mData = data;
	}

	public Bundle getData(){
		return mData;
	}
}
