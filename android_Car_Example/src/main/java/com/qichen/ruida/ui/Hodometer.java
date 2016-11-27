package com.qichen.ruida.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.andview.refreshview.XRefreshView;
import com.qichen.Utils.LogUtils;
import com.qichen.UtilsNet.NetAesCallBack;
import com.qichen.ruida.R;
import com.qichen.ruida.adapter.MyListView_04_more;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.bean.OrderInfo_Details;
import com.qichen.ruida.bean.OrderListBean;
import com.qichen.ruida.request.OrderListrequest;
import com.qichen.ruida.request.getOrderInfo_Details;
import com.qichen.ruida.view.LoadingView_09;
import com.qichen.ruida.view.MyListView;
import com.qichen.ruida.view.initAction_Bar;

import java.util.ArrayList;

//我的行程activity
public class Hodometer extends BaseActivity implements LoadingView_09.OnRefreshListener {
    ArrayList<OrderListBean.DataBean> mOders = new   ArrayList<OrderListBean.DataBean>();
    private initAction_Bar mBase_initAction;
    private MyListView mHodometer_listview;
    private boolean flag;

    Handler mHandler= new Handler();
    private XRefreshView mXrefreshview;
    private LoadingView_09 mLoading_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public int getlayouXML() {
        return R.layout.activity_hodometer;
    }

    @Override
    public void initView() {
        mBase_initAction = (initAction_Bar) findViewById(R.id.relativeLayout_title);
        mBase_initAction.setCallBack(new initAction_Bar.Action_bar_call_back() {
            @Override
          public void getAction_barView_backbutton( Button button) {
                button.setBackgroundResource(R.drawable.title_leftbutton);
            }

            @Override
            public void getAction_barView_title(TextView textView) {
                textView.setText("行程");
            }
        });
        mHodometer_listview = (MyListView) findViewById(R.id.Hodometer_listview);

        mXrefreshview = (XRefreshView) findViewById(R.id.xrefreshview);
        //loading
        mLoading_view = (LoadingView_09) findViewById(R.id.loading_view);
        mLoading_view.setRefrechListener(this);
        initData();

    }



    private void configXRfreshView(XRefreshView xRefreshView, XRefreshView.SimpleXRefreshListener listener) {
        xRefreshView.setPullLoadEnable(true);
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setAutoLoadMore(false);
        //两种方式设置空布局，传入空布局的view或者传入布局id都可以
//        TextView textView = new TextView(this);
//        textView.setText("没有数据，点击刷新");
//        textView.setGravity(Gravity.CENTER);
//        xRefreshView.setEmptyView(textView);
        //xRefreshView.setEmptyView(R.layout.layout_emptyview);
        xRefreshView.setXRefreshViewListener(listener);
    }


    @Override
    public void initData() {
        if (!mXrefreshview.mPullRefreshing){

            mLoading_view.setStatue(LoadingView_09.LOADING);
        }
        OrderListrequest.request(this, new NetAesCallBack() {
            @Override
            public void onSucceed(JSONObject jsonObject) {
                LogUtils.i("我的行程"+jsonObject);
               // OrderListBean data = jsonObject.getObject("data", OrderListBean.class);
                OrderListBean orderListBean = jsonObject.toJavaObject(OrderListBean.class);
                ArrayList<OrderListBean.DataBean> result = (ArrayList<OrderListBean.DataBean>) orderListBean.data;
                                mOders = result;
                                //获取网络成功调用这个方法
                                initAdapter();
                                if (null!=mXrefreshview)
                                    mXrefreshview.stopRefresh();
                                mLoading_view.setStatue(LoadingView_09.GONE);
            }

            @Override
            public void onError(String errorString) {

            }
        });

//        NetMessage.get(this)
//                .sendMessage(Constants.new_url, null, Constants.NORMAL, new NetAesCallBack() {
//                    @Override
//                    public void onSucceed(JSONObject jsonObject) {
//                        LogUtils.i("jsonObject订单是啥"+jsonObject);
//                        try{
//                            oder_04 data = jsonObject.getObject("data", oder_04.class);
//                            if (null!=data){
//                                ArrayList<oder_04.ResultBean> result = (ArrayList<oder_04.ResultBean>) data.result;
//                                mOders = result;
//                                //获取网络成功调用这个方法
//                                initAdapter();
//                                if (null!=mXrefreshview)
//                                    mXrefreshview.stopRefresh();
//                                mLoading_view.setStatue(LoadingView_09.GONE);
//                            }
//
//                        }catch (Exception e){
//                            mLoading_view.setStatue(LoadingView_09.GONE);
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(String errorString) {
//                        LogUtils.i("jsonObject订单是啥失败"+errorString);
//                        if (null!=mXrefreshview)
//                            mXrefreshview.stopRefresh();
//                        LogUtils.i("停止刷新");
//                        mLoading_view.setStatue(LoadingView_09.NO_NETWORK);
//                    }
//                });

    }

    @Override
    public void initListener() {

    }

    private void initAdapter() {

        final MyListView_04_more myListView_04_more = new MyListView_04_more(this, 99, mOders);


        mHodometer_listview.setAdapter(myListView_04_more);
        mHodometer_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i("我点的是"+position);
                OrderListBean.DataBean dataBean = mOders.get(position);
                LogUtils.i("传过去的请求数据"+dataBean.order_id);
                getOrderInfo_Details.request(Hodometer.this,dataBean ,new NetAesCallBack() {
                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        LogUtils.i("订单详情的内容是"+jsonObject);
                        OrderInfo_Details data = jsonObject.getObject("data", OrderInfo_Details.class);
                        Intent intent = new Intent(Hodometer.this, OrderInfp_Details_activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("OrderInfo_Details",data);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String errorString) {

                    }
                });


            }
        });

        //设置配置然后设置回调
        configXRfreshView(mXrefreshview, new XRefreshView.SimpleXRefreshListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                LogUtils.i("刷新_下拉刷新");
                initData();
                //mXrefreshview.stopRefresh();
            }
            //上拉加载更多
            @Override
            public void onLoadMore(boolean isSilence) {
                LogUtils.i("调用加载更多");
                //加载更多
                OrderListrequest.requestmore(Hodometer.this, new NetAesCallBack() {
                    @Override
                    public void onSucceed(JSONObject jsonObject) {
                        OrderListBean orderListBean = jsonObject.toJavaObject(OrderListBean.class);
                        LogUtils.i("加载更多的集合长度是"+orderListBean.data.size());
                        if (orderListBean.data.size()>0){
                            myListView_04_more.addData(orderListBean.data);

                        }
                        mXrefreshview.stopLoadMore();
                    }

                    @Override
                    public void onError(String errorString) {
                        LogUtils.i("jsonObject订单是啥失败"+errorString);
                        //加载完毕的方法
                        mHodometer_listview.onLoadComplete();
                        mXrefreshview.stopLoadMore();
                    }
                });

                LogUtils.i("刷新_上拉加载");
//                NetMessage.get(Hodometer.this)
//                        .sendMessage(Constants.urltest, null, Constants.TEST, new NetAesCallBack() {
//                            @Override
//                            public void onSucceed(JSONObject jsonObject) {
//                                LogUtils.i("jsonObject订单是啥"+jsonObject);
//                                try{
//                                    oder_04 data = jsonObject.getObject("data", oder_04.class);
//                                    if(null!=data){
//                                        //获取网络成功调用这个方法
//                                        // mOders.addAll(data.result);
//                                        myListView_04_more.addData(data.result);
////                                                mHodometer_listview.onLoadComplete();
////                                                //加载完毕的方法
//                                        mXrefreshview.stopLoadMore();
//                                    }
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//                            @Override
//                            public void onError(String errorString) {
//                                LogUtils.i("jsonObject订单是啥失败"+errorString);
//                                //加载完毕的方法
//                                // mHodometer_listview.onLoadComplete();
//                                mXrefreshview.stopLoadMore();
//                            }
//                        });
            }
        });



        //我的行程listView 加载更多
