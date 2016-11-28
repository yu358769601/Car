package com.qichen.ruida.MVP_base;

import com.qichen.ruida.mianMVP.interface_.MainInterFace;

/**
 * Created by 35876 于萌萌
 * 创建日期: 14:48 . 2016年11月28日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */
//泛型 是 给自己的接口对象  本类的接口是 给子类实现用的
public abstract class MVP_base<T> implements MainInterFace {
    public abstract void setListener(T listener);
}
