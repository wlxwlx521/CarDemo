package car.com.wlc.cardemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.utils.ToastUtil;
import car.com.wlc.cardemo.utils.city.CitySortModel;
import car.com.wlc.cardemo.zxing.activity.CaptureActivity;


public class EditCarInfoActivity extends AppActivity implements View.OnClickListener {
    private List<String> vehicleName = new ArrayList<>();
    private final int PRODUCTINFO = 0;
    private final int STYLEINFO = 1;

    private final Context context = EditCarInfoActivity.this;
    private TextView mLnpos, idName, mName, brandName, styleName, displace, transmissionType, fuelstyle, emissionStd,
            registstype, service, productTime, registAgency, registtime, distance, mVin;
    private EditText owner, ownerPhone, mColor;
    private VerInfoListBean infoListBean;
    private List<CitySortModel> brandList=new ArrayList<>();
    private String productId;
    private AlertDialog dialog;
    private TextView mInfoSave;
    private TextView mTitleName;
    private final int EDIT_SCAN=1012;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_info);
        infoListBean = ((VerInfoListBean) getIntent().getSerializableExtra("edit"));
        initView();
        showInfo(infoListBean);
    }

    private void initView() {
        findViewById(R.id.image_back).setOnClickListener(this);
        mTitleName = ((TextView) findViewById(R.id.text_name));
        TextView mSave = (TextView) findViewById(R.id.edit_info);
        mSave.setText("保存");
        mTitleName.setText("修改信息");

        mInfoSave = ((TextView)findViewById(R.id.edit_info));
        mInfoSave.setOnClickListener(this);
        findViewById(R.id.registstype_layout).setOnClickListener(this);
        findViewById(R.id.emissionStd_layout).setOnClickListener(this);
        findViewById(R.id.type_layout).setOnClickListener(this);
        findViewById(R.id.name_layout).setOnClickListener(this);
        findViewById(R.id.brand_layout).setOnClickListener(this);
        findViewById(R.id.fuetype_layout).setOnClickListener(this);
        findViewById(R.id.tra_layout).setOnClickListener(this);
        findViewById(R.id.service_layout).setOnClickListener(this);
        findViewById(R.id.displace_layout).setOnClickListener(this);
        mLnpos = (TextView) findViewById(R.id.edit_lnpo);
        idName = (TextView) findViewById(R.id.edit_idName);
        mName = (TextView) findViewById(R.id.edit_name);
        brandName = (TextView) findViewById(R.id.edit_brand);
        styleName = (TextView) findViewById(R.id.edit_product);
        mColor = (EditText) findViewById(R.id.edit_color);
        displace = (TextView) findViewById(R.id.edit_displace);
        transmissionType = (TextView) findViewById(R.id.edit_tra);
        fuelstyle = (TextView) findViewById(R.id.edit_fuestyle);
        emissionStd = (TextView) findViewById(R.id.edit_emissionStd);
        registstype = (TextView) findViewById(R.id.edit_registstype);
        owner = (EditText) findViewById(R.id.edit_owner);
        ownerPhone = (EditText) findViewById(R.id.edit_ownerphone);
        service = (TextView) findViewById(R.id.edit_service);
        productTime = (TextView) findViewById(R.id.edit_produceTime);
        registAgency = (TextView) findViewById(R.id.edit_registagency);
        registtime = (TextView) findViewById(R.id.edit_registtime);
        distance = (TextView) findViewById(R.id.edit_current_km);
        mVin = (TextView) findViewById(R.id.edit_vin);
        findViewById(R.id.edit_scan).setOnClickListener(this);

    }

    void showInfo(VerInfoListBean info) {
        String fueType = Contact.getFueType(info.getFuelType());
        String vehicleType = Contact.getVehicleType(info.getVehicleType());
        String serviceType = Contact.getService(info.getServiceType());
        String emissionType = Contact.getEmissionType(info.getEmissionStd());

        mLnpos.setText(info.getLicensePlateNo());
        if (! info.getBrandName().equals(""))
            mName.setText(info.getBrandName());

        if (!info.getProductName().equals("")){
            brandName.setText(info.getProductName());
        }

        if (! info.getSeriesName().equals(""))
            styleName.setText(info.getSeriesName());

        if (! info.getColor().equals(""))
            mColor.setText(info.getColor());

        if (! info.getDisplacement().equals(""))
            displace.setText(info.getDisplacement()+"L");


        transmissionType.setText(Contact.TRANSMISSIONTYPE[info.getTransmissionType()]);

        if (fueType!=null)
            fuelstyle.setText(fueType);

        if (emissionType!=null)
            emissionStd.setText(emissionType);

        registstype.setText(vehicleType);

        if (!info.getOwner().equals(""))
            owner.setText(info.getOwner());

        if (!info.getRegistTime().equals(""))
            ownerPhone.setText(info.getOwnerTelephone());

        if (serviceType!=null)
            service.setText(serviceType);

        if (!info.getProduceTime().equals(""))
            productTime.setText(info.getProduceTime());

        if (!info.getRegistAgency().equals(""))
            registAgency.setText(info.getRegistAgency());

        if (!info.getRegistTime().equals(""))
            registtime.setText(info.getRegistTime());

        if (!info.getVin().equals(""))
            mVin.setText(info.getVin());

        if (info.getIdName()!=null)
            idName.setText(info.getIdName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.name_layout:
                getDate();
                break;
            case R.id.brand_layout:
                if (brandList.size()!=0){
                    getPopWindow(brandList,brandName,STYLEINFO);
                }else{
                    showBrandName(infoListBean.getBrandId(),1);
                }
                break;
            case R.id.type_layout:
                showproductName();
                break;
            case R.id.displace_layout:
                showDisplace();
                break;
            case R.id.tra_layout:
                showChangeSpeed();
                break;
            case R.id.fuetype_layout:
                showoilType();
                break;
            case R.id.emissionStd_layout:
                showEmissinStd(Contact.EMISSIONSTD,emissionStd);
                break;
            case R.id.registstype_layout:
                showEmissinStd(Contact.VEHICLETYPE,registstype);
                break;
            case R.id.service_layout:
                showEmissinStd(Contact.SERVICE,service);
                break;
            case R.id.edit_info:
                saveDate();
                break;
            case R.id.edit_scan:
                startActivityForResult(new Intent(this, CaptureActivity.class),EDIT_SCAN);
                break;

        }
    }

    private void showEmissinStd(final String[] info, final TextView showView) {

        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_listview, null);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        final PopupWindow popupWindow = new PopupWindow(view, width / 2 - 15, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView mLv = (ListView) view.findViewById(R.id.list_view);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.stype_info_car, R.id.stype_name, info);
        mLv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.set_background));
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = info[position];
                if (showView.getId()==R.id.edit_emissionStd){

                }

                showView.setText(s);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(showView, 0, 0);

    }

    private void showDisplace() {
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
                    displace.setText(content+s);
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
                transmissionType.setText(Contact.TRANSMISSIONTYPE[position]);
                dialog.dismiss();

            }
        });
