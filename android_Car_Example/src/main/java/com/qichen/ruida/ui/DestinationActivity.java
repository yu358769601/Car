/**  
 * Project Name:Android_Car_Example  
 * File Name:DestinationActivity.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午10:52:03  
 *  
*/

package com.qichen.ruida.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.qichen.AppSQL.AppClass;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.TimerUtils;
import com.qichen.Utils.UtilsMyText;
import com.qichen.Utils.UtilsToast;
import com.qichen.interFace.CallBackTime;
import com.qichen.ruida.InputTipTask;
import com.qichen.ruida.PoiSearchTask;
import com.qichen.ruida.PositionEntity;
import com.qichen.ruida.R;
import com.qichen.ruida.RecomandAdapter;
import com.qichen.ruida.RouteTask;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import static com.qichen.ruida.Timer.DATEPICKER_TAG;
import static com.qichen.ruida.Timer.TIMEPICKER_TAG;

/**
 * ClassName:DestinationActivity <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年4月3日 上午10:52:03 <br/>
 *	带联想搜索的界面activity
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class DestinationActivity extends FragmentActivity implements OnClickListener, TextWatcher, OnItemClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	public  static final int NORMAL_0 =0;
	public  static final int SUBSCRIBE_1 =1;
	public  static final int ACCEPT_2 =2;
	public  static final int SEND_3 =3;
	public  static final int SELECT_START_4 =4;


	private ListView mRecommendList;

	private ImageView mBack_Image;

	private TextView mSearchText;

	private EditText mDestinaionText;

	private RecomandAdapter mRecomandAdapter;
	//当前界面是通过什么进来的 0 1 2 3
	private int mClassTag ;

	public CallBackTime callBack;

	private RouteTask mRouteTask;
	private LinearLayout mRl_add;
	private TextView mTv_appointment_time;

	Handler mHandler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

		}
	};
	private TextView tv_appointment_time_show;
	private TextView tv_timer;
	private TextView tv_timershow;
	private int mTag = 6;
	private TextView mRelativeLayout_title_titleview;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination);

		mRelativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
		mRelativeLayout_title_titleview.setText("选择下车地点");

		Button relativeLayout_title_leftbutton = (Button) findViewById(R.id.relativeLayout_title_leftbutton);
		relativeLayout_title_leftbutton.setVisibility(View.VISIBLE);
		relativeLayout_title_leftbutton.setBackgroundResource(R.drawable.title_leftbutton);

		Button relativeLayout_title_rightbutton = (Button) findViewById(R.id.relativeLayout_title_rightbutton);
		relativeLayout_title_rightbutton.setVisibility(View.GONE);

		relativeLayout_title_leftbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//添加的re
		mRl_add = (LinearLayout) findViewById(R.id.rl_add);

		mRl_add.setOnClickListener(this);
		//预约日期
		mTv_appointment_time = (TextView) findViewById(R.id.tv_appointment_time);
		tv_appointment_time_show = (TextView) findViewById(R.id.tv_appointment_time_show);
		//预约时间
//		tv_timer = (TextView) findViewById(R.id.tv_timer);
//		tv_timershow = (TextView) findViewById(R.id.tv_timershow);

		//tv_timer.setOnClickListener(this);
		//mTv_appointment_time.setOnClickListener(this);



		mRecommendList = (ListView) findViewById(R.id.recommend_list);
		mBack_Image = (ImageView) findViewById(R.id.destination_back);
		mBack_Image.setOnClickListener(this);

		mSearchText = (TextView) findViewById(R.id.destination_search);
		mSearchText.setOnClickListener(this);

		mDestinaionText = (EditText) findViewById(R.id.destination_edittext);
		mDestinaionText.addTextChangedListener(this);
		mRecomandAdapter = new RecomandAdapter(getApplicationContext());
		mRecommendList.setAdapter(mRecomandAdapter);
		mRecommendList.setOnItemClickListener(this);

		mRouteTask = RouteTask.getInstance(getApplicationContext());
		getBox();
	}

	private void getBox() {
		Intent intent = getIntent();
		int tag =  intent.getIntExtra("tag", 0);
		switch (tag){
			//是正常打车
			case NORMAL_0:
				mRl_add.setVisibility(View.GONE);
			break;
			//点了预约
			case SUBSCRIBE_1:
				mClassTag = SUBSCRIBE_1;

			break;
			//巴拉巴拉
			case ACCEPT_2:

			break;
			//巴拉巴拉
			case SEND_3:

			break;
			case SELECT_START_4:
				mClassTag = SELECT_START_4;
				mRl_add.setVisibility(View.GONE);
				mRelativeLayout_title_titleview.setText("请选择上车地点");
			break;
		}
	}




	@Override
	public void afterTextChanged(Editable arg0) {

		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (RouteTask.getInstance(getApplicationContext()).getStartPoint() == null) {
			Toast.makeText(getApplicationContext(), "检查网络，Key等问题", Toast.LENGTH_SHORT).show();
			return;
		}
		InputTipTask.getInstance(getApplicationContext(), mRecomandAdapter).searchTips(s.toString(),
				RouteTask.getInstance(getApplicationContext()).getStartPoint().city);

	}

	@Override
	public void onClick(View v) {
		final Calendar calendar = Calendar.getInstance();
		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),false);
		final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), true, false);
		switch (v.getId()) {
		case R.id.destination_back:
			Intent intent = new Intent(DestinationActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
			break;
		case R.id.destination_search:
			PoiSearchTask poiSearchTask = new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
			poiSearchTask.search(mDestinaionText.getText().toString(),
					RouteTask.getInstance(getApplicationContext()).getStartPoint().city);
			break;
//		//点击了预约日期
//		case R.id.tv_appointment_time:
//				datePickerDialog.setYearRange(2016, 2028);
//				datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
////			else if (tv_timer.getText().toString().equals("")){
////
////			}
//
//
//			break;
		//点击了预约日期
		case R.id.rl_add:
				datePickerDialog.setYearRange(2016, 2028);
				datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
//			else if (tv_timer.getText().toString().equals("")){
//
//			}


			break;

//		case R.id.tv_timer:
//			timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
//
//			break;


		}


	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		if (mClassTag==SUBSCRIBE_1){
			if (UtilsMyText.isEmptys(tv_appointment_time_show)){
				UtilsToast.showToast(this, "还没有设定预约时间");
				return;
			}
		}
		PositionEntity entity = (PositionEntity) mRecomandAdapter.getItem(position);
		if (entity.latitude == 0 && entity.longitude == 0) {
			PoiSearchTask poiSearchTask = new PoiSearchTask(getApplicationContext(), mRecomandAdapter);
			poiSearchTask.search(entity.address, RouteTask.getInstance(getApplicationContext()).getStartPoint().city);

		} else {
			//如果成功了
			//设置终点

			if (mClassTag==SELECT_START_4){
				mRouteTask.setStartPoint(entity);
			}else{

				mRouteTask.setEndPoint(entity);
			}
			//搜索
			mRouteTask.search();
			if (mClassTag==SUBSCRIBE_1){
				if (!UtilsMyText.isEmptys(tv_appointment_time_show)){

					AppClass.stringTime = tv_appointment_time_show.getText().toString();
				//	UtilsToast.showToast(this, "还没有设定预约时间");
					//如果有数值就把这个未知的数值发送给前一个activity

					Intent intent1 = new Intent("test");
					intent1.putExtra("msg", tv_appointment_time_show.getText().toString());
					LogUtils.i("发送广播"+tv_appointment_time_show.getText().toString());
					sendBroadcast(intent1);
					//发送一个预约广播
					//UtilsBroadcastReceiver.sendBroadcastReceiver(this,"msg_My","timer",tv_appointment_time_show.getText().toString());

				}
			} else if (mClassTag==SELECT_START_4){

				Intent intent1 = new Intent("start");
				intent1.putExtra("msgstart", "0");
				LogUtils.i("发送广播近来开始的选择");
				sendBroadcast(intent1);


				//发一个选择上车地点的广播
				//UtilsBroadcastReceiver.sendBroadcastReceiver(this,"msg_My","start","0");

			}


			Intent intent = new Intent(DestinationActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);

			finish();
		}
	}

	/**
	 * 日期选择回调
	 * @param datePickerDialog
	 * @param year
	 * @param month
     * @param day
     */
	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
		final Calendar calendar = Calendar.getInstance();
