package com.qichen.ruida.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qichen.Utils.LogUtils;
import com.qichen.ruida.R;
import com.qichen.ruida.adapter.MyViewHolder_04;

import java.util.List;

/**
 * Created by 35876 于萌萌
 * 创建日期: 16:00 . 2016年10月08日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */
public abstract class BaseListViewAdapter_04<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas;
    int type;

    public BaseListViewAdapter_04(Context context, int type, List<T> datas) {
        this.type = type;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    public interface CallBack {
        void loadMore(View view, int position, int type);
    }

    public void setData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public int getItemViewType(int position) {
       return getViewType(mDatas.get(position),position);

    }
    //如果不是  正常 加上加载更多的话 就重写
    public int getViewType(T t ,int position){
//        if (position == mDatas.size()) {
//            LogUtils.i("已经到了最后了");
//            return R.layout.item_listview_more_04;
//        } else {
            return R.layout.item_0_12;
       // }
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LogUtils.i("目前加载的条目是"+position+"翻出来的View"+convertView);
         MyViewHolder_04 myViewHolder = getViewHolder(position, convertView,
                parent);
        //convert(myViewHolder, getItem(position),position);
        if (position<mDatas.size()) {
            convert(myViewHolder, mDatas.get(position), position);
        }
        return myViewHolder.getConvertView();

    }

    public abstract void convert(MyViewHolder_04 helper, T item, int position);

    private MyViewHolder_04 getViewHolder(int position, View convertView,
                                          ViewGroup parent)
    {

        int itemViewType = getItemViewType(position);
            LogUtils.i("现在的itemViewType"+itemViewType);

        return MyViewHolder_04.get(mContext,convertView, type,parent, itemViewType,
                position);
    }
}