//
    }
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
                fuelstyle.setText(Contact.FUELTYPE[position]);

            }
        });
    }
    private void getDate() {
        RequestParams requestParams = new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{ \"cmd\": \"vehicleBrandInfoV3\",\"auth\": {\"devKey\": \"8da849223f31407f8602dabb54ddeb92\" },\"params\": {}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                List<CitySortModel> sortModelList = JsonData.getVehicle(result);

                getPopWindow(sortModelList, mName, PRODUCTINFO);
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

    void getPopWindow(final List<CitySortModel> list, final TextView showView, final int info) {
        vehicleName.clear();
        for (CitySortModel c : list) {
            vehicleName.add(c.getCityName());
        }
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_listview, null);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        final PopupWindow popupWindow = new PopupWindow(view, width / 2 - 15, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView mLv = (ListView) view.findViewById(R.id.list_view);
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.stype_info_car, R.id.stype_name, vehicleName);
        mLv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.set_background));
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = vehicleName.get(position);
                showView.setText(s);
                if (info==PRODUCTINFO) {
                    String objId = list.get(position).getId();
                    showBrandName(objId,0);
                }else if (info==STYLEINFO){
                    productId=list.get(position).getId();
                    styleName.setText("未设置");

                }else {

                }
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(showView, 0, 0);


    }

    private void showBrandName(String objId, final int i) {
        RequestParams requestParams = new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{\"cmd\" : \" vehicleProductInfoV3 \",\"auth\": { \"devKey\": " +
                "\"8da849223f31407f8602dabb54ddeb92\"}, \"params\":{\"brandId\" : \"" + objId + "\"}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<CitySortModel> modelList = JsonData.getVehicle(result);
                brandList.clear();
                brandList.addAll(modelList);
                styleName.setText("未设置");
                if (i==0){
                    CitySortModel model = modelList.get(0);
                    productId=model.getId();

                    if (!model.getCityName().equals("")) {
                        brandName.setText(model.getCityName());
                    }
                }
                if (i==1){
                    getPopWindow(brandList,brandName,STYLEINFO);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("1313", "onError: " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void showproductName() {
        RequestParams requestParams = new RequestParams(Contact.URL);
        requestParams.setAsJsonContent(true);
        requestParams.setBodyContent("{\"cmd\" : \" vehicleStyleInfoV3 \",\"auth\": { \"devKey\": " +
                "\"8da849223f31407f8602dabb54ddeb92\"}, \"params\":{\"productId\" : \"" + productId + "\"}}");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("13131", "onSuccess: " + result);
                List<CitySortModel> modelList = JsonData.getVehicle(result);
                modelList.add(0,new CitySortModel("未设置"));
                getPopWindow(modelList,styleName,2);
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
    }
    public void saveDate() {
        if (TextUtils.isEmpty(mLnpos.getText().toString())) {
            ToastUtil.toastUtils(this, "请输入车牌号");
            return;
        } else if (TextUtils.isEmpty(brandName.getText().toString()) ) {
            ToastUtil.toastUtils(this, "请输入品牌名");
        } else if (TextUtils.isEmpty(displace.getText().toString()) ){
            ToastUtil.toastUtils(this, "请输入排量");
        }else if (TextUtils.isEmpty(transmissionType.getText().toString())){
            ToastUtil.toastUtils(this, "请输入变速箱类型");
        }
        else {
            if (IsNetwork.isNetworkConnected(context)) {
                Map<String, UserInfo> data = SharedData.getData(this);
                UserInfo userInfo = data.get(Contact.USERINFO);
                showRequestDialog();
                //车牌号正则表达式
//                    if (! IsPhone.IsVehicleNumber(mLpno.getText().toString().trim())){
//                        ToastUtil.toastUtils(AddCarActivity.this,"请输入正确的车牌号");
//                        return;
//                    }


                RequestParams requestParams = new RequestParams(Contact.URL);
                requestParams.setAsJsonContent(true);

                requestParams.setBodyContent("{\"cmd\":\"modVehicelInfo\",\"auth\":{\"devKey\":\"8da849223f31407f8602dabb54ddeb92\"" +
                        ",\"userName\":\"" + userInfo.getUserName() + "\", \"password\": \""+userInfo.getUserPassword()+"\" },\"params\": { \"objId\": \"" + infoListBean.getObjId() + "\"}}");
                Log.e("3131", "addVehcile: " + requestParams.getBodyContent());
                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        UserInfo userInfo = JsonData.getStatu(result);
                        Log.e("111", "onSuccess: " + result);
                        if (userInfo.getResultNote().equals("Success")) {
                            getInfoBasic();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("222", "onError: " + ex.getMessage());

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            } else
                ToastUtil.toastUtils(context, "请检查网络状态");
        }
    }

    private void getInfoBasic() {
        UserInfo userInfo = SharedData.getData(context).get(Contact.USERINFO);
        if (IsNetwork.isNetworkConnected(context)) {
            RequestParams requestParams = new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{\"cmd\": \"vehicelInfo\",\"auth\": {\"devKey\": \"8da849223f31407f8602dabb54ddeb92\", \"userName\": \"" + userInfo.getUserName() + "\"," +
                    " \"password\": \"" + userInfo.getUserPassword() + "\"},\"params\": { \"objId\": \"" + infoListBean.getObjId() + "\"}}");
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    VerInfoListBean vehicleInfo = JsonData.getVehicleInfo(result);

                    String resultNote = vehicleInfo.getResultNote();
                    Log.e("44444", "onSuccess: " +vehicleInfo.toString());
                    if (resultNote.equals("Success")) {
                        Intent intent = new Intent(context, BasicActivity.class);
                        intent.putExtra("basicInfo",vehicleInfo);
                        setResult(RESULT_OK,intent);
                        dialog.dismiss();
                        finish();
                    }
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
    }

    private void showRequestDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_progress, null);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==EDIT_SCAN && resultCode==RESULT_OK){
            mVin.setText(data.getExtras().getString("result"));
        }
    }
}
