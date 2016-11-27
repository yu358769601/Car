package com.qichen.ruida.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnCameraChangeListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import com.amap.api.services.route.RouteSearch.FromAndTo;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.loveplusplus.update.AppUtils;
import com.loveplusplus.update.CallBack;
import com.loveplusplus.update.UpdateDialog;
import com.qichen.Constantss.Constantss;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.NetworkUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.person.PersonCenterActivity;
import com.qichen.person.PersonCenterLoginActivity;
import com.qichen.ruida.LocationTask;
import com.qichen.ruida.OnLocationGetListener;
import com.qichen.ruida.PositionEntity;
import com.qichen.ruida.R;
import com.qichen.ruida.RegeocodeTask;
import com.qichen.ruida.RouteTask;
import com.qichen.ruida.RouteTask.OnRouteCalculateListener;
import com.qichen.ruida.Utils_1;
import com.qichen.ruida.WX.WXPayUtils;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.bean.PeripheralInfo;
import com.qichen.ruida.bean.oderinfostatus;
import com.qichen.ruida.broadcastReceivers.UtilsBroadcastReceiver;
import com.qichen.ruida.request.CancellationOfOrder;
import com.qichen.ruida.request.SendOrder;
import com.qichen.ruida.request.UpDataRegist;
import com.qichen.ruida.service.MyMsgService;
import com.qichen.ruida.share.shareActivity;
import com.rongzhiheng.util.Constants;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.qichen.ruida.R.id.textView1;

//主界面MainActivity 带广告 lala
public class MainActivity extends BaseActivity implements OnCameraChangeListener, OnMapLoadedListener, OnLocationGetListener, OnClickListener, OnRouteCalculateListener, OnRouteSearchListener, AMap.OnMarkerClickListener {
	//路线规划 可以删除 路线规划的痕迹  和小汽车
	private DrivingRouteOverlay drivingRouteOverlay;

	private RouteSearch mRouteSearch;
	//主要的类
	private MapView mMapView;

	private AMap mAmap;

	private TextView mAddressTextView;

	private Button mDestinationButton;

	private Marker mPositionMark;

	public LatLng mStartPosition;

	private RegeocodeTask mRegeocodeTask;

	private LinearLayout mDestinationContainer;

	private TextView mRouteCostText;

	private TextView mDesitinationText;

	private LocationTask mLocationTask;

	private ImageView mLocationImage;

	private LinearLayout mFromToContainer;

	private Button mCancelButton;

	private boolean mIsFirst = true;

	private boolean mIsRouteSuccess = false;

	private long currentTime;

	private int Tagclass = 0;

	public String yuan;
	public String juli;
	public String fen;
	//倒计时的秒数
	public int timers = 5;

	//基数 打车高峰的时候用
 	public double msgs = 1.0;
	//订单状态 0 没有订单 1有订单
	public int orderStatus = 0;

	private Handler showHandler = new Handler();

	private Runnable showRunnable = new Runnable() {

		public void run() {

//			ImageView qidong = (ImageView) findViewById(R.id.qidong);
//			qidong.setVisibility(View.INVISIBLE);

			//如果手机联网了
			if (NetworkUtils.isNetworkAvailable(MainActivity.this)){
				//检查更新

			}else{
				UtilsToast.showToast(MainActivity.this, "请检查手机网络连接!");
			}
		}
	};


	private TextView mTextView1;
	private TextView mTextView2;
	private TextView mTextView3;
	private TextView mTextView4;
	//用户预约时间 显示
	private String mCallbackString;
	//用户预约时间 发送给后台
	private String sendCallbackString;
	private RouteTask mRouteTask;
	private ImageView mLocation_image1;
	private ImageView mLocation_image2;
    private TextView location_imagecc;
	private ImageView location_zhifu;
	private ImageView location_fenxiang;
	public ExecutorService mExecutorService;
	private Button cancel_button;
	//是否轮询
	boolean tag =true;
	//轮循 队列
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(final Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 1:
					if (null!=mStartPosition){
						Hashtable<String, String> hashtable = new Hashtable<String, String>();
						//必要字段
	//					passengerPosition action
	//					passenger_lon 经度
	//					passenger_lat 纬度
	//					passenger_id 用户身份
						//获取周边司机位置
						hashtable.put("action","passengerPosition");
						hashtable.put("passenger_lon",mStartPosition.longitude+"");
						hashtable.put("passenger_lat",mStartPosition.latitude+"");
						hashtable.put("passenger_id",SQUtils.getId(MainActivity.this));

						NetMessage.get(MainActivity.this)
								.sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
									@Override
									public void onSucceed(JSONObject jsonObject) {
										LogUtils.i("乘客周边数据jsonObject"+jsonObject);
										if (null!=jsonObject){
											try{
												PeripheralInfo peripheralInfo = jsonObject.toJavaObject(PeripheralInfo.class);
												LogUtils.i("乘客周边数据PeripheralInfo"+peripheralInfo);
												Double message = jsonObject.getDouble("message");
												msgs = message;

												//Double.parseDouble(message);
												//jsonObject.get();
												//添加覆盖物
												addMarkerData(peripheralInfo);
											}catch (Exception e){
												e.printStackTrace();
											}


										}


									}

									@Override
									public void onError(String errorString) {
										LogUtils.i("网络过来的数据"+errorString);
									}
								});



						i++;
						LogUtils.i("时间"+i);

