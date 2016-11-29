/**
 * Project Name:Android_Car_Example
 * File Name:Utils.java
 * Package Name:com.amap.api.car.example
 * Date:2015年4月7日下午3:43:05
 */

package com.qichen.ruida;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.qichen.Utils.LogUtils;
import com.qichen.Utils.UtilsToast;
import com.qichen.app.AppContext;
import com.qichen.ruida.bean.Carbean;
import com.qichen.ruida.bean.DriverPositon;
import com.qichen.ruida.bean.PeripheralInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:Utils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年4月7日 下午3:43:05 <br/>
 *
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class Utils_1 {

    private static ArrayList<Marker> markers = new ArrayList<Marker>();

    /**
     * 添加模拟测试的车的点
     */
    public static void addEmulateData(AMap amap, LatLng center) {
        if (markers.size() == 0) {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_car);

            for (int i = 0; i < 20; i++) {
                double latitudeDelt = (Math.random() - 0.5) * 0.1;
                double longtitudeDelt = (Math.random() - 0.5) * 0.1;
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.icon(bitmapDescriptor);
                markerOptions.position(new LatLng(center.latitude + latitudeDelt, center.longitude + longtitudeDelt));
                Marker marker = amap.addMarker(markerOptions);
                markers.add(marker);
            }
//        } else {
//            for (Marker marker : markers) {
//                double latitudeDelt = (Math.random() - 0.5) * 0.1;
//                double longtitudeDelt = (Math.random() - 0.5) * 0.1;
//                marker.setPosition(new LatLng(center.latitude + latitudeDelt, center.longitude + longtitudeDelt));
//
//            }
        }
    }

    /**
     * 在图表上添加点
     * @param amap 高德地图
     * @param data 装有坐标信息的对象集合
     */
    public static void addEmulateData(AMap amap, List<PeripheralInfo.DataBean> data) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.car1);
        Carbean.DataBean dataBean;
        //清除 之前的 覆盖物
        Utils_1.removeMarker();

           for (int i = 0; i < data.size(); i++) {
               PeripheralInfo.DataBean bean = data.get(i);
                 addMarkers(amap,bean, bitmapDescriptor);

           }
        //是否模拟点
        if (false){
            for (int i = 0; i < 10; i++) {
                double latitudeDelt = (Math.random() - 0.5) * 0.1;
                double longtitudeDelt = (Math.random() - 0.5) * 0.1;
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.icon(bitmapDescriptor);
                markerOptions.position(new LatLng(45.7389670+latitudeDelt , 126.5699260+longtitudeDelt));
                Marker marker = amap.addMarker(markerOptions);
                markers.add(marker);
            }
        }


        LogUtils.i("这个没有路过吗");
        UtilsToast.showToast(AppContext.getApplication(), "共发现周边"+data.size()+"个司机师傅"+"请耐心等待");
       }

    private static void addMarkers(AMap amap, PeripheralInfo.DataBean bean,BitmapDescriptor bitmapDescriptor) {
        //double latitudeDelt = (Math.random() - 0.5) * 0.1;
       // double longtitudeDelt = (Math.random() - 0.5) * 0.1;

        double begion_lon = Double.parseDouble(bean.positon_lon);
        double begion_lat = Double.parseDouble(bean.positon_lat);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);

        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.icon(bitmapDescriptor);
        LogUtils.i("添加了"+"begion_lon"+"\t"+begion_lon+"\n"+"begion_lat"+"\t"+begion_lat);
                                // 坑爹 先纬度  后精度
        markerOptions.position(new LatLng(begion_lat,begion_lon));
        Marker marker = amap.addMarker(markerOptions);
        LogUtils.i("添加了"+bean.positon_id);
       // marker.setTitle(bean.positon_id);
        markers.add(marker);
    }

    public static boolean removeMarker(){
        if (markers.size() == 0){
            return false;
        }
        for (int i = 0; i < markers.size(); i++) {
            Marker marker = markers.get(i);
            marker.remove();
        }
        markers.clear();
        return true;
    }

    /**
     * 添加单个 的覆盖物
     * @param amap
     * @param driverPositon
     * @param bitmapDescriptor
     */
    private static void addMarker(AMap amap, DriverPositon driverPositon, BitmapDescriptor bitmapDescriptor){
        double begion_lon = Double.parseDouble(driverPositon.positon_lon);
        double begion_lat = Double.parseDouble(driverPositon.positon_lat);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);

        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.icon(bitmapDescriptor);
        LogUtils.i("添加了"+"begion_lon"+"\t"+begion_lon+"\n"+"begion_lat"+"\t"+begion_lat);
        // 坑爹 先纬度  后精度
        markerOptions.position(new LatLng(begion_lat,begion_lon));
        Marker marker = amap.addMarker(markerOptions);
        LogUtils.i("添加了"+driverPositon.driver_id);
        // marker.setTitle(bean.positon_id);
        markers.add(marker);
    }

    //通过单独的 司机对象去 设置 覆盖物
    public static void addEmulateData(AMap mAmap, DriverPositon driverPositon) {
        BitmapDescriptor bitmapDescriptor;
        //清除 之前的 覆盖物
        Utils_1.removeMarker();
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.car1);
        addMarker(mAmap,driverPositon,bitmapDescriptor);




    }
}
