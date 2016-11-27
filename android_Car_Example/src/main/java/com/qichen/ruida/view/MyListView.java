package com.qichen.ruida.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qichen.ruida.R;

/**
 * Created by 35876 于萌萌
 * 创建日期: 22:45 . 2016年10月02日
 * 描述:
 * <p/>
 * <p/>
 * 备注:
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener {
  // public UserOnScrollListener mUserOnScrollListener;

    public MyListView(Context context) {
        super(context);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private static final String TAG = "AutoLoadListView";

    private LayoutInflater inflater;
    private View footer;

   // private TextView loadFail;
   // private TextView loadFull;
    private TextView loadIng;
    private ProgressBar progressBar;

    private boolean isLoading;// 判断是否正在加载

    public OnLoadListener mOnLoadListener;

//    public AutoLoadListView(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public AutoLoadListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public AutoLoadListView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(context);
//    }

    // 初始化组件
    private void initView(Context context) {
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.item_listview_more_04, null);
//        loadFull = (TextView) footer.findViewById(R.id.footer_loadFull);
//        loadFail = (TextView) footer.findViewById(R.id.footer_noData);
        loadIng = (TextView) footer.findViewById(R.id.footer_more);
        progressBar = (ProgressBar) footer.findViewById(R.id.item_listview_ProgressBar);



        //addFooterView(footer);
        setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!isLoading && view.getLastVisiblePosition() == view.getCount() - 1) {
            isLoading = true;
            setloading();
            if (mOnLoadListener != null) {
                mOnLoadListener.onLoad();
            }
        }
    }

    // 用于加载更多结束后的回调
    public void onLoadComplete() {
        isLoading = false;
        setLoadFull();
    }

    // 加载失败时footer显示
    public void setLoadFailed() {
        loadIng.setVisibility(View.GONE);
       // loadFull.setVisibility(GONE);
        progressBar.setVisibility(GONE);
       // loadFail.setVisibility(VISIBLE);
    }

    // 加载完成时footer显示
    public void setLoadFull() {
        loadIng.setVisibility(View.GONE);
        //loadFull.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        //loadFail.setVisibility(GONE);
    }

    // 正在加载时footer显示
    private void setloading() {
        loadIng.setVisibility(View.VISIBLE);
        progressBar.setVisibility(VISIBLE);
//        loadIng.setVisibility(View.GONE);
//        progressBar.setVisibility(GONE);


        //loadFull.setVisibility(GONE);
        //loadFail.setVisibility(GONE);
    }

    // 加载更多监听
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
    }

    public interface OnLoadListener {
        public void onLoad();
    }



//    public interface UserOnScrollListener {
//        void onScrollStateChanged(AbsListView view, int scrollState);
//        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
//    }
//    public interface OnPullUpLoadMoreListener {
//       void onPullUpLoadMore();
//    }
//
//
//    public void setCallBack(UserOnScrollListener userOnScrollListener){
//        mUserOnScrollListener = userOnScrollListener;
//    }
//
//    public void setCallBackLoadMore(OnPullUpLoadMoreListener mOnPullUpLoadMoreListener){
//        this.mOnPullUpLoadMoreListener = mOnPullUpLoadMoreListener;
//    }
//    OnPullUpLoadMoreListener mOnPullUpLoadMoreListener ;
//    private void init() {
//        super.setOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // 调用用户设置的OnScrollListener
//                if (mUserOnScrollListener != null) {
//
//                    mUserOnScrollListener.onScrollStateChanged(view, scrollState);
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                // 调用用户设置的OnScrollListener
//                if (mUserOnScrollListener != null) {
//                    mUserOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//                }
//
//                // firstVisibleItem是当前屏幕能显示的第一个元素的位置
//                // visibleItemCount是当前屏幕能显示的元素的个数
//                // totalItemCount是ListView包含的元素总数
//                int lastVisibleItem = firstVisibleItem + visibleItemCount;
//                if (lastVisibleItem == totalItemCount) {
//                    if (mOnPullUpLoadMoreListener != null) {
//                       // mIsLoading = true;
//                        mOnPullUpLoadMoreListener.onPullUpLoadMore();
//                    }
//                }
//            }
//        });
//    }

}
