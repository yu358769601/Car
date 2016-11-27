package com.qichen.person;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.UtilsNet.NetMessage;
import com.qichen.ruida.R;
import com.qichen.ruida.bean.PassengerUserInfo;
import com.rongzhiheng.util.AES;
import com.rongzhiheng.util.BitmapManager;
import com.rongzhiheng.util.CircleImageView;
import com.rongzhiheng.util.Constants;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;

import static com.qichen.ruida.R.id.passenger_job;
import static com.qichen.ruida.R.id.passenger_nickname;

//个人信息activity
public class PersonCenterXinXiActivity extends Activity  {
	
	Bitmap bitmap;
	BitmapManager bmpManager;
	CircleImageView passenger_photo;

	// 保存图片本地路径
	public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath() + "/account/";
	public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
	private static final String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;

	private static final String IMAGE_FILE_NAME = "faceImage.jpeg";
	private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";

	// 常量定义
	public static final int TAKE_A_PICTURE = 10;
	public static final int SELECT_A_PICTURE = 20;
	public static final int SET_PICTURE = 30;
	public static final int SET_ALBUM_PICTURE_KITKAT = 40;
	public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
	private String mAlbumPicturePath = null;

	File fileone = null;
	File filetwo = null;

	// 版本比较：是否是4.4及以上版本
	final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	public boolean uploading;

	public Bitmap passenger_photoBitmap;
	private int selectPhoto = 0;
	private String photoOrVideo = "";

	private String[] industry = { "培训", "图书", "教材", "考级", "技能", "发明" };
	private String[] sex = { "男", "女" };
	private int industry_select = -1;
	private int sex_select = -1;
	private Button passenger_industry;
	private Button passenger_sex;
	private int openSign = -1, totalSize;
	private ArrayList<Button> buttonList = new ArrayList<Button>();
	private ArrayList<TextView> textList = new ArrayList<TextView>();
	private ArrayList<LinearLayout> layOutList = new ArrayList<LinearLayout>();

	private Handler quitHandler = new Handler();

	private Runnable quitRunnable = new Runnable() {

		public void run() {

			Intent mIntent = new Intent("registSuccess");
			sendBroadcast(mIntent);

			quitHandler.removeCallbacks(quitRunnable); // 停止Timer
			finish();
		}
	};
	private EditText mPassenger_nickname;
	private Button mPassenger_sex;
	private EditText mPassenger_job;
	//private LoadingView_09 mLoading_view;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_center_xinxi);

		initView();


		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.person_photo);
		bmpManager = new BitmapManager(bitmap);
		CircleImageView.style = 2;

		File directory = new File(ACCOUNT_DIR);
		File imagepath = new File(IMGPATH);
		if (!directory.exists()) {
			Log.i("zou", "directory.mkdir()");
			directory.mkdir();
		}
		if (!imagepath.exists()) {
			Log.i("zou", "imagepath.mkdir()");
			imagepath.mkdir();
		}

		fileone = new File(IMGPATH, IMAGE_FILE_NAME);
		filetwo = new File(IMGPATH, TMP_IMAGE_FILE_NAME);

		try {
			if (!fileone.exists() && !filetwo.exists()) {
				fileone.createNewFile();
				filetwo.createNewFile();
			}
		} catch (Exception e) {
		}

		TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
		relativeLayout_title_titleview.setText("个人信息");

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
		//点了提交
		Button xinxi_queding = (Button) findViewById(R.id.xinxi_queding);
		xinxi_queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//发送消息
