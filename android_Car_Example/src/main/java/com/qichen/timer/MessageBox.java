//package com.qichen.timer;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.TextView;
//
//import com.qichen.ruida.R;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class MessageBox {
//	public static boolean b = true;
//	private static Timer Timer = new Timer();
//
//	/**
//	 * 单纯的提示
//	 * */
////	public static void promptDialog(String msg, Context context) {
////		try {
////			promptDialog("确定", "提示", msg, View.VISIBLE, context,
////					new OnClickListener() {
////
////						@Override
////						public void onClick(View v) {
////							((Dialog) v.getTag()).dismiss();
////						}
////					}, false);
////		} catch (Exception e) {
////		}
////	}
//
//	/**
//	 * 单纯的提示(全局性)
//	 *
//	 * @param isSystem
//	 *            是否是全局性的弹出框
//	 * */
////	public static void promptDialog(String msg, Context context,
////			boolean isSystem) {
////		try {
////			promptDialog("确定", "提示", msg, View.VISIBLE, context,
////					new OnClickListener() {
////
////						@Override
////						public void onClick(View v) {
////							((Dialog) v.getTag()).dismiss();
////						}
////					}, isSystem);
////		} catch (Exception e) {
////		}
////	}
//
//	/**
//	 * 提示，点击确认执行某些动作
//	 * */
////	public static Dialog promptDialog(String msg, Context context,
////			OnClickListener clicklistener) {
////
////		return promptDialog("确定", "提示", msg, View.VISIBLE, context,
////				clicklistener, false);
////	}
//
//	/**
//	 * 提示，点击确认执行某些动作
//	 * */
////	public static void promptDialog(String btnstr, String msg, Context context,
////			OnClickListener clicklistener) {
////		try {
////			promptDialog(btnstr, "提示", msg, View.VISIBLE, context,
////					clicklistener, false);
////		} catch (Exception e) {
////		}
////	}
//
//	/**
//	 * 提示，点击确认执行某些动作
//	 * */
////	public static void promptUpdateDialog(String btnstr, String msg,
////			Context context, OnClickListener clicklistener) {
////		try {
////			promptDialog(btnstr, "有可用的新版本", msg, View.VISIBLE, context,
////					clicklistener, false);
////		} catch (Exception e) {
////		}
////	}
//
//	/**
//	 * 单个按钮弹出框,自定义按钮文字,自定义标题,自定义内容,自定义点击事件
//	 * */
////	public static Dialog promptDialog(String btnstr, String title, String msg,
////			int titleVisible, Context context,
////			OnClickListener clicklistener, boolean isSystem) {
////		return promptTwoDialog(R.layout.layout_dialog_stop_track, "", btnstr,
////				title, msg, "", titleVisible, context, View.GONE, null,
////				clicklistener, isSystem);
////	}
//
//	/**
//	 * 双按钮,自定义标题,自定义内容,自定义确定事件
//	 * */
////	public static void promptTwoDialog(String btnStrNo, String btnStrYes,
////			Context context, String title, String msg,
////			OnClickListener clicklistener) {
////		promptTwoDialog(R.layout.layout_dialog_stop_track, btnStrNo, btnStrYes,
////				title, msg, "", View.VISIBLE, context, View.VISIBLE,
////				new OnClickListener() {
////
////					@Override
////					public void onClick(View v) {
////						((Dialog) v.getTag()).dismiss();
////					}
////				}, clicklistener, false);
////
////	}
//
//	/**
//	 * 另一个布局， 双按钮,自定义标题,自定义内容,自定义确定事件
//	 * */
////	public static void promptTwoDialog(String btnStrNo, String btnStrYes,
////			Context context, String msg, OnClickListener clicklistener) {
////		promptTwoDialog(R.layout.layout_dialog_stop_track1, btnStrNo,
////				btnStrYes, "", msg, "", View.VISIBLE, context, View.VISIBLE,
////				new OnClickListener() {
////
////					@Override
////					public void onClick(View v) {
////						((Dialog) v.getTag()).dismiss();
////					}
////				}, clicklistener, false);
////
////	}
//
//	/**
//	 * 验证申请弹框， 双按钮,输入框,自定义内容,自定义确定事件
//	 * */
////	public static Dialog promptTwoEditTextDialog(String btnStr1,
////			String btnStr2, Context context, String title, String strEt,
////			OnClickListener clicklistener) {
////		return promptTwoDialog(R.layout.layout_dialog_shenqing, btnStr1,
////				btnStr2, title, "", strEt, View.VISIBLE, context, View.VISIBLE,
////				new OnClickListener() {
////
////					@Override
////					public void onClick(View v) {
////						((Dialog) v.getTag()).dismiss();
////					}
////				}, clicklistener, false);
////
////	}
//
//	/**
//	 * 自定义 按钮名称，标题，内容，显示，点击事件
//	 *
//	 * @param btnstr1
//	 * @param btnstr2
//	 * @param msg
//	 * @param context
//	 * @param clicklistener
//	 * @param clicklistener1
//	 */
////	public static Dialog promptTwoDialog(int layout, String btnstr1,
////			String btnstr2, String title, String msg, String strEt,
////			int titleVisible, Context context, int btn1Visible,
////			OnClickListener clicklistener,
////			OnClickListener clicklistener1, boolean isSystem) {
////		final Dialog dialog = new Dialog(context, R.style.sc_FullScreenDialog);
////		LayoutInflater mLayoutInflater = (LayoutInflater) context
////				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		View reNameView = mLayoutInflater.inflate(layout, null);
////		LayoutParams params = new LayoutParams(
////				(int) (MyApplication.screenW * 0.8), LayoutParams.WRAP_CONTENT);
////		dialog.addContentView(reNameView, params);
////		Button rb1 = (Button) reNameView.findViewById(R.id.rb1);
////		Button rb2 = (Button) reNameView.findViewById(R.id.rb2);
////		View line = reNameView.findViewById(R.id.dialog_line);
////		if (layout == R.layout.layout_dialog_stop_track) {
////			TextView titleTv = (TextView) reNameView
////					.findViewById(R.id.tv_title);
////			titleTv.setVisibility(titleVisible);
////			titleTv.setText(title);
////			TextView tellTv = (TextView) reNameView.findViewById(R.id.tellTv);
////			tellTv.setText(msg);
////			rb1.setTag(dialog);
////			rb2.setTag(dialog);
////
////		} else if (layout == R.layout.layout_dialog_shenqing) {
////			TextView titleTv = (TextView) reNameView
////					.findViewById(R.id.tv_title);
////			titleTv.setVisibility(titleVisible);
////			titleTv.setText(title);
////			EditText et = (EditText) reNameView
////					.findViewById(R.id.dialog_shenqing_et);
////			rb1.setTag(dialog);
////			rb2.setTag(et);
////			et.setText(strEt);
////		} else {
////			TextView tellTv = (TextView) reNameView.findViewById(R.id.tellTv);
////			tellTv.setText(msg);
////
////			rb1.setTag(dialog);
////			rb2.setTag(dialog);
////		}
////
////		rb1.setVisibility(btn1Visible);
////		line.setVisibility(btn1Visible);
////
////		rb1.setText(btnstr1);
////		rb1.setOnClickListener(clicklistener);
////
////		rb2.setText(btnstr2);
////		rb2.setOnClickListener(clicklistener1);
////		if (b) {
////			b = false;
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////			if (isSystem) {
////				dialog.getWindow().setType(
////						WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
////			}
////			dialog.show();
////
////		} else {
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////		}
////		return dialog;
////
////	}
//
////	/**
////	 * 自定义 按钮名称，标题，内容，显示，点击事件
////	 *
////	 * @param btnstr1
////	 * @param btnstr2
////	 * @param msg
////	 * @param context
////	 * @param clicklistener
////	 * @param clicklistener1
////	 */
////	public static Dialog promptImageDialog(final Activity context,final String str,
////			final String path, BitmapUtils bitmapUtils) {
////		final Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen);
////		LayoutInflater mLayoutInflater = (LayoutInflater) context
////				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		LayoutParams params = new LayoutParams(
////				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
////		View layout = mLayoutInflater
////				.inflate(R.layout.activity_imagescan, null);
////		ImageView imageview = (ImageView) layout.findViewById(R.id.image);
////		ImageButton iv_left = (ImageButton) layout.findViewById(R.id.iv_boxleft);
////		ImageButton ib_image = (ImageButton) layout.findViewById(R.id.ib_image);
////		iv_left.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				dialog.dismiss();
////			}
////		});
////		ib_image.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				ActionSheetShare.showSheet(context, "",str,"");
////			}
////		});
////		imageview.setOnLongClickListener(new OnLongClickListener() {
////
////			@Override
////			public boolean onLongClick(View v) {
////				Toast.makeText(context, "图片已保存至" + path, Toast.LENGTH_LONG)
////						.show();
////				return false;
////			}
////		});
////
////		bitmapUtils.display(imageview, path);
////		dialog.addContentView(layout, params);
////
////		if (b) {
////			b = false;
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////			dialog.show();
////
////		} else {
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////		}
////		return dialog;
////
////	}
//
//	/**
//	 * 添加好友
//	 *
//	 * @param friendName
//	 * @param context
//	 * @param clicklistener
//	 * @param clicklistener1
//	 * @return
//	 */
////	public static EditText promptFriendGroupDialog(String friendName,
////			Context context, OnClickListener clicklistener1,
////			OnClickListener clicklistener2) {
////		final Dialog dialog = new Dialog(context, R.style.sc_FullScreenDialog);
////		LayoutInflater mLayoutInflater = (LayoutInflater) context
////				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		View reNameView = mLayoutInflater.inflate(
////				R.layout.layout_dialog_tianjiahaoyou, null);
////		LayoutParams params = new LayoutParams(
////				(int) (MyApplication.screenW * 0.8), LayoutParams.WRAP_CONTENT);
////		dialog.addContentView(reNameView, params);
////		Button rb1 = (Button) reNameView.findViewById(R.id.rb1);
////		Button rb2 = (Button) reNameView.findViewById(R.id.rb2);
////		TextView tellTv = (TextView) reNameView
////				.findViewById(R.id.dialog_tianjiahaoyou_name);
////		EditText et = (EditText) reNameView
////				.findViewById(R.id.dialog_tianjiahaoyou_beizhu);
////		TextView fenzu = (TextView) reNameView
////				.findViewById(R.id.dialog_tianjiahaoyou_fenzu);
////		tellTv.setText(friendName);
////		et.setTag(fenzu);
////		rb1.setTag(dialog);
////		rb2.setTag(dialog);
////		fenzu.setOnClickListener(clicklistener2);
////		rb1.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				dialog.dismiss();
////
////			}
////		});
////		rb2.setOnClickListener(clicklistener1);
////		if (b) {
////			b = false;
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////			dialog.show();
////
////		} else {
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////		}
////		return et;
////
////	}
//
//	/**
//	 * 车型验证码
//	 *
//	 * @param context
//	 * @param clicklistener
//	 * @param clicklistener1
//	 * @return
//	 */
////	public static EditText promptCheXingDialog(Context context,
////			OnClickListener clicklistener1, String imageStringData) {
////		final Dialog dialog = new Dialog(context, R.style.sc_FullScreenDialog);
////		LayoutInflater mLayoutInflater = (LayoutInflater) context
////				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		View reNameView = mLayoutInflater.inflate(
////				R.layout.layout_dialog_chexing, null);
////		LayoutParams params = new LayoutParams(
////				(int) (MyApplication.screenW * 0.8), LayoutParams.WRAP_CONTENT);
////		dialog.addContentView(reNameView, params);
////		Button rb1 = (Button) reNameView.findViewById(R.id.rb1);
////		Button rb2 = (Button) reNameView.findViewById(R.id.rb2);
////		ImageView image = (ImageView) reNameView.findViewById(R.id.image);
////		byte[] imagedata = Base64.decodeBase64(imageStringData);
////		Bitmap bitmap = BitmapFactory.decodeByteArray(imagedata, 0,
////				imagedata.length);
////		// 取得想要缩放的matrix参数
////		Matrix matrix = new Matrix();
////		matrix.postScale(2, 2);
////		// 得到新的图片
////		Bitmap scaleBit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
////				bitmap.getHeight(), matrix, true);
////		image.setImageBitmap(scaleBit);
////		// CommonUtils.scaleImg(bm, newWidth, newHeight)
////		EditText et = (EditText) reNameView
////				.findViewById(R.id.dialog_et_chejiaohao);
////		rb1.setTag(dialog);
////		rb2.setTag(dialog);
////		rb1.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				dialog.dismiss();
////
////			}
////		});
////		rb2.setOnClickListener(clicklistener1);
////		if (b) {
////			b = false;
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////			dialog.show();
////
////		} else {
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////		}
////		return et;
////
////	}
//
////	/**
////	 * 车架号验证
////	 *
////	 * @param context
////	 * @param clicklistener
////	 * @param clicklistener1
////	 * @return
////	 */
////	public static EditText promptChejiaohaoDialog(Context context,
////			OnClickListener clicklistener1) {
////		final Dialog dialog = new Dialog(context, R.style.sc_FullScreenDialog);
////		LayoutInflater mLayoutInflater = (LayoutInflater) context
////				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////		View reNameView = mLayoutInflater.inflate(
////				R.layout.layout_dialog_chejiaohao, null);
////		LayoutParams params = new LayoutParams(
////				(int) (MyApplication.screenW * 0.8), LayoutParams.WRAP_CONTENT);
////		dialog.addContentView(reNameView, params);
////		Button rb1 = (Button) reNameView.findViewById(R.id.rb1);
////		Button rb2 = (Button) reNameView.findViewById(R.id.rb2);
////		EditText et = (EditText) reNameView
////				.findViewById(R.id.dialog_et_chejiaohao);
////		rb1.setTag(dialog);
////		rb2.setTag(dialog);
////		rb1.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				dialog.dismiss();
////
////			}
////		});
////		rb2.setOnClickListener(clicklistener1);
////		if (b) {
////			b = false;
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////			dialog.show();
////
////		} else {
////			Timer.schedule(new TimerTask() {
////
////				@Override
////				public void run() {
////					b = true;
////				}
////			}, 1000);
////		}
////		return et;
////
////	}
//
//	/**
//	 * 选择时间
//	 *
//	 * @param index
//	 *            0（？-CurrentYear年？-currentMonth月？-currentDay日）1（？？？万）2（？年） 3（
//	 *            ？年？月？日）
//	 * @param context
//	 * @param
//	 * @param clicklistener1
//	 * @return
//	 */
//	public static Dialog promptDateGroupDialog(int index, Context context,
//			String strTitle, OnClickListener clicklistener1,
//			final OnActionSheetSelected selected) {
//		final Dialog dialog = new Dialog(context, R.style.sc_FullScreenDialog);
//		LayoutInflater mLayoutInflater = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View reNameView = null;
//		if (index == 0) {
//			reNameView = mLayoutInflater.inflate(R.layout.layout_yearmonthday,
//					null);
//		} else if (index == 1) {
//			reNameView = mLayoutInflater.inflate(
//					R.layout.layout_number_number_number, null);
//		} else if (index == 2) {
//			reNameView = mLayoutInflater.inflate(R.layout.layout_nianfen, null);
//		} else if (index == 3) {
//			reNameView = mLayoutInflater.inflate(R.layout.layout_yearmonthday_month_day, null);
//		} else if(index == 4){
//			reNameView = mLayoutInflater.inflate(R.layout.layout_yearmonthdaynormal,
//					null);
//		}
////		LayoutParams params = new LayoutParams(
////				(int) (MyApplication.screenW * 0.8), LayoutParams.WRAP_CONTENT);
//		LayoutParams params = new LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		dialog.addContentView(reNameView, params);
//		Button rb1 = (Button) reNameView.findViewById(R.id.rb1);
//		Button rb2 = (Button) reNameView.findViewById(R.id.rb2);
//		TextView title = (TextView) reNameView
//				.findViewById(R.id.birthday_tv_title);
//		final DatePicker picker = (DatePicker) reNameView
//				.findViewById(R.id.birthday_date);
//		title.setText(strTitle);
//		rb1.setTag(dialog);
//		rb2.setTag(dialog);
//		rb1.setOnClickListener(clicklistener1);
//		rb2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				((Dialog) v.getTag()).dismiss();
//				selected.onClick(picker.getTime());
//			}
//		});
//		if (b) {
//			b = false;
//			Timer.schedule(new TimerTask() {
//
//				@Override
//				public void run() {
//					b = true;
//				}
//			}, 1000);
//			dialog.show();
//
//		} else {
//			Timer.schedule(new TimerTask() {
//				@Override
//				public void run() {
//					b = true;
//				}
//			}, 1000);
//		}
//		return dialog;
//
//	}
//
//	public interface OnActionSheetSelected {
//		void onClick(String name);
//	}
//}
