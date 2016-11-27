/**  
 * Project Name:Android_Car_Example  
 * File Name:RecomandAdapter.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午11:29:45  
 *  
 */

package com.qichen.ruida;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ClassName:RecomandAdapter <br/>
 * Function: 显示的poi列表 <br/>
 * Date: 2015年4月3日 上午11:29:45 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class RecomandAdapter extends BaseAdapter {

	// PositionEntity[] entities = new PositionEntity[] {
	// new PositionEntity(39.908722, 116.397496, "天安门","010"),
	// new PositionEntity(39.91141, 116.411306, "王府井","010"),
	// new PositionEntity(39.908342, 116.375121, "西单","010"),
	// new PositionEntity(39.990949, 116.481090, "方恒国际中心","010"),
	// new PositionEntity(39.914529, 116.316648, "玉渊潭公园","010"),
	// new PositionEntity(39.999093, 116.273945, "颐和园","010"),
	// new PositionEntity(39.999022, 116.324698, "清华大学","010"),
	// new PositionEntity(39.982940, 116.319802, "中关村","010"),
	// new PositionEntity(39.933708, 116.454185, "三里屯","010"),
	// new PositionEntity(39.941627, 116.435584, "东直门","010") };

	PositionEntity[] entities = new PositionEntity[] { new PositionEntity(45.773941, 126.618958, "中央大街", "0451"),
			new PositionEntity(45.761554, 126.632979, "哈尔滨站", "0451"),
			new PositionEntity(45.622432, 126.243605, "太平国际机场", "0451"),
			new PositionEntity(45.788576, 126.710815, "哈尔滨东站", "0451"),
			new PositionEntity(45.706451, 126.578564, "哈尔滨西站", "0451"),
			new PositionEntity(45.675581, 126.619675, "哈尔滨南站", "0451"),
			new PositionEntity(45.850814, 126.536296, "哈尔滨北站", "0451") };

	private List<PositionEntity> mPositionEntities;

	private Context mContext;

	public RecomandAdapter(Context context) {
		mContext = context;
		mPositionEntities = Arrays.asList(entities);

	}

	public void setPositionEntities(List<PositionEntity> entities) {
		this.mPositionEntities = entities;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return mPositionEntities.size();
	}

	@Override
	public Object getItem(int position) {

		return mPositionEntities.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			textView = (TextView) inflater.inflate(R.layout.view_recommond, null);
		} else {
			textView = (TextView) convertView;
		}
		textView.setText(mPositionEntities.get(position).address);
		return textView;
	}

}
