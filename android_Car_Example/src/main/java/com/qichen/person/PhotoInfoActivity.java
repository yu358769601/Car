package com.qichen.person;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.qichen.ruida.R;
import com.rongzhiheng.util.AES;
import com.rongzhiheng.util.BitmapManager;
import com.rongzhiheng.util.Constants;
import com.rongzhiheng.util.Utils;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoInfoActivity extends Activity {

	private BitmapManager bmpManager;

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

	private int selectType;
	private boolean showXinxi;

	private String[] showType_array = { "jichuxinxi", "wanshanxinxi", "taobao", "zhima", "huabei", "jingdong", "xiaobai" };
	private String[] upLoadType_array = { "jichuxinxi_photo", "wanshanxinxi_photo", "taobao_photo", "zhima_photo", "huabei_photo", "jingdong_photo", "xiaobai_photo" };
	private String[] upLoadName_array = { "jichuxinxi_photo", "wanshanxinxi_photo", "taobao_photo", "zhima_photo", "huabei_photo", "jingdong_photo", "xiaobai_photo" };

	private ImageView photo_imageView1;
	private Bitmap photo_imageView1Bitmap;

	boolean firstPhoto, secondPhoto, thirdPhoto;

	private String photoOrVideo = "";
	private int selectPhoto = 0;

	private String type = "";

	public boolean uploading;

	private Handler quitHandler = new Handler();

	private Runnable quitRunnable = new Runnable() {

		public void run() {

			Intent mIntent = new Intent("tishengeduSuccess");
			// mIntent.putExtra("yaner", "发送广播，相当于在这里传送数据");
			// 发送广播
			sendBroadcast(mIntent);
			setResult(12345);
			quitHandler.removeCallbacks(quitRunnable); // 停止Timer
			finish();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.photoinfo_activity);

		bmpManager = new BitmapManager(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));

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
			e.printStackTrace();
		}

		// Bundle bundle = this.getIntent().getExtras(); // 传入的参数

		// selectType = bundle.getInt("selectType");

		TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);

		Button relativeLayout_title_leftbutton = (Button) findViewById(R.id.relativeLayout_title_leftbutton);
		relativeLayout_title_leftbutton.setVisibility(View.VISIBLE);
		Button relativeLayout_title_rightbutton = (Button) findViewById(R.id.relativeLayout_title_rightbutton);
		relativeLayout_title_rightbutton.setVisibility(View.GONE);

		relativeLayout_title_leftbutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		TextView shuoming = (TextView) findViewById(R.id.shuoming);
		// ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		// ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
		// TextView first_title_xinxi = (TextView)
		// findViewById(R.id.first_title_xinxi);
		// TextView first_reason_xinxi = (TextView)
		// findViewById(R.id.first_reason_xinxi);
		// TextView second_title_xinxi = (TextView)
		// findViewById(R.id.second_title_xinxi);
		// TextView second_reason_xinxi = (TextView)
		// findViewById(R.id.second_reason_xinxi);
		//
		// ImageView ic_info_ok = (ImageView) findViewById(R.id.ic_info_ok);
		// ImageView ic_info_ok1 = (ImageView) findViewById(R.id.ic_info_ok1);

		switch (selectType) {
		case 1:
			relativeLayout_title_titleview.setText("基础信息");
			shuoming.setText("上传填写资料的照片");
			// imageView1.setVisibility(View.VISIBLE);
			// imageView2.setVisibility(View.VISIBLE);
			// first_title_xinxi.setText("上传照片");
			// first_reason_xinxi.setText("如果您在线下填写了资料信息，拍照上传即可");
			// second_title_xinxi.setText("在线填写");
			// second_reason_xinxi.setText("在线填写您的信息资料，提交申请");
			// if ("1".equals(bundle.get("jichu_photo"))) {
			// ic_info_ok.setVisibility(View.VISIBLE);
			// showXinxi_photo = true;
			// }
			// if ("1".equals(bundle.get("jichu_xinxi"))) {
			// ic_info_ok1.setVisibility(View.VISIBLE);
			// showXinxi = true;
			// }
			break;
		case 2:

			relativeLayout_title_titleview.setText("完善信息");
			shuoming.setText("上传填写资料的照片");
			// imageView1.setVisibility(View.VISIBLE);
			// imageView2.setVisibility(View.VISIBLE);
			// first_title_xinxi.setText("上传照片");
			// first_reason_xinxi.setText("如果您在线下填写了资料信息，拍照上传即可");
			// second_title_xinxi.setText("在线填写");
			// second_reason_xinxi.setText("在线填写您的信息资料，提交申请");
			// if ("1".equals(bundle.get("wanshan_photo"))) {
			// ic_info_ok.setVisibility(View.VISIBLE);
			// showXinxi_photo = true;
			// }
			// if ("1".equals(bundle.get("wanshan_xinxi"))) {
			// ic_info_ok1.setVisibility(View.VISIBLE);
			// showXinxi = true;
			// }
			break;
		case 3:
			relativeLayout_title_titleview.setText("淘宝收货地址");
			shuoming.setText("上传相关资料的照片");
			// first_title_xinxi.setText("征信报告");
			// first_reason_xinxi.setText("由中国人民银行征信中心出具的记载个人信用信息的记录");
			// second_title_xinxi.setText("居住证明");
			// second_reason_xinxi.setText("租房合同、房产证明或购房合同拍照上传");
			break;
		case 4:
			relativeLayout_title_titleview.setText("芝麻信用");
			break;
		case 5:
			relativeLayout_title_titleview.setText("花呗");
			break;
		case 6:
			relativeLayout_title_titleview.setText("京东白条");
			break;
		case 7:
			relativeLayout_title_titleview.setText("小白信用");
			break;
		}

		// if ("1".equals(bundle.getString("showXinXi"))) {
		// showXinxi = true;
		// Thread thread = new Thread(new getUpLoadPhoto());
		// thread.start();
		// }

		Thread thread = new Thread(new userRegist());
		thread.start();

		photo_imageView1 = (ImageView) findViewById(R.id.photo_imageView1);
		photo_imageView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Thread thread = new Thread(new
				// UpdateStatusMp4Thread());
				// thread.start();

				if (showXinxi) {
					return;
				}

				if (selectPhoto != 0) {
					Toast.makeText(PhotoInfoActivity.this, "照片上传中~", Toast.LENGTH_SHORT).show();
					return;
				}

				selectPhoto = 1;
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
				startActivityForResult(intent, TAKE_A_PICTURE);
				Log.i("zou", "TAKE_A_PICTURE");
			}
		});

		Button tijiaoxinxi = (Button) findViewById(R.id.tijiaoxinxi);
		tijiaoxinxi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (type.equals("")) {
					Toast.makeText(PhotoInfoActivity.this, "请先上传照片~", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(PhotoInfoActivity.this, "提交信息中，请稍候~", Toast.LENGTH_SHORT).show();
					// Thread thread = new Thread(new upLoadPhoto());
					// thread.start();
				}
			}
		});

		if (!showXinxi) {
			tijiaoxinxi.setVisibility(View.VISIBLE);
		}

		// RelativeLayout relativeLayout1 = (RelativeLayout)
		// findViewById(R.id.relativeLayout1);
		// relativeLayout1.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(PhotoInfoActivity.this, "正在获取提升额度信息1~",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		//
		// RelativeLayout relativeLayout2 = (RelativeLayout)
		// findViewById(R.id.relativeLayout2);
		// relativeLayout2.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// switch (selectType) {
		// case 1:
		// Intent intent = new Intent(PhotoInfoActivity.this,
		// JiChuXinXiActivity.class);
		// if (showXinxi) {
		// intent.putExtra("showXinXi", "1");
		// } else {
		// intent.putExtra("showXinXi", "0");
		// }
		// startActivity(intent);
		// break;
		// case 2:
		//
		// break;
		// case 3:
		//
		// break;
		// }
		//
		//
		// }
		// });

		// SharedPreferences settings = getSharedPreferences("settings", 0);
		// TextView userName_edu = (TextView) findViewById(R.id.userName_edu);
		// userName_edu.setText(settings.getString("username", ""));
		// TextView xinyongedu = (TextView) findViewById(R.id.xinyongedu);
		// xinyongedu.setText(settings.getString("total", ""));
		//
		// Button part1_tianxie = (Button) findViewById(R.id.part1_tianxie);
		// part1_tianxie.setEnabled(false);
		// part1_tianxie.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(SelectInfoActivity.this,
		// JiChuXinXiActivity.class);
		// Button part1_tianxie = (Button) findViewById(R.id.part1_tianxie);
		// if (part1_tianxie.getText().equals("")) {
		// intent.putExtra("showXinXi", "1");
		// } else {
		// intent.putExtra("showXinXi", "0");
		// }
		// startActivity(intent);
		// }
		// });
		//
		// Button part2_tianxie = (Button) findViewById(R.id.part2_tianxie);
		// part2_tianxie.setEnabled(false);
		// part2_tianxie.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // Intent intent = new Intent(TiShengEDuActivity.this,
		// // WanShanXinXiActivity.class);
		// Intent intent = new Intent(SelectInfoActivity.this,
		// WanShanXinXi_WebViewActivity.class);
		// startActivity(intent);
		// }
		// });
		//
		// Button part3_tianxie = (Button) findViewById(R.id.part3_tianxie);
		// part3_tianxie.setEnabled(false);
		// part3_tianxie.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(SelectInfoActivity.this,
		// ShangChuanZiLiaoActivity.class);
		// Button part3_tianxie = (Button) findViewById(R.id.part3_tianxie);
		// if (part3_tianxie.getText().equals("")) {
		// intent.putExtra("showXinXi", "1");
		// } else {
		// intent.putExtra("showXinXi", "0");
		// }
		// startActivity(intent);
		// }
		// });
		//
		// Toast.makeText(SelectInfoActivity.this, "正在获取提升额度信息~",
		// Toast.LENGTH_SHORT).show();
	}

	class userRegist extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				SharedPreferences settings = getSharedPreferences("settings", 0);
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "userRegist");
				msgObject.put("passenger_telephone", settings.getString("passenger_telephone", ""));
				msgObject.put("passenger_from", "Android");
				// 上传的参数
				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
				System.out.println(msgObject.toString());
				// 返回的加密串
				String str = Utils.postUrlData(Constants.new_url, para);
				// 构建handler
				Message msg = userRegistHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("userRegist", str);
				msg.setData(bundle);
				userRegistHandler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 界面handler
	 */
	Handler userRegistHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("userRegist"));
				JSONObject obj = new JSONObject(str);
				Log.i("str", str);
				// 有数据
				if (obj.getString("result").equals("1")) {
					JSONObject obj_ = new JSONObject(obj.getString("data"));
					SharedPreferences settings = getSharedPreferences("settings", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("passenger_id", obj_.getString("passenger_id"));
					editor.putString("passenger_telephone", settings.getString("passenger_telephone", ""));
					editor.putString("passenger_nickname", obj_.getString("passenger_nickname"));
					editor.putString("passenger_photo", obj_.getString("passenger_photo"));
					editor.putString("passenger_sex", obj_.getString("passenger_sex"));
					editor.putString("passenger_industry", obj_.getString("passenger_industry"));
					editor.putString("passenger_job", obj_.getString("passenger_job"));
					editor.putString("passenger_money", obj_.getString("passenger_money"));
					editor.commit();

					bmpManager.loadBitmap(Constants.fangwen_image_url + obj_.getString("passenger_photo"), photo_imageView1);
					
//					bmpManager.loadBitmap(obj_.getString("imageUrl"), photo_imageView1);

					// String url = Constants.fangwen_image_url + obj_.getString("passenger_photo");
					// Bitmap bitmap = getHttpBitmap(url);
					// personcenter_photo.setImageBitmap(bitmap);

				} else {
//					Toast.makeText(PersonCenterActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	class getUpLoadPhoto extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {

				SharedPreferences settings = getSharedPreferences("settings", 0);
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "getUpLoadPhoto");
				msgObject.put("userId", settings.getString("userId", ""));
				msgObject.put("type", showType_array[selectType - 1]);
				// 上传的参数
				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
				System.out.println(msgObject.toString());
				// 返回的加密串
				String str = Utils.postUrlData(Constants.new_url, para);
				// 构建handler
				Message msg = getUpLoadPhotoHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("getUpLoadPhoto", str);
				msg.setData(bundle);
				getUpLoadPhotoHandler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 界面handler
	 */
	Handler getUpLoadPhotoHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("getUpLoadPhoto"));
				JSONObject obj = new JSONObject(str);

				Log.d("str=====", str);
				if (obj.getString("result").equals("1")) {
					JSONObject obj_ = new JSONObject(obj.getString("data"));
					bmpManager.loadBitmap(obj_.getString("imageUrl"), photo_imageView1);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	class upLoadPhoto extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {

				SharedPreferences settings = getSharedPreferences("settings", 0);
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "upLoadPhoto");
				msgObject.put("userId", settings.getString("userId", ""));
				msgObject.put(upLoadType_array[selectType - 1], upLoadName_array[selectType - 1]);
				// 上传的参数
				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
				System.out.println(msgObject.toString());
				// 返回的加密串
				String str = Utils.postUrlData(Constants.new_url, para);
				// 构建handler
				Message msg = upLoadPhotoHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("upLoadPhoto", str);
				msg.setData(bundle);
				upLoadPhotoHandler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 界面handler
	 */
	Handler upLoadPhotoHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("upLoadPhoto"));
				JSONObject obj = new JSONObject(str);

				Log.d("str=====", str);
				if (obj.getString("result").equals("1")) {
					Toast.makeText(PhotoInfoActivity.this, "信息提交成功！", Toast.LENGTH_SHORT).show();
					quitHandler.postDelayed(quitRunnable, 2000); // 开始Timer
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// if (selectType == 3) {
		// getInfo = false;
		// Thread thread = new dianOther();
		// thread.start();
		// }
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
					photo_imageView1.setImageBitmap(bitmap);
					photo_imageView1Bitmap = bitmap;
					break;
				// case 2:
				// zhuxuele_shenfenzheng_fanmian.setImageBitmap(bitmap);
				// zhuxuele_shenfenzheng_fanmianBitmap = bitmap;
				// break;
				// case 3:
				// zhuxuele_shenfenzheng_shouchi.setImageBitmap(bitmap);
				// zhuxuele_shenfenzheng_shouchiBitmap = bitmap;
				// break;
				}
				Toast.makeText(PhotoInfoActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
				Thread thread = new Thread(new UpdateStatusThread());
				thread.start();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PhotoInfoActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
		} else if (requestCode == SELECET_A_PICTURE_AFTER_KIKAT) {
			if (resultCode == RESULT_OK && null != data) {
				// Log.i("zou", "4.4���ϵ�");
				mAlbumPicturePath = getPath(getApplicationContext(), data.getData());
				cropImageUriAfterKikat(Uri.fromFile(new File(mAlbumPicturePath)));
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PhotoInfoActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
		} else if (requestCode == SET_ALBUM_PICTURE_KITKAT) {
			Log.i("zou", "4.4�����ϵ� RESULT_OK");

			Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, TMP_IMAGE_FILE_NAME)));
			switch (selectPhoto) {
			case 1:
				photo_imageView1.setImageBitmap(bitmap);
				photo_imageView1Bitmap = bitmap;
				break;
			// case 2:
			// zhuxuele_shenfenzheng_fanmian.setImageBitmap(bitmap);
			// zhuxuele_shenfenzheng_fanmianBitmap = bitmap;
			// break;
			// case 3:
			// zhuxuele_shenfenzheng_shouchi.setImageBitmap(bitmap);
			// zhuxuele_shenfenzheng_shouchiBitmap = bitmap;
			// break;
			}
			Toast.makeText(PhotoInfoActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
			Thread thread = new Thread(new UpdateStatusThread());
			thread.start();
			// Log.i("zou", "4.4�����ϵ� RESULT_OK");
			// Bitmap bitmap = data.getParcelableExtra("data");
			// mAcountHeadIcon.setImageBitmap(bitmap);
		} else if (requestCode == TAKE_A_PICTURE) {
			Log.i("zou", "TAKE_A_PICTURE-resultCode:" + resultCode);

			// Toast.makeText(TakePhotoActivity.this, "---"+resultCode,
			// Toast.LENGTH_SHORT).show();
			// Button button = (Button)findViewById(R.id.next);
			// button.setText(resultCode+"=t");
			// cameraCropImageUri(Uri.fromFile(new File(IMGPATH,
			// IMAGE_FILE_NAME)));

			if (resultCode == RESULT_OK) {
				// cameraCropImageUri(Uri.fromFile(new File(IMGPATH,
				// IMAGE_FILE_NAME)));
				Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
				switch (selectPhoto) {
				case 1:
					photo_imageView1.setImageBitmap(bitmap);
					photo_imageView1Bitmap = bitmap;
					break;
				// case 2:
				// zhuxuele_shenfenzheng_fanmian.setImageBitmap(bitmap);
				// zhuxuele_shenfenzheng_fanmianBitmap = bitmap;
				// break;
				// case 3:
				// zhuxuele_shenfenzheng_shouchi.setImageBitmap(bitmap);
				// zhuxuele_shenfenzheng_shouchiBitmap = bitmap;
				// break;
				}
				Toast.makeText(PhotoInfoActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
				Thread thread = new Thread(new UpdateStatusThread());
				thread.start();
			} else {
				Toast.makeText(PhotoInfoActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
		} else if (requestCode == SET_PICTURE) {
			// ���յ�����ͷ�� �����ǰ汾
			// Log.i("zou", "SET_PICTURE-resultCode:" + resultCode);
			Bitmap bitmap = null;
			// if (mIsKitKat) { //�߰汾
			// if (null != data) {
			// bitmap = data.getParcelableExtra("data");
			// showLoading();
			// mAccountControl.resetGoUserIcon(bitmap2byte(bitmap), this);
			// } else { //�߰汾����ͨ��data����ȡ��ͼƬ��ݵľ���uri
			// if (resultCode == RESULT_OK) {
			// bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH,
			// IMAGE_FILE_NAME)));
			// showLoading();
			// mAccountControl.resetGoUserIcon(bitmap2byte(bitmap), this);
			// }
			// }
			// } else { //�Ͱ汾
			if (resultCode == RESULT_OK && null != data) {
				bitmap = decodeUriAsBitmap(Uri.fromFile(new File(IMGPATH, IMAGE_FILE_NAME)));
				switch (selectPhoto) {
				case 1:
					photo_imageView1.setImageBitmap(bitmap);
					photo_imageView1Bitmap = bitmap;
					break;
				// case 2:
				// zhuxuele_shenfenzheng_fanmian.setImageBitmap(bitmap);
				// zhuxuele_shenfenzheng_fanmianBitmap = bitmap;
				// break;
				// case 3:
				// zhuxuele_shenfenzheng_shouchi.setImageBitmap(bitmap);
				// zhuxuele_shenfenzheng_shouchiBitmap = bitmap;
				// break;
				}
				Toast.makeText(PhotoInfoActivity.this, "正在上传照片", Toast.LENGTH_SHORT).show();
				Thread thread = new Thread(new UpdateStatusThread());
				thread.start();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(PhotoInfoActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			} else {
				Toast.makeText(PhotoInfoActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
				selectPhoto = 0;
			}
			// }
		}

		// if (requestCode == 1 && resultCode == Activity.RESULT_FIRST_USER) {
		// if (requestCode == 4444) {
		// // if (data != null) {
		// // Toast.makeText(ZhuXueLeWangQianZiLiaoActivity.this, "回来了",
		// // Toast.LENGTH_SHORT).show();
		//
		// Uri uri =
		// Uri.parse(Environment.getExternalStorageDirectory().getPath() +
		// "/zhuxueleshenqing.3gp");
		// videoView.setMediaController(new MediaController(this));
		// videoView.setVideoURI(uri);
		//
		// video_play.setVisibility(View.VISIBLE);
		// videoView.setVisibility(View.VISIBLE);
		//
		// videoView.setBackgroundColor(0);
		// RelativeLayout shipin_linearLayout = (RelativeLayout)
		// findViewById(R.id.shipin_linearLayout);
		// shipin_linearLayout.setBackgroundColor(0);
		//
		// zhuxuele_button_luzhishipin.setText("上传完成");
		//
		// SharedPreferences settings = getSharedPreferences("settings", 0);
		// video = "/" + settings.getString("userId", "")
		// + "/zhuxuele_video1.3gp";
		//
		// // }
		// }
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
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
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

	class UpdateStatusThread implements Runnable {
		public void run() {
			switch (selectPhoto) {
			case 1:
				type = upLoadType_array[selectType - 1];
				uploadFile(photo_imageView1Bitmap);
				break;
			// case 2:
			// type = "fanmian";
			// uploadFile(zhuxuele_shenfenzheng_fanmianBitmap);
			// break;
			// case 3:
			// type = "shouchi";
			// uploadFile(zhuxuele_shenfenzheng_shouchiBitmap);
			// break;
			}
		}
	}

	/* �ϴ��ļ���Server�ķ��� */
	private void uploadFile(Bitmap bitmap) {

		uploading = true;
		// if (StringUtils.isEmpty(PUser.imagename)||bitmap==null) {
		// if (StringUtils.isEmpty(PUser.headimg)) {
		// putContent(PUser.headimg);
		// } else {
		// putContent(PUser.headimg.substring(PUser.headimg.lastIndexOf("/") +
		// 1));
		// }
		//
		// } else {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		String name = "";
		switch (selectPhoto) {
		case 1:
			name = upLoadName_array[selectType - 1];
			break;
		case 2:
			name = "shenfenzheng_fanmian";
			break;
		case 3:
			name = "shenfenzheng_shouchi";
			break;
		}

		SharedPreferences settings = getSharedPreferences("settings", 0);

		// String filename =
		// "/"+settings.getString("userId","")+"/"+System.currentTimeMillis() +
		// "_" + (int)(Math.random()*100000+1) + ".jpg";

		String filename = "/" + settings.getString("userId", "") + "/" + "shopping_" + name + ".jpg";

		photoOrVideo = filename;

		// SharedPreferences settings = getSharedPreferences("store", 0);
		// SharedPreferences.Editor editor = settings.edit();
		// editor.putString("photo",filename);
		// editor.commit();

		try {
			String urlString = settings.getString("uploadUrl", "") + "shopping&time=" + settings.getString("time", "") + "&userId=" + settings.getString("userId", "");
			// URL url = new URL(com.pailife.info.Constants.image_url);
			// "http://dqtg.mashangyouli.com/admin/interface/uploadhead.php"
			// URL url = new URL(Constants.image_url +
			// settings.getString("userId", ""));
			URL url = new URL(urlString);
			// URL url = new URL("https://www.vivibank.com/upload/upload.php");
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

			// StrictMode.setThreadPolicy(new
			// StrictMode.ThreadPolicy.Builder()
			// .detectDiskReads().detectDiskWrites().detectNetwork()
			// .penaltyLog().build());
			// StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
			// .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
			// .build());
			/* ����DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			// ds.writeBytes("Content-Disposition: form-data; " +
			// "name=\"file1\";filename=\"" + PUser.imagename
			// + "\"" + end);
			ds.writeBytes("Content-Disposition: form-data; " + "name=\"file\";filename=\"" + filename + "\"" + end);
			ds.writeBytes(end);
			/* ȡ���ļ���FileInputStream */
			// FileInputStream fStream = new FileInputStream(PUser.uploadfile);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
			byte[] array = out.toByteArray();
			ds.write(array);
			/* ����ÿ��д��1024bytes */
			// int bufferSize = 1024;
			// byte[] buffer = new byte[bufferSize];
			// int length = -1;
			// /* ���ļ���ȡ����������� */
			// while ((length = fStream.read(buffer)) != -1) {
			// /* ������д��DataOutputStream�� */
			// ds.write(buffer, 0, length);
			// }
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			// fStream.close();
			ds.flush();
			/* ȡ��Response���� */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* ��Response��ʾ��Dialog */
			// showDialog("�ϴ��ɹ�" + b.toString().trim());
			// PUser.photorequset = b.toString().trim();
			String string = b.toString().trim();
			JSONObject jsonObject = new JSONObject(string.substring(string.indexOf("{")));
			String home_result = jsonObject.getString("result");
			if (home_result.equals("1")) {
				// Thread thread = new Thread(new caijixinxi());
				selectPhoto = 0;
				Toast.makeText(PhotoInfoActivity.this, "照片上传成功！", Toast.LENGTH_SHORT).show();
				// Thread thread = new Thread(new upLoadPhoto());
				// thread.start();
			}
			// String home_msg = jsonObject.getString("data");
			String home_msg = "-------------";
			// Toast.makeText(Pailife_PersonCenter.this,
			// "����ֵ"+home_result+home_msg, Toast.LENGTH_SHORT).show();
			System.out.println("返回值:" + home_result + home_msg);
			/* �ر�DataOutputStream */
			ds.close();
			con.disconnect();
			// putContent(home_msg);
		} catch (Exception e) {
			// Toast.makeText(Pailife_PersonCenter.this, "ͼƬ�ϴ�ʧ��",
			// Toast.LENGTH_SHORT).show();
			System.out.println("异常：" + e);
			// handle.sendEmptyMessage(10);
		}
		// }
	}

	class caijixinxi extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {

				SharedPreferences settings = getSharedPreferences("settings", 0);
				JSONObject msgObject = new JSONObject();
				msgObject.put("action", "online_apply");
				msgObject.put("userId", settings.getString("userId", ""));
				msgObject.put("type", type);
				msgObject.put("name", photoOrVideo);

				// 上传的参数
				String para = "para=" + AES.encrypt(Constants.key, Constants.iv, msgObject.toString());
				System.out.println(msgObject.toString());
				// 返回的加密串
				String str = Utils.postUrlData(Constants.new_url, para);
				// 构建handler
				Message msg = caijixinxiHandler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putString("caijixinxiHandler", str);
				msg.setData(bundle);
				caijixinxiHandler.sendMessage(msg);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 界面handler
	 */
	Handler caijixinxiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			try {
				String str = AES.decrypt(Constants.key, Constants.iv, msg.getData().getString("caijixinxiHandler"));
				JSONObject obj = new JSONObject(str);
				Log.d("caijixinxiHandler===", str);
				if (obj.getString("result").equals("1")) {
					if (selectPhoto != 0) {
						switch (selectPhoto) {
						case 1:
							firstPhoto = true;
							break;
						case 2:
							secondPhoto = true;
							break;
						case 3:
							thirdPhoto = true;
							break;
						}
						selectPhoto = 0;
						Toast.makeText(PhotoInfoActivity.this, "照片上传成功！", Toast.LENGTH_SHORT).show();
					} else {
						if (type.equals("video1")) {

						} else if (type.equals("video2")) {

						}
						Toast.makeText(PhotoInfoActivity.this, "视频上传成功！", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(PhotoInfoActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
					selectPhoto = 0;
				}

				uploading = false;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
