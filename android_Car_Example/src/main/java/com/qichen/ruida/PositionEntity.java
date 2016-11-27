/**  
 * Project Name:Android_Car_Example  
 * File Name:PositionEntity.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午9:50:28  
 *  
 */

package com.qichen.ruida;

/**
 * ClassName:PositionEntity <br/>
 * Function: 封装的关于位置的实体 <br/>
 * Date: 2015年4月3日 上午9:50:28 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class PositionEntity {

	public double latitude;

	public double longitude;

	public String address;

	public String city;

//	public String road;
//
//	public String street;
//	
//	public String poiName;

	public PositionEntity() {
	}

	public PositionEntity(double latitude, double longtitude, String address, String city) {
		this.latitude = latitude;
		this.longitude = longtitude;
		this.address = address;
	}

}
