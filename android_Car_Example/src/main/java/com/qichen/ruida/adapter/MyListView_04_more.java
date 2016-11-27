package com.qichen.ruida.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.qichen.interFace.ListViewLoadMore;
import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseListViewAdapter_04;
import com.qichen.ruida.bean.OrderListBean;
import com.qichen.ruida.view.MyListView;

import java.util.ArrayList;


/**
 * Created by 35876 于萌萌
 * 创建日期: 16:00 . 2016年10月08日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */ //我的行程adapter
public class MyListView_04_more extends BaseListViewAdapter_04<OrderListBean.DataBean> implements MyListView.OnLoadListener {
    public String net = "http://app.beta.9ac.com.cn/";
    ListViewLoadMore mListViewLoadMore;
    int type ;
    public MyListView_04_more(Context context, int type, ArrayList datas) {
        super(context, type, datas);
        this.type = type;
    }
    public void setListViewLoadMore(ListViewLoadMore mListViewLoadMore){
        this.mListViewLoadMore =mListViewLoadMore;
        mListViewLoadMore.listViewLoadMore(type);
    }
    //如果不是  正常 加上加载更多的话 就重写
//    public int getViewType(oder_04.ResultBean resultBean ,int position){
//        int i = Integer.parseInt(resultBean.delay);
//        if (position == mDatas.size()) {
//            return R.layout.load_more;
//        }
////        else if (i==1){
////            return R.layout.item_photo_layout_02;
////        }else if (i==2){
////            return R.layout.item_view_13;
////        }
//        else{
//            return R.layout.item_listview_04;
//        }
//    }
//    @Override
//    public int getCount() {
//        return mDatas.size()+1;
//    }

    @Override
    public void convert(MyViewHolder_04 helper, OrderListBean.DataBean item, int position) {
        //int i = Integer.parseInt(item.delay);
        int i1 = getViewType(item, position);
        if (i1 == R.layout.item_0_12) {

//            //电话号码
//            String phone = item.phone1;
//            //名字
//            String short_name = item.short_name;
//            //类别
//            String tag_name = item.tag_name;
//            //图片
//            oder_04.ResultBean.BiddingFilesBean biddingFilesBean = item.bidding_files.get(1);
//            String file_path = biddingFilesBean.file_path;
            String begion_address = item.begion_address;
            String end_address = item.end_address;
            String order_add_time = item.order_add_time;
            String order_id = item.order_id;
            //金额
            String order_money = item.order_money;
            helper.initText(R.id.item_tv_time,order_add_time);
            helper.initText(R.id.item_order_num,"订单号:"+order_id);
            helper.initText(R.id.item_tv_start,begion_address);
            helper.initText(R.id.item_tv_end,end_address);
            helper.initText(R.id.item_tv_money,"价格:"+order_money+"元");
            helper.initText(R.id.item_tv_prder_status,"订单状态:已完成");
            ImageView imageView =
                    helper.initImage(mContext, R.id.item_iv_1);
            imageView.setVisibility(View.INVISIBLE);



//            LogUtils.i("现在的" + position + "内容是" + item);
//            if (item.bidding_files.size() > 0) {
//                oder_04.ResultBean.BiddingFilesBean biddingFilesBean = item.bidding_files.get(0);
//                String file_path = biddingFilesBean.file_path;
//                helper.initImage(mContext, R.id.item_order_facebook_item_sellerImg, netWork.serverURL + file_path);
//            }
//            helper.initText(R.id.item_order_tv_phone, item.phone1);
//            helper.initText(R.id.item_tv_Order_status, item.status);
//            LinearLayout linearLayout = helper.initLinearLayout(mContext, R.id.ll_mid);

        }
//        else if (i1 == R.layout.item_photo_layout_02) {
//        } else if (i1 == R.layout.item_view_13) {
//        }



    }



    @Override
    public void onLoad() {
        mListViewLoadMore.listViewLoadMore(type);
    }
}