//				Thread thread = new Thread(new sendXinXi());
//				thread.start();
				initData(1);
			}
		});

		passenger_photo = (CircleImageView) findViewById(R.id.passenger_photo);
		passenger_photo.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				arg0.getParent().requestDisallowInterceptTouchEvent(true);//
				// 通知父控件勿拦截本控件touch事件
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.d("TAG", "ACTION_DOWN.............");
//					if (selectPhoto != 0) {
//						Toast.makeText(PersonCenterXinXiActivity.this, "照片上传中~", Toast.LENGTH_SHORT).show();
//						break;
//					}
					selectPhoto = 1;
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
					startActivityForResult(intent, TAKE_A_PICTURE);
					Log.i("zou", "TAKE_A_PICTURE");
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("TAG", "ACTION_MOVE.............");
					break;
				case MotionEvent.ACTION_CANCEL:
					Log.d("TAG", "ACTION_CANCEL.............");
					break;
				case MotionEvent.ACTION_UP:
					Log.d("TAG", "ACTION_UP.............");
					break;
				default:
					break;
				}
				return false;
			}
		});

		//////////////////////////////////////////////////////////////////////////////////////////////

		initPopupWindow();


		/*mLoading_view = (LoadingView_09) findViewById(R.id.loading_view);
		mLoading_view.setRefrechListener(this);*/

		initData(0);
		//获取人员信息