						if (null!=mExecutorService){
							if (!mExecutorService.isShutdown())
								mExecutorService.submit(new loopNet());
							//mExecutorService.shutdown();
						}
					}


				break;

				case 2:
					if (null!=mQidong)
						mQidong.setBackgroundResource(R.drawable.logo2);
					break;
				case 3:
					if (null!=mQidong)
						mQidong.setVisibility(View.GONE);
					upDateApp();

					break;
				//发送广播  这样 只要注册了接受者 都能得到消息
				case 4:
					//接到延时之后的指令就--
					timers--;
					LogUtils.i("设置倒计时步骤2"+"我延时1秒之后"+"目前的倒计时数量是"+timers);
					LogUtils.i("订单状态现在是"+mOrder_state+"现在的倒计时时间"+timers);


					//如果订单状态是0 或者 倒计时没结束  就在倒计时
					if (timers>0){
						UtilsBroadcastReceiver.sendBroadcastReceiver(MainActivity.this, "timerStatus","timeRun",timers);
						LogUtils.i("设置倒计时步骤3"+"倒计时数大于1"+"目前的倒计时数量是"+timers+"并且发了广播让别人也修改成倒计时数"+"之后又到了 循环-- 的步骤2");
						mDownTime.submit(new downTimer());
						//设置显示
						location_imagecc.setText("未接订单有效期:"+timers);
						//LogUtils.i("倒计时时间"+timers);
						//mDestinationButton.setText("查看订单"+timers);
					}else{
						//timers = 5;
						LogUtils.i("设置倒计时步骤3-1"+"倒计时数<=0"+"目前的倒计时数量是"+timers+"发广播 停止倒计时");
						mDestinationButton.setText("输入目的地");
						UtilsBroadcastReceiver.sendBroadcastReceiver(MainActivity.this, "timerStatus","timeRun",0);
						//mDownTime.shutdownNow();

					}
					break;
			}


		}
	};
	private ImageView location_openxiancheng;
	private long mExitTime;

	//订单编号 传给 只给取消用的
	private String mOder_id;

	private int mT;
	//预约时间
	public TextView mYuyue_text;
	//打车类型  0正常打车  1预约打车  2接机场  3送机场
	public int order_type;
	private Intent mMyMsgServiceintent;

	static MainActivity mActivity;

	//回调回来的服务
	public MyMsgService mMyMsgService;
	private NotificationManager mNotificationManager;
	private SoundPool soundPool;
	private ImageView mQidong;
	//乘客 订单状态
	private String mOrder_state;
	private ExecutorService mStartlogo;
	private ExecutorService mDownTime;

	public static MainActivity getactivity(){
		return mActivity;
	}

	//服务
	private Intent mBindIntent;
	//进到查看订单详情必须的
	//private String mOrder_id;

	//public String yuYueTime ;
	public interface OnGetLocationListener {
		public void getLocation(String locationAddress);
	}
	//时间
	public int i =0;



	//主页面创建
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		init(savedInstanceState);
		initView();
		LogUtils.i("声音");



			//如果手机联网了
		if (NetworkUtils.isNetworkAvailable(this)){
			//检查更新
			//upDateApp();
		}else{
			UtilsToast.showToast(this, "请检查手机网络连接!");
		}



		tag =true;

		initThreadPool();


		//注册广播接收者 修改成我要用车的字
		IntentFilter filter = new IntentFilter("setCar");
		registerReceiver(receivercar, filter);

		//设置起点的字
		IntentFilter filterstart = new IntentFilter("start");
		registerReceiver(receivercar, filterstart);


		//获取倒计时时间
		IntentFilter filter2 = new IntentFilter("timerStatus");
		registerReceiver(receiver2, filter2);

		//初始化服务 轮询获取消息的方法
		initService();
		//生命周期方法里面注册广播


//		//注册广播接收者
//		IntentFilter filter11 = new IntentFilter("m1");
//		registerReceiver(my_broadcastReceiver, filter11);


		//接收 是否有订单  订单消息是什么
//		setCallBackbroadcastReceiver("order_Info", new GetCallBackbroadcastReceiver() {
//			@Override
//			public My_broadcastReceiver getbroadcastReceiver() {
//				My_broadcastReceiver my_broadcastReceiver = new My_broadcastReceiver(new BroadcastReceiverCallBack() {
//					@Override
//					public void CallBack(Context context, Intent intent) {
//						//intent.getSerializableExtra();
//						oderinfostatus serializableExtra = (oderinfostatus) intent.getSerializableExtra("order");
//						LogUtils.i("工具广播过来的订单数据是"+serializableExtra);
//					}
//				});
//				return my_broadcastReceiver;
//			}
//		});
	}


	//在成员变量的位置 创建一个  广播接收类 接收倒计时时间
	private InnerReceiver2 receiver2 = new InnerReceiver2();

	public class InnerReceiver2 extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			int timeRun = intent.getIntExtra("timeRun", 0);
			// String timeStop = intent.getStringExtra("timeStop");

			LogUtils.i("timeRun"+"\t"+timeRun);
			if (timeRun>0){
				LogUtils.i("设置倒计时步骤4"+"更改倒计时数"+"目前的倒计时数量是"+timers);
				if ("0".equals(mOrder_state)){
					mDestinationButton.setText("查看订单"+timeRun);
				}else{
					mDestinationButton.setText("查看订单");
				}
			}else{
				LogUtils.i("设置倒计时步骤5"+"结束更改为输入目的地 倒计时数改成 -1"+"目前的倒计时数量是"+timers);
				mDestinationButton.setText("输入目的地");
				timers = -1;
				//如果订单状态是 未接单就取消订单
				if ("0".equals(mOrder_state)){
					LogUtils.i("主界面开始取消订单");
					Hashtable<String, String> hashtable = new Hashtable<String, String>();
					hashtable.put("action", "passengerCancleOrder");
					hashtable.put("order_id", mOder_id);
					hashtable.put("order_cancle_describe", "超时");
					CancellationOfOrder.request(context, hashtable, new NetAesCallBack() {
						@Override
						public void onSucceed(JSONObject jsonObject) {
							LogUtils.i("取消订单回来的json"+jsonObject);
							if (null!=jsonObject){
								// UtilsToast.showToast(Cancellation_order.this, jsonObject.getString("message"));
								//创建一个意图 然后放进去数据 发送
								Intent intent2 = new Intent("setCar");
								intent2.putExtra("msg","输入目的地");
								LogUtils.i("发送广播"+"设置字体");
								sendBroadcast(intent2);


								//finish();
							}

						}

						@Override
						public void onError(String errorString) {
							LogUtils.i("取消订单回来的json"+errorString);

						}
					});
				}



			}


		}
	}





	/**
	 * 创建线程池
	 */
	private void initThreadPool() {

		//创建一个线程池 这个线程池的作用是 轮询访问网络  获取周边信息
		mExecutorService = Executors.newSingleThreadExecutor();
		mExecutorService.submit(new loopNet());


		//创建一个线程池 延时 改变logo
		mStartlogo = Executors.newSingleThreadExecutor();
		mStartlogo.submit(new logo());

		//倒计时的线程池
		mDownTime = Executors.newFixedThreadPool(4);

	}

	@Override
	public int getlayouXML() {
		return R.layout.activity_main;
	}
	//初始化主界面的view
	@Override
	public void initView() {



				//soundPool.onLoadComplete(SoundPool soundPool, int sampleId, int status)
		mRouteSearch = new RouteSearch(this);
		mRouteSearch.setRouteSearchListener(this);

		//初始化 起始点和终点
		mRouteTask = RouteTask.getInstance(getApplicationContext());
		mRouteTask.setEndPoint(null);
		mRouteTask.setStartPoint(null);

		TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
		relativeLayout_title_titleview.setText("锐达快车");
		//上面四个按钮
		//打车
		mTextView1 = (TextView) findViewById(textView1);
		//预约打车
		mTextView2 = (TextView) findViewById(R.id.textView2);
		//接机场
		mTextView3 = (TextView) findViewById(R.id.textView3);
		//送机场
		mTextView4 = (TextView) findViewById(R.id.textView4);
		//缩放+
		mLocation_image1 = (ImageView) findViewById(R.id.location_image1);
		mLocation_image1.setVisibility(View.GONE);
		//缩放-
		mLocation_image2 = (ImageView) findViewById(R.id.location_image2);
		mLocation_image2.setVisibility(View.GONE);
		//清除
		location_imagecc = (TextView) findViewById(R.id.location_imagecc);
		location_imagecc.setVisibility(View.VISIBLE);
		//微信支付
		location_zhifu = (ImageView) findViewById(R.id.location_zhifu);
		location_zhifu.setVisibility(View.GONE);
		//取消
		cancel_button = (Button) findViewById(R.id.cancel_button);

		//微信分享
		location_fenxiang = (ImageView) findViewById(R.id.location_fenxiang);
		location_fenxiang.setVisibility(View.GONE);
		//开启一个线程
		location_openxiancheng = (ImageView) findViewById(R.id.location_openxiancheng);
		location_openxiancheng.setVisibility(View.GONE);
		//预约
		mYuyue_text = (TextView) findViewById(R.id.yuyue_text);


		location_openxiancheng.setOnClickListener(this);
		mLocation_image1.setOnClickListener(this);
		mLocation_image2.setOnClickListener(this);
		location_imagecc.setOnClickListener(this);
		location_zhifu.setOnClickListener(this);
		location_fenxiang.setOnClickListener(this);
		mTextView1.setOnClickListener(this);
		mTextView2.setOnClickListener(this);
		mTextView3.setOnClickListener(this);
		mTextView4.setOnClickListener(this);
		cancel_button.setOnClickListener(this);
		Button relativeLayout_title_leftbutton = (Button) findViewById(R.id.relativeLayout_title_leftbutton);
		relativeLayout_title_leftbutton.setVisibility(View.VISIBLE);
		relativeLayout_title_leftbutton.setBackgroundResource(R.drawable.title_leftbutton1);

		Button relativeLayout_title_rightbutton = (Button) findViewById(R.id.relativeLayout_title_rightbutton);
		relativeLayout_title_rightbutton.setVisibility(View.GONE);
		//跳转到个人中心
		relativeLayout_title_leftbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, PersonCenterActivity.class);
				startActivity(intent);
			}
		});


		mLocationTask = LocationTask.getInstance(getApplicationContext());
		mLocationTask.setOnLocationGetListener(this);
		mLocationTask.startSingleLocate();
