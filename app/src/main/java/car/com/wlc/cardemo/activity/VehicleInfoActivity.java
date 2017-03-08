package car.com.wlc.cardemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.CityAdapter;
import car.com.wlc.cardemo.adapter.MyArrayAdapter;
import car.com.wlc.cardemo.adapter.MyBrandAdapter;
import car.com.wlc.cardemo.adapter.MyStpeAdapter;
import car.com.wlc.cardemo.javaBean.CityBean;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.city.CitySortModel;
import car.com.wlc.cardemo.utils.city.PinyinComparator;
import car.com.wlc.cardemo.utils.city.PinyinUtils;
import car.com.wlc.cardemo.view.cityview.MySlideView;

public class VehicleInfoActivity extends AppActivity implements MySlideView.onTouchListener ,CityAdapter.onItemClickListener{

    public  List<String> pinyinList = new ArrayList<>();
    private PinyinComparator pinyinComparator;
    public List<CitySortModel> cityListV3=new ArrayList<>();
    private MySlideView mySlideView;
    private String[] cityName={"大众","现代","马自达","雪佛兰","别克","宝马","丰田","福特"};
    private Set<String> firstPinYin = new LinkedHashSet<>();
    private RecyclerView recyclerView;
    private TextView tvStickyHeaderView;
    private CityAdapter adapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView mHeadRecycler;
    private MyArrayAdapter myArrayAdapter;
    private List<CitySortModel> stypeList=new ArrayList<>();
    private ListView mLv;
    private TextView circleTxt;

    private boolean isLeftLitViewShow;
    private boolean isRightIshow;
    private ListView mLv3;
    private CityBean cityBean;
    private List<CitySortModel> infoV3;
    private List<CitySortModel> headList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_brand_info);
        infoV3 = ((List<CitySortModel>) getIntent().getExtras().getSerializable("vehicleBrandInfoV3"));
        Toolbar mTool = (Toolbar) findViewById(R.id.cartype_toolbar);
        mTool.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        mTool.setTitle("添加车辆");
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initview();
        initHeadView();
        /**
         *
         */
        showvehicleStyleInfoV3();
        //mlv3的点击事件
        showStypeClick();


    }

    private void showStypeClick() {
        mLv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CitySortModel citySortModel = stypeList.get(position);
                cityBean.setStyleName(citySortModel.getCityName());
                cityBean.setDisplacement(citySortModel.getDisplacementList());
                cityBean.setTransmissionType(citySortModel.getTransmissionType());
                Intent intent = new Intent(VehicleInfoActivity.this,AddCarActivity.class);
                intent.putExtra(Contact.CARID,cityBean);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }



    private void initview() {

        pinyinList.clear();
        mLv = ((ListView) findViewById(R.id.left_listview));
        mLv3 = ((ListView) findViewById(R.id.right_listview));
        mySlideView = (MySlideView) findViewById(R.id.my_slide_view);
        pinyinComparator = new PinyinComparator();
        tvStickyHeaderView = (TextView) findViewById(R.id.tv_sticky_header_view);
        circleTxt = ((TextView) findViewById(R.id.my_circle_view));

        recyclerView = (RecyclerView) findViewById(R.id.rv_sticky_example);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CityAdapter(getApplicationContext(),0);
        mySlideView.setListener(this);

        recyclerView.setAdapter(adapter);

        for (int i = 0; i < infoV3.size(); i++) {
            CitySortModel sortModel = infoV3.get(i);
            String pingYin = PinyinUtils.getPingYin(sortModel.getCityName());
            String sortString = pingYin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setCityPinyin(sortString);
            }
        }
        Collections.sort(infoV3, pinyinComparator);

        for (CitySortModel city : infoV3) {
            firstPinYin.add(city.getCityPinyin().substring(0, 1));
        }
        for (String string : firstPinYin) {
            pinyinList.add(string);
        }
      mySlideView.setPingyinList(pinyinList);
        adapter.refresh(infoV3);
        adapter.setListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
              if (isLeftLitViewShow || isRightIshow)
                mLv.setVisibility(View.GONE);
                mLv3.setVisibility(View.GONE);
                isLeftLitViewShow=false;
                isRightIshow=false;


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

    /**
     * 获取brandId
     * @param sortModel
     */
    public void initLeftView( CitySortModel sortModel){

         final MyBrandAdapter  myBrandAdapter = new MyBrandAdapter(this);
        mLv.setAdapter(myBrandAdapter);
        RequestParams requestParams =new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{\"cmd\" : \" vehicleProductInfoV3\",\"auth\": { \"devKey\": " +
                "\"8da849223f31407f8602dabb54ddeb92\"}, \"params\":{\"brandId\" : \""+sortModel.getId()+"\"}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                List<CitySortModel> vehicle = JsonData.getVehicle(result);
                cityListV3=vehicle;
                myBrandAdapter.refreshDate(vehicle);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    private void initHeadView() {
        mHeadRecycler = ((RecyclerView) findViewById(R.id.recycler_headview));
        StaggeredGridLayoutManager sg=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);

        mHeadRecycler.setLayoutManager(sg);
        myArrayAdapter = new MyArrayAdapter(this);
        mHeadRecycler.setAdapter(myArrayAdapter);

            for (int j = 0; j <infoV3.size() ; j++) {
                    headList.add(infoV3.get(j));
        }
        myArrayAdapter.refreshDate(headList);


       myArrayAdapter.setonItemClickListener(new MyArrayAdapter.onItemClickListener() {
           @Override
           public void itemClick(View view, CitySortModel citySortModel) {
               if(isLeftLitViewShow|| isRightIshow)
                   mLv.setVisibility(View.VISIBLE);
               mLv3.setVisibility(View.GONE);
               isLeftLitViewShow=true;
               cityBean=null;
               cityBean = new CityBean();
               cityBean.setName(citySortModel.getCityName());
               cityBean.setBrandId(citySortModel.getId());
           }
       });
        mHeadRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLeftLitViewShow||isRightIshow && (dx>3||dx<-3))
                    mLv3.setVisibility(View.GONE);
                mLv.setVisibility(View.GONE);
                isRightIshow=false;
                isRightIshow= false;
            }
        });


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
        for (int i = 0; i < infoV3.size(); i++) {
            if (infoV3.get(i).getCityPinyin().equals(textView)) {
                selectPosition = i;
                break;
            }
        }