//		Thread thread = new Thread(new userRegist());
//		thread.start();

	}

	private void initView() {
		//名字
		mPassenger_nickname = (EditText) findViewById(passenger_nickname);
			//性别
		mPassenger_sex = (Button) findViewById(R.id.passenger_sex);
		//工作
		mPassenger_job = (EditText) findViewById(passenger_job);

	}

	//网络请求
	private void initData(int i) {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();

		//mLoading_view.setStatue(LoadingView_09.LOADING);
		//回显
		if (i==0){
			hashtable.put("action", "getXinXi");
			hashtable.put("passenger_telephone", SQUtils.getStrings(this, "passenger_telephone", ""));
			NetMessage.get(this)
					.sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
						@Override
						public void onSucceed(com.alibaba.fastjson.JSONObject jsonObject) {

							if (null!=jsonObject){
								PassengerUserInfo data = jsonObject.getObject("data", PassengerUserInfo.class);
								if (!"".equals(data.passenger_photo)){
									bmpManager.loadBitmap(Constants.fangwen_image_url + data.passenger_photo, passenger_photo);
								}
								//mLoading_view.setStatue(LoadingView_09.GONE);
								//passenger_photo.seti
								LogUtils.i("个人信息回来的网络数据"+data);


								mPassenger_nickname.setText(data.passenger_nickname);

								SQUtils.setString(PersonCenterXinXiActivity.this, "passenger_nickname", data.passenger_nickname);



								mPassenger_sex.setText(data.passenger_sex);
//
//								Button passenger_industry = (Button) findViewById(R.id.passenger_industry);
//								passenger_industry.setText(data.passenger_industry);
//

								mPassenger_job.setText(data.passenger_job);

							}
						}

						@Override
						public void onError(String errorString) {
							LogUtils.i("回显网络数据错误信息"+errorString);
							//mLoading_view.setStatue(LoadingView_09.NO_NETWORK);
							UtilsToast.showToast(PersonCenterXinXiActivity.this, "访问失败");
						}
					});
		}
		//发送消息
		if (i==1){
//			EditText passenger_nickname = (EditText) findViewById(R.id.passenger_nickname);
//			EditText passenger_job = (EditText) findViewById(R.id.passenger_job);

			hashtable.put("action", "sendXinXi");
			hashtable.put("passenger_telephone", SQUtils.getStrings(this, "passenger_telephone", ""));
			hashtable.put("passenger_nickname", mPassenger_nickname.getText().toString());
			hashtable.put("passenger_sex", passenger_sex.getText().toString());
//			hashtable.put("passenger_industry", passenger_industry.getText().toString());
			hashtable.put("passenger_job", mPassenger_job.getText().toString());


			NetMessage.get(this)
					.sendMessage(Constants.new_url, hashtable, Constants.NORMAL, new NetAesCallBack() {
						@Override
						public void onSucceed(com.alibaba.fastjson.JSONObject jsonObject) {

							if (null!=jsonObject){
								String message = jsonObject.getString("message");
								LogUtils.i("个人信息回来的网络数据"+jsonObject);
								SQUtils.setString(PersonCenterXinXiActivity.this, "passenger_nickname", mPassenger_nickname.getText().toString());
								UtilsToast.showToast(PersonCenterXinXiActivity.this, "更改成功");
								String passenger_nickname1 = SQUtils.getStrings(PersonCenterXinXiActivity.this, "passenger_nickname", "");
								LogUtils.i("数据库名字"+passenger_nickname1);
								finish();
								//mLoading_view.setStatue(LoadingView_09.GONE);
							}
						}

						@Override
						public void onError(String errorString) {
							LogUtils.i("回显网络数据错误信息"+errorString);
							//mLoading_view.setStatue(LoadingView_09.NO_NETWORK);
						}
					});
		}


	}


	//点击屏幕外面edtext取消焦点
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public  boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getRawX() > left && event.getRawX() < right
					&& event.getRawY() > top && event.getRawY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}


	public void initPopupWindow() {

		LayoutInflater inflater = LayoutInflater.from(this);
		// 引入窗口配置文件
		View view = inflater.inflate(R.layout.pop_window, null);
		// 创建PopupWindow对象
		final PopupWindow pop = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);

		// 需要设置一下此参数，点击外边可消失
		// pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_back));
		// 设置点击窗口外边窗口消失
		pop.setOutsideTouchable(true);
		// 设置此参数获得焦点，否则无法点击
		pop.setFocusable(true);

		LinearLayout pop_layout1 = (LinearLayout) view.findViewById(R.id.pop_layout1);
		LinearLayout pop_layout2 = (LinearLayout) view.findViewById(R.id.pop_layout2);
		LinearLayout pop_layout3 = (LinearLayout) view.findViewById(R.id.pop_layout3);
		LinearLayout pop_layout4 = (LinearLayout) view.findViewById(R.id.pop_layout4);
		LinearLayout pop_layout5 = (LinearLayout) view.findViewById(R.id.pop_layout5);
		LinearLayout pop_layout6 = (LinearLayout) view.findViewById(R.id.pop_layout6);
		LinearLayout pop_layout7 = (LinearLayout) view.findViewById(R.id.pop_layout7);
		LinearLayout pop_layout8 = (LinearLayout) view.findViewById(R.id.pop_layout8);
		LinearLayout pop_layout9 = (LinearLayout) view.findViewById(R.id.pop_layout9);
		LinearLayout pop_layout10 = (LinearLayout) view.findViewById(R.id.pop_layout10);
		LinearLayout pop_layout11 = (LinearLayout) view.findViewById(R.id.pop_layout11);
		LinearLayout pop_layout12 = (LinearLayout) view.findViewById(R.id.pop_layout12);

		layOutList.add(pop_layout1);
		layOutList.add(pop_layout2);
		layOutList.add(pop_layout3);
		layOutList.add(pop_layout4);
		layOutList.add(pop_layout5);
		layOutList.add(pop_layout6);
		layOutList.add(pop_layout7);
		layOutList.add(pop_layout8);
		layOutList.add(pop_layout9);
		layOutList.add(pop_layout10);
		layOutList.add(pop_layout11);
		layOutList.add(pop_layout12);

		TextView pop_text1 = (TextView) view.findViewById(R.id.pop_text1);
		TextView pop_text2 = (TextView) view.findViewById(R.id.pop_text2);
		TextView pop_text3 = (TextView) view.findViewById(R.id.pop_text3);
		TextView pop_text4 = (TextView) view.findViewById(R.id.pop_text4);
		TextView pop_text5 = (TextView) view.findViewById(R.id.pop_text5);
		TextView pop_text6 = (TextView) view.findViewById(R.id.pop_text6);
		TextView pop_text7 = (TextView) view.findViewById(R.id.pop_text7);
		TextView pop_text8 = (TextView) view.findViewById(R.id.pop_text8);
		TextView pop_text9 = (TextView) view.findViewById(R.id.pop_text9);
		TextView pop_text10 = (TextView) view.findViewById(R.id.pop_text10);
		TextView pop_text11 = (TextView) view.findViewById(R.id.pop_text11);
		TextView pop_text12 = (TextView) view.findViewById(R.id.pop_text12);

		textList.add(pop_text1);
		textList.add(pop_text2);
		textList.add(pop_text3);
		textList.add(pop_text4);
		textList.add(pop_text5);
		textList.add(pop_text6);
		textList.add(pop_text7);
		textList.add(pop_text8);
		textList.add(pop_text9);
		textList.add(pop_text10);
		textList.add(pop_text11);
		textList.add(pop_text12);

		totalSize = textList.size();

		final Button pop_button1 = (Button) view.findViewById(R.id.pop_button1);
		final Button pop_button2 = (Button) view.findViewById(R.id.pop_button2);
		final Button pop_button3 = (Button) view.findViewById(R.id.pop_button3);
		final Button pop_button4 = (Button) view.findViewById(R.id.pop_button4);
		final Button pop_button5 = (Button) view.findViewById(R.id.pop_button5);
		final Button pop_button6 = (Button) view.findViewById(R.id.pop_button6);
		final Button pop_button7 = (Button) view.findViewById(R.id.pop_button7);
		final Button pop_button8 = (Button) view.findViewById(R.id.pop_button8);
		final Button pop_button9 = (Button) view.findViewById(R.id.pop_button9);
		final Button pop_button10 = (Button) view.findViewById(R.id.pop_button10);
		final Button pop_button11 = (Button) view.findViewById(R.id.pop_button11);
		final Button pop_button12 = (Button) view.findViewById(R.id.pop_button12);

		pop_button1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(0);
			}
		});
		pop_button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(1);
			}
		});
		pop_button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(2);
			}
		});
		pop_button4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(3);
			}
		});
		pop_button5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(4);
			}
		});
		pop_button6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(5);
			}
		});
		pop_button7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(6);
			}
		});
		pop_button8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(7);
			}
		});
		pop_button9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(8);
			}
		});
		pop_button10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(9);
			}
		});
		pop_button11.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(10);
			}
		});
		pop_button12.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setSelectStatus(11);
			}
		});

		buttonList.add(pop_button1);
		buttonList.add(pop_button2);
		buttonList.add(pop_button3);
		buttonList.add(pop_button4);
		buttonList.add(pop_button5);
		buttonList.add(pop_button6);
		buttonList.add(pop_button7);
		buttonList.add(pop_button8);
		buttonList.add(pop_button9);
		buttonList.add(pop_button10);
		buttonList.add(pop_button11);
		buttonList.add(pop_button12);

		////////////////////////////////////////////////////////////////////

		passenger_sex = (Button) this.findViewById(R.id.passenger_sex);
		passenger_sex.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				arg0.getParent().requestDisallowInterceptTouchEvent(true);//
				// 通知父控件勿拦截本控件touch事件
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.d("TAG", "ACTION_DOWN.............");
					Log.d("TAG", "jiekuanyongtu.............");
					// showPopupWindow(arg0);
					if (pop.isShowing()) {
						// 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
						pop.dismiss();
					} else {
						// 显示窗口
						openSign = 1;
						int yongtuLength = sex.length;
						for (int i = 0; i < totalSize; i++) {
							if (i < yongtuLength) {
								TextView pop_text = textList.get(i);
								pop_text.setText(sex[i]);
								LinearLayout pop_layout = layOutList.get(i);
								pop_layout.setVisibility(View.VISIBLE);
							} else {
								LinearLayout pop_layout = layOutList.get(i);
								pop_layout.setVisibility(View.GONE);
							}
						}
						setSelectStatus(sex_select);
						pop.showAtLocation(arg0, Gravity.CENTER, 0, 0);
						pop.showAsDropDown(arg0);
					}
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("TAG", "ACTION_MOVE.............");
					break;
				case MotionEvent.ACTION_CANCEL:
					Log.d("TAG", "ACTION_CANCEL.............");
					break;
				case MotionEvent.ACTION_UP:
					Log.d("TAG", "ACTION_UP.............");
					break;
				default:
					break;
				}
				return false;
			}
		});

		passenger_industry = (Button) this.findViewById(R.id.passenger_industry);
		passenger_industry.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				arg0.getParent().requestDisallowInterceptTouchEvent(true);//
				// 通知父控件勿拦截本控件touch事件
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.d("TAG", "ACTION_DOWN.............");
					Log.d("TAG", "jiekuanyongtu.............");
					// showPopupWindow(arg0);
					if (pop.isShowing()) {
						// 隐藏窗口，如果设置了点击窗口外小时即不需要此方式隐藏
						pop.dismiss();
					} else {
						// 显示窗口
						openSign = 2;
						int yongtuLength = industry.length;
						for (int i = 0; i < totalSize; i++) {
							if (i < yongtuLength) {
								TextView pop_text = textList.get(i);
								pop_text.setText(industry[i]);
								LinearLayout pop_layout = layOutList.get(i);
								pop_layout.setVisibility(View.VISIBLE);
							} else {
								LinearLayout pop_layout = layOutList.get(i);
								pop_layout.setVisibility(View.GONE);
							}
						}
						setSelectStatus(industry_select);
						pop.showAtLocation(arg0, Gravity.CENTER, 0, 0);
						pop.showAsDropDown(arg0);
					}
					break;
				case MotionEvent.ACTION_MOVE:
					Log.d("TAG", "ACTION_MOVE.............");
					break;
				case MotionEvent.ACTION_CANCEL:
					Log.d("TAG", "ACTION_CANCEL.............");
					break;
				case MotionEvent.ACTION_UP:
					Log.d("TAG", "ACTION_UP.............");
					break;
				default:
					break;
				}
				return false;
			}
		});

		Button pop_queding = (Button) view.findViewById(R.id.pop_queding);
		pop_queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
			}
		});
	}

	public void setSelectStatus(int select) {
		if (select == -1) {
			for (int i = 0; i < totalSize; i++) {
				buttonList.get(i).setBackgroundResource(R.drawable.pop_unselect);
			}
			return;
		}
		switch (openSign) {
		case 1:
			sex_select = select;
			passenger_sex.setText(sex[sex_select]);
			break;
		case 2:
			industry_select = select;
			passenger_industry.setText(industry[industry_select]);
			break;
		}
		for (int i = 0; i < totalSize; i++) {
			if (i == select) {
				buttonList.get(i).setBackgroundResource(R.drawable.pop_select);
			} else {
				buttonList.get(i).setBackgroundResource(R.drawable.pop_unselect);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECT_A_PICTURE) {
			if (resultCode == RESULT_OK && null != data) {
				Log.i("zou", "---------------------------");
				Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
				switch (selectPhoto) {
				case 1:
					passenger_photo.setImageBitmap(bitmap);
					passenger_photoBitmap = bitmap;
					break;
				}
				Toast.makeText(PersonCenterXinXiActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
				Thread thread = new Thread(new UpdateStatusThread());
				thread.start();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PersonCenterXinXiActivity.this, "ȡ��ͷ������", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
		} else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
			if (resultCode == RESULT_OK && null != data) {
				// Log.i("zou", "4.4���ϵ�");
				mAlbumPicturePath = getPath(getApplicationContext(), data.getData());
				cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PersonCenterXinXiActivity.this, "ȡ��ͷ������", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
		} else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
			Log.i("zou", "4.4�����ϵ� RESULT_OK");

			Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
			switch (selectPhoto) {
			case 1:
				passenger_photo.setImageBitmap(bitmap);
				passenger_photoBitmap = bitmap;
				break;
			}
			Toast.makeText(PersonCenterXinXiActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
			Thread thread = new Thread(new UpdateStatusThread());
			thread.start();

		} else if (requestCode == TAKE_A_PICTURE) {
			Log.i("zou", "TAKE_A_PICTURE-resultCode:" + resultCode);

			if (resultCode == RESULT_OK) {
				cameraCropImageUri(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
			} else {
				Toast.makeText(PersonCenterXinXiActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
			}
			// if (resultCode == RESULT_OK) {
			// Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
			// switch (selectPhoto) {
			// case 1:
			// passenger_photo.setImageBitmap(bitmap);
			// passenger_photoBitmap = bitmap;
			// break;
			// }
			// Toast.makeText(PersonCenterXinXiActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
			// Thread thread = new Thread(new UpdateStatusThread());
			// thread.start();
			// } else {
			// Toast.makeText(PersonCenterXinXiActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
			// selectPhoto = 0;
			// }
		} else if (requestCode == SET_PICTURE) {
			Bitmap bitmap = null;
			if (resultCode == RESULT_OK && null != data) {
				bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
				switch (selectPhoto) {
				case 1:
					passenger_photo.setImageBitmap(bitmap);
					passenger_photoBitmap = bitmap;
					break;
				}
				Toast.makeText(PersonCenterXinXiActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
				Thread thread = new Thread(new UpdateStatusThread());
				thread.start();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PersonCenterXinXiActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			} else {
				Toast.makeText(PersonCenterXinXiActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
		} else if (requestCode == 8888) {
			Intent i = new Intent();
			setResult(9999, i);
			finish();
		}
	}

	/**
	 * <br>
	 * ���ܼ���:�ü�ͼƬ����ʵ��---------------------- ��� <br>
	 * ������ϸ����: <br>
	 * ע��:
	 */
	private void cropImageUri() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, SELECT_A_PICTURE);
	}

	/**
	 * <br>
	 * ���ܼ���:4.4���ϲü�ͼƬ����ʵ��---------------------- ��� <br>
	 * ������ϸ����: <br>
	 * ע��:
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void selectImageUriAfterKikat() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(intent, SELECET_A_PICTURE_AFTER_KIKAT);
	}

	/**
	 * <br>
	 * ���ܼ���:�ü�ͼƬ����ʵ��----------------------��� <br>
	 * ������ϸ����: <br>
	 * ע��:
	 * 
	 * @param uri
	 */
	private void cameraCropImageUri(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
		intent.putExtra("scale", true);
		// if (mIsKitKat) {
		// intent.putExtra("return-data", true);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		// } else {
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		// }
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, SET_PICTURE);
	}

	/**
	 * <br>
	 * ���ܼ���: 4.4�����ϸĶ���ü�ͼƬ����ʵ�� --------------------��� <br>
	 * ������ϸ����: <br>
	 * ע��:
	 * 
	 * @param uri
	 */
	private void cropImageUriAfterKikat(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/jpeg");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", 640);
		// intent.putExtra("outputY", 640);
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
		intent.putExtra("scale", true);
		// intent.putExtra("return-data", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, SET_ALBUM_PICTURE_KITKAT);
	}

	/**
	 * <br>
	 * ���ܼ���: <br>
	 * ������ϸ����: <br>
	 * ע��:
	 * 
	 * @param uri
	 * @return
	 */
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	/**
	 * <br>
	 * ���ܼ���:4.4�����ϻ�ȡͼƬ�ķ��� <br>
	 * ������ϸ����: <br>
	 * ע��:
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

//	@Override
//	public void refresh() {
////		Thread thread = new Thread(new userRegist());
////		thread.start();
//
//		initData(0);
//	}

	class UpdateStatusThread implements Runnable {
		public void run() {
			switch (selectPhoto) {
			case 1:
				uploadFile(passenger_photoBitmap);
				break;
			}
		}
	}

	/* �ϴ��ļ���Server�ķ��� */
	private void uploadFile(Bitmap bitmap) {

		uploading = true;
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		SharedPreferences settings = getSharedPreferences("settings", 0);

		// String filename = "/" + settings.getString("passenger_id", "") + "/" + "zhuxuele.jpg";

		String filename = System.currentTimeMillis() + "_" + (int) (Math.random() * 100000 + 1) + ".jpg";

		photoOrVideo = filename;

		try {
			String urlString = Constants.image_url + settings.getString("passenger_id", "");
			// String urlString = "http://192.168.0.102/qichen/upload_img.php?app=xingxingdai&loan_area=4&loan_id=269";
			Log.d("123================", urlString);
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* ����Input��Output����ʹ��Cache */
			con.setReadTimeout(10000 /* milliseconds */);
			con.setConnectTimeout(15000 /* milliseconds */);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* ���ô��͵�method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + filename + "\"" + end);
			ds.writeBytes(end);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
			byte[] array = out.toByteArray();
			ds.write(array);
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			;
			ds.flush();
			/* ȡ��Response���� */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			String string = b.toString().trim();
			System.out.println("返回值:" + string);
			JSONObject jsonObject = new JSONObject(string.substring(string.indexOf("{")));
			String home_result = jsonObject.getString("result");
			if (home_result.equals("1")) {
				selectPhoto = 0;
				Toast.makeText(PersonCenterXinXiActivity.this, "照片上传成功！", Toast.LENGTH_SHORT).show();
			}
			String home_msg = "-------------";
			System.out.println("返回值:" + home_result + home_msg);
			ds.close();
			con.disconnect();
		} catch (Exception e) {
			System.out.println("异常：" + e);
		}
	}

//	class sendXinXi extends Thread implements Runnable {
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			super.run();
//			try {
//
//				EditText passenger_nickname = (EditText) findViewById(passenger_nickname);
//				EditText passenger_job = (EditText) findViewById(passenger_job);
//				JSONObject msgObject = new JSONObject();
//				msgObject.put("action", "sendXinXi");
//				SharedPreferences settings = getSharedPreferences("settings", 0);
//				msgObject.put("passenger_telephone", settings.getString("passenger_telephone", ""));
//				msgObject.put("passenger_nickname", passenger_nickname.getText().toString());
//				msgObject.put("passenger_sex", passenger_sex.getText().toString());
//				msgObject.put("passenger_industry", passenger_industry.getText().toString());
//				msgObject.put("passenger_job", passenger_job.getText().toString());
//				// 上传的参数
//				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
//				System.out.println(msgObject.toString());
//				// 返回的加密串
//				String str = Utils.postUrlData(Constants.new_url, para);
//				// 构建handler
//				Message msg = getYanZhengMaHandler.obtainMessage();
//				Bundle bundle = new Bundle();
//				bundle.putString("getYanZhengMa", str);
//				msg.setData(bundle);
//				getYanZhengMaHandler.sendMessage(msg);
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

	/**
	 * 界面handler
	 */
	Handler getYanZhengMaHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("getYanZhengMa"));
				JSONObject obj = new JSONObject(str);
				Log.i("str", str);
				LogUtils.i("发送后台的数据回调"+obj);
				// 有数据
				if (obj.getString("result").equals("1")) {
					Toast.makeText(PersonCenterXinXiActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
					quitHandler.postDelayed(quitRunnable, 2000); // 开始Timer
				} else {
					Toast.makeText(PersonCenterXinXiActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	
	
//	class userRegist extends Thread implements Runnable {
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			super.run();
//			try {
//				SharedPreferences settings = getSharedPreferences("settings", 0);
//				JSONObject msgObject = new JSONObject();
//				msgObject.put("action", "getXinXi");
//				msgObject.put("passenger_telephone", settings.getString("passenger_telephone", ""));
//				// 上传的参数
//				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
//				System.out.println(msgObject.toString());
//				// 返回的加密串
//				String str = Utils.postUrlData(Constants.new_url, para);
//				// 构建handler
//				Message msg = userRegistHandler.obtainMessage();
//				Bundle bundle = new Bundle();
//				bundle.putString("userRegist", str);
//				msg.setData(bundle);
//				userRegistHandler.sendMessage(msg);
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

//	/**
//	 * 界面handler
//	 */
//	Handler userRegistHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			try {
//				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("userRegist"));
//				JSONObject obj = new JSONObject(str);
//				Log.i("str", str);
//				LogUtils.i("原始数据"+obj);
//				// 有数据
//				if (obj.getString("result").equals("1")) {
//					JSONObject obj_ = new JSONObject(obj.getString("data"));
//
//					if (!obj_.getString("passenger_photo").equals("")) {
//						bmpManager.loadBitmap(Constants.fangwen_image_url + obj_.getString("passenger_photo"), passenger_photo);
//					}
//					//passenger_photo.seti
//					LogUtils.i("个人信息回来的网络数据"+obj_);
//
//					EditText passenger_nickname = (EditText) findViewById(passenger_nickname);
//					passenger_nickname.setText(obj_.getString("passenger_nickname"));
//
////					Button passenger_sex = (Button) findViewById(R.id.passenger_sex);
////					passenger_sex.setText(obj_.getString("passenger_sex"));
////
////					Button passenger_industry = (Button) findViewById(R.id.passenger_industry);
////					passenger_industry.setText(obj_.getString("passenger_industry"));
////
////					EditText passenger_job = (EditText) findViewById(R.id.passenger_job);
////					passenger_job.setText(obj_.getString("passenger_job"));
//
//				} else {
//					Toast.makeText(PersonCenterXinXiActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	};

}
