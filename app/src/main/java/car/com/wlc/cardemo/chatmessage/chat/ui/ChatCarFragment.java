package car.com.wlc.cardemo.chatmessage.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.chatmessage.chat.models.User;
import car.com.wlc.cardemo.utils.AMapUtil;

import static android.R.attr.fragment;
import static android.R.attr.thickness;
import static android.R.id.list;


/**
 * Created by Administrator on 2017/3/18.
 */

public class ChatCarFragment extends Fragment implements AMapLocationListener,LocationSource,AMap.OnMarkerClickListener{
    private View view;
    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isFirstLoc;
    private static ChatCarFragment fragment;

    public static ChatCarFragment newInstance() {
        if (fragment ==null){
            fragment = new ChatCarFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoc=true;
        if (view != null){
            if (view.getParent() != null){
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
      view = inflater.from(getContext()).inflate(R.layout.fragment_chat_car,container,false);
        mapView = ((MapView) view.findViewById(R.id.chat_map));
        mapView.onCreate(savedInstanceState);
        initAMap();
        return view;
    }
    private void initAMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        } else {
            aMap.clear();
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
            aMap = mapView.getMap();
        }
        initMap();
    }
    /**
     * 设置一些amap的属性
     */
    private void initMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //使用 aMap.setMapTextZIndex(2) 可以将地图底图文字设置在添加的覆盖物之上
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        MyLocationStyle style = new MyLocationStyle();

            style.strokeColor(Color.argb(0,0,0,0));
        style.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(style);//设置定位蓝点的Style
//
        setfromandtoMarker();

    }
    private void setfromandtoMarker() {
       aMap.addMarker(new MarkerOptions().title("车马炮")
                .position(new LatLng(35.21545,113.251863)).visible(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_car)));

        aMap.addMarker(new MarkerOptions().title("车马炮").visible(true)
                .position(new LatLng(35.2198,113.260595))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_car)));
        aMap.addMarker(new MarkerOptions().title("车马炮").visible(true)
                .position(new LatLng(35.219225,113.258942))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_car)));

    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);

                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

                    isFirstLoc = false;
                }
            } else {
                // ToastUtil.toastUtils(getContext(), aMapLocation.getErrorInfo());

            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
           mListener= onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();

            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置定位参数

            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        if (mlocationClient !=null){
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient=null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mlocationClient != null){
            mlocationClient.onDestroy();
        }
        mlocationClient=null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getContext(), MessengerActivity.class);
        intent.putExtra("user",new User(0,"车马炮",R.mipmap.usercenter_demo_headimage));
        startActivity(new Intent(getContext(),MessengerActivity.class));
        return false;
    }
}