//		mLocationTask.startLocate();
		mRegeocodeTask = new RegeocodeTask(getApplicationContext());
		RouteTask.getInstance(getApplicationContext()).addRouteCalculateListener(this);
		currentTime = System.currentTimeMillis();

		//初始化启动logo
		mQidong = (ImageView) findViewById(R.id.qidong);
		mQidong.setVisibility(View.VISIBLE);
		initData();
	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {

	}

//	My_broadcastReceiver my_broadcastReceiver = new My_broadcastReceiver(new BroadcastReceiverCallBack() {
//		@Override
//		public void CallBack(Context context, Intent intent) {
//			String wangwang = intent.getStringExtra("wangwang");
//			UtilsToast.showToast(context, wangwang);
//		}
//	});



	private void initService() {
		doBindSerive();
		//UtilsSound.sound(MainActivity.this);
	}
		//代理人对象
	private MyMsgService.MyBind myBinder;

	private ServiceConnection conn = new ServiceConnection() {
		boolean tag = false;

		//当Activity和Service连接成功时会调用该方法
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			//在这里通过自定义的Binder与Service通信  代理人对象
			myBinder = (MyMsgService.MyBind)service;
			mMyMsgService = myBinder.getMyMsgService();
			mMyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {

		//服务回调
				@Override
				public void callBackOrder(JSONObject jsonObject) {
					LogUtils.i("轮询获取订单数据"+jsonObject);
					oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
					LogUtils.i("主页面设置订单号"+data.order_id);
					//订单号(重要)
					mOder_id =data.order_id;
					LogUtils.i("订单状态轮询"+data.order_state);
					//乘客订单状态
					mOrder_state = data.order_state;
//订单状态 0 未被接单  1 已接单  2 乘客已上车 3 订单完成
//  4 乘客取消订单 5 司机到达目的地(乘客未上车) 6司机取消订单  -1没订单

//					if (!order_stuatus.equals(data.order_state)){
//								//有订单  如果是未接单
//								order_stuatus_frist = false;
//					}

					//发了订单 未接
					if ("0".equals(data.order_state)){
						//order_stuatus_frist = false;
						LogUtils.i("订单状态不是-1"+"号码是"+data.order_id);
						//用这个数值 去打开 查看订单
						Tagclass = 1;
						//mDestinationButton.setText("查看订单");
						//mDestinationButton.setText("查看订单"+timers);
						tag = true;
					}else if ("1".equals(data.order_state)){
						//mDestinationButton.setText("查看订单");
						//mDestinationButton.setText("查看订单"+timers);
					}else if ("-1".equals(data.order_state)){
						//mDestinationButton.setText("我要用车");
						//没进来过
						if (tag){
							tag =false;
							mDestinationButton.setText("输入目的地");
						}
					}



				}
			});
			//服务回调
