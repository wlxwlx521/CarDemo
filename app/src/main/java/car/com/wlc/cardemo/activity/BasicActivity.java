package car.com.wlc.cardemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.StyleNameBean;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.ToastUtil;

/**
 * 车辆基本信息
 */
public class BasicActivity extends AppActivity {

    private Context context=BasicActivity.this;
    private TextView mLnpos,mName,mColor,brandName,styleName,idName;

    private TextView displace,transmissionType,mVin,service,registstype,registAgency,registtime,distance,fuelstyle,emissionStd,owner,ownerPhone,productTime;

    private TextView editInfo;
    private final int REQUESTCODE=101;

    private ImageView btnBack;
    private VerInfoListBean verInfoListBean;
    private StyleNameBean styleNameBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        verInfoListBean = ((VerInfoListBean) getIntent().getSerializableExtra("objId"));
        initView();
    }

    /**
     * 初始化控件
     */

    private void initView() {
        editInfo = ((TextView) findViewById(R.id.edit_info));
        btnBack = ((ImageView) findViewById(R.id.image_back));
        ((TextView) findViewById(R.id.text_name)).setText("基本信息");
        editInfo.setText("编辑");
        mLnpos = (TextView) findViewById(R.id.basic_lnpo);
        idName = (TextView) findViewById(R.id.basic_idName);
        mName = (TextView) findViewById(R.id.basic_name);
        brandName = (TextView) findViewById(R.id.basic_brand);
        styleName = (TextView) findViewById(R.id.basic_styleName);
        mColor = (TextView) findViewById(R.id.basic_color);
        displace = (TextView)findViewById(R.id.basic_displace);
        transmissionType = (TextView)findViewById(R.id.basic_tra);
        fuelstyle = (TextView) findViewById(R.id.basic_fuelstyle);
        emissionStd = (TextView) findViewById(R.id.basic_emissionStd);
        registstype = (TextView) findViewById(R.id.basic_registstype);
        owner = (TextView) findViewById(R.id.basic_owner);
        ownerPhone = (TextView)findViewById(R.id.basic_ownerphone);
        service = (TextView) findViewById(R.id.basic_service);
        productTime = (TextView) findViewById(R.id.basic_produceTime);
        registAgency = (TextView) findViewById(R.id.basic_registagency);
        registtime = (TextView) findViewById(R.id.basic_registtime);
        distance = (TextView) findViewById(R.id.basic_km);
        mVin = (TextView) findViewById(R.id.basic_vin);
        if (verInfoListBean!=null)
        showInfo(verInfoListBean);
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsNetwork.isNetworkConnected(context)){
                    Intent intent = new Intent(context,EditCarInfoActivity.class);
                    intent.putExtra("edit",verInfoListBean);
                    startActivityForResult(intent,REQUESTCODE);
                }else {
                    ToastUtil.toastUtils(context,"请检查网络");
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUESTCODE&&resultCode==RESULT_OK){
            VerInfoListBean basicInfo = (VerInfoListBean) data.getSerializableExtra("basicInfo");
            Log.e("333", "onActivityResult: " + basicInfo.toString());
            showInfo(basicInfo);
        }
    }

    /**
     * 车辆基本信息
     * @param info
     */
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

//        if (! styleNameBean.getStyleName().equals(""))
//            styleName.setText(styleNameBean.getStyleName());

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
}
