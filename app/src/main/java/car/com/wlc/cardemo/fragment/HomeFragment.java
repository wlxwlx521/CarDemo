package car.com.wlc.cardemo.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.activity.LoginActivity;
import car.com.wlc.cardemo.activity.ShopActivity;
import car.com.wlc.cardemo.adapter.CityAdapter;
import car.com.wlc.cardemo.chatmessage.car.VehicleConditionActivity;
import car.com.wlc.cardemo.chatmessage.chat.ui.ChatActivity;
import car.com.wlc.cardemo.javaBean.BannerItem;
import car.com.wlc.cardemo.javaBean.CityBean;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.utils.LocationUtils;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.utils.city.CircleTextView;
import car.com.wlc.cardemo.utils.city.CitySortModel;
import car.com.wlc.cardemo.utils.city.PinyinComparator;
import car.com.wlc.cardemo.utils.city.PinyinUtils;
import car.com.wlc.cardemo.view.LocalImageHolderView;
import car.com.wlc.cardemo.view.WaveView;
import car.com.wlc.cardemo.view.cityview.MySlideView;
import car.com.wlc.cardemo.zxing.activity.CaptureActivity;

import static android.content.ContentValues.TAG;
import static car.com.wlc.cardemo.R.id.carfriend_chat;


/**
 * wlx {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements  AMapLocationListener, View.OnClickListener, MySlideView.onTouchListener, CityAdapter.onItemClickListener {


    private static HomeFragment homeFragment;
    private WaveView waveView1, waveView2, waveView3;
    private int REQUSECODE = 1;
    private UserInfo userInfo;
    private ConvenientBanner mBanner;
    private TextView mCityText;
    private List<CitySortModel> cityList = new ArrayList<>();
    public static List<String> pinyinList = new ArrayList<>();
    private Set<String> firstPinYin = new LinkedHashSet<>();
    private AlertDialog dialog;
    private CityAdapter adapter;
    private CircleTextView circleTxt;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


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
        requestLocationPermission();
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


    private void requestLocationPermission() {
        Log.e(TAG, "requestLocationPermission: " + "申请授权");

        MPermissions.requestPermissions(this, 5, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    @PermissionGrant(5)
    public void requestLocationSuccess() {

    }

    @PermissionDenied(5)
    public void requestLocationFailed() {
        Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();


    }



    private void inintView(View view) {


        view.findViewById(R.id.carfriend_chat).setOnClickListener(this);
        view.findViewById(R.id.day_carstatu).setOnClickListener(this);
        mCityText = ((TextView) view.findViewById(R.id.location_text));
        mCityText.setOnClickListener(this);
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

    /**
     * 轮播图
     */
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
                if (userInfo.isStatus()) {

                } else {
                    showLoadDialog();
                }
                break;
            case carfriend_chat:
                startActivity(new Intent(getActivity(), ChatActivity.class));
                break;
            case R.id.location_text:
                showDialogCity();
                break;


        }

    }

    /**
     * 显示dialog
     */
    private void showDialogCity() {
        cityList.clear();
        pinyinList.clear();
        firstPinYin.clear();
        //
        //城市名字
        List<String> stringList = CityBean.getSampleContactList();
        PinyinComparator pinyinComparator = new PinyinComparator();
        for (int i = 0; i < stringList.size(); i++) {
            CitySortModel sortModel = new CitySortModel();
            String cityName = stringList.get(i);
            sortModel.setCityName(cityName);
            String pingYin = PinyinUtils.getPingYin(cityName);
            String sortString = pingYin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                sortModel.setCityPinyin(sortString);
            }
            cityList.add(sortModel);
        }
        Collections.sort(cityList, pinyinComparator);
        for (CitySortModel city : cityList) {
            firstPinYin.add(city.getCityPinyin().substring(0, 1));
        }
        for (String string : firstPinYin) {
            pinyinList.add(string);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.city_diaglog, null);
        initCityView(view);
        builder.setView(view);
        dialog = builder.create();

        dialog.show();

    }

    private void initCityView(View view) {
        MySlideView mySlideView = (MySlideView) view.findViewById(R.id.my_slide_view);
        circleTxt = (CircleTextView) view.findViewById(R.id.my_circle_view);
        final TextView tvStickyHeaderView = (TextView) view.findViewById(R.id.tv_sticky_header_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_sticky_example);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CityAdapter(getContext(), 1);
        recyclerView.setAdapter(adapter);
        mySlideView.setPingyinList(pinyinList);
        adapter.refresh(cityList);

        mySlideView.setListener(this);
        adapter.setListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                View stickyInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tvStickyHeaderView.getMeasuredWidth() / 2, tvStickyHeaderView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {
                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvStickyHeaderView.getMeasuredHeight();
                    if (transViewStatus == CityAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tvStickyHeaderView.setTranslationY(dealtY);
                        } else {
                            tvStickyHeaderView.setTranslationY(0);
                        }
                    } else if (transViewStatus == CityAdapter.NONE_STICKY_VIEW) {
                        tvStickyHeaderView.setTranslationY(0);
                    }
                }
            }
        });
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


    @PermissionGrant(4)
    public void requestCameraSuccess() {
        startActivityForResult(new Intent(getContext(), CaptureActivity.class), REQUSECODE);
        Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(4)
    public void requestCameraFailed() {
        Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void itemClick(int i) {
        mCityText.setText(cityList.get(i).getCityName());
        dialog.dismiss();
    }

    @Override
    public void showTextView(String textView, boolean dismiss) {
        if (dismiss) {
            circleTxt.setVisibility(View.GONE);
        } else {
            circleTxt.setVisibility(View.VISIBLE);
            circleTxt.setText(textView);
        }

        int selectPosition = 0;
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getCityPinyin().equals(textView)) {
                selectPosition = i;
                break;
            }
        }

        scroll2Position(selectPosition);
    }

    private void scroll2Position(int index) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (index <= firstPosition) {
            recyclerView.scrollToPosition(index);
        } else if (index <= lastPosition) {
            int top = recyclerView.getChildAt(index - firstPosition).getTop();
            recyclerView.scrollBy(0, top);
        } else {
            recyclerView.scrollToPosition(index);
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

        String city = aMapLocation.getCity();
       if (city != null){
           mCityText.setText(city);
       }
    }

}