//			MyMsgService.setCallBackOrderInfo(new MyMsgService.CallBackOrderInfo() {
//				@Override
//				public void callBackOrder(JSONObject jsonObject) {
//					LogUtils.i("静态回调 主页面 订单数据"+jsonObject);
//					oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
//					LogUtils.i("主页面设置订单号"+data.order_id);
//					mOrder_id = data.order_id;
//					if (SQUtils.isCallCar(MainActivity.this)){
//						mDestinationButton.setText("查看订单");
//					}
//				}
//			});
		}


		//当Activity和Service断开连接时会调用该方法
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	};

	/**
	 * 通过bindService()启动(绑定)Service
	 */
	private void doBindSerive()
	{
		mBindIntent = new Intent(this, MyMsgService.class);
		//bindIntent.setAction("com.test.service.My_MSG");
		//当Service还没创建时,
		//第三个参数如果为0则不自动创建Service,为Service.BIND_AUTO_CREATE则自动创建
		bindService(mBindIntent, conn, Service.BIND_AUTO_CREATE);
	}

	/**
	 * 解除绑定Service
	 */
	private void doUnbindService()
	{
		unbindService(conn);
		stopService(mBindIntent);
	}
	public CallBack CallBacktitleString;

	public interface CallBacktitleString{
		void callBacktitleString(String titleString);
	}

	private void addMotification(Context context, String titleString, String data, Class clazz) {
		LogUtils.i("添加通知栏");
		if (mNotificationManager ==null){
			mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
		}
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
		//PendingIntent pendingIntent = openActivity(context, clazz, data);
		String s ="";
		if (titleString.equals("有新订单")) {
			s = "您有" + "条未读消息";

//			case "订单":
//				s="您有订单发货啦";
//				break;
		}

		mBuilder.setContentTitle(titleString)//设置通知栏标题
				.setContentText(s) //设置通知栏显示内容
				.setContentIntent(getDefalutIntent(context, Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//	.setNumber(number) //设置通知集合的数量
				.setTicker("您有新的消息") //通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
				.setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				.setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
				// Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
			//.setSmallIcon(R.drawable.logo)//设置通知小ICON
				.setContentIntent(pendingintent);
		Notification notification = mBuilder.build();
		notification.flags = Notification.FLAG_AUTO_CANCEL;

//		if (pendingIntent!=null){
//			LogUtils.i("来透传消息了 来的是"+titleString);
//			if ("报价".equals(titleString)){
//
//				mNotificationManager.notify(1, mBuilder.build());
//				if (callBack!=null){
//
//					callBack.callBacktitleString(titleString);
//				}
//
//			}
//			if ("订单".equals(titleString)){
//				mNotificationManager.notify(2, mBuilder.build());
//
//				if (callBack!=null){
//
//					callBack.callBacktitleString(titleString);
//				}
//			}
//		}
		//mNotificationManager.notify(1, mBuilder.build());
	}
	public PendingIntent getDefalutIntent(Context context, int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
		return pendingIntent;
	}



	//在成员变量的位置 创建一个  广播接收类
	private InnerReceivercar receivercar = new InnerReceivercar();

	//接收别的地方过来的数据 写一个内容类
	public class InnerReceivercar extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//使用intent获取发送过来的数据

			String msg = intent.getStringExtra("msg");

			LogUtils.i("过来的数据是"+msg);
			if (!TextUtils.isEmpty(msg)){
				LogUtils.i("设置为空");
				mDestinationButton.setText(msg);
				mRouteTask = RouteTask.getInstance(getApplicationContext());
				//PositionEntity endPoint = mRouteTask.getEndPoint();
				mRouteTask.setEndPoint(new PositionEntity(0,0,"",""));
//				endPoint.latitude = 0;
//				endPoint.longitude = 0;
				mDesitinationText.setText("");
				mRouteCostText.setText("估计费用");
				mYuyue_text.setVisibility(View.GONE);
				mYuyue_text.setText("");
				if (drivingRouteOverlay != null) {
					//删除掉 规划覆盖物
					drivingRouteOverlay.removeFromMap();
				}



				//mRouteCostText.setText(mRouteCostText.getText().toString()+msg);
			}
			String msgstart = intent.getStringExtra("msgstart");
			if (!TextUtils.isEmpty(msgstart)){
				//移动到这个点 设定缩放范围
				PositionEntity startPoint = mRouteTask.getStartPoint();
				double latitude = startPoint.latitude;
				double longitude = startPoint.longitude;

				CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude,longitude), TRIM_MEMORY_RUNNING_CRITICAL, 0, 0));
				mAmap.moveCamera(cameraUpdate);
				mAmap.animateCamera(cameraUpdate, 1000, new AMap.CancelableCallback() {
					@Override
					public void onFinish() {
						LogUtils.i("跳转完毕");
					}

					@Override
					public void onCancel() {
						LogUtils.i("跳转取消");
					}
				});
			}

		}
	}

	public loopNet getloopNetClass(){
		return new loopNet();
	}
	//循环体
		public   	class loopNet implements Runnable{
		@Override
		public void run() {
			//现在是开启状态
				if (tag){
					Message message = mHandler.obtainMessage();
					message.what = 1;
					mHandler.sendMessage(message);
					SystemClock.sleep(10000);
				}

		}

	}

	//软件更新检查
	private void upDateApp() {

//		//原来自动更新
//		UpdateChecker.checkForDialog(MainActivity.this, new CallBack() {
//			@Override
//			public void callDownLoad() {
//				UtilsToast.showToast(MainActivity.this, "正在后台下载更新");
//			}
//
//			@Override
//			public void callCancel(int apkforceUpData) {
//					if (apkforceUpData==0){
//						UtilsToast.showToast(MainActivity.this, "此次是强制更新取消退出软件");
//						finish();
//					}
//			}
//		});
		UpDataRegist.request(this, new NetAesCallBack() {
			@Override
			public void onSucceed(JSONObject jsonObject) {
				int versionCode = AppUtils.getVersionCode(MainActivity.this);
				Integer data = jsonObject.getInteger("data");
				LogUtils.i("更新数据"+jsonObject+"本地版本号"+versionCode+"网络版本号"+data);
				if (data>versionCode){
					LogUtils.i("提示版本升级");
					showDialog(MainActivity.this, "更新", 1, "http://59.110.11.60/APK/ruida.apk", new CallBack() {
						@Override
						public void callDownLoad() {
							UtilsToast.showToast(MainActivity.this, "正在后台下载更新");
						}

						@Override
						public void callCancel(int apkforceUpData) {
//							UtilsToast.showToast(MainActivity.this, "此次是强制更新取消退出软件");
//							finish();
						}
					});
				}
			}

			@Override
			public void onError(String errorString) {

			}
		});



	}
	//广告计时类
	class logo implements Runnable{

		@Override
		public void run() {
			SystemClock.sleep(5000);
			Message message = mHandler.obtainMessage();
			message.what = 2;
			mHandler.sendMessage(message);
			SystemClock.sleep(5000);
			Message message1 = mHandler.obtainMessage();
			message1.what = 3;
			mHandler.sendMessage(message1);
		}
	}


	/**
	 * Show dialog
	 */
	private void showDialog(Context context, String content, int apkforceUpData, String apkUrl, CallBack callBack) {
		UpdateDialog.show(context, content, apkforceUpData,apkUrl,callBack);
	}
	//初始化地图的view
	private void init(Bundle savedInstanceState) {
		//我的位置
		mAddressTextView = (TextView) findViewById(R.id.address_text);
		mAddressTextView.setOnClickListener(this);
		//输入目的地等
		mDestinationButton = (Button) findViewById(R.id.destination_button);

		mDestinationButton.setOnClickListener(this);
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		mAmap = mMapView.getMap();
		mAmap.getUiSettings().setZoomControlsEnabled(false);
		mAmap.setOnMapLoadedListener(this);
		mAmap.setOnCameraChangeListener(this);
		mAmap.moveCamera(CameraUpdateFactory.zoomTo(TRIM_MEMORY_RUNNING_CRITICAL));
		//意思是只要有覆盖被点击了我就有方法去响应
		//mAmap.setOnMarkerClickListener(this);

//		mMapView.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				LogUtils.i("我点了到地图"+"位置是"+event.getRawX());
//				return true;
//			}
//		});
//		mAmap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
//			@Override
//			public void onTouch(MotionEvent motionEvent) {
//				LogUtils.i("我点了到地图"+"位置是"+motionEvent.getRawX());
//			}
//		});
		mDestinationContainer = (LinearLayout) findViewById(R.id.destination_container);
		mRouteCostText = (TextView) findViewById(R.id.routecost_text);

		//点击了红色终点
		mDesitinationText = (TextView) findViewById(R.id.destination_text);

		mDesitinationText.setOnClickListener(this);
		mLocationImage = (ImageView) findViewById(R.id.location_image);
		mLocationImage.setOnClickListener(this);
		mFromToContainer = (LinearLayout) findViewById(R.id.fromto_container);
		mCancelButton = (Button) findViewById(R.id.cancel_button);
		mCancelButton.setOnClickListener(this);


		//注册广播接收者
		IntentFilter filter = new IntentFilter("test");
		registerReceiver(receiver, filter);
	}

	private void hideView() {

		mFromToContainer.setVisibility(View.GONE);
		mDestinationButton.setVisibility(View.GONE);
		mCancelButton.setVisibility(View.GONE);
	}

	private void showView() {
		mFromToContainer.setVisibility(View.VISIBLE);
		mDestinationButton.setVisibility(View.VISIBLE);
		if (mIsRouteSuccess) {
			//mCancelButton.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 对marker标注点点击响应事件
	 * 点击覆盖物
	 */
	@Override
	public boolean onMarkerClick(final Marker marker) {
//		if (mAmap != null) {
//			jumpPoint(marker);
//		}
		//通过marker可以获取坐标 获得一个坐标可以获得经纬度
		LatLng position = marker.getPosition();
		double latitude = position.latitude;
		double longitude = position.longitude;

		//Toast.makeText(this, "您点击了Marker"+"\n"+"latitude"+latitude+"\n"+"longitude"+longitude, Toast.LENGTH_LONG).show();
		//UtilsToast.showToast(this, marker.getTitle());
		return false;
	}

	@Override
	public void onCameraChange(CameraPosition arg0) {
		// hideView();
	}

	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {
		showView();
		mStartPosition = cameraPosition.target;
		mRegeocodeTask.setOnLocationGetListener(this);
		mRegeocodeTask.search(mStartPosition.latitude, mStartPosition.longitude);
		// if (mIsFirst) {
		// Utils.addEmulateData(mAmap, mStartPosition);
		// if (mPositionMark != null) {
		// mPositionMark.setToTop();
		// }
		// mIsFirst = false;
		// }
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();

		if (null!=mExecutorService){
			tag = true;
			if (!mExecutorService.isShutdown())
				mExecutorService.submit(new loopNet());
//			ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
//			scheduledExecutorService.schedule()

		}
		//if (null!=mDestinationButton)
//		getOrder.request(this, new NetAesCallBack() {
//			@Override
//			public void onSucceed(JSONObject jsonObject) {
//				LogUtils.i("获取订单状态的"+jsonObject);
//				oderinfostatus data = jsonObject.getObject("data", oderinfostatus.class);
//				String order = data.order;
//				int i = Integer.parseInt(order);
//				//这用户有订单
//				orderStatus = i;
//				mDestinationButton.setText("查看订单");
//			}
//
//			@Override
//			public void onError(String errorString) {
//				LogUtils.i("获取订单状态的"+errorString);
//			}
//		});
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();

			tag = false;

	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		mLocationTask.onDestroy();
		//取消广播
		unregisterReceiver(receiver);
		//取消广播 取消倒计时
		unregisterReceiver(receiver2);

		//取消广播 设置字体的
		unregisterReceiver(receivercar);
		if (null!=mExecutorService){
			LogUtils.i("关闭了程序 关闭线程");
			mExecutorService.shutdownNow();
		}

		doUnbindService();
		//registerReceiver(my_broadcastReceiver);
	}

	/**
	 * 地图刚加载的时候
	 */
	@Override
	public void onMapLoaded() {
		//创建一个覆盖物
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.setFlat(true);
		markerOptions.anchor(0.5f, 0.5f);
		markerOptions.position(new LatLng(0, 0));
		//创建一个小蓝点
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_loaction_start)));
		mPositionMark = mAmap.addMarker(markerOptions);
		//覆盖物的位置是 地图的中心点
		mPositionMark.setPositionByPixels(mMapView.getWidth() / 2, mMapView.getHeight() / 2);
		mLocationTask.startSingleLocate();
		 mLocationTask.startLocate();
	}

	/**
	 * 点击事件
	 * @param v
     */
	@Override
	public void onClick(View v) {
		//去搜索界面
		Intent intent = new Intent(this, DestinationActivity.class);
		//去登陆界面
		Intent intent99 = new Intent(this, PersonCenterLoginActivity.class);
		//去查看订单
		Intent intent_1 = new Intent(this, ShowOrderForm.class);
		Bundle bundle = new Bundle();
		//intent_1.putExtra("order_id",mOrder_id);
		//intent_1.putExtra("order_tag",Tagclass);
		bundle.putString("order_id",mOder_id);
		bundle.putInt("order_tag",Tagclass);
		intent_1.putExtras(bundle);
		LogUtils.i("主界面订单号码"+mOder_id);
		if (!SQUtils.isLogin(this)) {
			startActivity(intent99);
			return;
		} else if (SQUtils.isCallCar(this)){
			startActivity(intent_1);
			return;
		}

		switch (v.getId()) {
			//点了起始点
			case R.id.address_text:
				intent.putExtra("tag",4);
				mYuyue_text.setVisibility(View.GONE);
				mYuyue_text.setText("");
				startActivity(intent);
			break;


			//点击了请输入目的地的
		case R.id.destination_button:
			//如果这个按钮上面的字是 输入目的地
			if ("输入目的地".equals(mDestinationButton.getText())) {
				order_type = 0;
				mYuyue_text.setVisibility(View.GONE);
				mYuyue_text.setText("");
				intent.putExtra("tag",0);
				startActivity(intent);
			} else if ("我要用车".equals(mDestinationButton.getText())){
				//发送订单请求了
				//如果这个按钮上面的字是 我要用车
				//发送订单的请求
				if ("".equals(mDesitinationText.getText().toString())){
					UtilsToast.showToast(this, "没有填写终点");
					return;
				}
				mDestinationButton.setClickable(false);
				//发送订单请求
				SendOrder.request(this, new NetAesCallBack() {
					@Override
					public void onSucceed(JSONObject jsonObject) {
						LogUtils.i("发送订单回来的数据"+jsonObject);
						if (null!=jsonObject){
							mOder_id = jsonObject.getString("data");
							//设置订单生命时间
							timers = 120;
							LogUtils.i("设置倒计时步骤1"+"我点了打车"+"目前的倒计时数量是"+timers);
							mDestinationButton.setText("查看订单"+timers);
							mDestinationButton.setClickable(true);
							//开始了倒计时
							mDownTime.submit(new downTimer());
						}
					}

					@Override
					public void onError(String errorString) {
						LogUtils.i("发送订单回来的数据"+errorString);
						mDestinationButton.setClickable(true);
					}
				});


			}else if ("查看订单".equals(mDestinationButton.getText().toString().substring(0,4))){
				startActivity(intent_1);
				LogUtils.i("点了查看订单");
			}
			break;
			//打车
			case R.id.textView1:
				LogUtils.i("按了一下打车");
					intent.putExtra("tag",0);
					mYuyue_text.setVisibility(View.GONE);
					mYuyue_text.setText("");
					startActivity(intent);


//				Intent intent1 = new Intent(this, ShowOrderForm.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("order_id",mOder_id);
//					intent1.putExtras(bundle);
//					startActivity(intent1);
//					LogUtils.i("点了查看订单");
				break;
		//预约打车
		case R.id.textView2:
			order_type = 1;
				intent.putExtra("tag",1);
				startActivity(intent);

			break;
		//接机场
		case R.id.textView3:
			order_type = 2;
//			intent.putExtra("tag",2);
//			startActivity(intent);
				mRouteTask = RouteTask.getInstance(getApplicationContext());
				mRouteTask.setStartPoint(new PositionEntity(45.622394,126.243546,"太平国际机场","哈尔滨"));
				mRouteTask.search();
				mYuyue_text.setVisibility(View.GONE);
				mYuyue_text.setText("");
//			mAddressTextView.setText(RouteTask.getInstance(getApplicationContext()).getEndPointString().address);
				//移动到这个点 设定缩放范围
				CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(Constantss.TAIPINGGUOJIJICHANG, TRIM_MEMORY_RUNNING_CRITICAL, 0, 0));
				mAmap.moveCamera(cameraUpdate);
				mAmap.animateCamera(cameraUpdate, 1000, new AMap.CancelableCallback() {
					@Override
					public void onFinish() {
						LogUtils.i("跳转完毕");
					}

					@Override
					public void onCancel() {
						LogUtils.i("跳转取消");
					}
				});


			break;
		//送机场
		case R.id.textView4:
			order_type = 3;
//			intent.putExtra("tag",3);
//			startActivity(intent);
				mYuyue_text.setVisibility(View.GONE);
				mYuyue_text.setText("");
				mRouteTask = RouteTask.getInstance(getApplicationContext());
				mRouteTask.setEndPoint(new PositionEntity(45.622394,126.243546,"太平国际机场","哈尔滨"));
				mRouteTask.search();
				mDesitinationText.setText(RouteTask.getInstance(getApplicationContext()).getEndPoint().address);




			break;
		//点击回到当前位置
		case R.id.location_image:
			mLocationTask.startSingleLocate();



//			CameraUpdate cameraUpdate1 = CameraUpdateFactory.newLatLngZoom(new LatLng(0,0), TRIM_MEMORY_RUNNING_CRITICAL);
//			mAmap.moveCamera(cameraUpdate1);
//			mAmap.animateCamera(cameraUpdate1, 1000, new AMap.CancelableCallback() {
//				@Override
//				public void onFinish() {
//					LogUtils.i("跳转完毕");
//				}
//
//				@Override
//				public void onCancel() {
//					LogUtils.i("跳转取消");
//				}
//			});
			break;
		//点击了上面的目的地
			case R.id.destination_text:
				if (!SQUtils.isLogin(this)) {
					startActivity(intent99);
				}else if (SQUtils.isCallCar(this)){
					startActivity(intent_1);
				}
				else{
					intent.putExtra("tag",0);
					mYuyue_text.setVisibility(View.GONE);
					mYuyue_text.setText("");
					order_type = 0;
					startActivity(intent);
				}


			break;

			case R.id.location_image1:
				changeCamera(CameraUpdateFactory.zoomIn(), null);
			break;
			case R.id.location_image2:
				changeCamera(CameraUpdateFactory.zoomOut(), null);
			break;
			case R.id.cancel_button:
				if ("取消".equals(mCancelButton.getText().toString())){

					LogUtils.i("点了取消");
					mT = 1;
					mRouteCostText.setText("");
					if (drivingRouteOverlay != null) {
						//删除掉 规划覆盖物
						drivingRouteOverlay.removeFromMap();
					}
				}
//				else
//				if ("查看订单".equals(mCancelButton.getText().toString())){
//					Intent intent1 = new Intent(this, ShowOrderForm.class);
//					Bundle bundle = new Bundle();
//					bundle.putString("order_id",mOder_id);
//					intent1.putExtras(bundle);
//					startActivity(intent1);
//					LogUtils.i("点了查看订单");
//				}


				//mExecutorService.shutdownNow();
				//tag = false;
				//mExecutorService.shutdownNow();
				//setEndPointremove();

				break;
			case R.id.location_imagecc:
               // Utils_1.removeMarker();
			break;
			case R.id.location_zhifu:
				WXPayUtils wxPayUtils = new WXPayUtils(this, "http://www.weixin.qq.com/wxpay/pay.php");
				wxPayUtils.pay("美丽的","0.01","247632115");

				break;
			case R.id.location_fenxiang:
				startActivity(new Intent(this,shareActivity.class));
				break;
			case R.id.location_openxiancheng:
				if (null!=mExecutorService){
					tag = true;
					if (!mExecutorService.isShutdown())
						mExecutorService.submit(new loopNet());
				}
				break;




		}


	}






	//倒计时的线程
	public class downTimer implements Runnable{

		@Override
		public void run() {
			SystemClock.sleep(1000);
			Message message = mHandler.obtainMessage();
			message.what = 4;
			mHandler.sendMessage(message);
		}
	}



	/**
	 * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
	 */
	private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
		mAmap.animateCamera(update, 500, callback);
		//mAmap.moveCamera(update);
		}

