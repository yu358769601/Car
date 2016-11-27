package com.qichen.ruida.view;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qichen.ruida.R;


/**
 * 作者：Picasso on 2016/7/20 11:05
 * 详情：自定义loadingView
 * 使用步骤
 *          1
 *              xml 布局文件放 自定义的LoadingView 到对应位置
 *          2
 *              这个activity 找到这个LoadingView对象  findViewbyid
 *          3
 *              在初始化initView之后这个时候已经找到了 LoadingView
 *              在获取网络数据的方法里面使用这个
 *              使用LoadingView.setStatue(LoadingView.LOADING);
 *          4
 *              此时已经开始了load动画  这个时候在获取网络的方法里面的失败的地方
 *              使用LoadingView.setStatue(LoadingView.NO_NETWORK);
 *              这代表没有网络
 *          5
 *              使用刷新数据的方法  loading_view.setRefrechListener(this);
 *              这样在下面的  实现了的 那个抽象类 能使用 刷新数据的方法
 *
 */
public  class LoadingView_09 extends LinearLayout implements View.OnClickListener {
    public static final int LOADING = 0;
    public static final int STOP_LOADING = 1;
    public static final int NO_DATA = 2;
    public static final int NO_NETWORK = 3;
    public static final int GONE = 4;
    public static final int LOADING_DIALOG = 5;
    public static final int NO_BUY = 6;
    public static final int NO_OFFER = 7;

    private ImageView imageView;
    private RelativeLayout mRlError;
    private LinearLayout mLlLoading;
    private AnimationDrawable mAni;
    private View mView;
    private boolean run;
    private OnRefreshListener mListener;
    private RelativeLayout mRlNodata;
    private RelativeLayout mRlNobuy;
    private RelativeLayout mRlNooffer;

    public void setRefrechListener(OnRefreshListener mListener) {
        this.mListener = mListener;
    }

    public interface OnRefreshListener {
        void refresh();
    }

    public LoadingView_09(Context context) {
        super(context);
        init(context);
    }

    public LoadingView_09(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView_09(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.loading_layout_09, this);
        imageView = (ImageView) mView.findViewById(R.id.iv_loading);
        mLlLoading = (LinearLayout) mView.findViewById(R.id.ll_loading);
        mRlError = (RelativeLayout) mView.findViewById(R.id.rl_error);
        mRlNodata = (RelativeLayout) mView.findViewById(R.id.rl_nodata);
        mRlNobuy = (RelativeLayout) mView.findViewById(R.id.rl_nodbuy);
        mRlNooffer = (RelativeLayout) mView.findViewById(R.id.rl_nooffer);

        imageView.setBackgroundResource(R.drawable.loading_dialog_09);
        mAni = (AnimationDrawable) imageView.getBackground();
        //mAni= (AnimationDrawable)getResources().getDrawable(R.drawable.loading_dialog_09);

        TextView tvError = (TextView) mView.findViewById(R.id.tv_error);
        String exchange = getResources().getString(R.string.click_to_refresh_08);
        tvError.setText(Html.fromHtml(exchange));
        tvError.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.size_base480_10dp));

        TextView tv_nodata = (TextView) mView.findViewById(R.id.tv_nodata);
        String nodata = getResources().getString(R.string.no_data_08);
        //tv_nodata.setText(Html.fromHtml(nodata));
        tv_nodata.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimensionPixelSize(R.dimen.size_base480_10dp));


        mRlError.setOnClickListener(this);

        setStatue(GONE);
    }
    public boolean isRuning(){
        if (run){
            return true;
        }else{

            return false;
        }
    }
    public void setStatue(int status) {
        setVisibility(View.VISIBLE);

        try {
            //加载中
            if (status == LOADING) {
                mRlError.setVisibility(View.GONE);
                mRlNodata.setVisibility(View.GONE);
                mRlNobuy.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.VISIBLE);
                mRlNooffer.setVisibility(View.GONE);
                mAni.start();
                run = true;
            } else if (status == STOP_LOADING) {
                mAni.stop();
                setVisibility(View.GONE);
                run = false;
                //无数据情况
            } else if (status == NO_DATA) {
                mAni.stop();
                mRlError.setVisibility(View.GONE);
                mRlNodata.setVisibility(View.VISIBLE);
                mLlLoading.setVisibility(View.GONE);
                mRlNooffer.setVisibility(View.GONE);
                mRlNobuy.setVisibility(View.GONE);
                run = false;
                //无网络情况
            } else if (status == NO_NETWORK) {
                mAni.stop();
                mRlError.setVisibility(View.VISIBLE);
                mRlNodata.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.GONE);
                mRlNooffer.setVisibility(View.GONE);
                mRlNobuy.setVisibility(View.GONE);
                run = false;
                //无采购情况
            } else if (status == NO_BUY) {
                mAni.stop();
                mRlError.setVisibility(View.GONE);
                mRlNooffer.setVisibility(View.GONE);
                mRlNodata.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.GONE);
                mRlNobuy.setVisibility(VISIBLE);
                run = false;
            } else if (status == NO_OFFER){
                mAni.stop();
                mRlError.setVisibility(View.GONE);
                mRlNooffer.setVisibility(View.VISIBLE);
                mRlNodata.setVisibility(View.GONE);
                mLlLoading.setVisibility(View.GONE);
                mRlNobuy.setVisibility(View.GONE);
                run = false;
            } else {
                mAni.stop();
                setVisibility(View.GONE);
                run = false;
            }
        } catch (OutOfMemoryError e) {
        }
    }


    @Override
    public void onClick(View v) {
        mListener.refresh();
        setStatue(LOADING);
    }


}
