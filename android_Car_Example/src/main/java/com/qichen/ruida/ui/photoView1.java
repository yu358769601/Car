package com.qichen.ruida.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.qichen.ruida.R;
import com.qichen.ruida.base.BaseActivity;
import com.qichen.ruida.imageviewer.View.ImageViewerAttacher;
import com.qichen.ruida.imageviewer.View.ImageViewerr;
import com.qichen.ruida.sizeUtils.BitmapUtils;
import com.qichen.ruida.view.initAction_Bar;

public class photoView1 extends BaseActivity {

    private initAction_Bar mRelativeLayout_title;
    private ImageViewerr mAPPtututu1;
    private int mIntExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getbox();
        initView();
    }

    private void getbox() {
        mIntExtra = getIntent().getIntExtra("shuoming", 0);

    }

    @Override
    public int getlayouXML() {
        return R.layout.activity_photo_view1;
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
        mAPPtututu1 = (ImageViewerr) findViewById(R.id.APPtututu1);

        initData();
    }

    @Override
    public void initData() {
            if (R.drawable.shuoming==mIntExtra){
                Bitmap bitmap = BitmapUtils.readResource(this, R.drawable.shuoming);
               // Bitmap resizeBitmap = BitmapUtils.getResizeBitmap(c, parse, 350, true);
                mAPPtututu1.setImageBitmap(bitmap);
                ImageViewerAttacher imageViewerAttacher = new ImageViewerAttacher(mAPPtututu1);
                //mAPPtututu1.setBackgroundResource(R.drawable.shuoming);
            }
    }

    @Override
    public void initListener() {

    }
}