//获取第二个界面过来的消息-------------------------------------------------------------------------------------------
private InnerReceiver receiver = new InnerReceiver();
	//注册广播
//	@Override
//	protected void onRestart() {
//		super.onRestart();
//		//注册广播
//		IntentFilter filter = new IntentFilter("test");
//		registerReceiver(receiver, filter);
//	}

//	@Override
//	protected void onStop() {
//		super.onStop();
//
//	}
	//接收别的地方过来的数据
	public class InnerReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//使用intent获取发送过来的数据

			String msg = intent.getStringExtra("msg");
			LogUtils.i("过来的数据是"+msg);
			if (!TextUtils.isEmpty(msg)){
				mCallbackString = msg;
				//mRouteCostText.setText(mRouteCostText.getText().toString()+msg);
				//mDesitinationText.setText(mDesitinationText.getText().toString()+"预约"+msg);
				//预约
				mYuyue_text.setVisibility(View.VISIBLE);
				mYuyue_text.setText("预约时间"+msg);
				order_type = 1;
			}
		}
	}

//获取第二个界面过来的消息-------------------------------------------------------------------------------------------



	// 逆地理编码 查询
	//点了定位之后调用的方法
	@Override
	public void onLocationGet(PositionEntity entity) {
		// todo 这里在网络定位时可以减少一个逆地理编码

		Log.e("333", entity.address);
		// String address = entity.address;
		// mAddressTextView.setText(address.substring(address.indexOf("区") + 1,
		// address.length()));
		RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);

		mStartPosition = new LatLng(entity.latitude, entity.longitude);
		CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(mStartPosition, mAmap.getCameraPosition().zoom);
		mAmap.animateCamera(cameraUpate);

