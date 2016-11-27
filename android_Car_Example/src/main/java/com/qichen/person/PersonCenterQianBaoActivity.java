package com.qichen.person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.qichen.Utils.LineRadioGroup;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.SQUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.Utils.linesFristCallBack;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.ruida.request.getMoney;
import com.qichen.ruida.ui.PayActivity;
import com.qichen.ruida.R;

import java.util.ArrayList;

//充值返现activity
public class PersonCenterQianBaoActivity extends Activity implements View.OnClickListener {

	private int selectMoney = 8;
	private int[] buttonMoney = { 50, 100, 300, 500, 1000, 2000};
	private int[] songMoney = { 30, 70, 240, 500, 1000, 2000};
	private LinearLayout mLl_lens;
	private TextView mText_money_text;
	private TextView mText_money_text_shuoming;
	private EditText mEt_inoutqian;
	private TextView mTv_info;
	private Button mMoney_chongzhi;
	private LinearLayout mLl_show;
	private TextView mText_money;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_center_qianbao);

		TextView relativeLayout_title_titleview = (TextView) this.findViewById(R.id.relativeLayout_title_titleview);
		relativeLayout_title_titleview.setText("充值返现");

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
		initView();
		//initButton();

		//showText();


	}
	//初始化 布局
	private void initView() {
		mLl_lens = (LinearLayout) findViewById(R.id.ll_lens);
		//冲了多少钱
		mText_money_text = (TextView) this.findViewById(R.id.text_money_text);

		//我的余额
		mText_money = (TextView) this.findViewById(R.id.text_money);

		mText_money.setText(SQUtils.getStrings(this, "passenger_money","")+"元");
		mText_money_text_shuoming = (TextView) this.findViewById(R.id.text_money_text_shuoming);
		//手动输入钱数
		mEt_inoutqian = (EditText) findViewById(R.id.et_inoutqian);
		mEt_inoutqian.setVisibility(View.GONE);
		//下面金钱提示
		mTv_info = (TextView) findViewById(R.id.tv_info);
		mTv_info.setVisibility(View.GONE);
		//点击我要去充值
		mMoney_chongzhi = (Button) findViewById(R.id.money_chongzhi);
		//该不该显示
		mLl_show = (LinearLayout) findViewById(R.id.ll_show);
//		mEt_inoutqian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//
//			}
//		});
		//获取金钱的 请求
		getMoney.request(this, new NetAesCallBack() {
			@Override
			public void onSucceed(JSONObject jsonObject) {
				LogUtils.i("金钱应该是"+jsonObject);
				mText_money.setText(jsonObject.getString("data")+"元");
			}

			@Override
			public void onError(String errorString) {
				LogUtils.i("金钱应该是"+errorString);
			}
		});


		//文本发生变动的时候的监听接口
		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
					LogUtils.i("文本内容是"+s.toString());
				//输入的钱数
				if (s.length()!=0){
					//当这个输入框获取 焦点的时候 让他们都清楚状态
					LineRadioGroup.clearCheckAll();
//					int i = Integer.parseInt(s.toString());
//					for (int j = 0; j < buttonMoney.length; j++) {
//
//
//						//档数
//						int i1 = buttonMoney[j];
//
//						LogUtils.i("档位"+i1);
//						//我充值金额  比最大的还大
//						if (i>=200&&i>=buttonMoney[buttonMoney.length-1]){
//							upShowTextdown(buttonMoney.length-1,i);
//							upShowText(buttonMoney.length-1,i);
//							return;
//						}
//
//
//						//起点200 才开始送
//						if (i>=200&&i<i1){
//
//							if (j ==0){
////								LogUtils.i("最低的档位");
////								upShowTextdown(j,i);
////								upShowText(j,i);
//								//return;
//							}else{
//								//你输入的数比 档位的数小与等于
//								LogUtils.i("输入的数比档位的数小");
//								upShowTextdown(j-1,i);
//								upShowText(j-1,i);
//								return;
//							}
//						}else{
//							mTv_info.setText("");
//							upShowTextmin(i);
//						}
//					}
//
//					mLl_show.setVisibility(View.VISIBLE);
				}else{
					//如果冲得钱为0 就是那个数也是0
					mText_money_text.setText("");
					//如果输入框里面的 长度是0
					mTv_info.setText("");
					//长度为0 隐藏
					mLl_show.setVisibility(View.INVISIBLE);
				}


			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		};

		//监听文本变化
		//mEt_inoutqian.addTextChangedListener(textWatcher);



		ArrayList<LineRadioGroup.FeedbackOptionItem> feedbackOptionItems = new ArrayList<LineRadioGroup.FeedbackOptionItem>();
		for (int i = 0; i < buttonMoney.length; i++) {
			feedbackOptionItems.add(new LineRadioGroup.FeedbackOptionItem(""+i,buttonMoney[i]+"",songMoney[i]+"","","","",""));
		}
		//给他赋值
		LineRadioGroup.setLinesFristCallBack(new linesFristCallBack() {
			@Override
			public void fristCallback(LineRadioGroup.FeedbackOptionItem feedbackOptionItem) {
				String tid = feedbackOptionItem.tid;
				int pos = Integer.parseInt(tid);
				upShowText(pos);
			}
		});
		LineRadioGroup.updateOptionsView(this, feedbackOptionItems, mLl_lens, new LineRadioGroup.CallBackItemNum() {
			@Override
			public void callback(int pos, String title, int linespos, String num, String span) {
				LogUtils.i("我现在点的内容是"
								+"pos"+"\t"+pos+"\n"
								+"title"+"\t"+title+"\n"
								+"linespos"+"\t"+linespos+"\n"
								+"num"+"\t"+num
								+"span"+"\t"+span
							);
				mEt_inoutqian.setText("");
				upShowText(pos);
				mLl_show.setVisibility(View.VISIBLE);
			}

		});




		mMoney_chongzhi.setOnClickListener(this);

		//交易金额 测试交易金金额0.01
		//mText_money_text.setText("0.01");

	}

	@Override
	protected void onResume() {
		super.onResume();
		//获取金钱的 请求
		if (null!=mText_money){
			getMoney.request(this, new NetAesCallBack() {
				@Override
				public void onSucceed(JSONObject jsonObject) {
					LogUtils.i("金钱应该是"+jsonObject);
					mText_money.setText(jsonObject.getString("data")+"元");
				}

				@Override
				public void onError(String errorString) {
					LogUtils.i("金钱应该是"+errorString);
				}
			});
		}



	}



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
	/**
	 * 输入框右面的提示
	 * @param j 送的钱数
	 * @param i 你输入的钱数
     */
	private void upShowTextdown(int j, int i) {
		mTv_info.setText("送" + songMoney[j] + "元，可得" + (i + songMoney[j]) + "元余额");
	}

	/**
	 * 更新 上面的显示
	 * @param pos 当前的pos  通过这个数   可以找到两个 充值的档位
     */
	private void upShowText(int pos) {
		mText_money_text.setText(buttonMoney[pos] + "");
		mText_money_text_shuoming.setText(
				"送" + songMoney[pos] + "元，可得" + (buttonMoney[pos] + songMoney[pos]) + "元余额");

	}

	/**
	 * 下面输入框 更新 上面的显示
	 * @param pos 档位
	 * @param i 你输入的钱数
     */
	private void upShowText(int pos,int i) {
		mText_money_text.setText(i + "");
		mText_money_text_shuoming.setText(
				"送" + songMoney[pos] + "元，可得" + (i + songMoney[pos]) + "元余额");

	}
	/**
	 * 下面输入框 更新 上面的显示
	 * @param i 你输入的钱数
     */
	private void upShowTextmin(int i) {
		mText_money_text.setText(i + "");
		mText_money_text_shuoming.setText("");

	}




	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.money_chongzhi:
					//点击了去充值
				startPay();
			break;
		}
	}

	private void startPay() {
		//选择支付方式
		if (TextUtils.isEmpty(mText_money_text.getText().toString())){
			UtilsToast.showToast(this, "没有充值金额");
			return;
		}
		//int parseInt = Integer.parseInt(mText_money_text.getText().toString());
//		if (parseInt<=0){
//			UtilsToast.showToast(this, "充值金额不正确");
//			return;
//		}
		Intent intent = new Intent(this, PayActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("payment",mText_money_text.getText().toString());

		intent.putExtras(bundle);
		startActivity(intent);
		//可以带着数据传过去
	}

}






