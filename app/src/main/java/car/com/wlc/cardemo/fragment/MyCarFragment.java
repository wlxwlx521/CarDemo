package car.com.wlc.cardemo.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.LoginActivity;
import car.com.wlc.cardemo.activity.MeterActivity;
import car.com.wlc.cardemo.activity.RecordCarActivity;
import car.com.wlc.cardemo.adapter.MyCarAdapter;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.DatabaseOpenHelper;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCarFragment extends Fragment implements LocationSource, AMapLocationListener, View.OnClickListener {


    private static final int REQUSECODE = 1;
    private static MyCarFragment myCarFragment;
    private CheckBox mCheckCar;
    private View mMyChoose;
    private MapView mMapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    List<VerInfoListBean> list;
    View view;
    private RecyclerView mRecycler;
    private MyCarAdapter myArrayAdapter;
    private TextView mMyCarName;
    private int i = 1;
    private boolean isFirstLoc;
    private UserInfo userInfo;
    private Marker marker;
    private CheckBox pc_checkBox;
    private Double longitude;
    private Double latitude;
    private MyLocationStyle myLocationStyle;
    private double plongitude;
    private double platitude;
    private MarkerOptions markerOptions;


    public static MyCarFragment getInstance() {
        if (myCarFragment == null) {
            myCarFragment = new MyCarFragment();
        }
        return myCarFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isFirstLoc = true;
        userInfo = SharedData.getData(getContext()).get(Contact.USERINFO);

//        int i = ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
//        if (i != PackageManager.PERMISSION_GRANTED) {
//
//        }
        if (view != null)

        {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        view = inflater.inflate(R.layout.fragment_my_car, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        init();
        requestDate();
        //权限管理
        requestLocationPermission();
        return view;
    }

    private void requestLocationPermission() {
        MPermissions.requestPermissions(this, 5, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @PermissionGrant(5)
    public void requestContactSuccess() {
        initAMap();
    }

    @PermissionDenied(5)
    public void requestContactFailed() {
        Toast.makeText(getActivity(), "定位授权失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void init() {
        initAMap();

        view.findViewById(R.id.my_car_navigation).setOnClickListener(this);
        mCheckCar = ((CheckBox) view.findViewById(R.id.mycar_check));
        mMyChoose = view.findViewById(R.id.my_choose);
        mRecycler = ((RecyclerView) view.findViewById(R.id.mycar_recyler));
        mMyCarName = ((TextView) view.findViewById(R.id.mycar_name));
        myArrayAdapter = new MyCarAdapter(getContext());
        StaggeredGridLayoutManager sg = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        mRecycler.setLayoutManager(sg);
        mRecycler.setAdapter(myArrayAdapter);
        view.findViewById(R.id.mycar_menu).setOnClickListener(this);
        view.findViewById(R.id.record_car).setOnClickListener(this);

        mCheckCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!userInfo.isStatus()) {
                        showLoadDialog();
                    }
                    Log.i("info", "onCheckedChanged: " + i);
                    mMyChoose.setVisibility(View.VISIBLE);

                } else {

                    mMyChoose.setVisibility(View.GONE);
                }
            }
        });
        myArrayAdapter.setonItemClickListener(new MyCarAdapter.onItemClickListener() {
            @Override
            public void itemClick(View view, int postion) {
                String idName = list.get(postion).getIdName();
                String no = list.get(postion).getLicensePlateNo();
                showMyCarName(idName, no);
            }
        });
        pc_checkBox = (CheckBox) view.findViewById(R.id.my_car_checkBox);

        pc_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //定位车

                    Log.e("lyf", "onCheckedChanged: true" + latitude + longitude);
                    //113.247757,35.207535
//                    LatLng latLng = new LatLng(39.906901,116.397972);
//                    marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"));
                    // 设置当前地图显示为当前位置
                    if (latitude != null && longitude != null) {
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 19));
                        Log.e("lyf", "onCheckedChanged: "+latitude+";"+longitude );

//
//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
//                        Log.e("lyf", "onCheckedChanged: " + mapScreenMarkers);
//                        Marker marker = mapScreenMarkers.get(1);
//                        marker.setVisible(true);






                    }
                } else {
                    //定位人
                    if (platitude != 0 && plongitude != 0) {

                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(platitude, plongitude), 19));


