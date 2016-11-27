package com.qichen.zhifubao.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qichen.ruida.R;

import static com.qichen.ruida.R.id.commodity_info;
import static com.qichen.ruida.R.id.product_price;
import static com.qichen.ruida.R.id.product_subject;

public class ExternalFragment extends Fragment {

	private TextView mProduct_price;
	private TextView mCommodity_info;
	private TextView mProduct_subject;
	private View mInflate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflate = inflater.inflate(R.layout.pay_external, container, false);


		return mInflate;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//消费金额
		mProduct_price = (TextView)mInflate.findViewById(product_price);
		//商品描述
		mCommodity_info = (TextView)mInflate.findViewById(commodity_info);
		//商品名字
		mProduct_subject = (TextView)mInflate.findViewById(product_subject);




		final PayDemoActivity activity = (PayDemoActivity) getActivity();
		activity.setCallBack(new PayDemoActivity.CallBack() {
			@Override
			public void callBack(Bundle mData) {
				//undle data = activity.getData();
				String string1 = mData.getString("String1");
				String string2 = mData.getString("String2");
				String string3 = mData.getString("String3");

				mProduct_subject.setText(string1);
				mCommodity_info.setText(string2);
				mProduct_price.setText(string3);
			}
		});

//		Bundle data = activity.getData();
//		String string1 = data.getString("String1");
//		String string2 = data.getString("String2");
//		String string3 = data.getString("String3");
//		mProduct_price.setText("1");
//		mCommodity_info.setText("2");
//		mProduct_subject.setText("3");
//		mProduct_price.setText(string1);
//		mCommodity_info.setText(string2);
//		mProduct_subject.setText(string3);
	}
}
