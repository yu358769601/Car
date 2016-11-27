package com.qichen.ruida.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 35876 于萌萌
 * 创建日期: 11:31 . 2016年10月21日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public abstract class baseRecyclerview<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //每个条目 应该显示什么
    public T json;
    public Context context;
    private int card;
    public LayoutInflater mLayoutInflater;
    public List<T> mTs = new ArrayList();
    public baseRecyclerview(Context context, int card , T json){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.json = json;
        this.card = card;

    }
    public baseRecyclerview(Context context, List<T> mTs){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mTs=mTs;
        notifyDataSetChanged();
    }
    public void setData(List<T> mTs){
        this.mTs = mTs;
        notifyDataSetChanged();
    }
    public void addData(List<T> mTs){
        this.mTs.addAll(mTs);
        notifyDataSetChanged();
    }
    public List<T> getTs(){
        return mTs;
    }


    @Override
    public itemView onCreateViewHolder(ViewGroup parent, int viewType) {

        return initonCreateViewHolder(parent,viewType);
    }

    protected abstract itemView initonCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            initonBindViewHolder(holder,position,json);
    }

    protected abstract void initonBindViewHolder(RecyclerView.ViewHolder holder, int position,T json);

    @Override
    public int getItemCount() {
        return card+mTs.size();
    }

    public class itemView extends RecyclerView.ViewHolder{

        public itemView(View itemView) {
            super(itemView);
        }

    }
}