//	public void showText() {
//		TextView text_money_text = (TextView) this.findViewById(R.id.text_money_text);
//		text_money_text.setText(buttonMoney[selectMoney] + "");
//
//		TextView text_money_text_shuoming = (TextView) this.findViewById(R.id.text_money_text_shuoming);
//		text_money_text_shuoming.setText(
//				"送" + songMoney[selectMoney] + "元，可得" + (buttonMoney[selectMoney] + songMoney[selectMoney]) + "元余额");
//	}

//	public void initButton() {
//		final Button money_button_1 = (Button) findViewById(R.id.money_button_1);
//		final Button money_button_2 = (Button) findViewById(R.id.money_button_2);
//		final Button money_button_3 = (Button) findViewById(R.id.money_button_3);
//
//		final Button money_button_4 = (Button) findViewById(R.id.money_button_4);
//		final Button money_button_5 = (Button) findViewById(R.id.money_button_5);
//		final Button money_button_6 = (Button) findViewById(R.id.money_button_6);
//
//		final Button money_button_7 = (Button) findViewById(R.id.money_button_7);
//		final Button money_button_8 = (Button) findViewById(R.id.money_button_8);
//		final Button money_button_9 = (Button) findViewById(R.id.money_button_9);
//
//		money_button_1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 0;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money_red);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//
//			}
//		});
//
//		money_button_2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 1;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money_red);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_3.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 2;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money_red);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_4.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 3;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money_red);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_5.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 4;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money_red);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_6.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 5;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money_red);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_7.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 6;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money_red);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_8.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 7;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money_red);
//				money_button_9.setBackgroundResource(R.drawable.border_money);
//			}
//		});
//
//		money_button_9.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				selectMoney = 8;
//				showText();
//				money_button_1.setBackgroundResource(R.drawable.border_money);
//				money_button_2.setBackgroundResource(R.drawable.border_money);
//				money_button_3.setBackgroundResource(R.drawable.border_money);
//				money_button_4.setBackgroundResource(R.drawable.border_money);
//				money_button_5.setBackgroundResource(R.drawable.border_money);
//				money_button_6.setBackgroundResource(R.drawable.border_money);
//				money_button_7.setBackgroundResource(R.drawable.border_money);
//				money_button_8.setBackgroundResource(R.drawable.border_money);
//				money_button_9.setBackgroundResource(R.drawable.border_money_red);
//			}
//		});
//	}