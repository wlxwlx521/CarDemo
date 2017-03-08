package car.com.wlc.cardemo.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.CityAdapter;
import car.com.wlc.cardemo.javaBean.CityBean;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.utils.Base64Encode;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.IsPhone;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.utils.ToastUtil;
import car.com.wlc.cardemo.utils.city.CircleTextView;
import car.com.wlc.cardemo.utils.city.CitySortModel;
import car.com.wlc.cardemo.utils.city.PinyinComparator;
import car.com.wlc.cardemo.utils.city.PinyinUtils;
import car.com.wlc.cardemo.view.JellyInterpolator;
import car.com.wlc.cardemo.view.cityview.MyListView;
import car.com.wlc.cardemo.view.cityview.MySlideView;


public class RegActivity extends AppActivity implements MySlideView.onTouchListener, CityAdapter.onItemClickListener {

    public static List<String> pinyinList = new ArrayList<>();
    private Set<String> firstPinYin = new LinkedHashSet<>();
    private TextView mCity;
    private Toolbar mToolBar;
    private EditText passReg;
    private CheckBox checkReg, loginReg;
    private EditText phoneReg;
    private final int SUCCESS = 111;
    private final int FALSE = 112;
    private Context context = RegActivity.this;
    private View progress;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    startActivity(new Intent(context, LoginActivity.class));
                    progress.setVisibility(View.GONE);
                    finish();
                    break;
                case FALSE:
                    progress.setVisibility(View.GONE);
                    break;

            }
        }
    };
    private CityAdapter adapter;
    private TextView tvStickyHeaderView;
    private LinearLayoutManager layoutManager;
    private List<CitySortModel> cityList=new ArrayList<>();
    private MySlideView mySlideView;
    private CircleTextView circleTxt;
    private RecyclerView recyclerView;
    private AlertDialog dialog;
    private float values[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        values=fillRandomValues(15,100,10);

        mToolBar = ((Toolbar) findViewById(R.id.toolbar_reg));

        mToolBar.setNavigationIcon(R.mipmap.em_mm_title_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegActivity.this, LoginActivity.class));
                finish();

            }
        });
        mToolBar.setTitle("用户注册");
        mToolBar.setTitleTextColor(Color.WHITE);
        initView();
        startView();
    }
    private float[] fillRandomValues(int length, int max, int min) {
        Random random = new Random();

        float[] newRandomValues = new float[length];
        for (int i = 0; i < newRandomValues.length; i++) {
            newRandomValues[i] = random.nextInt(max - min + 1) - min;
        }
        return newRandomValues;
    }
    private void initView() {
        passReg = ((EditText) findViewById(R.id.password_reg));
        phoneReg = ((EditText) findViewById(R.id.phone_reg));
        progress = findViewById(R.id.reg_progress);
        CheckBox mBntPunum = ((CheckBox) findViewById(R.id.btn_passnum));
        checkReg = ((CheckBox) findViewById(R.id.check_reg));
        loginReg = ((CheckBox) findViewById(R.id.login_reg));
        mCity = ((TextView) findViewById(R.id.city_reg));
        mBntPunum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    passReg.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passReg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        mCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCity();
            }
        });

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.city_diaglog, null);

        builder.setView(view);
        initCityView(view);
         dialog = builder.create();

        dialog.show();

    }

    private void initCityView(View view) {
        mySlideView = (MySlideView) view.findViewById(R.id.my_slide_view);
        circleTxt = (CircleTextView) view.findViewById(R.id.my_circle_view);
        tvStickyHeaderView = (TextView) view.findViewById(R.id.tv_sticky_header_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_sticky_example);
        TextView locationCity = (TextView) view.findViewById(R.id.location_city);
//        view.findViewById(R.id.location_layout).setVisibility(View.VISIBLE);
//        initHeadView(view);
        mySlideView.setPingyinList(pinyinList);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CityAdapter(this,1);
        recyclerView.setAdapter(adapter);

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

    private void  initHeadView(View view) {
        view.findViewById(R.id.hot_city_layout).setVisibility(View.VISIBLE);
        MyListView mGvCity = (MyListView)view.findViewById(R.id.myList_view);
        final String[] datas = getResources().getStringArray(R.array.city);
        final ArrayList<String> cityList = new ArrayList<>();
        for (int i = 0; i < datas.length; i++) {
            cityList.add(datas[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.gridview_item, R.id.tv_city, datas);
        mGvCity.setAdapter(adapter);
        mGvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCity.setText(datas[i]);
                dialog.cancel();
            }
        });
    }

    private void startView() {
        if (loginReg.isClickable()) {
            loginReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String password = passReg.getText().toString().trim();
                    final String phone = phoneReg.getText().toString().trim();
                    if (TextUtils.isEmpty(phone)) {
                        ToastUtil.toastUtils(context, "请输入手机号码");
                        return;
                    } else if (IsPhone.checkPhoneNumber(phone) && !TextUtils.isEmpty(password) && IsPhone.checkPassWord(password)) {
                        final String encodePass = Base64Encode.encryption(password);
                        progress.setVisibility(View.VISIBLE);
                        progressAnimator(progress);
                        if (IsNetwork.isNetworkConnected(context)) {
                            RequestParams requestParams = new RequestParams("https://58.215.50.52:38443/openapi/openapi");
                            requestParams.setAsJsonContent(true);
                            requestParams.setBodyContent("{ \"cmd\": \"register\", \"auth\": {\"devKey\": \"8da849223f31407f8602dabb54ddeb92\" },  \"params\": {\"userName\": \"" + phone + "\", \"password\": \"" + password + " \",\"email\": \" \",  \"mobile\": \"" + phone + "\"}}");
                            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    UserInfo userInfo = JsonData.getStatu(result);
                                    Log.e("TAG", "onSuccess: " + result);

                                    if (userInfo.getResultNote().equals("注册成功")) {
                                        handler.sendEmptyMessageDelayed(SUCCESS, 800);
                                        SharedData.saveData(context, phone, phone, encodePass, userInfo.getUserId(), false);
                                    } else {
                                        handler.sendEmptyMessageDelayed(FALSE, 800);
                                        SharedData.saveData(context, "", "", "", "", false);
                                    }
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    Log.e("TAG", "onError: " + ex.getMessage());
                                }

                                @Override
                                public void onCancelled(CancelledException cex) {
                                }

                                @Override
                                public void onFinished() {
                                }
                            });

                        } else
                            ToastUtil.toastUtils(context, "请检查网络");

                    } else if (IsPhone.checkPhoneNumber(phone) && TextUtils.isEmpty(password) || IsPhone.checkPassWord(password)) {

                        ToastUtil.toastUtils(context, "请输入6-20位的号码");
                    } else {
                        ToastUtil.toastUtils(context, "请输入正确的手机号码");
                    }
                }
            });
        }
        checkReg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    loginReg.setClickable(true);
                    loginReg.setBackgroundColor(Color.parseColor("#7cb3e6"));

                } else {

                    loginReg.setClickable(false);
                    loginReg.setBackgroundColor(Color.parseColor("#FFBFBFBF"));

                }
            }
        });
    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(100);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }


    @Override
    public void itemClick(int i) {
        mCity.setText(cityList.get(i).getCityName());
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
}