//        mHodometer_listview.setOnLoadListener(new MyListView.OnLoadListener() {
//            @Override
//            public void onLoad() {
//                LogUtils.i("加载更多了 listView 回调");
//                myListView_04_more.setListViewLoadMore(new ListViewLoadMore() {
//                    @Override
//                    public void listViewLoadMore(int type) {
//                        LogUtils.i("适配器回调号码是"+type);
//                        //加载更多
//                        NetMessage.get(Hodometer.this)
//                                .sendMessage(Constants.urltest, null, Constants.TEST, new NetAesCallBack() {
//                                    @Override
//                                    public void onSucceed(JSONObject jsonObject) {
//                                        LogUtils.i("jsonObject订单是啥"+jsonObject);
//                                        try{
//                                            oder_04 data = jsonObject.getObject("data", oder_04.class);
//                                            if(null!=data){
//                                                //获取网络成功调用这个方法
//                                                // mOders.addAll(data.result);
//                                                myListView_04_more.addData(data.result);
//                                                //加载完毕的方法
//                                                mHodometer_listview.onLoadComplete();
//                                            }
//                                        }catch (Exception e){
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                    @Override
//                                    public void onError(String errorString) {
//                                        LogUtils.i("jsonObject订单是啥失败"+errorString);
//                                        //加载完毕的方法
//                                        mHodometer_listview.onLoadComplete();
//                                    }
//                                });
//                    }
//                });
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
////                        LogUtils.i("加载更多开始");
////
////                        SystemClock.sleep(3000);
////                        mHandler.post(new Runnable() {
////                            @Override
////                            public void run() {
////
////                                //加载完毕的方法
////                                mHodometer_listview.onLoadComplete();
////                            }
////                        });
////
////                        LogUtils.i("加载完成");
////                    }
////                }).start();
//
//            }
//        });





//        mHodometer_listview.setCallBack(new MyListView.UserOnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
//        mHodometer_listview.setCallBackLoadMore(new MyListView.OnPullUpLoadMoreListener() {
//            @Override
//            public void onPullUpLoadMore() {
//                LogUtils.i("到底了");
//            }
//        });

//        mHodometer_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    // 当不滚动时
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        // 判断滚动到底部
//                        if (mHodometer_listview.getLastVisiblePosition()
//                                == (mHodometer_listview.getCount() - 1)) {
//                            LogUtils.i("滑动_到底了");
//
//                        }
//                        // 判断滚动到顶部
//
//                        if(mHodometer_listview.getFirstVisiblePosition() == 0){
//                            LogUtils.i("滑动_顶部");
//                        }
//
//                        break;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                if (firstVisibleItem + visibleItemCount == totalItemCount && !flag) {
//                   // LogUtils.i("滑动_到底了");
//                    flag = true;
//                } else
//                   // LogUtils.i("滑动_没到底");
//                    flag = false;
//            }
//        });



    }
    //点了按钮之后
    @Override
    public void refresh() {
            initData();
    }
}
