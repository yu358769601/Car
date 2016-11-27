package com.qichen.ruida.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.view.initAction_Bar;

//关于我们详情activity
public class Activity1 extends BaseActivity {

    private LinearLayout mLl_1;
    private LinearLayout mLl_2;
    private ImageView image_1;
    private int mTag;
    private TextView mTv_tishi;
    private LinearLayout mLl_3;
    private initAction_Bar mRelativeLayout_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBox();
        initView();
    }

    private void getBox() {
        mTag = getIntent().getIntExtra("tag", 0);

    }

    @Override
    public int getlayouXML() {
        return R.layout.activity_1;
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
                textView.setText("关于我们");
            }
        });

        mLl_1 = (LinearLayout) findViewById(R.id.ll_1);
        mLl_2 = (LinearLayout) findViewById(R.id.ll_2);
        mLl_3 = (LinearLayout) findViewById(R.id.ll_3);

        image_1 = (ImageView) findViewById(R.id.image_1);
        //mTv_tishi = (TextView) findViewById(R.id.tv_tishi);

        switch (mTag){
            case 0:
                mLl_1.setVisibility(View.VISIBLE);
                mLl_2.setVisibility(View.GONE);
                mLl_3.setVisibility(View.GONE);
                //image_1.setVisibility(View.GONE);
            break;
            case 1:
                mLl_1.setVisibility(View.GONE);
                mLl_2.setVisibility(View.VISIBLE);
                mLl_3.setVisibility(View.GONE);
                //image_1.setVisibility(View.GONE);
            break;
            case 2:
                mLl_1.setVisibility(View.GONE);
                mLl_2.setVisibility(View.GONE);
                mLl_3.setVisibility(View.VISIBLE);
                //image_1.setVisibility(View.VISIBLE);
            break;
        }

        initListener();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        if (null!=image_1){
            image_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Activity1.this, photoView1.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("shuoming", R.drawable.shuoming);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }



    }
}
