package car.com.wlc.cardemo.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import car.com.wlc.cardemo.R;
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
import car.com.wlc.cardemo.utils.ToastUtil;

import static android.content.ContentValues.TAG;

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

    public static MyCarFragment getInstance() {
        if (myCarFragment == null) {
            myCarFragment = new MyCarFragment();
        }
        return myCarFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isFirstLoc=true;
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
        Log.e(TAG, "requestLocationPermission: " + "申请授权");

        MPermissions.requestPermissions(this, 5, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    @PermissionGrant(5)
    public void requestContactSuccess() {

        Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
        initAMap();


    }

    @PermissionDenied(5)
    public void requestContactFailed() {
        Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void init() {
        initAMap();
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
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);


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

                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.i("info", "onSuccess: 111");
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

                    isFirstLoc = false;
                }
            } else {
                ToastUtil.toastUtils(getContext(), aMapLocation.getErrorInfo());

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
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}



