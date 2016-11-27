package com.qichen.ruida.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator 于萌萌
 * 创建日期: 12:32 . 2016年08月15日
 * 描述:
 * <p/>
 * <p/>
 * 备注:
 */
public class imagelochViewPager0 extends ViewPager {
    public imagelochViewPager0(Context context) {
        super(context);
    }

    public imagelochViewPager0(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        boolean b = false;
        try {
            b = super.onInterceptTouchEvent(arg0);
        } catch (Exception e) {

        }
        return b; //网上看的方法是直接返回false，但是会导致ViewPager翻页有BUG
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        try {
            super.onTouchEvent(arg0);
        } catch (Exception e) {

        }
        return false;
    }
}
