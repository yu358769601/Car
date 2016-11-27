package com.qichen.ruida.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qichen.Utils.LogUtils;


/**
 * Created by 35876 于萌萌
 * 创建日期: 23:03 . 2016年09月25日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */
public class MyViewHolder_04 {

    private final SparseArray<View> mViews;
    private View mConvertView;

    private MyViewHolder_04(Context context, int type, ViewGroup parent, int itemViewType,
                            int position)
    {
        this.mViews = new SparseArray<View>();

        mConvertView = LayoutInflater.from(context).inflate(itemViewType, parent,
                false);
        //View inflate = mInflater.inflate(mItemLayoutMore,parent,false);
//        if (itemViewType== R.layout.item_listview_more_04) {
//            callBack.loadMore(mConvertView, position, type);
//        }
        //setTag
        mConvertView.setTag(this);


    }

    /**
     * 拿到一个ViewHolder对象
     * @param context
     * @param convertView
     * @param type
     *@param parent
     * @param itemViewType
     * @param position
     * @param      @return
     */
    public static MyViewHolder_04 get(Context context, View convertView,
                                      int type, ViewGroup parent, int itemViewType, int position )
    {

        if (convertView == null)
        {
            return new MyViewHolder_04(context, type,parent, itemViewType, position);
        }
        return (MyViewHolder_04) convertView.getTag();
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public TextView initText(int viewId, String text)
    {
        TextView view = getView(viewId);
        view.setText(text);
        return view;
    }

    public LinearLayout initLinearLayout(Context context, int viewId){
        LinearLayout view = getView(viewId);
        return view;
    }

    /**
     *
     * @param context
     * @param viewId
     * @param url
     * @return
     */
    public ImageView initImage(Context context, int viewId, String url){
        ImageView view = getView(viewId);
        try{
            Glide.with(context)
                    .load(url)
                    .into(view);
        }catch (Exception e){
            LogUtils.i(e.toString());
        }finally {
            return view;
        }

        //view.initText(text);
    }
    /**
     *
     * @param context
     * @param viewId
     * @param
     * @return
     */
    public ImageView initImage(Context context, int viewId){
        ImageView view = getView(viewId);
            return view;

        //view.initText(text);
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {

        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }


}