package car.com.wlc.cardemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import car.com.wlc.cardemo.AppActivity;
import car.com.wlc.cardemo.R;
import car.com.wlc.cardemo.javaBean.Contact;
import car.com.wlc.cardemo.javaBean.UserInfo;
import car.com.wlc.cardemo.javaBean.VerInfoListBean;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.utils.ToastUtil;
import car.com.wlc.cardemo.zxing.activity.CaptureActivity;

/**
 * 绑定车辆接口
 */
public class BindDeviceVehicleActivity extends AppActivity implements View.OnClickListener {

    private EditText mBindDevice;
    private Context context=BindDeviceVehicleActivity.this;
    private VerInfoListBean infoListBean;
    private Toolbar mTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_device_vehicle);
        infoListBean = ((VerInfoListBean) getIntent().getSerializableExtra("bean"));
        initView();

    }

    private void initView() {
        mTool = ((Toolbar) findViewById(R.id.toolBar_layout));
        mTool.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTool.setTitle("绑定车辆");
        mBindDevice = ((EditText) findViewById(R.id.edit_device));
       findViewById(R.id.btn_bind_device).setOnClickListener(this);
        findViewById(R.id.device_scan).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_bind_device:
                String device = mBindDevice.getText().toString().trim();
                    bindDevice(device);
                break;
            case R.id.device_scan:
                Intent intent = new Intent(context, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,100);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==100 && resultCode==RESULT_OK){

            String result = data.getExtras().getString("result");
            mBindDevice.setText(result);
        }
    }

    /**
     * 设备码
     * @param device
     */
    private void bindDevice(String device) {
        if (TextUtils.isEmpty(device)){
            ToastUtil.toastUtils(context,"请输入设备码");
        }
        else if (IsNetwork.isNetworkConnected(context)){
            final UserInfo userInfo = SharedData.getData(context).get(Contact.USERINFO);
            RequestParams requestParams =new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{\"cmd\": \"bindDeviceVehicle\", \"auth\": { \"devKey\": \""+Contact.KEY+"\"," +
                    " \"userName\": \""+userInfo.getUserName()+"\",\"password\": \""+userInfo.getUserPassword()+"\"}, \"params\": { \"deviceId\": \""+device+"\",\"objId\": \""+infoListBean.getObjId()+"\",\"lpno\": \""+infoListBean.getLicensePlateNo()+"\",\"ownerUserId\": \""+userInfo.getUserId()+"\"}}");
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    UserInfo statu = JsonData.getStatu(result);
                    if (statu.getResultNote().equals("Success")){
                        ToastUtil.toastUtils(context,"绑定成功");
                    }
                    else {
                        ToastUtil.toastUtils(context,statu.getResultNote());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("3232", "onError: " + ex.getMessage());
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
}