//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
//
//                        Log.e("lyf", "onCheckedChanged: " + mapScreenMarkers);
//                        Marker marker = mapScreenMarkers.get(1);
//                        marker.setVisible(false);
                    }
                }
            }
        });
    }

    private void initAMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        } else {
            aMap.clear();
            aMap.setLocationSource(this);
            aMap.setMyLocationEnabled(true);
            aMap = mMapView.getMap();

        }
        initMap();
    }

    private void showMyCarName(String idName, String no) {
        if (idName != null) {
            mMyCarName.setText(idName);
        } else
            mMyCarName.setText(no);
    }

    /**
     * 设置一些amap的属性
     */
    private void initMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        //使用 aMap.setMapTextZIndex(2) 可以将地图底图文字设置在添加的覆盖物之上
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.plocation)));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


    }

    public void showLoadDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle("提示").setMessage("亲爱的用户,目前您还没有登陆，是否登录").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.create().show();
    }

    /**
     * 请求车辆信息
     */
    private void requestDate() {
        UserInfo userInfo = SharedData.getData(getContext()).get(Contact.USERINFO);
        list = null;
        try {
            list = DatabaseOpenHelper.getInstance().findAll(VerInfoListBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        if (IsNetwork.isNetworkConnected(getContext())) {
            RequestParams requestParams = new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{\"cmd\": \"userVehicleList\", \"auth\": { \"devKey\": \"8da849223f31407f8602dabb54ddeb92\", \"userName\": \"" + userInfo.getUserName() + "\",\"password\": \"" + userInfo.getUserPassword() + "\"}, \"params\": { \"objAuthType\": \"0\",\"pageNo\":0}}");
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("info", "result " + result);
                    list = JsonData.getVehicleInfoList(result);


                    VerInfoListBean bean = list.get(0);

                    if (bean.getResultNote().equals("Success")) {

                        try {
                            Log.i("info", "onSuccess: ");
                            DatabaseOpenHelper.getInstance().delete(VerInfoListBean.class);
                            list.remove(0);
                            myArrayAdapter.refreshDate(list);
                            String no = list.get(0).getLicensePlateNo();
                            String idName = list.get(0).getIdName();
                            showMyCarName(idName, no);
                            DatabaseOpenHelper.getInstance().save(bean);

                            // mMyChoose.setVisibility(View.VISIBLE);
                            for (VerInfoListBean ver : list) {

                                if (ver.getIsBind() == 1) {
                                    Log.e("lyf", "onSuccess: " + ver.getLongitude() + ver.getLatitude());
                                    longitude = Double.valueOf(ver.getLongitude());

                                    latitude = Double.valueOf(ver.getLatitude());

                                    markerOptions = new MarkerOptions();
                                    markerOptions.position(new LatLng(latitude, longitude));
                                    markerOptions.title("车");
                                    markerOptions.visible(true);
                                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_car));
                                    markerOptions.icon(bitmapDescriptor);
                                    aMap.addMarker(markerOptions);


                                }
                            }

                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.i("info", "onSuccess: 111");
                        showLoadDialog();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("9090", "onError: " + ex.getMessage());
                    //   Toast.makeText(getContext(),"服务器忙，请稍后重试",0).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            if (list != null) {
                list.remove(0);

            }
        }
    }


    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);

                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    plongitude = aMapLocation.getLongitude();
                    platitude = aMapLocation.getLatitude();
                    isFirstLoc = false;
                }
            } else {
                // ToastUtil.toastUtils(getContext(), aMapLocation.getErrorInfo());

            }
        }
    }

    /**
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        mListener = onLocationChangedListener;
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

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        mMyChoose.setVisibility(View.GONE);
        deactivate();
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_car:

                startActivity(new Intent(getContext(), RecordCarActivity.class));
                break;
            case R.id.mycar_menu:
                startActivity(new Intent(getContext(), MeterActivity.class));
                break;
            case R.id.my_car_navigation:
                if (isAvilible(getActivity(), "com.autonavi.minimap")) {
                    Intent intent = new Intent("android.intent.action.VIEW",
                            android.net.Uri.parse("androidamap://showTraffic?sourceApplication=softname&amp;poiid=BGVIS1&amp;lat=36.2&amp;lon=116.1&amp;level=10&amp;dev=0"));
                    intent.setPackage("com.autonavi.minimap");
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "检测到您没有安装高德地图应用，请先去下载安装", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

}



