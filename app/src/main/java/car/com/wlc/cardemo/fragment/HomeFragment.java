package car.com.wlc.cardemo.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.LoginActivity;
import car.com.wlc.cardemo.activity.ShopActivity;
import car.com.wlc.cardemo.chatmessage.car.VehicleConditionActivity;
import car.com.wlc.cardemo.chatmessage.chat.ui.ChatActivity;
import car.com.wlc.cardemo.javaBean.BannerItem;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.utils.LocationUtils;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.view.LocalImageHolderView;
import car.com.wlc.cardemo.view.WaveView;
import car.com.wlc.cardemo.zxing.activity.CaptureActivity;

import static car.com.wlc.cardemo.R.id.carfriend_chat;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, LocationSource, AMapLocationListener {
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static HomeFragment homeFragment;
    private WaveView waveView1, waveView2, waveView3;
    private int REQUSECODE = 1;
    private UserInfo userInfo;
    private ConvenientBanner mBanner;
    private TextView mLocation;
    private LatLng latLng = null;
    private FloatingActionsMenu fab_menu;
    private TextView mCityText;


    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    //存放图片的id
    private int[] imageIds = new int[]{
            R.mipmap.pscj_big, R.mipmap.qczl_big, R.mipmap.pscj_big, R.mipmap.qczl_big, R.mipmap.pscj_big,

    };
    //存放图片的标题
    private String[] titles = new String[]{
            "日常如何保养车",
            "除雾妙招，！防止冬季/雨季玻璃起雾妙招",
            "来看看你今天战胜了多少车友",
            "冬季雨季，你知道如何正常保暖吗？",
            "空调保养法则！"
    };

    private ArrayList<BannerItem> imList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LocationUtils.setPermission(Manifest.permission.LOCATION_HARDWARE, getContext());
        userInfo = SharedData.getData(getContext()).get(Contact.USERINFO);
        //显示的图片
        imList = new ArrayList();
        for (int i = 0; i < imageIds.length; i++) {
            imList.add(new BannerItem(titles[i], imageIds[i]));
        }
        inintView(view);


    }

    private void inintView(View view) {


        view.findViewById(carfriend_chat).setOnClickListener(this);
        view.findViewById(R.id.day_carstatu).setOnClickListener(this);
        mCityText = ((TextView) view.findViewById(R.id.location_text));

        mBanner = ((ConvenientBanner) view.findViewById(R.id.home_banner));
        // mLocation = ((TextView) view.findViewById(R.id.location_text));
        view.findViewById(R.id.car_shop_layout).setOnClickListener(this);
        waveView1 = (WaveView) view.findViewById(R.id.wave1);
        waveView2 = (WaveView) view.findViewById(R.id.wave2);
        waveView3 = (WaveView) view.findViewById(R.id.wave3);
        view.findViewById(R.id.day_price).setOnClickListener(this);
        view.findViewById(R.id.day_run).setOnClickListener(this);
        view.findViewById(R.id.day_carstatu).setOnClickListener(this);
        //水波视图
        waveView1.setWaveHeight(0.8f);
        waveView2.setWaveHeight(0.58f);
        waveView3.setWaveHeight(0.38f);
        waveView1.setCurrentText("80分", Color.parseColor("#3F51B5"), 46);
        waveView2.setCurrentText("58分", Color.parseColor("#3F51B5"), 46);
        waveView3.setCurrentText("38分", Color.parseColor("#3F51B5"), 46);
        view.findViewById(R.id.btn_scan).setOnClickListener(this);
        initBanner();

    }

    private void initBanner() {

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        mBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, imList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.indicator_no_select, R.drawable.indicator_select})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。//        convenientBanner.setManualPageable(false);//设置不能手动影响

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        waveView1.start();
        waveView2.start();
        waveView3.start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:

                String[] strings = {Manifest.permission.CAMERA};
                MPermissions.requestPermissions(this, 4, Manifest.permission.CAMERA);


                break;
            case R.id.car_shop_layout:

                // startActivity(new Intent(getContext(), CarShopActivity.class));
                startActivity(new Intent(getContext(), ShopActivity.class));
                break;
            case R.id.day_carstatu:
                startActivity(new Intent(getActivity(), VehicleConditionActivity.class));
                break;
            case R.id.day_price:
            case R.id.day_run:
                if (userInfo.isStatus())

                {

                } else

                {
                    showLoadDialog();
                }

                break;
            case R.id.carfriend_chat:
                startActivity(new Intent(getActivity(), ChatActivity.class));
                break;


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        mBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        mBanner.stopTurning();
    }

    @Override
    public void onStop() {
        super.onStop();
        waveView1.cancel();
        waveView2.cancel();
        waveView3.cancel();

    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mLocation.setText(aMapLocation.getCity());
                latLng = new LatLng(aMapLocation.getLongitude(), aMapLocation.getLatitude());

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorInfo());
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

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @PermissionGrant(4)
    public void requestContactSuccess() {
        startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQUSECODE);
        Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(4)
    public void requestContactFailed() {
        Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
