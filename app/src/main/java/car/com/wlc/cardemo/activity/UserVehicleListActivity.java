package car.com.wlc.cardemo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
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
import car.com.wlc.cardemo.utils.DatabaseOpenHelper;
import car.com.wlc.cardemo.utils.IsNetwork;
import car.com.wlc.cardemo.utils.JsonData;
import car.com.wlc.cardemo.utils.SharedData;
import car.com.wlc.cardemo.utils.ToastUtil;

public class UserVehicleListActivity extends AppActivity implements View.OnClickListener {


    private Context context = UserVehicleListActivity.this;


    private ImageView mLeft;
    private ImageView mRight;

    private Toolbar mToolBar;
    private List<VerInfoListBean> mList ;
    private int SUCCESS = 100;
    private int currentPosition = 0;
    private ImageView mCarImage;
    private TextView mLnpo;
    private AlertDialog dialog;
    private Button btnBinding;
    private String[] isBinding={"未绑定","绑定"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicle_list);
        mList = (ArrayList<VerInfoListBean>) getIntent().getExtras().getSerializable(Contact.USERCARKEY);
        initView();

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("currentPosition", currentPosition);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUCCESS && resultCode == RESULT_OK) {
            VerInfoListBean listBean = (VerInfoListBean) data.getSerializableExtra(Contact.ADDCARKEY);
            mList.add(listBean);
            currentPosition = mList.size() - 1;
            isInvisible(mList.size() - 1);
            addCarResult(listBean);
        }

    }

    private void addCarResult(VerInfoListBean listBean) {
        String url = listBean.getPicture();
        Picasso.with(context).load(url).placeholder(R.mipmap.cxz_common_location_car).
                error(R.mipmap.cxz_common_location_car).into(mCarImage);
        mLnpo.setText(listBean.getLicensePlateNo());
    }

    private UserInfo getUserData() {
        Map<String, UserInfo> data = SharedData.getData(context);
        UserInfo userInfo = data.get(Contact.USERINFO);
        return userInfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_des:

                --currentPosition;
                countControl();
                isInvisible(currentPosition);
                showCurrentCar(currentPosition);
                break;
            case R.id.right_add:

                ++currentPosition;
                countControl();
                isInvisible(currentPosition);
                showCurrentCar(currentPosition);
                break;
            case R.id.btn_deletecar:
             showDialogAlert();
                break;
            case R.id.btn_addcar:
                startActivityForResult(new Intent(context, AddCarActivity.class), SUCCESS);
                break;
            case R.id.info_layout:
                showRequestDialog();
                break;
            case R.id.sell_layout:
                break;
            case R.id.insur_layout:
                break;
            case R.id.btn_bangding:
                VerInfoListBean bean = mList.get(currentPosition);
                if (bean.getIsBind() == 0){

                    Intent intent = new Intent(context, BindDeviceVehicleActivity.class);
                    intent.putExtra("bean",bean);
                    startActivity(intent);
                }else {
                    ToastUtil.toastUtils(context,"购买服务未开启");
                }

                break;


        }
    }

    private void isInvisible(int i) {
        if (mList.size() > 1 && i < mList.size()) {
            mRight.setVisibility(View.VISIBLE);
            mRight.setClickable(true);
        }
        if (mList.size() > 1 && i != 0) {
            mLeft.setVisibility(View.VISIBLE);
            mLeft.setClickable(true);
        }
        if (i == 0) {
            mLeft.setVisibility(View.INVISIBLE);

            mLeft.setClickable(false);

        }
        if (mList.size() <= 1) {
            mRight.setClickable(false);
            mLeft.setClickable(false);
            mRight.setVisibility(View.INVISIBLE);
            mLeft.setVisibility(View.INVISIBLE);
        }
        if (i == mList.size() - 1 && mList.size() > 1) {
            mRight.setVisibility(View.INVISIBLE);
            mRight.setClickable(false);
        }
    }

    private void countControl() {
        currentPosition = currentPosition <= 0 ? 0 : currentPosition < mList.size() - 1 ? currentPosition : mList.size() - 1;
    }

    /**
     * 显示当前车信息
     *
     * @param i
     */

    private void showCurrentCar(int i) {
        VerInfoListBean bean = mList.get(i);

        String url = "http://static.cpsdna.com/laso/images/vehicle_logo/" + bean.getPicture();
        Picasso.with(context).load(url).placeholder(R.mipmap.cxz_common_location_car).
                error(R.mipmap.cxz_common_location_car).into(mCarImage);
        mLnpo.setText(bean.getLicensePlateNo());
        if (bean.getIsBind() ==0 ){
            btnBinding.setText("绑定设备");
        }else {
            btnBinding.setText("购买服务");
        }

    }

    private void initView() {
        findViewById(R.id.btn_addcar).setOnClickListener(this);
        findViewById(R.id.btn_deletecar).setOnClickListener(this);
        btnBinding = ((Button) findViewById(R.id.btn_bangding));
        if (mList != null){
            if (mList.get(0).getIsBind() ==0){
                btnBinding.setText("绑定设备");
            }else {
                btnBinding.setText("购买服务");
            }
        }
        btnBinding.setOnClickListener(this);
        mCarImage = ((ImageView) findViewById(R.id.image_mycar_picture));
        mLnpo = ((TextView) findViewById(R.id.text_lnpo));
        mToolBar = ((Toolbar) findViewById(R.id.toolBar_layout));
        mToolBar.setNavigationIcon(R.mipmap.iapppay_title_button_back_on);
        mToolBar.setTitle("我的车辆");
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLeft = ((ImageView) findViewById(R.id.left_des));
        mLeft.setOnClickListener(this);

        mRight = ((ImageView) findViewById(R.id.right_add));
        mRight.setOnClickListener(this);
        findViewById(R.id.info_layout).setOnClickListener(this);
        showDate();
    }

    /**
     * 删除车辆警告提醒
     */
    private void showDialogAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("删除车辆");
        builder.setMessage("您确定删除车辆");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deletCar();
            }


        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void showRequestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_progress, null);
        dialog = builder.create();
        dialog.setView(view);
        dialog.show();
        showBasicInfo();

    }

    /**
     * 确定删除
     */
    private void deletCar() {
        final VerInfoListBean bean = mList.get(currentPosition);

        if (IsNetwork.isNetworkConnected(context)) {
            RequestParams requestParams = new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);

            requestParams.setBodyContent("{\"cmd\": \"delVehicle\", \"auth\": { \"devKey\": \"8da849223f31407f8602dabb54ddeb92\",\"userName\": \"" + getUserData().getUserName() + "\",\"password\": \"" + getUserData().getUserPassword() + "\"},\"params\": {\"objId\": \"" + bean.getObjId() + "\"}}");

            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("2121", "onSuccess: " + result);
                    UserInfo info = JsonData.getStatu(result);

                    Log.e("TAG", "onSuccess: " + result);
                    if (info.getResultNote().equals("Success")) {
                        ToastUtil.toastUtils(context, "删除成功");

                        try {
                            DatabaseOpenHelper.getInstance().deleteById(UserInfo.class, bean.getObjId());
                            DatabaseOpenHelper.getInstance().deleteById(VerInfoListBean.class, bean.getObjId());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        mList.remove(currentPosition);
                        showDate();
                    } else {
                        ToastUtil.toastUtils(context, info.getResultNote());
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
        } else {
            ToastUtil.toastUtils(context, "请检查网络连接网状态！");
        }
    }

    private void showBasicInfo() {
        VerInfoListBean bean = mList.get(currentPosition);
        UserInfo userInfo = SharedData.getData(context).get(Contact.USERINFO);
        if (IsNetwork.isNetworkConnected(context)) {
            RequestParams requestParams = new RequestParams(Contact.URL);
            requestParams.setAsJsonContent(true);
            requestParams.setBodyContent("{\"cmd\": \"vehicelInfo\",\"auth\": {\"devKey\": \"8da849223f31407f8602dabb54ddeb92\", \"userName\": \"" + userInfo.getUserName() + "\"," +
                    " \"password\": \"" + userInfo.getUserPassword() + "\"},\"params\": { \"objId\": \"" + bean.getObjId() + "\"}}");
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {

                    VerInfoListBean vehicleInfo = JsonData.getVehicleInfo(result);
                    Log.e("1414", "onSuccess: " + result);
                    if (vehicleInfo.getResultNote().equals("Success")){
                        startIntent(vehicleInfo);

                    }


                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    dialog.dismiss();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        }else
        startIntent(null);
        dialog.dismiss();
    }

    private void startIntent(VerInfoListBean verInfoListBean) {
        dialog.dismiss();
        Intent intent = new Intent(context, BasicActivity.class);
        intent.putExtra("objId",verInfoListBean);
        startActivity(intent);


    }


    private void showDate() {
        countControl();
        isInvisible(currentPosition);
        showCurrentCar(currentPosition);
    }


}