//		double latitude = mStartPosition.latitude;
//		double longitude = mStartPosition.longitude;
//		LogUtils.i("当前经纬度"+"latitude"+"\t"+latitude+"\n"+"longitude"+"\t"+longitude);

		if (mPositionMark != null) {
				mPositionMark.setToTop();
			}



	}

    /**
     * 添加覆盖物数据
     */
    public void addMarkerData(PeripheralInfo peripheralInfo) {
		if (null!=peripheralInfo.data  ){
			List<PeripheralInfo.DataBean> data = peripheralInfo.data;
			if (data.size()>0){
				Utils_1.addEmulateData(mAmap, data);
				return;
			}

		}
		UtilsToast.showToast(this, "没有获取到周边车辆信息");

    }
	//重新定位之后的经纬度
	//当单位移动了 调用的方法
    @Override
	public void onRegecodeGet(PositionEntity entity) {
		Log.e("444", entity.city);
		Log.e("444", entity.address);
		Log.e("444", mStartPosition.latitude + "");
		Log.e("444", mStartPosition.longitude + "");
		if (entity.city.equals("010")) {
			return;
		}
		if (mIsFirst) {
			mIsFirst = false;
			showHandler.postDelayed(showRunnable, 1000);
		}

		//这里是 移动之后就设定起点
		String address = entity.address;
		mAddressTextView.setText(address.substring(address.indexOf("区") + 1, address.length()));
		// mDesitinationText.setText(System.currentTimeMillis()+"");
		entity.latitude = mStartPosition.latitude;
		entity.longitude = mStartPosition.longitude;

		RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);
		RouteTask.getInstance(getApplicationContext()).search();

	}

	/**
	 * 获取出发点的文本
	 * @return
     */
	public String getStartPointString(){
		return  mAddressTextView.getText().toString().trim();
	}

	/**
	 * 获取终点的文本
	 * @return
     */
	public String getEndPointString(){
		return mDesitinationText.getText().toString().trim();
	}

	/**
	 * 获取 开始坐标的 经纬度的数组
	 * @return
     */
	public String[] getStartPoint(){
		double latitude = mStartPosition.latitude;
		double longitude = mStartPosition.longitude;
		String[] strings = {latitude+"",longitude+""};
		return strings;
	}

	/**
	 * 获取终点坐标 数组
	 * @return
     */
	public String[] getEndPoint(){
		mRouteTask = RouteTask.getInstance(getApplicationContext());
		PositionEntity endPoint = mRouteTask.getEndPoint();

		double latitude = endPoint.latitude;
		double longitude = endPoint.longitude;
		String[] strings = {latitude+"",longitude+""};
		return strings;
	}

	/**
	 * 制空终点
	 */
	public void setEndPointremove(){
		mRouteTask = RouteTask.getInstance(getApplicationContext());
		//mRouteTask.removeRouteCalculateListener(this);
		//mRouteSearch.
	}


	/**
	 *
	 * @param distance 公里数
	 * @param duration 时间
     */
	public double[] sun(float distance, int duration){
		// 公里数小于 <=3 公里  9元 +时间(分钟) * 0.3元
		//   >3  <=15 公里  9元  + (当前公里数 -3) *2 +时间(分钟) *0.3元
		// >15公里  9元 + 12公里 *2  + (当前公里数 -15) *2.8 +时间(分钟) *0.3元
		double[] doubles = {};
		double Result =0.0;
		if (distance<=3){
			// 公里数小于 <=3 公里  9元 +时间(分钟) * 0.3元
			Result =9+(duration*0.3);
		}else if (distance>3 && distance<=15){
			//   >3  <=15 公里  9元  + (当前公里数 -3) *2 +时间(分钟) *0.3元
			Result =(9+(distance-3)*2)+(duration*0.3);
		}else{
			Result =(9+(12*2)+(distance-15) *2.8)+(duration*0.3);
		}
		doubles = new double[]{Result,9,0.3};
		return doubles;

	}
	/**
	 * 获取估算信息
	 * @param cost 钱
	 * @param distance  距离
	 * @param duration  时间
     */
	@Override
	public void onRouteCalculate(float cost, float distance, int duration) {
		mDestinationContainer.setVisibility(View.VISIBLE);
		mIsRouteSuccess = false;
		double[] sun;
		mRouteCostText.setVisibility(View.VISIBLE);
		mDesitinationText.setText(RouteTask.getInstance(getApplicationContext()).getEndPoint().address);
		if (TextUtils.isEmpty(mCallbackString)){
			//String.format("预估费用%.2f元，距离%.1fkm，用时%d分", cost, distance, duration);
			sun = sun(distance, duration);
			mRouteCostText.setText(String.format("预估费用%.2f元，距离%.1fkm，用时%d分 \n其中起车费%.1f元 ， 服务费%.1f元/分钟", (sun[0]*msgs), distance, duration,sun[1],sun[2]));

		}else{
			//mRouteCostText.setText(String.format("预估费用%.2f元，距离%.1fkm，用时%d分", cost, distance, duration)+"\t"+"预约时间"+mCallbackString);
			sun = sun(distance, duration);
			mRouteCostText.setText(String.format("预估费用%.2f元，距离%.1fkm，用时%d分 \n其中起车费%.1f元 ， 服务费%.1f元/分钟", (sun[0]*msgs), distance, duration,sun[1],sun[2]));
		}

		//等着发给后台服务器请求
		//yuan = cost+"";
		yuan = sun[0]*msgs+"";
		juli = distance+"";
		fen = duration+"";


		//把这个值给 发送给后台的数据交互
		sendCallbackString = mCallbackString;
		//清空显示
		mCallbackString ="";
//		if (!TextUtils.isEmpty(AppClass.stringTime)){
//			mRouteCostText.setText(mRouteCostText.getText().toString()+"\t"+"预定时间"+AppClass.stringTime);
//			AppClass.stringTime = "";
//		}

		//如果是 没有订单状态 就不要设置这个按钮的名字了
		if ("-1".equals(mOrder_state)){
			mDestinationButton.setText("我要用车");

		}
		//mCancelButton.setVisibility(View.VISIBLE);
		// mDestinationButton.setOnClickListener(null);

		PositionEntity endPosition = RouteTask.getInstance(getApplicationContext()).getEndPoint();

		FromAndTo fromAndTo = new FromAndTo(new LatLonPoint(mStartPosition.latitude, mStartPosition.longitude), new LatLonPoint(endPosition.latitude, endPosition.longitude));
		DriveRouteQuery driveRouteQuery = new DriveRouteQuery();
		if (mT==0){
			//有规划的
			 driveRouteQuery = new DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null, null, "");
		}else{
			//没有规划的
			 driveRouteQuery = new DriveRouteQuery();

		}
		if (drivingRouteOverlay != null) {
			//去掉小汽车
			drivingRouteOverlay.setNodeIconVisibility(false);
		}
		mRouteSearch.calculateDriveRouteAsyn(driveRouteQuery);// 异步路径规划驾车模式查询


	}

	// @Override
	// public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
	// // 解析result获取算路结果
	// Log.e("-----", rCode + "");
	//
	// Log.e("-----", latLonPoints.get(0).getLatitude() + "");
	// Log.e("-----", latLonPoints.get(0).getLongitude() + "");
	// Log.e("-----", latLonPoints.get(1).getLatitude() + "");
	// Log.e("-----", latLonPoints.get(1).getLongitude() + "");

	// List<LatLng> latLngs = new ArrayList<LatLng>();
	// // latLngs.add(new LatLng(39.999391, 116.135972));
	// // latLngs.add(new LatLng(39.898323, 116.057694));
	// // latLngs.add(new LatLng(39.900430, 116.265061));
	// // latLngs.add(new LatLng(39.955192, 116.140092));
	// List<DrivePath> drivePaths = result.getPaths();
	//
	// for (DrivePath drivePath:drivePaths) {
	//
	// latLngs.add(new LatLng(drivePath., 116.135972));
	// }
	// mAmap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));

	// }

	/**
	 * 驾车模式回调 开始拿到数据在地图上画画了
	 * @param result
	 * @param arg1
     */
	@Override
	public void onDriveRouteSearched(DriveRouteResult result, int arg1) {
		if (result != null && result.getPaths() != null && result.getPaths().size() > 0) {
			if (drivingRouteOverlay != null) {
				drivingRouteOverlay.removeFromMap();
			}
			DriveRouteResult driveRouteResult = result;
			DrivePath drivePath = driveRouteResult.getPaths().get(0);
			drivingRouteOverlay = new DrivingRouteOverlay(MainActivity.this, mAmap, drivePath, driveRouteResult.getStartPos(), driveRouteResult.getTargetPos());
			drivingRouteOverlay.setNodeIconVisibility(false);
			drivingRouteOverlay.addToMap();
			// drivingRouteOverlay.zoomToSpan();
		}
	}
	// {
	//
	// /* 修改起点marker样式 */
	// @Override
	// protected BitmapDescriptor getStartBitmapDescriptor() {
	// if (flag == 110) {
	// return BitmapDescriptorFactory
	// .fromResource(R.drawable.start);
	// } else {
	//
	// return BitmapDescriptorFactory
	// .fromResource(R.drawable.map_car);
	// }
	// }
	//
	// /* 修改终点marker样式 */
	// @Override
	// protected BitmapDescriptor getEndBitmapDescriptor() {
	// if (flag == 110) {
	// return BitmapDescriptorFactory
	// .fromResource(R.drawable.end);
	// } else {
	// return BitmapDescriptorFactory
	// .fromResource(R.drawable.map_user);
	// }
	// }
	//
	// /* 修改中间点marker样式 */
	// @Override
	// protected BitmapDescriptor getDriveBitmapDescriptor() {
	// return BitmapDescriptorFactory
	// .fromResource(R.drawable.nothing);
	// }
	//
	// };
	// drivingRouteOverlay.removeFromMap();
	// drivingRouteOverlay.addToMap();
	// drivingRouteOverlay.setThroughPointIconVisibility(false);
	// drivingRouteOverlay.zoomToSpan();// 移动到当前视图
	// }
	// }

	// drivingRouteOverlay.zoomToSpan();// 移动到当前视图
	// 这个方法和aMap.moveCamera(CameraUpdateFactory.zoomTo(15));

	/**
	 * 公交模式回调
	 * @param arg0
	 * @param arg1
     */
	@Override
	public void onBusRouteSearched(BusRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 走路模式对调
	 * @param arg0
	 * @param arg1
     */
	@Override
	public void onWalkRouteSearched(WalkRouteResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	//判断 是否 关闭  APP 双击返回键  退出 APP
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



//	public class SendOrder extends Thread implements Runnable {
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			super.run();
//			try {
//				JSONObject msgObject = new JSONObject();
//				// msgObject.put("action", "SendOrder");
//				// msgObject.put("passenger_id", passenger_id); //乘客Id
//				// msgObject.put("begion_address", begion_address); //开始地址
//				// msgObject.put("end_address", end_address); //终点地址
//				// msgObject.put("begion_lat", begion_lat); //开始纬度
//				// msgObject.put("begion_lon", begion_lon); //开始经度
//				// msgObject.put("end_lat", end_lat); //终点纬度
//				// msgObject.put("end_lon", end_lon); //终点经度
//				// msgObject.put("order_number", order_number); //打车人数
//				// msgObject.put("order_type", order_type); //0、马上打车 1、预约打车
//				// msgObject.put("order_compute_mileage", order_compute_mileage); //预估里程
//				// msgObject.put("order_compute_money", order_compute_money); //预估价格
//				// msgObject.put("order_compute_time", order_compute_time); //预估时间
//
//				msgObject.put("action", "SendOrder");
//				msgObject.put("passenger_id", "1");
//				msgObject.put("begion_address", "共乐街道通达街262号聚贤花园");
//				msgObject.put("end_address", "太平国际机场");
//				msgObject.put("begion_lat", "45.746542");
//				msgObject.put("begion_lon", "126.605064");
//				msgObject.put("end_lat", "45.622432");
//				msgObject.put("end_lon", "126.243605");
//				msgObject.put("order_number", "1");
//				msgObject.put("order_type", "1");
//				msgObject.put("order_compute_mileage", "34.5");
//				msgObject.put("order_compute_money", "90.28");
//				msgObject.put("order_compute_time", "45");
//				// 上传的参数
//				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
//				System.out.println(msgObject.toString());
//				// 返回的加密串
//				String str = Utils.postUrlData(Constants.new_url, para);
//				// 构建handler
//				Message msg = sendPositionHandler.obtainMessage();
//				Bundle bundle = new Bundle();
//				bundle.putString("sendPosition", str);
//				msg.setData(bundle);
//				sendPositionHandler.sendMessage(msg);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	/**
//	 * 界面handler
//	 */
//	public Handler sendPositionHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			try {
//				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("sendPosition"));
//				JSONObject obj = new JSONObject(str);
//				Log.i("str", str);
//				// 有数据
//				if (obj.getString("result").equals("1")) {
//					Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//				} else {
//					Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	};
}
