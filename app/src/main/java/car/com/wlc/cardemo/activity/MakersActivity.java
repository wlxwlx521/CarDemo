package car.com.wlc.cardemo.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.utils.AMapUtil;
import car.com.wlc.cardemo.utils.DrivingRouteOverLay;
import car.com.wlc.cardemo.utils.VehicleTrackIntf;

public class MakersActivity extends AppActivity implements
         RouteSearch.OnRouteSearchListener/*,LocationSource,AMapLocationListener*/
,AMap.OnMarkerClickListener,AMap.InfoWindowAdapter,AMap.OnInfoWindowClickListener{

    private MapView mMapView;
    private AMap mMap;
    private VehicleTrackIntf intf;
    private RouteSearch mRouteSearch;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private final int ROUTE_TYPE_DRIVE=2;
    private DriveRouteResult mDriveRouteResult;
    private TextView mAverageFuel;
    private TextView averageSpped;
    private TextView totalTime;
    private TextView referFue;
    private TextView totalFue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers);
        intf = (VehicleTrackIntf) getIntent().getSerializableExtra("VehicleTrackIntf");
        mStartPoint = new LatLonPoint(intf.getStartLat(),intf.getStartLng());
        mEndPoint = new LatLonPoint(intf.getEndLat(),intf.getEndLng());//dataBean.getEndLatitude(),dataBean.getStartLongitude()
        mMapView = ((MapView) findViewById(R.id.map_view));
        mMapView.onCreate(savedInstanceState);
        init();

        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
    }

    private void init() {
        if (mMap==null){
             mMap = mMapView.getMap();
        }
        mAverageFuel = ((TextView) findViewById(R.id.text_averagefuel));
        averageSpped = ((TextView) findViewById(R.id.text_averagespped));
        totalTime = ((TextView) findViewById(R.id.text_totaltime));
        referFue = ((TextView) findViewById(R.id.text_referfue));
        totalFue = ((TextView) findViewById(R.id.text_totalfue));
        mAverageFuel.setText("平均耗油："+intf.getAverageFuel());
        averageSpped.setText(intf.getTotalMileAge());
        totalTime.setText("总时长：2h");
        referFue.setText("参考油价：37.2元");
        totalFue.setText("总耗油："+intf.getTotalFuelAge());
        registerListener();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        setfromandtoMarker( AMapUtil.convertToLatLng(mStartPoint),AMapUtil.convertToLatLng(mEndPoint));
    }

    /**
     * 注册监听
     */
    private void registerListener() {

        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);

    }
    private void setfromandtoMarker(LatLng startLatLng, LatLng endLatLng) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(startLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start)));
        marker.showInfoWindow();

        mMap.addMarker(new MarkerOptions()
                .position(endLatLng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end)));



    }
    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            return;
        }
        if (mEndPoint == null) {

        }
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onStart();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        mMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths().get(0);
                    DrivingRouteOverLay drivingRouteOverlay = new DrivingRouteOverLay(this, mMap, drivePath,mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                }
            }}

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        if (marker.getTitle().equals("起点")){
            title.setText(intf.getStartTime());
        }else  if (marker.getTitle().equals("终点")){
            title.setText(
                    intf.getEndTime()
            );
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        if (marker.getTitle().equals("起点")){
            title.setText(intf.getStartTime());
        }else  if (marker.getTitle().equals("终点")){
            title.setText(intf.getEndTime());
        }

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.e("1565", "onInfoWindowClick: " + marker.getTitle());


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }
}