//		int i = calendar.get(Calendar.HOUR_OF_DAY);
//		int i1 = calendar.get(Calendar.MINUTE);
		String date = year+"-"+(month+1)+"-"+day;
		 TimePickerDialog timePickerDialog = null;
//		if (!TimerUtils.getToday(date)){
//			timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), true, false);
//		}else{
//			int[] time = TimerUtils.get30After();
//			timePickerDialog = TimePickerDialog.newInstance(this, time[0] ,time[1], true, false);
//		}
		timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), true, false);

		//int[] time = TimerUtils.get30After();
		//final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), true, false);
		//final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, time[0] ,time[1], true, false);
		tv_appointment_time_show.setText("");
		mTag = TimerUtils.setDatas(this, year, month, day, tv_appointment_time_show);
		//TimerUtils.set0(year,month,day,tv_appointment_time_show);
		//没有选择今天之前的日期
		LogUtils.i("当前状态"+mTag);
		if (mTag==0){
			timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
		}else if (mTag!=1){
			//timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
			UtilsToast.showToast(this, "必须选择当天时间进行预约");
		}
	}




	/**
	 * 时间选择回调
	 * @param view      The view associated with this listener.
	 * @param hourOfDay The hour that was set.
	 * @param minute    The minute that was set.
     */
	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		//tv_timershow.setText("");

		if (mTag==0){
			if (TimerUtils.time_(hourOfDay,minute)==2){
				UtilsToast.showToast(this, "预约打车选择时间应该在当前时间的后30分钟之内");
				tv_appointment_time_show.setText("");
				AppClass.DataString = "";
				return;
			}
			if (TimerUtils.time_(hourOfDay,minute)==1){
				UtilsToast.showToast(this, "当日不能选择当前时间以前的");
				tv_appointment_time_show.setText("");
				AppClass.DataString = "";
				return;
			}
//			if (TimerUtils.time_(hourOfDay,minute)==0){
//				UtilsToast.showToast(this, "");
//				tv_appointment_time_show.setText("");
//				AppClass.DataString = "";
//				return;
//			}



		}
		TimerUtils.set0(hourOfDay,minute,tv_appointment_time_show);


	}
}
