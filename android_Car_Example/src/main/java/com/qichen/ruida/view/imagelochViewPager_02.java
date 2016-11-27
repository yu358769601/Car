package com.qichen.ruida.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by Administrator 于萌萌
 * 创建日期: 12:32 . 2016年08月15日
 * 描述:
 * <p/>
 * <p/>
 * 备注:
 */
public class imagelochViewPager_02 extends ViewPager {
    private ViewGroup parent;
    public imagelochViewPager_02(Context context) {
        super(context);
    }

    public imagelochViewPager_02(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        boolean b = false;
//        try {
//            b = super.onInterceptTouchEvent(arg0);
//        } catch (Exception e) {
//
//        }
//        return b; //网上看的方法是直接返回false，但是会导致ViewPager翻页有BUG
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent arg0) {
//        try {
//            super.onTouchEvent(arg0);
//        } catch (Exception e) {
//
//        }
//        return false;
//    }
public void setNestedpParent(ViewGroup parent) {

    this.parent = parent;
}
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (parent != null) {
            Log.i("dispatch_touch_event","---"+ev.getAction());
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (parent != null) {
            Log.i("onintercepte_touch_eve","---"+ev.getAction());
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (parent != null) {
            Log.i("on_touch_event","---"+ev.getAction());
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }
}
