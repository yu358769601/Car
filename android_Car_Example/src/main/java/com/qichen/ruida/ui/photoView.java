package com.qichen.ruida.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qichen.Utils.LogUtils;
import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.imageviewer.adapter.MyShowbigImageAdapteryuanshi_02;
import com.qichen.ruida.view.imagelochViewPager0;
import com.qichen.ruida.view.imagelochViewPager_02;
import com.qichen.ruida.view.initAction_Bar;

import java.util.ArrayList;

public class photoView extends BaseActivity {
    //  //模式 0 是本地模式  1是网络模式
    public static int LOCAL_MODE = 0;
    public static int NET_MODE = 1;
    private initAction_Bar mRelativeLayout_title;
    private imagelochViewPager0 vp_image;

    //如果传过来集合你得告诉我你点了第几号
    int postion = 0;

    public int mode =0;
    //自定义的ViewPager
    // private imagelochViewPager vp_lochshowbigImage = null;
    //装ViewPager的适配器
    //  private MyShowbigImageAdapteryuanshi_02 myShowbigImageAdapter =null;
   // private List<UploadFile_02> localList;
    private Bundle extras;
    private ArrayList<String> networkList;
    private ArrayList<String> imagePath;
    private MyShowbigImageAdapteryuanshi_02 myShowbigImageAdapter;
    private imagelochViewPager_02 vp_lochshowbigImage;
    private initAction_Bar mBase_action_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getbox();
        initView();
    }

    private void getbox() {
        extras = getIntent().getExtras();
        postion = extras.getInt("position");
        LogUtils.i("之前那个页面点选的号码是" + postion);
        //获取本地图片的集合
        //localList = (List<UploadFile>) extras.getSerializable("list");
        //获取网络图片的集合
        networkList = extras.getStringArrayList("arrayList");
        initData();
    }

    @Override
    public int getlayouXML() {
        return R.layout.activity_photo_view;
    }

    @Override
    public void initView() {
        mRelativeLayout_title = (initAction_Bar) findViewById(R.id.relativeLayout_title);
        mRelativeLayout_title.setCallBack(new initAction_Bar.Action_bar_call_back() {
            @Override
            public void getAction_barView_backbutton(Button button) {

            }

            @Override
            public void getAction_barView_title(TextView textView) {
                textView.setText("查看图片");
            }
        });
        vp_image = (imagelochViewPager0) findViewById(R.id.vp_image);

        initData();
    }

    public void initData() {

//        imagePath = new ArrayList<>();
//        //如果localList 有值说明是本地模式
//        if (localList != null) {
//            //本地的
//            for (int i = 0; i < localList.size(); i++) {
//                String localPath = localList.get(i).getLocalPath();
//                imagePath.add(localPath);
//            }
//            mode = LOCAL_MODE;
//            LogUtils.i("本地过来的" + imagePath);
//        }
//        if (networkList != null) {
//            for (int i = 0; i < networkList.size(); i++) {
//                String s = networkList.get(i);
//                imagePath.add(NetMan.BASE_URL + s);
//            }
//            LogUtils.i("网络过来的" + imagePath);
//            mode = NET_MODE;
//        }


        initAdapter();
    }
    private void initAdapter() {
        myShowbigImageAdapter = new MyShowbigImageAdapteryuanshi_02(this, postion, mode);

        myShowbigImageAdapter.setData(imagePath);
        vp_lochshowbigImage.setAdapter(myShowbigImageAdapter);
        vp_lochshowbigImage.setVisibility(View.VISIBLE);
        vp_lochshowbigImage.setCurrentItem(postion);

    }

    @Override
    public void initListener() {

    }
}
