package car.com.wlc.cardemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.CityBean;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.StyleNameBean;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.DatabaseOpenHelper;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.utils.ToastUtil;
import car.com.wlc.cardemo.utils.city.CitySortModel;
import car.com.wlc.cardemo.zxing.activity.CaptureActivity;

/**
 *  添加车辆
 */
public class AddCarActivity extends AppActivity implements View.OnClickListener{


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private TextView pailiang;
    private TextView changeSpeed;
    private TextView oilType;
    private TextView textTime;
    private EditText mLpno;
    private TextView mBrand;
    private EditText mCarName;
    private TextView mVin;
    private final int VEHICLEBRANDINFO=101;
    private final int QR_CODE=10010;
    private int fueltype;
    private int transmissionType;
    private String mBrandId;
    private CityBean cityBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        initView();
    }

    /*
            初始化控件
     */
    private void initView() {
        Toolbar toolBar = (Toolbar) findViewById(R.id.add_toolbar);
         pailiang = (TextView) findViewById(R.id.pailiang_car);
        changeSpeed = ((TextView) findViewById(R.id.change_speed));
        oilType = ((TextView) findViewById(R.id.oil_type));
        textTime = ((TextView) findViewById(R.id.shangpai_time));
        mLpno = ((EditText) findViewById(R.id.car_lpno));
        mBrand = ((TextView) findViewById(R.id.car_brand));
        mCarName = ((EditText) findViewById(R.id.car_idname));
        mVin = ((TextView) findViewById(R.id.car_vin));
        findViewById(R.id.qr_code).setOnClickListener(this);

        toolBar.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        toolBar.setTitle("添加车辆");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pailiang.setOnClickListener(this);
        changeSpeed.setOnClickListener(this);
        oilType.setOnClickListener(this);
        textTime.setOnClickListener(this);
        mBrand.setOnClickListener(this);
        findViewById(R.id.btn_addvehicle).setOnClickListener(this);
    }

    /**
     * 排量
     */

    private void showCustomDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.settingpailiang_pop, null);
        view.findViewById(R.id.trasmiss_layout).setVisibility(View.GONE);
       view.findViewById(R.id.diapass_layout).setVisibility(View.VISIBLE);
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        final EditText customEdit = (EditText) view.findViewById(R.id.edit_pailiang);
        final TextView isText = (TextView) view.findViewById(R.id.text_iswoluan);
        CheckBox check = (CheckBox) view.findViewById(R.id.cb_isware);
        Button btnSure = (Button) view.findViewById(R.id.btn_sure);
        Button btnCancle = (Button) view.findViewById(R.id.btn_cancle);

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = customEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(content)){
                    String s = isText.getText().toString();
                    pailiang.setText(content+s);
                    dialog.dismiss();
                }

            }
        });

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isText.setText("T");
                }else
                    isText.setText("L");
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pailiang_car:
                showCustomDialog();
            break;
            case R.id.change_speed:
                showChangeSpeed();
                break;
            case R.id.oil_type:
                showoilType();
                break;
            case  R.id.shangpai_time:
                showCalendar();
                break;
            case R.id.btn_addvehicle:
                addVehcile();
                break;
            case R.id.car_brand:
                getDate();

                break;
            case R.id.qr_code:
               startActivityForResult(new Intent(this, CaptureActivity.class),QR_CODE);
                break;

        }
    }
    /**
     * 获取info
     */
    private void getDate() {
        RequestParams requestParams = new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{ \"cmd\": \"vehicleBrandInfoV3\",\"auth\": {\"devKey\": \"8da849223f31407f8602dabb54ddeb92\" },\"params\": {}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                List<CitySortModel> vehicle = JsonData.getVehicle(result);
                Intent intent=new Intent(AddCarActivity.this,VehicleInfoActivity.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("vehicleBrandInfoV3", (Serializable) vehicle);
                intent.putExtras(bundle);
                startActivityForResult(intent,VEHICLEBRANDINFO);




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
     * 燃油类型
     */
    private void showoilType() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.settingpailiang_pop, null);
        view.findViewById(R.id.trasmiss_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.diapass_layout).setVisibility(View.GONE);

        ListView mLv = (ListView) view.findViewById(R.id.trasmiss_listview);
        ((TextView) view.findViewById(R.id.text_stype)).setText("请选择燃油类型");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_tramiss_view, R.id.tramssion_item,Contact.FUELTYPE);
        mLv.setAdapter(adapter);

        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                fueltype=position;
                oilType.setText(Contact.FUELTYPE[position]);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==QR_CODE && resultCode==RESULT_OK){
            mVin.setText(data.getExtras().getString("result"));
        }
        if (requestCode==VEHICLEBRANDINFO && resultCode==RESULT_OK){
             cityBean = (CityBean) data.getSerializableExtra(Contact.CARID);
            mBrand.setText(cityBean.getName()+"-"+cityBean.getProductName()+"-"+cityBean.getStyleName());
            oilType.setText(Contact.TRANSMISSIONTYPE[cityBean.getTransmissionType()]);
            transmissionType=cityBean.getTransmissionType();
            mBrandId=cityBean.getBrandId();

            if (cityBean.getDisplacement() != null){
                pailiang.setText(cityBean.getDisplacement());
                pailiang.setClickable(false);
            }else
                pailiang.setClickable(true);
        }
    }

    /**
     * 车牌号判断
     */
    private void addVehcile() {
        if (TextUtils.isEmpty(mLpno.getText().toString())){
            ToastUtil.toastUtils(this,"请输入车牌号");
            return;
        }else if (TextUtils.isEmpty(mBrand.getText().toString())||
                TextUtils.isEmpty(pailiang.getText().toString())||
                TextUtils.isEmpty(changeSpeed.getText().toString())||TextUtils.isEmpty(oilType.getText().toString())){
                ToastUtil.toastUtils(this,"请输入车牌品名");
        }else {
            Map<String, UserInfo> data = SharedData.getData(this);
            UserInfo userInfo = data.get(Contact.USERINFO);
            if (!userInfo.isStatus()) {
                ToastUtil.toastUtils(AddCarActivity.this, "您的账号属于试用账号,不能试用此功能!");
                return;
            } else {
                if (IsNetwork.isNetworkConnected(AddCarActivity.this)) {
                    //车牌号正则表达式
//                    if (! IsPhone.IsVehicleNumber(mLpno.getText().toString().trim())){
//                        ToastUtil.toastUtils(AddCarActivity.this,"请输入正确的车牌号");
//                        return;
//                    }
                    final String mLnpoName = mLpno.getText().toString().trim();

                    //车辆别名

                    //燃油类型
                    if (fueltype==0){
                        fueltype=1;
                    }else {
                        fueltype=2;
                    }
                    RequestParams requestParams = new RequestParams(Contact.URL);
                    requestParams.setAsJsonContent(true);

                    requestParams.setBodyContent("{\"cmd\":\"addVehicle\",\"auth\":{\"devKey\":\"8da849223f31407f8602dabb54ddeb92\"" +
                            ",\"userName\":\"" +userInfo.getUserName()+ "\", \"password\": \""+userInfo.getUserPassword()+"\" },\"params\": {\"lpno\": \"" + mLnpoName + "\",\"brandId\":\""+mBrandId +"\",\"displacement\":\""+pailiang.getText().toString()+"\", \"transmissionType\": \""+transmissionType+"\"  }}");
                    Log.e("3131", "addVehcile: " + requestParams.getBodyContent());
                    x.http().post(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {

                            UserInfo userInfo = JsonData.getStatu(result);

                            if (userInfo.getResultNote().equals("Success")) {
                                ToastUtil.toastUtils(AddCarActivity.this, "添加成功");
                                StyleNameBean styleNameBean = new StyleNameBean();
                                styleNameBean.setBrandId(mBrandId);
                                styleNameBean.setStyleName(cityBean.getStyleName());

                                Intent intent = new Intent(AddCarActivity.this, UserVehicleListActivity.class);

                                VerInfoListBean bean=new VerInfoListBean();
                                bean.setLicensePlateNo(mLnpoName);
                                bean.setObjId(userInfo.getObjId());
                                bean.setPicture(cityBean.getLogoPath());
                                intent.putExtra(Contact.ADDCARKEY,bean);
                                setResult(RESULT_OK,intent);

                                try {
                                    DatabaseOpenHelper.getInstance().save(styleNameBean);
                                    DatabaseOpenHelper.getInstance().save(userInfo);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                finish();
                            }
                            else {
                                ToastUtil.toastUtils(AddCarActivity.this,userInfo.getResultNote());
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {


                            ToastUtil.toastUtils(AddCarActivity.this,"请求失败");
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }else
                    ToastUtil.toastUtils(AddCarActivity.this,"请检查网络状态");

            }


        }


    }


    /**
     * 日历
     */
    private void showCalendar() {
        View view = LayoutInflater.from(this).inflate(R.layout.calendar_layout, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        CalendarView cd = (CalendarView) view.findViewById(R.id.calenddarView);
        cd.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                textTime.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

    }

    /**
     * 变速箱类型
     */
    private void showChangeSpeed() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.settingpailiang_pop, null);
        view.findViewById(R.id.trasmiss_layout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.diapass_layout).setVisibility(View.GONE);

        ListView mLv = (ListView) view.findViewById(R.id.trasmiss_listview);
        ((TextView) view.findViewById(R.id.text_stype)).setText("请选择变速箱类型");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.layout_tramiss_view, R.id.tramssion_item,Contact.TRANSMISSIONTYPE);
        mLv.setAdapter(adapter);

        final AlertDialog dialog = builder.create();
        dialog.setView(view);
        dialog.show();
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeSpeed.setText(Contact.TRANSMISSIONTYPE[position]);
                dialog.dismiss();

            }
        });
//
    }
}

