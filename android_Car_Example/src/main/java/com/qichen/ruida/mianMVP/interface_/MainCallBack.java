package com.qichen.ruida.mianMVP.interface_;

/**
 * Created by 35876 于萌萌
 * 创建日期: 15:08 . 2016年11月28日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public interface  MainCallBack {

    void succeed(String string);
    void Failure(String string);

    void getOrder_id(String mOder_id);
    void getOrder_state(String mOrder_state);

}
