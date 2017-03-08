/**
 * 
 */
package car.com.wlc.cardemo.utils;


import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;

/**
 * 辅助工具类
 * @创建时间： 2015年11月24日 上午11:46:50
 * @项目名称： AMapLocationDemo2.x
 * @author hongming.wang
 * @文件名称: AMapUtil.java
 * @类型名称: AMapUtil
 */
public class AMapUtil {
	/**
	 *  开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP= 2;

	/**
	 * 把LatLonPoint对象转化为LatLon对象
	 */
	public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
		return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
	}
	public static String getStopTime(String time1,String time2){

		return null;
	}

}
