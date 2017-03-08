package car.com.wlc.cardemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.adapter.RecordAdapter;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.PressDataBean;
import car.com.wlc.cardemo.utils.DateUtils;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.ToastUtil;
import car.com.wlc.cardemo.utils.VehicleTrackIntf;

public class RecordCarActivity extends AppActivity implements
        View.OnClickListener {

    private ImageView mCarLogo;
    private Context context=RecordCarActivity.this;
    private TextView mCarName;
    private ListView mLv;
    private RecordAdapter recordAdapter;
    private View progress;
    private TextView isShow;
    private List<VehicleTrackIntf > mList=new ArrayList<>();
    private TextView mDateAdd;
    private TextView mDateDec;
    private TextView mTextDate;
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_car);

        init();

    }

    private void init() {

        Date date=new Date(System.currentTimeMillis());
        final String time = df.format(date);
        String endTime = DateUtils.addTime(time,1,df);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolBar_layout);
        mCarLogo = ((ImageView) findViewById(R.id.image_car_logo));
        mDateAdd = ((TextView) findViewById(R.id.date_add));
        mDateDec = ((TextView) findViewById(R.id.date_dec));
        mTextDate = ((TextView) findViewById(R.id.text_date));
        mTextDate.setText(time);
        mCarName = ((TextView) findViewById(R.id.car_name));
        mLv = ((ListView) findViewById(R.id.mycar_listview));
        isShow = ((TextView) findViewById(R.id.text_isshow));
        progress = findViewById(R.id.progress_layout);
        progress.setVisibility(View.VISIBLE);
        mCarLogo.setOnClickListener(this);
        recordAdapter = new RecordAdapter(this);
        mLv.setAdapter(recordAdapter);

        requestRecordTime(time,endTime);

        mToolBar.setTitle("行车记录");
        mToolBar.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VehicleTrackIntf intf = mList.get(position);
                Log.e("1314", "VehicleTrackIntf: " + intf.toString());
                Intent intent = new Intent(context,MakersActivity.class);
                intent.putExtra("VehicleTrackIntf",intf);
                startActivity(intent);
            }
        });

        //日期设置
        mDateDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = mTextDate.getText().toString();
                String desTime = DateUtils.desTime(date,1,df);
                mTextDate.setText(desTime);
                isShow.setVisibility(View.GONE);
                mLv.setVisibility(View.GONE);
                mList.clear();
                requestRecordTime(desTime,date);
            }
        });
        mDateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = mTextDate.getText().toString();
                if (time.equals(date)){
                    ToastUtil.toastUtils(context,"您选择的查询时间已超过当前时间范围");
                    return;
                }else{
                    String addTime = DateUtils.addTime(date,1,df);
                    mTextDate.setText(addTime);
                    String s = DateUtils.addTime(date, 2, df);
                    mLv.setVisibility(View.GONE);
                    isShow.setVisibility(View.GONE);
                    mList.clear();
                    requestRecordTime(addTime,s);

                }

            }
        });
        mTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();



            }
        });

    }
    private void showCalendar() {
        View view = LayoutInflater.from(this).inflate(R.layout.calendar_layout, null);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        CalendarView cd = (CalendarView) view.findViewById(R.id.calenddarView);
        cd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth,0,0,0);
                String time = df.format(calendar.getTime());
                mTextDate.setText(time);

                String endTime = DateUtils.addTime(time, 1, df);

                alertDialog.dismiss();
                mLv.setVisibility(View.GONE);
                isShow.setVisibility(View.GONE);
                mList.clear();
                requestRecordTime(time,endTime);
            }
        });
        alertDialog.show();

    }

    private void trackSegListWithTime(String start,String end) {
        if (IsNetwork.isNetworkConnected(getApplicationContext())){
            RequestParams requestParams=new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{ \"cmd\": \"trackSegListWithTime\", \"auth\": {\"devKey\": \""+ Contact.KEY+"\",\"userName\": " +
                    "\"18300647276\", \"password\": \"lolo88550845\"}, \"params\": { \"objId\":\"17010414091763141\", \"segType\": \"1\",\"beginTime\":\""+start+"\", \"endTime\":\""+end+"\"}}");
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    VehicleTrackIntf intf = JsonData.getVehicleTrackIntf(result);
                    mList.add(intf);

                    recordAdapter.refreshData(mList);


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
        }else {
            ToastUtil.toastUtils(getApplicationContext(),"请检查网络状态");
        }



    }
    /**
     * 请求数据
     */
    private void requestRecordTime(String start,String end) {
        progress.setVisibility(View.VISIBLE);

        if (IsNetwork.isNetworkConnected(this)){
            RequestParams requestParams=new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{ \"cmd\": \"segTrackPressData\", \"auth\": {\"d" +
                    "evKey\": \""+ Contact.KEY+"\",\"userName\": " +
                    "\"18300647276\", \"password\": \"lolo88550845\"}, \"params\": { \"objId\":\"17010414091763141\", \"beginTime\":\""+start+" 00:00:00\", \"endTime\":\""+end+" 00:00:00\"}}");
            Log.e("er56", "requestRecordTime: " + requestParams.getBodyContent());
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("4578", "result: " + result);
                    List<PressDataBean> beanList = JsonData.getPressDataBean(result);
                    progress.setVisibility(View.GONE);
                    if (beanList==null) {

                        Log.e("4567", "onSuccess: " + "24556");
                        isShow.setVisibility(View.VISIBLE);
                    } else {
                        mLv.setVisibility(View.VISIBLE);
                        isShow.setVisibility(View.GONE);
                        getAddress(beanList);
                    }
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    progress.setVisibility(View.GONE);
                    Log.e("TAG", "onError: " + ex.getMessage());
                }
                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }else {
            progress.setVisibility(View.GONE);
           isShow.setText("请检查网络状态");
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_car_logo:
                break;
        }
    }


    /**
     * 转换地理
     * @param list
     */
    public void getAddress(List<PressDataBean> list) {
        for (int i = 0; i < list.size(); i++) {
            PressDataBean bean = list.get(i);
            trackSegListWithTime(bean.getStartTime(),bean.getEndTime());

        }
        progress.setVisibility(View.GONE);


    }


}

