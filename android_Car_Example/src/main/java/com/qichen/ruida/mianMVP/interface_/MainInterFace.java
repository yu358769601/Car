package com.qichen.ruida.mianMVP.interface_;

/**
 * Created by 35876 于萌萌
 * 创建日期: 13:53 . 2016年11月28日
 * 描述:
 * <p>
 * <p>
 * 备注:
 */

public interface MainInterFace {
    //获取 结果的接口
    double[] sun(float distance, int duration);
    //初始化线程池的操作
    void initThreadPool();
    //绑定一个服务
    void doBindSerive();
    //解除绑定一个服务
    void doUnbindService();
}
