package com.qichen.Utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by 35876 于萌萌
 * 创建日期: 14:25 . 2016年10月21日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public class UtilsSetManager {
    /**
     * 方便的设置recyclerView 的管理器的方法
     *
     * @param context 上下文
     * @param mode  LinearLayoutManager.HORIZONTAL 横着的
     *              LinearLayoutManager.VERTICAL 竖着的
     *
     * @param recyclerView 需要被管理的recyclerView
     */
    public static void LinearLayoutManager(Context context ,int mode ,RecyclerView recyclerView){
        int modes =0;
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        if (mode == LinearLayoutManager.HORIZONTAL){
            modes = LinearLayoutManager.HORIZONTAL;
        }else{
            modes = LinearLayoutManager.VERTICAL;
        }
        mLayoutManager.setOrientation(modes);
        recyclerView.setLayoutManager(mLayoutManager);
    }

}