//        recyclerView.scrollToPosition(selectPosition);
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
    public void itemClick(int i) {
        if(isLeftLitViewShow|| isRightIshow)
        mLv.setVisibility(View.VISIBLE);
        mLv3.setVisibility(View.GONE);

        isLeftLitViewShow=true;
        CitySortModel sortModel = infoV3.get(i);
        initLeftView(sortModel);
        cityBean=null;
        cityBean = new CityBean();
        cityBean.setBrandId(sortModel.getId());
        cityBean.setName(sortModel.getCityName());
    }
    public  void initvehicleStyleInfoV3(CitySortModel sortModel){

        final MyStpeAdapter myBrandAdapter=new MyStpeAdapter(this);
        mLv3.setAdapter(myBrandAdapter);
        RequestParams requestParams =new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{\"cmd\" : \" vehicleStyleInfoV3\",\"auth\": { \"devKey\": " +
                "\"8da849223f31407f8602dabb54ddeb92\"}, \"params\":{\"productId\" : \""+sortModel.getId()+"\"}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                List<CitySortModel> vehicle = JsonData.getVehicle(result);
                stypeList.addAll(vehicle);
                Log.e("777", "onSuccess: " + vehicle.toString());

                myBrandAdapter.refreshDate(vehicle);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    /**
     * 显示vehicleStyleInfoV3
     */
    public void showvehicleStyleInfoV3(){
        mLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isRightIshow && scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    mLv3.setVisibility(View.GONE);

                }
                isRightIshow=false;
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               mLv.setVisibility(View.VISIBLE);
                isLeftLitViewShow=true;
                if (! isRightIshow){
                    mLv3.setVisibility(View.VISIBLE);

                }
                isRightIshow=true;

                CitySortModel citySortModel = cityListV3.get(position);
                initvehicleStyleInfoV3(citySortModel);
                cityBean.setProductId(citySortModel.getId());
                cityBean.setProductName(citySortModel.getCityName());
                cityBean.setLogoPath(citySortModel.getLogopath());

            }
        });

    }

}
