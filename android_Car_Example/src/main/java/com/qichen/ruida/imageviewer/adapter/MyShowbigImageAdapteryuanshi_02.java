package com.qichen.ruida.imageviewer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.qichen.Utils.LogUtils;
import com.qichen.ruida.R;
import com.qichen.ruida.imageviewer.View.ImageViewerr;

import java.util.ArrayList;
import java.util.List;

/**
 * 看大图的ViewPager 原始
 *///
public class MyShowbigImageAdapteryuanshi_02 extends PagerAdapter {
    public int mode=0;
    private ArrayList<String> paths = new ArrayList<String>();
    private List<View> viewlist;
    private Context c;
    private LayoutInflater mInflater;
    int postion;
    private ImageViewerr tutu;
    public MyShowbigImageAdapteryuanshi_02(Context context, int postion, int mode) {
        this.postion = postion;
        viewlist = new ArrayList<View>();
        c = context;
        mInflater = LayoutInflater.from(c);
        this.mode = mode;
    }

    public void setData( ArrayList<String> data) {


        paths = data;
        initSimpleDraweeView();
        notifyDataSetChanged();
    }

    /**
     * 给集合 添加数据的方法
     */
    private void initSimpleDraweeView() {
        int size = paths==null?0:paths.size();

        for (int i = 0; i < size; i++) {
            //循环添加给集合
            //这个item里面必须是自定义的View ImageViewerr 要不然出现在左上角
            View inflate = mInflater.inflate(R.layout.item_tutu_02, null);
            viewlist.add(inflate);
        }
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewlist.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {



        View view = viewlist.get(position);

       tutu = (ImageViewerr) view.findViewById(R.id.APPtututu);

         Uri parse = Uri.parse(paths.get(position));
        LogUtils.i("我的uri 是"+parse);



//        if (mode == photoView.LOCAL_MODE){
//            parse = Uri.parse("file://"+paths.get(position));
//           Bitmap resizeBitmap = BitmapUtils.getResizeBitmap(c, parse, 350, true);
//           tutu.setImageBitmap(resizeBitmap);
//            ImageViewerAttacher imageViewerAttacher = new ImageViewerAttacher(tutu);
//            LogUtils.i("我现在点开的是本地模式"+parse);
//        }
//        if (mode == photoView.NET_MODE){
//
//            LogUtils.i("我现在点开的是网络模式"+parse);
//            Glide
//                    .with(c)
//                    .load(parse)
//
//                    .into(tutu);
//            ImageViewerAttacher imageViewerAttacher = new ImageViewerAttacher(tutu);
//        }
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }


}
